package com.jh.report.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.constant.CommonDefineEnum;
import com.jh.constant.SysConstant;
import com.jh.data.model.ReportCreateParam;
import com.jh.report.entity.DsReport;
import com.jh.report.model.DsReportParam;
import com.jh.report.service.IDsReportService;
import com.jh.util.DownloadUtil;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * 1.报告Controller
 *
 * @version <1> 2018-05-13 10:56 zhangshen: Created.
 */
@RestController
@RequestMapping("/dsReport")
public class DsReportController  extends BaseController {

    @Autowired
    private IDsReportService dsReportService;//报告

    /**
     * Description: 根据条件查询报告是否已经生成
     * @param reportCreateParam
     * @return
     * @version <1> 2018/7/19 13:40 zhangshen: Created.
     */
    @ApiOperation(value = "根据条件查询报告是否已经生成", notes = "根据条件查询报告是否已经生成")
    @ApiImplicitParam(name = "reportCreateParam",value = "报告生成参数",required = true, dataType = "ReportCreateParam")
    @PostMapping("/checkReportIsExist")
    public ResultMessage checkReportIsExist(@RequestBody ReportCreateParam reportCreateParam) {
        return dsReportService.checkReportIsExist(reportCreateParam);
    }

    /**
     * Description: 创建报告
     * @param reportCreateParam
     * @return
     * @version <1> 2018/5/13 12:14 zhangshen: Created.
     */
    @ApiOperation(value = "创建报告", notes = "创建报告")
    @ApiImplicitParam(name = "reportCreateParam",value = "报告生成参数",required = true, dataType = "ReportCreateParam")
    @PostMapping("/createReport")
    public ResultMessage createReport(HttpServletRequest request, @RequestBody ReportCreateParam reportCreateParam){
        reportCreateParam.setCreator(getCurrentAccountId());
        reportCreateParam.setCreatorName(getCurrentNickName());
        return dsReportService.createReportFun(reportCreateParam);
    }

    /**
     * Description: 查询报告信息
     * @param dsReportParam
     * @return
     * @version <1> 2018/5/15 14:56 zhangshen: Created.
     */
    @ApiOperation(value = "查询报告", notes = "查询报告")
    @ApiImplicitParam(name = "dsReportParam",value = "报告查询参数",required = false, dataType = "DsReportParam")
    @PostMapping("/findDsReportByPage")
    public PageInfo<DsReport> findDsReportByPage(DsReportParam dsReportParam){
        PageInfo<DsReport> pages = dsReportService.findDsReportByPage(dsReportParam);
        return pages;
    }

    /**
     * Description: 报告文件下载
     * @param reportId 报告id
     * @param response
     * @return
     * @version <1> 2018/5/15 17:04 zhangshen: Created.
     */
    @ApiOperation("报告文件下载")
    @ApiImplicitParam(name = "reportId",value = "报告文件id",required = true,paramType = "query", dataType = "Integer")
    @RequestMapping("/down")
    public void dsReportDown(@RequestParam Integer reportId, HttpServletResponse response){
        DsReport dsReport = dsReportService.findDsReportById(reportId);//根据id查询报告信息
        if(dsReport != null){
            DownloadUtil.getInstance().downloadFile(dsReport.getFilePath(), response);//根据查询的报告地址,下载对应文件
        }
    }

    /**
     * Description: 报告文件下载
     * @param reportId 报告id
     * @param type 报告类型 word或者pdf
     * @param response
     * @return
     * @version <1> 2018/5/23 17:04 zhangshen: Created.
     */
    @ApiOperation("报告文件下载")
    @RequestMapping("/down2")
    public void dsReportDown2(@RequestParam Integer reportId, @RequestParam String type, HttpServletResponse response){
        DsReport dsReport = dsReportService.findDsReportById(reportId);//根据id查询报告信息
        if("word".equals(type)){
            String filePathPdf = dsReport.getFilePath();//文件路径
            Pattern p = Pattern.compile("pdf");
            String filePathWord = filePathPdf.substring(0, filePathPdf.lastIndexOf(".")) + ".doc" ;
            Matcher m = p.matcher(filePathWord);
            String tmp = m.replaceFirst("word");//将第一个pdf改成word
            dsReport.setFilePath(tmp);
        }
        if(dsReport != null){
            DownloadUtil.getInstance().downloadFile(dsReport.getFilePath(), response);//根据查询的报告地址,下载对应文件
        }
    }

