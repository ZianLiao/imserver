package com.css.im.mbg.util;

public class ByteBufferStd {
    /// <summary>
    /// 数组最大长度
    /// </summary>
    private final int MAXLENGTH = 1024 * 60;

    /// <summary>
    /// 数组增量
    /// </summary>
    private final int INCREMENT = 1024 * 20;

    /// <summary>
    /// 缓冲器
    /// </summary>
    private byte[] cacheBytes;

    /// <summary>
    /// 缓冲器容量
    /// </summary>
    private int capacity = 0;

    /// <summary>
    /// 字节计数
    /// </summary>
    private int count = 0;

    /// <summary>
    /// 构造函数
    /// </summary>
    public ByteBufferStd() {
        cacheBytes = new byte[MAXLENGTH];
        capacity = MAXLENGTH;
    }

    /// <summary>
    /// 向缓冲器添加字节数组
    /// </summary>
    /// <param name="bytes">字节数组</param>
    /// <param name="length">每次添加的字节数</param>
    public void AddBytes(byte[] bytes, int length) {
        if (count + length < capacity) {
            System.arraycopy(bytes, 0, cacheBytes, count, length);
            count += length;
        } else {
            int tempCapacity = capacity + INCREMENT;
            while (count + length >= tempCapacity) {
                tempCapacity += INCREMENT;
            }

            byte[] cacheBytes_change = new byte[tempCapacity];
            System.arraycopy(cacheBytes, 0, cacheBytes_change, 0, count);
            cacheBytes = cacheBytes_change;
            System.arraycopy(bytes, 0, cacheBytes, count, length);

            count += length;
        }
    }

    /// <summary>
    /// 获取缓冲器中的字节数组
    /// </summary>
    /// <returns>字节数组</returns>
    public byte[] GetBytes() {
        byte[] retBytes = new byte[count];
        System.arraycopy(cacheBytes, 0, retBytes, 0, count);

        return retBytes;
    }

}