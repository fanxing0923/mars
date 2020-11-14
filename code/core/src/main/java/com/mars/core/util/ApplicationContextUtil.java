/**
 * @program: mars
 * @description:
 * @author: xing.fan
 * @create: 2020-11-13 15:24
 **/
package com.mars.core.util;

import com.mars.core.db.impl.ProtoBuffUtil;
import com.mars.core.msg.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
@Log4j2
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
        try {
            initMsg();
        } catch (Exception ex) {
            log.error("", ex);
            System.exit(1);
        }
    }


    /***初始化消息协议*/
    @IMsgHandler
    private void initMsg() throws Exception {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(IMsgAction.class);
        beans.entrySet().forEach(entry -> {
            try {
                Object obj = entry.getValue();
                Optional<Method> opt = Arrays.stream(obj.getClass().getMethods())
                        .filter(temp -> temp.getAnnotationsByType(IMsgHandler.class).length > 0).findAny();
                if (opt.isEmpty()) {
                    return;
                }
                Method med = opt.get();
                int msgType = ProtoBuffUtil.getMsgType(med.getParameterTypes()[1]);
                Class<?> clazz = Class.forName(med.getParameterTypes()[1].getName());
                MsgManager.getIns()
                        .registerMsg(msgType
                                , new MsgEntry(med, MsgValue.builder().action(obj)
                                        .msgType(msgType)
                                        .msgClass(clazz)
                                        .parseFrom(clazz
                                                .getMethod("parseFrom", byte[].class)).build()));
            } catch (Exception ex) {
                log.error("", ex);
                System.exit(1);
            }
        });
    }

}