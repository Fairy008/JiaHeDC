package com.jh.collector.controller;

import com.jh.collector.entity.CalibrationSubtask;
import com.jh.collector.entity.PolicyInfo;
import com.jh.collector.service.PolicyInfoService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 保单信息
 * XZG 2019-07-30  15:30
 */
@Api(value = "保单信息",description = "保单信息")
@RestController
@RequestMapping("/policyInfo")
public class PolicyInfoController {

    @Autowired
    private PolicyInfoService policyInfoService;

    /**
     * 获取子任务下的所有保单
     * @param subtaskId  子任务id
    * xzg 2019/7/30 16:38
    * @return
    */
    @ApiOperation(value = "获取保单",notes = "获取子任务下的所有保单")
    @ApiImplicitParam(name = "subtaskId",value = "子任务主键",required = true,dataType = "Integer")
    @GetMapping("/policyInfoList")
    public ResultMessage policyInfoList(@RequestParam(value = "subtaskId") Integer subtaskId){
        return policyInfoService.findPolicyListBySubtaskId(subtaskId);
    }


    /**
     * @author sxj
     * @date 2019-07-31
     * 查询子任务下的所有保单列表
     * @param villageId
     * @param keyWord
     * @return
     */
    @ApiOperation(value = "查询子任务下的所有保单列表",notes = "查询子任务下的所有保单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "villageId",value = "行政村id",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "keyWord",value = "保单号/身份证号",required = true,dataType = "Integer")
    })
    @GetMapping(value = "/queryVillageDetail")
    public ResultMessage queryVillageDetail(@RequestParam(value = "villageId",required = true) Integer villageId,
                                            @RequestParam(value = "keyWord",required = false) String keyWord){
        List<PolicyInfo> list = policyInfoService.queryVillageDetail(villageId,keyWord);
        return ResultMessage.success(list);
    }


}
