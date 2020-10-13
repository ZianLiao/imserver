package com.css.im.org.model;

import java.io.Serializable;

public class OrgUserDept implements Serializable {
    private String userDpetId;

    private String userId;

    private String deptId;

    private String createTime;

    private static final long serialVersionUID = 1L;

    public String getUserDpetId() {
        return userDpetId;
    }

    public void setUserDpetId(String userDpetId) {
        this.userDpetId = userDpetId == null ? null : userDpetId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userDpetId=").append(userDpetId);
        sb.append(", userId=").append(userId);
        sb.append(", deptId=").append(deptId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}