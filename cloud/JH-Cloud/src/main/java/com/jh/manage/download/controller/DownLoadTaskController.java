package com.jh.manage.download.controller;


import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.manage.download.Enum.DataAcquisitionEnum;
import com.jh.manage.download.Enum.DownloadStatusEnum;
import com.jh.manage.download.model.DownloadParam;
import com.jh.manage.download.service.IDataDownloadService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据下载任务
 * 分页查询、任务添加、任务修改、任务暂停、任务重新执行、任务详情查看
 * Created by XZG on 2018/2/28.
 */
@Api(value = "数据下载任务接口")
@RestController
@RequestMapping("/downLoadTask")
public class DownLoadTaskController extends BaseController {

    @Autowired
    private IDataDownloadService downloadService;

    /**
     * 下载任务分页查询
     * @param downloadParam
     * @return
     * @version <I> 2018-02-28 XZG: Created
     */
    @ApiOperation("下载任务分页查询")
    @ApiImplicitParam(name = "downloadParam",value = "下载任务查询条件",required = true,dataType = "DownloadParam")
    @PostMapping("/downloadPage")
    public PageInfo<DownloadParam> downloadPage(DownloadParam downloadParam, HttpServletRequest request){
        if(downloadParam.isUserFlag()){ //添加当前登录人信息查询
            downloadParam.setCreator(getCurrentAccountId());
        }


        return downloadService.findTaskPage(downloadParam);
    }


    /**
     * 新建数据下载任务
     * @param request
     * @param downloadParam  下载任务参数
     * @return
     * @version <I> 2018-02-28 XZG: Created
     */
    @ApiOperation("新增下载任务")
    @ApiImplicitParam(name = "downloadParam",value = "下载任务参数",required = true,dataType = "DownloadParam")
    @PostMapping("/saveDownload")
    public ResultMessage saveDownload(HttpServletRequest request, @RequestBody DownloadParam downloadParam){
        if (downloadParam == null){
            return ResultMessage.fail(DataAcquisitionEnum.PARAM_EMPTY.getValue(), DataAcquisitionEnum.PARAM_EMPTY.getMesasge());
        }
        downloadParam.setCreator(getCurrentAccountId());
        downloadParam.setCreatorName(getCurrentNickName());
        return downloadService.saveDownload(downloadParam);
    }


    /**
     * 暂停下载任务
     * 修改任务状态为（暂停）,暂停当前下载任务，并启动下一个待下载任务
     * @param downloadParam
     * @return
     * @version <I> 2018-02-28 XZG: Created
     */
//    @ApiOperation("暂停下载任务")
//    @ApiImplicitParam(name = "downloadParam",value = "下载任务参数",required = true,dataType = "DownloadParam")
//    @PostMapping("/suspend")
//    public ResultMessage suspend(@RequestBody(required = true) DownloadParam downloadParam){
//        if (downloadParam == null || downloadParam.getDownloadId() == null){
//            return ResultMessage.fail(DataAcquisitionEnum.PARAM_EMPTY.getValue(),DataAcquisitionEnum.PARAM_EMPTY.getMesasge());
//        }
//        return downloadService.suspendDownload(downloadParam.getDownloadId());
//    }

    /**\
     * 重新启动下载任务
     * 更新当前下载任务信息
     * 修改任务状态为（待下载）
     * @param downloadParam
     * @return
     * @version <I> 2018-02-28 XZG: Created
     */
    @ApiOperation("重新启动下载任务")
    @ApiImplicitParam(name = "downloadParam",value = "下载任务参数",required = true,dataType = "DownloadParam")
    @PostMapping("/tryAgain")
    public ResultMessage tryAgain(HttpServletRequest request, @RequestBody(required = true) DownloadParam downloadParam){
        if (downloadParam == null){
            return ResultMessage.fail(DataAcquisitionEnum.PARAM_EMPTY.getValue(), DataAcquisitionEnum.PARAM_EMPTY.getMesasge());
        }
        downloadParam.setModifier(getCurrentAccountId());
        downloadParam.setModifierName(getCurrentNickName());
        return downloadService.tryAgainTask(downloadParam);
    }


    /**\
     * 重新启动下载任务
     * 更新当前下载任务信息
     * 修改任务状态为（待下载）
     * @param downloadId
     * @return
     * @version <I> 2018-05-31 wl: Created
     */
    @ApiOperation("重新启动下载任务")
    @ApiImplicitParam(name = "downloadId", value="下载任务id", required = true, dataType = "Integer")
    @PostMapping("/startAgain")
    public ResultMessage startAgain(HttpServletRequest request, @RequestParam Integer downloadId){
        DownloadParam downloadParam=new DownloadParam();
        downloadParam.setModifier(getCurrentAccountId());
        downloadParam.setModifierName(getCurrentNickName());
        downloadParam.setDownloadStatus(DownloadStatusEnum.WAIT.getValue());
        downloadParam.setDownloadId(downloadId);
        return downloadService.updateDownload(downloadParam);
    }

    /**
     * 继续下载
     * 修改任务状态为（待下载），并启动
     * @param downloadParam
     * @return
     * @version <I> 2018-02-28 XZG: Created
     */
//    @ApiOperation("继续下载")
//    @ApiImplicitParam(name = "downloadParam",value = "下载任务参数",required = true,dataType = "DownloadParam")
//    @PostMapping("/continueDownload")
//    public ResultMessage continueDownload(@RequestBody(required = true) DownloadParam downloadParam){
//        if (downloadParam == null || downloadParam.getDownloadId() == null){
//            return ResultMessage.fail(DataAcquisitionEnum.PARAM_EMPTY.getValue(),DataAcquisitionEnum.PARAM_EMPTY.getMesasge());
//        }
//        return downloadService.continueDownload(downloadParam.getDownloadId());
//    }

    /**
     * 根据任务主键查询
     * @param downloadParam
     * @return
     */
    @ApiOperation("根据任务主键查询")
    @ApiImplicitParam(name = "downloadParam",value = "下载任务参数",required = true,dataType = "DownloadParam")
    @PostMapping("/findById")
    public ResultMessage findById(@RequestBody(required = true) DownloadParam downloadParam){
        if (downloadParam == null || downloadParam.getDownloadId() == null){
            return ResultMessage.fail(DataAcquisitionEnum.PARAM_EMPTY.getValue(), DataAcquisitionEnum.PARAM_EMPTY.getMesasge());
        }
        return downloadService.findById(downloadParam.getDownloadId());
    }

}
