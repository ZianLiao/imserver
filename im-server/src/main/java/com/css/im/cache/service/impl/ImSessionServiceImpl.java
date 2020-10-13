package com.css.im.cache.service.impl;

import com.css.common.BaseService;
import com.css.im.cache.constant.SessionMap;
import com.css.im.cache.entity.IMSessionCacheEntity;
import com.css.im.cache.keys.RedisKeysConstant;
import com.css.im.cache.service.IMSessionService;
import com.css.im.sys.Status;
import com.css.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Create by wx on 2020-08-26
 */
@Service("iMSessionService")
public class ImSessionServiceImpl extends BaseService implements IMSessionService {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SessionMap sessionMap;

    @Resource
    HashOperations<String, String, Object> hashOperations;

    @Override
    public synchronized boolean cacheUserSession(Session session, String uid, String wsVersion) {
        try {
            IMSessionCacheEntity entity = new IMSessionCacheEntity();
            entity.setLastActiveTime(DateTimeUtils.getTodayDateTime());
            entity.setUid(uid);
            entity.setVersion(wsVersion);
            sessionMap.putSession(uid, session);
            hashOperations.put(RedisKeysConstant.getUidKey(uid), RedisKeysConstant.SessionKey.KEY_SESSION, entity);
            return true;
        } catch (Exception e) {
            logger.error(String.format("缓存用户%s会话连接失败!连接版本为：%s", uid, wsVersion), e.getMessage());
        }
        return false;
    }

    @Override
    public synchronized boolean cacheUserSession(Session session, String uid, String wsVersion, Status.UserOnlineStatus status) {
        cacheUserSession(session,uid,wsVersion);
        cacheUserOnlineStatus(uid,status);
        return false;
    }

    @Override
    public Session getUserSession(String uid) {
        return sessionMap.getSession(uid);
    }

    @Override
    public void manageUserSession() {
        Set<String> keys = hashOperations.getOperations().keys(RedisKeysConstant.getUidKey("*"));

        for (String key : keys) {
            String uid = key.substring(key.indexOf(":") + 1);
            //检测是否超时
            IMSessionCacheEntity entity = (IMSessionCacheEntity) hashOperations.get(RedisKeysConstant.getUidKey(uid), RedisKeysConstant.SessionKey.KEY_SESSION);
            //命令超时
            if (DateTimeUtils.getCurrentTimeInMillis() -
                    DateTimeUtils.getTimeInMillis(entity.getLastActiveTime(), "yyyy-MM-dd HH:mm:ss") > RedisKeysConstant.SESSION_TIMEOUT) {

                hashOperations.getOperations().delete(key);
                sessionMap.removeSession(uid);
            }

        }
    }

    @Override
    public boolean updateSession(String uid) {
        try {
            IMSessionCacheEntity entity = (IMSessionCacheEntity) hashOperations.get(RedisKeysConstant.getUidKey(uid), RedisKeysConstant.SessionKey.KEY_SESSION);
            if (null != entity) {
                entity.setLastActiveTime(DateTimeUtils.getTodayDateTime());
                hashOperations.put(RedisKeysConstant.getUidKey(uid), RedisKeysConstant.SessionKey.KEY_SESSION, entity);
            }
            return true;
        } catch (Exception e) {
            logger.error(String.format("更新用户%s的会话连接失败!", uid), e.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeSession(String uid) {

        try {
            hashOperations.getOperations().delete(RedisKeysConstant.getUidKey(uid));
            sessionMap.removeSession(uid);
            return true;
        } catch (Exception e) {
            logger.error(String.format("删除用户%s的会话连接失败!", uid), e.getMessage());
        }
        return false;
    }

    @Override
    public String getUserIdBySession(Session session) {
        return sessionMap.getUidBySession(session);
    }

    @Override
    public List<String> getAllOnlineUsers() {
        Set<String> keys = hashOperations.getOperations().keys(RedisKeysConstant.getUidKey("*"));
        return keys.stream().map(key -> key.substring(key.indexOf(":") + 1)).collect(Collectors.toList());
    }

    @Override
    public void cacheUserOnlineStatus(String uid, Status.UserOnlineStatus status) {
        hashOperations.put(RedisKeysConstant.getUidKey(uid),
                RedisKeysConstant.SessionKey.KEY_ONLINE_STATUS, status.status());
    }

    @Override
    public Status.UserOnlineStatus getUserOnlineStatus(String uid) {
        Short status = (Short) (hashOperations.get(RedisKeysConstant.getUidKey(uid),
                RedisKeysConstant.SessionKey.KEY_ONLINE_STATUS));
        if (status == null) {
            status = Status.UserOnlineStatus.ONLINE.status();
        }
        return Status.UserOnlineStatus.statusOf(status);
    }

    @Override
    public void clearAllUserSession() {
        hashOperations.getOperations().delete(RedisKeysConstant.getUidKey("*"));
        sessionMap.empty();
    }

}
