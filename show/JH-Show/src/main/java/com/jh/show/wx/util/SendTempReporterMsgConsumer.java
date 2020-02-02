package com.jh.show.wx.util;

import com.github.sd4324530.fastweixin.api.CustomAPI;
import com.github.sd4324530.fastweixin.api.enums.ResultType;
import com.github.sd4324530.fastweixin.message.Article;
import com.github.sd4324530.fastweixin.message.NewsMsg;
import com.jh.show.wx.model.WXSentReporter;
import com.jh.util.PropertyUtil;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 处理微信公众号最新简报模板消息的发送
 * @version <1> 2018-05-06 lxy： Created.
 */
public class SendTempReporterMsgConsumer implements BiConsumer<String,List<Map<String, Object>>> {
    private CustomAPI customAPI;
    private List<WXSentReporter> sentReporters;//定义装载发送简报模板消息实体
    public SendTempReporterMsgConsumer(CustomAPI customAPI, List<WXSentReporter> sentReporters) {
        this.customAPI = customAPI;
        this.sentReporters = sentReporters;
    }

    @Override
    public void accept(String wxId,List<Map<String, Object>> briefMapList) {
        StringBuilder summary = WeChatUtils.wrapBriefReporterSummary(briefMapList);//将最新简报作简要概览总结
        sendTemplateMsg(summary.toString(),wxId);//发送消息
        for(Map<String,Object> briefMap : briefMapList){
            Object reporterId = briefMap.get("reporterId");//简报编号
            if(reporterId == null) continue;
            //创建已发送简报模板消息实体,用于保存到数据库，表示已经发送的简报信息。
            WXSentReporter reporter = new WXSentReporter();
            reporter.setWxId(wxId);
            reporter.setReporterId(new Long(reporterId+""));
            sentReporters.add(reporter);
        }
    }

    /**
     * 发送模板消息
     * @param summary 简报概要
     * @param wxId 微信编号
     * @return 返回SendTemplateResponse
     */
    private ResultType sendTemplateMsg(String summary,String wxId) {
        if(StringUtils.isEmpty(summary)){
            return ResultType.SYSTEM_ERROR;
        }
        String url = PropertyUtil.getPropertiesForConfig("weixin.webroot.url")+
                "/nolog/wechat/user/mobileRepoterItems?wxId="+wxId;
        NewsMsg newsMsg = new NewsMsg(); //新闻消息
        Article article = new Article();//文章
        article.setTitle("最新农情简报");//标题
        article.setUrl(url);//设置跳转的地址
        article.setDescription(summary);//描述
        String webPage= PropertyUtil.getPropertiesForConfig("weixin.webpage.url");
        article.setPicUrl(webPage+"/images/crop/farmer.jpg");
        newsMsg.add(article);//添加文章
        ResultType resultType = customAPI.sendCustomMessage(wxId,newsMsg);//发送新闻消息
        return resultType;
    }
}
