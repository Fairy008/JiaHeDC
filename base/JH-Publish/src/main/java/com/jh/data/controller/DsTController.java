package com.jh.data.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.data.entity.DsT;
import com.jh.data.model.DsTParam;
import com.jh.data.model.TemTrmmTotalDataReturn;
import com.jh.data.model.TtnParam;
import com.jh.data.service.IDsTService;
import com.jh.data.utils.ExcelUtil;
import com.jh.util.RedisUtil;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * description:地温数据管理发布
 *
 * @version <1> 2018-06-07 wl: Created.
 */
@RestController
@RequestMapping("/dsT")
public class DsTController extends BaseController {

    @Autowired
    private IDsTService dsTService;

    @ApiOperation(value = "获取地温明细列表", notes = "获取地温明细列表")
    @ApiImplicitParam(name = "dsTParam",value = "地温明细查询参数",required = false, dataType = "DsTParam")
    @PostMapping("/findByPage")
    public PageInfo<DsT> findByPage(DsTParam dsTParam, HttpServletRequest request){
        PageInfo<DsT> pages = dsTService.findByPage(dsTParam);
        return pages;
    }

    /**
     * 地温明细修改
     * @param request
     * @param dsTParam
     * @return
     * @version <1> 2018-06-07 wl： Created.
     */
    @ApiOperation(value = "地温明细修改",notes = "修改地温明细")
    @ApiImplicitParam(name = "dsTParam",value = "地温明细实体",required = true,dataType = "DsTParam")
    @PostMapping("/edit")
    public ResultMessage edit(HttpServletRequest request, @RequestBody DsTParam dsTParam){
            dsTParam.setModifierName(getCurrentNickName());
            dsTParam.setModifier(getCurrentAccountId());
        return dsTService.updateDsT(dsTParam);
    }


    /**
     * @description: 根据ID查询地温详情
     * @param request
     * @param id
     * @return
     * @version <1> 2018-06-07 wl： Created.
     */
    @ApiOperation(value = "地温明细查询",notes = "按地温明细id查询")
    @ApiImplicitParam(name = "id",value = "地温明细主键",required = true,paramType = "query", dataType = "Integer")
    @PostMapping("/findById")
    public ResultMessage findById(HttpServletRequest request, @RequestParam Integer id){
        return dsTService.findById(id);
    }

    /**
     *删除数据集明细
     * @param id 订单Id
     * @return ResultMessage
     * @version <1> 2018-06-07 wl： Created.
     */
    @ApiOperation(value = "删除数据集明细",notes = "删除数据集明细")
    @ApiImplicitParam(name = "id",value = "订单Id",required = true,dataType = "Integer")
    @PostMapping("delete")
    public ResultMessage delete(@RequestParam Integer id){
        DsTParam dsTParam=new DsTParam();
        dsTParam.setId(id);
        dsTParam.setDelFlag("0");
        return dsTService.updateDsT(dsTParam);
    }

    /**
     * 地温明细发布或撤销
     * @param request
     * @param dsTParam
     * @return
     * @version <1> 2018-06-07 wl： Created.
     */
    @ApiOperation(value = "地温明细发布或撤销",notes = "发布或撤销地温明细")
    @ApiImplicitParam(name = "dsAreaParam",value = "地温明细实体",required = true,dataType = "DsAreaParam")
    @PostMapping("/publish")
    public ResultMessage publish(HttpServletRequest request, @RequestBody DsTParam dsTParam){

        dsTParam.setPublisher(getCurrentAccountId());
        dsTParam.setPublisherName(getCurrentNickName());

        return dsTService.publish(dsTParam);
    }

