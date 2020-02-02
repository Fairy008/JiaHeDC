package com.jh.data.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.data.entity.DsTtn;
import com.jh.data.model.DsTtnParam;
import com.jh.data.model.ReportCreateParam;
import com.jh.data.model.TemTrmmTotalDataReturn;
import com.jh.data.model.TtnParam;
import com.jh.data.service.IDsTtnService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * description:地温、干旱、降雨
 *
 * @version <1> 2018-05-07 wl: Created.
 */
@RestController
@RequestMapping("/dsTtn")
public class DsTtnController extends BaseController {

    @Autowired
    private IDsTtnService dsTtnService;

    /**
     * 获取区域内降水Trim、地表温度T、干旱Nddi的报告生成列表
     * @param reportCreateParam
     * @return
     * @version <1> 2018-05-11 zhangshen: Created.
     */
    @ApiOperation(value = "获取区域内降水Trim、地表温度T、干旱Nddi的报告生成列表", notes = "获取区域内降水Trim、地表温度T、干旱Nddi的报告生成列表")
    @ApiImplicitParam(name = "reportCreateParam",value = "报告生成参数",required = false, dataType = "ReportCreateParam")
    @PostMapping("/findDsTtnldReportCreateData")
    public PageInfo<DsTtn> findDsTtnldReportCreateData(ReportCreateParam reportCreateParam){
        PageInfo<DsTtn> pages = dsTtnService.findDsTtnldReportCreateData(reportCreateParam);
        return pages;
    }



    @ApiOperation(value = "获取地温、降雨明细列表", notes = "获取地温、降雨明细列表")
    @ApiImplicitParam(name = "dsAreaParam",value = "地温、降雨明细查询参数",required = false, dataType = "DsAreaParam")
    @PostMapping("findByPage")
    public PageInfo<DsTtn> findByPage(DsTtnParam dsTtnParam, HttpServletRequest request){
        PageInfo<DsTtn> pages = dsTtnService.findByPage(dsTtnParam);
        return pages;
    }

    /**
     * 地温、降雨明细修改
     * @param request
     * @param dsTtnParam
     * @return
     * @version <1> 2018-05-11 wl： Created.
     */
    @ApiOperation(value = "地温、降雨明细修改",notes = "修改地温、降雨明细")
    @ApiImplicitParam(name = "dsAreaParam",value = "地温、降雨明细实体",required = true,dataType = "DsAreaParam")
    @PostMapping("/edit")
    public ResultMessage edit(HttpServletRequest request, @RequestBody DsTtnParam dsTtnParam){
            dsTtnParam.setModifierName(getCurrentNickName());
            dsTtnParam.setModifier(getCurrentAccountId());
        return dsTtnService.updateDsTtn(dsTtnParam);
    }


    /**
     * @description: 根据ID查询地温、降雨详情
     * @param request
     * @param id
     * @return
     ** @version <1> 2018-05-11 wl : created.
     */
    @ApiOperation(value = "地温、降雨明细查询",notes = "按地温、降雨明细id查询")
    @ApiImplicitParam(name = "id",value = "地温、降雨明细主键",required = true,paramType = "query", dataType = "Integer")
    @PostMapping("/findById")
    public ResultMessage findById(HttpServletRequest request, @RequestParam Integer id){
        return dsTtnService.findById(id);
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
        DsTtnParam dsTtnParam=new DsTtnParam();
        dsTtnParam.setId(id);
        dsTtnParam.setDelFlag("0");
        return dsTtnService.updateDsTtn(dsTtnParam);
    }

    /**
     * 查询指定时间类上中下旬的雨量和地温的均值以及十年的均值
     * @param param
     * @return
     * @version <1> 2018-3-21 lxy:Created
     */
    @ApiOperation(value="查询指定时间内上中下旬的雨量总量和地温的均值以及十年的均值",notes="根据区域、日期段查询上中下旬的雨量总量和地温的均值以及十年的均值" )
    @PostMapping("/queryTrmmAndTempForTenDaysAndHistory")
    public PageInfo<TemTrmmTotalDataReturn>  queryTrmmAndTempForTenDaysAndHistory(TtnParam param){
        ResultMessage resultMessage = new ResultMessage();

        if(param != null){
           return dsTtnService.findTrmmAndTempForTenDaysAndHistory(param);
        }
        else{
            return new PageInfo<TemTrmmTotalDataReturn>(null);
        }

    }

    /**
     * 查询指定时间类上中下旬的雨量和地温的均值以及十年的均值
     * @param param
     * @return
     * @version <1> 2018-3-21 lxy:Created
     */
    @ApiOperation(value="查询指定时间内上中下旬的雨量总量和地温的均值以及十年的均值",notes="根据区域、日期段查询上中下旬的雨量总量和地温的均值以及十年的均值" )
    @PostMapping("/queryTtnForTenDaysAndHistory")
    public ResultMessage queryTtnForTenDaysAndHistory(@RequestBody TtnParam param){
        ResultMessage resultMessage = ResultMessage.success();
        if(param != null){
            List<TemTrmmTotalDataReturn> listRetun =  dsTtnService.findTtnForTenDaysAndHistory(param);
            if(listRetun.size() == 0){
                resultMessage = ResultMessage.fail();
                resultMessage.setData(null);
            }else{
                resultMessage.setData(listRetun);
            }
        }
        else{
            resultMessage = ResultMessage.fail();
            resultMessage.setData(null);
        }
        return resultMessage;
    }

    /**
     * 查询指定时间类上中下旬的雨量和地温的均值以及十年的均值
     * @param regionId 区域
     * @param regionName 区域名
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     * @version <1> 2018-06-29 cxw:Created
     */
    @ApiOperation(value = "导出查询数据",notes = "")
    @GetMapping( "/exportData")
    public ResultMessage exportData(HttpServletRequest request, HttpServletResponse response, Long regionId, String regionName, String startDate, String endDate) throws IOException, ParseException {
        return dsTtnService.exportData(request,response,regionId,regionName,startDate,endDate);
    }

    /**
     * 查询指定时间类上中下旬的雨量和地温的均值以及十年的均值
     * @param regionId 区域
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     * @version <1> 2018-06-29 cxw:Created
     */
    @ApiOperation(value = "导出查询数据判断",notes = "")
    @GetMapping(value = "/isExistData")
    public ResultMessage isExistData( Long regionId, String startDate,String endDate) throws IOException {
        return dsTtnService.isExistData(regionId,startDate,endDate);
    }

}
