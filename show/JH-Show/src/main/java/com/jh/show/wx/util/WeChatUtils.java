package com.jh.show.wx.util;

import com.jh.show.wx.vo.BriefReporterVO;
import com.github.sd4324530.fastweixin.api.*;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.jh.util.PropertyUtil;

import java.util.List;
import java.util.Map;

/**
 * 微信相关api的调用
 * @version <1> 2018-05-06 lxy： Created.
 */
public class WeChatUtils {
    public static String Msg_Wechat_Not_BriefingOrKeyword = "用户未添加关键字或订阅简报";
    public static String Msg_Wechat_FindBriefing_Fail = "简报查询失败";

    /**
     * 根据腾讯的验证码code，获取用户的openId
     * @param code 腾讯验证码
     * @return 获取用户的openId
     * @version <1> 2018-05-06 lxy： Created.
     */
    public static String getWeChatId(String code){
        String appId= PropertyUtil.getPropertiesForConfig("weixin.appId");
        String secret = PropertyUtil.getPropertiesForConfig("weixin.appsec");
        ApiConfig apiConfig = new ApiConfig(appId,secret);//微信基础操作API
        OauthAPI oauthAPI = new OauthAPI(apiConfig); //微信网页获取用户信息授权信息
        OauthGetTokenResponse tokenResponse = oauthAPI.getToken(code);//获取用户openId
        String wechatId = tokenResponse.getOpenid();//微信编号
        return wechatId;
    }

    /**
     * 将最新简报作简要概览总结
     * @param briefMapList 最新简报
     * @return 返回概览总结
     * @version <1> 2018-05-19 lxy： Created.
     */
    public static StringBuilder wrapBriefReporterSummary(List<Map<String, Object>> briefMapList) {
        StringBuilder summary = new StringBuilder("您订阅的最新农情简报：");//总结
        boolean isFirst = true;//第一条记录
        for(Map<String,Object> briefMap : briefMapList){
            BriefReporterVO briefReporterVO = WeChatUtils.wrapperBriefReporterFromMap(briefMap);//包装成BriefReporterVO对象
            String regionName = briefReporterVO.getRegionName();//区域名称
            String cropName = briefReporterVO.getCropName();//作物名称
            if(isFirst){
                summary.append("区域（").append(regionName).append(")，");
                summary.append("作物（").append(cropName);
                isFirst = false;//已经读了第一条，isFirst 就改为 false
            }else{
                summary.append("、").append(cropName);
            }
        }
        summary.append("）有最新的").append(briefMapList.size()).append("条农情简报信息，请您点击查阅！");
        return summary;
    }

    /**
     * @param map 包含BriefReporter数据的Map形式
     * @return 转化成BriefReporterVO
     * @version <1> 2018-05-17 lxy： Created.
     */
    public static BriefReporterVO wrapperBriefReporter(Map<String,Object> map) {
        BriefReporterVO reporterVO = new BriefReporterVO();
        reporterVO.setReporterId(map.get("reporterId")==null?null:new Long(map.get("reporterId").toString()));//简报标号
        reporterVO.setRegionId(map.get("regionId")==null?null:new Long(map.get("regionId").toString()));//区域编号
        reporterVO.setCropsId(map.get("cropsId")==null?null:new Integer(map.get("cropsId").toString()));//作物编号
        reporterVO.setReportDataTimeEnd(map.get("reportDataTimeEnd")==null?null:map.get("reportDataTimeEnd").toString());//简报数据时间结束
        reporterVO.setReporterMobileContent(map.get("reporterMobileContent")==null?null:map.get("reporterMobileContent").toString());//简报内容
        reporterVO.setWxId(map.get("wxId")==null?null:map.get("wxId").toString());//微信号
        reporterVO.setRegionName(map.get("regionName")==null?null:map.get("regionName").toString());//区域名称
        reporterVO.setCropName(map.get("cropName")==null?null:map.get("cropName").toString());//作物名称
        reporterVO.setReporterName(map.get("reporterName") == null?null:map.get("reporterName").toString());//简报名称
        return reporterVO;
    }

