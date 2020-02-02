package com.jh.collection.controller.nolog;

import com.jh.collection.service.IReportAnalysisService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 采集数据分析controller
 * @version <1> 2019-01-25 xy： Created.
 */
@Api(value = "nolog采集数据分析",description="nolog采集数据分析")
@RestController
@RequestMapping("/nolog/collection/analysis/")
public class NologReportAnalysisController {

    private static final Logger LOG = LoggerFactory.getLogger(NologReportAnalysisController.class);

    @Resource(name="reportAnalysisServiceImpl")
    private IReportAnalysisService reportAnalysisService;

    /**
     * 用户采集子任务关联采集字段
     * @return PageInfo
     * @version <1> 2018-11-19 xy： Created.
     */
    @ApiOperation(value = "",notes = "分布面积历史查询")
    @ApiImplicitParam(name = "taskItemId",value = "用户采集子任务关联采集字段参数",required = false,dataType = "taskItemId")
    @GetMapping("queryDistributionHistory")
    public ResultMessage queryDistributionHistory(@RequestParam(required = true) Integer taskItemId){
        return reportAnalysisService.queryDistributionHistory(taskItemId);
    }
}
