package com.css.im.sys.service;

import com.css.im.sys.model.SysMenu;
import com.css.im.sys.model.SysUser;

import java.util.List;

/**
 * Create by wx on 2020-09-07
 */
public interface SysMenuService {
    /***
     * 获取用户具有的所有菜单
     * @param sysUser
     * @return
     */
    List<SysMenu> loadAllUserMenus(SysUser sysUser);

}
