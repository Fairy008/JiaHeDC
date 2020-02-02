package com.jh.collection.controller;

import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.CollectionTemplate;
import com.jh.collection.entity.query.CollectionTemplateQuery;
import com.jh.collection.service.ICollectionTemplateService;
import com.jh.forum.base.controller.BaseController;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.jh.constant.CommonDefineEnum.REQUEST_FAIL;

/**
 * 用户采集模板controller
 * @version <1> 2018-12-04 xy： Created.
 */
@Api(value = "采集模板",description="用户采集模板服务")
@RestController
@RequestMapping("/collection/template/")
public class CollectionTemplateController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(CollectionTemplateController.class);

    @Resource(name="collectionTemplateServiceImpl")
    private ICollectionTemplateService collectionTemplateService;

    @ModelAttribute
    public CollectionTemplate get(@RequestParam(required=false) Integer id) {
        CollectionTemplate entity = null;
        if (id !=null){
            entity = collectionTemplateService.getById(id);
        }
        if (entity == null){
            entity = new CollectionTemplate();
        }
        return entity;
    }
    /**
     * 采集任务模板分页查询
     * @param collectionTemplateQuery 请求查询参数
     * @return PageInfo
     * @version <1> 2018-11-19 xy： Created.
     */
    @ApiOperation(value = "采集任务模板分页查询",notes = "采集任务模板分页查询")
    @ApiImplicitParam(name = "collectionTemplateQuery",value = "采集任务模板参数",required = false,dataType = "CollectionTemplate")
    @PostMapping("findByPage")
    public PageInfo<CollectionTemplate> findByPage(CollectionTemplateQuery collectionTemplateQuery){
        return collectionTemplateService.findByPage(collectionTemplateQuery);
    }
    /**
     * 采集任务模板Api分页查询
     * @param collectionTemplateQuery 请求查询参数
     * @return PageInfo
     * @version <1> 2018-11-19 xy： Created.
     */
    @ApiOperation(value = "采集任务模板分页查询",notes = "采集任务模板分页查询")
    @ApiImplicitParam(name = "collectionTemplateQuery",value = "采集任务模板参数",required = false,dataType = "CollectionTemplate")
    @PostMapping("findApiByPage")
    public ResultMessage findApiByPage( CollectionTemplateQuery collectionTemplateQuery){
        PageInfo<CollectionTemplate> pageInfo = null;
        try {
            pageInfo = collectionTemplateService.findByPage(collectionTemplateQuery);
        }catch (Exception e){
            return ResultMessage.fail(REQUEST_FAIL.getMesasge());
        }
        return ResultMessage.success(pageInfo);
    }
    /**
     * 新增采集任务模板
     * @param collectionTemplate 新增采集任务模板
     * @return
     * @version <1> 2018-12-04 xy： Created.
     */
    @ApiOperation(value = "新增采集任务模板",notes = "新增采集任务模板")
    @ApiImplicitParam(name = "collectionTask",value = "新增采集任务模板",required = false,dataType = "CollectionTemplate")
    @PostMapping("add")
    public ResultMessage add(@RequestBody CollectionTemplate collectionTemplate){
        collectionTemplate.setPhone(this.getCurrentAccount());
        return  collectionTemplateService.addTemplate(collectionTemplate);
    }

    /**
     * 根据ID获取采集任务模板
     * @param collectionTemplate 采集任务模板对象
     * @return
     * @version <1> 2018-11-19 xy： Created.
     */
    @ApiOperation(value = "根据ID获取采集任务对象",notes = "根据ID获取采集任务对象")
    @ApiImplicitParam(name = "collectionTemplate",value = "采集任务对象",required = true,dataType = "CollectionTemplate")
    @PostMapping("getById")
    public ResultMessage getById(CollectionTemplate collectionTemplate){
        return ResultMessage.success(collectionTemplate);
    }

    /**
     * 更新采集任务模板
     * @param collectionTemplate 采集任务模板对象
     * @return
     * @version <1> 2018-11-19 xy： Created.
     * @version <2> 2018-11-19 xy： update:重写方法
     */
    @ApiOperation(value = "更新采集任务模板",notes = "更新采集任务模板")
    @ApiImplicitParam(name = "CollectionTemplate",value = "更新采集任务模板",required = false,dataType = "CollectionTemplate")
    @PostMapping("update")
    public ResultMessage update(@RequestBody CollectionTemplate collectionTemplate){
        return collectionTemplateService.updateTemplate(collectionTemplate);
    }

    /**
     * 删除采集任务
     * @param collectionTemplate 删除采集任务
     * @return
     * @version <1> 2018-11-19 xy： Created
     * @version <2> 2018-11-19 xy： update:重写方法
     */
    @ApiOperation(value = "删除采集任务",notes = "删除采集任务")
    @PostMapping("delete")
    public ResultMessage delete( CollectionTemplate collectionTemplate){
        return collectionTemplateService.deleteTemplate(collectionTemplate);
    }

    /**
     * 删除采集任务
     * @param collectionTemplate 删除采集任务
     * @return
     * @version <1> 2018-11-19 xy： Created
     * @version <2> 2018-11-19 xy： update:重写方法
     */
    @ApiOperation(value = "api删除采集任务",notes = "api删除采集任务")
    @PostMapping("apiDelete")
    public ResultMessage apiDelete(@RequestBody CollectionTemplate collectionTemplate){
        return collectionTemplateService.deleteTemplate(collectionTemplate);
    }
}
