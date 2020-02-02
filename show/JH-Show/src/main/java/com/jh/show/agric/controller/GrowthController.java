package com.jh.show.agric.controller;


import com.jh.biz.feign.IGrowthService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 长势数据集服务:
 * 1. 根据区域、日期段查询区域作物当年与上一年的长势数据
 * 2. 根据区域、日期段查询近三年及近十年的长势数据（同比分页查询）
 * @version <1> 2018-04-27 cxw: Created.
 */
@Api(value = "Growth Service Interface",description = "长势数据接口")
@RestController
@RequestMapping("/growth")
public class GrowthController {

    @Autowired
    private IGrowthService growthService;


//    @ApiOperation(value="查询长势指定日期环比数据",notes="根据区域、日期段查询区域作物当年与上一年的长势数据" )
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "regionId",value = "区域ID",required = true,dataType = "Long",paramType = "query",defaultValue = "3100000000"),
//            @ApiImplicitParam(name = "cropId",value = "作物ID",required = true,dataType = "int",paramType = "query",defaultValue = "501"),
//            @ApiImplicitParam(name = "startDate",value = "开始日期",required = true,dataType = "String",paramType = "query",defaultValue = "2018-01-01"),
//            @ApiImplicitParam(name = "endDate",value = "结束日期",required = true,dataType = "String",paramType = "query",defaultValue = "2018-12-31"),
//            @ApiImplicitParam(name = "accuracyId",value = "精度id",required = true,dataType = "int",paramType = "query",defaultValue = "4011")
//    })
//    @GetMapping("/GrowthForPrevious")
//    public ResultMessage queryGrowthList(Long regionId, Integer cropId, String startDate, String endDate, Integer accuracyId){
//        return growthService.queryGrowthList(regionId,cropId,startDate,endDate,accuracyId);
//
//    }
//
//
//
//    @ApiOperation(value="查询长势指定日期三年同比数据",notes="根据区域、日期段查询近三年及近十年的长势数据" )
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "regionId",value = "区域ID",required = true,dataType = "Long",paramType = "query",defaultValue = "3100000000"),
//            @ApiImplicitParam(name = "cropId",value = "作物ID",required = true,dataType = "int",paramType = "query",defaultValue = "501"),
//            @ApiImplicitParam(name = "startDate",value = "开始日期",required = true,dataType = "String",paramType = "query",defaultValue = "2018-01-01"),
//            @ApiImplicitParam(name = "endDate",value = "结束日期",required = true,dataType = "String",paramType = "query",defaultValue = "2018-12-31"),
//            @ApiImplicitParam(name = "accuracyId",value = "精度id",required = true,dataType = "int",paramType = "query",defaultValue = "4011")
//    })
//    @GetMapping("/GrowthForThree")
//    public ResultMessage queryGrowthThreeList(Long regionId, Integer cropId,String startDate, String endDate,Integer accuracyId){
//        return growthService.queryGrowthThreeList(regionId,cropId,startDate,endDate,accuracyId);
//    }


    /*******************************************长势数据接口调用***************************************************************
     ***1.获取指定区域一个时间段内的长势数据时间点
     ***2.获取指定区域各种长势（7种数值:异常偏低，明显偏低，偏低，正常持平，偏高，明显偏高，异常偏高）的面积和占比情况*******************
     ***3.获取指定区域下各区域的作物长势情况(异常偏低，明显偏低，偏低，正常持平，偏高，明显偏高，异常偏高)******************************
     ***4.获取指定区域下各区域的作物长势数据和上年同比数据 ************************************************************************
     ************************************************************************************************************************
     ************************************************************************************************************************/

    @ApiOperation(value="查询某时间段内的长势数据时间点",notes="根据区域、时间，作物，分辨率查询作物长势时间点" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regionId",value = "区域ID",required = true,dataType = "Long",paramType = "query",defaultValue = "3103000145"),
            @ApiImplicitParam(name = "cropId",value = "作物ID",required = true,dataType = "int",paramType = "query",defaultValue = "511"),
            @ApiImplicitParam(name = "startDate",value = "开始日期",required = true,dataType = "String",paramType = "query",defaultValue = "2016-01-01"),
            @ApiImplicitParam(name = "endDate",value = "结束日期",required = true,dataType = "String",paramType = "query",defaultValue = "2018-12-31"),
            @ApiImplicitParam(name = "resolution",value = "精度",required = true,dataType = "int",paramType = "query",defaultValue = "4010")
    })
    @GetMapping("/queryGrowthTimes")
    public ResultMessage queryGrowthTimes(Long regionId,Integer cropId,String startDate,String endDate,Integer resolution){
        return growthService.queryGrowthTimes(regionId, cropId, startDate, endDate, resolution);
    }


    @ApiOperation(value="查询该区域作物各种长势情况的面积分布和占比数据" , notes = "根据区域、数据日期、作物及分辨率查询该查询该区域作物各种长势情况的面积分布和占比数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regionId",value = "区域ID",required = true,dataType = "Long",paramType = "query",defaultValue = "3103000145"),
            @ApiImplicitParam(name = "cropId",value = "作物ID",required = true,dataType = "int",paramType = "query",defaultValue = "511"),
            @ApiImplicitParam(name = "dataTime",value = "数据日期",required = true,dataType = "String",paramType = "query",defaultValue = "2018-10-01"),
            @ApiImplicitParam(name = "resolution",value = "精度",required = true,dataType = "int",paramType = "query",defaultValue = "4010")
    })
    @GetMapping("/queryGrowthArea")
    public ResultMessage queryGrowthArea(Long regionId, Integer cropId, String dataTime, Integer resolution){
        return growthService.queryGrowthArea(regionId,cropId,dataTime,resolution);
    }


    @ApiOperation(value="查询该区域下各级各级区域的作物长势数据", notes="根据区域、数据日期、作物和分辨率查询该区域下各级区域的作物长势数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regionId",value = "区域ID",required = true,dataType = "Long",paramType = "query",defaultValue = "3102000006"),
            @ApiImplicitParam(name = "cropId",value = "作物ID",required = true,dataType = "int",paramType = "query",defaultValue = "511"),
            @ApiImplicitParam(name = "dataTime",value = "数据日期",required = true,dataType = "String",paramType = "query",defaultValue = "2018-10-01"),
            @ApiImplicitParam(name = "resolution",value = "精度",required = true,dataType = "int",paramType = "query",defaultValue = "4010")
    })
    @GetMapping("/queryGrowthInRegion")
    public ResultMessage queryGrowthInRegion(Long regionId, Integer cropId, String dataTime , Integer resolution){
        return growthService.queryGrowthInRegion(regionId,cropId,dataTime,resolution);
    }


//    @ApiOperation(value="查询该区域下各级各级区域的作物长势数据及上年同期作物长势数据", notes="根据区域、数据日期、作物和分辨率查询该区域下各级区域的作物长势数据及上年同期作物长势数据")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "regionId",value = "区域ID",required = true,dataType = "Long",paramType = "query",defaultValue = "3102000006"),
//            @ApiImplicitParam(name = "cropId",value = "作物ID",required = true,dataType = "int",paramType = "query",defaultValue = "511"),
//            @ApiImplicitParam(name = "dataTime",value = "数据日期",required = true,dataType = "String",paramType = "query",defaultValue = "2018-10-01"),
//            @ApiImplicitParam(name = "resolution",value = "精度",required = true,dataType = "int",paramType = "query",defaultValue = "4010")
//    })
//    @GetMapping("/queryGrowthWithLastYear")
//    public ResultMessage queryGrowthWithLastYear(Long regionId, Integer cropId, String dataTime, Integer resolution){
//        return growthService.queryGrowthWithLastYear(regionId,cropId,dataTime,resolution);
//    }






}
