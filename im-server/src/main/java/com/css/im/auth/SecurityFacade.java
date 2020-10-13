package com.css.im.auth;

import com.css.im.sys.model.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class SecurityFacade {


    /**
     * 当前用户信息
     *
     * @return
     */
    public static String getCurUserInfo() {
        //TODO
        return getUserProfile().getName() + "(" + getUserProfile().getUsername() + ")";
    }


    /**
     * get user's current login profile return null if user doesn't login at all
     * 注意：此方法需要在。DO请求中使用，不允许在servlet filter或页面中使用！
     *
     * @return
     */
    public static SysUser getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUer = (SysUser) authentication.getPrincipal();
        return sysUer;
    }

    /**
     * get user's current login profile return null if user doesn't login at all
     * 注意：此方法需要在。在controller请求中使用，不允许在servlet filter service或页面中使用！
     *
     * @return
     */
    public static String getCurUserId() {

        SysUser sysUer = getUserProfile();
        if (null != sysUer) {
            return sysUer.getUserId();
        }
        return null;
    }


}
