package com.jh.manage.download.controller;

import com.jh.manage.download.Enum.DataAcquisitionEnum;
import com.jh.manage.download.model.DownloadDetailParam;
import com.jh.manage.download.service.IDataDownloadDetailService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 下载任务详情
 * Created by XZG on 2018/3/8.
 */
@Api(value = "任务详情接口")
@RestController
@RequestMapping("/downloadDetail")
public class DownloadDetailController {

    @Autowired
    private IDataDownloadDetailService dataDownloadDetailService;

    /**
     * 查看下载任务详情
     * @param detailParam
     * @return
     * @version <I> 2018-03-08 XZG: Created
     */
    @ApiOperation("查看下载任务详情")
    @ApiImplicitParam(name = "detailParam",value = "下载文件详情",required = true,dataType = "DownloadDetailParam")
    @PostMapping("/findDetailList")
    public ResultMessage findDetailList(@RequestBody(required = true) DownloadDetailParam detailParam){
        if (detailParam == null || detailParam.getDownloadId() == null){
            return ResultMessage.fail(DataAcquisitionEnum.PARAM_EMPTY.getValue(), DataAcquisitionEnum.PARAM_EMPTY.getMesasge());
        }
        return  dataDownloadDetailService.findDetailList(detailParam.getDownloadId());
    }

}
