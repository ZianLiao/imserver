package com.css.im.sys.model;

import java.io.Serializable;

public class SysGroupPermission implements Serializable {
    private String groupPermissionId;

    private String groupId;

    private String permissionId;

    private static final long serialVersionUID = 1L;

    public String getGroupPermissionId() {
        return groupPermissionId;
    }

    public void setGroupPermissionId(String groupPermissionId) {
        this.groupPermissionId = groupPermissionId == null ? null : groupPermissionId.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId == null ? null : permissionId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", groupPermissionId=").append(groupPermissionId);
        sb.append(", groupId=").append(groupId);
        sb.append(", permissionId=").append(permissionId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}