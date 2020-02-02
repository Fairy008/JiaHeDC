package com.jh.briefing.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.briefing.entity.TemplateReporter;
import com.jh.briefing.model.TemplateReporterParam;
import com.jh.briefing.service.ITemplateReporterService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  简报Controller
 * @version <1> 2018-04-13 lxy： Created.
 */
@RestController
@RequestMapping("/templateReporter")
public class TemplateReporterController extends BaseController {
    @Autowired
    private ITemplateReporterService templateReporterService;//模板服务



    public static String Value_ContentType_Html = "text/html";

    /**
     * 简报分页查询
     * @param templateReporterParam 模板参数
     * @return PageInfo
     * @version <1> 2018-04-12 lxy： Created.
     */
    @ApiOperation(value = "简报分页查询",notes = "简报分页查询")
    @ApiImplicitParam(name = "templateReporterParam",value = "简报分页查询参数",required = false,dataType = "TemplateReporterParam")
    @PostMapping("findByPage")
    public PageInfo<TemplateReporterParam> findByPage(TemplateReporterParam templateReporterParam){
        return templateReporterService.findByPage(templateReporterParam);
    }

    /**
     * 生成简报信息
     * @param request 请求参数
     * @param templateReporterParam 模板参数
     * @return ResultMessage
     * @version <1> 2018-04-12 lxy： Created.
     */
    @ApiOperation(value = "生成简报",notes = "生成简报")
    @ApiImplicitParam(name = "templateReporter",value = "生成简报参数",required = false,dataType = "TemplateReporterParam")
    @PostMapping("generatorReporter")
    public ResultMessage generatorReporter(HttpServletRequest request, @RequestBody TemplateReporterParam templateReporterParam){
        templateReporterParam.setCreator(getCurrentNickName());//设置创建者

        return templateReporterService.generatorReporter(templateReporterParam);//调用生成模板服务
    }

    /**
     * 批量生成简报信息
     * @param request 请求参数
     * @param templateReporterParam 模板参数
     * @return ResultMessage
     * @version <1> 2018-04-12 lxy： Created.
     */
    @ApiOperation(value = "批量生成简报",notes = "批量生成简报")
    @ApiImplicitParam(name = "templateReporter",value = "生成简报参数",required = false,dataType = "TemplateReporterParam")
    @PostMapping("batchGeneratorReporter")
    public ResultMessage batchGeneratorReporter(HttpServletRequest request, @RequestBody TemplateReporterParam templateReporterParam){
        templateReporterParam.setCreator(getCurrentNickName());//设置创建者
        return templateReporterService.batchGeneratorReporter(templateReporterParam);//调用生成模板服务
    }

    /**
     * 获取简报信息
     * @param templateReporter 模板查询参数
     * @return ResultMessage
     * @version <1> 2018-04-12 lxy： Created.
     */
    @ApiOperation(value = "获取简报信息",notes = "获取简报信息")
    @ApiImplicitParam(name = "templateReporter",value = "获取简报信息参数对象",required = true,dataType = "TemplateReporter")
    @PostMapping("getById")
    public ResultMessage getById(@RequestBody TemplateReporter templateReporter){
        Long reperterId = templateReporter.getReporterId();//简报编号
        String flag = templateReporter.getFlag();//获取简报类型 1：预览，2：编辑
        return templateReporterService.getBriefReporterByReporterId(reperterId,flag);
    }

    /**
     * 简报信息修改
     * @param templateReporterParam 模板查询参数
     * @return ResultMessage
     * @version <1> 2018-04-12 lxy： Created.
     */
    @ApiOperation(value = "简报信息记录修改",notes = "简报信息记录修改")
    @ApiImplicitParam(name = "templateReporterParam",value = "简报信息参数对象",required = false,dataType = "TemplateReporterParam")
    @PostMapping("update")
    public ResultMessage update(HttpServletRequest request, @RequestBody TemplateReporterParam templateReporterParam){
        ResultMessage result = ResultMessage.fail();
        templateReporterParam.setModifier(getCurrentNickName());//设置修改人
        result = templateReporterService.updateReporter(templateReporterParam);
        return result;
    }

    /**
     * 简报信息状态修改
     * @param templateReporterParam 模板查询参数
     * @return ResultMessage
     * @version <1> 2018-06-03 lxy： Created.
     */
    @ApiOperation(value = "简报信息状态修改",notes = "简报信息状态修改")
    @ApiImplicitParam(name = "templateReporterParam",value = "简报信息参数对象",required = false,dataType = "TemplateReporterParam")
    @PostMapping("updateAudisStatus")
    public ResultMessage updateAudisStatus(HttpServletRequest request, @RequestBody TemplateReporterParam templateReporterParam){
        templateReporterParam.setModifier(getCurrentNickName());//设置修改人
        ResultMessage result = templateReporterService.updateAudisStatusInIds(templateReporterParam);
        return result;
    }

    /**
     * 简报信息记录删除
     * @param templateReporter 模板查询参数
     * @return ResultMessage
     * @version <1> 2018-04-12 lxy： Created.
     */
    @ApiOperation(value = "简报信息记录删除",notes = "简报信息记录删除")
    @ApiImplicitParam(name = "templateReporter",value = "简报信息请求参数",required = false,dataType = "TemplateReporter")
    @PostMapping("delete")
    public ResultMessage delete(@RequestBody TemplateReporter templateReporter){
        ResultMessage result = ResultMessage.fail();
        result = templateReporterService.delete(templateReporter);
        return result;
    }

