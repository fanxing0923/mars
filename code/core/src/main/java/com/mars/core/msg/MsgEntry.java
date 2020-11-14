/**
 * @program: mars
 * @description:
 * @author: xing.fan
 * @create: 2020-11-13 17:22
 **/
package com.mars.core.msg;

import com.mars.core.db.impl.ProtoBuffUtil;
import lombok.Builder;

import java.lang.reflect.Method;
import java.util.AbstractMap;
//		CGDemoMsg.CGDemoMsgProto msg = CGDemoMsg.CGDemoMsgProto.parseFrom((byte[]) null);
public class MsgEntry extends AbstractMap.SimpleEntry<Method,MsgValue> {
    /**
     * Creates an entry representing a mapping from the specified
     * key to the specified value.
     *
     * @param key   the key represented by this entry
     * @param value the value represented by this entry
     */
    public MsgEntry(Method key, MsgValue value) {
        super(key, value);
    }
}