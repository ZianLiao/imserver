package com.css.im.websocket.service;

import javax.websocket.Session;

public interface WebSocketService {
    enum WebSocketVersion {
        v1, v2
    }

    WebSocketVersion getWsVersion();

    void prevOpen(Session session, String wsVersion, String uid);

    void onOpen(Session session, String wsVersion, String uid);

    void afterOpen(Session session, String wsVersion, String uid);

    void prevClose(Session session, String wsVersion, String uid);

    void onClose(Session session, String wsVersion, String uid);

    void afterClose(Session session, String wsVersion, String uid);

    void prevMessage(String uid, String message, Session session);

    void onMessage(String uid, String message, Session session);

    void afterMessage(String uid, String message, Session session);

    void onError(Session session, Throwable error);

}
