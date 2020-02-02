package com.jh.dgy.controller;


import com.jh.dgy.service.IDistributionService;
import com.jh.dgy.vo.DistributionAndYieldVo;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * description:分布数据集服务:
 * 1.根据区域、时间，作物，分辨率查询作物分布生成时间
 * 2.根据区域、起止时间查询当年与前一年区域及下一级区域的作物种植面积数据
 * 3.根据区域、起止时间查询近三年区域及下一级区域的作物种植面积
 * @version <1> 2018-04-27 cxw: Created.
 */
@Api(value="作物分布数据集服务", description = "作物分布数据集服务接口")
@RestController
@RequestMapping("distribution")
public class DistributionController {

    @Autowired
    IDistributionService distributionService ;

    /*
    * 根据区域、时间，作物，分辨率查询作物分布生成时间
    * @param :
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    *   resolution: 分辨率
    * @return ResultMessage
    * @version <1> 2018-05-22 cxw: Created.
    */
    @ApiOperation(value="查询某时间段分布时间",notes="根据区域、时间，作物，分辨率查询作物分布时间" )
    @GetMapping("/queryDistributionTimes")
    public ResultMessage queryDistributionTimes(Long regionId,Integer cropId,String startDate,String endDate,Integer resolution){
        return distributionService.findDistributionTimes(regionId,cropId,startDate,endDate,resolution);
    }


    /*
    * 根据区域、时间查询分布数据
    * @param :
    *   regionId: 区域ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    * @return ResultMessage
    * @version <1> 2019-03-20 cxw: Created.
    */
    @ApiOperation(value="查询所有分布",notes="根据区域、时间查询所有分布" )
    @GetMapping("/queryAllDistribution")
    public ResultMessage queryAllDistribution(Long regionId,String startDate,String endDate){
        return distributionService.findAllDistribution(regionId,startDate,endDate);
    }

    /*
      * 根据区域、时间查询当年与前一年区域及下一级区域的作物种植面积数据
      *   regionId: 区域ID
      *     cropId: 作物ID
      *  dataTime: 分布时间点
      *   resolution: 分辨率
      * @return ResultMessage
      * @version <1> 2018-04-27 27: Created.
      */
    @ApiOperation(value="查询指定时间及上一年作物的种植面积",notes="根据区域、时间查询当年与前一年区域及下一级区域的作物种植面积数据" )
    @GetMapping("/distributionForPrevious")
    public ResultMessage queryDistributionForPrevious(Long regionId,Integer cropId,String dataTime,Integer accuracyId,Integer isQueryLargeArea){
        return  distributionService.findDistributionForPrevious(regionId,cropId,dataTime,accuracyId,isQueryLargeArea);
    }

    /*
    * 根据区域、时间，作物，分辨率查询作物分布
    * @param :
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    *   resolution: 分辨率
    * @return ResultMessage
    * @version <1> 2019-03-21 cxw: Created.
    */
    @ApiOperation(value="查询作物分布",notes="根据区域、时间，作物，分辨率查询作物分布" )
    @GetMapping("/queryAllDistributionByTime")
    public ResultMessage queryAllDistributionByTime(Long regionId,Integer cropId,String startDate,String endDate,Integer resolution){
        return distributionService.queryAllDistributionByTime(regionId,cropId,startDate,endDate,resolution);
    }


    /*
    * 根据区域、时间查询近三年区域及下一级区域的作物种植面积
    * @param :
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  dataTime: 分布时间点
    *   resolution: 分辨率
    * @return ResultMessage
    * @version <1> 2018-04-27 cxw: Created.
    */
    @ApiOperation(value="查询三年内区域内作物种植面积",notes="根据区域、时间查询近三年区域及下一级区域的作物种植面积" )
    @GetMapping("/distributionForThree")
    public ResultMessage queryDistributionForThree(Long regionId,Integer cropId,String dataTime,Integer accuracyId){
        return distributionService.findDistributionForThree(regionId,cropId,dataTime,accuracyId);
    }

    
    
    
	/*
	 * 根据区域、作物，时间，精度查询重庆市特定分下在重庆市的百分占比
	 *  regionId: 区域ID
	 *  cropId: 作物ID
	 *  dataTime: 分布时间点
	 *  accuracyId: 精度
	 * @return ResultMessage
	 * @version <1> 2018-10-30 liyb: Created.
	 */
    @ApiOperation(value="查询重庆指定分区下在重庆市的百分占比",notes="根据区域、时间查询重庆特定分区下在重庆市的百分占比" )
    @GetMapping("/queryRegionPercentage")
    public ResultMessage queryRegionPercentage(Long regionId,Integer cropId,String dataTime,Integer accuracyId){
        return distributionService.findRegionPercentage(regionId,cropId,dataTime,accuracyId);
    }

}
