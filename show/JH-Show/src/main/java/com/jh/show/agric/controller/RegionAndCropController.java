package com.jh.show.agric.controller;

import com.jh.biz.feign.IRegionAndCropService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 区域与作物配置接口
 * 1.根据区域ID查询关联作物
 * @version <1> 2018-08-13 cxw: Created.
 */
@Api(value = "区域与作物配置接口",description="区域与作物配置接口")
@RestController
@RequestMapping("/regionAndCrop")
public class RegionAndCropController {

    @Autowired
    private IRegionAndCropService regionAndCropService;

    /**
     * 根据区域查询作物
     * regionId 区域ID
     * <1> 2018-8-13 cxw: Created.
     */
    @ApiOperation(value="根据区域查询作物",notes = "根据区域查询作物")
    @ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3102000006")
    @GetMapping( "queryCropListByRegionId")
    public ResultMessage queryCropListByRegionId(Long regionId) {
        return  regionAndCropService.queryCropListByRegionId(regionId);
    }
}
