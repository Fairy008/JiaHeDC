package com.jh.show.wx.controller;

import com.github.sd4324530.fastweixin.api.MenuAPI;
import com.github.sd4324530.fastweixin.api.OauthAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.entity.Menu;
import com.github.sd4324530.fastweixin.api.entity.MenuButton;
import com.github.sd4324530.fastweixin.api.enums.MenuType;
import com.github.sd4324530.fastweixin.api.enums.OauthScope;
import com.github.sd4324530.fastweixin.api.enums.ResultType;
import com.github.sd4324530.fastweixin.handle.MessageHandle;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.req.BaseEvent;
import com.github.sd4324530.fastweixin.message.req.BaseReqMsg;
import com.github.sd4324530.fastweixin.message.req.MenuEvent;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import com.github.sd4324530.fastweixin.servlet.WeixinControllerSupport;
import com.jh.biz.feign.IBriefService;
import com.jh.show.wx.service.impl.WxServiceImpl;
import com.jh.show.wx.util.ReplyMessageHandle;
import com.jh.show.wx.util.ReporterEventHandle;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 接入微信公众号 入口
 * @version <1> 2018-05-06 lxy： Created.
 */
@RestController
@RequestMapping("/nolog/weixin/reporter")
public class WeChatReporterController extends WeixinControllerSupport {
    private static final Logger log = LoggerFactory.getLogger(WeixinControllerSupport.class);

    @Autowired
    private WxServiceImpl wxService;

    @Autowired
    private IBriefService briefService;

    /**
     * 获取微信App编号
     * @return 返回AppId
     * @version <1> 2018-05-06 lxy： Created.
     */
    @Override
    protected String getAppId() {
        return PropertyUtil.getPropertiesForConfig("weixin.appId","config.properties");
    }

    /**
     * 获取微信 accessToken
     * @return 微信 accessToken
     * @version <1> 2018-05-06 lxy： Created.
     */
    @Override
    protected String getToken() {
        return PropertyUtil.getPropertiesForConfig("weixin.token","config.properties");
    }

    /**
     * 获取微信开发编码
     * @return 微信开发编码
     * @version <1> 2018-05-06 lxy： Created.
     */
    protected String getAppsec(){
        return PropertyUtil.getPropertiesForConfig("weixin.appsec","config.properties");
    }

    /**
     * 设置AESKey，设置加密的密钥
     * @return
     */
    @Override
    protected String getAESKey() {
        return super.getAESKey();
    }

    /**
     *  处理用户关注微信后触发地事件,
     * @param event 关注事件
     * @return 返回给用户信息
     * @version <1> 2018-05-06 lxy： Created.
     */
    @Override
    protected BaseMsg handleSubscribe(BaseEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("谢谢您关注珈和农情遥感公众号，您可以获得最新农情资讯！"+System.getProperty("line.separator"));
        stringBuilder.append("回复区域+空格+作物，就可以获得最新的农情简报。");
        stringBuilder.append("(如输入\"湖北 冬小麦\" 发送公众平台，您会及时得到简报信息！)"+System.getProperty("line.separator"));
        stringBuilder.append("您还可以在会员服务中，注册平台账户，订制区域、农作物来获取您感兴趣的简报信息！");
        return new TextMsg(stringBuilder.toString());
    }

    /**
     *  处理用户取消关注微信后触发地事件,
     * @param event 关注事件
     * @return 返回给用户信息
     * @version <1> 2018-05-06 lxy： Created.
     */
    @Override
    protected BaseMsg handleUnsubscribe(BaseEvent event) {
        wxService.removeUserRelate(event.getFromUserName());
        return new TextMsg("虽然不舍，望您再次关注我们公众号，给您带来更新，更实用的农情咨询！再次谢谢！");
    }

//    /**
//     * 添加消息处理
//     * @return 返回消息
//     * @version <1> 2018-05-06 lxy： Created.
//     */
//    @Override
//    protected List<MessageHandle> initMessageHandles() {
//        List<MessageHandle> handles = new ArrayList<MessageHandle>();
//        handles.add(new ReplyMessageHandle(wxService));
//        return handles;
//    }

