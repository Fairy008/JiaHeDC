package com.jh.dgy.controller;

import com.jh.dgy.service.IYieldService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:估产数据集服务:
 * 1.根据区域、时间，作物，分辨率查询作物估产生成时间
 * 2.根据年份查询区域及子区域作物当年与上一年的估产数据
 * 3.根据年份查询区域及子区域作物三年的估产及十年的估产均值
 * @version <1> 2018-04-27 cxw: Created.
 */
@Api(value="作物估产数据集服务", description = "作物估产数据集服务接口")
@RestController
@RequestMapping("yield")
public class YieldController {

    @Autowired
    IYieldService yieldService ;

    /*
    * 根据区域、时间，作物，分辨率查询作物估产生成时间
    * @param :
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    *   resolution: 分辨率
    * @return ResultMessage
    * @version <1> 2018-05-22 cxw: Created.
    */
    @ApiOperation(value="查询某时间段估产时间",notes="根据区域、时间，作物，分辨率查询作物估产生成时间" )
    @GetMapping("/queryYieldTimes")
    public ResultMessage queryYieldTimes(Long regionId,Integer cropId,String startDate,String endDate,Integer resolution){
        return yieldService.findYieldTimes(regionId,cropId,startDate,endDate,resolution);
    }

    /*
    * 根据区域、时间查询所有估产
    * @param :
    *   regionId: 区域ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    * @return ResultMessage
    * @version <1> 2018-05-22 cxw: Created.
    */
    @ApiOperation(value="查询所有估产数据",notes="根据区域、时间查询所有估产数据" )
    @GetMapping("/queryAllYield")
    public ResultMessage queryAllYield(Long regionId,String startDate,String endDate){
        return yieldService.findAllYield(regionId,startDate,endDate);
    }

    /*
    * 查询区域及子区域作物当年与上一年的估产数据
    * @param:
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  dataTime: 估产时间点
    *   resolution: 分辨率
    * @return ResultMessage
    * @version <1> 2018-04-27 cxw: Created.
    */
    @ApiOperation(value="区域及子区域作物当年与上一年的估产数据",notes="根据年份查询区域及子区域作物当年与上一年的估产数据" )
    @GetMapping("/yieldForPrevious" )
    public ResultMessage queryYieldForPrevious(Long regionId,Integer cropId,String dataTime,Integer resolution){
        return yieldService.findYieldForPrevious(regionId,cropId,dataTime,resolution);
    }


    /*
    * 根据年份查询区域及子区域作物三年的估产及十年的估产均值
    * @param :
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  dataTime: 估产时间点
    *   resolution: 分辨率
    * @return ResultMessage
    * @version <1> 2018-04-27 cxw: Created.
    */
    @ApiOperation(value="区域及子区域作物三年的估产及十年的估产均值",notes="根据年份查询区域及子区域作物三年的估产及十年的估产均值" )
    @GetMapping("/yieldForThree" )
    public ResultMessage queryYieldForThree(Long regionId,Integer cropId,String dataTime,Integer resolution){
        return yieldService.findYieldForThree(regionId,cropId,dataTime,resolution);
    }


    /**
     * 查询该区域下各级区域的作物估产情况
     * @param regionId  区域id
     * @param cropId    作物id
     * @param dataTime  数据时间
     * @param resolution    数据精度
     * @return
     */
    @ApiOperation(value="查询该区域下各级区域的作物估产情况",notes="查询该区域下各级区域的作物估产情况" )
    @GetMapping("/queryYieldInRegion")
    public ResultMessage queryYieldInRegion(Long regionId,Integer cropId,String dataTime,Integer resolution,Integer isQueryLargeArea){
        return yieldService.findYieldInRegion(regionId , cropId, dataTime, resolution,isQueryLargeArea);
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
         return yieldService.findRegionPercentage(regionId,cropId,dataTime,accuracyId);
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
    @ApiOperation(value="查询作物估产",notes="根据区域、时间，作物，分辨率查询作物估产" )
    @GetMapping("/queryAllYieldByTime")
    public ResultMessage queryAllYieldByTime(Long regionId,Integer cropId,String startDate,String endDate,Integer resolution){
        return yieldService.queryAllYieldByTime(regionId,cropId,startDate,endDate,resolution);
    }


}
