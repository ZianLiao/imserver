package com.css.im.chat.service.impl;

import com.css.common.BaseService;
import com.css.im.cache.service.IMSessionService;
import com.css.im.chat.ChatStatus;
import com.css.im.chat.IMMessage;
import com.css.im.chat.IMMsgType;
import com.css.im.chat.mapper.ChatMessageReceiverMapper;
import com.css.im.chat.mapper.ChatMessageSendMapper;
import com.css.im.chat.model.*;
import com.css.im.chat.service.IMChatService;
import com.css.im.chat.service.IMMessageEventHandler;
import com.css.im.chat.service.IMMsgService;
import com.css.im.config.FileConfig;
import com.css.im.file.model.FileItem;
import com.css.im.file.service.IMFileService;
import com.css.im.sys.mapper.SysUserMapper;
import com.css.im.sys.model.SysUser;
import com.css.im.sys.service.SysUserService;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by wx on 2020-09-10
 */
@Service("iMMsgService")
public class IMMsgServiceImpl extends BaseService implements IMMsgService {
    @Autowired
    IMChatService imChatService;

    @Autowired
    IMSessionService imSessionService;

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysUserService sysUserService;

    @Autowired
    IMSessionService iMSessionService;

    @Autowired
    ChatMessageReceiverMapper chatMessageReceiverMapper;
    @Autowired
    ChatMessageSendMapper chatMessageSendMapper;

    @Autowired
    FileConfig fileConfig;

    @Autowired
    IMFileService imFileService;

    /**
     * 上线
     *
     * @param uid
     */
    @Override
    public void informOnline(String uid) {
        JSONObject object = new JSONObject();
        SysUser user = sysUserMapper.selectByPrimaryKey(uid);
        object.put("msg", String.format("%s上线了!", user.getUsername()));
        //构建上线消息体
        IMMessage message = IMMessage.newIMMessage(IMMsgType.LOGIN);
        message.setContent(object);
        List<String> allOnlineUids = iMSessionService.getAllOnlineUsers();
        //      allOnlineUids.remove(uid);
        //TODO：
        imChatService.inform(message.toJSONObject(), allOnlineUids, new IMMessageEventHandler() {
            @Override
            public void onSuccess(List<String> uids) {

            }

            @Override
            public void onFail() {

            }
        });
    }

    @Override
    public void informOffline(String uid) {
        JSONObject object = new JSONObject();
        SysUser user = sysUserMapper.selectByPrimaryKey(uid);
        object.put("msg", String.format("%s下线了!", user.getUsername()));
        //构建上线消息体
        IMMessage message = IMMessage.newIMMessage(IMMsgType.LOGOUT);
        message.setContent(object);
        List<String> allOnlineUids = iMSessionService.getAllOnlineUsers();
        allOnlineUids.remove(uid);
        //TODO:
        imChatService.inform(message.toJSONObject(), allOnlineUids, new IMMessageEventHandler() {
            @Override
            public void onSuccess(List<String> uids) {

            }

            @Override
            public void onFail() {

            }
        });
    }

    @Override
    public void informLeaveGroup(String uid, String gid) {
        JSONObject object = new JSONObject();
        SysUser user = sysUserMapper.selectByPrimaryKey(uid);
        object.put("msg", String.format("%s退出群组!", user.getUsername()));
        object.put("gid", gid);
        //构建上线消息体
        IMMessage message = IMMessage.newIMMessage(IMMsgType.GROUP_LEAVE);
        message.setContent(object);
        List<String> allOnlineUids = iMSessionService.getAllOnlineUsers();
        allOnlineUids.remove(uid);
        //TODO：
        imChatService.inform(message.toJSONObject(), allOnlineUids, new IMMessageEventHandler() {
            @Override
            public void onSuccess(List<String> uids) {

            }

            @Override
            public void onFail() {

            }
        });
    }

    @Override
    public void informKiffGroup(String uid, String gid) {

    }

    @Override
    public void informEnterGroup(String uid, String gid) {

    }