    /**
     * 添加消息处理
     * @return 返回消息
     * @version <1> 2018-05-06 lxy： Created.
     */
//    @Override
//    protected BaseMsg initMessageHandles(TextReqMsg message) {
//
////        ReporterEventHandle eventHandle = new ReporterEventHandle(wxService);
////        eventHandle.add
////        return eventHandle.handleReporter(wechatId);
//        ReplyMessageHandle handler = new ReplyMessageHandle(wxService);
//        return handler.handle(message);
//    }

    /**
     * 处理文本消息，有需要时子类重写
     *
     * @param msg 请求消息对象
     * @return 响应消息对象
     */
    protected BaseMsg handleTextMsg(TextReqMsg msg) {
        ReplyMessageHandle handler = new ReplyMessageHandle(wxService);
        return handler.handle(msg);
    }


    /**
     * 处理菜单点击事件
     * @param event 菜单点击事件
     * @return 返回信息
     * @version <1> 2018-05-06 lxy： Created.
     */
    @Override
    protected BaseMsg handleMenuClickEvent(MenuEvent event) {
        //处理菜单点击事件，返回消息
        ReporterEventHandle eventHandle = new ReporterEventHandle(wxService);
        String wechatId = event.getFromUserName();
        String key = event.getEventKey();
        switch (key.toLowerCase()) {
            case "reporter":
                return eventHandle.handleReporter(wechatId);
            case "contact":
                return eventHandle.handleContact();
            default:
                return new TextMsg("不识别的菜单命令");
        }
    }

    /**
     * 创建菜单
     * @return 返回创建菜单的结果
     * @version <1> Created by lxy on 2018/4/24.
     */
    @GetMapping("/create-menu")
    public String createMenu(){

        //获取对应的accessToken信息
        ApiConfig config = new ApiConfig(this.getAppId(),this.getAppsec());
        OauthAPI oauthAPI = new OauthAPI(config);
        /**
         *  准备一级主菜单 按钮
         */
        MenuButton reporterMenu = new MenuButton();
        reporterMenu.setType(MenuType.CLICK); //可点击的菜单
        reporterMenu.setKey("reporter");
        reporterMenu.setName("农情资讯");

        //会员服务
        MenuButton memberMenu = new MenuButton();
        memberMenu.setType(MenuType.CLICK); //可点击的菜单
        memberMenu.setKey("member");
        memberMenu.setName("会员服务");

        //会员服务--个人中心
        MenuButton userCenterMenu = new MenuButton();
        userCenterMenu.setType(MenuType.VIEW);//链接菜单
        userCenterMenu.setKey("userCenter");
        userCenterMenu.setName("个人中心");
        String userCenterUrl = PropertyUtil.getPropertiesForConfig("weixin.user.center.url");//个人中心
        userCenterMenu.setUrl(oauthAPI.getOauthPageUrl(userCenterUrl,OauthScope.SNSAPI_BASE,"state"));//获取code,
        memberMenu.setSubButton(Arrays.asList(userCenterMenu));//添加会员子菜单

        //联系我们
        MenuButton contactUsMenu = new MenuButton();
        contactUsMenu.setType(MenuType.CLICK); //可点击的菜单
        contactUsMenu.setKey("contact");
        contactUsMenu.setName("联系我们");

        //获取创建菜单的API
        MenuAPI menuAPI = new MenuAPI(config);
        menuAPI.deleteMenu();//先删除菜单
        //定义菜单
        Menu menu = new Menu();
        menu.setButton(Arrays.asList(reporterMenu,memberMenu,contactUsMenu));//添加所有得菜单
        ResultType resultType = menuAPI.createMenu(menu);//创建菜单
        return resultType.toString();
    }

    /**
     * 根据ID查询简报详情
     * @param  reportId  type(微信/pc/文字/图表)
     * @return ResultMessage :
     * @version <1> 2018-07-27 wl:Created.
     */
    @ApiOperation(value = "根据ID查询简报详情",notes = "根据ID查询简报详情")
    @ApiImplicitParam(name = "reportId",value = "简报id",required = true,dataType = "Long")
    @GetMapping("findBriefReportByType")
    public ResultMessage findBriefReportByType(Long reportId, Integer type, HttpServletResponse response){
        ResultMessage result = briefService.findBriefReportByType(reportId,type);
        return result;
    }
}
