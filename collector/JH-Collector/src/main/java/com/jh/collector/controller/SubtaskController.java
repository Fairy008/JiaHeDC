package com.jh.collector.controller;

import com.jh.collector.service.CalibrationSubtaskService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 子任务
 * XZG 2019-07-30  14:00
 */
@Api(value = "子任务",description = "子任务接口")
@RestController
@RequestMapping("/subtask")
public class SubtaskController {

    @Autowired
    private CalibrationSubtaskService subtaskService;

    /**
     * 绑定子任务的定标人
    * XZG 2019-07-29  13:09
    * @return
    */
    @ApiOperation(value = "绑定定标人",notes = "任务下载前给子任务绑定定标人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subtaskId",value = "子任务主键",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "phone",value = "手机号",required = true,dataType = "String")
    })
    @PostMapping("/relsteCalibrator")
    public ResultMessage relsteCalibrator(@RequestParam(value = "subtaskId",required = true) Integer subtaskId,
                                          @RequestParam(value = "phone",required = true) String phone){
        return subtaskService.relateSubtaskCalibrator(subtaskId,phone);
    }

}
