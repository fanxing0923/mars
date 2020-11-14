package com.mars.core.db.impl;

import com.google.common.primitives.Ints;
import com.mars.core.db.BaseEntity;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ProtoBuffUtil {
    /***序列化对象序为二进制数组*/
    public static byte[] toBytes(BaseEntity entity) throws Exception {
        return obj2Bytes(entity, entity.getClass());
    }

    /***反序列化二进制数组为数据对象*/
    public static <T extends BaseEntity> T toInstance(byte[] bytes, Class<? extends BaseEntity> clazz) throws Exception {
        return (T) bytes2Obj(bytes, clazz);
    }

    public static byte[] obj2Bytes(Object obj, Class clazz) {
        if (obj == null) {
            return null;
        }
        Schema schema = RuntimeSchema.getSchema(clazz);
        LinkedBuffer buffer = LinkedBuffer.allocate(4096);
        byte[] protoBuff = null;
        protoBuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        return protoBuff;
    }


    public static Object bytes2Obj(byte[] bytes, Class clazz) throws Exception {
        if (bytes == null) {
            return null;
        }
        Schema schema = RuntimeSchema.getSchema(clazz);
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        return obj;
    }

    private static List<byte[]> list2Bytes(List list, Class clazz) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        List<byte[]> bytes = new ArrayList<byte[]>();
        Schema schema = RuntimeSchema.getSchema(clazz);
        LinkedBuffer buffer = LinkedBuffer.allocate(4096);
        byte[] protoBuff = null;
        for (Object p : list) {
            try {
                protoBuff = ProtostuffIOUtil.toByteArray(p, schema, buffer);
                bytes.add(protoBuff);
            } finally {
                buffer.clear();
            }
        }
        return bytes;
    }

    private static List bytes2List(
            List<byte[]> bytesList, Class clazz) {
        if (bytesList == null || bytesList.size() <= 0) {
            return null;
        }
        Schema schema = RuntimeSchema.getSchema(clazz);
        List<Object> list = new ArrayList<Object>();
        for (byte[] bs : bytesList) {
            Object obj = null;
            try {
                obj = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            ProtostuffIOUtil.mergeFrom(bs, obj, schema);
            list.add(obj);
        }
        return list;
    }

    /***
     * CGDemoMsg cg = CGDemoMsg.CGDemoMsgProto.newBuilder().getProtoId()
     */
    public static int getMsgType(Class<?> clazz) {
        try {
            Object obj = Class.forName(clazz.getName())
                    .getMethod("newBuilder").invoke(null);
            Method med = obj.getClass().getMethod("getProtoId");
            return Ints.tryParse(String.valueOf(med.invoke(obj)));
        } catch (Exception e) {
            log.error("", e);
        }
        return -1;
    }
}
