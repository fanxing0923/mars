package com.mars.impl.demo.service;

import com.mars.core.service.BaseService;
import com.mars.entity.demo.DemoEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class DemoService extends BaseService implements IDemoService {

    @Override
    public int helloInsert() throws Exception {
        DemoEntity db = new DemoEntity();
        db.setUid(1001);
        db.setStr("hello");
        this.insert(db);
        return this.insert(db);
    }

    @Override
    public String helloFind() throws Exception {
        DemoEntity db = this.getOneByUid(1001, DemoEntity.class);
        return db.getStr();
    }
}
