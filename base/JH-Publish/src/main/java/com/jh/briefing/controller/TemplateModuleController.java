package com.jh.briefing.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.briefing.entity.TemplateModule;
import com.jh.briefing.model.TemplateModuleParam;
import com.jh.briefing.service.ITemplateModuleService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 模板模块 Controller类
 * 1.根据区域，作物，生育期配置模板模块信息
 * @version <1> 2018-04-22 lxy： Created.
 */
@RestController
@RequestMapping("/templateModule")
public class TemplateModuleController extends BaseController {

    @Autowired
    private ITemplateModuleService templateModuleService;

    /**
     *模板模块信息分页查询
     * @param templateModuleParam 模板模块对象
     * @return
     * @version <1> 2018-04-22 lxy： Created.
     */
    @ApiOperation(value = "模板模块信息分页查询",notes = "模板模块信息列表")
    @ApiImplicitParam(name = "templateModuleParam",value = "模板模块对象请求参数",required = false,dataType = "TemplateModuleParam")
    @PostMapping("queryByPage")
    public PageInfo<TemplateModuleParam> queryByPage(TemplateModuleParam templateModuleParam){
        return templateModuleService.queryTemplateModuleByPage(templateModuleParam);
    }

    /**
     *  根据模块名称检查是否有重名
     * @param moduleName 模板模块对象
     * @return 返回是否有重复
     * @version <1> 2018-06-13 lxy： Created.
     */
    @ApiOperation(value = "根据模块名称检查是否有重名",notes = "根据模块名称检查是否有重名")
    @ApiImplicitParam(name = "moduleName",value = "模块名称",required = false,dataType = "String")
    @GetMapping("/checkModuleExists")
    public ResultMessage checkModuleExists(@RequestParam("moduleName") String moduleName){
        TemplateModuleParam param = new TemplateModuleParam();
        param.setTemplateName(moduleName);//模板名称
        return templateModuleService.queryTemplateModuleByParam(param);
    }


    /**
     * 模板模块信息记录新增
     * @param templateModule 模板模块对象
     * @return
     * @version <1> 2018-04-22 lxy： Created.
     */
    @ApiOperation(value = "新增模板模块信息",notes = "新增模板模块信息")
    @ApiImplicitParam(name = "templateModule",value = "模板模块信息参数对象",required = false,dataType = "TemplateModule")
    @PostMapping("add")
    public ResultMessage add(@RequestBody TemplateModule templateModule){
        templateModule.setCreateTime(LocalDateTime.now());//设置现在时间
        return templateModuleService.saveTemplateModule(templateModule);
    }


    /**
     * 根据ID获取模板模块信息记录对象
     * @param templateId 模板模块对象
     * @return
     * @version <1> 2018-04-22 lxy： Created.
     */
    @ApiOperation(value = "根据ID获取模板模块信息记录对象",notes = "根据ID获取模板模块信息记录对象")
    @ApiImplicitParam(name = "templateId",value = "模板模块信息请求参数对象",required = true,dataType = "java.lang.Integer")
    @PostMapping("getById")
    public ResultMessage getById(@RequestParam Integer templateId){
        return templateModuleService.findTemplateModuleByTemplateId(templateId);
    }

    /**
     * 模板模块信息记录修改
     * @param templateModule 模板模块对象
     * @return
     * @version <1> 2018-04-22 lxy： Created.
     */
    @ApiOperation(value = "模板模块信息记录修改",notes = "模板模块信息记录修改")
    @ApiImplicitParam(name = "bugDiseaseControlParam",value = "模板模块信息参数对象",required = false,dataType = "templateModule")
    @PostMapping("update")
    public ResultMessage update(@RequestBody TemplateModule templateModule){
        templateModule.setModifyTime(LocalDateTime.now());//设置修改的时间
        return templateModuleService.updateTemplateModule(templateModule);
    }

    /**
     * 模板模块信息配置记录删除
     * @param templateId 模板模块信息ID
     * @return
     * @version <1> 2018-04-22 lxy： Created.
     */
    @ApiOperation(value = "删除模板模块信息记录",notes = "删除模板模块信息记录")
    @ApiImplicitParam(name = "templateId",value = "模板模块信息ID",required = false,dataType = "Integer")
    @PostMapping("delete")
    public ResultMessage delete(@RequestParam Integer templateId){
        return  templateModuleService.deleteByTemplateId(templateId);
    }

    /**
     * 查询所有模板信息
     * @return 返回所有的模块信息
     * @version <1> 2018-04-23 lxy： Created.
     */
    @ApiOperation(value = "查询所有模板信息",notes = "查询所有模板信息")
    @ApiImplicitParam(name = "templateModuleParam",value = "模板模块对象请求参数",required = false,dataType = "TemplateModuleParam")
    @PostMapping("queryAll")
    public ResultMessage queryAllTemplateModules(){
        return templateModuleService.queryAllTemplateModules();
    }
}
