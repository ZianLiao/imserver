package com.css.im.websocket.model;

import java.io.Serializable;

public class WsServer implements Serializable {
    private String serverId;

    /**
     * 配置键
     *
     * @mbg.generated
     */
    private String serverKey;

    /**
     * 适用的app版本号
     *
     * @mbg.generated
     */
    private String appVersions;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private String createTime;

    /**
     * ws服务版本号
     *
     * @mbg.generated
     */
    private String wsVersion;

    /**
     * 服务地址
     *
     * @mbg.generated
     */
    private String wsUrl;

    /**
     * 备注
     *
     * @mbg.generated
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId == null ? null : serverId.trim();
    }

    public String getServerKey() {
        return serverKey;
    }

    public void setServerKey(String serverKey) {
        this.serverKey = serverKey == null ? null : serverKey.trim();
    }

    public String getAppVersions() {
        return appVersions;
    }

    public void setAppVersions(String appVersions) {
        this.appVersions = appVersions == null ? null : appVersions.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getWsVersion() {
        return wsVersion;
    }

    public void setWsVersion(String wsVersion) {
        this.wsVersion = wsVersion == null ? null : wsVersion.trim();
    }

    public String getWsUrl() {
        return wsUrl;
    }

    public void setWsUrl(String wsUrl) {
        this.wsUrl = wsUrl == null ? null : wsUrl.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serverId=").append(serverId);
        sb.append(", serverKey=").append(serverKey);
        sb.append(", appVersions=").append(appVersions);
        sb.append(", createTime=").append(createTime);
        sb.append(", wsVersion=").append(wsVersion);
        sb.append(", wsUrl=").append(wsUrl);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}