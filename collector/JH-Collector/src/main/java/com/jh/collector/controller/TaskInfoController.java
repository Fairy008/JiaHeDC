package com.jh.collector.controller;



import com.jh.biz.feign.IRegionService;
import com.jh.collector.entity.CalibrationSubtask;
import com.jh.collector.entity.Task;
import com.jh.collector.service.TaskInfoService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(value = "主任务",description = "主任务接口")
@RestController
@RequestMapping("/taskInfoController")
public class TaskInfoController extends BaseController{

    @Autowired
    private TaskInfoService taskInfoService;

    @Autowired
    private IRegionService regionService;


    /**
     * @Author：sxj
     * @Date：2019-07-31
     * 定标任务模块
     * 主任务列表查询
     * @param keyWord  区域id:regionId,创建人:creator
     * @return
     */
    @ApiOperation(value = "主任务列表查询",notes = "主任务列表查询 taskList")
    @ApiImplicitParam(name = "keyWord",value = "主任务列表查询",required = true,dataType = "Integer")
    @GetMapping(value = "/queryTaskList")
    public ResultMessage queryTaskList(@RequestParam(value = "keyWord",required = false) Long keyWord){
        Integer accountId = getCurrentAccountId();
        return taskInfoService.queryTaskList(accountId,keyWord);
    }

    /**
     * 查询任务信息
     * @param taskId  任务id
     * @return
     */

    @ApiOperation(value = "查询任务信息",notes = "查询任务信息")
    @ApiImplicitParam(name = "taskId",value = "子任务列表",required = true,dataType = "Integer")
    @GetMapping(value = "/queryTaskById")
    public ResultMessage queryTaskById(@RequestParam(value = "taskId",required = true) Integer taskId){
        Map map = new HashMap();
        Task task = taskInfoService.queryTaskById(taskId);
        map.put("taskName",task.getTaskName());
        ResultMessage resultMessage = regionService.queryRegionListByParentId(task.getRegionId());
        map.put("regionList",resultMessage.getData());
      return ResultMessage.success(map);
    }


/**
     * 查询子任务列表
     * @param subTaskId  任务id
     * @return
     *//*

    @ApiOperation(value = "查询行政村详细信息",notes = "查询行政村详细信息")
    @ApiImplicitParam(name = "taskId",value = "子任务详情",required = true,dataType = "Long")
    @PostMapping(value = "/queryVillageDetail")
    public ResultMessage queryTaskList(Integer subTaskId){
        return taskInfoService.queryVillageDetail(subTaskId);
    }
*/


    /**
     * 移动端 获取用户的任务列表
     * @param phone 手机号
     * XZG 2019-07-29  13:09
     * @return
     */
    @ApiOperation(value = "获取任务列表",notes = "获取用户的所有主任务和子任务")
    @ApiImplicitParam(name = "phone",value = "手机号",required = true,dataType = "String")
    @GetMapping("/taskList")
    public ResultMessage findTaskList(@RequestParam(value = "phone",required = true) String phone){
        return taskInfoService.findAllTaskByPhone(phone);
    }



    /**
     * @author sxj
     * @date 2019-07-30
     * 任务删除
     * @param taskId
     * @return
     */
    @ApiOperation(value = "任务删除",notes = "任务删除")
    @ApiImplicitParam(name = "taskId",value = "主任务id",required = true,dataType = "integer")
    @PostMapping("/deleteTask")
    public ResultMessage deleteTask(@RequestParam(value = "taskId",required = true) int taskId){
            return taskInfoService.deleteByTaskId(taskId);
    }


    @ApiOperation(value = "创建任务",notes = "创建任务")
    @ApiImplicitParam(name = "taskId",value = "主任务id",required = true,dataType = "integer")
    @PostMapping("/addTask")
    public ResultMessage addTask(HttpServletRequest request, @RequestBody Task task){
        return  taskInfoService.addTask(task);
    }

}
