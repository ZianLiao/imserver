package com.css.im.websocket.service;

public interface IMWSServerService {

    /**
     * 获取websocket服务地址
     */
    String getWebSocketServerUrl(String uid, String appVersion);

}
