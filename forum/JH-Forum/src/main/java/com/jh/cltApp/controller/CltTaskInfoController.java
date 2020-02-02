package com.jh.cltApp.controller;

import com.github.pagehelper.PageInfo;
import com.jh.cltApp.entity.CltTaskInfo;
import com.jh.cltApp.service.ICltTaskInfoService;
import com.jh.cltApp.vo.CltTaskInfoParamsVO;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.Enum.CltTaskStatusEnum;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 采集器任务controller
 * @version <1> 2019/4/9 16:37 zhangshen:Created.
 */
@Api(value = "采集器任务",description = "采集器任务")
@RestController
@RequestMapping("/cltTaskInfo")
public class CltTaskInfoController extends BaseController {

    @Autowired
    private ICltTaskInfoService cltTaskInfoService;

    /**
     * 根据用户id, 查询任务列表, 包括自己创建的任务和参与的任务
     * @param
     * @return
     * @version <1> 2019/4/9 11:31 zhangshen:Created.
     */
    @ApiOperation(value = "查询任务列表",notes = "查询任务列表")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/findCombinationPage")
    public PageInfo<CltTaskInfo> findCombinationPage(@RequestBody CltTaskInfo cltTaskInfo, @RequestParam String phone){
        cltTaskInfo.setCreator(getCurrentAccountIdApp(phone));
        return cltTaskInfoService.findCltTaskInfoPageInfo(cltTaskInfo);
    }

    /**
     * 新建任务
     * @param 
     * @return 
     * @version <1> 2019/4/10 16:29 zhangshen:Created.
     */
    @ApiOperation(value = "新建任务",notes = "新建任务")
    @ApiImplicitParam(name = "cltTaskInfoParamsVO",value = "新建任务实体",required = true,dataType = "CltTaskInfoParamsVO")
    @PostMapping("/createTaskInfo")
    public ResultMessage createTaskInfo(@RequestBody CltTaskInfoParamsVO cltTaskInfoParamsVO, @RequestParam String phone){
        cltTaskInfoParamsVO.setCreator(getCurrentAccountIdApp(phone));
        cltTaskInfoParamsVO.setCreatorName(getCurrentNickNameApp(phone));
        cltTaskInfoParamsVO.setModifier(getCurrentAccountIdApp(phone));
        cltTaskInfoParamsVO.setModifierName(getCurrentNickNameApp(phone));
        return cltTaskInfoService.createTaskInfo(cltTaskInfoParamsVO);
    }

    /**
     * 修改任务
     * @param
     * @return
     * @version <1> 2019/4/10 16:29 zhangshen:Created.
     */
    @ApiOperation(value = "修改任务",notes = "修改任务")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/updateTaskInfoByTaskId")
    public ResultMessage updateTaskInfoByTaskId(@RequestBody CltTaskInfoParamsVO cltTaskInfoParamsVO, @RequestParam String phone){
        cltTaskInfoParamsVO.setModifier(getCurrentAccountIdApp(phone));
        cltTaskInfoParamsVO.setModifierName(getCurrentNickNameApp(phone));
        return cltTaskInfoService.updateTaskInfoByTaskId(cltTaskInfoParamsVO);
    }

    /**
     * 删除任务
     * @param
     * @return
     * @version <1> 2019/4/10 16:29 zhangshen:Created.
     */
    @ApiOperation(value = "根据任务id删除任务",notes = "根据任务id删除任务")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/deleteTaskInfoByTaskId")
    public ResultMessage deleteTaskInfoByTaskId(@RequestBody CltTaskInfo cltTaskInfo){
        return cltTaskInfoService.deleteTaskInfoByTaskId(cltTaskInfo.getTaskId());
    }

    /**
     * 修改任务状态
     * @param
     * @return
     * @version <1> 2019/4/10 16:29 zhangshen:Created.
     */
    @ApiOperation(value = "修改任务状态",notes = "修改任务状态")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/updateTaskStatusByTaskId")
    public ResultMessage updateTaskStatusByTaskId(@RequestBody CltTaskInfo cltTaskInfo, @RequestParam String phone){
        cltTaskInfo.setModifier(getCurrentAccountIdApp(phone));
        cltTaskInfo.setModifierName(getCurrentNickNameApp(phone));
        //撤回轮播状态修改
        if(null != cltTaskInfo.getIsPublish() && cltTaskInfo.getIsPublish()== CltTaskStatusEnum.ARTICLE_STATUS_WITHDRAW.getId()){
            cltTaskInfo.setIndexShow(0);
        }
        return cltTaskInfoService.updateTaskStatusByTaskId(cltTaskInfo);
    }

    /**
     * 我分享的任务列表
     * @param 
     * @return 
     * @version <1> 2019/4/10 16:48 zhangshen:Created.
     */
    @ApiOperation(value = "我分享的任务列表",notes = "我分享的任务列表")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/findShareCltTaskInfoPageInfo")
    public PageInfo<CltTaskInfo> findShareCltTaskInfoPageInfo(@RequestBody CltTaskInfo cltTaskInfo, @RequestParam String phone){
        cltTaskInfo.setCreator(getCurrentAccountIdApp(phone));
        return cltTaskInfoService.findShareCltTaskInfoPageInfo(cltTaskInfo);
    }

    /**
     * 我参与的任务列表
     * @param
     * @return 
     * @version <1> 2019/4/10 16:49 zhangshen:Created.
     */
    @ApiOperation(value = "我参与的任务列表",notes = "我参与的任务列表")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/findJoinCltTaskInfoPageInfo")
    public PageInfo<CltTaskInfo> findJoinCltTaskInfoPageInfo(@RequestBody CltTaskInfo cltTaskInfo, @RequestParam String phone){
        cltTaskInfo.setCreator(getCurrentAccountIdApp(phone));
        return  cltTaskInfoService.findJoinCltTaskInfoPageInfo(cltTaskInfo);
    }

    /**
     * 根据taskId查找任务详情
     * @param
     * @return 
     * @version <1> 2019/4/15 16:32 zhangshen:Created.
     */
    @ApiOperation(value = "根据taskId查找任务详情",notes = "根据taskId查找任务详情")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/getCltTaskInfoByTaskId")
    public ResultMessage getCltTaskInfoByTaskId(@RequestBody CltTaskInfo cltTaskInfo) {
        return cltTaskInfoService.getCltTaskInfoByTaskId(cltTaskInfo);
    }
}
