package com.jh.collector.controller;

import com.jh.collector.entity.CalibrationSubtask;
import com.jh.collector.service.CalibrationSubtaskService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@RequestMapping("/subTaskController")
public class CalibrationSubTaskController {

    @Autowired
    private CalibrationSubtaskService calibrationSubtaskService;

    /**
     * 查询任务列表
     * @param taskId  任务id
     * @return
     */
    @ApiOperation(value = "查询任务列表",notes = "查询任务列表")
    @ApiImplicitParam(name = "taskId",value = "任务列表",required = true,dataType = "Integer")
    @GetMapping(value = "/querySubTaskList")
    public ResultMessage querySubTaskList(Integer taskId){
        List<CalibrationSubtask> list = calibrationSubtaskService.findAllSubTaskByTaskId(taskId);
        return ResultMessage.success(list);
    }
    /**
     * 查询行政村列表
     * @param calibrationSubtask  任务id 行政村 定标人 任务状态
     * @return
     */
    @ApiOperation(value = "查询行政村列表",notes = "查询行政村列表")
    @ApiImplicitParam(name = "calibrationSubtask",value = "行政村列表",required = true,dataType = "CalibrationSubtask")
    @GetMapping(value = "/queryTaskVillage")
    public ResultMessage queryTaskVillage(CalibrationSubtask calibrationSubtask){
        List<CalibrationSubtask> list = calibrationSubtaskService.queryTaskVillage(calibrationSubtask);
        return ResultMessage.success(list);
    }

}
