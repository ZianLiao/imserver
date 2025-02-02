package com.css.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public abstract class UUIDUtils {


    /**
     * 生成新的UUID 32位串
     *
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getToken() {

        return RandomStringUtils.randomAlphabetic(32);

    }

    public static String batchCode() {
        return "W" + DateTimeUtils.formatCurrentTime();
    }

}
