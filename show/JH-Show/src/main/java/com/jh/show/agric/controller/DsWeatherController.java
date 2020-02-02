package com.jh.show.agric.controller;

import com.jh.biz.feign.IDsWeatherService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(value="天气预报接口",description="天气预报接口")
@RestController
@RequestMapping("/weather")
public class DsWeatherController {

	@Autowired
	private IDsWeatherService weatherService;

	/*
	 * 根据区域查询区域及下一级区域的天气预报数据
	 *   regionId: 区域ID
	 * @return ResultMessage
	 * @version <1> 2018-11-02 Roach: Created.
	 */
	@ApiOperation(value = "根据区域查询区域及下一级区域的天气预报数据", notes = "根据区域查询区域及下一级区域的天气预报数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3103000019")
	})
	@GetMapping("/queryRegionAndBeyond")
	public ResultMessage queryRegionAndBeyond(Long regionId){
		return weatherService.queryRegionAndBeyond(regionId);
	}

	/*
	 *根据区域查询该区域的天气预报数据
	 * @param 数据集查询参数
	 *   regionId: 区域ID
	 *@return ResultMessage
	 * @version <1> 2018-11-02 Roach: Created.
	 */
	@ApiOperation(value="根据区域查询该区域的天气预报数据",notes="根据区域查询该区域的天气预报数据" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3103000019")
	})
	@GetMapping("/queryRegionWeather")
	public ResultMessage queryRegionWeather(Long regionId){
		return weatherService.queryRegionWeather(regionId);
	}

	
}