package com.jh.show.wx.controller;

import com.jh.show.wx.model.WXRegionCrop;
import com.jh.show.wx.model.WXRelateAccount;
import com.jh.show.wx.model.WXSentReporter;
import com.jh.show.wx.service.IWxService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 微信接口
 * @version <1> 2018-05-08 xzg：Created
 * @version <2> 2018-05-16 lxy：Updated 添加保存已发送的微信消息
 */
@Api(value = "对接微信相关接口")
@RestController
@RequestMapping("/nolog/wechat")
public class WechatController {

    @Autowired
    private IWxService weChatService;

//    /**
//     * 微信用户注册
//     * 默认密码123456
//     * @param relateAccount  注册手机号  微信号
//     * @return
//     */
//    @ApiOperation(value = "微信用户注册")
//    @RequestMapping(value = "/regist",method = RequestMethod.POST)
//    public ResultMessage regionUserByWechat(@RequestBody WXRelateAccount relateAccount){
//        return iwxService.addWechatUser(relateAccount);
//    }

    /**
     * 微信用户订阅简报
     * @param regionCrop   区域id  作物id   订阅手机号
     * @return
     */
    @ApiOperation(value = "微信用户订阅简报")
    @RequestMapping(value = "/subscribe",method = RequestMethod.POST)
    public ResultMessage subscribeBriefing(@RequestBody WXRegionCrop regionCrop){
        return weChatService.addSubscribeBriefing(regionCrop);
    }

    /**
     * 根据微信编号获取已发送的简报消息
     * @param wxIds 多个微信编号
     * @return 返回已发送的简报消息
     * @version <1> 2018-05-16 lxy：Created
     */
    @ApiOperation(value = "根据微信编号获取已发送的简报消息")
    @RequestMapping(value = "/findWxSentReporterByWxIds",method = RequestMethod.POST)
    public ResultMessage findWxSentReporterByWxIds(@RequestBody List<String> wxIds){
        return weChatService.findWxSentReporterByWxIds(wxIds);
    }

    /**
     * 保存已发送的微信消息
     * @param reporters 要保存的消息
     * @return 返回操作的结果
     * @version <1> 2018-05-16 lxy：Created
     */
    @ApiOperation(value = "保存已发送的微信消息")
    @RequestMapping(value = "/saveWxSentReporter",method = RequestMethod.POST)
    public ResultMessage saveWxSentReporter(@RequestBody List<WXSentReporter> reporters){
        return weChatService.saveWxSentReporter(reporters);
    }
}
