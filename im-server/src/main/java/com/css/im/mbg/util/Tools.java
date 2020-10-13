package com.css.im.mbg.util;

import cn.hutool.core.codec.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Tools {
    public static byte[] covertIntToByte(int src) {
        int byteNum = (40 - Integer.numberOfLeadingZeros(src < 0 ? ~src : src)) / 8;
        byte[] result = new byte[4];
        for (int i = 0; i < byteNum; i++) {
            result[i] = (byte) (src >> (i * 8));
        }
        return result;
    }

    public static byte[] covertLongToByte(long src) {
        int byteNum = (72 - Long.numberOfLeadingZeros(src < 0 ? ~src : src)) / 8;
        byte[] result = new byte[8];
        for (int i = 0; i < byteNum; i++) {
            result[i] = (byte) (src >> (i * 8));
        }
        return result;
    }

    public static byte[] covertStringToByte(String src) {
        return src.getBytes();
    }

    public static int covertByteToInt(byte[] _package, int index) {
        int result = 0;
        int temp;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            temp = _package[4 - 1 - i + index];
            if (temp < 0) {
                temp += 256;
            }
            result += (temp & 0x000000FF) << shift;
        }
        return result;
    }

    public static long covertByteToLong(byte[] _package, int index) {
        long result = 0;
        int temp;
        for (int i = 0; i < 8; i++) {
            int shift = (8 - 1 - i) * 8;
            temp = _package[8 - 1 - i + index];
            if (temp < 0) {
                temp += 256;
            }
            result += (temp & 0x00000000000000FF) << shift;
        }
        return result;
    }

    public static String covertByteToString(byte[] _package, int index) {
        //第三个参数20.表示要转换字符串的长度，usercode长度为20
        return new String(_package, index, 20);
    }

    public static String getTime() {
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = df.format(dt);
        return nowTime;
    }

    public static String getTime(String pattern) {
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat(pattern);
        String nowTime = df.format(dt);
        return nowTime;
    }

    @SuppressWarnings("finally")
    public static byte[] getBytesFromStream(InputStream stream) {
        byte[] buffer = new byte[10240];
        int readCnt;
        ByteBufferStd bbs = new ByteBufferStd();
        try {
            while ((readCnt = stream.read(buffer, 0, 10240)) > 0) {
                bbs.AddBytes(buffer, readCnt);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return bbs.GetBytes();
        }
    }

    public static String Bytes2Base64String(byte[] value) {
        if (value == null || value.length < 0)
            return null;
        return Base64.encode(value);
    }


    public static byte[] Base64String2Bytes(String value) {
        try {
            return Base64.decode(value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
