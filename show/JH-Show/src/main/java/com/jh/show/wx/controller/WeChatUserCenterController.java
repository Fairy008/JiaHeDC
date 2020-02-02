package com.jh.show.wx.controller;


import com.jh.show.wx.service.IWxService;
import com.jh.show.wx.util.WeChatUtils;
import com.jh.show.wx.vo.BriefReporterVO;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 微信用户中心接口
 * @version <1> 2018-05-06 lxy： Created.
 */
@RestController
@RequestMapping("/nolog/wechat/user")
public class WeChatUserCenterController {

    @Autowired
    private IWxService iwxService;

    /**
     * 进入用户中心
     * @param code 腾讯验证码
     * @return 返回用户中心相关页面
     * @version <1> 2018-05-06 lxy： Created.
     */
    @RequestMapping(value="/userCenter",method = RequestMethod.GET)
    public ModelAndView userCenter(@RequestParam String code, ModelAndView model, HttpSession session){
        String wechatId=null;
        Object wechatIdObj = session.getAttribute("wechatId");//从Session中获取wechatId
        if(wechatIdObj == null){
            wechatId = WeChatUtils.getWeChatId(code);//获取用户微信openId
            session.setAttribute("wechatId",wechatId);//放入session中
        }else{
            wechatId = (String) wechatIdObj;//从Session中获取wechatId,避免重复到腾讯服务器中获取code,否注code重复使用会报错.
        }

        ResultMessage resultMessage = iwxService.getWeChatUserRegisterStatus(wechatId);//获取微信用户注册状态和订阅信息
        String webPage= PropertyUtil.getPropertiesForConfig("weixin.webpage.url");
        model.addObject("baseUrl",webPage);//设置根目录路径
        model.addObject("wechatId",wechatId);//设置微信编号
        String messageCode = resultMessage.getMsg();//消息编码
        if("notRegist".equals(messageCode)){//未注册
            model.setViewName("redirect:"+webPage+"/wechatRegister.html");
            return model;
        }else{//已经注册.
            int version = new Random().nextInt();//版本号
            model.addObject("version",version);
            model.addObject("result",resultMessage);
            model.setViewName("redirect:"+webPage+"/wechatSubscribe.html");
            return model;
        }
    }

    /**
     * 注册成功或者订阅成功,都经过此请求,跳转到订阅页面
     * @param wechatId 用户微信编号
     * @return 返回用户中心相关页面
     */
    @RequestMapping(value = "/redirect",method = RequestMethod.GET)
    public ModelAndView redirect(@RequestParam String wechatId,@RequestParam(required = false) String subscribeSuccess,ModelAndView model){
        String webPage= PropertyUtil.getPropertiesForConfig("weixin.webpage.url");
        model.addObject("baseUrl",webPage);//设置根目录路径
        int version = new Random().nextInt();//版本号
        model.addObject("version",version);//设置版本号
        if("true".equals(subscribeSuccess)){
            model.setViewName("redirect:"+webPage+"/subscribeSuccess.html");
            return model;
        }
        ResultMessage resultMessage = iwxService.getWeChatUserRegisterStatus(wechatId);//获取微信用户注册状态和订阅信息
        model.addObject("wechatId",wechatId);//设置微信编号
        String messageCode = resultMessage.getMsg();//消息编码
        if("notRegist".equals(messageCode)){//未注册
            model.setViewName("redirect:"+webPage+"/wechatRegister.html");
            return model;
        }else{//已经注册.
            model.addObject("result",resultMessage);
            model.setViewName("redirect:"+webPage+"/wechatSubscribe.html");
            return model;
        }
    }