    /**
     * @param map 包含BriefReporter数据的Map形式
     * @return 转化成BriefReporterVO
     * @version <1> 2018-05-17 lxy： Created.
     */
    public static BriefReporterVO wrapperBriefReporterFromMap(Map<String,Object> map) {
        BriefReporterVO reporterVO = new BriefReporterVO();
        reporterVO.setReporterId(map.get("reporterid")==null?null:new Long(map.get("reporterid").toString()));//简报标号
        reporterVO.setRegionId(map.get("regionid")==null?null:new Long(map.get("regionid").toString()));//区域编号
        reporterVO.setCropsId(map.get("cropsid")==null?null:new Integer(map.get("cropsid").toString()));//作物编号
        reporterVO.setReportDataTimeEnd(map.get("reportdatatimeend")==null?null:map.get("reportdatatimeend").toString());//简报数据时间结束
        reporterVO.setReporterMobileContent(map.get("reportermobilecontent")==null?null:map.get("reportermobilecontent").toString());//简报内容
        reporterVO.setWxId(map.get("wxid")==null?null:map.get("wxid").toString());//微信号
        reporterVO.setRegionName(map.get("regionname")==null?null:map.get("regionname").toString());//区域名称
        reporterVO.setCropName(map.get("cropname")==null?null:map.get("cropname").toString());//作物名称
        reporterVO.setReporterName(map.get("reportername") == null?null:map.get("reportername").toString());//简报名称
        return reporterVO;
    }

    /**
     * 获得发送模板消息TemplateMsgAPI
     * @return 返回模板消息
     * @version <1> 2018-05-17 lxy： Created.
     */
    public static TemplateMsgAPI getTemplateMessageAPI(){
        String appId= PropertyUtil.getPropertiesForConfig("weixin.appId");
        String secret = PropertyUtil.getPropertiesForConfig("weixin.appsec");
        ApiConfig apiConfig = new ApiConfig(appId,secret);//微信基础操作API
        TemplateMsgAPI templateMsgAPI = new TemplateMsgAPI(apiConfig); //微信模板消息接口
        return templateMsgAPI;//返回模板消息
    }

    /**
     * 获得发送模板消息TemplateMsgAPI
     * @return 返回客户消息API
     * @version <1> 2018-05-17 lxy： Created.
     */
    public static CustomAPI getCustomAPI(){
        String appId= PropertyUtil.getPropertiesForConfig("weixin.appId");
        String secret = PropertyUtil.getPropertiesForConfig("weixin.appsec");
        ApiConfig apiConfig = new ApiConfig(appId,secret);//微信基础操作API
        CustomAPI customAPI = new CustomAPI(apiConfig); //微信模板消息接口
        return customAPI;//返回客户消息API
    }

    /**
     * 获得发送消息MessageAPI
     * @return 返回消息API
     * @version <1> 2018-05-17 lxy： Created.
     */
    public static MessageAPI getMessageAPI(){
        String appId= PropertyUtil.getPropertiesForConfig("weixin.appId");
        String secret = PropertyUtil.getPropertiesForConfig("weixin.appsec");
        ApiConfig apiConfig = new ApiConfig(appId,secret);//微信基础操作API
        MessageAPI messageAPI = new MessageAPI(apiConfig);//微信发送消息接口
        return messageAPI;
    }

    /**
     * 获得UserAPI
     * @return UserAPI
     * @version <1> 2018-05-17 lxy： Created.
     */
    public static UserAPI getUserAPI(){
        String appId= PropertyUtil.getPropertiesForConfig("weixin.appId");
        String secret = PropertyUtil.getPropertiesForConfig("weixin.appsec");
        ApiConfig apiConfig = new ApiConfig(appId,secret);//微信基础操作API
        UserAPI userAPI = new UserAPI(apiConfig);//用户操作API
        return userAPI;
    }


    /**
     * 将最新简报作简要概览总结
     * @param briefMapList 最新简报
     * @return 返回概览总结
     * @version <1> 2018-05-19 lxy： Created.
     */
    public static StringBuilder wrapSingleBriefReporterSummary(List<Map<String, Object>> briefMapList) {
        StringBuilder summary = new StringBuilder("");//总结
        boolean isFirst = true;//第一条记录
        for(Map<String,Object> briefMap : briefMapList){
            BriefReporterVO briefReporterVO = WeChatUtils.wrapperBriefReporterFromMap(briefMap);//包装成BriefReporterVO对象
            String regionName = briefReporterVO.getRegionName();//区域名称
            String cropName = briefReporterVO.getCropName();//作物名称
            if(isFirst){
                summary.append("区域（").append(regionName).append(")，");
                summary.append("作物（").append(cropName);
                isFirst = false;//已经读了第一条，isFirst 就改为 false
            }else{
                summary.append("、").append(cropName);
            }
        }
        summary.append("）有最新的").append(briefMapList.size()).append("条农情简报信息，请您点击查阅！");
        return summary;
    }


}
