package com.jh.data.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.data.entity.DsYield;
import com.jh.data.model.DsYieldParam;
import com.jh.data.model.ReportCreateParam;
import com.jh.data.service.IDsYieldService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * description:估产明细
 *
 * @version <1> 2018-05-07 wl: Created.
 */
@RestController
@RequestMapping("/dsYield")
public class DsYieldController extends BaseController {

    @Autowired
    private IDsYieldService dsYieldService;

    /**
     * 获取估产的报告生成列表
     * @param reportCreateParam
     * @return
     * @version <1> 2018-05-11 zhangshen: Created.
     */
    @ApiOperation(value = "获取估产的报告生成列表", notes = "获取估产的报告生成列表")
    @ApiImplicitParam(name = "reportCreateParam",value = "报告生成参数",required = false, dataType = "ReportCreateParam")
    @PostMapping("/findDsYieldReportCreateData")
    public PageInfo<DsYield> findDsYieldReportCreateData(ReportCreateParam reportCreateParam){
        PageInfo<DsYield> pages = dsYieldService.findDsYieldReportCreateData(reportCreateParam);
        return pages;
    }

    @ApiOperation(value = "获取估产明细列表", notes = "获取估产明细列表")
    @ApiImplicitParam(name = "dsAreaParam",value = "估产明细查询参数",required = false, dataType = "DsAreaParam")
    @PostMapping("findByPage")
    public PageInfo<DsYield> findByPage(DsYieldParam dsYieldParam, HttpServletRequest request){
        PageInfo<DsYield> pages = dsYieldService.findByPage(dsYieldParam);
        return pages;
    }

    /**
     * 估产明细修改
     * @param request
     * @param dsYieldParam
     * @return
     * @version <1> 2018-05-11 wl： Created.
     */
    @ApiOperation(value = "估产明细修改",notes = "修改估产明细")
    @ApiImplicitParam(name = "dsAreaParam",value = "估产明细实体",required = true,dataType = "DsAreaParam")
    @PostMapping("/edit")
    public ResultMessage edit(HttpServletRequest request, @RequestBody DsYieldParam dsYieldParam){
            dsYieldParam.setModifierName(getCurrentNickName());
            dsYieldParam.setModifier(getCurrentAccountId());
        return dsYieldService.updateDsYield(dsYieldParam);
    }

    /**
     * 估产明细修改
     * @param request
     * @param dsYieldParam
     * @return
     * @version <1> 2018-05-11 wl： Created.
     */
    @ApiOperation(value = "估产明细删除",notes = "删除估产明细")
    @ApiImplicitParam(name = "dsAreaParam",value = "估产明细实体",required = true,dataType = "DsAreaParam")
    @PostMapping("/del")
    public ResultMessage del(HttpServletRequest request, @RequestBody DsYieldParam dsYieldParam){
        dsYieldParam.setDelFlag("0");
        return dsYieldService.updateDsYield(dsYieldParam);
    }


    /**
     * @description: 根据ID查询估产详情
     * @param request
     * @param id
     * @return
     ** @version <1> 2018-05-11 wl : created.
     */
    @ApiOperation(value = "估产明细查询",notes = "按估产明细id查询")
    @ApiImplicitParam(name = "id",value = "估产明细主键",required = true,paramType = "query", dataType = "Integer")
    @PostMapping("/findById")
    public ResultMessage findById(HttpServletRequest request, @RequestParam Integer id){
        return dsYieldService.findById(id);
    }


    /**
     *删除数据集明细
     * @param id 订单Id
     * @return ResultMessage
     * @version <1> 2018-05-11 wl： Created.
     */
    @ApiOperation(value = "删除数据集明细",notes = "删除数据集明细")
    @ApiImplicitParam(name = "id",value = "订单Id",required = true,dataType = "Integer")
    @PostMapping("delete")
    public ResultMessage delete(@RequestParam Integer id){
        DsYieldParam dsYieldParam=new DsYieldParam();
        dsYieldParam.setId(id);
        dsYieldParam.setDelFlag("0");
        return dsYieldService.updateDsYield(dsYieldParam);
    }

    /**
     * 估产明细发布或撤销
     * @param request
     * @param dsYieldParam
     * @return
     * @version <1> 2018-05-22 wl： Created.
     */
    @ApiOperation(value = "估产明细发布或撤销",notes = "估产或撤销分布明细")
    @ApiImplicitParam(name = "dsYieldParam",value = "估产明细实体",required = true,dataType = "dsYieldParam")
    @PostMapping("/publish")
    public ResultMessage publish(HttpServletRequest request, @RequestBody DsYieldParam dsYieldParam){

        dsYieldParam.setPublisher(getCurrentAccountId());
        dsYieldParam.setPublisherName(getCurrentNickName());

        return dsYieldService.publish(dsYieldParam);
    }
}
