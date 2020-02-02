package com.jh.show.agric.controller;

import com.jh.show.wx.service.IWxService;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * description:简报查询服务:
 * 1.获取最新简报
 * @version <1> 2018-08-10 cxw: Created.
 */
@Api(value = "微信简报查询服务",description="微信简报查询服务")
@RestController
@RequestMapping("/briefReport")
public class BriefReportController {
    @Autowired
    private IWxService wxService;

    /**
     * 获取微信用户注册状态和订阅信息
     * @param wxId 微信编号
     * @return 返回用户中心相关页面
     * @version <1> 2018-05-06 lxy： Created.
     */
    @ApiOperation(value = "获取微信用户注册状态和订阅信息",notes = "获取微信用户注册状态和订阅信息")
    @ApiImplicitParam(name = "wxId",value = "微信ID",required = true, dataType = "String" ,paramType="query",defaultValue="odICI0wnpOeuZkGzr5PLOxtrIsSM")
    @GetMapping("/mobileBreifRepoterItems")
    public ModelAndView mobileBreifRepoterItems(@RequestParam String wxId){
        ModelAndView model = new ModelAndView();
        String webPage= PropertyUtil.getPropertiesForConfig("weixin.webpage.url");
        model.addObject("baseUrl",webPage);//设置根目录路径
        ResultMessage resultMessage = wxService.queryReporterByRegionAndCrops(wxId,null);//获取微信用户注册状态和订阅信息
        if(resultMessage.isFlag()){
            List<Map<String,Object>> reporters = (List<Map<String, Object>>) resultMessage.getData();
            model.addObject("reporters",reporters);//返回前端
            model.addObject("wxId",wxId);//将微信号也放入到页面中。
        }
        model.setViewName("redirect:"+webPage+"/wechatReporterItems.html");
        return model;
    }
}
