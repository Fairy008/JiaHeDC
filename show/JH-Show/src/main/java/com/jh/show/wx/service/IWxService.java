package com.jh.show.wx.service;


import com.jh.show.wx.model.WXRegionCrop;
import com.jh.show.wx.model.WXRelateAccount;
import com.jh.show.wx.model.WXSentReporter;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 *  业务逻辑处理,调用微服务接口
 * @version <1> 2018-05-06 lxy： Created.
 */

public interface IWxService {
    /**
     *
     * @param wechatID 微信用户编号
     * @param keyWord 搜索关键字
     * @return 返回最新的简报信息
     * @version <1> 2018-05-06 lxy： Created.
     */
    public ResultMessage queryReporterByRegionAndCrops(String wechatID, String keyWord);

    /**
     * 获取用户的微信号的注册信息，是否注册和订阅简报信息
     * @param wxId 用户微信openId
     * @return 返回微信号是否注册和订阅简报信息。
     * @version <1> 2018-05-06 lxy： Created.
     */
    public ResultMessage getWeChatUserRegisterStatus(String wxId);

    /**
     * 根据简报编号查询对应的简报
     * @param reporterId 简报编号
     * @param wechatId 微信号
     * @return 返回最新简报
     * @version <1> 2018-05-06 lxy： Created.
     */
    public ResultMessage findBriefReporterByReporterId(Long reporterId, String wechatId);

    /**
     * 定时查询所有注册微信用户订阅的最新简报
     * @return 返回最新简报
     * @version <1> 2018-05-06 lxy： Created.
     */
    public ResultMessage findScheduleBriefReporter();

    /**
     * 发送微信简报模板消息
     * @return 操作结果
     * @version <1> 2018-05-16 lxy： Created.
     */
    public ResultMessage sendTemplateMessage();

//    /**
//     * 通过微信注册
//     * @return
//     * @version <1> 2018-05-08 xzg： Created.
//     */
//    ResultMessage addWechatUser(WXRelateAccount relateAccount);

    /**
     * 保存微信用户订阅的区域、作物
     * @return 返回操作结果
     * @version <1> 2018-05-08 xzg： Created.
     */
    ResultMessage addSubscribeBriefing(WXRegionCrop regionCrop);

    /**
     * 保存微信用户已经推送的模板消息。
     * @param reporters 记录已发送的微信消息实体
     * @return 返回操作结果
     * @version <1> 2018-05-16 lxy：Created
     */
    ResultMessage saveWxSentReporter(List<WXSentReporter> reporters);

    /**
     * 根据微信编号查询 微信用户已经推送的模板消息。
     * @param wxIds 多个微信编号
     * @return 返回微信用户已经推送的模板消息。
     * @version <1> 2018-05-16 lxy：Created
     */
    ResultMessage findWxSentReporterByWxIds(List<String> wxIds);


}
