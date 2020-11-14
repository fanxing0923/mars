package com.mars.core.service;

import com.mars.core.db.BaseEntity;
import com.mars.core.db.IDbService;
import com.mars.core.db.impl.RedisDBServiceImpl;

import java.util.List;
import java.util.Map;

public class BaseService implements IDbService {
    private IDbService dbService = null;

    {
        dbService = RedisDBServiceImpl.getInstance();
    }

    @Override
    public <T extends BaseEntity> List<T> getAllList(long uid, Class<T> clazz) throws Exception {
        return dbService.getAllList(uid, clazz);
    }

    @Override
    public <T extends BaseEntity> Map<Long, T> getAllMap(long uid, Class<T> clazz) throws Exception {
        return dbService.getAllMap(uid, clazz);
    }

    @Override
    public <T extends BaseEntity> T getOneById(long uid, Class<T> clazz, long id) throws Exception {
        return dbService.getOneById(uid, clazz, id);
    }

    @Override
    public <T extends BaseEntity> T getOneByUid(long uid, Class<T> clazz) throws Exception {
        return dbService.getOneByUid(uid, clazz);
    }

    @Override
    public <T extends BaseEntity> Map<Long, T> getAllMapBySidList(long uid, Class<T> clazz, List<Long> sidList) throws Exception {
        return dbService.getAllMapBySidList(uid, clazz, sidList);
    }

    @Override
    public <T extends BaseEntity> int insert(T entity) throws Exception {
        return dbService.insert(entity);
    }

    @Override
    public <T extends BaseEntity> int insert(List<T> entityList) throws Exception {
        return dbService.insert(entityList);
    }

    @Override
    public <T extends BaseEntity> int insert(Map<Long, T> entityMap) throws Exception {
        return dbService.insert(entityMap);
    }

    @Override
    public <T extends BaseEntity> int delete(T entity) throws Exception {
        return dbService.delete(entity);
    }

    @Override
    public <T extends BaseEntity> int delete(List<T> entityList) throws Exception {
        return dbService.delete(entityList);
    }

    @Override
    public <T extends BaseEntity> int delete(long uid, Class<? extends BaseEntity> clazz, List<Long> dbIdList) throws Exception {
        return dbService.delete(uid, clazz, dbIdList);
    }

    @Override
    public <T extends BaseEntity> int deleteAll(long uid, Class<? extends BaseEntity> clazz) throws Exception {
        return dbService.deleteAll(uid, clazz);
    }

    @Override
    public <T extends BaseEntity> int delete(long uid, Class<? extends BaseEntity> clazz, long dbId) throws Exception {
        return dbService.delete(uid, clazz, dbId);
    }

    @Override
    public <T extends BaseEntity> int update(T entity) throws Exception {
        return dbService.update(entity);
    }
}
