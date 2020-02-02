package com.jh.cltApp.controller;

import com.github.pagehelper.PageInfo;
import com.jh.cltApp.entity.CltTemplate;
import com.jh.cltApp.service.ICltTemplateService;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.base.feign.IUserService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 采集数据模板controller
 * @version <1> 2019/4/9 16:37 zhangshen:Created.
 */
@Api(value = "采集数据模板",description = "采集数据模板")
@RestController
@RequestMapping("/cltTemplate")
public class CltTemplateController extends BaseController {

    @Autowired
    private ICltTemplateService cltTemplateService;

    /**
     * 查询我的所有模板(带分页)
     * @param 
     * @return 
     * @version <1> 2019/4/10 10:04 zhangshen:Created.
     */
    @ApiOperation(value = "查询我的所有模板(带分页)",notes = "查询我的所有模板(带分页)")
    @ApiImplicitParam(name = "cltTemplate",value = "模板实体",required = true,dataType = "CltTemplate")
    @PostMapping("/findCltTemplatePageInfo")
    public PageInfo<CltTemplate> findCltTemplatePageInfo(@RequestBody CltTemplate cltTemplate, @RequestParam String phone){
        cltTemplate.setCreator(getCurrentAccountIdApp(phone));
        return cltTemplateService.findCltTemplatePageInfo(cltTemplate);
    }

    /**
     * 查询我的所有模板(不带分页)
     * @param
     * @return 
     * @version <1> 2019/4/11 13:21 zhangshen:Created.
     */
    @ApiOperation(value = "查询我的所有模板(不带分页)",notes = "查询我的所有模板(不带分页)")
    @ApiImplicitParam(name = "cltTemplate",value = "模板实体",required = true,dataType = "CltTemplate")
    @PostMapping("/findCltTemplateList")
    public ResultMessage findCltTemplateList(@RequestBody CltTemplate cltTemplate, @RequestParam String phone){
        cltTemplate.setCreator(getCurrentAccountIdApp(phone));
        return cltTemplateService.findCltTemplateList(cltTemplate);
    }

    /**
     * 新增模板
     * @param 
     * @return 
     * @version <1> 2019/4/10 14:01 zhangshen:Created.
     */
    @ApiOperation(value = "新增模板",notes = "新增模板")
    @ApiImplicitParam(name = "cltTemplate",value = "模板实体",required = true,dataType = "CltTemplate")
    @PostMapping("/createCltTemplate")
    public ResultMessage createCltTemplate(@RequestBody CltTemplate cltTemplate, @RequestParam String phone){
        cltTemplate.setCreator(getCurrentAccountIdApp(phone));
        cltTemplate.setCreatorName(getCurrentNickNameApp(phone));
        cltTemplate.setModifier(getCurrentAccountIdApp(phone));
        cltTemplate.setModifierName(getCurrentNickNameApp(phone));
        return cltTemplateService.createCltTemplate(cltTemplate);
    }

    /**
     * 没有使用的模板支持删除
     * @param
     * @return 
     * @version <1> 2019/4/11 13:23 zhangshen:Created.
     */
    @ApiOperation(value = "没有使用的模板支持删除",notes = "没有使用的模板支持删除")
    @ApiImplicitParam(name = "cltTemplate",value = "模板实体",required = true,dataType = "CltTemplate")
    @PostMapping("/deleteCltTemplate")
    public ResultMessage deleteCltTemplate(@RequestBody CltTemplate cltTemplate){
        return cltTemplateService.deleteCltTemplate(cltTemplate.getTemplateId());
    }
}
