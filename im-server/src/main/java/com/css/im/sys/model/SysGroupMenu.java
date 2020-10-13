package com.css.im.sys.model;

import java.io.Serializable;

public class SysGroupMenu implements Serializable {
    private String groupMenuId;

    private String groupId;

    private String menuId;

    private static final long serialVersionUID = 1L;

    public String getGroupMenuId() {
        return groupMenuId;
    }

    public void setGroupMenuId(String groupMenuId) {
        this.groupMenuId = groupMenuId == null ? null : groupMenuId.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", groupMenuId=").append(groupMenuId);
        sb.append(", groupId=").append(groupId);
        sb.append(", menuId=").append(menuId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}