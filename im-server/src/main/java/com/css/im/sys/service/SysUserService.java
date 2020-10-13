package com.css.im.sys.service;

import com.css.im.sys.model.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Create by wx on 2020-09-02
 */
public interface SysUserService extends UserDetailsService {

    /***
     * 获取用户具有的所有权限
     * @param sysUser
     * @return
     */
    void loadPermissions(SysUser sysUser);

    /***
     * 更新用户信息
     * @param sysUser
     */
    boolean updateUser(SysUser sysUser);

    /**
     * 删除token
     */
    void clearToken(String uid);

    SysUser getUserById(String uid);

    List<SysUser> getAllUser();

    String getUserIdByUserName(String username);
}
