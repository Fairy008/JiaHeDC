package com.jh.collector.controller;

import com.jh.collector.service.CalibrationInfoService;
import com.jh.collector.vo.SubmitSubTaskVo;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 定标信息
 */
@Api(value = "定标信息",description = "定标信息接口")
@RestController
@RequestMapping("/calibrationInfo")
public class CalibrationInfoController {

    @Autowired
    private CalibrationInfoService calibrationInfoService;

    /**
     * 上传地块的图片
     * @param request  地块图片文件
     * XZG 2019-07-29  13:09
     * @return
     */
    @ApiOperation(value = "上传地块的图片",notes = "上传图片列表 fileList")
    @ApiImplicitParam(name = "request",value = "文件列表",required = true,dataType = "HttpServletRequest")
    @PostMapping(value = "/uploadImage")
    public ResultMessage uploadImage(HttpServletRequest request){
        List<MultipartFile> files =((MultipartHttpServletRequest)request).getFiles("file");
        Integer policyNo = Integer.valueOf(request.getParameter("policyNo"));
       return calibrationInfoService.uploadImg(files,policyNo);
    }

    /**
     * 提交子任务
     * @param submitPolicyVo
     * XZG 2019-07-29  13:09
     * @return
     */
    @ApiOperation(value = "提交子任务",notes = "提交子任务接口")
    @ApiImplicitParam(name = "submitPolicyVo",value = "子任务数据",required = true,dataType = "SubmitSubTaskVo")
    @PostMapping("/submitSubTask")
    public ResultMessage submitSubTask(@RequestBody SubmitSubTaskVo submitPolicyVo){
        return calibrationInfoService.submitSubTask(submitPolicyVo);
    }

    /**
     * 根据保单号获取所有的地块信息
     * @param policyNo
    * xzg 2019/7/31 14:10
    * @return
    */
    @ApiOperation(value = "获取地块信息",notes = "根据保单号获取所有的地块信息")
    @ApiImplicitParam(name = "policyNo",value = "保单号",required = true,dataType = "String")
    @GetMapping("/calibareDetails")
    public ResultMessage calibareDetails(@RequestParam(value = "policyNo",required = true) Integer policyNo){
        return calibrationInfoService.findCalibrationInfoListByPolicyNo(policyNo);
    }

}
