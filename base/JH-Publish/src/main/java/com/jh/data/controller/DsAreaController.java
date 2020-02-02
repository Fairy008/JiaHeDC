package com.jh.data.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.data.entity.DsArea;
import com.jh.data.model.DsAreaParam;
import com.jh.data.model.ReportCreateParam;
import com.jh.data.service.IDsAreaService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * description:分布明细
 *
 * @version <1> 2018-05-07 wl: Created.
 */
@RestController
@RequestMapping("/dsArea")
public class DsAreaController extends BaseController {

    @Autowired
    private IDsAreaService dsAreaService;

    @ApiOperation(value = "获取分布明细列表", notes = "获取分布明细列表")
    @ApiImplicitParam(name = "dsAreaParam",value = "分布明细查询参数",required = false, dataType = "DsAreaParam")
    @PostMapping("findByPage")
    public PageInfo<DsArea> findByPage(DsAreaParam dsAreaParam, HttpServletRequest request){
        PageInfo<DsArea> pages = dsAreaService.findByPage(dsAreaParam);
        return pages;
    }

    /**
     * 分布明细修改
     * @param request
     * @param dsAreaParam
     * @return
     * @version <1> 2018-05-08 wl： Created.
     */
    @ApiOperation(value = "分布明细修改",notes = "修改分布明细")
    @ApiImplicitParam(name = "dsAreaParam",value = "分布明细实体",required = true,dataType = "DsAreaParam")
    @PostMapping("/edit")
    public ResultMessage edit(HttpServletRequest request, @RequestBody DsAreaParam dsAreaParam){
            dsAreaParam.setModifierName(getCurrentNickName());
            dsAreaParam.setModifier(getCurrentAccountId());
        return dsAreaService.updateDsArea(dsAreaParam);
    }


    /**
     * @description: 根据ID查询分布详情
     * @param request
     * @param id
     * @return
     ** @version <1> 2018-05-09 wl : created.
     */
    @ApiOperation(value = "分布明细查询",notes = "按分布明细id查询")
    @ApiImplicitParam(name = "id",value = "分布明细主键",required = true,paramType = "query", dataType = "Integer")
    @PostMapping("/findById")
    public ResultMessage findById(HttpServletRequest request, @RequestParam Integer id){
        return dsAreaService.findById(id);
    }

    /**
     * 获取分布的报告生成列表
     * @param reportCreateParam
     * @return
     * @version <1> 2018-05-10 zhangshen: Created.
     */
    @ApiOperation(value = "获取分布的报告生成列表", notes = "获取分布的报告生成列表")
    @ApiImplicitParam(name = "reportCreateParam",value = "报告生成参数",required = false, dataType = "ReportCreateParam")
    @PostMapping("/findDsAreaReportCreateData")
    public PageInfo<DsArea> findDsAreaReportCreateData(ReportCreateParam reportCreateParam){
        PageInfo<DsArea> pages = dsAreaService.findDsAreaReportCreateData(reportCreateParam);
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
        DsAreaParam dsAreaParam=new DsAreaParam();
        dsAreaParam.setId(id);
        dsAreaParam.setDelFlag("0");
        return dsAreaService.updateDsArea(dsAreaParam);
    }

    /**
     * 分布明细发布或撤销
     * @param request
     * @param dsAreaParam
     * @return
     * @version <1> 2018-05-16 wl： Created.
     */
    @ApiOperation(value = "分布明细发布或撤销",notes = "发布或撤销分布明细")
    @ApiImplicitParam(name = "dsAreaParam",value = "分布明细实体",required = true,dataType = "DsAreaParam")
    @PostMapping("/publish")
    public ResultMessage publish(HttpServletRequest request, @RequestBody DsAreaParam dsAreaParam){
        dsAreaParam.setPublisherName(getCurrentNickName());
        dsAreaParam.setPublisher(getCurrentAccountId());
        return dsAreaService.publish(dsAreaParam);
    }

}