    /**
     * 从模板消息进入到手机简报的详细页面
     * @param reporterId 简报编号
     * @return 返回用户中心相关页面
     * @version <1> 2018-05-06 lxy： Created.
     */
    @RequestMapping(value="/mobileRepoterDetail",method = RequestMethod.GET)
    public ModelAndView mobileRepoterDetail(@RequestParam Long reporterId,@RequestParam String wxId, ModelAndView model){
        String webPage= PropertyUtil.getPropertiesForConfig("weixin.webpage.url");
        model.addObject("baseUrl",webPage);//设置根目录路径
        model.addObject("reporterId",reporterId);
        model.addObject("wxId",wxId);
        ResultMessage resultMessage = iwxService.findBriefReporterByReporterId(reporterId,wxId);//获取微信用户注册状态和订阅信息
        if(resultMessage.isFlag()){
            BriefReporterVO reporter = (BriefReporterVO) resultMessage.getData();//获取当前简报信息
            String mobileReporterContent = reporter.getReporterMobileContent();//手机简报内容
            if(!StringUtils.isEmpty(mobileReporterContent)){
                mobileReporterContent = mobileReporterContent.replaceAll("(\\n\\r|\\n|\\r)","<br>");//将换行符替换成html的<br>,便于网页展示
                reporter.setReporterMobileContent(mobileReporterContent);//手机内容
            }
            model.addObject("reporter",reporter);//设置简报
        }
        model.setViewName("redirect:"+webPage+"/wechatReporter.html");
        return model;
    }

    /**
     * 获取微信用户注册状态和订阅信息
     * @param wxId 微信编号
     * @return 返回用户中心相关页面
     * @version <1> 2018-05-06 lxy： Created.
     */
    @RequestMapping(value="/mobileRepoterItems",method = RequestMethod.GET)
    public ModelAndView mobileRepoterItems(@RequestParam String wxId, ModelAndView model){
        String webPage= PropertyUtil.getPropertiesForConfig("weixin.webpage.url");
        model.addObject("baseUrl",webPage);//设置根目录路径
        ResultMessage resultMessage = iwxService.queryReporterByRegionAndCrops(wxId,null);//获取微信用户注册状态和订阅信息
        if(resultMessage.isFlag()){
            List<Map<String,Object>> reporters = (List<Map<String, Object>>) resultMessage.getData();
            model.addObject("reporters",reporters);//返回前端
            model.addObject("wxId",wxId);//将微信号也放入到页面中。
        }
        model.setViewName("redirect:"+webPage+"/wechatReporterItems.html");
        return model;
    }

    /**
     * 获取微信用户相关信息
     * @param wechatId 微信id
     * @return
     */
    @RequestMapping(value="userInfo",method = RequestMethod.GET)
    public ResultMessage userInfo(@RequestParam(value="wechatId") String wechatId){
        ResultMessage resultMessage = iwxService.getWeChatUserRegisterStatus(wechatId);//获取微信用户注册状态和订阅信息
        return resultMessage;
    }

    /**
     * 获取该微信用户订阅的简报列表
     * @param wechatId 微信id
     * @return
     */
    @RequestMapping(value="queryReportItems",method = RequestMethod.GET)
    public ResultMessage queryReportItems(@RequestParam(value="wechatId") String wechatId){
        ResultMessage resultMessage = iwxService.queryReporterByRegionAndCrops(wechatId,null);//获取微信用户注册状态和订阅信息
        List<Map<String, Object>> briefMapList= (List<Map<String,Object>>)resultMessage.getData();//最新简报信息
        List<BriefReporterVO> briefReporterVOList = new ArrayList<BriefReporterVO>();
        for(Map<String,Object> briefMap : briefMapList){
            BriefReporterVO briefReporterVO = WeChatUtils.wrapperBriefReporterFromMap(briefMap);//包装成BriefReporterVO对象
            briefReporterVOList.add(briefReporterVO);
        }
        Map<String,Object> userAndBriefMap = new HashMap<String,Object>();
        userAndBriefMap.put("reporters",briefReporterVOList);//设置当前微信用户
        return ResultMessage.success(userAndBriefMap);
    }


    /**
     * 根据简报id和微信id查询简报详情
     * @param reporterId 简报id
     * @param wxId 微信id
     * @return
     */
    @RequestMapping(value="queryMobileReportInfo",method = RequestMethod.GET)
    public ResultMessage queryMobileReportInfo(@RequestParam(value="reporterId") Long reporterId, @RequestParam(value="wxId") String wxId){
        ResultMessage resultMessage = iwxService.findBriefReporterByReporterId(reporterId,wxId);//获取微信用户注册状态和订阅信息
        return resultMessage;
    }



}
