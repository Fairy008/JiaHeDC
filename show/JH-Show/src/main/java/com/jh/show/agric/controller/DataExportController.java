package com.jh.show.agric.controller;


import com.github.pagehelper.PageInfo;
import com.jh.biz.controller.BaseController;
import com.jh.biz.feign.*;
import com.jh.show.agric.utils.DataExportUtils;
import com.jh.show.agric.utils.ExcelUtil;
import com.jh.util.AccountTokenUtil;
import com.jh.util.CacheUtil;
import com.jh.util.RedisUtil;
import com.jh.util.StringUtil;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jh.util.CacheUtil.CACHE_TRANS_NAME;
//import static com.sun.tools.corba.se.idl.constExpr.Expression.one;

@Api(value = "导出数据",description = "导出查询数据接口")
@RestController
@RequestMapping("/dataExport")
public class DataExportController extends BaseController{

    @Autowired
    private IDistributionService distributionService;//分布
    @Autowired
    private IYieldService yieldService;//估产
    @Autowired
    private IGrowthService growthService;//长势
    @Autowired
    public HttpServletResponse response;
    @Autowired
    private IDsTrmmService trmmService;//降雨
    @Autowired
    private IDsTService tService;;//地温
    @Autowired
    private IDsTempService tempService;//气温
    @Autowired
    IPersonService personService;
    @Autowired
    public HttpServletRequest request;

    private static final String YEAR_NAME ="三年";
    private static final String TOTAL_NAME ="统计数据";


    /**
     * 导出excel
     *
     * @return
     * @version <1> 2018-09-26 lijie： Created.
     */
    @ApiOperation(value = "导出数据", notes = "导出数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regionId",value = "区域id",required = true,dataType = "Long",paramType = "query",defaultValue = "2300000000"),
            @ApiImplicitParam(name = "cropId",value = "作物id",required = true,dataType = "int",paramType = "query",defaultValue = "504"),
            @ApiImplicitParam(name = "dataTime",value = "数据日期",required = true,dataType = "String",paramType = "query",defaultValue = "2016-08-30"),
            @ApiImplicitParam(name = "resolution",value = "数据精度/分辨率",required = true,dataType = "int",paramType = "query",defaultValue = "4006")
    })
    @GetMapping("export")
    public ResultMessage export(@RequestParam Integer dsId,
                                @RequestParam String regionId,
                                @RequestParam Integer cropId,
                                @RequestParam String dataTime,
                                @RequestParam Integer resolution,
                                @RequestParam String startDate,
                                @RequestParam String endDate) throws Exception {
        try {
            String accountToken = AccountTokenUtil.getToken(request);
            Integer accountId= Integer.parseInt(this.getCurrentAccountId(request).toString());
            ResultMessage result = ResultMessage.fail();
            if(dsId == 1801){
                //分布
                result = distributionService.queryDistributionForThree(Long.parseLong(regionId),cropId,dataTime,resolution);
            }else if(dsId == 1802){
                //估产
                result = yieldService.queryYieldForThree(Long.parseLong(regionId),cropId,dataTime,resolution);
            }else if(dsId == 1803){
                //长势
                result = growthService.queryGrowthArea(Long.parseLong(regionId),cropId,dataTime,resolution);
            }else if(dsId == 1804){
                //地温
                result = tService.queryTForMon(Long.parseLong(regionId),startDate,endDate,resolution);
            }else if(dsId == 1805){
                //降雨
                result = trmmService.queryTrmmForMon(Long.parseLong(regionId),startDate,endDate,resolution);
            }else if(dsId == 1807){
                //气温
                result = tempService.queryTempForMon(Long.parseLong(regionId),startDate,endDate);
            }


            if (!result.isFlag()) {
                return result;
            }
            Map<String, Object> allMap = null;
            List allList = null;
            List productList = new ArrayList();
            //有权限的日期
            List dateList = new ArrayList<String>();
            //无权限的日期
            List nodateList = new ArrayList<String>();
            if(dsId == 1801 || dsId == 1802 ){
                allMap = (Map<String, Object>) result.getData();
            }else{
                allList = (List)result.getData();
                //根据权限重新组装list
                for(int i=0;i<allList.size();i++){
                    Map<String,Object> map = (Map<String,Object>) (allList.get(i));
                    String date = map.get("date")+"";
                    if(StringUtils.isNotBlank(date)){
                        if(dateList.contains(date)) {
                            productList.add(map);
                            continue;
                        }
                        if(nodateList.contains(date)) {
                            continue;
                        }
                        ResultMessage message = personService.queryHasProductForFeign(accountId,dsId,Long.parseLong(regionId),cropId,resolution,date,date,accountToken);
                        if(message.isFlag()){
                            int num = (int)message.getData();
                            if(num > 0){
                                productList.add(map);
                                dateList.add(date);
                            }else{
                                nodateList.add(date);
                            }
                        }
                    }
                }
            }
            String regionName=RedisUtil.get(CacheUtil.CACHE_REGION_TYPE+CacheUtil.CACHE_TRANS_NAME+regionId);
            String cropName=RedisUtil.get(CacheUtil.CACHE_DICT_TYPE+CacheUtil.CACHE_TRANS_NAME+cropId);
            if(StringUtils.isBlank(cropName)){
                cropName ="" ;
            }
            String dsName=RedisUtil.get(CacheUtil.CACHE_DICT_TYPE+CacheUtil.CACHE_TRANS_NAME+dsId);
            String date =dataTime;
            String yearName = YEAR_NAME;
            if(dsId == 1804 || dsId == 1805|| dsId == 1807){
                date = startDate +"~" +endDate;
                yearName = "";
            }
            //文件名
            String fileName = date+ regionName + cropName + yearName + dsName +TOTAL_NAME+".xls";
            Integer currentYear = Integer.parseInt(dataTime.substring(0,4));//当年
            //标题
            String[] title = DataExportUtils.getTitle(dsId,currentYear);
            //内容
            String [][] values =DataExportUtils.getValues(dsId,allMap,productList,dataTime);

            HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(fileName.split("\\.")[0],title,values,null);
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            return ResultMessage.fail("导出文档失败：" + e);
        }
        return ResultMessage.success();
    }


    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



}
