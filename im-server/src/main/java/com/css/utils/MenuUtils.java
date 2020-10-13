package com.css.utils;

public abstract class MenuUtils {


    /**
     * 生成新的MenuKey
     *
     * @return
     */
    public static String createMenuKey() {
        return "MENU_" + DateTimeUtils.formatCurrentTime();
    }

}
