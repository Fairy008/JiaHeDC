/*
降雨地温数据集接口

@version <1> 2017-10-30 Hayden : Created.
*/
package com.jh.show.agric.controller;

import com.jh.biz.feign.IDsTrmmService;
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
 * description:降雨数据集服务:
 * 1.根据区域，日期构造某一时间段内降雨的近三年和10年均值数据查询服务
 * 2.根据区域，日期构造降雨环比查询服务
 * @version <1> 2018-08-10 cxw: Created.
 */
@Api(value="降雨数据集接口",description="降雨数据集接口")
@RestController
@RequestMapping("/trmm")
public class DsTrmmController {

	@Autowired
	private IDsTrmmService trmmService;

	/*
	 * 根据区域，日期构造某一时间段内降雨的近三年和10年均值数据查询服务
	 * @param 数据集查询参数
	 *   regionId: 区域ID
     *  startDate: 开始日期
     *    endDate: 结束日期
     *  accuracyId: 精度
     * @return ResultMessage
	 * @version <1> 2018-08-10 cxw: Created.
	 */
	@ApiOperation(value = "查询指定时间段降雨同比数据", notes = "根据区域、时间段查询某一时间段内降雨的近三年和10年均值数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3100000000"),
			@ApiImplicitParam(name = "startDate",value = "开始日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-01-01"),
			@ApiImplicitParam(name = "endDate",value = "结束日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-12-31"),
			@ApiImplicitParam(name = "accuracyId",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4009")
	})
	@GetMapping("/queryTrmmForChart")
	public ResultMessage queryTrmmForChart(Long regionId,String startDate,String endDate ,Integer accuracyId){
		return trmmService.queryTrmmForChart(regionId,startDate,endDate,accuracyId);
	}

	/*
	* 根据区域，日期构造降雨环比查询服务
    * @param 数据集查询参数
	*   regionId: 区域ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    *  accuracyId: 精度
    * @return ResultMessage
	* @version <1> 2018-08-10 cxw: Created.
	*/
	@ApiOperation(value="查询降雨指定日期环比数据",notes="根据区域、日期段查询区域及下一级区域在日期段内的同期环比降雨数据" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3100000000"),
			@ApiImplicitParam(name = "startDate",value = "开始日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-01-01"),
			@ApiImplicitParam(name = "endDate",value = "结束日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-12-31"),
			@ApiImplicitParam(name = "accuracyId",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4009")
	})
	@GetMapping("/trmmForMon")
	public ResultMessage queryTrmmForMon(Long regionId,String startDate,String endDate,Integer accuracyId){
		return trmmService.queryTrmmForMon(regionId,startDate,endDate,accuracyId);
	}

	/*
	* 根据区域，日期构造降雨报告生成服务
	* @param 数据集查询参数
	*   regionId: 区域ID
	*   chinaName:区域中文名
	*  startDate: 开始日期
	*    endDate: 结束日期
	*  accuracyId: 精度
	* @return ResultMessage
	* @version <1> 2018-08-10 cxw: Created.
	*/
	@ApiOperation(value="构造降雨报告生成服务",notes="根据区域、日期段查询区域及下一级区域在日期段内的降雨报告" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "chinaName",value = "区域名称",required = true, dataType = "String" ,paramType="query",defaultValue="中国"),
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3100000000"),
			@ApiImplicitParam(name = "startDate",value = "开始日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-01-01"),
			@ApiImplicitParam(name = "endDate",value = "结束日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-12-31"),
			@ApiImplicitParam(name = "accuracyId",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4009")
	})
	@GetMapping("/trmmForReport")
	public ResultMessage queryTrmmForReport(String chinaName, Long regionId, String startDate, String endDate,Integer accuracyId){
		return trmmService.queryTrmmForReport(chinaName,regionId,startDate,endDate,accuracyId);
	}


	/*
	 * 查询开始和结束日期时间段内有降雨数据的所有日期
	 * @param 数据集查询参数
	 *   regionId: 区域ID
	 *  startDate: 开始日期
	 *    endDate: 结束日期
	 *  accuracyId: 精度
	 * @return ResultMessage
	 * @version <1> 2018-11-14 Roach: Created.
	 */
	@ApiOperation(value = "查询开始和结束日期时间段内有降雨数据的所有日期", notes = "查询开始和结束日期时间段内有降雨数据的所有日期")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3100000000"),
			@ApiImplicitParam(name = "startDate",value = "开始日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-01-01"),
			@ApiImplicitParam(name = "endDate",value = "结束日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-12-31"),
			@ApiImplicitParam(name = "accuracyId",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4009")
	})
	@GetMapping("/queryTrmmTimes")
	public ResultMessage queryTrmmTimes(Long regionId,String startDate,String endDate ,Integer accuracyId){
		return trmmService.queryTrmmTimes(regionId,startDate,endDate,accuracyId);
	}
}