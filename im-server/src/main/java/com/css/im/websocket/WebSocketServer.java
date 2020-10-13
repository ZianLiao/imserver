package com.css.im.websocket;

import com.css.im.IMApplicationContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * 提供聊天服务
 * Create by wx on 2020-08-24
 */
@ServerEndpoint(value = "/im/api/wserver/{wsVersion}/{uid}")
@Component
public class WebSocketServer {

    static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    WebSocketServiceDispatcher dispatcher = (WebSocketServiceDispatcher) IMApplicationContextAware.getApplicationContext().getBean("webSocketServiceDispatcher");


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("wsVersion") String wsVersion,
                       @PathParam("uid") String uid) {
        dispatcher.onOpen(session, wsVersion, uid);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("wsVersion") String wsVersion,
                        @PathParam("uid") String uid) {
        dispatcher.onClose(session, wsVersion, uid);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("wsVersion") String wsVersion,
                          @PathParam("uid") String uid) {
        dispatcher.onMessage(message, session, wsVersion, uid);
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error, @PathParam("wsVersion") String wsVersion,
                        @PathParam("uid") String uid) {
        dispatcher.onError(session, error, wsVersion, uid);
    }
}
