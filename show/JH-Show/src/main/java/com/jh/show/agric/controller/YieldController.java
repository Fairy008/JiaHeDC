package com.jh.show.agric.controller;


import com.jh.biz.feign.IYieldService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Yield Service Interface",description = "估产数据接口")
@RestController
@RequestMapping("/yield")
public class YieldController {

    @Autowired
    private IYieldService yieldService;


    @ApiOperation(value="查询某时间段估产时间",notes="根据区域、时间，作物，分辨率查询作物估产生成时间" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regionId",value = "区域id",required = true,dataType = "Long",paramType = "query",defaultValue = "500000000"),
            @ApiImplicitParam(name = "cropId",value = "作物id",required = true,dataType = "int",paramType = "query",defaultValue = "504"),
            @ApiImplicitParam(name = "startDate",value = "开始日期",required = true,dataType = "String",paramType = "query",defaultValue = "2010-10-10"),
            @ApiImplicitParam(name = "endDate",value = "结束日期",required = true,dataType = "String",paramType = "query",defaultValue = "2018-10-10"),
            @ApiImplicitParam(name = "resolution",value = "数据精度/分辨率",required = true,dataType = "int",paramType = "query",defaultValue = "4006")
    })
    @GetMapping("/queryYieldTimes")
    public ResultMessage queryYieldTimes(@RequestParam Long regionId,
                                         @RequestParam Integer cropId,
                                         @RequestParam String startDate,
                                         @RequestParam String endDate,
                                         @RequestParam Integer resolution){
        return yieldService.queryYieldTimes(regionId,cropId,startDate,endDate,resolution);
    }



    @ApiOperation(value="区域及子区域作物当年与上一年的估产数据",notes="根据年份查询区域及子区域作物当年与上一年的估产数据" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regionId",value = "区域id",required = true,dataType = "Long",paramType = "query",defaultValue = "2300000000"),
            @ApiImplicitParam(name = "cropId",value = "作物id",required = true,dataType = "int",paramType = "query",defaultValue = "504"),
            @ApiImplicitParam(name = "dataTime",value = "数据日期",required = true,dataType = "String",paramType = "query",defaultValue = "2016-08-30"),
            @ApiImplicitParam(name = "resolution",value = "数据精度/分辨率",required = true,dataType = "int",paramType = "query",defaultValue = "4006")
    })
    @GetMapping("/yieldForPrevious" )
    public ResultMessage queryYieldForPrevious(@RequestParam Long regionId,
                                               @RequestParam Integer cropId,
                                               @RequestParam String dataTime,
                                               @RequestParam Integer resolution){
        return yieldService.queryYieldForPrevious(regionId,cropId,dataTime,resolution);
    }



    @ApiOperation(value="区域及子区域作物三年的估产及十年的估产均值",notes="根据年份查询区域及子区域作物三年的估产及十年的估产均值" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regionId",value = "区域id",required = true,dataType = "Long",paramType = "query",defaultValue = "2300000000"),
            @ApiImplicitParam(name = "cropId",value = "作物id",required = true,dataType = "int",paramType = "query",defaultValue = "504"),
            @ApiImplicitParam(name = "dataTime",value = "数据日期",required = true,dataType = "String",paramType = "query",defaultValue = "2016-08-30"),
            @ApiImplicitParam(name = "resolution",value = "数据精度/分辨率",required = true,dataType = "int",paramType = "query",defaultValue = "4006")
    })
    @GetMapping("/yieldForThree" )
    public ResultMessage queryYieldForThree(@RequestParam Long regionId,
                                            @RequestParam Integer cropId,
                                            @RequestParam String dataTime,
                                            @RequestParam Integer resolution){
        return yieldService.queryYieldForThree(regionId,cropId,dataTime,resolution);
    }



    @ApiOperation(value="查询该区域下各级区域的作物估产情况",notes="查询该区域下各级区域的作物估产情况" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regionId",value = "区域id",required = true,dataType = "Long",paramType = "query",defaultValue = "2300000000"),
            @ApiImplicitParam(name = "cropId",value = "作物id",required = true,dataType = "int",paramType = "query",defaultValue = "504"),
            @ApiImplicitParam(name = "dataTime",value = "数据日期",required = true,dataType = "String",paramType = "query",defaultValue = "2016-08-30"),
            @ApiImplicitParam(name = "resolution",value = "数据精度/分辨率",required = true,dataType = "int",paramType = "query",defaultValue = "4006"),
			@ApiImplicitParam(name = "isQueryLargeArea",value = "是否查大区",required = true, dataType = "int" ,paramType="query",defaultValue="0")
    })
    @GetMapping("/queryYieldInRegion")
    public ResultMessage queryYieldInRegion(@RequestParam Long regionId,
                                            @RequestParam Integer cropId,
                                            @RequestParam String dataTime,
                                            @RequestParam Integer resolution,
                                            @RequestParam Integer isQueryLargeArea
                                            ){
        return yieldService.queryYieldInRegion(regionId , cropId, dataTime, resolution,isQueryLargeArea);
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
			@ApiImplicitParam(name = "dataTime",value = "估产时间",required = true, dataType = "String" ,paramType="query",defaultValue="2016-08-30"),
			@ApiImplicitParam(name = "accuracyId",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4010")
	})
	@GetMapping("/queryRegionPercentage")
	public ResultMessage queryRegionPercentage(Long regionId,Integer cropId,String dataTime,Integer accuracyId){
		return  yieldService.queryRegionPercentage(regionId,cropId,dataTime,accuracyId);
	}
	

}