    @Override
    public List<ChatMessageSend> getOffLineMsg(String uid) {
        if (uid == null) {
            return Collections.EMPTY_LIST;
        }
        ChatMessageReceiverExample example = new ChatMessageReceiverExample();
        example.createCriteria().andToUidEqualTo(uid).andStatusEqualTo(ChatStatus.MessageStatus.UnSend.status());
        List<ChatMessageReceiver> messageReceivers = chatMessageReceiverMapper.selectByExample(example);
        List<String> msgIds = messageReceivers.stream().map(ChatMessageReceiver::getMsgId).collect(Collectors.toList());
        ChatMessageSendExample sendExample = new ChatMessageSendExample();
        sendExample.setOrderByClause("msg_seq");
        if (msgIds.size() > 0) {
            sendExample.createCriteria().andMsgIdIn(msgIds);
            List<ChatMessageSend> messageSends = chatMessageSendMapper.selectByExampleWithBLOBs(sendExample);
            messageSends = messageSends.stream().map(this::getRealMsg).collect(Collectors.toList());
            return messageSends;
        } else {
            return Collections.EMPTY_LIST;
        }

    }

    private ChatMessageSend getRealMsg(ChatMessageSend messageSend) {
        if (messageSend != null && messageSend.getContent() != null
                && messageSend.getContent().equals(messageSend.getMsgId())) {
            messageSend.setContent(readCacheMsg(messageSend.getMsgId()));
        }
        return messageSend;
    }

    @Transactional
    @Override
    public boolean updateMessageReceiverStatus(String msgId, String fromUid, List<String> toUids,
                                               ChatStatus.MessageStatus targetStatus) {
        if (msgId == null || fromUid == null || toUids == null
                || toUids.size() < 1 || targetStatus == null) {
            return false;
        }
        ChatMessageReceiverExample example = new ChatMessageReceiverExample();
        example.createCriteria().andFromUidEqualTo(fromUid)
                .andToUidIn(toUids).andMsgIdEqualTo(msgId);
        ChatMessageReceiver receiver = new ChatMessageReceiver();
        receiver.setStatus(targetStatus.status());
        int affected = chatMessageReceiverMapper.updateByExampleSelective(receiver, example);
        return affected > 0;
    }

    @Override
    public boolean updateMessageReceiverStatus(String msgId, String fromUid, String toUid,
                                               ChatStatus.MessageStatus originStatus,
                                               ChatStatus.MessageStatus targetStatus) {
        ChatMessageReceiverExample filter = new ChatMessageReceiverExample();
        filter.createCriteria().andStatusEqualTo(originStatus.status()).andFromUidEqualTo(fromUid).
                andToUidEqualTo(toUid).andMsgIdEqualTo(msgId);
        ChatMessageReceiver chatMessageReceiver = new ChatMessageReceiver();
        chatMessageReceiver.setStatus(targetStatus.status());
        int affected = chatMessageReceiverMapper.updateByExampleSelective(chatMessageReceiver, filter);
        return affected > 0;
    }

    @Override
    public boolean updateMessageReceiverStatus(List<ChatMessageSend> messageSends, List<String> toUids,
                                               ChatStatus.MessageStatus targetStatus) {
        if (null == messageSends || messageSends.size() == 0 || toUids == null || toUids.size() == 0) {
            return false;
        }
        ChatMessageReceiverExample example = new ChatMessageReceiverExample();
        for (ChatMessageSend messageSend : messageSends) {
            example.or().andFromUidEqualTo(messageSend.getFromUid())
                    .andToUidIn(toUids).andMsgIdEqualTo(messageSend.getMsgId());
        }
        ChatMessageReceiver receiver = new ChatMessageReceiver();
        receiver.setStatus(targetStatus.status());
        int affected = chatMessageReceiverMapper.updateByExampleSelective(receiver, example);
        return affected > 0;
    }

