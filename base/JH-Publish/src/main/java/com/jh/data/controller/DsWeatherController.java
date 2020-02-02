package com.jh.data.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.data.entity.DsWeather;
import com.jh.data.model.DsWeatherParam;
import com.jh.data.service.IDsWeatherService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 天气数据管理发布
 * @version <1> 2018-09-29 huxiaoqiang: Created.
 */
@RestController
@RequestMapping("/dsWeather")
public class DsWeatherController extends BaseController {

    @Autowired
    private IDsWeatherService dsWeatherService;

    @ApiOperation(value = "获取天气明细列表",notes = "获取天气明细列表")
    @ApiImplicitParam(name = "dsWeatherParam",value = "天气明细查询参数",required = false,dataType = "DsWeatherParam")
    @PostMapping("/findByPage")
    public PageInfo<DsWeather> findByPage(DsWeatherParam dsWeatherParam, HttpServletRequest request){
        PageInfo<DsWeather> pages = dsWeatherService.findByPage(dsWeatherParam);
        return pages;
    }

    @ApiOperation(value = "根据id获取单条天气明细",notes = "根据id获取单条天气明细")
    @ApiImplicitParam(name = "id",value = "天气id",required = true,paramType = "query",dataType = "Integer")
    @PostMapping("/findById")
    public ResultMessage findById(HttpServletRequest request,@RequestParam Integer id){
        return dsWeatherService.findById(id);
    }


    @ApiOperation(value = "根据id删除单条天气数据",notes = "根据id删除单条天气数据")
    @ApiImplicitParam(name = "id",value = "天气id",required = true,dataType = "Integer")
    @PostMapping("/delete")
    public ResultMessage delete(@RequestParam Integer id){
        DsWeatherParam dsWeatherParam = new DsWeatherParam();
        dsWeatherParam.setId(id);
        dsWeatherParam.setDelFlag("0");
        return dsWeatherService.updateDsWeather(dsWeatherParam);
    }


    @ApiOperation(value = "天气明细发布或撤销",notes = "发布或撤销天气明细")
    @ApiImplicitParam(name = "e",value = "天气明细实体",required = true,dataType = "DsWeatherParam")
    @PostMapping("/publish")
    public ResultMessage publish(HttpServletRequest request, @RequestBody DsWeatherParam dsWeatherParam){

        dsWeatherParam.setPublisher(getCurrentAccountId());
        dsWeatherParam.setPublisherName(getCurrentNickName());

        return dsWeatherService.publish(dsWeatherParam);
    }

    /**
     * 天气明细修改
     * @param request
     * @param dsWeatherParam
     * @return
     * @version <1> 2019-04-18 cxw： Created.
     */
    @ApiOperation(value = "天气明细修改",notes = "修改天气明细")
    @ApiImplicitParam(name = "dsWeatherParam",value = "天气明细实体",required = true,dataType = "DsWeatherParam")
    @PostMapping("/edit")
    public ResultMessage edit(HttpServletRequest request, @RequestBody DsWeatherParam dsWeatherParam){
        dsWeatherParam.setModifierName(getCurrentNickName());
        dsWeatherParam.setModifier(getCurrentAccountId());
        return dsWeatherService.updateDsWeather(dsWeatherParam);
    }


}
