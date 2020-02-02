package com.jh.show.report.controller;

import com.github.pagehelper.PageInfo;
import com.jh.biz.controller.BaseController;
import com.jh.constant.CommonDefineEnum;
import com.jh.show.report.common.ResultConstantMsg;
import com.jh.show.report.enums.ReportEnum;
import com.jh.biz.feign.IRegionService;
import com.jh.show.report.service.IReportService;
import com.jh.util.DateUtil;
import com.jh.util.DownloadUtil;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * description:报告信息
 *
 * @version <1> 2018-06-24 wl: Created.
 */
@RestController
@RequestMapping("/report")
public class ReportController extends BaseController {

    @Autowired
    private IReportService reportService;

    @Autowired
    private IRegionService regionService;

    /**
     * Description: 查询报告列表
     * @param
     * @return
     * @version <1> 2018-06-24 wl:Created.
     */
    @ApiOperation(value = "查询报告列表", notes = "查询报告列表")
    @ApiImplicitParam(name = "page",value = "报告查询参数",required = false, dataType = "Integer")
    @GetMapping("/findReportByPage")
    public PageInfo<Map<String,Object>> findReportByPage(@RequestParam Integer page, @RequestParam Integer rows, HttpServletRequest request){
        Map<String,Object> map =new HashMap<>();
        Long regionId=null;
        Map<String,Object> currentAccount = this.getCurrentUserInfo(request);
        if (currentAccount != null && currentAccount.containsKey("region_id")){
            regionId = Long.parseLong(currentAccount.get("region_id").toString());
        }
        map.put("regionId",regionId);
        map.put("page",page);
        map.put("rows",rows);
        PageInfo<Map<String,Object>> pages = reportService.findReportByPage(map);
        return pages;
    }

    /**
     * 根据ID查询报告详情
     * @param  reportId
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
    @ApiOperation(value = "根据ID查询报告详情",notes = "根据ID查询报告详情")
    @ApiImplicitParam(name = "reportId",value = "报告id",required = true,dataType = "Long")
    @GetMapping("findReportById")
    public void findReportById(@RequestParam Integer reportId, HttpServletResponse response){
        Map<String,Object> map=reportService.findReportById(reportId);
        if(map != null){
            String filePath = map.get("file_path").toString();
            String fileAbsolutePath = CephUtils.getAbsolutePath(filePath);
            fileAbsolutePath = fileAbsolutePath.replace("\\",File.separator);
            DownloadUtil.downloadFileStream(fileAbsolutePath, ResultConstantMsg.Value_ContentType_ShowPdf,response);
        }
    }

    /**
     * 查询时间轴
     * @param id  定制区域id
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
    @ApiOperation(value = "查询时间轴",notes = "查询时间轴")
    @GetMapping("findTimeAxis")
    public ResultMessage findTimeAxis(HttpServletRequest request, @RequestParam(required = false) Integer id ){
        Map<String,Object> map =new HashMap<>();
        map.put("id",id);
        if(id != null){//根据用户自定义的区域查询图层
            map.put("regionType", ReportEnum.DRAW_AREA.getValue());
            List<Map<String,Object>> userRegion=reportService.findCustomLocaleList(map);
            map.put("bbox",userRegion.get(0).get("bbox"));

            ResultMessage resultMessage = regionService.findRegion(map);
            List<Map<String,Object>> list = new ArrayList<>();
            if(resultMessage.getData()!=null){
                list = (List<Map<String,Object>>) resultMessage.getData();
            }

           // List<Map<String,Object>> list =(List<Map<String,Object>>)regionService.findRegion(map).getData();
            if(list.size()>0){
                List listRegions=new ArrayList();
                for(int i=0;i<list.size();i++){
                    listRegions.add(Long.parseLong(list.get(i).get("region_id").toString()));
                }
                map.put("regionList",listRegions);
            }
        }else{
            map.put("regionId",getCurrentRegionId(request));
        }
        ResultMessage result = reportService.findTimeAxis(map);
        return result;
    }


    /**
     * 查询图层
     * @param dataTime 数据时间 id用户定制的区域id
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
    @ApiOperation(value = "查询图层",notes = "查询图层")
    @ApiImplicitParam(name = "dataTime",value = "图层时间",required = true,dataType = "String")
    @GetMapping("findLayers")
    public ResultMessage findLayers(HttpServletRequest request, @RequestParam String dataTime , @RequestParam(required = false)  Integer id ){
        Map<String ,Object> map=new HashMap<>();
        map.put("dataTime", DateUtil.strToDate(dataTime));
        // map.put("dataTime", dataTime);
        if(id != null){//根据用户自定义的区域查询图层
            map.put("id",id);
            List<Map<String,Object>> userRegion=reportService.findCustomLocaleList(map);
            map.put("bbox",userRegion.get(0).get("bbox"));

            ResultMessage resultMessage = regionService.findRegion(map);
            List<Map<String,Object>> list = new ArrayList<>();
            if(resultMessage.getData()!=null){
                list = (List<Map<String,Object>>) resultMessage.getData();
            }

           // List<Map<String,Object>> list =(List<Map<String,Object>>) regionService.findRegion(map).getData();
            if(list.size()>0){
                List listRegions=new ArrayList();
                for(int i=0;i<list.size();i++){
                    listRegions.add(Long.parseLong(list.get(i).get("region_id").toString()));
                }
                map.put("regionList",listRegions);
            }
        }else{
            map.put("regionId" ,getCurrentRegionId(request));
        }
        ResultMessage result = reportService.findLayers(map);
        return result;
    }

    /**
     * @description: 保存用户自定义的区域信息并生成相关区域报告
     * @param request
     * @param  paramMap 自定义区域名  bbox  边界信息
     * @return
     * @version <1> 2018-06-24 wl:Created.
     */
    @ApiOperation(value = "保存用户绘制的区域信息",notes = "保存用户绘制的区域信息")
    @ApiImplicitParam(name = "map",value = "边界信息",required = true,dataType = "Map<String,Object>")
    @PostMapping("/regionByUser")
    public ResultMessage regionByUser(HttpServletRequest request, @RequestBody Map<String,Object> paramMap){
        //如果用户已经有五个自定义区域则不可再添加
        Map<String,Object> map=new HashMap<>();
        map.put("userId",getCurrentAccountId(request));
        map.put("regionType",ReportEnum.DRAW_AREA.getValue());
        List<Map<String,Object>> userRegion=reportService.findCustomLocaleList(map);
        if(userRegion.size()>=5){
            return new ResultMessage(false, CommonDefineEnum.FAIL.getValue(),null, ResultConstantMsg.drawRegionNum);
        }else{
            paramMap.put("userId",getCurrentAccountId(request));
            return  reportService.saveRegionByUser(paramMap);
        }

    }

