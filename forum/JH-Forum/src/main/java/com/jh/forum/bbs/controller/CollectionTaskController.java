package com.jh.forum.cms.controller;

import com.github.pagehelper.PageInfo;
import com.jh.biz.feign.IPersonService;
import com.jh.cltApp.entity.CltTaskInfo;
import com.jh.cltApp.entity.CltTaskUser;
import com.jh.cltApp.entity.CltTemplate;
import com.jh.cltApp.service.ICltTaskInfoService;
import com.jh.cltApp.service.ICltTaskUserService;
import com.jh.cltApp.vo.CltTaskInfoParamsVO;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.Enum.CltTaskStatusEnum;
import com.jh.forum.bbs.Enum.FollowTypeEnum;
import com.jh.forum.bbs.entity.ForumFollow;
import com.jh.forum.bbs.service.IForumFollowService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 采集器任务后台管理controller
 * @version <1> 2019/4/15 16:37 lijie:Created.
 */
@Api(value = "论坛-我的任务",description = "论坛-我的任务")
@RestController
@RequestMapping("/myCollectionTask")
public class CollectionTaskController extends BaseController {

    @Autowired
    private ICltTaskInfoService cltTaskInfoService;
    @Autowired
    private ICltTaskUserService cltTaskUserService;
    @Autowired
    private IForumFollowService forumFollowService;

    /**
     * 查询任务列表
     * @param
     * @return
     * @version <1> 2019/4/9 11:31 lijie:Created.
     */
    @ApiOperation(value = "查询任务列表",notes = "查询任务列表")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/findPage")
    public PageInfo<CltTaskInfo> findPage(@RequestBody CltTaskInfo cltTaskInfo){
        cltTaskInfo.setCreator(getCurrentAccountId());
        return cltTaskInfoService.findCltTaskInfoPageInfoBbs(cltTaskInfo);
    }

    /**
     * 查询任务列表不分页
     * @param
     * @return
     * @version <1> 2019/4/9 11:31 lijie:Created.
     */
    @ApiOperation(value = "查询写报告任务列表",notes = "查询写报告任务列表")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @GetMapping("/findTaskList")
    public ResultMessage findTaskList(Long regionId, String startDate, String endDate){
        CltTaskInfo cltTaskInfo = new CltTaskInfo();
        cltTaskInfo.setRegionId(regionId);
        cltTaskInfo.setStartCreateTime(startDate);
        cltTaskInfo.setEndCreateTime(endDate);
        cltTaskInfo.setIsPublish(CltTaskStatusEnum.ARTICLE_STATUS_PUBLISHED.getId());
        return cltTaskInfoService.findCltTaskInfoListInfoCms(cltTaskInfo);
    }

    /**
     * 新建任务
     * @param
     * @return
     * @version <1> 2019/4/10 16:29 lijie:Created.
     */
    @ApiOperation(value = "新建任务",notes = "新建任务")
    @ApiImplicitParam(name = "cltTaskInfoParamsVO",value = "新建任务实体",required = true,dataType = "CltTaskInfoParamsVO")
    @PostMapping("/createTaskInfo")
    public ResultMessage createTaskInfo(@RequestBody CltTaskInfoParamsVO cltTaskInfoParamsVO){
        cltTaskInfoParamsVO.setCreator(getCurrentAccountId());
        cltTaskInfoParamsVO.setCreatorName(getCurrentNickName());
        cltTaskInfoParamsVO.setModifier(getCurrentAccountId());
        cltTaskInfoParamsVO.setModifierName(getCurrentNickName());
        Integer[] arr = cltTaskInfoParamsVO.getUserArr();
        if(arr == null){
            cltTaskInfoParamsVO.setUserArr(new Integer []{getCurrentAccountId()});
        }
        return cltTaskInfoService.createTaskInfo(cltTaskInfoParamsVO);
    }

    /**
     * 修改任务
     * @param
     * @return
     * @version <1> 2019/4/10 16:29 lijie:Created.
     */
    @ApiOperation(value = "修改任务",notes = "修改任务")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/updateTaskInfoByTaskId")
    public ResultMessage updateTaskInfoByTaskId(@RequestBody CltTaskInfoParamsVO cltTaskInfoParamsVO){
        cltTaskInfoParamsVO.setModifier(getCurrentAccountId());
        cltTaskInfoParamsVO.setModifierName(getCurrentNickName());
        Integer[] arr = cltTaskInfoParamsVO.getUserArr();
        if(arr == null){
            cltTaskInfoParamsVO.setUserArr(new Integer []{getCurrentAccountId()});
        }
        return cltTaskInfoService.updateTaskInfoByTaskId(cltTaskInfoParamsVO);
    }

