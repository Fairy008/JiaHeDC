/*
地温地温数据集接口

@version <1> 2017-10-30 Hayden : Created.
*/
package com.jh.show.agric.controller;

import com.jh.biz.feign.IDsTService;
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
 * description:地温数据集服务:
 * 1.根据区域，日期构造某一时间段内地温的近三年和10年均值数据查询服务
 * 2.根据区域，日期构造地温环比查询服务
 * @version <1> 2018-08-10 cxw: Created.
 */
@Api(value="地温数据集接口",description="地温数据集接口")
@RestController
@RequestMapping("/t")
public class DsTController {

	@Autowired
	private IDsTService tService;

	/*
	 * 根据区域，日期构造某一时间段内地温的近三年和10年均值数据查询服务
	 * @param  数据集查询参数
	 *   regionId: 区域ID
     *  startDate: 开始日期
     *    endDate: 结束日期
     *  accuracyId: 精度
    * @return ResultMessage
	* @version <1> 2018-08-10 cxw: Created.
	 */
	@ApiOperation(value = "查询指定时间段地温同比数据", notes = "根据区域、时间段查询某一时间段内地温的近三年和10年均值数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3100000000"),
			@ApiImplicitParam(name = "startDate",value = "开始日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-01-01"),
			@ApiImplicitParam(name = "endDate",value = "结束日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-12-31"),
			@ApiImplicitParam(name = "accuracyId",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4007")
	})
	@GetMapping("/queryTForChart")
	public ResultMessage queryTForChart(Long regionId,String startDate,String endDate,Integer accuracyId){
		return tService.queryTForChart(regionId,startDate,endDate,accuracyId);
	}

	/*
	* 根据区域，日期构造地温环比查询服务
 	 * @param 数据集查询参数
	 *   regionId: 区域ID
     *  startDate: 开始日期
     *    endDate: 结束日期
     *  accuracyId: 精度
    * @return ResultMessage
	* @version <1> 2018-08-10 cxw: Created.
	*/
	@ApiOperation(value="查询地温指定日期环比数据",notes="根据区域、日期段查询区域及下一级区域在日期段内的同期环比地温数据" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3100000000"),
			@ApiImplicitParam(name = "startDate",value = "开始日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-01-01"),
			@ApiImplicitParam(name = "endDate",value = "结束日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-12-31"),
			@ApiImplicitParam(name = "accuracyId",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4007")
	})
	@GetMapping("/tForMon")
	public ResultMessage queryTForMon(Long regionId,String startDate,String endDate,Integer accuracyId){
        return tService.queryTForMon(regionId,startDate,endDate,accuracyId);
	}

	/*
	* 根据区域，日期构造地温报告生成服务
	* @param  数据集查询参数
	*   regionId: 区域ID
	*   chinaName:区域中文名
	*  startDate: 开始日期
	*    endDate: 结束日期
	*  accuracyId: 精度
	* @return ResultMessage
	* @version <1> 2018-08-10 cxw: Created.
	*/
	@ApiOperation(value="构造地温报告生成服务",notes="根据区域、日期段查询区域及下一级区域在日期段内的地温报告" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "chinaName",value = "区域名称",required = true, dataType = "String" ,paramType="query",defaultValue="中国"),
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3100000000"),
			@ApiImplicitParam(name = "startDate",value = "开始日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-01-01"),
			@ApiImplicitParam(name = "endDate",value = "结束日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-12-31"),
			@ApiImplicitParam(name = "accuracyId",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4007")
	})
	@GetMapping("/tForReport")
	public ResultMessage queryTForReport(String chinaName, Long regionId, String startDate, String endDate,Integer accuracyId){
		return tService.queryTForReport(chinaName,regionId,startDate,endDate,accuracyId);
	}


	/*
	 * 查询指定之间段内地温有数据的日期
	 * @param  数据集查询参数
	 *   regionId: 区域ID
	 *  startDate: 开始日期
	 *    endDate: 结束日期
	 *  accuracyId: 精度
	 * @return ResultMessage
	 * @version <1> 2018-11-14 Roach: Created.
	 */
	@ApiOperation(value = "查询指定之间段内地温有数据的日期", notes = "查询指定之间段内地温有数据的日期")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3100000000"),
			@ApiImplicitParam(name = "startDate",value = "开始日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-01-01"),
			@ApiImplicitParam(name = "endDate",value = "结束日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-12-31"),
			@ApiImplicitParam(name = "accuracyId",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4007")
	})
	@GetMapping("/queryTTimes")
	public ResultMessage queryTTimes(Long regionId,String startDate,String endDate,Integer accuracyId){
		return tService.queryTTimes(regionId,startDate,endDate,accuracyId);
	}

}