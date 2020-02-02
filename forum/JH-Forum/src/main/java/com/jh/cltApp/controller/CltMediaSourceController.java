package com.jh.cltApp.controller;

import com.jh.cltApp.entity.CltMediaSource;
import com.jh.cltApp.service.ICltMediaSourceService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 媒体资源controller
 * @version <1> 2019/4/9 16:37 zhangshen:Created.
 */
@Api(value = "媒体资源",description = "媒体资源")
@RestController
@RequestMapping("/cltMediaSource")
public class CltMediaSourceController {

    @Autowired
    private ICltMediaSourceService cltMediaSourceService;

    /**
     * 根据采集点id查询媒体资源信息
     * @param
     * @return
     * @version <1> 2019/4/8 18:47 zhangshen:Created.
     */
    @ApiOperation(value = "根据采集点id查询媒体资源信息",notes = "根据采集点id查询媒体资源信息")
    @ApiImplicitParam(name = "cltMediaSource",value = "媒体资源实体",required = true,dataType = "CltMediaSource")
    @PostMapping("/findCltMediaSourceListByItemId")
    public ResultMessage findCltMediaSourceListByItemId(@RequestBody CltMediaSource cltMediaSource){
        return cltMediaSourceService.findCltMediaSourceListByItemId(cltMediaSource.getItemId());
    }

    /**
     * 新增媒体资源
     * @param
     * @return
     * @version <1> 2019/4/8 19:47 zhangshen:Created.
     */
    @ApiOperation(value = "新增媒体资源",notes = "新增媒体资源")
    @ApiImplicitParam(name = "cltMediaSource",value = "媒体资源实体",required = true,dataType = "CltMediaSource")
    @PostMapping("/cerateMediaSource")
    public ResultMessage cerateMediaSource(@RequestBody CltMediaSource cltMediaSource){
        return cltMediaSourceService.cerateMediaSource(cltMediaSource);
    }

    /**
     * 根据任务id删除媒体资源
     * @param
     * @return
     * @version <1> 2019/4/8 19:49 zhangshen:Created.
     */
    @ApiOperation(value = "根据任务id删除媒体资源",notes = "根据任务id删除媒体资源")
    @ApiImplicitParam(name = "taskId",value = "任务id",required = true,dataType = "Integer")
    @PostMapping("/deleteMediaSourceByTaskId")
    public ResultMessage deleteMediaSourceByTaskId(@RequestParam Integer taskId){
        return cltMediaSourceService.deleteMediaSourceByTaskId(taskId);
    }

    /**
     * 根据id删除媒体资源
     * @param
     * @return
     * @version <1> 2019/4/8 19:50 zhangshen:Created.
     */
    @ApiOperation(value = "根据id删除媒体资源",notes = "根据id删除媒体资源")
    @ApiImplicitParam(name = "id",value = "媒体资源id",required = true,dataType = "Integer")
    @PostMapping("/deleteMediaSourceById")
    public ResultMessage deleteMediaSourceById(@RequestParam Integer id){
        return cltMediaSourceService.deleteMediaSourceById(id);
    }
}
