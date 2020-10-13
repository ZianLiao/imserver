package com.css.im.mbg.service;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.List;

/**
 * 处理消息
 * Create by wx on 2020-08-28
 */
@Component
public class WebSocketMessageService {

    @Autowired
    private List<IMMessageService> imMessageServices;

    /***
     * 处理接收到的消息
     * @param message
     * @param session
     */
    @Async("taskExecutor")
    public void messageHandle(String uid, String message, Session session) {

        JSONObject messageObj = JSONObject.fromObject(message);

        String type = messageObj.getString("MessageType");
        for (IMMessageService service : imMessageServices) {
            if (service.getIMMessageType().toString().equals(type)) {
                synchronized (session) {
                    service.handlerMessage(uid, messageObj, session);
                }
                return;
            }
        }
    }

}
