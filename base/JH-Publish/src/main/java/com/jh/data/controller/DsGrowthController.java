package com.jh.data.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.data.entity.DsGrowth;
import com.jh.data.model.DsGrowthParam;
import com.jh.data.model.ReportCreateParam;
import com.jh.data.service.IDsGrowthService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * description:长势明细
 *
 * @version <1> 2018-05-07 wl: Created.
 */
@RestController
@RequestMapping("/dsGrowth")
public class DsGrowthController extends BaseController {

    @Autowired
    private IDsGrowthService dsGrowthService;

    @ApiOperation(value = "获取长势明细列表", notes = "获取长势明细列表")
    @ApiImplicitParam(name = "dsGrowthParam",value = "长势明细查询参数",required = false, dataType = "DsGrowthParam")
    @PostMapping("findByPage")
    public PageInfo<DsGrowth> findByPage(DsGrowthParam dsGrowthParam, HttpServletRequest request){
        PageInfo<DsGrowth> pages = dsGrowthService.findByPage(dsGrowthParam);
        return pages;
    }

    /**
     * 长势明细修改
     * @param request
     * @param dsGrowthParam
     * @return
     * @version <1> 2018-05-10 wl： Created.
     */
    @ApiOperation(value = "长势明细修改",notes = "修改长势明细")
    @ApiImplicitParam(name = "dsGrowthParam",value = "长势明细实体",required = true,dataType = "dsGrowthParam")
    @PostMapping("/edit")
    public ResultMessage edit(HttpServletRequest request, @RequestBody DsGrowthParam dsGrowthParam){
            dsGrowthParam.setModifierName(getCurrentNickName());
            dsGrowthParam.setModifier(getCurrentAccountId());
        return dsGrowthService.updateDsGrowth(dsGrowthParam);
    }


    /**
     * @description: 根据ID查询长势详情
     * @param request
     * @param id
     * @return
     ** @version <1> 2018-05-10 wl : created.
     */
    @ApiOperation(value = "长势明细查询",notes = "按长势明细id查询")
    @ApiImplicitParam(name = "id",value = "长势明细主键",required = true,paramType = "query", dataType = "Integer")
    @PostMapping("/findById")
    public ResultMessage findById(HttpServletRequest request, @RequestParam Integer id){
        return dsGrowthService.findById(id);
    }

    /**
     * 获取长势的报告生成列表
     * @param reportCreateParam
     * @return
     * @version <1> 2018-05-11 zhangshen: Created.
     */
    @ApiOperation(value = "获取长势的报告生成列表", notes = "获取长势的报告生成列表")
    @ApiImplicitParam(name = "reportCreateParam",value = "报告生成参数",required = false, dataType = "ReportCreateParam")
    @PostMapping("/findDsGrowthReportCreateData")
    public PageInfo<DsGrowth> findDsGrowthReportCreateData(ReportCreateParam reportCreateParam){
        PageInfo<DsGrowth> pages = dsGrowthService.findDsGrowthReportCreateData(reportCreateParam);
        return pages;
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
        DsGrowthParam dsGrowthParam=new DsGrowthParam();
        dsGrowthParam.setId(id);
        dsGrowthParam.setDelFlag("0");
        return dsGrowthService.updateDsGrowth(dsGrowthParam);
    }

    /**
     * 长势明细发布或撤销
     * @param request
     * @param dsGrowthParam
     * @return
     * @version <1> 2018-06-08 wl： Created.
     */
    @ApiOperation(value = "长势明细发布或撤销",notes = "发布或撤销长势明细")
    @ApiImplicitParam(name = "dsGrowthParam",value = "长势明细实体",required = true,dataType = "DsGrowthParam")
    @PostMapping("/publish")
    public ResultMessage publish(HttpServletRequest request, @RequestBody DsGrowthParam dsGrowthParam){
        dsGrowthParam.setPublisherName(getCurrentNickName());
        dsGrowthParam.setPublisher(getCurrentAccountId());
        return dsGrowthService.publish(dsGrowthParam);
    }
}