    /**
     * Description: 预览pdf文件报告
     * @param reportId 文件路径
     * @param response
     * @return 
     * @version <1> 2018/5/16 15:26 zhangshen: Created.
     */
    @GetMapping("/previewReportPdf")
    public void previewReportPdf(@RequestParam Integer reportId , HttpServletResponse response){
        DsReport dsReport = dsReportService.findDsReportById(reportId);//根据id查询报告信息
        if(dsReport != null){
            dsReportService.previewReportPdf(dsReport.getFilePath(), response);//根据查询的报告地址,预览对应文件
        }
    }

    /**
     * Description: 根据报告ids,批量发布报告
     * @param reportIds
     * @return
     * @version <1> 2018/5/18 15:34 zhangshen: Created.
     */
    @ApiOperation("批量发布报告")
    @ApiImplicitParam(name = "reportIds",value = "报告信息ids",required = true,dataType = "String")
    @PostMapping("/batchPublishReport")
    public ResultMessage batchPublishReport(@RequestBody String reportIds, HttpServletRequest request){
//        PermAccount permAccount = getCurrentPermAccount(request);//获取登录人信息
        DsReport dsReport = new DsReport();
        dsReport.setPublisherName(getCurrentNickName());

        return dsReportService.batchPublishReport(reportIds, dsReport);
    }

    /**
     * Description: 根据id查询报告信息
     * @param reportId 报告id
     * @return
     * @version <1> 2018/5/21 10:04 zhangshen: Created.
     */
    @ApiOperation("报告文件下载")
    @ApiImplicitParam(name = "reportId",value = "报告文件id",required = true, dataType = "Integer")
    @RequestMapping("/findDsReportById")
    public ResultMessage findDsReportById(@RequestParam Integer reportId){
        DsReport dsReport = dsReportService.findDsReportById(reportId);//根据id查询报告信息
        boolean isExist = dsReportService.findWordIsExist(dsReport.getFilePath(), ".doc");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dsReport", dsReport);
        map.put("isExist", isExist);
        return new ResultMessage(true, CommonDefineEnum.SUCCESS.getValue(), CommonDefineEnum.SUCCESS.getMesasge(), map);
    }

    /**
     * Description: 编辑报告和上传, reportId为空是上传,reportId不为空是编辑
     * @param request
     * @return
     * @version <1> 2018-05-21 zhangshen : Created.
     */
    @ApiOperation(value="编辑报告和上传",notes="从请求对象中获取数据")
    @PostMapping("/updateReportById")
    public @ResponseBody ResultMessage updateReportById(HttpServletRequest request){
        String reportId = request.getParameter("reportId");
        DsReport dsReport = new DsReport();
        if(StringUtils.isNotBlank(reportId)){//有reportId,则更新
            dsReport.setModifier(getCurrentAccountId());
            dsReport.setModifierName(getCurrentNickName());
        }else if(StringUtils.isBlank(reportId)){//没有reportId,则新增
            dsReport.setCreator(getCurrentAccountId());
            dsReport.setCreatorName(getCurrentNickName());
        }
        return dsReportService.updateReportById(request, dsReport);
    }

    /**
     * Description: 根据报告ids,批量撤回报告
     * @param reportIds
     * @return
     * @version <1> 2018/5/24 11:34 zhangshen: Created.
     */
    @ApiOperation("批量撤回报告")
    @ApiImplicitParam(name = "reportIds",value = "报告信息ids",required = true,dataType = "String")
    @PostMapping("/batchCancelReport")
    public ResultMessage batchCancelReport(@RequestBody String reportIds, HttpServletRequest request){
        return dsReportService.batchCancelReport(reportIds);
    }

