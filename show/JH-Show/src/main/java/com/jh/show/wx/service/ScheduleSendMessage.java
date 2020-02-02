package com.jh.show.wx.service;


import com.jh.show.wx.service.impl.WxServiceImpl;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *  定时发送最新简报消息
 * @version <1> 2018-05-17 lxy： Created.
 */
@Component
@EnableScheduling
public class ScheduleSendMessage {
    @Autowired
    private WxServiceImpl wxService;

    /**
     * 定时发送消息
     * cron 星期一、三、五、日 早上九点执行
     */
    @Scheduled(cron = "0 0 9 ? * SUN,MON,WED,FRI")
    public void sendMessage() throws InterruptedException {
        ResultMessage resultMessage = wxService.sendTemplateMessage();
        if(resultMessage.isFlag()){
            System.out.println("发送最新简报模板消息成功！");
        }else{
            System.out.println("发送最新简报模板消息失败！");
        }
    }
}
