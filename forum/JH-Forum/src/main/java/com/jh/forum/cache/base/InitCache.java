package com.jh.forum.cache.base;

import com.jh.forum.cache.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 微服务启动加载社区缓存
 * @param
 * @return 
 * @version <1> 2019/3/6 14:25 zhangshen:Created.
 */
@Component
public class InitCache implements ApplicationRunner {

    @Autowired
    private ICacheService cache;

    @Override
    @Async
    public void run(ApplicationArguments arg) throws Exception {
        cache.initAllDataCache();
    }
}
