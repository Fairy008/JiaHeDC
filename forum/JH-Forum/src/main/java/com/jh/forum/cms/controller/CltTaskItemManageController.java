package com.jh.forum.cms.controller;

import com.github.pagehelper.PageInfo;
import com.jh.cltApp.entity.CltTaskItem;
import com.jh.cltApp.service.ICltTaskItemService;
import com.jh.cltApp.vo.CltTaskItemVO;
import com.jh.forum.base.controller.BaseController;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 采集数据点controller
 * @version <1> 2019/4/9 16:37 zhangshen:Created.
 */
@Api(value = "采集数据点后台管理",description = "采集数据点后台管理")
@RestController
@RequestMapping("/cltTaskItemManage")
public class CltTaskItemManageController extends BaseController {

    @Autowired
    private ICltTaskItemService cltTaskItemService;

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

    /**
     * 根据任务id查询任务明细数据列表(带分页)
     * @param
     * @return 
     * @version <1> 2019/4/11 13:14 zhangshen:Created.
     */
    @ApiOperation(value = "根据任务id查询任务明细数据列表(带分页)",notes = "根据任务id查询任务明细数据列表(带分页)")
    @ApiImplicitParam(name = "cltTaskItem",value = "采集数据点实体",required = true,dataType = "CltTaskItem")
    @PostMapping("/findTaskItemPageInfo")
    public PageInfo<CltTaskItemVO> findTaskItemPageInfo(CltTaskItem cltTaskItem){
        return cltTaskItemService.findAllTaskItemPageInfo(cltTaskItem);
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
        return cltTaskItemService.findTaskItemList(cltTaskItem);
    }

    /**
     * 新建采集点数据
     * @param
     * @return
     * @version <1> 2019/4/8 19:36 zhangshen:Created.
     */
    @ApiOperation(value = "新建采集点数据",notes = "新建采集点数据")
    @ApiImplicitParam(name = "cltTaskItemVO",value = "采集数据点实体",required = true,dataType = "CltTaskItemVO")
    @PostMapping("/createTaskItem")
    public ResultMessage createTaskItem(@RequestBody CltTaskItemVO cltTaskItemVO, @RequestParam String phone){
        cltTaskItemVO.setCreator(getCurrentAccountIdApp(phone));
        cltTaskItemVO.setCreatorName(getCurrentNickNameApp(phone));
        cltTaskItemVO.setModifier(getCurrentAccountIdApp(phone));
        cltTaskItemVO.setModifierName(getCurrentNickNameApp(phone));
        return cltTaskItemService.createTaskItem(cltTaskItemVO);
    }

    /**
     * 删除采集点数据
     * @param
     * @return
     * @version <1> 2019/4/8 19:36 zhangshen:Created.
     */
    @ApiOperation(value = "删除采集点数据",notes = "删除采集点数据")
    @ApiImplicitParam(name = "cltTaskItem",value = "采集数据点实体",required = true,dataType = "CltTaskItem")
    @PostMapping("/deleteTaskItemByItemId")
    public ResultMessage deleteTaskItemByItemId(@RequestBody CltTaskItem cltTaskItem){
        return cltTaskItemService.deleteTaskItemByItemId(cltTaskItem.getItemId());
    }

    /**
     * 修改采集点数据
     * @param
     * @return
     * @version <1> 2019/4/8 19:38 zhangshen:Created.
     */
    @ApiOperation(value = "修改采集点数据",notes = "修改采集点数据")
    @ApiImplicitParam(name = "cltTaskItem",value = "采集数据点实体",required = true,dataType = "CltTaskItem")
    @PostMapping("/updateTaskItemByItemId")
    public ResultMessage updateTaskItemByItemId(@RequestBody CltTaskItemVO cltTaskItem){
        cltTaskItem.setModifier(getCurrentAccountId());
        cltTaskItem.setModifierName(getCurrentNickName());
        return cltTaskItemService.updateTaskItemByItemId(cltTaskItem);
    }
}
