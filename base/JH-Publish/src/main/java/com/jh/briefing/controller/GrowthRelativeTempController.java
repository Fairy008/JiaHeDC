package com.jh.briefing.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.briefing.entity.GrowthRelativeGroundTemp;
import com.jh.briefing.model.GrowthRelativeGroundTempParam;
import com.jh.briefing.service.IGrowthRelativeGroundTempService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 物候期地温关系 Controller类
 *
 * @version <1> 2018-04-13 lxy： Created.
 */
@RestController
@RequestMapping("/growthTemp")
public class GrowthRelativeTempController extends BaseController {

    @Autowired
    private IGrowthRelativeGroundTempService growthRelativeGroundTempService;

    /**
     *农作物候期地温关系信息分页查询
     * @param growthRelativeGroundTempParam
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     */
    @ApiOperation(value = "物候期地温关系信息分页查询",notes = "物候期地温关系信息列表")
    @ApiImplicitParam(name = "growthRelativeGroundTempParam",value = "物候期地温关系对象请求参数",required = false,dataType = "GrowthRelativeGroundTempParam")
    @PostMapping("findByPage")
    public PageInfo<GrowthRelativeGroundTempParam> findByPage(GrowthRelativeGroundTempParam growthRelativeGroundTempParam){
        return growthRelativeGroundTempService.findByPage(growthRelativeGroundTempParam);
    }


    /**
     * 物候期地温关系信息记录新增
     * @param request
     * @param growthRelativeGroundTemp
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     */
    @ApiOperation(value = "新增物候期地温关系信息",notes = "新增物候期地温关系信息")
    @ApiImplicitParam(name = "growthRelativeGroundTemp",value = "物候期地温关系信息参数对象",required = false,dataType = "GrowthRelativeGroundTemp")
    @PostMapping("add")
    public ResultMessage add(HttpServletRequest request, @RequestBody GrowthRelativeGroundTemp growthRelativeGroundTemp){
        ResultMessage result = ResultMessage.fail();
        result = growthRelativeGroundTempService.saveGrowthRelativeGroundTemp(growthRelativeGroundTemp);
        return result;
    }


    /**
     * 根据ID获取物候期地温关系信息记录对象
     * @param relativeId
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     */
    @ApiOperation(value = "根据ID获取物候期地温关系信息记录对象",notes = "根据ID获取物候期地温关系信息记录对象")
    @ApiImplicitParam(name = "relativeId",value = "物候期地温关系信息请求参数对象",required = true,dataType = "relativeId")
    @PostMapping("getById")
    public ResultMessage getById(@RequestParam("relativeId") Integer relativeId){
        return growthRelativeGroundTempService.findGrowthRelativeTempById(relativeId);
    }

    /**
     * 物候期地温关系修改
     * @param request
     * @param growthRelativeGroundTemp
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     */
    @ApiOperation(value = "物候期地温关系信息记录修改",notes = "物候期地温关系信息记录修改")
    @ApiImplicitParam(name = "growthRelativeGroundTemp",value = "物候期地温关系信息参数对象",required = false,dataType = "BugDiseaseControl")
    @PostMapping("update")
    public ResultMessage update(HttpServletRequest request, @RequestBody GrowthRelativeGroundTemp growthRelativeGroundTemp){
        ResultMessage result = ResultMessage.fail();
        result = growthRelativeGroundTempService.updateGrowthRelativeGroundTemp(growthRelativeGroundTemp);
        return result;
    }

    /**
     * 物候期地温关系删除
     * @param request
     * @param relativeId
     * @return
     * @version <1> 2018-04-19 lxy： Created.
     */
    @ApiOperation(value = "删除物候期地温关系信息记录",notes = "删除物候期地温关系信息记录")
    @ApiImplicitParam(name = "relativeId",value = "物候期地温关系信息记录请求参数",required = false,dataType = "Integer")
    @PostMapping("delete")
    public ResultMessage delete(HttpServletRequest request, @RequestParam("relativeId") Integer relativeId){
        ResultMessage result = ResultMessage.fail();
        result = growthRelativeGroundTempService.deleteGrowthRelativeTemp(relativeId);
        return result;
    }
}
