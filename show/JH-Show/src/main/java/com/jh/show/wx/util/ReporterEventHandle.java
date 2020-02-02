package com.jh.show.wx.util;


import com.github.sd4324530.fastweixin.message.Article;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.NewsMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.jh.show.wx.service.impl.WxServiceImpl;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;

import java.util.List;
import java.util.Map;

/**
 * 点击菜单获返回对应的消息
 * @version <1> 2018-05-06 lxy： Created.
 */
public class ReporterEventHandle {
    private WxServiceImpl businessService;
    public ReporterEventHandle(){
    }
    public ReporterEventHandle(WxServiceImpl businessService){
        this.businessService = businessService;
    }

    /**
     * 点击菜单返回对应的消息
     * @param wechatId 用户微信编号
     * @return 返回消息
     * @version <1> 2018-05-06 lxy： Created.
     */
    public BaseMsg handleReporter(String wechatId) {
        ResultMessage resultMessage = businessService.queryReporterByRegionAndCrops(wechatId,null);
        if(resultMessage.isFlag()){
            List<Map<String, Object>> briefMapList= (List<Map<String,Object>>)resultMessage.getData();//最新简报信息
            StringBuilder summary = WeChatUtils.wrapBriefReporterSummary(briefMapList);//将最新简报作简要概览总结
            return createNewsMsg(summary.toString(),wechatId);//返回手机简报信息
        }else{
            String message = resultMessage.getMsg();//消息编号
            if(WeChatUtils.Msg_Wechat_Not_BriefingOrKeyword.equals(message)){
                String userCenterUrl = PropertyUtil.getPropertiesForConfig("weixin.webroot.url")+"/nolog/wechat/user/redirect?wechatId="+wechatId;
                return new TextMsg("您可以回复，如\"湖北 冬小麦\"获取最新简报信息。" +System.getProperty("line.separator")+
                        "您也可以进入<a href='"+userCenterUrl+"'>会员服务-个人中心</a>，注册会员订阅最新简报！");
            }else{
                return new TextMsg("您订阅的最新简报信息在更新中，请您稍后处理！");
            }

        }
    }

    /**
     *
     * @param summary 最新简报概要
     * @param wxId 微信编号
     * @return 新闻消息
     */
    private NewsMsg createNewsMsg(String summary,String wxId){
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
        return newsMsg;
    }

    /**
     * 联系我们
     * @return 联系我们的信息
     * @version <1> 2018-05-06 lxy： Created.
     */
    public BaseMsg handleContact() {
        StringBuilder info = new StringBuilder();
        info.append("公司简介：").append(System.getProperty("line.separator"));
        info.append(" 武汉珈和科技有限公司成立于2013年，致力于空间智能技术为核心的大数据信息服务，" +
                "创新技术依托于中国科学院、武汉大学、北京师范大学，是业内领先的卫星遥感解译服务商。珈和科技秉承“提升效能、优化生态”的企业使命，" +
                "潜心耕耘遥感信息行业多年。服务领域涵盖遥感影像私有云、遥感影像数据挖掘、农业遥感行业应用等。")
                .append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));;
        info.append("企业邮箱：").append(System.getProperty("line.separator"));
        info.append("Market@datall.cn").append(System.getProperty("line.separator"));
        info.append("联系电话：").append(System.getProperty("line.separator"));
        info.append("027-81562688");
        return new TextMsg(info.toString());
    }
}
