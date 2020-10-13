package com.css.im.chat.service.impl;

import com.css.common.BaseService;
import com.css.im.cache.service.IMSessionService;
import com.css.im.chat.ChatStatus;
import com.css.im.chat.mapper.ChatGroupMapper;
import com.css.im.chat.mapper.ChatMessageReceiverMapper;
import com.css.im.chat.mapper.ChatMessageSendMapper;
import com.css.im.chat.model.ChatGroup;
import com.css.im.chat.model.ChatMessageReceiver;
import com.css.im.chat.model.ChatMessageSend;
import com.css.im.chat.service.IMChatService;
import com.css.im.chat.service.IMMessageEventHandler;
import com.css.im.chat.service.IMMsgService;
import com.css.utils.DateTimeUtils;
import com.css.utils.UUIDUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by wx on 2020-08-31
 */
@Service
public class IMChatServiceImpl extends BaseService implements IMChatService {

    @Autowired
    IMSessionService iMSessionService;

    @Autowired
    ChatGroupMapper chatGroupMapper;

    @Autowired
    ChatMessageSendMapper chatMessageSendMapper;

    @Autowired
    ChatMessageReceiverMapper chatMessageReceiverMapper;
    @Autowired
    IMMsgService imMsgService;

    @Override
    public boolean response(JSONObject message, String uid, IMMessageEventHandler handler) {
        JSONArray messages = new JSONArray();
        messages.add(message);
        return response(messages, uid, handler);
    }

    @Override
    public List<String> inform(JSONObject message, List<String> targetUids, IMMessageEventHandler handler) {
        JSONArray messages = new JSONArray();
        messages.add(message);
        return inform(messages, targetUids, handler);
    }

    @Override
    public List<String> inform(JSONObject message, String... uids) {
        return inform(message, Arrays.asList(uids), null);
    }

    @Transactional
    @Override
    public synchronized boolean response(JSONArray messages, String uid, IMMessageEventHandler handler) {
        try {
            Session session = iMSessionService.getUserSession(uid);
            sessionWrite(session, messages.toString());
            if (handler != null) {
                handler.onSuccess(Arrays.asList(uid));
            }
            return true;
        } catch (Exception e) {
            if (handler != null) {
                handler.onFail();
            }
            logger.error(String.format("响应用户：%s 消息：%s 异常:%s", uid, messages.toString(),e.getMessage()));
        }
        return false;
    }

    @Transactional
    @Override
    public synchronized List<String> inform(JSONArray messages, List<String> targetUids, IMMessageEventHandler handler) {
        List<String> allOnlineUids = iMSessionService.getAllOnlineUsers();
        //取得target users中所有在线的用户
        List<String> onLineUids = targetUids.stream().filter(item -> allOnlineUids.contains(item)).collect(Collectors.toList());
        List<String> successUids = new ArrayList<String>();
        for (String uid : onLineUids) {
            try {
                Session session = iMSessionService.getUserSession(uid);
                sessionWrite(session, messages.toString());
                successUids.add(uid);
            } catch (Exception e) {
                logger.error(String.format("通知用户：%s 消息：%s 异常", uid, messages.toString()));
            }
        }
        if (handler != null) {
            handler.onSuccess(successUids);
        }
        return successUids;
    }


    @Transactional
    @Override
    public ChatMessageSend chat(String fromUid, String toUid, String content, String plainText,
                                ChatStatus.MessageContentType contentType, String fileId) {
        try {
            ChatMessageSend messageSend = new ChatMessageSend();
            messageSend.setMsgTime(DateTimeUtils.getTodayDateTime());
            messageSend.setChatType(ChatStatus.ChatType.Chat.status().intValue());
            messageSend.setFromUid(fromUid);
            messageSend.setToUid(toUid);
            messageSend.setContentType(contentType.status());
            messageSend.setMsgSeq(DateTimeUtils.formatCurrentTime());
            messageSend.setMsgId(UUIDUtils.generateUUID());
            messageSend.setPlainText(plainText);
            if (StringUtils.isNotBlank(fileId)) {
                messageSend.setFileId(fileId);
            }
            //针对于数据量较大的消息体特殊处理
            messageSend.setContent(content);
            imMsgService.cacheMessage(messageSend);

            ChatMessageReceiver receiver = new ChatMessageReceiver();
            receiver.setFromUid(fromUid);
            receiver.setId(UUIDUtils.generateUUID());
            receiver.setToUid(toUid);
            receiver.setMsgId(messageSend.getMsgId());
            receiver.setStatus(ChatStatus.MessageStatus.UnSend.status());
            chatMessageSendMapper.insert(messageSend);
            chatMessageReceiverMapper.insert(receiver);
            return messageSend;
        } catch (Exception e) {
            logger.error(String.format("%s发送给%s的单聊信息：%s入库失败", fromUid, toUid, content), e.getMessage());
        }
        return null;
    }

