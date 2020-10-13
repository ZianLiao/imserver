package com.css.im.mbg.model;

import java.util.List;


public class OANotify {
    private String content;//content of the notification

    private String url;//the link below the notification, go to this website when clicked

    private List<String> targetUser;//receiver in IM, either UUID or username

    private String fromAppName;

    private String sendUser;

    /**
     * useless
     */
    private String srcIp;
    private String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(List<String> targetUser) {
        this.targetUser = targetUser;
    }

    public String getFromAppName() {
        return fromAppName;
    }

    public void setFromAppName(String fromAppName) {
        this.fromAppName = fromAppName;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
