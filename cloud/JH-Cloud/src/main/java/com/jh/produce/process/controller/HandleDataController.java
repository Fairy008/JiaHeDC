package com.jh.produce.process.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.produce.process.Enum.ExecStatusEnum;
import com.jh.produce.process.entity.HandleData;
import com.jh.produce.process.model.DataStepParam;
import com.jh.produce.process.model.HandleDataParam;
import com.jh.produce.process.model.HandleRelateDataParam;
import com.jh.produce.process.service.IHandleDataService;
import com.jh.util.DownloadUtil;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * description:数据处理
 * @version <1> 2018-03-12 cxj： Created.
 */
@RestController
@RequestMapping("/handleData")
public class HandleDataController extends BaseController {
    @Autowired
    private IHandleDataService handleDataService;

    /**
     * 保存任务信息
     * @param handleData
     * @return
     * @version <1> 2018-03-12 cxj： Created.
     */
    @ApiOperation(value = "保存任务信息",notes = "保存任务信息")
    @ApiImplicitParam(name = "handleData",value = "任务参数",required = false,dataType = "HandleData")
    @PostMapping("/saveTask")
    public ResultMessage saveTask(HttpServletRequest request, @RequestBody HandleData handleData){
        handleData.setCreatorName(getCurrentNickName());
        handleData.setCreator(getCurrentAccountId());
        return handleDataService.saveTask(handleData);
    }

    /**
     * 任务分页查询
     * @param handleDataParam
     * @return
     * @version <I> 2018-03-12 cxj: Created
     */
    @ApiOperation("任务分页查询")
    @ApiImplicitParam(name = "handleDataParam",value = "任务查询条件",required = true,dataType = "HandleDataParam")
    @PostMapping("/taskPage")
    public PageInfo<HandleData> taskPage(HandleDataParam handleDataParam, HttpServletRequest request){
        if(handleDataParam.isUserFlag()){ //添加当前登录人信息查询
            handleDataParam.setCreator(getCurrentAccountId());
        }

        return handleDataService.taskPage(handleDataParam);
    }

    /**
     * 查询执行任务时所需的参数
     * @param handleDataParam
     * @return
     * @version <I> 2018-03-13 cxj: Created
     */
    @ApiOperation("查询执行任务时所需的参数")
    @ApiImplicitParam(name = "handleDataParam",value = "任务查询条件",required = true,dataType = "HandleDataParam")
    @PostMapping("/findTaskParams")
    public ResultMessage findTaskParams(@RequestBody HandleDataParam handleDataParam){
        return handleDataService.findTaskParams(handleDataParam);
    }

    /**
     * 查询执行任务时所需的参数，查询自动创建任务数据中的数据
     * @param handleDataParam
     * @return
     * @version <I> 2018-03-13 cxj: Created
     */
    @ApiOperation("查询执行任务时所需的参数")
    @ApiImplicitParam(name = "handleDataParam",value = "任务查询条件",required = true,dataType = "HandleDataParam")
    @PostMapping("/findHandleFileInfo")
    public ResultMessage findHandleFileInfo(@RequestBody HandleDataParam handleDataParam){
        return handleDataService.findHandleFileInfo(handleDataParam);
    }

    /**
     * 查询执行任务时所需的参数,,查询手动创建任务文件夹的数据
     * @param handleDataParam
     * @return
     * @version <I> 2019-10-10 cxw: Created
     */
    @ApiOperation("查询执行任务时所需的参数")
    @ApiImplicitParam(name = "handleDataParam",value = "任务查询条件",required = true,dataType = "HandleDataParam")
    @PostMapping("/findHandleDataInfo")
    public ResultMessage findHandleDataInfo(@RequestBody HandleDataParam handleDataParam){
        return handleDataService.findHandleDataInfo(handleDataParam);
    }


    /**
     * 添加任务步骤
     * @param dataStepParam
     * @return
     * @version <I> 2018-03-15 cxj: Created
     */
    @ApiOperation("添加任务步骤")
    @ApiImplicitParam(name = "taskInfoJson",value = "任务参数",required = true,dataType = "String")
    @PostMapping("/handleData")
    public ResultMessage handleData(HttpServletRequest request, @RequestBody DataStepParam dataStepParam){

        return handleDataService.addHandleData(dataStepParam);
    }

    /**
     * 重新执行任务和步骤状态
     * @param handleDataParam
     * @return
     * @version <I> 2018-03-21 cxj: Created
     */
    @ApiOperation("重新执行任务和步骤状态")
    @ApiImplicitParam(name = "handleDataParam",value = "任务参数",required = true,dataType = "HandleDataParam")
    @RequestMapping("/againStatus")
    public ResultMessage againStatus(@RequestBody HandleDataParam handleDataParam){
        handleDataParam.setHandleStatus(ExecStatusEnum.TOBEEXECUTED.getValue());
        return handleDataService.updateStatus(handleDataParam);
    }

    @ApiOperation("数据处理文件下载")
    @ApiImplicitParam(name = "dataUrl",value = "处理结果地址",required = true,paramType = "query", dataType = "String")
    @RequestMapping("/down")
    public void handleDown(@RequestParam String dataUrl, HttpServletResponse response){
        DownloadUtil.getInstance().downloadFile(dataUrl, response);
    }

    /**
     * 创建数据生产任务
     * @param handleData
     * @return
     * @version <1> 2019-02-14 zhangshen： Created.
     */
    @ApiOperation(value = "创建数据生产任务",notes = "创建数据生产任务")
    @ApiImplicitParam(name = "handleData",value = "任务参数",required = false,dataType = "HandleData")
    @PostMapping("/createHandleTask")
    public ResultMessage createHandleTask(HttpServletRequest request, @RequestBody HandleData handleData){
        handleData.setCreatorName(getCurrentNickName());
        handleData.setCreator(getCurrentAccountId());
        return handleDataService.createHandleTask(handleData);
    }

    /**
     * 数据集入库
     * @param handleData
     * @return
     * @version <1> 2019-02-18 zhangshen： Created.
     */
    @ApiOperation(value = "数据集入库",notes = "数据集入库")
    @ApiImplicitParam(name = "handleData",value = "任务参数",required = false,dataType = "HandleData")
    @PostMapping("/findTaskByHandleId")
    public ResultMessage findTaskByHandleId(@RequestBody HandleData handleData){
        return handleDataService.findTaskByHandleId(handleData);
    }

    /**
     * 删除任务
     * @param handleId
     * @return
     * @version <I> 2019-03-21 lijie: Created
     */
    @ApiOperation("删除任务")
    @ApiImplicitParam(name = "handleId",value = "任务ID",required = true,dataType = "Integer")
    @RequestMapping("/deleteHandle")
    public ResultMessage deleteHandle(Integer handleId){
        return handleDataService.deleteHandle(handleId);
    }


    /**
     * 更新任务数据执行顺序
     * @param dataIdList
     * @return
     * @version <1> 2019-10-08 cxw： Created.
     */
    @ApiOperation(value = "更新任务数据执行顺序",notes = "更新任务数据执行顺序")
    @ApiImplicitParam(name = "dataIdList",value = "任务参数",required = false,dataType = "List<HandleRelateDataParam>")
    @PostMapping("/updateHandleDataIndex")
    public ResultMessage updateHandleDataIndex(@RequestBody List<HandleRelateDataParam> dataIdList){
        return handleDataService.updateHandleDataIndex(dataIdList);
    }

}