    /**
     * Description: 根据报告ids,批量删除报告
     * @param reportIds
     * @return
     * @version <1> 2018/5/24 11:34 zhangshen: Created.
     */
    @ApiOperation("批量删除报告")
    @ApiImplicitParam(name = "reportIds",value = "报告信息ids",required = true,dataType = "String")
    @PostMapping("/batchDeleteReport")
    public ResultMessage batchDeleteReport(@RequestBody String reportIds, HttpServletRequest request){
        return dsReportService.batchDeleteReport(reportIds);
    }

    /**
     * Description: 根据条件查询数据时间
     * @param reportCreateParam
     * @return
     * @version <1> 2018/7/18 17:10 zhangshen: Created.
     */
    @ApiOperation(value = "根据条件查询数据时间", notes = "根据条件查询数据时间")
    @ApiImplicitParam(name = "reportCreateParam",value = "报告生成参数",required = true, dataType = "ReportCreateParam")
    @PostMapping("/queryDateTimeList")
    public ResultMessage queryDateTimeList(@RequestBody ReportCreateParam reportCreateParam) {
        return dsReportService.queryDateTimeList(reportCreateParam);
    }

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
     * @version <1> 2018-05-10 xzg : Created.
     */
    @ApiOperation(value = "报告分页查询")
    @RequestMapping(value = "/queryReportPage",method = RequestMethod.GET)
    public PageInfo<Map<String,Object>> queryReportPage(int pageNum, int pageSize, Long regionId, Integer dsId, Integer accuracyId, Integer cropId, String startDate, String endDate){
      /*  String token = getToken();
        return dsReportService.findReportPage(pageNum,pageSize,regionId,dsId,accuracyId,cropId,startDate,endDate,token);*/
        return dsReportService.findDsReportPage(pageNum,pageSize,regionId,dsId,accuracyId,cropId,startDate,endDate);
    }

    /**
     *查询所有审批通过的报告
     * @param regionId  区域ID
     * @param dsId 数据集ID
     * @param accuracyId 分辨率ID
     * @param cropId   作物ID
     * @param dataTime  报告时间（年）
     * @return
     * @version <1> 2018-08-10 cxw : Created.
     */
    @ApiOperation(value = "查询所有报告")
    @RequestMapping(value = "/queryAllReport",method = RequestMethod.GET)
    public ResultMessage queryAllReport( Long regionId, Integer dsId, Integer accuracyId, Integer cropId, String dataTime){
        return dsReportService.findAllReport(regionId,dsId,accuracyId,cropId,dataTime);
    }

    /**
     * Description:查询报告列表（其他服务调用）
     * @param
     * @return
     * @version <1> 2018-08-17 lj:Created.
     */
    @ApiOperation(value = "查询报告列表（其他服务调用）", notes = "查询报告列表（其他服务调用）")
    @ApiImplicitParam(name = "page",value = "报告查询（其他服务调用）参数",required = false, dataType = "Integer")
    @GetMapping("/findReportShowByPage")
    public PageInfo<Map<String,Object>> findReportShowByPage(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam Integer publish_status,@RequestParam Long regionId){
        Map<String,Object> map =new HashMap<>();
        map.put("page",page);
        map.put("rows",rows);
        map.put("regionId",regionId);
        map.put("publish_status",publish_status);
        PageInfo<Map<String,Object>> pages = dsReportService.findReportShowByPage(map);
        return pages;
    }

    /**
     * 根据ID查询报告详情（其他服务调用）
     * @param  reportId
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
    @ApiOperation(value = "根据ID查询报告（其他服务调用）详情",notes = "根据ID查询报告（其他服务调用）详情")
    @ApiImplicitParam(name = "reportId",value = "报告id",required = true,dataType = "Integer")
    @GetMapping("/findReportShowById")
    public ResultMessage findReportShowById(@RequestParam Integer reportId){
        return dsReportService.findReportShowById(reportId);
    }


    /**
     * 返回所有的报告种类
     * @return ResultMessage :
     * @version <1> 2018-11-26 Roach:Created.
     */
    @ApiOperation(value = "返回所有的报告种类",notes = "返回所有的报告种类")
    @GetMapping("/findAllReportTempType")
    public ResultMessage findAllReportTempType(){
        return dsReportService.findAllReportTempType();
    }

}
