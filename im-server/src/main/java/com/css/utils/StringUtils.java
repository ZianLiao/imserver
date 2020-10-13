package com.css.utils;

public abstract class StringUtils {

    //检查字符串b是否存在于字符数组a中 存在返回true 不存在返回false
    public static boolean isHave(String[] a, String b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i].equals(b)) {
                return true;
            }
        }
        return false;
    }

    //字符串判空
    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static boolean isNotEmpty(String string) {
        return string != null && string.length() > 0;
    }
}
