package com.css.im.websocket.service.impl;

import com.css.im.cache.service.IMSessionService;
import com.css.im.chat.service.IMMsgService;
import com.css.im.mbg.service.WebSocketMessageService;
import com.css.im.websocket.service.AbstractWebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

/**
 * 提供新版本服务
 * Create by wx on 2020-09-15
 */
@Service
public class WebSocketServiceV2Impl extends AbstractWebSocketService {

    @Autowired
    WebSocketMessageService webSocketMessageService;

    @Autowired
    IMSessionService iMSessionService;

    @Autowired
    IMMsgService imMsgService;

    @Override
    public WebSocketVersion getWsVersion() {
        return WebSocketVersion.v2;
    }

    @Override
    public void onOpen(Session session, String wsVersion, String uid) {
        //广播用户上线消息
        imMsgService.informOnline(uid);
    }

    @Override
    public void onClose(Session session, String wsVersion, String uid) {
        //广播用户下线消息
        imMsgService.informOffline(uid);
    }

    @Override
    public void onMessage(String uid, String message, Session session) {
        webSocketMessageService.messageHandle(uid, message, session);
    }

    @Override
    public void onError(Session session, Throwable error) {

    }
}
