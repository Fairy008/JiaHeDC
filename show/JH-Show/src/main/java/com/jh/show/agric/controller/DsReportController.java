package com.jh.show.agric.controller;

import com.github.pagehelper.PageInfo;
import com.jh.biz.controller.BaseController;
import com.jh.biz.feign.IDsReportService;
import com.jh.show.agric.service.IShowDsReportService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * description:报告查询服务:
 * 1.根据区域、作物，时间，精度分页查询所有报告
 * 2.根据区域、作物，时间，精度查询所有报告（时间倒序）
 * 3.预览pdf文件报告
 * @version <1> 2018-08-10 cxw: Created.
 */
@Api(value = "报告查询服务",description="报告查询服务")
@RestController
@RequestMapping("/dsReport")
public class DsReportController extends BaseController {

    @Autowired
    private IDsReportService dsReportService;

    @Autowired
    private IShowDsReportService showDsReportService;

    /**
     *分页条件查询审批通过的报告
     * @param pageNum 当前页
     * @param pageSize 每页显示多少条
     * @param regionId  区域ID
     * @param dsId 数据集ID
     * @param accuracyId 分辨率ID
     * @param cropId   作物ID
     * @param startDate  报告数据开始时间
     * @param endDate    报告数据结束时间
     * @return
     * @version <1> 2018-08-10 cxw : Created.
     */
    @ApiOperation(value = "报告分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页",required = true, dataType = "int" ,paramType="query",defaultValue="1"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示多少条",required = true, dataType = "int" ,paramType="query",defaultValue="10"),
            @ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3100000000"),
            @ApiImplicitParam(name = "dsId",value = "数据集ID",required = true, dataType = "int" ,paramType="query",defaultValue="1801"),
            @ApiImplicitParam(name = "accuracyId",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4011"),
            @ApiImplicitParam(name = "cropId",value = "作物ID",required = true, dataType = "int" ,paramType="query",defaultValue="501"),
            @ApiImplicitParam(name = "startDate",value = "开始日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-01-01"),
            @ApiImplicitParam(name = "endDate",value = "结束日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-12-31")
    })
    @GetMapping("/queryReportPage")
    public PageInfo<Map<String,Object>> queryReportPage(int pageNum, int pageSize, Long regionId, Integer dsId, Integer accuracyId, Integer cropId, String startDate, String endDate){
        return dsReportService.queryReportPage(pageNum,pageSize,regionId,dsId,accuracyId,cropId,startDate,endDate);
    }

    /**
     *分页条件查询审批通过的报告
     * @param regionId  区域ID
     * @param dsId 数据集ID
     * @param accuracyId 分辨率ID
     * @param cropId   作物ID
     * @param dataTime  报告时间（年）
     * @return
     * @version <1> 2018-08-10 cxw : Created.
     */
    @ApiOperation(value = "查询所有报告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3100000000"),
            @ApiImplicitParam(name = "dsId",value = "数据集ID",required = true, dataType = "int" ,paramType="query",defaultValue="1801"),
            @ApiImplicitParam(name = "accuracyId",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4011"),
            @ApiImplicitParam(name = "cropId",value = "作物ID",required = false, dataType = "int" ,paramType="query",defaultValue="501"),
            @ApiImplicitParam(name = "dataTime",value = "日期",required = false, dataType = "String" ,paramType="query",defaultValue="2017")
    })
    @GetMapping("/queryAllReport")
    public ResultMessage queryAllReport(Long regionId, Integer dsId, Integer accuracyId, Integer cropId, String dataTime){
        return dsReportService.queryAllReport(regionId,dsId,accuracyId,cropId,dataTime);
    }

    /**
     * Description: 预览pdf文件报告
     * @param reportId 文件路径
     * @param response
     * @return
     * @version <1> 2018-08-10 cxw : Created.
     * @version <2> 2018-08-23 zhangshen : Update.
     */
    @ApiOperation(value = "预览pdf文件报告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reportId",value = "报告ID",required = true, dataType = "int" ,paramType="query",defaultValue="495"),
            @ApiImplicitParam(name = "response",value = "数据集ID",required = true, dataType = "HttpServletResponse" ,paramType="query"),
    })
    @GetMapping("/previewReportPdf")
    public void previewReportPdf( Integer reportId , HttpServletResponse response){
         //dsReportService.previewReportPdf(reportId,response);
        showDsReportService.previewReportPdf(reportId,response);
    }

}