    @Override
    public boolean updateMessageReceiverStatus(String senderId, String receiverId,
                                               ChatStatus.MessageStatus targetStatus) {
        ChatMessageReceiverExample filter = new ChatMessageReceiverExample();
        filter.createCriteria().andFromUidEqualTo(senderId).andToUidEqualTo(receiverId);
        ChatMessageReceiver receiver = new ChatMessageReceiver();
        receiver.setStatus(targetStatus.status());
        int affected = chatMessageReceiverMapper.updateByExampleSelective(receiver, filter);
        return affected > 0;
    }

    @Override
    public boolean updateMsgStatusByGroupId(String groupId, ChatStatus.MessageStatus targetStatus) {
        ChatMessageSendExample filter = new ChatMessageSendExample();
        filter.createCriteria().andChatGroupIdEqualTo(groupId).andFileIdIsNull();
        List<ChatMessageSend> messageSends = chatMessageSendMapper.selectByExample(filter);
        List<String> readMsgIdList = messageSends.stream().
                map(ChatMessageSend::getMsgId).collect(Collectors.toList());
        ChatMessageReceiverExample receiverExample = new ChatMessageReceiverExample();
        receiverExample.createCriteria().andMsgIdIn(readMsgIdList).
                andStatusEqualTo(ChatStatus.MessageStatus.Send.status());
        ChatMessageReceiver messageReceiver = new ChatMessageReceiver();
        messageReceiver.setStatus(targetStatus.status());
        int affected = chatMessageReceiverMapper.updateByExampleSelective(messageReceiver, receiverExample);
        return affected > 0;
    }


    public boolean updateMessageReceiverStatus(String fileId, ChatStatus.MessageStatus targetStatus, String receiverId) {
        if (StringUtils.isBlank(fileId)) {
            return false;
        }
        int affected = chatMessageReceiverMapper.updateStatusByFileId(fileId, targetStatus.status(), receiverId);
        return affected > 0;
    }

    public boolean updateMessageSendContentType(String msgId, ChatStatus.MessageContentType targetStatus) {
        ChatMessageSend send = new ChatMessageSend();
        send.setMsgId(msgId);
        send.setContentType(targetStatus.status());
        int affected = chatMessageSendMapper.updateByPrimaryKeySelective(send);
        return affected > 0;
    }

    public boolean updateMessageReceiverByMsgId(ChatMessageReceiver chatMessageReceiver) {
        ChatMessageReceiverExample example = new ChatMessageReceiverExample();
        example.createCriteria().andMsgIdEqualTo(chatMessageReceiver.getMsgId());
        int affected = chatMessageReceiverMapper.updateByExampleSelective(chatMessageReceiver, example);
        return affected > 0;
    }


