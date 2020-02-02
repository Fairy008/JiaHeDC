package com.jh.forum.bbs.controller;

import com.jh.biz.feign.*;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.service.IDsService;
import com.jh.forum.bbs.vo.DsVo;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 数据集查询
 * @version <1> 2019-03-13 cxw:Created.
 */
@Api(value = "数据集接口",description = "数据集接口")
@RestController
@RequestMapping("/dataSet")
public class DsController extends BaseController {
    @Autowired
    private IRegionAndCropService regionAndCropService;

    @Autowired
    private IDsResolutionService dsResolutionService;

    @Autowired
    private IDsService dsService;

    /**
     * 根据区域查询所有数据集
     * regionId 区域ID
     * <1> 2019-03-13 cxw: Created.
     */
//    @ApiOperation(value="根据区域查询数据集数据",notes = "根据区域查询数据集数据")
//    @ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3102000000")
//    @GetMapping( "/queryDsListByRegionId")
//    public ResultMessage queryDsListByRegionId(Long regionId) {
//        ResultMessage  cropResult= regionAndCropService.queryCropListByRegionId(regionId);
//        ResultMessage  dsResolutionResult= dsResolutionService.queryDsResolutionList();
//        return  iDsService.findDsListByRegionId(cropResult,dsResolutionResult,regionId);
//    }

    /**
     * 根据区域时间查询所有数据集
     * regionId 区域ID
     * startDate 开始时间
     * endDate 结束时间
     * <1> 2019-03-13 cxw: Created.
     */
    @ApiOperation(value="根据行政区域和起止日期查询所有数据集数据",notes = "根据行政区域和起止日期查询所有数据集数据")
    @PostMapping( "/queryDsList")
    public ResultMessage queryDsList(@RequestBody DsVo dsVo) {

//        return  iDsService.findDsListByRegionIdAndDate(dsTributes,dsYields,dsGrowth,dsTrmms,dsTs,dsTemps);


        ResultMessage result = dsService.findDsList(dsVo);

        return result;

    }


}
