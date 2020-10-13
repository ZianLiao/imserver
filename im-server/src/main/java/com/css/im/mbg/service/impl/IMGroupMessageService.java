package com.css.im.mbg.service.impl;

import com.css.im.cache.service.IMSessionService;
import com.css.im.chat.ChatStatus;
import com.css.im.chat.model.ChatGroup;
import com.css.im.chat.model.ChatMessageSend;
import com.css.im.chat.service.IMChatGroupService;
import com.css.im.chat.service.IMChatService;
import com.css.im.chat.service.IMMessageEventHandler;
import com.css.im.chat.service.IMMsgService;
import com.css.im.mbg.IMMessageType;
import com.css.im.mbg.model.MessagePackage;
import com.css.im.mbg.service.AbstractIMMessageService;
import com.css.im.mbg.util.Command;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by Charles Zhang
 *
 * @date 9/1/2020
 */
@Service
public class IMGroupMessageService extends AbstractIMMessageService {
    private static final Gson gson = new Gson();
    @Autowired
    IMChatService imChatService;
    @Autowired
    IMMsgService imMsgService;
    @Autowired
    IMChatGroupService chatGroupService;
    @Autowired
    IMSessionService iMSessionService;

    @Override
    public IMMessageType getIMMessageType() {
        return IMMessageType.mtGroupMsg;
    }

    @Override
    public void handlerMessage(String uid, JSONObject messageObj, Session session) {
        logger.info("处理群组聊天消息：" + messageObj.toString());
        try {
            //get message package by gson
            MessagePackage message = gson.fromJson(messageObj.toString(), MessagePackage.class);
            String groupId = (String) message.getParameter("groupUuid");
            String content = (String) message.getParameter("msg");
            String plainText = message.getParameter("msgToApp").toString();
            ChatGroup chatGroup = chatGroupService.getChatGroupById(groupId);
            String membersUids = chatGroup.getMembersUids();
            List<String> memberList = new ArrayList<>(Arrays.asList(membersUids.split(",")));
//            //exclude the sender from the receivers
            boolean flag = memberList.remove(message.getSendUser());
            if (!flag) {
                logger.error("群消息移除失败");
                return;
            }
            message.setCode(Command.Common.SUCCESS);
            //send message to all group members except the sender
            ChatMessageSend messageSend = imChatService.chatGroup(uid, groupId, content, plainText,
                    ChatStatus.MessageContentType.Text, null);
            if (messageSend != null) {
                imChatService.inform(JSONObject.fromObject(gson.toJson(message)), memberList, new IMMessageEventHandler() {
                    @Override
                    public void onSuccess(List<String> uids) {
                        imMsgService.updateMessageReceiverStatus(messageSend.getMsgId(), messageSend.getFromUid(), uids,
                                ChatStatus.MessageStatus.Send);
                    }

                    @Override
                    public void onFail() {

                    }
                });
            }
            MessagePackage msgReceipt = new MessagePackage();
            msgReceipt.setMessageType(IMMessageType.mtGroupMsgReceipt);
            msgReceipt.setCode(Command.Common.SUCCESS);
            msgReceipt.setReceiveUser((String) message.getParameter("groupUuid"));
            msgReceipt.setDescription((String) message.getParameter("msg"));
            JSONArray messageArr = new JSONArray();
            messageArr.add(JSONObject.fromObject(gson.toJson(msgReceipt)));
            sessionWrite(session, messageArr.toString());
        } catch (JsonSyntaxException e) {
            logger.error("群消息发送失败： " + e.getMessage());
        }
    }
}
