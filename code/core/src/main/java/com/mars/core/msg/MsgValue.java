/**
 * @program: mars
 * @description:
 * @author: xing.fan
 * @create: 2020-11-13 20:17
 **/
package com.mars.core.msg;

import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Method;

@Data
@Builder
public class MsgValue {
    private int msgType;
    private Object action;
    private Method parseFrom;
    private Class<?>  msgClass;
}