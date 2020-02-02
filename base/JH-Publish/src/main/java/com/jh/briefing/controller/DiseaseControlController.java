package com.jh.briefing.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.briefing.model.DiseaseControlParam;
import com.jh.briefing.service.IDiseaseControlService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * description:农作物病情防治 Controller类
 * 1.根据区域，作物，生育期配置农作物病情防治信息
 * @version <1> 2018-04-13 lxy： Created.
 * @version <2> 2018-04-18 cxw： update:1.修改增删改查方法；2.更新完善注释.
 */
@RestController
@RequestMapping("/disease")
public class DiseaseControlController extends BaseController {

    @Autowired
    private IDiseaseControlService diseaseControlService;

    /**
     *农作物病情防治信息分页查询
     * @param diseaseControlParam 农作物病情防治对象
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-18 cxw： update:重写方法
     */
    @ApiOperation(value = "农作物病情防治信息分页查询",notes = "农作物病情防治信息列表")
    @ApiImplicitParam(name = "diseaseControlParam",value = "农作物病情防治对象请求参数",required = false,dataType = "DiseaseControlParam")
    @PostMapping("queryByPage")
    public PageInfo<DiseaseControlParam> queryByPage(DiseaseControlParam diseaseControlParam){
        return diseaseControlService.findDiseaseByPage(diseaseControlParam);
    }


    /**
     * 农作物病情防治信息记录新增
     * @param diseaseControlParam 农作物病情防治对象
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-18 cxw： update:重写方法
     */
    @ApiOperation(value = "新增农作物病情防治信息",notes = "新增农作物病情防治信息")
    @ApiImplicitParam(name = "diseaseControlParam",value = "农作物病情防治信息参数对象",required = false,dataType = "DiseaseControlParam")
    @PostMapping("add")
    public ResultMessage add(@RequestBody DiseaseControlParam diseaseControlParam){
        return  diseaseControlService.addDiseaseControl(diseaseControlParam);
    }


    /**
     * 根据ID获取农作物病情防治信息记录对象
     * @param diseaseControlParam 农作物病情防治对象
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-18 cxw： update:重写方法
     */
    @ApiOperation(value = "根据ID获取农作物病情防治信息记录对象",notes = "根据ID获取农作物病情防治信息记录对象")
    @ApiImplicitParam(name = "diseaseControlParam",value = "农作物病情防治信息请求参数对象",required = true,dataType = "DiseaseControlParam")
    @PostMapping("getById")
    public ResultMessage getById(@RequestBody DiseaseControlParam diseaseControlParam){
        return diseaseControlService.getById(diseaseControlParam.getDiseaseId());
    }

    /**
     * 农作物病情防治信息记录记录修改
     * @param diseaseControlParam 农作物病情防治对象
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-18 cxw： update:重写方法
     */
    @ApiOperation(value = "农作物病情防治信息记录修改",notes = "农作物病情防治信息记录修改")
    @ApiImplicitParam(name = "diseaseControlParam",value = "农作物病情防治信息参数对象",required = false,dataType = "DiseaseControlParam")
    @PostMapping("update")
    public ResultMessage update(@RequestBody DiseaseControlParam diseaseControlParam){
        return diseaseControlService.updateDiseaseControl(diseaseControlParam);
    }

    /**
     * 农作物病情防治信息记录删除
     * @param diseaseId 农作物病情防治对象ID
     * @return
     * @version <1> 2018-04-12 lxy： Created
     * @version <2> 2018-04-18 cxw： update:重写方法
     */
    @ApiOperation(value = "删除农作物病情防治信息记录",notes = "删除农作物病情防治信息记录")
    @ApiImplicitParam(name = "diseaseId",value = "农作物病情防治信息ID",required = false,dataType = "Integer")
    @PostMapping("delete")
    public ResultMessage delete( @RequestParam Integer diseaseId){
        return diseaseControlService.deleteDiseaseControl(diseaseId);
    }
}
