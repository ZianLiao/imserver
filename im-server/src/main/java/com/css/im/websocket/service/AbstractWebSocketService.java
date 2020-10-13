package com.css.im.websocket.service;

import com.css.common.BaseService;

import javax.websocket.Session;

/**
 * Create by wx on 2020-09-16
 */
public abstract class AbstractWebSocketService extends BaseService implements WebSocketService {
    @Override
    public void prevOpen(Session session, String wsVersion, String uid) {
    }

    @Override
    public void afterOpen(Session session, String wsVersion, String uid) {
    }

    @Override
    public void prevClose(Session session, String wsVersion, String uid) {
    }

    @Override
    public void afterClose(Session session, String wsVersion, String uid) {
    }

    @Override
    public void prevMessage(String uid, String message, Session session) {
    }

    @Override
    public void afterMessage(String uid, String message, Session session) {
    }
}
