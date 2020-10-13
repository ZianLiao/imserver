package com.css.im.mbg.service.impl;

import com.css.im.chat.ChatStatus;
import com.css.im.chat.service.IMChatService;
import com.css.im.chat.service.IMMessageEventHandler;
import com.css.im.chat.service.IMMsgService;
import com.css.im.mbg.IMMessageType;
import com.css.im.mbg.model.MessagePackage;
import com.css.im.mbg.service.AbstractIMMessageService;
import com.css.utils.StringUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.List;

/**
 * created by Charles Zhang
 *
 * @date 2020/9/30
 */
@Service
public class IMMsgAlreadyReadService extends AbstractIMMessageService {
    @Autowired
    IMMsgService imMsgService;
    @Autowired
    IMChatService imChatService;

    @Override
    public IMMessageType getIMMessageType() {
        return IMMessageType.mtMsgReadAlready;
    }

    @Override
    public void handlerMessage(String uid, JSONObject messageObj, Session session) {
        logger.debug("处理消息已读：" + messageObj.toString());
        MessagePackage message = gson.fromJson(messageObj.toString(), MessagePackage.class);
        String msgId = (String) message.getParameter("msgId");
        //一对一消息特有
        String toUid = (String) message.getParameter("toUid");
        String fromUid = (String) message.getParameter("fromUid");
        String chatGroupId = (String) message.getParameter("chatGroupId");
        if (StringUtils.isNotEmpty(chatGroupId)) {
            //更新群内的消息体
            imMsgService.updateMsgStatusByGroupId(chatGroupId, ChatStatus.MessageStatus.Read_informed);
        } else {
            //一对一消息
            boolean flag = imMsgService.updateMessageReceiverStatus(msgId,
                    fromUid, toUid, ChatStatus.MessageStatus.Send, ChatStatus.MessageStatus.Read_not_inform);
            if (flag) {
                imChatService.response(messageObj, fromUid, new IMMessageEventHandler() {
                    @Override
                    public void onSuccess(List<String> uids) {
                        imMsgService.updateMessageReceiverStatus(msgId, fromUid, toUid,
                                ChatStatus.MessageStatus.Read_not_inform, ChatStatus.MessageStatus.Read_informed);
                    }

                    @Override
                    public void onFail() {

                    }
                });
            }
        }

    }

}
