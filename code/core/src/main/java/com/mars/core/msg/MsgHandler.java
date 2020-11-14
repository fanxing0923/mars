/**
 * @program: mars
 * @description:
 * @author: xing.fan
 * @create: 2020-11-09 10:31
 **/
package com.mars.core.msg;

import com.google.protobuf.GeneratedMessage;
import lombok.Data;

import java.lang.reflect.Method;
@Data
public class MsgHandler {
    private Method med;
    private Object source;
    private GeneratedMessage msg;
}