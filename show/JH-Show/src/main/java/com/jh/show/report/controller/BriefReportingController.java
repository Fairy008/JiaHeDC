package com.jh.show.report.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.biz.controller.BaseController;
import com.jh.biz.feign.IBriefService;
import com.jh.show.report.service.IBriefReportService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * description:查询简报
 *
 * @version <1> 2018-06-24 wl: Created.
 */
@RestController
@RequestMapping("nolog/briefingReport")
public class BriefReportingController extends BaseController {

    @Autowired
    private IBriefReportService briefReportService;

    @Autowired
    private IBriefService briefService;

    /**
     * Description: 查询简报列表
     * @param  (page /rows)
     * @return
     * @version <1> 2018-06-24 wl:Created.
     */
    @ApiOperation(value = "查询简报列表", notes = "查询简报列表")
    @ApiImplicitParam(name = "pages",value = "报告查询参数",required = false, dataType = "Integer")
    @GetMapping("/findBriefReportByPage")
    public PageInfo<Map<String,Object>> findBriefReportByPage(@RequestParam Integer page, @RequestParam Integer rows, HttpServletRequest request){
        Long regionId=null;
        Map<String,Object> currentAccount = this.getCurrentUserInfo(request);
        if (currentAccount != null && currentAccount.containsKey("region_id")){
            regionId = Long.parseLong(currentAccount.get("region_id").toString());
        }
        if(null == regionId){
            Map<String,Object> userData = this.getCurrentUserData(request);
            Map<String,Object> defaultProduct = (Map<String,Object>)userData.get("defaultProduct");
            if (defaultProduct != null && defaultProduct.get("regionId") != null){
                regionId = Long.valueOf(defaultProduct.get("regionId").toString());
            }
        }

        Map<String,Object> mapBrief=new HashMap<>();
        mapBrief.put("page",page);
        mapBrief.put("rows",rows);
        mapBrief.put("regionId",regionId);
        PageInfo<Map<String,Object>> pages = briefReportService.findBriefReportByPage(mapBrief);
        return pages;
    }

    /**
     * 根据ID查询简报详情
     * @param  reportId
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
    @ApiOperation(value = "根据ID查询简报详情",notes = "根据ID查询简报详情")
    @ApiImplicitParam(name = "reportId",value = "简报id",required = true,dataType = "Long")
    @GetMapping("findBriefReportById")
    public ResultMessage findBriefReportById(@RequestParam Long reportId, HttpServletResponse response){
        ResultMessage result = briefService.findBriefReportByType(reportId,1);
        return result;
    }


    /**
     * 简报预览
     * @param  reportId
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
    @ApiOperation(value = "简报预览",notes = "简报预览")
    @ApiImplicitParam(name = "reportId",value = "简报id",required = true,dataType = "Long")
    @GetMapping("previewBriefReportById/{reportId}")
    public ResultMessage previewBriefReportById(@PathVariable Long reportId, HttpServletResponse response){
        ResultMessage result = briefService.findBriefReportByType(reportId,1);
        return result;
    }

    /**
     * 简报预览不包含降雨量和墒情
     * @param  reportId
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
    @ApiOperation(value = "简报预览不包含降雨量和墒情",notes = "简报预览不包含降雨量和墒情")
    @ApiImplicitParam(name = "reportId",value = "简报id",required = true,dataType = "Long")
    @GetMapping("previewBriefReportByIdNew/{reportId}")
    public ResultMessage previewBriefReportByIdNew(@PathVariable Long reportId, HttpServletResponse response){
        ResultMessage result = briefService.findBriefReportByTypeNew(reportId,1);
        return result;
    }

    /**
     * 简报预览降雨量和墒情(因降雨量数据量较大查询慢，单独用接口查询)
     * @param  reportId
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
    
    @ApiOperation(value = "简报预览降雨量和墒情",notes = "简报预览降雨量和墒情")
    @ApiImplicitParam(name = "reportId",value = "简报id",required = true,dataType = "Long",paramType = "query",defaultValue = "5720")
    @GetMapping("previewBriefReportByIdTrrm/{reportId}")
    public ResultMessage previewBriefReportByIdTrrm(@PathVariable Long reportId, HttpServletResponse response){
        ResultMessage result = briefService.findBriefReportByTypeTrrm(reportId,1);
        return result;
    }

    /**
     * 查询区域最新简报
     * @param  regionId
     * @return ResultMessage :
     * @version <1> 2018-06-24 lj:Created.
     */
    @ApiOperation(value = "查询区域最新简报",notes = "查询区域最新简报")
    @ApiImplicitParam(name = "regionId",value = "简报id",required = true,dataType = "Long")
    @GetMapping("getNewReportByRegionId")
    public ResultMessage getNewReportByRegionId(@RequestParam Long regionId){
        Long reportId=briefReportService.getNewBriefReport(regionId);
        if(reportId==null){
            return ResultMessage.success();
        }
        ResultMessage result = briefService.findBriefReportByType(reportId,1);
        return result;
    }


    /**
     * 简报分页查询
     * @param pageNum   第几页
     * @param pageSize  每页查询多少条
     * @param regionId  区域id
     * @param cropId    作物id
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    @ApiOperation(value = "简报分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页",required = true, dataType = "int" ,paramType="query",defaultValue="1"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示多少条",required = true, dataType = "int" ,paramType="query",defaultValue="10"),
            @ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3100000000"),
            @ApiImplicitParam(name = "cropId",value = "作物ID",required = true, dataType = "int" ,paramType="query",defaultValue="501"),
            @ApiImplicitParam(name = "startDate",value = "开始日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-01-01"),
            @ApiImplicitParam(name = "endDate",value = "结束日期",required = true, dataType = "String" ,paramType="query",defaultValue="2017-12-31")
    })
    @GetMapping("/findBriefPage")
    public PageInfo<Map<String,Object>> findBriefPage(int pageNum, int pageSize, Long regionId, Integer cropId, String startDate, String endDate){
        PageHelper.startPage(pageNum,pageSize);
        Map<String,Object> mapBrief=new HashMap<>();
        mapBrief.put("regionId",regionId);
        mapBrief.put("cropId",cropId);
        mapBrief.put("startDate",startDate);
        mapBrief.put("endDate",endDate);
        PageInfo<Map<String,Object>> pages = briefReportService.findBriefPage(mapBrief);
        return pages;

    }


}
