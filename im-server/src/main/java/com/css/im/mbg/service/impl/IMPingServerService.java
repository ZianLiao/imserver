package com.css.im.mbg.service.impl;

import com.css.im.cache.service.IMSessionService;
import com.css.im.chat.service.IMChatService;
import com.css.im.mbg.IMMessageType;
import com.css.im.mbg.model.MessagePackage;
import com.css.im.mbg.service.AbstractIMMessageService;
import com.css.im.mbg.util.Command;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

/**
 * created by Charles Zhang
 *
 * @date 9/1/2020
 */
@Service
public class IMPingServerService extends AbstractIMMessageService {
    private static final Gson gson = new Gson();
    @Autowired
    IMChatService imChatService;
    @Autowired
    IMSessionService imSessionService;

    @Override
    public IMMessageType getIMMessageType() {
        return IMMessageType.mtPingServer;
    }

    @Override
    public void handlerMessage(String uid, JSONObject messageObj, Session session) {
        if (session.isOpen()) {
            logger.info(String.format("响应ping：%s", messageObj.toString()));
            MessagePackage message = gson.fromJson(messageObj.toString(), MessagePackage.class);
            MessagePackage receipt = new MessagePackage();
            receipt.setMessageType(IMMessageType.mtPingServer);
            receipt.setCode(Command.Common.SUCCESS);
            receipt.setReceiveUser("");
            receipt.setDescription(message.getDescription());
            receipt.setParameter("msgType", "0");
            logger.info("发送给服务端PingServer" + gson.toJson(receipt));
            JSONArray messageArr = new JSONArray();
            messageArr.add(JSONObject.fromObject(gson.toJson(receipt)));
            sessionWrite(session, messageArr.toString());
        } else {
            logger.error("无法连接到[{}]客户端", uid);
        }
    }
}
