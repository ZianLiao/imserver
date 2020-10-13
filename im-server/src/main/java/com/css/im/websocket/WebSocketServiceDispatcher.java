package com.css.im.websocket;

import com.css.im.cache.service.IMSessionService;
import com.css.im.sys.Status;
import com.css.im.websocket.service.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.List;

/**
 * Create by wx on 2020-09-15
 */
@Component("webSocketServiceDispatcher")
public class WebSocketServiceDispatcher {

    Logger logger = LoggerFactory.getLogger(WebSocketServiceDispatcher.class);

    @Autowired
    IMSessionService iMSessionService;

    @Autowired
    List<WebSocketService> webSocketServices;

    /**
     * 连接建立成功调用的方法
     */
    public void onOpen(Session session, String wsVersion, String uid) {
        for (WebSocketService webSocketService : webSocketServices) {
            if (webSocketService.getWsVersion().toString().equals(wsVersion)) {
                //test if the user login repeatedly
                webSocketService.prevOpen(session, wsVersion, uid);
                logger.info(String.format("用户%s建立连接", uid));
                //缓存用户连接
                iMSessionService.cacheUserSession(session, uid, wsVersion,Status.UserOnlineStatus.ONLINE);
                webSocketService.onOpen(session, wsVersion, uid);
                webSocketService.afterOpen(session, wsVersion, uid);
            }
        }
    }

    /**
     * 连接关闭调用的方法
     */
    public void onClose(Session session, String wsVersion, String uid) {

        for (WebSocketService webSocketService : webSocketServices) {
            if (webSocketService.getWsVersion().toString().equals(wsVersion)) {
                webSocketService.prevClose(session, wsVersion, uid);
                logger.info(String.format("用户%s关闭连接", uid));
                //删除缓存
                iMSessionService.removeSession(uid);
                webSocketService.onClose(session, wsVersion, uid);
                webSocketService.afterClose(session, wsVersion, uid);
            }
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    public void onMessage(String message, Session session, String wsVersion, String uid) {

        for (WebSocketService webSocketService : webSocketServices) {
            if (webSocketService.getWsVersion().toString().equals(wsVersion)) {
                webSocketService.prevMessage(uid, message, session);
                //logger.info(String.format("接收到消息%s,uid:%s,wsVersion:%s",message,uid,wsVersion));
                iMSessionService.updateSession(uid);
                webSocketService.onMessage(uid, message, session);
                webSocketService.prevMessage(uid, message, session);
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    public void onError(Session session, Throwable error, String wsVersion, String uid) {
        logger.error(String.format("会话出现异常%s", error.getMessage()));
        for (WebSocketService webSocketService : webSocketServices) {
            if (webSocketService.getWsVersion().toString().equals(wsVersion)) {
                webSocketService.onError(session, error);
            }
        }
    }


}
