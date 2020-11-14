/**
 * @program: mars
 * @description:
 * @author: xing.fan
 * @create: 2020-11-09 10:26
 **/
package com.mars.core.msg;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

import java.util.Map;


public class MsgManager {
    private static MsgManager ins = null;
    private static Map<Integer, MsgEntry> msgMap = Maps.newHashMap();
    private static BiMap<Class<?>, Integer> msgTypeMap = HashBiMap.create();

    private MsgManager() {
    }

    /***获取实例**/
    public static MsgManager getIns() {
        if (ins == null) {
            synchronized (MsgManager.class) {
                if (ins == null) {
                    ins = new MsgManager();
                }
            }
            return ins;
        }
        return ins;
    }

    /***获取执行体*/
    public MsgEntry getActionImpl(int msgType) {
        return msgMap.get(msgType);
    }

    /***注册消息*/
    public void registerMsg(int msgType, MsgEntry msgEntry) throws Exception {
        if(msgMap.get(msgType)!=null){
            throw new Exception("msgType repeat:"+msgType);
        }
        msgMap.put(msgType, msgEntry);
        msgTypeMap.put(msgEntry.getValue().getMsgClass(),msgType);
    }

    /***获取消息类型*/
    public int getMsgType(Class clazz){
        return msgTypeMap.get(clazz);
    }

}