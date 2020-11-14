package com.mars.core.db.impl;

import com.google.common.primitives.Ints;
import com.google.protobuf.GeneratedMessage;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

/**
 * @ Description   :  序列化工具类
 * @ Author        :  xing.fan
 * @ CreateDate    :  2019/11/20
 * @ Version       :  v1.0.0
 */
public class BytesUtil {
    public static byte[] shortToByteArray(short value) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort(value);
        byte[] bytes =buffer.array();
        return bytes;
    }

    public static byte[] longToByteArray(long value) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(value);
        byte[] bytes =buffer.array();
        return bytes;
    }

    public static byte[] intToByteArray(int value) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(value);
        byte[] bytes =buffer.array();
        return bytes;
    }

    public static int getMsgType(GeneratedMessage builder) throws Exception{
        Method getMoney;
        getMoney = builder.getClass().getMethod("getProtoId");
        Object money2 = getMoney.invoke(builder);
        return Ints.tryParse(money2.toString());
    }


    /***二进制数组转换为long类型*/
    public static long byte2Long(byte[] b) {
        long s = 0;
        long s0 = b[0] & 0xff;
        long s1 = b[1] & 0xff;
        long s2 = b[2] & 0xff;
        long s3 = b[3] & 0xff;
        long s4 = b[4] & 0xff;
        long s5 = b[5] & 0xff;
        long s6 = b[6] & 0xff;
        long s7 = b[7] & 0xff;
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s4 <<= 8 * 4;
        s5 <<= 8 * 5;
        s6 <<= 8 * 6;
        s7 <<= 8 * 7;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
        return s;
    }
}
