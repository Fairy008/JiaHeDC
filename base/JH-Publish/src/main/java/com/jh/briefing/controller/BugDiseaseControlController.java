package com.jh.briefing.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.briefing.model.BugDiseaseControlParam;
import com.jh.briefing.service.IBugDiseaseControlService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * description:农作物害虫防治 Controller类
 * 1.根据区域，作物，生育期配置农作物害虫防治信息
 * @version <1> 2018-04-13 lxy： Created.
 * @version <2> 2018-04-19 cxw： update:1.修改增删改查方法；2.更新完善注释.
 */
@RestController
@RequestMapping("/bugDisease")
public class BugDiseaseControlController extends BaseController {

    @Autowired
    private IBugDiseaseControlService diseaseControlService;

    /**
     *农作物害虫防治信息分页查询
     * @param bugDiseaseControlParam 农作物害虫防治对象
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-19 cxw： update:重写方法
     */
    @ApiOperation(value = "农作物害虫防治信息分页查询",notes = "农作物害虫防治信息列表")
    @ApiImplicitParam(name = "bugDiseaseControlParam",value = "农作物害虫防治对象请求参数",required = false,dataType = "BugDiseaseControlParam")
    @PostMapping("queryByPage")
    public PageInfo<BugDiseaseControlParam> queryByPage(BugDiseaseControlParam bugDiseaseControlParam){
        return diseaseControlService.findBugDiseaseByPage(bugDiseaseControlParam);
    }


    /**
     * 农作物害虫防治信息记录新增
     * @param bugDiseaseControlParam 农作物害虫防治对象
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-19 cxw： update:重写方法
     */
    @ApiOperation(value = "新增农作物害虫防治信息",notes = "新增农作物害虫防治信息")
    @ApiImplicitParam(name = "bugDiseaseControlParam",value = "农作物害虫防治信息参数对象",required = false,dataType = "BugDiseaseControlParam")
    @PostMapping("add")
    public ResultMessage add(@RequestBody BugDiseaseControlParam bugDiseaseControlParam){
        return diseaseControlService.addBugDiseaseControl(bugDiseaseControlParam);
    }


    /**
     * 根据ID获取农作物害虫防治信息记录对象
     * @param bugDiseaseControlParam 农作物害虫防治对象
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-19 cxw： update:重写方法
     */
    @ApiOperation(value = "根据ID获取农作物害虫防治信息记录对象",notes = "根据ID获取农作物害虫防治信息记录对象")
    @ApiImplicitParam(name = "bugDiseaseControlParam",value = "农作物害虫防治信息请求参数对象",required = true,dataType = "BugDiseaseControlParam")
    @PostMapping("getById")
    public ResultMessage getById(@RequestBody BugDiseaseControlParam bugDiseaseControlParam){
        return diseaseControlService.getById(bugDiseaseControlParam.getBugId());
    }

    /**
     * 农作物害虫防治信息记录修改
     * @param bugDiseaseControlParam 农作物害虫防治对象
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-19 cxw： update:重写方法
     */
    @ApiOperation(value = "农作物害虫防治信息记录修改",notes = "农作物害虫防治信息记录修改")
    @ApiImplicitParam(name = "bugDiseaseControlParam",value = "农作物害虫防治信息参数对象",required = false,dataType = "BugDiseaseControlParam")
    @PostMapping("update")
    public ResultMessage update(@RequestBody BugDiseaseControlParam bugDiseaseControlParam){
        return diseaseControlService.updateBugDiseaseControl(bugDiseaseControlParam);
    }

    /**
     * 农作物害虫防治信息配置记录删除
     * @param bugId 农作物害虫防治信息ID
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-19 cxw： update:重写方法
     */
    @ApiOperation(value = "删除农作物害虫防治信息记录",notes = "删除农作物害虫防治信息记录")
    @ApiImplicitParam(name = "bugId",value = "农作物害虫防治信息ID",required = false,dataType = "Integer")
    @PostMapping("delete")
    public ResultMessage delete(@RequestParam Integer bugId){
        return  diseaseControlService.deleteBugDiseaseControl(bugId);
    }
}
