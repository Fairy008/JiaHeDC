package com.jh.briefingNew.controller;

import com.jh.briefingNew.service.IBriefingCropService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:根据区域加载作物
 *
 * @version <1> 2018-07-23 wl: Created.
 */
@RestController
@RequestMapping("/briefCrop")
public class BriefingCropController {
    @Autowired
    private IBriefingCropService briefingCropService;

    /**
     * 根据区域加载作物
     *  regionId : 区域ID
     * @return
     * @version <1> 2018-07-23 wl : Created.
     */
    @ApiOperation(value="查询作物列表",notes="根据区域查询作物列表" )
    @ApiImplicitParam(name = "regionId",value = "区域ID",required = false,dataType = "Long")
    @PostMapping("queryCropListByRegionId")
    public ResultMessage queryCropListByRegionId(@RequestParam Long regionId)
    {
        return briefingCropService.findCropList(regionId);
    }
}
