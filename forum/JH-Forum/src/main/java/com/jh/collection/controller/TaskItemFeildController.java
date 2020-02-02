package com.jh.collection.controller;

import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.TaskItemFeild;
import com.jh.collection.entity.vo.CollectionFeildValue;
import com.jh.collection.service.ITaskItemFeildService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户采集子任务关联采集字段controller
 * @version <1> 2018-12-04 xy： Created.
 */
@Api(value = "采集子任务关联采集字段",description="采集子任务关联采集字段服务")
@RestController
@RequestMapping("/collection/taskFeild/")
public class TaskItemFeildController {

    private static final Logger LOG = LoggerFactory.getLogger(TaskItemFeildController.class);

    @Resource(name="taskItemFeildServiceImpl")
    private ITaskItemFeildService taskItemFeildService;

    /**
     * 用户采集子任务关联采集字段
     * @return PageInfo
     * @version <1> 2018-11-19 xy： Created.
     */
    @ApiOperation(value = "用户采集子任务关联采集字段分页查询",notes = "用户采集子任务关联采集字段分页查询")
    @ApiImplicitParam(name = "taskItemFeildQuery",value = "用户采集子任务关联采集字段参数",required = false,dataType = "TaskItemFeild")
    @PostMapping("findByPage")
    public PageInfo<TaskItemFeild> findByPage(TaskItemFeild taskItemFeildQuery){
        return taskItemFeildService.findByPage(taskItemFeildQuery);
    }

    @PostMapping("findByList")
    public PageInfo<TaskItemFeild> findByList(@RequestBody TaskItemFeild taskItemFeildQuery){
        return taskItemFeildService.findByPage(taskItemFeildQuery);
    }

    /**
     * 更新采集数据
     * @param collectionFeildValue 更新采集数据
     * @return
     * @version <1> 2018-12-04 xy： Created.
     */
    @ApiOperation(value = "更新采集字段数据",notes = "用户采集子任务关联采集字段")
    @ApiImplicitParam(name = "taskItemFeild",value = "用户采集子任务关联采集字段",required = true,dataType = "ApiImplicitParam")
    @PostMapping("updateData")
    public ResultMessage updateData(@RequestBody CollectionFeildValue collectionFeildValue){
        return  taskItemFeildService.updateData(collectionFeildValue);
    }

    /**
     * 根据ID获取采集任务模板
     * @param taskItemFeild 采集任务模板对象
     * @return
     * @version <1> 2018-11-19 xy： Created.
     */
    @ApiOperation(value = "用户采集子任务关联采集字段对象",notes = "根据ID获取用户采集子任务关联采集字段对象")
    @ApiImplicitParam(name = "taskItemFeild",value = "采集任务对象",required = true,dataType = "TaskItemFeild")
    @PostMapping("getById")
    public ResultMessage getById(TaskItemFeild taskItemFeild){
        return ResultMessage.success(taskItemFeildService.getById(taskItemFeild.getId()));
    }

    /**
     * 更新用户采集子任务关联采集字段
     * @param taskItemFeild 采集任务模板对象
     * @return
     * @version <1> 2018-12-04 xy： Created.
     */
    @ApiOperation(value = "更新用户采集子任务关联采集字段",notes = "更新用户采集子任务关联采集字段")
    @ApiImplicitParam(name = "taskItemFeild",value = "更新任务信息",required = false,dataType = "TaskItemFeild")
    @PostMapping("update")
    public ResultMessage update(@RequestBody TaskItemFeild taskItemFeild){
        return taskItemFeildService.updateTaskItemFeild(taskItemFeild);
    }

    /**
     * 删除用户采集子任务关联采集字段
     * @param )
    public ResultMessage delete( TaskItemFeild taskItemFeild 删除采集任务
     * @return
     * @version <1> 2018-12-04 xy： Created
     */
    @ApiOperation(value = "删除用户采集子任务关联采集字段",notes = "删除用户采集子任务关联采集字段")
    @PostMapping("delete")
    public ResultMessage delete( TaskItemFeild taskItemFeild){
        return taskItemFeildService.deleteTaskItemFeild(taskItemFeild);
    }
}
