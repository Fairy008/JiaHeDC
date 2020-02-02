package com.jh.manage.archive.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.manage.archive.entity.DataArchive;
import com.jh.manage.archive.model.ArchiveParam;
import com.jh.manage.archive.service.IDataArchiveService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * description:
 *
 * @version <1> 2018-03-21 wl: Created.
 */
@RestController
@RequestMapping("/archive")
public class DataArchiveController extends BaseController {

    @Autowired
    private IDataArchiveService dataArchiveService;

    @ApiOperation(value = "获取归档任务列表", notes = "获取归档任务列表")
    @ApiImplicitParam(name = "archiveParam",value = "数据归档",required = false, dataType = "ArchiveParam")
    @PostMapping("/findByPage")
    public PageInfo<DataArchive> findByPage(ArchiveParam archiveParam, HttpServletRequest request){
        if(archiveParam.isUserFlag()){ //添加当前登录人信息查询
            archiveParam.setCreator(getCurrentAccountId());
        }
        return dataArchiveService.findByPage(archiveParam);
    }

    /**
     * @description: 数据归档任务新增
     * @param request
     * @param archiveParam
     * @return
     * @version <1> 2018-03-21 wl : created.
     */
    @ApiOperation(value = "数据归档任务新增",notes = "新增数据归档任务")
    @ApiImplicitParam(name = "dataArchive",value = "数据归档任务实体",required = true,dataType = "DataArchive")
    @PostMapping("/add")
    public ResultMessage add(HttpServletRequest request, @RequestBody ArchiveParam archiveParam){
            archiveParam.setCreator(getCurrentAccountId());
            archiveParam.setCreatorName(getCurrentNickName());
            archiveParam.setPhone(getCurrentAccount());

        return  dataArchiveService.saveArchive(archiveParam);
    }

    /**
     * @description: 根据ID查询数据归档任务实体
     * @param request
     * @param archiveId
     * @return
     ** @version <1> 2018-03-21 wl : created.
     */
    @ApiOperation(value = "数据归档任务查询",notes = "按数据归档任务id查询")
    @ApiImplicitParam(name = "archiveId",value = "数据归档任务主键",required = true,paramType = "query", dataType = "Integer")
    @PostMapping("/findById")
    public ResultMessage findById(HttpServletRequest request, @RequestParam Integer archiveId){
        return dataArchiveService.findById(archiveId);
    }


    /**
     * 数据归档任务修改
     * @param request
     * @param archiveParam
     * @return
     * @version <1> 2018-03-26 wl： Created.
     */
    @ApiOperation(value = "资源修改",notes = "修改资源角色")
    @ApiImplicitParam(name = "permResource",value = "系统资源实体",required = true,dataType = "PermResource")
    @PostMapping("/edit")
    public ResultMessage edit(HttpServletRequest request, @RequestBody ArchiveParam archiveParam){
            archiveParam.setModifier(getCurrentAccountId());
            archiveParam.setModifierName(getCurrentNickName());
        return dataArchiveService.updateArchive(archiveParam);
    }

    /**
     * 查询数据归档详情列表
     * @return
     * @version <1> 2018-03-26 wl : Created.
     */
    @ApiOperation(value = "获取数据归档详情", notes = "获取数据归档详情")
    @ApiImplicitParam(name = "archiveId", value="数据归档id", required = true, dataType = "Integer")
    @PostMapping("/findDetail")
    public ResultMessage findDetail(HttpServletRequest request, @RequestParam Integer archiveId){
        return dataArchiveService.findDetail(archiveId);
    }

    /**
     * @description: 数据归档任务执行
     * @param archiveId
     * @return
     * @version <1> 2018-03-27 wl : created.
     */
    @ApiOperation(value = "数据归档任务执行",notes = "执行数据归档任务")
    @ApiImplicitParam(name = "archiveId",value = "数据归档任务id",required = true,dataType = "Integer")
    @PostMapping("/exec")
    public ResultMessage exec(@RequestParam Integer archiveId){
        return  dataArchiveService.exec(archiveId);
    }

    /**
     *统计不同卫星数据的归档数量
     * @param archiveParam
     * @return ResultMessage
     * @version <1> 2018-04-17 wl： Created.
     */
    @ApiOperation(value = "统计不同卫星数据的归档数量",notes = "统计不同卫星数据的归档数量")
    @ApiImplicitParam(name = "archiveParam",value = "订单对象",required = true,dataType = "ArchiveParam")
    @PostMapping("queryArchiveSateNum")
    public ResultMessage queryArchiveSateNum(@RequestBody ArchiveParam archiveParam){
        return dataArchiveService.queryArchiveSateNum(archiveParam);
    }

    /**
     *统计不同卫星数据的归档数量(当天和总计)
     * @param archiveParam
     * @return ResultMessage
     * @version <1> 2018-06-12 wl： Created.
     */
    @ApiOperation(value = "统计不同卫星数据的归档数量(当天和总计)",notes = "统计不同卫星数据的归档数量(当天和总计)")
    @ApiImplicitParam(name = "archiveParam",value = "订单对象",required = true,dataType = "ArchiveParam")
    @PostMapping("/nolog/queryArchiveSateSum")
    public ResultMessage queryArchiveSateSum(@RequestBody ArchiveParam archiveParam){
        return dataArchiveService.queryArchiveSateSum(archiveParam);
    }

    /**
     * 卫星数据及归档统计table
     * @param archiveParam
     * @return ResultMessage
     * @version <1> 2018-06-26 zhangshen: Created.
     */
    @ApiOperation(value = "卫星数据及归档统计(总计)",notes = "卫星数据及归档统计(总计)")
    @ApiImplicitParam(name = "archiveParam",value = "订单对象",required = true,dataType = "ArchiveParam")
    @PostMapping("/nolog/queryArchiveSateSumTable")
    public ResultMessage queryArchiveSateSumTable(@RequestBody ArchiveParam archiveParam){
        return dataArchiveService.queryArchiveSateSumTable(archiveParam);
    }

}
