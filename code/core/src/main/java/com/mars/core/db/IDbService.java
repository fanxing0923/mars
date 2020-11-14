package com.mars.core.db;

import java.util.List;
import java.util.Map;

public interface IDbService {
    /***
     * @Desc 获取玩家单表数据 AS LIST
     * @param uid 玩家唯一ID
     * @param clazz 实体CLASS类型*/
    <T extends BaseEntity> List<T> getAllList(long uid,
                                              Class<T> clazz) throws Exception;

    /***
     * @Desc 获取玩家单表数据 AS MAP
     * @param uid 玩家唯一ID
     * @param clazz 实体CLASS类型*/
    <T extends BaseEntity> Map<Long, T> getAllMap(long uid,
                                                  Class<T> clazz) throws Exception;

    /**
     * @param uid   玩家唯一ID
     * @param clazz 实体CLASS类型
     * @param id    单条数据唯一ID
     * @Desc 获取单条数据
     */
    <T extends BaseEntity> T getOneById(long uid, Class<T> clazz, long id)
            throws Exception;

    /**
     * @param uid   玩家唯一ID
     * @param clazz 实体CLASS类型
     * @Desc 获取单条数据 （
     */
    <T extends BaseEntity> T getOneByUid(long uid, Class<T> clazz)
            throws Exception;


    /***
     * @Desc 获取玩家单表数据 AS MAP
     * @param uid 玩家唯一ID
     * @param clazz 实体CLASS类型*/
    <T extends BaseEntity> Map<Long, T> getAllMapBySidList(long uid,
                                                           Class<T> clazz, List<Long> sidList) throws Exception;


    /***
     * @Desc 插入一条数据（缓存层和DB层）
     * @param entity 实体BEAN*/
    <T extends BaseEntity> int insert(T entity)
            throws Exception;

    /***
     * @Desc 插入数据（缓存层和DB层）
     * @param entityList 实体集合*/
    <T extends BaseEntity> int insert(List<T> entityList)
            throws Exception;

    /***
     * @Desc 插入数据（缓存层和DB层）
     * @param entityMap 实体Map*/
    <T extends BaseEntity> int insert(Map<Long, T> entityMap)
            throws Exception;

    /***
     * @Desc 删除一条数据（缓存层和DB层）
     * @param entity 实体对象*/
    <T extends BaseEntity> int delete(T entity)
            throws Exception;

    /***
     * @Desc 删除一条数据（缓存层和DB层）
     * @param entityList 实体对象List*/
    <T extends BaseEntity> int delete(List<T> entityList)
            throws Exception;


    /**
     * @param uid      玩家唯一ID
     * @param clazz    实体CLASS类型
     * @param dbIdList 数据唯一ID LIST
     * @Desc 删除一条数据
     */
    <T extends BaseEntity> int delete(long uid, Class<? extends BaseEntity> clazz, List<Long> dbIdList)
            throws Exception;

    /**
     * @param uid   玩家唯一ID
     * @param clazz 实体CLASS类型
     * @Desc 删除全部数据
     */
    <T extends BaseEntity> int deleteAll(long uid, Class<? extends BaseEntity> clazz)
            throws Exception;

    /**
     * @param uid   玩家唯一ID
     * @param clazz 实体CLASS类型
     * @param dbId  数据唯一ID
     * @Desc 删除一条数据
     */
    <T extends BaseEntity> int delete(long uid, Class<? extends BaseEntity> clazz, long dbId)
            throws Exception;

    /**
     * @param entity 实体对象
     * @Desc 更新一条数据（缓存层和DB层）
     **/
    <T extends BaseEntity> int update(T entity)
            throws Exception;

}
