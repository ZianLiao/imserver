package com.css.im.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Create by wx on 2020-09-07
 */

@Component
@ConfigurationProperties(prefix = "file")
public class FileConfig {

    /***
     * 文件存储目录
     */
    private String storage;
    private String url;
    private Boolean delete;
    private String basicHeadPath;
    private String cacheMsgPath;


    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public String getBasicHeadPath() {
        File basicHead = new File(basicHeadPath);
        if (!basicHead.exists()) {
            basicHead.mkdirs();
        }
        return basicHeadPath;
    }

    public void setBasicHeadPath(String basicHeadPath) {
        this.basicHeadPath = basicHeadPath;
    }

    public String getCacheMsgPath() {
        return cacheMsgPath;
    }

    public void setCacheMsgPath(String cacheMsgPath) {
        this.cacheMsgPath = cacheMsgPath;
    }
}
