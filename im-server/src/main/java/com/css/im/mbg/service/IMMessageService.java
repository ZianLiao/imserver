package com.css.im.mbg.service;

import com.css.im.mbg.IMMessageType;
import net.sf.json.JSONObject;

import javax.websocket.Session;

public interface IMMessageService {

    /**
     * 获取消息类型
     */
    IMMessageType getIMMessageType();

    /**
     * 处理具体类型的消息
     */
    void handlerMessage(String uid, JSONObject messageObj, Session session);

}
