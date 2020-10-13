package com.css.im.mbg.service.impl;

import com.css.im.chat.ChatStatus;
import com.css.im.chat.model.ChatMessageReceiver;
import com.css.im.chat.model.ChatMessageSend;
import com.css.im.chat.service.IMChatService;
import com.css.im.chat.service.IMMessageEventHandler;
import com.css.im.chat.service.IMMsgService;
import com.css.im.mbg.IMMessageType;
import com.css.im.mbg.model.MessagePackage;
import com.css.im.mbg.service.AbstractIMMessageService;
import com.css.im.mbg.util.Command;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.Arrays;
import java.util.List;

/**
 * created by Charles Zhang
 *
 * @date 9/1/2020
 */
@Service
public class IMChatMessageService extends AbstractIMMessageService {
    @Autowired
    IMChatService imChatService;
    @Autowired
    IMMsgService imMsgService;

    @Override
    public IMMessageType getIMMessageType() {
        return IMMessageType.mtMessage;
    }

    @Override
    public void handlerMessage(String uid, JSONObject messageObj, Session session) {
        logger.info("处理单人聊天信息：" + messageObj.toString());
        //get message package by gson
        MessagePackage message = gson.fromJson(messageObj.toString(), MessagePackage.class);
        logger.info("解析客户端发来数据：" + messageObj.toString());
        String receiveUser = message.getReceiveUser();
        //发送的文本内容，是一段html
        String description = message.getDescription();
        String msgName = message.getParameter("msgName").toString();
        String plainText = message.getParameter("msgToApp").toString();

        //向对方发消息
        if (StringUtils.isNotBlank(receiveUser)) {
            if ("toBurn".equals(msgName)) {
                //销毁发消息，在线直接发，不在添加记录
                String msgId = message.getParameter("msgId").toString();
                //1.张三给李四发一条阅后焚毁消息burn后，2.李四收到并发送焚毁消息toBurn，
                // 因这两条消息实为同一个消息体，此时recevieUser是张三，sendUser是李四
                String fromUid = message.getReceiveUser();
                String toUid = message.getSendUser();
                imChatService.response(JSONObject.fromObject(gson.toJson(message)), receiveUser, new IMMessageEventHandler() {
                    @Override
                    public void onSuccess(List<String> uids) {
                        logger.info("销毁发消息:发给客户端数据" + JSONObject.fromObject(gson.toJson(message)).toString());
                        //成功失败都更新发送状态（离线）
                        List<String> toUids = Arrays.asList(toUid);
                        imMsgService.updateMessageReceiverStatus(msgId, fromUid, toUids,
                                ChatStatus.MessageStatus.Send);
                    }

                    @Override
                    public void onFail() {
                        //对方未在线发送失败，加一条销毁消息记录发给离线人员,把content_type的：burn状态改为toBurn
                        logger.info("销毁发消息：更改离线数据状态为");
                        imMsgService.updateMessageSendContentType(msgId,
                                ChatStatus.MessageContentType.To_Burn);
                        ChatMessageReceiver receiver = new ChatMessageReceiver();
                        //因处理同一条消息体。为让发消息者收到此离线消息，需交换发送人接收人id，
                        receiver.setMsgId(msgId);
                        receiver.setFromUid(toUid);
                        receiver.setToUid(fromUid);
                        imMsgService.updateMessageReceiverByMsgId(receiver);
                    }
                });

            } else {
                ChatMessageSend messageSend = new ChatMessageSend();
                if ("burn".equals(msgName)) {
                    //阅后即焚消息
                    messageSend = imChatService.chat(uid, receiveUser, description, plainText,
                            ChatStatus.MessageContentType.Read_Burn, null);
                } else {
                    //普通消息
                    messageSend = imChatService.chat(uid, receiveUser, description, plainText,
                            ChatStatus.MessageContentType.Text, null);
                }
                if (messageSend != null) {
                    String msgId = messageSend.getMsgId();
                    String fromUid = messageSend.getFromUid();
                    message.setParameter("msgId", msgId);
                    imChatService.response(JSONObject.fromObject(gson.toJson(message)),
                            receiveUser, new IMMessageEventHandler() {
                        @Override
                        public void onSuccess(List<String> uids) {
                            imMsgService.updateMessageReceiverStatus(msgId, fromUid, uids,
                                    ChatStatus.MessageStatus.Send);
                        }

                        @Override
                        public void onFail() {

                        }
                    });
                }
                //给自己回执
                //焚毁消息，无需向发送者客户端发送回执。
                MessagePackage msgReceipt = new MessagePackage();
                msgReceipt.setMessageType(IMMessageType.mtMessageReceipt);
                msgReceipt.setCode(Command.Common.SUCCESS);
                msgReceipt.setReceiveUser(receiveUser);
                msgReceipt.setDescription(description);
                msgReceipt.setParameter("msgName", msgName);
                msgReceipt.setParameter("msgId", messageSend.getMsgId());
//                msgReceipt.setParameter("msgToApp", plainText);
                if (message.getParameter("msgType") != null) {
                    msgReceipt.setParameter("msgType", message.getParameter("msgType"));
                }
                JSONArray messageArr = new JSONArray();
                messageArr.add(JSONObject.fromObject(gson.toJson(msgReceipt)));
                sessionWrite(session, messageArr.toString());
                logger.info("发给自己客户端的数据" + messageArr.toString());
            }

        } else {
            logger.error("接收者为空！");
        }

    }
}