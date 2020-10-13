package com.css.im.auth;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ExplorerIpUtils {

    public static String getClientIP(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip)) {
            ip = request.getRemoteAddr();
        }
        String[] ips = new String[0];
        if (StringUtils.isNotEmpty(ip)) {
            ips = ip.split(",");
        }
        for (int i = 0; i < ips.length; i++) {
            if (!"unknown".equalsIgnoreCase(ips[i])) {
                ip = ips[i];
                break;
            }
        }
        return ip;
    }

    // TODO 没有safari ??? 连使用的操作系统信息也一并记录一下吧，
    public static String getClientExplorer(HttpServletRequest request) {

        String explorer = "未知";
        String info = request.getHeader("user-agent");

        final String regexStr = "";
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher;

        if (info.indexOf("MSIE") != -1) {
            explorer = "IE"; // 微软IE
            pattern = Pattern.compile(explorer + "\\s([0-9.]+)");
        } else if (info.indexOf("Firefox") != -1) {
            explorer = "Firefox"; // 火狐
            pattern = Pattern.compile(explorer + "\\/([0-9.]+)");
        } else if (info.indexOf("Chrome") != -1) {
            explorer = "Chrome"; // Google
            pattern = Pattern.compile(explorer + "\\/([0-9.]+)");
        } else if (info.indexOf("Opera") != -1) {
            explorer = "Opera"; // Opera
            pattern = Pattern.compile("Version\\/([0-9.]+)");
        }

        matcher = pattern.matcher(info);

        try {
            if (matcher.find()) {
                if (matcher.groupCount() > 0) {
                    explorer += " " + matcher.group(1);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return explorer;
    }

}
