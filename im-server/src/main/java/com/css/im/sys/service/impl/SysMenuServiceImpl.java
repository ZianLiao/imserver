package com.css.im.sys.service.impl;

import com.css.common.BaseService;
import com.css.im.sys.mapper.SysMenuMapper;
import com.css.im.sys.model.SysMenu;
import com.css.im.sys.model.SysUser;
import com.css.im.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by wx on 2020-09-07
 */
@Service
public class SysMenuServiceImpl extends BaseService implements SysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> loadAllUserMenus(SysUser sysUser) {

        List<SysMenu> menus = sysMenuMapper.selectByUserId(sysUser.getUserId());

        return menus;
    }
}