    /**
     * 查询指定时间类上中下旬的雨量和地温的均值以及十年的均值
     * @param param
     * @return
     * @version <1> 2018-3-21 lxy:Created
     */
    @ApiOperation(value="查询指定时间内上中下旬的雨量总量以及十年的均值",notes="根据区域、日期段查询上中下旬的雨量总量以及十年的均值" )
    @PostMapping("/queryTForTenDaysAndHistory")
    public PageInfo<TemTrmmTotalDataReturn>  queryTForTenDaysAndHistory(TtnParam param){
        ResultMessage resultMessage = new ResultMessage();

        if(param != null){
            return dsTService.findTForTenDaysAndHistory(param);
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
    @ApiOperation(value="查询指定时间内上中下旬的雨量总量以及十年的均值",notes="根据区域、日期段查询上中下旬的雨量总量以及十年的均值" )
    @PostMapping("/queryTListForTenDaysAndHistory")
    public ResultMessage queryTListForTenDaysAndHistory(@RequestBody TtnParam param){
        ResultMessage resultMessage = ResultMessage.success();
        if(param != null){
            List<TemTrmmTotalDataReturn> listRetun =  dsTService.findTListForTenDaysAndHistory(param);
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
     * 查询指定时间类上中下旬的地温的均值以及十年的均值
     * @param regionId 区域
     * @param regionName 区域名
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     * @version <1> 2018-06-29 cxw:Created
     */
    @ApiOperation(value = "导出查询数据",notes = "")
    @GetMapping( "/exportTData")
    public ResultMessage exportTData(HttpServletRequest request, HttpServletResponse response, Long regionId, String regionName, String startDate, String endDate) throws IOException, ParseException {
//        return dsTService.exportTData(request,response,regionId,regionName,startDate,endDate);

        String[] title = {"区域","区域编号","区域级别","月旬序列","地温(℃)","历年地温均值(℃)","距平值","距平比率%"};


        if(regionId != null && startDate != null && endDate != null){
            String fileName = RedisUtil.get("CACHE_REGION_NAME_" + regionId) + "各地区地温数据一览表（"+ startDate.toString().substring(0,7) + "-" + endDate.toString().substring(0,7) +"）" + ".xls";

            System.out.println(fileName);

            TtnParam ttnParam = new TtnParam();
            ttnParam.setRegionId(regionId);
            ttnParam.setStartDate(LocalDate.parse(startDate));
            ttnParam.setEndDate(LocalDate.parse(endDate));

            ttnParam.setExportFlag(1);

            List<TemTrmmTotalDataReturn> list =  dsTService.findTListForTenDaysAndHistory(ttnParam);

            String [][] content=new String[list.size()][title.length];
            for(int i=0;i<list.size();i++){
                TemTrmmTotalDataReturn temTrmm = list.get(i);
                content[i][0] = temTrmm.getRegionId() == 0 ? "" : String.valueOf(temTrmm.getRegionId());
                content[i][1] = temTrmm.getRegionName() == null ? "" : temTrmm.getRegionName().toString();
                content[i][2] = temTrmm.getLevel() +"";
                content[i][3] = temTrmm.getDateFlag();
                content[i][4] = temTrmm.getTemp() + "";

                content[i][5] = temTrmm.getTempYearsAvg() + "";
                content[i][6] = temTrmm.getDistance() + "";
                content[i][7] = temTrmm.getPercent();
            }

            //创建HSSFWorkbook
            HSSFWorkbook wb= ExcelUtil.getHHSWorkbook("地温",title,content,null);

            try{
                ExcelUtil.setResponseHeader(response,fileName);
                OutputStream os=response.getOutputStream();
                wb.write(os);
                os.flush();
                os.close();
                return ResultMessage.success("数据导出成功");
            }catch (Exception ec){
                ec.printStackTrace();

                return ResultMessage.fail("数据导出失败");
            }

        }

        return ResultMessage.fail();

    }


    /**
     * 查询指定时间类上中下旬的地温的均值以及十年的均值
     * @param regionId 区域
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     * @version <1> 2018-06-29 cxw:Created
     */
    @ApiOperation(value = "导出查询数据判断",notes = "")
    @GetMapping(value = "/isExistTData")
    public ResultMessage isExistTData( Long regionId, String startDate,String endDate) throws IOException {
        return dsTService.isExistTData(regionId,startDate,endDate);
    }
}
