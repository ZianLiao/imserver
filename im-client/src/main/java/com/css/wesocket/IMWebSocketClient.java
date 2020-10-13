package com.css.wesocket;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * Create by wx on 2020-09-02
 */
public class IMWebSocketClient extends WebSocketClient {
    private static Logger logger = LoggerFactory.getLogger(WSTest.class);

    MessageHandler handler;

    public IMWebSocketClient(URI serverUri,MessageHandler handler) {
        super(serverUri);
        this.handler = handler;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        logger.info("建立连接成功！");
    }

    @Override
    public void onMessage(String s) {
        System.out.println("服务端发来消息： " + s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        handler.handleMessage(s);
    }

    @Override
    public void onError(Exception e) {

    }
}