    /**
     * 删除任务
     * @param
     * @return
     * @version <1> 2019/4/10 16:29 lijie:Created.
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
     * @version <1> 2019/4/10 16:29 lijie:Created.
     */
    @ApiOperation(value = "修改任务状态",notes = "修改任务状态")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/updateTaskStatusByTaskId")
    public ResultMessage updateTaskStatusByTaskId(@RequestBody CltTaskInfo cltTaskInfo){
        cltTaskInfo.setModifier(getCurrentAccountId());
        cltTaskInfo.setModifierName(getCurrentNickName());
        return cltTaskInfoService.updateTaskStatusByTaskId(cltTaskInfo);
    }

    /**
     * 我分享的任务列表
     * @param
     * @return
     * @version <1> 2019/4/10 16:48 lijie:Created.
     */
    @ApiOperation(value = "我分享的任务列表",notes = "我分享的任务列表")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/findShareCltTaskInfoPageInfo")
    public PageInfo<CltTaskInfo> findShareCltTaskInfoPageInfo(@RequestBody CltTaskInfo cltTaskInfo){
        cltTaskInfo.setCreator(getCurrentAccountId());
        cltTaskInfo.setIsPublish(getCurrentAccountId());
        return cltTaskInfoService.findShareCltTaskInfoPageInfo(cltTaskInfo);
    }

    /**
     * 我参与的任务列表
     * @param
     * @return
     * @version <1> 2019/4/10 16:49 lijie:Created.
     */
    @ApiOperation(value = "我参与的任务列表",notes = "我参与的任务列表")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/findJoinCltTaskInfoPageInfo")
    public PageInfo<CltTaskInfo> findJoinCltTaskInfoPageInfo(@RequestBody CltTaskInfo cltTaskInfo){
        cltTaskInfo.setCreator(getCurrentAccountId());
        return  cltTaskInfoService.findJoinCltTaskInfoPageInfo(cltTaskInfo);
    }

    /**
     * 根据taskId查找任务详情
     * @param
     * @return
     * @version <1> 2019/4/15 16:32 lijie:Created.
     */
    @ApiOperation(value = "根据taskId查找任务详情",notes = "根据taskId查找任务详情")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/getCltTaskInfoByTaskId")
    public ResultMessage getCltTaskInfoByTaskId(@RequestBody CltTaskInfo cltTaskInfo) {
        return cltTaskInfoService.getCltTaskInfoByTaskId(cltTaskInfo);
    }

    /**
     * 查询任务参与人(不带分页)
     * @param
     * @return
     * @version <1> 2019/4/10 10:55 lijie:Created.
     */
    @ApiOperation(value = "查询任务参与人(不带分页)",notes = "查询任务参与人(不带分页)")
    @ApiImplicitParam(name = "cltTaskUser",value = "参与人实体",required = true,dataType = "CltTaskUser")
    @PostMapping("/findCltTaskUserList")
    public ResultMessage findCltTaskUserList(@RequestBody CltTaskUser cltTaskUser){
        cltTaskUser.setCreator(getCurrentAccountId());
        return cltTaskUserService.findCltTaskUserList(cltTaskUser);
    }

    /**
     * 查询我的关注列表(不带分页)
     * @param
     * @return
     * @version <1> 2019/4/19 10:36 zhangshen:Created.
     */
    @ApiOperation(value = "查询我的关注列表(不带分页)",notes = "查询我的关注列表(不带分页)")
    @ApiImplicitParam(name = "forumFollow",value = "关注实体",required = true,dataType = "ForumFollow")
    @PostMapping("/findMyFollowList")
    public ResultMessage findMyFollowList(@RequestBody ForumFollow forumFollow){
        if (forumFollow.getCreator() == null) {
            forumFollow.setCreator(getCurrentAccountId());
        }
        forumFollow.setFollowType(FollowTypeEnum.FOLLOW_TYPE_FOCUS.getId());
        return forumFollowService.findMyFollowList(forumFollow);
    }

    /**
     * 修改任务状态
     * @param
     * @return
     * @version <1> 2019/4/10 16:29 lijie:Created.
     */
    @ApiOperation(value = "批量修改任务状态",notes = "批量修改任务状态")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/auditTaskList")
    public ResultMessage auditTaskList(@RequestBody CltTaskInfo cltTaskInfo){
        cltTaskInfo.setModifier(getCurrentAccountId());
        cltTaskInfo.setModifierName(getCurrentNickName());
        cltTaskInfo.setModifier(getCurrentAccountId());
        cltTaskInfo.setModifierName(getCurrentNickName());
        cltTaskInfo.setModifyTime(LocalDateTime.now());
        cltTaskInfo.setAuditTime(LocalDateTime.now());
        cltTaskInfo.setAuditor(getCurrentAccountId());
        cltTaskInfo.setPublishTime(LocalDateTime.now());
        return cltTaskInfoService.auditTaskList(cltTaskInfo);
    }

}