    @Transactional
    @Override
    public ChatMessageSend chatGroup(String fromUid, String toGid, String content, String plainText,
                                     ChatStatus.MessageContentType contentType, String fileId) {
        try {
            //获取群用户ids
            ChatGroup chatGroup = chatGroupMapper.selectByPrimaryKey(toGid);
            if (chatGroup.getStatus().shortValue() != ChatStatus.ChatGroupStatus.Normal.status()
                    || StringUtils.isBlank(chatGroup.getMembersUids())) {
                return null;
            }
            String[] uids = chatGroup.getMembersUids().split(",");

            ChatMessageSend messageSend = new ChatMessageSend();
            messageSend.setMsgTime(DateTimeUtils.getTodayDateTime());
            messageSend.setChatType(ChatStatus.ChatType.GroupChat.status().intValue());
            messageSend.setFromUid(fromUid);
            messageSend.setChatGroupId(toGid);
            messageSend.setContentType(contentType.status());
            messageSend.setMsgSeq(DateTimeUtils.formatCurrentTime());
            messageSend.setMsgId(UUIDUtils.generateUUID());
            messageSend.setPlainText(plainText);
            //特殊处理大图片消息
            messageSend.setContent(content);
            imMsgService.cacheMessage(messageSend);
            if (StringUtils.isNotBlank(fileId)) {
                messageSend.setFileId(fileId);
            }

            chatMessageSendMapper.insert(messageSend);
            for (String uid : uids) {
                if (uid.equals(fromUid)) {
                    continue;
                }
                ChatMessageReceiver receiver = new ChatMessageReceiver();
                receiver.setFromUid(fromUid);
                receiver.setId(UUIDUtils.generateUUID());
                receiver.setToUid(uid);
                receiver.setMsgId(messageSend.getMsgId());
                receiver.setStatus(ChatStatus.MessageStatus.UnSend.status());
                chatMessageReceiverMapper.insert(receiver);
            }
            return messageSend;
        } catch (Exception e) {
            logger.error(String.format("%s发送给群%s的聊聊信息：%s入库失败", fromUid, toGid, content), e.getMessage());
        }
        return null;
    }

    @Override
    public ChatMessageSend operateGroup(String fromUid, String toGid, String content, List<String> receivers) {
        try {
            //获取群用户ids
            ChatGroup chatGroup = chatGroupMapper.selectByPrimaryKey(toGid);
            if (StringUtils.isBlank(chatGroup.getMembersUids())) {
                return null;
            }
            ChatMessageSend messageSend = new ChatMessageSend();
            messageSend.setMsgTime(DateTimeUtils.getTodayDateTime());
            messageSend.setChatType(ChatStatus.ChatType.GroupOperate.status().intValue());
            messageSend.setFromUid(fromUid);
            messageSend.setChatGroupId(toGid);
            messageSend.setContent(content);
            messageSend.setContentType(ChatStatus.MessageContentType.Text.status());
            messageSend.setMsgSeq(DateTimeUtils.formatCurrentTime());
            messageSend.setMsgId(UUIDUtils.generateUUID());
            chatMessageSendMapper.insert(messageSend);
            for (String uid : receivers) {
                if (uid.equals(fromUid)) {
                    continue;
                }
                ChatMessageReceiver receiver = new ChatMessageReceiver();
                receiver.setFromUid(fromUid);
                receiver.setId(UUIDUtils.generateUUID());
                receiver.setToUid(uid);
                receiver.setMsgId(messageSend.getMsgId());
                receiver.setStatus(ChatStatus.MessageStatus.UnSend.status());
                chatMessageReceiverMapper.insert(receiver);
            }
            return messageSend;
        } catch (Exception e) {
            logger.error(String.format("%s发送给群%s的聊聊信息：%s入库失败", fromUid, toGid, content), e.getMessage());
        }
        return null;
    }

    @Transactional
    @Override
    public ChatMessageSend sysBroadcast(String fromUid, List<String> toUids, String content,
                                        ChatStatus.MessageContentType contentType, String fileId) {
        try {
            ChatMessageSend messageSend = new ChatMessageSend();
            messageSend.setMsgTime(DateTimeUtils.getTodayDateTime());
            messageSend.setChatType(ChatStatus.ChatType.SysChat.status().intValue());
            messageSend.setFromUid(fromUid);
            messageSend.setContent(content);
            messageSend.setContentType(contentType.status());
            messageSend.setMsgSeq(DateTimeUtils.formatCurrentTime());
            messageSend.setMsgId(UUIDUtils.generateUUID());
            if (StringUtils.isNotBlank(fileId)) {
                messageSend.setFileId(fileId);
            }

            chatMessageSendMapper.insert(messageSend);
            for (String uid : toUids) {
                ChatMessageReceiver receiver = new ChatMessageReceiver();
                receiver.setFromUid(messageSend.getFromUid());
                receiver.setId(UUIDUtils.generateUUID());
                receiver.setToUid(uid);
                receiver.setMsgId(messageSend.getMsgId());
                receiver.setStatus(ChatStatus.MessageStatus.UnSend.status());
                chatMessageReceiverMapper.insert(receiver);
            }
            return messageSend;
        } catch (Exception e) {
            logger.error(String.format("发送的广播消息：%s入库失败", content), e.getMessage());
        }
        return null;
    }

    @Override
    public ChatMessageSend OANotify(String fromUid, List<String> toUids, String content) {
        try {
            ChatMessageSend messageSend = new ChatMessageSend();
            messageSend.setMsgTime(DateTimeUtils.getTodayDateTime());
            //暂时当做是单聊
            messageSend.setChatType(ChatStatus.ChatType.SysChat.status().intValue());
            messageSend.setFromUid(fromUid);
            messageSend.setContent(content);
            messageSend.setContentType(ChatStatus.MessageContentType.Text.status());
            messageSend.setMsgSeq(DateTimeUtils.formatCurrentTime());
            messageSend.setMsgId(UUIDUtils.generateUUID());
            chatMessageSendMapper.insert(messageSend);
            for (String uid : toUids) {
                ChatMessageReceiver receiver = new ChatMessageReceiver();
                receiver.setFromUid(messageSend.getFromUid());
                receiver.setId(UUIDUtils.generateUUID());
                receiver.setToUid(uid);
                receiver.setMsgId(messageSend.getMsgId());
                receiver.setStatus(ChatStatus.MessageStatus.UnSend.status());
                chatMessageReceiverMapper.insert(receiver);
            }
            return messageSend;
        } catch (Exception e) {
            logger.error(String.format("OA发送的消息提醒：%s入库失败", content), e.getMessage());
        }
        return null;
    }

}
