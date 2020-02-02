package com.jh.manage.storage.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.manage.order.model.OrderParam;
import com.jh.manage.storage.entity.DataStorage;
import com.jh.manage.storage.model.StorageParam;
import com.jh.manage.storage.service.IDataStorageService;
import com.jh.util.DateUtil;
import com.jh.util.DownloadUtil;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 元数据管理
 * Created by lcw on 2018-03-04
 */
@RestController
@RequestMapping("/storage")
public class StorageController  extends BaseController {

    @Autowired
    private IDataStorageService dataStorageService;

    /**
     * 根据查询条件检索元数据
     * @param request
     * @param storageParam
     * @return
     */
    @PostMapping("/queryDatas")
    @ApiImplicitParam(name = "storageParam",value = "元数据对象",required = true, dataType = "StorageParam")
    public ResultMessage queryDatas(HttpServletRequest request, @RequestBody StorageParam storageParam){

        ResultMessage result = dataStorageService.findDatas(storageParam);

        return result;
    }

    /**
     * 缩略图下载
     * @param storageId
     * @param response
     */
    @GetMapping("showThumbnail")
    @ApiOperation(value = "查看缩略图",notes = "查看缩略图")
    @ApiImplicitParam(name = "storageId",value = "缩略图地址",required = true,paramType = "query", dataType = "Integer")
    public void showThumbnail(@RequestParam Integer storageId, HttpServletResponse response){
        ResultMessage result = dataStorageService.findStorageById(storageId);
        System.out.println(result.isFlag());
        if(result.isFlag()){
            DataStorage storage = (DataStorage) result.getData();
            System.out.println(storage.getThumbnailUrl());
            DownloadUtil.getInstance().downloadByThumbnail(storage.getThumbnailUrl(), response);
        }
    }

    /**
     *统计不同卫星的元数据数量
     * @param storageParam
     * @return ResultMessage
     * @version <1> 2018-04-18 wl： Created.
     */
    @ApiOperation(value = "统计不同卫星的元数据数量",notes = "统计不同卫星的元数据数量")
    @ApiImplicitParam(name = "storageParam",value = "订单对象",required = true,dataType = "StorageParam")
    @PostMapping("queryDataStorageSateNum")
    public ResultMessage queryDataStorageSateNum(@RequestBody StorageParam storageParam ){
        return dataStorageService.queryDataStorageSateNum(storageParam);
    }

    /**
     * @description: 检索分页查询
     * @param request
     * @param storageParam
     * @return
     * @version <1> 2018-04-25 wl： Created.
     */
    @ApiOperation(value = "检索分页查询",notes = "分页查询元数据检索信息")
    @ApiImplicitParam(name = "storageParam",value = "检索查询参数",required = false,dataType = "StorageParam")
    @PostMapping("/findByPage")
    public PageInfo<DataStorage> findByPage(HttpServletRequest request, StorageParam storageParam){
        return dataStorageService.findByPage(storageParam);
    }

    /**
     *查询cookie中保存的数据详细信息
     * @param taskStorageIdList
     * @return ResultMessage
     * @version <1> 2018-04-27 wl： Created.
     */
    @ApiOperation(value = "查询cookie中保存的数据详细信息",notes = "查询cookie中保存的数据详细信息")
    @ApiImplicitParam(name = "taskStorageIdList",value = "订单对象",required = true,dataType = "List<Integer>")
    @PostMapping("queryDataStorageByCookie")
    public List<DataStorage> queryDataStorageByCookie(@RequestBody List<Integer> taskStorageIdList ){

        return dataStorageService.findStorageByIdList(taskStorageIdList);
    }

    /**
     * Description: 根据ids查询首页数据,将id查询成对象
     * @param ids 
     * @return 
     * @version <1> 2018/5/28 16:40 zhangshen: Created.
     */
    @PostMapping("/findStorageByIds")
    @ApiImplicitParam(name = "ids", required = false, dataType = "String")
    public ResultMessage findStorageByIds(@RequestBody String ids){
        ResultMessage result = dataStorageService.findStorageByIds(ids);
        return result;
    }


    @ApiOperation("原始文件下载")
    @ApiImplicitParam(name = "storageId",value = "原始文件id",required = true,paramType = "query", dataType = "Integer")
    @RequestMapping("/down")
    public void dateReprocessDown(@RequestParam Integer storageId, HttpServletResponse response){
        DataStorage dataStorage = dataStorageService.findDataStorageById(storageId);
        if(null != dataStorage){
            DownloadUtil.getInstance().downloadFile(dataStorage.getStorageUrl(), response);
        }
    }

