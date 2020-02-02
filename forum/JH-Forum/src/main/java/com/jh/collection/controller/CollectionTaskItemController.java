package com.jh.collection.controller;

import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.CollectionTaskItem;
import com.jh.collection.service.ICollectionTaskItemService;
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
 * 用户采集子任务controller
 * @version <1> 2018-12-04 xy： Created.
 */
@Api(value = "采集子任务",description="用户采集子任务服务")
@RestController
@RequestMapping("/collection/taskItem/")
public class CollectionTaskItemController {

    private static final Logger LOG = LoggerFactory.getLogger(CollectionTaskItemController.class);

    @Resource(name="collectionTaskItemServiceImpl")
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
    @ApiOperation(value = "采集子任务分页查询",notes = "采集子任务分页查询")
    @ApiImplicitParam(name = "collectionTaskItemQuery",value = "采集子任务参数",required = false,dataType = "CollectionTaskItem")
    @PostMapping("findByPage")
    public PageInfo<CollectionTaskItem> findByPage(CollectionTaskItem collectionTaskItemQuery){
        return collectionTaskItemService.findByPage(collectionTaskItemQuery);
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
     * 新增采集任务模板
     * @param collectionTaskItem 新增采集任务模板
     * @return
     * @version <1> 2018-12-04 xy： Created.
     */
    @ApiOperation(value = "新增采集子任务",notes = "新增采集子任务")
    @ApiImplicitParam(name = "collectionTaskItem",value = "新增采集子任务",required = false,dataType = "CollectionTaskItem")
    @PostMapping("add")
    public ResultMessage add(@RequestBody CollectionTaskItem collectionTaskItem){
        return  collectionTaskItemService.addTaskItem(collectionTaskItem);
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

    /**
     * 根据ID获取Api采集任务模板
     * @param collectionTaskItem 采集任务模板对象
     * @return
     * @version <1> 2018-11-19 xy： Created.
     */
    @ApiOperation(value = "根据ID获取API采集任务对象",notes = "根据ID获取API采集任务对象")
    @ApiImplicitParam(name = "collectionTaskItem",value = "采集任务对象",required = true,dataType = "CollectionTaskItem")
    @GetMapping("getApiById")
    public ResultMessage getApiById(CollectionTaskItem collectionTaskItem){
        return ResultMessage.success(collectionTaskItemService.getById(collectionTaskItem.getId()));
    }

    /**
     * 更新采集任务模板
     * @param collectionTaskItem 采集任务模板对象
     * @return
     * @version <1> 2018-11-19 xy： Created.
     * @version <2> 2018-11-19 xy： update:重写方法
     */
    @ApiOperation(value = "更新采集任务信息",notes = "更新采集任务信息")
    @ApiImplicitParam(name = "CollectionTaskItem",value = "更新任务信息",required = false,dataType = "CollectionTaskItem")
    @PostMapping("update")
    public ResultMessage update(@RequestBody CollectionTaskItem collectionTaskItem){
        return collectionTaskItemService.updateTaskItem(collectionTaskItem);
    }

    /**
     * 删除采集任务
     * @param collectionTaskItem 删除采集任务
     * @return
     * @version <1> 2018-11-19 xy： Created
     * @version <2> 2018-11-19 xy： update:重写方法
     */
    @ApiOperation(value = "删除采集任务",notes = "删除采集任务")
    @PostMapping("delete")
    public ResultMessage delete( CollectionTaskItem collectionTaskItem){
        return collectionTaskItemService.deleteTaskItem(collectionTaskItem);
    }

    @ApiOperation(value = "删除采集任务",notes = "删除采集任务")
    @PostMapping("apiDelete")
    public ResultMessage apiDelete(@RequestBody CollectionTaskItem collectionTaskItem){
        return collectionTaskItemService.deleteTaskItem(collectionTaskItem);
    }

    /**
     * 获取所有经纬度坐标
     * @param collectionTaskItemQuery
     * @return
     */
    @ApiOperation(value = "获取所有经纬度坐标",notes = "获取所有经纬度坐标")
    @ApiImplicitParam(name = "collectionTaskItemQuery",value = "获取所有经纬度坐标",required = false,dataType = "CollectionTaskItem")
    @PostMapping("getCoordinates")
    public ResultMessage getCoordinates(@RequestBody CollectionTaskItem collectionTaskItemQuery){
        return collectionTaskItemService.getCoordinates(collectionTaskItemQuery);
    }

    /**
     * 根据任务ID获取所有经纬度坐标
     * @param taskId
     * @return
     */
   /* @ApiOperation(value = "获取所有经纬度坐标",notes = "获取所有经纬度坐标")
    @ApiImplicitParam(name = "taskId",value = "任务Id",required = false,dataType = "Integer")
    @PostMapping("getCoordinatesByTaskId")
    public ResultMessage getCoordinatesByTaskId(@RequestParam(required=false) Integer taskId){
        return collectionTaskItemService.getCoordinatesByTaskId(taskId);
    }*/
}