    /**
     * @description: 查询用户绘制的区域作物的分布信息
     * @param request
     * @param bbox  边界信息
     * @return
     * @version <1> 2018-06-25 wl:Created.
     */
    @ApiOperation(value = "查询用户绘制的区域作物的分布信息",notes = "查询用户绘制的区域作物的分布信息")
    @ApiImplicitParam(name = "bbox",value = "边界信息",required = true,dataType = "String")
    @GetMapping("/findAreas")
    public ResultMessage findAreas(HttpServletRequest request, @RequestParam String bbox , @RequestParam String dataTime){
        Map<String,Object> map = new HashMap<>();
        map.put("bbox",bbox);
        map.put("dataTime",dataTime);
        return  reportService.findAreas(bbox);
    }

    /**
     * @description: 查询用户绘制的区域列表
     * @param request
     * @return
     * @version <1> 2018-06-27 wl:Created.
     */
    @ApiOperation(value = "查询用户绘制的区域列表",notes = "查询用户绘制的区域列表")
    @GetMapping("/findCustomLocal")
    public ResultMessage findCustomLocal(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",getCurrentAccountId(request));
        map.put("regionType",ReportEnum.DRAW_AREA.getValue());
        List<Map<String,Object>> userAreaList=reportService.findCustomLocaleList(map);
        if(userAreaList.size()==0){
            return new ResultMessage(false,CommonDefineEnum.FAIL.getValue(),ResultConstantMsg.noData,userAreaList);
        }
        return ResultMessage.success(userAreaList) ;
    }

    /**
     * Description: 预览pdf文件报告
     * @param reportId 文件路径
     * @param response
     * @return
     * @version <1> 2018/5/16 15:26 zhangshen: Created.
     */
    @GetMapping("/previewReportPdf/{reportId}")
    public void previewReportPdf(@PathVariable Integer reportId , HttpServletResponse response){
        Map<String,Object> map=reportService.findReportById(reportId);
        if(map != null){
            String filePath = map.get("file_path").toString();
            String fileAbsolutePath = CephUtils.getAbsolutePath(filePath);
            fileAbsolutePath = fileAbsolutePath.replace("\\",File.separator);
            DownloadUtil.downloadFileStream(fileAbsolutePath, ResultConstantMsg.Value_ContentType_ShowPdf,response);
        }
    }

    /**
     * 获取分布统计数据
     * @return
     * @version <1> 2018/6/28 15:26 xzg: Created.
     */
    @PostMapping("/findDsArea")
    public ResultMessage findDsArea(@RequestBody Map<String,String> params, HttpServletRequest request){
        String bbox = null;
        Long regionId = getCurrentRegionId(request);
        String dataTime = null;
        if (params != null){
            if (params.containsKey("bbox")){
                bbox = params.get("bbox").toString();
            }
            if (params.containsKey("dataTime")){
                dataTime = params.get("dataTime").toString();
            }
        }
        return reportService.findCustomRegionArea(bbox,regionId,dataTime);
    }

}
