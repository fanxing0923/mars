package com.mars.impl.demo.action;

import com.google.protobuf.AbstractMessage;
import com.mars.core.msg.IMsgAction;
import com.mars.core.msg.IMsgHandler;
import com.mars.gen.msg.demo.CGDemoMsg;
import com.mars.impl.demo.service.IDemoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Component
@IMsgAction
@Log4j2
public class DemoAction {
    @Autowired
    IDemoService service;

    @IMsgHandler
    public void cgMsg(long uid, CGDemoMsg.CGDemoMsgProto cgDemoMsg, List<AbstractMessage.Builder> backList) throws Exception {
        log.info("cgDemoMsg"+cgDemoMsg.getStr());

        CGDemoMsg.CGDemoMsgProto.Builder proto = CGDemoMsg.CGDemoMsgProto.newBuilder();
        proto.setStr(service.helloFind());
        backList.add(proto);
    }

}
