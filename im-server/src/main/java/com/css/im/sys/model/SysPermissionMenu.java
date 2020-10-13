package com.css.im.sys.model;

import java.io.Serializable;

public class SysPermissionMenu implements Serializable {
    private String permissionMenuId;

    private String permissionId;

    private String menuId;

    private static final long serialVersionUID = 1L;

    public String getPermissionMenuId() {
        return permissionMenuId;
    }

    public void setPermissionMenuId(String permissionMenuId) {
        this.permissionMenuId = permissionMenuId == null ? null : permissionMenuId.trim();
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId == null ? null : permissionId.trim();
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
        sb.append(", permissionMenuId=").append(permissionMenuId);
        sb.append(", permissionId=").append(permissionId);
        sb.append(", menuId=").append(menuId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}