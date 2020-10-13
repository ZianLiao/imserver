package com.css.common;


import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;

public abstract class BaseService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected static Gson gson = new Gson();

    public void sessionWrite(Session session, String message) {
        synchronized (session) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                logger.info(String.format("发送消息%s失败", message));
            }
        }
    }
}
