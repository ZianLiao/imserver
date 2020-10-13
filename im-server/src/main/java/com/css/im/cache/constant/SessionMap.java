package com.css.im.cache.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by wx on 2020-09-02
 */
@Component
public class SessionMap {

    Logger logger = LoggerFactory.getLogger(SessionMap.class);
    private ConcurrentHashMap<String, Session> webSessions = new ConcurrentHashMap<>();

    public synchronized void putSession(String uid, Session session) {
        webSessions.put(uid, session);
    }

    public synchronized void removeSession(String uid) {
        Session session = getSession(uid);
        if (null == session) {
            return;
        }
        try {
            session.close();
        } catch (Exception e) {
            logger.error(String.format("尝试关闭%s会话Session", uid));
        } finally {
            webSessions.remove(uid);
        }

    }

    public synchronized Session getSession(String uid) {
        return webSessions.get(uid);
    }

    public synchronized String getUidBySession(Session seeion) {
        for (String uid : webSessions.keySet()) {
            if (seeion.equals(webSessions.get(uid))) {
                return uid;
            }
        }
        return null;
    }

    public synchronized void empty() {
        webSessions.clear();
    }

}
