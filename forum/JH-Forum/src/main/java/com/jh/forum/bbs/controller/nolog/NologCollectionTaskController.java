package com.jh.forum.bbs.controller.nolog;

import com.github.pagehelper.PageInfo;
import com.jh.biz.feign.IPersonService;
import com.jh.cltApp.entity.CltTaskInfo;
import com.jh.cltApp.entity.CltTaskItem;
import com.jh.cltApp.entity.CltTaskUser;
import com.jh.cltApp.service.ICltTaskInfoService;
import com.jh.cltApp.service.ICltTaskItemService;
import com.jh.cltApp.service.ICltTaskUserService;
import com.jh.cltApp.vo.CltTaskInfoParamsVO;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.Enum.FollowTypeEnum;
import com.jh.forum.bbs.entity.ForumFollow;
import com.jh.forum.bbs.service.IForumFollowService;
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
 * 采集器任务后台管理controller
 * @version <1> 2019/4/15 16:37 lijie:Created.
 */
@Api(value = "采集器任务后台管理",description = "采集器任务后台管理")
@RestController
@RequestMapping("/nolog/collectionTask")
public class NologCollectionTaskController extends BaseController {

    @Autowired
    private ICltTaskInfoService cltTaskInfoService;
    @Autowired
    private ICltTaskItemService cltTaskItemService;

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
        return cltTaskInfoService.getListByCombination(cltTaskInfo);
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
     * 根据任务id查询任务明细数据列表(不带分页)
     * @param
     * @return
     * @version <1> 2019/4/11 13:14 zhangshen:Created.
     */
    @ApiOperation(value = "根据任务id查询任务明细数据列表(不带分页)",notes = "根据任务id查询任务明细数据列表(不带分页)")
    @ApiImplicitParam(name = "cltTaskItem",value = "采集数据点实体",required = true,dataType = "CltTaskItem")
    @PostMapping("/findTaskItemList")
    public ResultMessage findTaskItemList(@RequestBody CltTaskItem cltTaskItem){
        return cltTaskItemService.findAllTaskItemList(cltTaskItem);
    }

    /**
     * 根据采集点数据id查询任务明细数据
     * @param
     * @return
     * @version <1> 2019/4/11 13:09 zhangshen:Created.
     */
    @ApiOperation(value = "采集点数据",notes = "采集点数据")
    @ApiImplicitParam(name = "cltTaskItem",value = "采集数据点实体",required = true,dataType = "CltTaskItem")
    @PostMapping("/findTaskItemByItemId")
    public ResultMessage findTaskItemByItemId(@RequestBody CltTaskItem cltTaskItem){
        return cltTaskItemService.findTaskItemByItemId(cltTaskItem);
    }



}
