package com.css.im.cache.entity;

import java.io.Serializable;

/**
 * redis 缓存的会话实体
 * Create by wx on 2020-08-27
 */
public class IMSessionCacheEntity implements Serializable {

    private String uid;
    private String lastActiveTime;
    private String version;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(String lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
