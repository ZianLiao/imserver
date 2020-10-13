package com.css.im.sys.service.impl;

import com.css.common.BaseService;
import com.css.im.sys.mapper.SysUserMapper;
import com.css.im.sys.model.SysMenu;
import com.css.im.sys.model.SysUser;
import com.css.im.sys.model.SysUserExample;
import com.css.im.sys.service.SysMenuService;
import com.css.im.sys.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create by wx on 2020-09-01
 */
@Service
public class SysUserServiceImpl extends BaseService implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysMenuService sysMenuService;

    /***
     * 根据用户名获取用户信息
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        SysUserExample filter = new SysUserExample();
        filter.createCriteria().andUsernameEqualTo(s);
        List<SysUser> users = sysUserMapper.selectByExampleWithBLOBs(filter);
        if (users.size() == 0) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
        loadPermissions(users.get(0));
        return users.get(0);
    }

    @Override
    public void loadPermissions(SysUser sysUser) {
        List<SysMenu> meuns = sysMenuService.loadAllUserMenus(sysUser);
        sysUser.setMenus(meuns);
    }

    @Transactional
    @Override
    public boolean updateUser(SysUser sysUser) {
        int affected = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        return affected > 0;
    }

    @Transactional
    @Override
    public void clearToken(String uid) {
        SysUser update = new SysUser();
        update.setUserId(uid);
        update.setToken(StringUtils.EMPTY);
        sysUserMapper.updateByPrimaryKeySelective(update);
    }

    @Override
    public SysUser getUserById(String uid) {
        return sysUserMapper.selectByPrimaryKey(uid);
    }

    @Override
    public List<SysUser> getAllUser() {
        return sysUserMapper.queryAllUser();
    }

    @Override
    public String getUserIdByUserName(String username) {
        SysUserExample filter = new SysUserExample();
        filter.createCriteria().andUsernameEqualTo(username);
        List<SysUser> sysUsers = sysUserMapper.selectByExample(filter);
        if (sysUsers == null || sysUsers.size() < 1) {
            return null;
        } else {
            return sysUsers.get(0).getUserId();
        }
    }


}
