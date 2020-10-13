package com.css.im.mbg.service.impl;

import com.css.im.chat.ChatStatus;
import com.css.im.chat.model.ChatMessageSend;
import com.css.im.chat.service.IMChatService;
import com.css.im.chat.service.IMMessageEventHandler;
import com.css.im.chat.service.IMMsgService;
import com.css.im.mbg.IMMessageType;
import com.css.im.mbg.model.MessagePackage;
import com.css.im.mbg.service.AbstractIMMessageService;
import com.css.im.mbg.util.Command;
import com.css.im.mbg.util.OperateEnum;
import com.css.utils.DateTimeUtils;
import com.css.utils.StringUtils;
import com.google.gson.Gson;
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
 * @date 9/4/2020
 */
@Service
public class IMBroadCastMsgService extends AbstractIMMessageService {
    private static final Gson gson = new Gson();

    @Autowired
    IMMsgService imMsgService;
    @Autowired
    IMChatService imChatService;
    @Autowired
    IMBroadCastMsgService broadCastMsgService;


    @Override
    public IMMessageType getIMMessageType() {
        return IMMessageType.mtBroadcastingMsg;
    }

    @Override
    public void handlerMessage(String uid, JSONObject messageObj, Session session) {
        logger.info("处理广播消息：" + messageObj.toString());
        try {
            MessagePackage message = gson.fromJson(messageObj.toString(), MessagePackage.class);
            message.setParameter("createTime", DateTimeUtils.getTodayDateTime());
            message.setOperate(OperateEnum.BroadcastingStyle.RECEIVER.toString());
            message.setCode(Command.Common.SUCCESS);
            message.setDescription("操作成功！");
            logger.info("get the type: " + message.getParameter("type"));
            String receiverLongStr = (String) message.getParameter("receiver");
            List<String> toUserIds = new ArrayList<String>();
            if (StringUtils.isNotEmpty(receiverLongStr)) {
                toUserIds = Arrays.asList(receiverLongStr.split(","));
            }

            ChatMessageSend messageSend = imChatService.sysBroadcast(uid, toUserIds, (String) message.getParameter("msg"),
                    ChatStatus.MessageContentType.Text, null);
            if (null != messageSend) {
                imChatService.inform(JSONObject.fromObject(gson.toJson(message)), toUserIds,
                        new IMMessageEventHandler() {
                            @Override
                            public void onSuccess(List<String> uids) {
                                imMsgService.updateMessageReceiverStatus(messageSend.getMsgId(), messageSend.getFromUid(), uids,
                                        ChatStatus.MessageStatus.Send);
                            }

                            @Override
                            public void onFail() {

                            }
                        });
                MessagePackage receipt = new MessagePackage();
                receipt.setMessageType(IMMessageType.mtBroadcastingReceipt);
                receipt.setCode(Command.Common.SUCCESS);
                receipt.setOperate(OperateEnum.BroadcastingStyle.RECEIVER.toString());
                receipt.setDescription("操作成功！");
                //send the feedback to the sender
                JSONArray messageArr = new JSONArray();
                messageArr.add(JSONObject.fromObject(gson.toJson(receipt)));
                sessionWrite(session, messageArr.toString());
            }
        } catch (Exception e) {
            logger.error("广播消息发送失败： " + e.getMessage());
        }
    }
}
