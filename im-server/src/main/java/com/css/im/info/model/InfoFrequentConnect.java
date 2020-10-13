package com.css.im.info.model;

import java.io.Serializable;

public class InfoFrequentConnect implements Serializable {
    private String infoId;

    /**
     * 用户id
     *
     * @mbg.generated
     */
    private String userId;

    /**
     * 添加时间
     *
     * @mbg.generated
     */
    private String createTime;

    /**
     * 常用联系人组名称
     *
     * @mbg.generated
     */
    private String groupName;

    /**
     * 联系人ids
     *
     * @mbg.generated
     */
    private String connectUids;

    private static final long serialVersionUID = 1L;

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId == null ? null : infoId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getConnectUids() {
        return connectUids;
    }

    public void setConnectUids(String connectUids) {
        this.connectUids = connectUids == null ? null : connectUids.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", infoId=").append(infoId);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", groupName=").append(groupName);
        sb.append(", connectUids=").append(connectUids);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}