package com.jh.collection.controller.nolog;

import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.CollectionTaskItem;
import com.jh.collection.service.ICollectionTaskItemService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.jh.constant.CommonDefineEnum.REQUEST_FAIL;

/**
 * 用户采集子任务controller
 * @version <1> 2018-12-04 xy： Created.
 */
@Api(value = "nolog采集子任务",description="nolog用户采集子任务服务")
@RestController
@RequestMapping("/nolog/collection/taskItem/")
public class NologCollectionTaskItemController {

    @Autowired
    private ICollectionTaskItemService collectionTaskItemService;

    @ModelAttribute
    public CollectionTaskItem get(@RequestParam(required=false) Integer id) {
        CollectionTaskItem entity = null;
        if (id !=null){
            entity = collectionTaskItemService.getById(id);
        }
        if (entity == null){
            entity = new CollectionTaskItem();
        }
        return entity;
    }

    /**
     * 采集任务模板分页查询
     * @param collectionTaskItemQuery 请求查询参数
     * @return PageInfo
     * @version <1> 2018-11-19 xy： Created.
     */
    @ApiOperation(value = "采集子任务Api分页查询",notes = "采集子任务分页查询")
    @ApiImplicitParam(name = "collectionTaskItemQuery",value = "采集子任务参数",required = false,dataType = "CollectionTaskItem")
    @PostMapping("findApiByPage")
    public ResultMessage findApiByPage(@RequestBody CollectionTaskItem collectionTaskItemQuery){
        PageInfo<CollectionTaskItem> pageInfo = null;
        try{
            pageInfo = collectionTaskItemService.findByPage(collectionTaskItemQuery);
        }catch(Exception e){
            return ResultMessage.fail(REQUEST_FAIL.getMesasge());
        }
        return ResultMessage.success(pageInfo);
    }

    /**
     * 根据ID获取采集任务模板
     * @param collectionTaskItem 采集任务模板对象
     * @return
     * @version <1> 2018-11-19 xy： Created.
     */
    @ApiOperation(value = "根据ID获取采集任务对象",notes = "根据ID获取采集任务对象")
    @ApiImplicitParam(name = "collectionTaskItem",value = "采集任务对象",required = true,dataType = "CollectionTaskItem")
    @PostMapping("getById")
    public ResultMessage getById(CollectionTaskItem collectionTaskItem){
        return ResultMessage.success(collectionTaskItem);
    }
}
