package com.css.utils;

import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieUtil {

    public static void setValue(HttpServletResponse response, String key, String value) {
        Assert.notNull(key);

        Cookie c = new Cookie(key, value);
        c.setMaxAge(60 * 10);
        c.setPath("/");
        response.addCookie(c);
    }

    public static String getValue(HttpServletRequest request, String key) {
        Assert.notNull(key);

        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void clearValue(HttpServletResponse response, String key) {
        setValue(response, key, null);
    }
}
