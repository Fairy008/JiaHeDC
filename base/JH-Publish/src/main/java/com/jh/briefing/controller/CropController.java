package com.jh.briefing.controller;

import com.jh.briefing.service.ICropService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 加载作物接口
 * @version <1> 2018-04-17 cxw : Created.
 */
@RestController
@RequestMapping("/crop")
public class CropController {

    @Autowired
    private ICropService cropService;

    /**
     * 根据区域加载作物
     *  regionId : 区域ID
     * @return
     * @version <1> 2018-04-17 cxw : Created.
     */
    @ApiOperation(value="查询作物列表",notes="根据区域查询作物列表" )
    @ApiImplicitParam(name = "regionId",value = "区域ID",required = false,dataType = "Long")
    @PostMapping("queryCropListByRegionId")
    public ResultMessage queryCropListByRegionId(@RequestParam Long regionId)
    {
            return cropService.findCropList(regionId);
    }
}
