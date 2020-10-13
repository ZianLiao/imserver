package com.css.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    private static final String HEX_CHARS = "0123456789abcdef";

    public static String md5String16(String str) {
        String result = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] data = md5.digest(str.getBytes(StandardCharsets.UTF_8));
            result = hexBytes(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String hexBytes(byte[] b) {
        StringBuffer buf = new StringBuffer();
        for (byte t : b) {
            int h = (t & 0xf0) >> 4;
            buf.append(HEX_CHARS.charAt(h));
            int l = t & 0x0f;
            buf.append(HEX_CHARS.charAt(l));
        }
        return buf.substring(8, 24);
    }
}
