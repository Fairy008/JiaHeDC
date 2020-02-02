package com.jh.forum.bbs.util;

import com.jh.forum.bbs.mapping.IForumDownloadDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Configuration
@EnableScheduling
public class ScheduledUtil {


    @Autowired
    private IForumDownloadDataMapper downloadDataMapper;

    /**
     * 功能描述:每天凌晨4点定时刷新数据的下载量和收藏量
     * @Param:
     * @Return: []
     * @version<1>  2019/9/29  wangli :Created
     */
    @Scheduled(cron = "0 30 2 ? * *")
    private void numTask(){
        downloadDataMapper.updateNum();
    }

}