    public List<ChatMessageSend> getFileOffLineMsg(String uid) {
        if (uid == null) {
            return Collections.EMPTY_LIST;
        }
        ChatMessageReceiverExample example = new ChatMessageReceiverExample();
        example.createCriteria().andToUidEqualTo(uid).andStatusEqualTo(ChatStatus.MessageStatus.UnSend.status());
        List<ChatMessageReceiver> messageReceivers = chatMessageReceiverMapper.selectByExample(example);
        List<String> msgIds = messageReceivers.stream().
                map(ChatMessageReceiver::getMsgId).collect(Collectors.toList());
        ChatMessageSendExample sendExample = new ChatMessageSendExample();
        sendExample.setOrderByClause("msg_seq");
        if (msgIds.size() > 0) {
            sendExample.createCriteria().andMsgIdIn(msgIds).
                    andContentTypeEqualTo(ChatStatus.MessageContentType.File.status());
            List<ChatMessageSend> messageSends = chatMessageSendMapper.selectByExampleWithBLOBs(sendExample);
            return messageSends;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public void cacheMessage(ChatMessageSend messageSend) {
        if (messageSend != null && messageSend.getContent() != null
                && messageSend.getContent().length() > 10000) {
            File cache = new File(fileConfig.getCacheMsgPath(), messageSend.getMsgId());
            try {
                FileUtils.writeStringToFile(cache, messageSend.getContent(), "UTF-8");
                messageSend.setContent(messageSend.getMsgId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String readCacheMsg(String msgId) {
        File cache = new File(fileConfig.getCacheMsgPath(), msgId);
        if (cache.exists()) {
            try {
                return FileUtils.readFileToString(cache, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 获取本人所有的发送给他人他人已读了的消息
     *
     * @param uid
     * @return
     */
    @Override
    public List<ChatMessageSend> getReadUnInformedMsg(String uid) {
        if (uid == null) {
            return Collections.EMPTY_LIST;
        }
        ChatMessageReceiverExample example = new ChatMessageReceiverExample();
        example.createCriteria().andFromUidEqualTo(uid).
                andStatusEqualTo(ChatStatus.MessageStatus.Read_not_inform.status());
        List<ChatMessageReceiver> messageReceivers = chatMessageReceiverMapper.selectByExample(example);
        List<String> msgIds = messageReceivers.stream().
                map(ChatMessageReceiver::getMsgId).collect(Collectors.toList());
        ChatMessageSendExample sendExample = new ChatMessageSendExample();
        sendExample.setOrderByClause("msg_seq");
        if (msgIds.size() > 0) {
            sendExample.createCriteria().andMsgIdIn(msgIds);
            List<ChatMessageSend> messageSends = chatMessageSendMapper.selectByExampleWithBLOBs(sendExample);
            return messageSends;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public List<MsgReadInfo> getReadUserList(String groupId, String senderId, boolean isFile) {
        List<MsgReadInfo> readInfoList = new ArrayList<>();
        List<ChatMessageSend> msgSendList = chatMessageSendMapper.queryMsgSendList(groupId, senderId, isFile,
                0, 10);
        if (isFile) {
            //针对于文件消息
            for (ChatMessageSend fileSend : msgSendList) {
                MsgReadInfo fileReadInfo = new MsgReadInfo();
                fileReadInfo.setMsgTime(fileSend.getMsgTime());
                fileReadInfo.setMsgId(fileSend.getMsgId());
                FileItem fileItem = imFileService.getFileItem(fileSend.getFileId());
                fileReadInfo.setContent(fileItem.getFileName());
                ChatMessageReceiverExample receiverFilter = new ChatMessageReceiverExample();
                receiverFilter.createCriteria ().andMsgIdEqualTo(fileSend.getMsgId()).andFromUidEqualTo(senderId).
                        andStatusEqualTo(ChatStatus.MessageStatus.Downloaded.status());
                List<ChatMessageReceiver> receiverList = chatMessageReceiverMapper.selectByExample(receiverFilter);
                List<String> readUserIdList = receiverList.stream().
                        map(ChatMessageReceiver::getToUid).collect(Collectors.toList());
                fileReadInfo.setReaderList(readUserIdList);
                readInfoList.add(fileReadInfo);
            }
        } else {
            //针对于普通消息
            for (ChatMessageSend msgSend : msgSendList) {
                MsgReadInfo msgReadInfo = new MsgReadInfo();
                msgReadInfo.setMsgId(msgSend.getMsgId());
                msgReadInfo.setMsgTime(msgSend.getMsgTime());
                msgReadInfo.setContent(msgSend.getPlainText());
                ChatMessageReceiverExample receiverFilter = new ChatMessageReceiverExample();
                receiverFilter.createCriteria ().andMsgIdEqualTo(msgSend.getMsgId()).andFromUidEqualTo(senderId).
                        andStatusEqualTo(ChatStatus.MessageStatus.Read_informed.status());
                List<ChatMessageReceiver> receiverList = chatMessageReceiverMapper.selectByExample(receiverFilter);
                List<String> readUserIdList = receiverList.stream().
                        map(ChatMessageReceiver::getToUid).collect(Collectors.toList());
                msgReadInfo.setReaderList(readUserIdList);
                readInfoList.add(msgReadInfo);
            }
        }
        return readInfoList;
    }

}
