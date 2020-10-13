package com.css.im.timer;

import com.css.im.cache.service.IMSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时检测，用户连接session是否还在保持长连接，中断的移除缓存
 * Create by wx on 2020-08-26
 */
@Component
public class IMSessionConnectCheckTask {

    @Autowired
    IMSessionService iMSessionService;

    //每5分钟检测一次
    @Scheduled(fixedRate = 300000)
    public void cacheSessionCheck() {
        iMSessionService.manageUserSession();

    }


}
