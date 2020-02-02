/*
气温数据集接口

@version <1> 2017-10-30 Hayden : Created.
*/
package com.jh.show.agric.controller;

import com.jh.biz.feign.IDsTempService;
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
 * description:气温数据集服务:
 * 1.根据区域，日期构造某一时间段内气温的近三年和10年均值数据查询服务
 * 2.根据区域，日期构造气温环比查询服务
 * @version <1> 2018-10-16 huxiaoqiang: Created.
 */
@Api(value="气温数据集接口",description="气温数据集接口")
@RestController
@RequestMapping("/temp")
public class DsTempController {

	@Autowired
	private IDsTempService tempService;

	/*
	 * 根据区域，日期构造某一时间段内气温的近三年和10年均值数据查询服务
	 * @param 数据集查询参数
	 *   regionId: 区域ID
     *  startDate: 开始日期
     *    endDate: 结束日期
     *  accuracyId: 精度
     * @return ResultMessage
	 * @version <1> 2018-10-16 huxiaoqiang: Created.
	 */
	@ApiOperation(value = "查询指定时间段气温同比数据", notes = "根据区域、时间段查询某一时间段内气温的近三年和10年均值数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3103000019"),
			@ApiImplicitParam(name = "startDate",value = "开始日期",required = true, dataType = "String" ,paramType="query",defaultValue="2018-01-01"),
			@ApiImplicitParam(name = "endDate",value = "结束日期",required = true, dataType = "String" ,paramType="query",defaultValue="2018-01-10")
	})
	@GetMapping("/queryTempForChart")
	public ResultMessage queryTempForChart(Long regionId,String startDate,String endDate){
		return tempService.queryTempForChart(regionId,startDate,endDate);
	}

	/*
	* 根据区域，日期构造气温环比查询服务
    * @param 数据集查询参数
	*   regionId: 区域ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    *  accuracyId: 精度
    * @return ResultMessage
	* @version <1> 2018-10-16 huxiaoqiang: Created.
	*/
	@ApiOperation(value="查询气温指定日期环比数据",notes="根据区域、日期段查询区域及下一级区域在日期段内的同期环比气温数据" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3103000019"),
			@ApiImplicitParam(name = "startDate",value = "开始日期",required = true, dataType = "String" ,paramType="query",defaultValue="2018-01-01"),
			@ApiImplicitParam(name = "endDate",value = "结束日期",required = true, dataType = "String" ,paramType="query",defaultValue="2018-01-10")
	})
	@GetMapping("/tempForMon")
	public ResultMessage queryTempForMon(Long regionId,String startDate,String endDate){
		return tempService.queryTempForMon(regionId,startDate,endDate);
	}

	/*
	* 根据区域，日期构造气温报告生成服务
	* @param 数据集查询参数
	*   regionId: 区域ID
	*   chinaName:区域中文名
	*  startDate: 开始日期
	*    endDate: 结束日期
	*  accuracyId: 精度
	* @return ResultMessage
	* @version <1> 2018-10-16 huxiaoqiang: Created.
	*/
	@ApiOperation(value="构造气温报告生成服务",notes="根据区域、日期段查询区域及下一级区域在日期段内的气温报告" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "chinaName",value = "区域中文名",required = true, dataType = "String" ,paramType="query",defaultValue="重庆市"),
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3103000019"),
			@ApiImplicitParam(name = "startDate",value = "开始日期",required = true, dataType = "String" ,paramType="query",defaultValue="2018-01-01"),
			@ApiImplicitParam(name = "endDate",value = "结束日期",required = true, dataType = "String" ,paramType="query",defaultValue="2018-01-20"),
	})
	@GetMapping("/tempForReport")
	public ResultMessage queryTempForReport(String chinaName,Long regionId, String startDate, String endDate){
		return tempService.queryTempForReport(chinaName,regionId,startDate,endDate);
	}


	/*
	 * 查询开始和结束日期时间段内有气温数据的所有日期
	 * @param 数据集查询参数
	 *   regionId: 区域ID
	 *  startDate: 开始日期
	 *    endDate: 结束日期
	 *  accuracyId: 精度
	 * @return ResultMessage
	 * @version <1> 2018-11-14 Roach: Created.
	 */
	@ApiOperation(value = "查询开始和结束日期时间段内有气温数据的所有日期", notes = "查询开始和结束日期时间段内有气温数据的所有日期")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3103000019"),
			@ApiImplicitParam(name = "startDate",value = "开始日期",required = true, dataType = "String" ,paramType="query",defaultValue="2018-01-01"),
			@ApiImplicitParam(name = "endDate",value = "结束日期",required = true, dataType = "String" ,paramType="query",defaultValue="2018-01-10")
	})
	@GetMapping("/queryTempTimes")
	public ResultMessage queryTempTimes(Long regionId,String startDate,String endDate){
		return tempService.queryTempTimes(regionId,startDate,endDate);
	}
}