    @RequestMapping(value = "register")
    public String register(@RequestParam String code, @RequestParam String callback){
//        String appId = "wx9e2ce0498c01af3a";//开发者编号
//        String appsec = "23b8cc66d6a6387f9a784b84a59a45c6";//微信开发者码
//        ApiConfig apiConfig = new ApiConfig(appId,appsec);
//        OauthAPI oauthAPI = new OauthAPI(apiConfig);
//        OauthGetTokenResponse tokenResponse = oauthAPI.getToken(code);
//        String openId = tokenResponse.getOpenid();
//        ResultMessage result = ResultMessage.success(openId);
        String result = callback+"('"+code+"')";
        return result;
    }

    /**
     * 根据ID查询简报详情
     * @param  reportId type
     * @return ResultMessage :
     * @version <1> 2018-07-27 wl:Created.
     */
    @ApiOperation(value = "根据ID查询简报详情",notes = "根据ID查询简报详情")
    @ApiImplicitParam(name = "reportId",value = "简报id",required = true,dataType = "Long")
    @GetMapping("findBriefReportById")
    public ResultMessage findBriefReportById(Long reportId, Integer type,HttpServletResponse response){
        ResultMessage result = templateReporterService.findBriefReportById(reportId,type);
        /*if (result.isFlag()){
            TemplateReporterParam  briefReport = (TemplateReporterParam) result.getData();
            if(type==0){
                DownloadUtil.downloadTextMsg(briefReport.getReportChartMobile(), Value_ContentType_Html,response);
            }else{
                DownloadUtil.downloadTextMsg(briefReport.getReportChartPc(), Value_ContentType_Html,response);
            }

        }*/
        return result;
    }
    //查询报告列表

    //查询单个的



    //微信端查询  条件  一個区域  多个作物
    //报告产品查询   一個区域（）
    //pc管理端查询  reportId  (一個区域  一個作物  开始时间  结束时间)

    //查询列表
    //产品列表   根据关注的区域来查询
    //微信查询  根据关注的单个区域和多个作物查询
    //pc端     查询所有的列表

    //单个查询   reportId  type(pc[文字/图表]/mobile[文字/图表])


    /**
     * 根据ID查询简报详情
     * @param  reportId  type(微信/pc/文字/图表)
     * @return ResultMessage :
     * @version <1> 2018-07-27 wl:Created.
     */
    @ApiOperation(value = "根据ID查询简报详情",notes = "根据ID查询简报详情")
    @ApiImplicitParam(name = "reportId",value = "简报id",required = true,dataType = "Long")
    @GetMapping("findBriefReportByType")
    public ResultMessage findBriefReportByType(Long reportId, Integer type,HttpServletResponse response){
        ResultMessage result = templateReporterService.findBriefReportByType(reportId,type);
        return result;
    }

    /* 根据ID查询简报详情
     * @param  reportId
     * @return ResultMessage :
            * @version <1> 2018-07-27 lj:Created.
     */
    @ApiOperation(value = "根据ID查询简报详情(不包含降雨量和墒情)",notes = "根据ID查询简报详情")
    @ApiImplicitParam(name = "reportId",value = "简报id",required = true,dataType = "Long")
    @GetMapping("findBriefReportByTypeNew")
    public ResultMessage findBriefReportByTypeNew(Long reportId, Integer type,HttpServletResponse response){
        ResultMessage result = templateReporterService.findBriefReportByTypeNew(reportId,type);
        return result;
    }

    /* 根据ID查询简报详情
     * @param  reportId
     * @return ResultMessage :
            * @version <1> 2018-07-27 lj:Created.
     */
    @ApiOperation(value = "根据ID查询简报详情(降雨量和墒情)",notes = "根据ID查询简报详情")
    @ApiImplicitParam(name = "reportId",value = "简报id",required = true,dataType = "String")
    @GetMapping("findBriefReportByTypeTrrm")
    public ResultMessage findBriefReportByTypeTrrm(Long reportId, Integer type,HttpServletResponse response){
        ResultMessage result = templateReporterService.findBriefReportByTypeTrrm(reportId,type);
        return result;
    }

    /**
     * 简报分页查询
     * @param regionId 根据用户关注的区域查询
     * @return PageInfo
     * @version <1> 2018-04-12 lxy： Created.
     */
    @ApiOperation(value = "简报分页查询",notes = "简报分页查询")
    @ApiImplicitParam(name = "templateReporterParam",value = "简报分页查询参数",required = false,dataType = "TemplateReporterParam")
    @GetMapping("findPublishBriefReportByPage")
    public ResultMessage  findPublishBriefReportByPage(Long regionId,Integer rows,Integer page){
        ResultMessage resultMessage=new ResultMessage();
        TemplateReporterParam templateReporterParam=new TemplateReporterParam();
        templateReporterParam.setRegionId(regionId);
        templateReporterParam.setRows(rows);
        templateReporterParam.setPage(page);
        PageInfo<TemplateReporterParam> pageResult= templateReporterService.findByPage(templateReporterParam);
        return ResultMessage.success(pageResult);
    }



    /**
     * 根据生成简报的参数查询该简报是否已存在
     * @param request 请求参数
     * @param templateReporterParam 模板参数
     * @return ResultMessage
     * @version <1> 2018-11-19 Roach： Created.
     */
    @ApiOperation(value = "查询简报是否已存在",notes = "查询简报是否已存在")
    @ApiImplicitParam(name = "templateReporter",value = "查询简报是否已存在参数",required = false,dataType = "TemplateReporterParam")
    @PostMapping("checkBriefIsExist")
    public ResultMessage checkBriefIsExist(HttpServletRequest request, @RequestBody TemplateReporterParam templateReporterParam){
        return templateReporterService.checkBriefIsExist(templateReporterParam);//调用生成模板服务
    }

}
