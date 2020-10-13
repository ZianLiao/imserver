package com.css.im.cache.service;

import com.css.im.sys.Status;

import javax.websocket.Session;
import java.util.List;

/**
 * 提供长连接会话服务
 * Create by wx on 2020-08-26
 */
public interface IMSessionService {

    /***
     * 缓存websocket长连接会话,每个用户只能缓存一个session
     * @param session
     * @param uid
     * @param wsVersion
     * @return
     */
    boolean cacheUserSession(Session session, String uid, String wsVersion);
    boolean cacheUserSession(Session session, String uid, String wsVersion,Status.UserOnlineStatus status);

    /***
     * 获取会话连接
     * @param uid
     * @return
     */
    Session getUserSession(String uid);

    /****
     * 维护session,没有心跳的移除缓存
     */
    void manageUserSession();

    /***
     * 更细session，最后会话会话时间
     * @param uid
     * @return
     */
    boolean updateSession(String uid);

    /***
     * 删除会话缓存
     * @param uid
     * @return
     */
    boolean removeSession(String uid);

    /***
     * 通过session获取uid
     * @param session
     * @return
     */
    String getUserIdBySession(Session session);

    /***
     * 获取所有在缓存中的用户
     * @return
     */
    List<String> getAllOnlineUsers();

    /***
     * 缓存用户在线状态
     */
    void cacheUserOnlineStatus(String uid, Status.UserOnlineStatus status);

    /***
     * 获取用户在线状态
     */
    Status.UserOnlineStatus getUserOnlineStatus(String uid);

    /**
     * 清空所有缓存
     */
    void clearAllUserSession();

}
