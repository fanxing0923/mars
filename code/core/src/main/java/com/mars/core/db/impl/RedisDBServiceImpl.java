package com.mars.core.db.impl;

import com.mars.core.db.BaseEntity;
import com.mars.core.db.IDbService;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.ByteBuffer;

public class RedisDBServiceImpl implements IDbService {
    private static IDbService instance = null;

    public static IDbService getInstance() {
        if (instance == null) {
            synchronized (RedisDBServiceImpl.class) {
                if (instance == null) {
                    instance = new RedisDBServiceImpl();
                }
            }
        }
        return instance;
    }

    private RedisDBServiceImpl() {
    }

    @Override
    public <T extends BaseEntity> List<T> getAllList(long uid, Class<T> clazz) throws Exception {
        return null;
    }

    @Override
    public <T extends BaseEntity> Map<Long, T> getAllMap(long uid, Class<T> clazz) throws Exception {
        Map<Long, T> resultMap = new HashMap<Long, T>();
        Jedis jedis = null;
        try {
            jedis = RedisConnManager.getInstance().getConn();
            byte[] keys = clazz.newInstance().getTableKey().getBytes();
            Map<byte[], byte[]> dbMap = jedis.hgetAll(keys);
            for (Map.Entry entry : dbMap.entrySet()) {
                long id = BytesUtil.byte2Long((byte[]) entry.getKey());
                T value = ProtoBuffUtil.toInstance((byte[]) entry.getValue(), clazz);
                resultMap.put(id, value);
            }
            return resultMap;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public <T extends BaseEntity> T getOneById(long uid, Class<T> clazz, long id) throws Exception {
        return null;
    }

    @Override
    public <T extends BaseEntity> T getOneByUid(long uid, Class<T> clazz) throws Exception {
        return null;
    }

    @Override
    public <T extends BaseEntity> Map<Long, T> getAllMapBySidList(long uid, Class<T> clazz, List<Long> sidList) throws Exception {
        return null;
    }

    @Override
    public <T extends BaseEntity> int insert(T entity) throws Exception {
        Jedis jedis = null;
        try {
            jedis = RedisConnManager.getInstance().getConn();
            byte[] keys = entity.getTableKey().getBytes();
            byte[] value = ProtoBuffUtil.toBytes(entity);
            Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
            map.put(BytesUtil.longToByteArray(entity.getId()), value);
            String result = jedis.hmset(keys, map);
            return "OK".equals(result) ? 1 : 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public <T extends BaseEntity> int insert(List<T> entityList) throws Exception {
        return 0;
    }

    @Override
    public <T extends BaseEntity> int insert(Map<Long, T> entityMap) throws Exception {
        return 0;
    }

    @Override
    public <T extends BaseEntity> int delete(T entity) throws Exception {
        return 0;
    }

    @Override
    public <T extends BaseEntity> int delete(List<T> entityList) throws Exception {
        return 0;
    }

    @Override
    public <T extends BaseEntity> int delete(long uid, Class<? extends BaseEntity> clazz, List<Long> dbIdList) throws Exception {
        return 0;
    }

    @Override
    public <T extends BaseEntity> int deleteAll(long uid, Class<? extends BaseEntity> clazz) throws Exception {
        return 0;
    }

    @Override
    public <T extends BaseEntity> int delete(long uid, Class<? extends BaseEntity> clazz, long dbId) throws Exception {
        return 0;
    }

    @Override
    public <T extends BaseEntity> int update(T entity) throws Exception {
        return this.insert(entity);
    }
}
