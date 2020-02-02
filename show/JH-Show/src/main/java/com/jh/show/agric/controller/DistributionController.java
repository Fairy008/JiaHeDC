package com.jh.show.agric.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jh.vo.ResultMessage;
import com.jh.biz.feign.IDistributionService;


/**
 * description:分布数据集服务:
 * 1.根据区域、时间，作物，分辨率查询作物分布时间
 * 2.根据区域、作物，时间，精度查询当年与前一年区域及下一级区域的作物种植面积数据
 * 3.根据区域、作物，时间，精度查询当前区域及下一级区域的作物种植面积近三年及历年10年均值
 * @version <1> 2018-08-10 cxw: Created.
 */
@Api(value = "Distribution Service Interface",description="分布数据操作接口")
@RestController
@RequestMapping("/distribution")
public class DistributionController{
	@Autowired
	private IDistributionService distributionService;

	@ApiOperation(value="查询某时间段分布时间",notes="根据区域、时间，作物，分辨率查询作物分布时间" )
	@ApiImplicitParams({
		@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3100000000"),
		@ApiImplicitParam(name = "cropId",value = "作物ID",required = true, dataType = "int" ,paramType="query",defaultValue="501"),
		@ApiImplicitParam(name = "startDate",value = "开始日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-01-01"),
		@ApiImplicitParam(name = "endDate",value = "结束日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-12-31"),
		@ApiImplicitParam(name = "resolution",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4011")
	})
    @GetMapping("/queryDistributionTimes")
    public ResultMessage queryDistributionTimes(@RequestParam Long regionId,@RequestParam Integer cropId,@RequestParam String startDate,@RequestParam String endDate,@RequestParam Integer resolution){
        return distributionService.queryDistributionTimes(regionId,cropId,startDate,endDate,resolution);
    }

  /*
   * 根据区域、作物，时间，精度查询当年与前一年区域及下一级区域的作物种植面积数据
   *  regionId: 区域ID
   *  cropId: 作物ID
   *  dataTime: 分布时间点
   *  accuracyId: 精度
   * @return ResultMessage
   * @version <1> 2018-08-10 cxw: Created.
   */
	@ApiOperation(value="查询指定时间及上一年作物的种植面积",notes="根据区域、作物，时间，精度查询当年与前一年区域及下一级区域的作物种植面积数据" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3102000006"),
			@ApiImplicitParam(name = "cropId",value = "作物ID",required = true, dataType = "int" ,paramType="query",defaultValue="504"),
			@ApiImplicitParam(name = "dataTime",value = "分布时间",required = true, dataType = "String" ,paramType="query",defaultValue="2016-08-31"),
			@ApiImplicitParam(name = "accuracyId",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4010"),
			@ApiImplicitParam(name = "isQueryLargeArea",value = "是否查大区",required = true, dataType = "int" ,paramType="query",defaultValue="0")
	})
	@GetMapping("/distributionForPrevious")
	public ResultMessage queryDistributionForPrevious(Long regionId,Integer cropId,String dataTime,Integer accuracyId,Integer isQueryLargeArea){
		return  distributionService.queryDistributionForPrevious(regionId,cropId,dataTime,accuracyId,isQueryLargeArea);
	}


	/*
    * 根据区域、作物，时间，精度查询当前区域及下一级区域的作物种植面积近三年及历年10年均值
    * regionId: 区域ID
	* cropId: 作物ID
	* dataTime: 分布时间点
	* accuracyId: 精度
	* @return ResultMessage
	* @version <1> 2018-08-10 cxw: Created.
    */
	@ApiOperation(value="查询当前区域及下一级区域的作物种植面积近三年及历年10年均值",notes="根据区域、作物，时间，精度查询当前区域及下一级区域的作物种植面积近三年及历年10年均值" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3100000000"),
			@ApiImplicitParam(name = "cropId",value = "作物ID",required = true, dataType = "int" ,paramType="query",defaultValue="501"),
			@ApiImplicitParam(name = "dataTime",value = "分布时间",required = true, dataType = "String" ,paramType="query",defaultValue="2017-01-01"),
			@ApiImplicitParam(name = "accuracyId",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4011")
	})
	@GetMapping("/distributionForThree")
	public ResultMessage queryDistributionForThree(Long regionId,Integer cropId,String dataTime,Integer accuracyId){
		return distributionService.queryDistributionForThree(regionId,cropId,dataTime,accuracyId);
	}


	/*
	 * 根据区域、作物，时间，精度查询重庆市特定分区当年与前一年区域及下一级区域的作物种植面积数据
	 *  regionId: 区域ID
	 *  cropId: 作物ID
	 *  dataTime: 分布时间点
	 *  accuracyId: 精度
	 * @return ResultMessage
	 * @version <1> 2018-08-10 huxiaoqiang: Created.
	 */
	@ApiOperation(value="查询重庆指定分区下指定时间及上一年作物的种植面积",notes="根据区域、时间查询重庆特定分区当年与前一年区域及下一级区域的作物种植面积数据" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3103000019"),
			@ApiImplicitParam(name = "cropId",value = "作物ID",required = true, dataType = "int" ,paramType="query",defaultValue="507"),
			@ApiImplicitParam(name = "dataTime",value = "分布时间",required = true, dataType = "String" ,paramType="query",defaultValue="2016-08-31"),
			@ApiImplicitParam(name = "accuracyId",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4010")
	})
	@GetMapping("/queryBeyondDistribution")
	public ResultMessage queryBeyondDistribution(Long regionId,Integer cropId,String dataTime,Integer accuracyId){
		return  distributionService.queryBeyondDistribution(regionId,cropId,dataTime,accuracyId);
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
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3104000103"),
			@ApiImplicitParam(name = "cropId",value = "作物ID",required = true, dataType = "int" ,paramType="query",defaultValue="509"),
			@ApiImplicitParam(name = "dataTime",value = "分布时间",required = true, dataType = "String" ,paramType="query",defaultValue="2016-08-30"),
			@ApiImplicitParam(name = "accuracyId",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4010")
	})
	@GetMapping("/queryRegionPercentage")
	public ResultMessage queryRegionPercentage(Long regionId,Integer cropId,String dataTime,Integer accuracyId){
		return  distributionService.queryRegionPercentage(regionId,cropId,dataTime,accuracyId);
	}
	
}