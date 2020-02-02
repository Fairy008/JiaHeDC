package com.jh.show.wx.util;

import com.github.sd4324530.fastweixin.handle.MessageHandle;
import com.github.sd4324530.fastweixin.message.Article;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.NewsMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import com.jh.show.wx.service.impl.WxServiceImpl;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import com.jh.constant.SysConstant;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 回复消息处理
 * @version <1> 2018-05-06 lxy： Created.
 */
public class ReplyMessageHandle implements MessageHandle<TextReqMsg>{
    private WxServiceImpl businessService;
    public ReplyMessageHandle(){

    }
    public ReplyMessageHandle(WxServiceImpl businessService){
        this.businessService = businessService;
    }


/**
     * 处理公众号用户发过来的信息.
     * @param message 消息
     * @return
     * @version <1> 2018-05-06 lxy： Created.
     */
    @Override
    public BaseMsg handle(TextReqMsg message) {
        String content = message.getContent().trim();//消息内容
        String []params = content.split("\\s+");//截取
        if(params.length <= 1){
            return new TextMsg("请您输入如：\"湖北 冬小麦\"格式内容,获取最新的简报！");
        }
        String regionName = params[0];//区域名称
        String cropsName = params[1];//作物名称

        //用户微信编号
        String wechatId = message.getFromUserName();
        ResultMessage resultMessage = businessService.queryReporterByRegionAndCrops(wechatId,regionName+" "+cropsName);
        Map<String,String> excludeRepeatReporter = new HashMap<>();//排除重复简报的容器
        if(resultMessage.isFlag()){
            List<Map<String,Object>> resultDatas = (List<Map<String,Object>>)resultMessage.getData();
//            String reporterMobileContent = "";
//            StringBuilder summary = WeChatUtils.wrapSingleBriefReporterSummary(resultDatas);//将最新简报作简要概览总结
            String reporterId = resultDatas.get(0).get("reporterid").toString();
            String reportername = resultDatas.get(0).get("reportername").toString();
//            return createNewsMsg(summary.toString(),reporterId,wechatId);//返回手机简报信息
            return createNewsMsg(reportername,reporterId,wechatId);//返回手机简报信息
//            String userCenterUrl = PropertyUtil.getPropertiesForConfig("weixin.webroot.url")+"/nolog/wechat/user/redirect?wechatId="+wechatId;//进入用户中心地址
//            String endSummary = PropertyUtil.getPropertiesForConfig("weixin.end.summary");//简报后面那详情点击
//            endSummary = endSummary.replace("{{url}}",userCenterUrl);//替换url
//            boolean first=true;
//            for(HashMap map:resultDatas){
//                BriefReporterVO reporterVO = WeChatUtils.wrapperBriefReporterFromMap(map);
//                String containsKey = reporterVO.getRegionId()+"-"+reporterVO.getCropsId()+"-"+reporterVO.getReportDataTimeEnd();//组成判断是否有重复简报的键
//                String reporter = reporterVO.getReporterMobileContent();
//                if(StringUtils.isEmpty(reporter)){
//                    continue;
//                }else{
//                    if(excludeRepeatReporter.containsKey(containsKey)){//如果有包含的键，就跳到下一次循环
//                        continue;
//                    }
//                    excludeRepeatReporter.put(containsKey,containsKey);//存放重复键
//                }
//                if(first == true){
//                    reporterMobileContent = reporter+endSummary;
//                    first = false;
//                }else{
//                    reporterMobileContent+=System.getProperty("line.separator")+System.getProperty("line.separator")+reporter+endSummary;
//                }
//            }
//            if(StringUtils.isEmpty(reporterMobileContent)){
//                return new TextMsg(String.format("您获取得最新简报信息手机版正在更新中，请您稍后获取！",regionName,cropsName));//返回手机简报信息没有的情况
//            }
//            return new TextMsg(reporterMobileContent);//返回手机简报信息
        }else{
            String replyMsg = String.format(resultMessage.getMsg());
            if(SysConstant.Msg_Wechat_FindBriefing_Fail.equals(replyMsg)){
                return new TextMsg(String.format("您获取得最新简报信息手机版正在更新中，请您稍后获取！",regionName,cropsName));
            }
            return new TextMsg(replyMsg);
        }
    }


    @Override
    public boolean beforeHandle(TextReqMsg message) {
        String content = message.getContent();//消息内容
        if(content.matches("xxx") || content.matches(" ")){
            return false;
        }
        return true ;
    }

    /**
     *
     * @param summary 最新简报概要
     * @param wxId 微信编号
     * @return 新闻消息
     */
    private NewsMsg createNewsMsg(String summary,String reporterId, String wxId){
        String url = PropertyUtil.getPropertiesForConfig("weixin.webroot.url")+
                "/nolog/wechat/user/mobileRepoterDetail?reporterId="+reporterId+"&wxId="+wxId;
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
}
