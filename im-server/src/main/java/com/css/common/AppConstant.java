package com.css.common;


public abstract class AppConstant {
    /**
     * 系统编码方式
     */
    public static final String ENCODE = "utf-8";

    public static final String MESSAGE_FORMAT_JSON = "application/json";

    public final static class SysApp {

        public final static String SV_TRAIN_ROOT = "WEB-INF/";
        public final static String SYS_ID = "SYS00001";

    }

    public final static class SecurityConst {
        public static final String AQ_AUTHENTICATION_COOKIE_KEY = "AQ_AUTHENTICATION_COOKIE_KEY";
        public static final String USER_OPERATER_COOKIE_KEY = "USER_OPERATER_COOKIE_KEY";
        public final static String AQ_AUTHENTICATION_KEY = "AQ_AUTHENTICATION";
        public static final String ANONYMOUS_ID = "100001";
    }

    public static String SITE_NAME = null;
    public static String APP_ROOT_URL = null;
}
