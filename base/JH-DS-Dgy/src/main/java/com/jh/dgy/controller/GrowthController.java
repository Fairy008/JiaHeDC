package com.jh.dgy.controller;

import com.jh.dgy.service.IGrowthService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 长势数据集服务:
 * 1. 根据区域、日期段查询区域作物当年与上一年的长势数据
 * 2. 根据区域、日期段查询近三年及近十年的长势数据（同比分页查询）
 * @version <1> 2018-10-18 huxiaoqiang: Created.
 */
@Api(value = "作物长势数据集服务",description="作物长势数据集服务接口")
@RestController
@RequestMapping("/growth")
public class GrowthController {

    @Autowired
    private IGrowthService growthService;

    /**
    * 根据区域、日期段查询区域及下一级区域在日期段内的长势数据
    * @param :
     *   regionId: 区域ID
     *     cropId: 作物ID
     *  startDate: 结束日期
     *    endDate: 结束日期
     *   resolution: 分辨率
    * @return ResultMessage
    * @version  <1> 2018-04-27 cxw : Created.:
    */
//    @ApiOperation(value="查询长势指定日期环比数据",notes="根据区域、日期段查询区域作物当年与上一年的长势数据" )
//    @GetMapping("/GrowthForPrevious")
//    public ResultMessage queryGrowthList(Long regionId, Integer cropId,String startDate, String endDate,Integer accuracyId){
//        return growthService.findGrowthForPrevious(regionId,cropId,startDate,endDate,accuracyId);
//
//    }

    /**
    * 根据区域、日期段查询近三年及近十年的长势数据
    * @param:
     *   regionId: 区域ID
     *     cropId: 作物ID
     *  startDate: 结束日期
     *    endDate: 结束日期
     *   resolution: 分辨率
    * @return ResultMessage
    * @version  <1> 2018-04-27 cxw : Created.:
    */
//    @ApiOperation(value="查询长势指定日期三年同比数据",notes="根据区域、日期段查询近三年及近十年的长势数据" )
//    @GetMapping("/GrowthForThree")
//    public ResultMessage queryGrowthThreeList(Long regionId, Integer cropId,String startDate, String endDate,Integer accuracyId){
//        return growthService.findGrowthThreeList(regionId,cropId,startDate,endDate,accuracyId);
//    }


    /*******************************************长势数据接口调用***************************************************************
     ***1.获取指定区域一个时间段内的长势数据时间点
     ***2.获取指定区域各种长势（7种数值:异常偏低，明显偏低，偏低，正常持平，偏高，明显偏高，异常偏高）的面积和占比情况*******************
     ***3.获取指定区域下各区域的作物长势情况(异常偏低，明显偏低，偏低，正常持平，偏高，明显偏高，异常偏高)******************************
     ***4.获取指定区域下各区域的作物长势数据和上年同比数据 ************************************************************************
     ************************************************************************************************************************
     ************************************************************************************************************************/

    /**
     *查询时间段内的长势数据时间点
     * @param regionId 区域
     * @param cropId 作物
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param resolution 精度
     * @version <1> 2018-10-18 huxiaoqiang: Created.
     *
     */
    @ApiOperation(value="查询某时间段内的长势数据时间点",notes="根据区域、时间，作物，分辨率查询作物长势时间点" )
    @GetMapping("/queryGrowthTimes")
    public ResultMessage queryGrowthTimes(Long regionId,Integer cropId,String startDate,String endDate,Integer resolution){
        return growthService.findGrowthTimes(regionId, cropId, startDate, endDate, resolution);
    }

    /**
     *查询所有分布
     * @param regionId 区域
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @version <1> 2019-03-20 cxw: Created.
     */
    @ApiOperation(value="查询所有分布",notes="查询所有分布数据" )
    @GetMapping("/queryAllGrowth")
    public ResultMessage queryAllGrowth(Long regionId,String startDate,String endDate){
        return growthService.findAllGrowth(regionId,startDate,endDate);
    }

    /**
     * 查询该区域作物各种长势情况的面积分布和占比数据
     * @param regionId  区域
     * @param cropId    作物
     * @param dataTime 数据日期
     * @param resolution 精度
     * @return
     * @version <1> 2018-10-18 huxiaoqiang: Created.
     */
    @ApiOperation(value="查询该区域作物各种长势情况的面积分布和占比数据" , notes = "根据区域、数据日期、作物及分辨率查询该查询该区域作物各种长势情况的面积分布和占比数据")
    @GetMapping("/queryGrowthArea")
    public ResultMessage queryGrowthArea(Long regionId, Integer cropId, String dataTime, Integer resolution){
        return growthService.queryGrowthArea(regionId,cropId,dataTime,resolution);
    }

    /**
     * 查询所指区域
     * @param regionId  区域
     * @param cropId    作物
     * @param dataTime 数据日期
     * @param resolution 精度
     * @return
     * @version <1> 2018-10-18 huxiaoqiang: Created.
     */
    @ApiOperation(value="查询该区域下各级区域的作物长势数据", notes="根据区域、数据日期、作物和分辨率查询该区域下各级区域的作物长势数据")
    @GetMapping("/queryGrowthInRegion")
    public ResultMessage queryGrowthInRegion(Long regionId, Integer cropId, String dataTime , Integer resolution){
        return growthService.queryGrowthInRegion(regionId,cropId,dataTime,resolution);
    }



    /**
     * 查询该区域下各级各级区域的作物长势数据及上年同期作物长势数据
     *
     * @param regionId  区域
     * @param cropId    作物
     * @param dataTime 数据日期
     * @param resolution 精度
     * @return
     * @version  <1> 2018-09-29 lcw : Created.:
     */
//    @ApiOperation(value="查询该区域下各级各级区域的作物长势数据及上年同期作物长势数据", notes="根据区域、数据日期、作物和分辨率查询该区域下各级区域的作物长势数据及上年同期作物长势数据")
//    @GetMapping("/queryGrowthWithLastYear")
//    public ResultMessage queryGrowthWithLastYear(Long regionId, Integer cropId, String dataTime, Integer resolution){
//        return growthService.queryGrowthWithLastYear(regionId,cropId,dataTime,resolution);
//    }

    /*
    * 根据区域、时间，作物，分辨率查询作物长势
    * @param :
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    *   resolution: 分辨率
    * @return ResultMessage
    * @version <1> 2019-03-21 cxw: Created.
    */
    @ApiOperation(value="查询作物长势",notes="根据区域、时间，作物，分辨率查询作物长势" )
    @GetMapping("/queryAllGrowthByTime")
    public ResultMessage queryAllGrowthByTime(Long regionId,Integer cropId,String startDate,String endDate,Integer resolution){
        return growthService.queryAllGrowthByTime(regionId,cropId,startDate,endDate,resolution);
    }


}
