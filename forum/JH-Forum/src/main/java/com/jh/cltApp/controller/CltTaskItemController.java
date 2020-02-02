package com.jh.cltApp.controller;

import com.github.pagehelper.PageInfo;
import com.jh.cltApp.entity.CltMediaSource;
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

import javax.servlet.http.HttpServletRequest;

/**
 * 采集数据点controller
 * @version <1> 2019/4/9 16:37 zhangshen:Created.
 */
@Api(value = "采集数据点",description = "采集数据点")
@RestController
@RequestMapping("/cltTaskItem")
public class CltTaskItemController extends BaseController {

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
    public PageInfo<CltTaskItemVO> findTaskItemPageInfo(@RequestBody CltTaskItem cltTaskItem, @RequestParam String phone){
        cltTaskItem.setCreator(getCurrentAccountIdApp(phone));
        return cltTaskItemService.findTaskItemPageInfo(cltTaskItem);
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
     * 1.往数据采集点表新增一条采集数据
     * 2.返回itemId
     * 3.拿到itemId再上传媒体文件，往媒体文件表插入数据
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
     * mui上传多个文件
     * 1.根据itemId删除此采集点下的所有媒体文件
     * 2.上传文件
     * 3.插入媒体文件信息到媒体表
     * 4.返回是否插入成功
     * @param 
     * @return 
     * @version <1> 2019/4/20 10:03 zhangshen:Created.
     */
    @RequestMapping(value="/muiUploadFile", method = {RequestMethod.POST})
    public ResultMessage muiUploadFile(@RequestParam String phone, @RequestParam Integer taskId, @RequestParam Integer itemId, HttpServletRequest request) {
        CltMediaSource cltMediaSource = new CltMediaSource();
        cltMediaSource.setCreator(getCurrentAccountIdApp(phone));
        cltMediaSource.setCreatorName(getCurrentNickNameApp(phone));
        cltMediaSource.setModifier(getCurrentAccountIdApp(phone));
        cltMediaSource.setModifierName(getCurrentNickNameApp(phone));
        return cltTaskItemService.muiUploadFile(cltMediaSource, phone, taskId, itemId, request);
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
        return cltTaskItemService.updateTaskItemByItemId(cltTaskItem);
    }

    /**
     * 根据任务id和用户查看自己的采集点数据列表
     * @param
     * @return
     * @version <1> 2019/4/17 15:26 zhangshen:Created.
     */
    @ApiOperation(value = "根据任务id和用户查看自己的采集点数据列表",notes = "根据任务id和用户查看自己的采集点数据列表")
    @ApiImplicitParam(name = "cltTaskItem",value = "采集数据点实体",required = true,dataType = "CltTaskItem")
    @PostMapping("/findTaskItemListSelf")
    public ResultMessage findTaskItemListSelf(@RequestBody CltTaskItem cltTaskItem, @RequestParam String phone) {
        cltTaskItem.setCreator(getCurrentAccountIdApp(phone));
        return cltTaskItemService.findTaskItemListSelf(cltTaskItem);
    }
}
