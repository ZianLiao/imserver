package com.css.im.sys.model;

import java.io.Serializable;

public class SysUserMenu implements Serializable {
    private String userMenuId;

    private String userId;

    private String menuId;

    private static final long serialVersionUID = 1L;

    public String getUserMenuId() {
        return userMenuId;
    }

    public void setUserMenuId(String userMenuId) {
        this.userMenuId = userMenuId == null ? null : userMenuId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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
        sb.append(", userMenuId=").append(userMenuId);
        sb.append(", userId=").append(userId);
        sb.append(", menuId=").append(menuId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}