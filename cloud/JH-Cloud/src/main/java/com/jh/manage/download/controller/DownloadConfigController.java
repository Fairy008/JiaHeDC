package com.jh.manage.download.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.constant.CommonDefineEnum;
import com.jh.constant.ConstantUtil;
import com.jh.manage.download.entity.DataDownloadConfig;
import com.jh.manage.download.model.DownloadConfigParam;
import com.jh.manage.download.service.IDownloadConfigService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @description: 下载参数配置接口
 * @version <1> 2018-02-05 djh： Created.
 */
@Api(value = "下载参数配置接口")
@RestController
@RequestMapping("/downloadConfig")
public class DownloadConfigController extends BaseController {
    @Autowired
    private IDownloadConfigService downloadConfigService;

    /**
     *  下载配置分页查询
     *
     *  @param downloadConfigParam 下载参数配置参数
     *  @return
     *  @version <1> 2018-02-05 djh： Created.
     */
    @ApiOperation("下载配置分页查询")
    @ApiImplicitParam(name = "downloadConfigParam", value = "下载参数配置参数", required = true, dataType = "DownloadConfigParam")
    @PostMapping("/findByPage")
    public PageInfo<DownloadConfigParam> findByPage(DownloadConfigParam downloadConfigParam) {
        return downloadConfigService.findByPage(downloadConfigParam);
    }

    /**
     *  保存下载配置对象
     *
     *  @param request HttpServletRequest请求对象
     *  @param downloadConfigParam 下载参数配置参数
     *  @return
     *  @version <1> 2018-02-06 djh： Created.
     */
    @ApiOperation("保存下载配置对象")
    @ApiImplicitParam(name = "downloadConfigParam", value = "下载参数配置参数", required = true, dataType = "DownloadConfigParam")
    @PostMapping("/saveDataDownloadConfig")
    public ResultMessage saveDataDownloadConfig(HttpServletRequest request, @RequestBody DownloadConfigParam downloadConfigParam) {
        if (downloadConfigParam == null || downloadConfigParam.getDataDownloadConfig() == null) {
            return ResultMessage.fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(), CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }
        DataDownloadConfig dataDownloadConfig = downloadConfigParam.getDataDownloadConfig();
        dataDownloadConfig.setCreateTime(LocalDateTime.now());
        dataDownloadConfig.setCreatorName(getCurrentNickName());
        dataDownloadConfig.setCreator(getCurrentAccountId());
        return downloadConfigService.saveDataDownloadConfig(dataDownloadConfig);
    }

    /**
     *  删除指定id的下载配置记录
     *
     *  @param downloadConfigParam 下载参数配置参数
     *  @return
     *  @version <1> 2018-02-06 djh： Created.
     */
    @ApiOperation("删除指定id的下载配置记录")
    @ApiImplicitParam(name = "downloadConfigParam", value = "下载参数配置参数", required = true, dataType = "DownloadConfigParam")
    @PostMapping("/deleteDataDownloadConfigById")
    public ResultMessage deleteDataDownloadConfigById(@RequestBody DownloadConfigParam downloadConfigParam) {
        if (downloadConfigParam == null || downloadConfigParam.getDataDownloadConfig() == null) {
            return ResultMessage.fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(), CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }
        DataDownloadConfig dataDownloadConfig = downloadConfigParam.getDataDownloadConfig();
        dataDownloadConfig.setDelFlag(ConstantUtil.SET_DEL_FLAG_IS_DELETE_STATE);
        return downloadConfigService.deleteDataDownloadConfigById(dataDownloadConfig);
    }


    /**
     *  查询指定id对应的下载配置信息
     *
     *  @param downloadConfigParam 下载参数配置参数
     *  @return
     *  @version <1> 2018-02-06 djh： Created.
     */
    @ApiOperation("查询指定id对应的下载配置信息")
    @ApiImplicitParam(name = "downloadConfigParam", value = "下载参数配置参数", required = true, dataType = "DownloadConfigParam")
    @PostMapping("/queryDataDownloadConfigById")
    public ResultMessage queryDataDownloadConfigById(@RequestBody DownloadConfigParam downloadConfigParam) {
        if (downloadConfigParam == null) {
            return ResultMessage.fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(), CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }
        return downloadConfigService.queryDataDownloadConfigById(downloadConfigParam);
    }

    /**
     *  更新指定id对应的下载配置信息
     *
     *  @param downloadConfigParam 下载参数配置参数
     *  @return
     *  @version <1> 2018-02-06 djh： Created.
     */
    @ApiOperation("更新指定id对应的下载配置信息")
    @ApiImplicitParam(name = "downloadConfigParam", value = "下载参数配置参数", required = true, dataType = "DownloadConfigParam")
    @PostMapping("/updateDataDownloadConfigById")
    public ResultMessage updateDataDownloadConfigById(HttpServletRequest request, @RequestBody DownloadConfigParam downloadConfigParam) {
        if (downloadConfigParam == null || downloadConfigParam.getDataDownloadConfig() == null) {
            return ResultMessage.fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(), CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }
        DataDownloadConfig dataDownloadConfig = downloadConfigParam.getDataDownloadConfig();
        dataDownloadConfig.setModifyTime(LocalDateTime.now());
        dataDownloadConfig.setDownloadType(2301);
        dataDownloadConfig.setModifierName(getCurrentNickName());
        dataDownloadConfig.setModifier(getCurrentAccountId());
        return downloadConfigService.deleteDataDownloadConfigById(dataDownloadConfig);
    }

    @ApiOperation("根据数据分类查询对应的下载配置信息")
    @ApiImplicitParam(name = "downloadConfigParam", value = "下载参数配置参数", required = true, dataType = "DownloadConfigParam")
    @PostMapping("/queryByDataType")
    public ResultMessage queryByDataType(@RequestBody DownloadConfigParam downloadConfigParam){
        if (downloadConfigParam == null || downloadConfigParam.getSatId() == null) {
            return ResultMessage.fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(), CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }
        return downloadConfigService.findConfigByDataType(downloadConfigParam);
    }

}
