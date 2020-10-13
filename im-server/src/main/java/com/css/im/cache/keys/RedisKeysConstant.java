package com.css.im.cache.keys;

import java.io.Serializable;

/**
 * 定义redis缓存里的key值
 * Create by wx on 2020-08-27
 */
public abstract class RedisKeysConstant implements Serializable {

    /**
     * 会话连接超时时间120s
     */
    public static final long SESSION_TIMEOUT = 120000;

    public static String getUidKey(String uid) {
        return "UID:" + uid;
    }

    public static class SessionKey {

        public static final String KEY_SESSION = "SESSION";
        public static final String KEY_LAST_ACTiVE_TIME = "LAST_ACTiVE_TIME";
        public static final String KEY_ONLINE_STATUS = "ONLINE_STATUS";

    }

}
