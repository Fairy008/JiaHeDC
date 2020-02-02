package com.jh.briefingNew.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.briefingNew.entity.BriefingReport;
import com.jh.briefingNew.model.BriefingReportParam;
import com.jh.briefingNew.service.IBriefingReportService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * description:生成农情简报
 *
 * @version <1> 2018-07-18 wl: Created.
 */
@RestController
@RequestMapping("/briefingReport")
public class BriefingReportController extends BaseController {

    @Autowired
    private IBriefingReportService briefingReportService;

    /**
     * 简报分页查询
     * @param briefingReportParam 模板参数
     * @return PageInfo
     * @version <1> 2018-07-23 wl： Created.
     */
    @ApiOperation(value = "简报分页查询",notes = "简报分页查询")
    @ApiImplicitParam(name = "templateReporterParam",value = "简报分页查询参数",required = false,dataType = "TemplateReporterParam")
    @PostMapping("findByPage")
    public PageInfo<BriefingReport> findByPage(BriefingReportParam briefingReportParam){
        return briefingReportService.findByPage(briefingReportParam);
    }

    /**
     * 生成简报信息
     * @param request 请求参数
     * @param briefingReportParam 模板参数
     * @return ResultMessage
     * @version <1> 2018-07-23 wl： Created.
     */
    @ApiOperation(value = "生成简报",notes = "生成简报")
    @ApiImplicitParam(name = "templateReporter",value = "生成简报参数",required = false,dataType = "TemplateReporterParam")
    @PostMapping("createReport")
    public ResultMessage createReport(HttpServletRequest request, @RequestBody BriefingReportParam briefingReportParam){
        //生成图表类简报
        briefingReportParam.setCreator(getCurrentNickName());//设置创建者
        return briefingReportService.createReport(briefingReportParam);//调用生成模板服务

    }

}