    /**
     *统计不同卫星的元数据数量(当天和总计)
     * @param storageParam
     * @return ResultMessage
     * @version <1> 2018-06-12 wl： Created.
     */
    @ApiOperation(value = "统计不同卫星的元数据数量(当天和总计)",notes = "统计不同卫星的元数据数量(当天和总计)")
    @ApiImplicitParam(name = "storageParam",value = "订单对象",required = true,dataType = "StorageParam")
    @PostMapping("queryDataStorageSateSum")
    public ResultMessage queryDataStorageSateSum(@RequestBody StorageParam storageParam ){
        return dataStorageService.queryDataStorageSateSum(storageParam);
    }

    /**
     *统计不同卫星的元数据数量(当天和总计)
     * @param storageParam
     * @return ResultMessage
     * @version <1> 2018-06-12 wl： Created.
     */
    @ApiOperation(value = "统计原始影像数据总和，数据订单下载总和，归档数据总和",notes = "统计原始影像数据总和，数据订单下载总和，归档数据总和")
    @ApiImplicitParam(name = "storageParam",value = "订单对象",required = true,dataType = "StorageParam")
    @PostMapping("queryDataSum")
    public ResultMessage queryDataSum(@RequestBody StorageParam storageParam ){
        return dataStorageService.queryDataSum(storageParam);
    }



    @PostMapping("queryGFDatas")
    public ResultMessage queryGFDatas(@RequestBody StorageParam storageParam){
        System.out.println(storageParam.getSatellites());


        return dataStorageService.queryGFDatas(storageParam);
    }


    /**
     * 统计未入库的高分影像景序列号
     * @param storageParam
     * @return
     */
    @PostMapping("queryNoSceneDatas")
    public ResultMessage queryNoSceneDatas(@RequestBody StorageParam storageParam){

        return dataStorageService.querySceneDatas(storageParam);
    }


    /**
     * 周报数据统计：按卫星统计总量，所选时间段的总量
     * 1.自然周数据统计
     * 2.指定时间段数据统计（默认时间段为当月1日~当天）
     *
     * @param storageParam
     * @return
     */
    @PostMapping("getWeeklyReport")
    public ResultMessage getWeeklyReport(@RequestBody  StorageParam storageParam){

        //本周
        Date  date = new Date();  //当天
        //获取当前星期几
        int weekDay = DateUtil.weekOfDate(date); //一周的第一天
//      周一
        Date firstDate = DateUtil.subDay(date , weekDay -1);

        //入库时间起止
        storageParam.setBeginTime(DateUtil.dateToString(firstDate,"yyyy-MM-dd"));
        storageParam.setEndTime(DateUtil.dateToString(date,"yyyy-MM-dd"));

        //统计数据
        ResultMessage storageResult = dataStorageService.queryDatasForReport(storageParam);


        return storageResult;
    }


    /**
     * 按时间段统计数据
     * @param storageParam
     * @return
     */
    @PostMapping("getDateReport")
    public ResultMessage getDateReport(@RequestBody StorageParam storageParam){

        if(StringUtils.isBlank(storageParam.getBeginTime())){


            int year = DateUtil.getYear();
            int month = DateUtil.getMonth();
            //本月第一天
            String firstDayInMonth = year + "-" + (month  < 10 ? "0" + month : month )  + "-01";
            storageParam.setBeginTime(firstDayInMonth);
        }
        if(StringUtils.isBlank(storageParam.getEndTime())){

            //本周
            Date  date = new Date();  //当天
            String curDate = DateUtil.dateToString(date , "yyyy-MM-dd");

            storageParam.setEndTime(curDate);
        }



        ResultMessage result = dataStorageService.queryDatasForReport(storageParam);

        return result;
    }






    public static void main(String[] args){

        //本周
        Date  date = new Date();  //当天

        System.out.println(date);
        //获取当前星期几
        int weekDay = DateUtil.weekOfDate(date); //一周的第一天
        System.out.println(weekDay);


//      周一
        Date firstDate = DateUtil.subDay(date , weekDay );

        System.out.println(firstDate);


        Date onDate = DateUtil.subDay(firstDate, 6);
        System.out.println(onDate);





    }


}
