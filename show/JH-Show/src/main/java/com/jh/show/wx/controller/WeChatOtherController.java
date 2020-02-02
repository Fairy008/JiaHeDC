package com.jh.show.wx.controller;


import com.github.sd4324530.fastweixin.api.OauthAPI;
import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.response.GetUsersResponse;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.jh.show.wx.service.impl.WxServiceImpl;
import com.jh.show.wx.util.WeChatUtils;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信其他接口的操作类 ,后面会删除
 * @version <1> 2018-05-06 lxy： Created.
 */
@RestController
@RequestMapping("/weixin/other")
public class WeChatOtherController {

    @Autowired
    private WxServiceImpl businessService;

    /**
     * 根据腾讯微信的验证码，获取用户的openId，就是微信编号
     * @param code 腾讯验证码
     * @return 返回 微信编号
     */
    @RequestMapping(value="/getWeChartId",method = RequestMethod.POST)
    public ResultMessage getWeChartId(@RequestParam String code){
        String appId= PropertyUtil.getPropertiesForConfig("weixin.appId");
        String secret = PropertyUtil.getPropertiesForConfig("weixin.appsec");
        ApiConfig apiConfig = new ApiConfig(appId,secret);//微信基础操作API
        OauthAPI oauthAPI = new OauthAPI(apiConfig); //微信网页获取用户信息授权信息
        OauthGetTokenResponse tokenResponse = oauthAPI.getToken(code);//获取用户openId
        String wechatId = tokenResponse.getOpenid();//微信编号
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setData(wechatId);
        if(StringUtils.isEmpty(wechatId)){
            resultMessage.setFlag(false);
        }
        resultMessage.setFlag(true);

        return resultMessage;
    }

    /**
     * 发送模板消息
     * @return 返回操作结果
     */
    @RequestMapping(value="/sendTemplateMessage",method = RequestMethod.GET)
    public ResultMessage sendTemplateMessage(){
        ResultMessage resultMsg = businessService.sendTemplateMessage();//获取最新简报
        return resultMsg;
    }

    /**
     * 获取所有关注的微信用户
     * @return 所有关注的微信用户
     */
    @RequestMapping(value="/getAllWxUsers",method = RequestMethod.GET)
    public ResultMessage getAllWxUsers(){
        UserAPI userAPI = WeChatUtils.getUserAPI();
        GetUsersResponse response =  userAPI.getUsers(null);
        return ResultMessage.success(response);
    }
}
