package com.jh.forum.cms.controller;

import com.github.pagehelper.PageInfo;
import com.jh.biz.feign.IPersonService;
import com.jh.cltApp.entity.CltTemplate;
import com.jh.cltApp.service.ICltTemplateService;
import com.jh.forum.base.controller.BaseController;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 采集数据模板后台管理controller
 * @version <1> 2019/4/9 16:37 lijie:Created.
 */
@Api(value = "采集数据模板后台管理",description = "采集数据模板后台管理")
@RestController
@RequestMapping("/cltTemplateManage")
public class CltTemplateManageController extends BaseController {

    @Autowired
    private ICltTemplateService cltTemplateService;
    @Autowired
    private IPersonService personService;

    /**
     * 查询我的所有模板(带分页)
     * @param 
     * @return 
     * @version <1> 2019/4/10 10:04 lijie:Created.
     */
    @ApiOperation(value = "查询我的所有模板(带分页)",notes = "查询我的所有模板(带分页)")
    @ApiImplicitParam(name = "cltTemplate",value = "模板实体",required = true,dataType = "CltTemplate")
    @PostMapping("/findByPage")
    public PageInfo<CltTemplate> findByPage(CltTemplate cltTemplate){
        cltTemplate.setCreator(getQueryCreator());
        return cltTemplateService.findByPage(cltTemplate);
    }

    /**
     * 查询我的所有模板(不带分页)
     * @param
     * @return
     * @version <1> 2019/4/11 13:21 lijie:Created.
     */
    @ApiOperation(value = "查询我的所有模板(不带分页)",notes = "查询我的所有模板(不带分页)")
    @ApiImplicitParam(name = "cltTemplate",value = "模板实体",required = false,dataType = "CltTemplate")
    @PostMapping("/findCltTemplateList")
    public ResultMessage findCltTemplateList(){
        CltTemplate cltTemplate = new CltTemplate();
        cltTemplate.setCreator(getCurrentAccountId());
        return cltTemplateService.findCltTemplateList(cltTemplate);
    }

    /**
     * 新增模板
     * @param
     * @return
     * @version <1> 2019/4/10 14:01 lijie:Created.
     */
    @ApiOperation(value = "新增模板",notes = "新增模板")
    @ApiImplicitParam(name = "cltTemplate",value = "模板实体",required = true,dataType = "CltTemplate")
    @PostMapping("/createCltTemplate")
    public ResultMessage createCltTemplate(@RequestBody CltTemplate cltTemplate){
        cltTemplate.setCreator(getCurrentAccountId());
        cltTemplate.setCreatorName(getCurrentNickName());
        cltTemplate.setModifier(getCurrentAccountId());
        cltTemplate.setModifierName(getCurrentNickName());
        return cltTemplateService.createCltTemplate(cltTemplate);
    }

    /**
     * 编辑模板
     * @param
     * @return
     * @version <1> 2019/4/10 14:01 lijie:Created.
     */
    @ApiOperation(value = "编辑模板",notes = "编辑模板")
    @ApiImplicitParam(name = "cltTemplate",value = "模板实体",required = true,dataType = "CltTemplate")
    @PostMapping("/updateCltTemplate")
    public ResultMessage updateCltTemplate(@RequestBody CltTemplate cltTemplate){
        cltTemplate.setModifier(getCurrentAccountId());
        cltTemplate.setModifierName(getCurrentNickName());
        return cltTemplateService.updateCltTemplate(cltTemplate);
    }

    /**
     * 根据Id查询模板详情
     * @param
     * @return
     * @version <1> 2019/4/10 14:01 lijie:Created.
     */
    @ApiOperation(value = "根据Id查询模板详情",notes = "根据Id查询模板详情")
    @ApiImplicitParam(name = "cltTemplate",value = "模板实体",required = true,dataType = "CltTemplate")
    @GetMapping("/getById")
    public ResultMessage getById(@RequestParam Integer templateId){
        return cltTemplateService.getById(templateId);
    }

    /**
     * 没有使用的模板支持删除
     * @param
     * @return 
     * @version <1> 2019/4/11 13:23 lijie:Created.
     */
    @ApiOperation(value = "没有使用的模板支持删除",notes = "没有使用的模板支持删除")
    @ApiImplicitParam(name = "cltTemplate",value = "模板实体",required = true,dataType = "CltTemplate")
    @PostMapping("/deleteCltTemplate")
    public ResultMessage deleteCltTemplate(@RequestBody CltTemplate cltTemplate){
        return cltTemplateService.deleteCltTemplate(cltTemplate.getTemplateId());
    }

    /**
     * 查询我的所有模板(不带分页)
     * @param
     * @return
     * @version <1> 2019/4/11 13:21 lijie:Created.
     */
    @ApiOperation(value = "验证模板名称是否重复",notes = "验证模板名称是否重复")
    @ApiImplicitParam(name = "cltTemplate",value = "模板实体",required = false,dataType = "CltTemplate")
    @PostMapping("/validTemplateName")
    public ResultMessage validTemplateName(@RequestBody CltTemplate cltTemplate){
        return cltTemplateService.validTemplateName(cltTemplate);
    }

    private Integer getQueryCreator() {
        Integer creator = getCurrentAccountId();
        ResultMessage result = personService.isExistRole(creator, "FORUM_ADMIN");
        Integer flag = (Integer) result.getData();
        if (flag > 0) creator = null;//是管理员则不加创建人条件
        return creator ;
    }
}
