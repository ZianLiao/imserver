package com.css.utils;

import org.apache.commons.lang3.StringUtils;


/**
 * 文件操作
 *
 * @author wx
 */
public abstract class FileUtils {

    /**
     * <p>
     * 规范化一个文件名称, 实现应该转 "\" 到 "/", 排除多余的 "/" 输入可以是操作系统文件名称或虚拟文件系统名称
     *
     * @param original 要规范化的文件名称
     * @return 规范化后的虚拟文件名称
     */
    public static String normalize(String original) {
        if (StringUtils.isBlank(original)) {
            return original;
        }
        original = original.replace('\\', '/');
        original = eliminateRedundantSlassh(original);
        /**
         * in Linux(Unix like) system, must add prefix "/", unckecked, and in
         * WindowNT, if there is no ":", shoulb add one.
         */
        if (original.indexOf(':') == -1) {
            if (!original.startsWith("/")) {
                original = "/" + original;
            }
        } else {
            if (original.startsWith("/")) {
                original = original.substring(1);
            }
        }

        if (original.endsWith("/")) {
            original = original.substring(0, original.length() - 1);
        }

        return original;
    }

    /**
     * 从路径名称中排除多余的 "/", 通常调用这个方法之前一定要把 "\" 转成 "/"
     *
     * @return 返回排除了多余的"/"的路径
     */
    private static String eliminateRedundantSlassh(String path) {

        boolean isSlash = false;
        StringBuffer result = new StringBuffer(path.length());

        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);

            if (c != '/' || !isSlash) {
                result.append(c);
            }

            isSlash = (c == '/');
        }
        return result.toString();
    }

}
