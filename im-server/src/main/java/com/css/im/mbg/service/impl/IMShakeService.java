package com.css.im.mbg.service.impl;

import com.css.im.chat.service.IMChatService;
import com.css.im.mbg.IMMessageType;
import com.css.im.mbg.model.MessagePackage;
import com.css.im.mbg.service.AbstractIMMessageService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

/**
 * created by Charles Zhang
 *
 * @date 9/4/2020
 */
@Service
public class IMShakeService extends AbstractIMMessageService {
    @Autowired
    IMChatService imChatService;

    @Override
    public IMMessageType getIMMessageType() {
        return IMMessageType.mtShake;
    }

    @Override
    public void handlerMessage(String uid, JSONObject messageObj, Session session) {
        try {
            MessagePackage message = gson.fromJson(messageObj.toString(), MessagePackage.class);
            imChatService.inform(JSONObject.fromObject(gson.toJson(message)), message.getReceiveUser());
        } catch (Exception e) {
            logger.error("抖动窗口消息发送失败" + e.getMessage());
        }
    }
}
