package com.jh.data.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.data.entity.DsT;
import com.jh.data.mapping.IDsTMapper;
import com.jh.data.model.*;
import com.jh.data.service.IDsTService;
import com.jh.data.utils.ExcelUtil;
import com.jh.util.ArithUtil;
import com.jh.util.DateUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * description:
 *
 * @version <1> 2018-06-07 wl: Created.
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class DsTServiceImpl implements IDsTService{

    @Autowired
    private IDsTMapper dsTMapper;

    @Override
    public PageInfo<DsT> findByPage(DsTParam dsTParam) {
        PageHelper.startPage(dsTParam.getPage(), dsTParam.getRows());
        List<DsT> list = dsTMapper.findByPage(dsTParam);
        return new PageInfo<DsT>(list);
    }

    @Override
    public ResultMessage updateDsT(DsTParam dsTParam) {
        dsTMapper.updateDsT(dsTParam);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage findById(Integer id) {
        DsT dsT=dsTMapper.findById(id);
        return ResultMessage.success(dsT);
    }

    @Override
    public ResultMessage publish(DsTParam dsTParam) {
        int num = dsTMapper.publish(dsTParam);
        if(num > 0){
            return ResultMessage.success();
        }else{
            return ResultMessage.fail();
        }
    }

    /**
     * 查询指定时间内上中下旬的地温的均值以及历史的均值
     * @param param 区域编号，开始时间，结束时间
     * @return
     * @version <1> 2018-3-21 lxy:Created
     */
    @Override
    public  PageInfo<TemTrmmTotalDataReturn> findTForTenDaysAndHistory(TtnParam param) {
        //获得regionId 区域，用于重新组装一個 Param Map ,传给queryHistoryAvgForTrmmAndTempTotal(Map param)方法
        Long regionId = param.getRegionId();

        //获得时间段，每个月上中下旬的地温均值
        PageHelper.startPage(param.getPage(), param.getRows());
        List<TemTrmmTotalDataReturn> tenDaysTempList = dsTMapper.findTByTenDays(param);


        //遍历上中下旬地温均值  起名规则： tenTrmm 十年雨量总和和地温均值
        for(TemTrmmTotalDataReturn tenTempData : tenDaysTempList){

            /**
             * 查询历史地温均值，地温距平均值，
             */
            //组装一個 HashMap<String,String>的参数 , 用于queryHistoryAvgForTrmmAndTempTotal(Map param)方法
            //放置区域编号
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("regionId",tenTempData.getRegionId());
            //获得当前遍历到的上中下旬地温均值的月份。
            String endDate = tenTempData.getDate();
            //设置月份
            LocalDate localDate = LocalDate.parse(endDate+"-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int month = localDate.getMonth().getValue();
            paramMap.put("month",month);
            //设置年份参数
            int endDateYear = localDate.getYear();
            paramMap.put("endDate",endDateYear+"");

            //根据 区域编号，日期，月份，查询地温均值数据
            List<TenDaysTrmmDataReturn> tenYearTotalList = new ArrayList<TenDaysTrmmDataReturn>();
            tenYearTotalList =  dsTMapper.findHistoryAvgForTTotal(paramMap);
            TenDaysTrmmDataReturn tenYearTotal = new  TenDaysTrmmDataReturn();
            if(tenYearTotalList.size()>0){
                tenYearTotal = tenYearTotalList.get(0);
            }

            double tempFirstAvg = 0d;
            double tempSecondAvg = 0d;
            double tempThirdAvg = 0d;
            if(tenYearTotal!=null){
                if("1".equals(tenTempData.getDateFlag())){
                    tempFirstAvg = tenYearTotal.getTempFirstAvg();
                    tenTempData.setTempYearsAvg(tempFirstAvg);
                }else if("2".equals(tenTempData.getDateFlag())){
                    tempSecondAvg = tenYearTotal.getTempSecondAvg();
                    tenTempData.setTempYearsAvg(tempSecondAvg);
                }else if("3".equals(tenTempData.getDateFlag())) {
                    tempThirdAvg = tenYearTotal.getTempThirdAvg();
                    tenTempData.setTempYearsAvg(tempThirdAvg);
                }
            }


            /**
             * 距平均值
             */

            //上旬距平均值
            double distanceFirst = ArithUtil.sub(tenTempData.getTemp(),tenTempData.getTempYearsAvg());
            tenTempData.setDistance(distanceFirst);


            /**
             * 距平均值百分比
             */

            //距平均值百分比上旬
            double percentDistanceFirst = ArithUtil.div(tenTempData.getDistance(),tenTempData.getTempYearsAvg(),2);
            percentDistanceFirst = ArithUtil.mul(percentDistanceFirst,100d);
            tenTempData.setPercent(percentDistanceFirst+"%");
            //组装dataFlag的值
            String dateFlag=tenTempData.getDate();
            if("1".equals(tenTempData.getDateFlag())){
                dateFlag+="上旬";
            }else if("2".equals(tenTempData.getDateFlag())){
                dateFlag+="中旬";
            }else if("3".equals(tenTempData.getDateFlag())){
                dateFlag+="下旬";
            }
            tenTempData.setDateFlag(dateFlag);
        }

        return new PageInfo<TemTrmmTotalDataReturn>(tenDaysTempList);
    }

    /**
     * 查询指定时间内上中下旬的雨量和地温的均值以及历史的均值
     * @param param 区域编号，开始时间，结束时间
     * @return
     * @version <1> 2018-3-21 lxy:Created
     */
    @Override
    public  List<TemTrmmTotalDataReturn> findTListForTenDaysAndHistory(TtnParam param) {
        //获得regionId 区域，用于重新组装一個 Param Map ,传给queryHistoryAvgForTrmmAndTempTotal(Map param)方法
        Long regionId = param.getRegionId();



        //获得时间段，每个月上中下旬的地温均值
        if(param.getExportFlag() != 1){
            PageHelper.startPage(param.getPage(), param.getRows());
        }
        List<TemTrmmTotalDataReturn> tenDaysTempList = dsTMapper.findTByTenDays(param);

        //装载组装的数据容器，用于返回给前端
        /*List<TemTrmmTotalDataReturn> temTrmmTotalDataList = new ArrayList<TemTrmmTotalDataReturn>();*/

        //遍历上中下旬地温均值  起名规则： tenTrmm 十年雨量总和和地温均值
        for(TemTrmmTotalDataReturn tenTempData : tenDaysTempList){

           /* *//**
             * 上中下旬地温均值基础信息
             *//*
            //装载总数据Pojo (上旬数据) -- 起名规则 tem：地温  trmm 雨量
            TemTrmmTotalDataReturn temTempTotal1 = new TemTrmmTotalDataReturn();
            //设置日期标识
            temTempTotal1.setDateFlag(tenTempData.getDate()+"上旬");
            //设置区域编号
            temTempTotal1.setRegionId(tenTempData.getRegionId());
            //设置级别编号
            temTempTotal1.setLevel(tenTempData.getLevel());
            //设置区域名称
            temTempTotal1.setRegionName(tenTempData.getRegionName());
            //设置上旬地温平均值
            double tempFirst = 0d;
            tempFirst = tenTempData.getTempFirst();
            temTempTotal1.setTemp(tenTempData.getTempFirst());




            //装载总数据Pojo (中旬数据) -- 起名规则 tem：地温  trmm 雨量
            TemTrmmTotalDataReturn temTempTotal2 = new TemTrmmTotalDataReturn();
            //设置日期标识
            temTempTotal2.setDateFlag(tenTempData.getDate()+"中旬");
            //设置区域编号
            temTempTotal2.setRegionId(tenTempData.getRegionId());
            //设置级别编号
            temTempTotal2.setLevel(tenTempData.getLevel());
            //设置区域名称
            temTempTotal2.setRegionName(tenTempData.getRegionName());
            //设置中旬地温平均值
            double tempSecond = 0d;
            tempSecond = tenTempData.getTempSecond();
            temTempTotal2.setTemp(tenTempData.getTempSecond());


            //装载总数据Pojo (下旬数据)   -- 起名规则 tem：地温  trmm 雨量
            TemTrmmTotalDataReturn temTempTotal3 = new TemTrmmTotalDataReturn();
            //设置日期标识
            temTempTotal3.setDateFlag(tenTempData.getDate()+"下旬");
            //设置区域编号
            temTempTotal3.setRegionId(tenTempData.getRegionId());
            //设置级别编号
            temTempTotal3.setLevel(tenTempData.getLevel());
            //设置区域名称
            temTempTotal3.setRegionName(tenTempData.getRegionName());
            //设置下旬地温平均值
            double tempThird = 0d;
            tempThird = tenTempData.getTempThird();
            temTempTotal3.setTemp(tenTempData.getTempThird());*/


            /**
             * 查询历史地温均值，地温距平均值，
             */
            //组装一個 HashMap<String,String>的参数 , 用于queryHistoryAvgForTrmmAndTempTotal(Map param)方法
            //放置区域编号
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("regionId",tenTempData.getRegionId());
            //获得当前遍历到的上中下旬地温均值的月份。
            String endDate = tenTempData.getDate();
            //设置月份
            LocalDate localDate = LocalDate.parse(endDate+"-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int month = localDate.getMonth().getValue();
            paramMap.put("month",month);
            //设置年份参数
            int endDateYear = localDate.getYear();
            paramMap.put("endDate",endDateYear+"");

            //根据 区域编号，日期，月份，查询地温均值数据
            List<TenDaysTrmmDataReturn> tenYearTotalList = new ArrayList<TenDaysTrmmDataReturn>();
            tenYearTotalList =  dsTMapper.findHistoryAvgForTTotal(paramMap);
            TenDaysTrmmDataReturn tenYearTotal = new  TenDaysTrmmDataReturn();
            if(tenYearTotalList.size()>0){
                tenYearTotal = tenYearTotalList.get(0);
            }

            /**
             * 历史地温均值
             */
            //历史地温均值（上）
            /*double tempFirstAvg = 0d;
            if(tenYearTotal!=null)
                tempFirstAvg = tenYearTotal.getTempFirstAvg();
            temTempTotal1.setTempYearsAvg( tempFirstAvg);
            //历史地温均值（中）
            double tempSecondAvg = 0d;
            if(tenYearTotal!=null)
                tempSecondAvg = tenYearTotal.getTempSecondAvg();
            temTempTotal2.setTempYearsAvg(tempSecondAvg);
            //历史地温均值（下）
            double tempThirdAvg = 0;
            if(tenYearTotal!=null)
                tempThirdAvg = tenYearTotal.getTempThirdAvg();
            temTempTotal3.setTempYearsAvg(tempThirdAvg);*/


            double tempFirstAvg = 0d;
            double tempSecondAvg = 0d;
            double tempThirdAvg = 0d;
            if(tenYearTotal!=null){
                if("1".equals(tenTempData.getDateFlag())){
                    tempFirstAvg = tenYearTotal.getTempFirstAvg();
                    tenTempData.setTempYearsAvg(tempFirstAvg);
                }else if("2".equals(tenTempData.getDateFlag())){
                    tempSecondAvg = tenYearTotal.getTempSecondAvg();
                    tenTempData.setTempYearsAvg(tempSecondAvg);
                }else if("3".equals(tenTempData.getDateFlag())) {
                    tempThirdAvg = tenYearTotal.getTempThirdAvg();
                    tenTempData.setTempYearsAvg(tempThirdAvg);
                }
            }


            /**
             * 距平均值
             */

            //上旬距平均值
            double distanceFirst = ArithUtil.sub(tenTempData.getTemp(),tenTempData.getTempYearsAvg());
            tenTempData.setDistance(distanceFirst);
            /*//中旬距平均值
            double distanceSecond = ArithUtil.sub(tempSecond,tempSecondAvg);
            temTempTotal2.setDistance(distanceSecond);
            //下旬距平均值
            double distanceThird = ArithUtil.sub(tempThird,tempThirdAvg);
            temTempTotal3.setDistance(distanceThird);*/


            /**
             * 距平均值百分比
             */

            //距平均值百分比上旬
            double percentDistanceFirst = ArithUtil.div(tenTempData.getDistance(),tenTempData.getTempYearsAvg(),2);
            percentDistanceFirst = ArithUtil.mul(percentDistanceFirst,100d);
            tenTempData.setPercent(percentDistanceFirst+"%");
            /*//距平均值百分比上旬
            double percentDistanceSecond = ArithUtil.div(distanceSecond,tempSecondAvg,2);
            percentDistanceSecond = ArithUtil.mul(percentDistanceSecond,100d);
            temTempTotal2.setPercent(percentDistanceSecond+"%");
            //距平均值百分比上旬
            double percentDistanceThird = ArithUtil.div(distanceThird,tempThirdAvg,2);
            percentDistanceThird = ArithUtil.mul(percentDistanceThird,100d);
            temTempTotal3.setPercent(percentDistanceThird+"%");*/

            //最后装入容器
            /*temTrmmTotalDataList.add(temTempTotal1);//上旬数据
            temTrmmTotalDataList.add(temTempTotal2);//中旬旬数据
            temTrmmTotalDataList.add(temTempTotal3);//下旬数据*/
            //组装dataFlag的值
            String dateFlag=tenTempData.getDate();
            if("1".equals(tenTempData.getDateFlag())){
                dateFlag+="上旬";
            }else if("2".equals(tenTempData.getDateFlag())){
                dateFlag+="中旬";
            }else if("3".equals(tenTempData.getDateFlag())){
                dateFlag+="下旬";
            }
            tenTempData.setDateFlag(dateFlag);
        }
        return  tenDaysTempList;
    }


//    /** 导出查询数据
//     * @param regionId 区域ID
//     * @param startDate 开始日期
//     * @param endDate 结束日期
//     * @param regionName 区域名
//     * @return
//     * @version <1> 2018-06-28 cxw:Created
//     */
//    @Override
//    public ResultMessage exportTData(HttpServletRequest request, HttpServletResponse response, Long regionId, String regionName, String startDate, String endDate) throws IOException, ParseException {
//        ResultMessage ju = ResultMessage.success();
//        if(StringUtils.isNotBlank(regionName)&& StringUtils.isNotBlank(startDate)&& StringUtils.isNotBlank(endDate)&&regionId!=null)
//        {
//            TtnParam param = new  TtnParam();
//            param.setRegionId(regionId);
//            param.setStartDate(DateUtil.strToLocalDate(startDate));
//            param.setEndDate(DateUtil.strToLocalDate(endDate));
//
//            //调用导出excel方法
//            if (null != request || null != response) {
//                List<TemTrmmTotalDataReturn>  list= findTListForTenDaysAndHistory(param);
//                if(list.size()>0) {
//                    ByteArrayOutputStream out = new ByteArrayOutputStream();
//                    ExcelUtil.exportExcel(out, listObjectToListMap(list), 2l,"地温","历年地温均值");
//                    String fileName = regionName+"("+DateUtil.yyyymmddToYYYYmm(startDate) +"至"+DateUtil.yyyymmddToYYYYmm(endDate)+ ")度" + "气象对比" ;
//                    if (fileName == null || "".equals(fileName)) {
//                        fileName = UUID.randomUUID().toString();
//                    }
//
//                    byte[] content = out.toByteArray();
//                    InputStream is = new ByteArrayInputStream(content);
//                    // 设置response参数，可以打开下载页面
//                    response.reset();
//                    //office 2007 excel文档
//                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//                    response.setContentType("application/octet-stream;charset=utf-8");
//                    response.setHeader("Content-Disposition", "attachment;filename="
//                            + new String(fileName.getBytes(),"iso-8859-1") + ".xls");
//                    response.setContentLength(content.length);
//                    ServletOutputStream outputStream = response.getOutputStream();
//                    BufferedInputStream bis = new BufferedInputStream(is);
//                    BufferedOutputStream bos = new BufferedOutputStream(outputStream);
//                    byte[] buff = new byte[8192];
//                    int bytesRead;
//                    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
//                        bos.write(buff, 0, bytesRead);
//                    }
//                    bis.close();
//                    bos.close();
//                    outputStream.flush();
//                    outputStream.close();
//                    ju.setMsg("导出数据成功");
//                }
//                else{
//                    ju = ResultMessage.fail();
//                    ju.setMsg("导出数据失败");
//                }
//            }
//            else {
//                ju = ResultMessage.fail();
//                ju.setMsg("导出数据失败");
//            }
//
//        }
//
//        else
//        {
//            ju = ResultMessage.fail();
//            ju.setMsg("参数错误");
//        }
//        return  ju;
//    }


    /** 导出查询数据判断
     * @param list 对象集合
     * @return List<Map<String,Object>> 返回map类型list集合
     * @version <1> 2018-06-28 cxw:Created
     */
    private  List<Map<String,Object>> listObjectToListMap( List<TemTrmmTotalDataReturn> list)
    {
        List<Map<String,Object>> lm = new ArrayList<Map<String,Object>>();
        for(TemTrmmTotalDataReturn temp:list){
            Map map = new HashMap();
            map.put("regionName",temp.getRegionName());
            map.put("regionId",temp.getRegionId());
            map.put("level",temp.getLevel());
            map.put("dateFlag",temp.getDateFlag());
            map.put("distance",temp.getDistance());
            map.put("percent",temp.getPercent());
            map.put("temp",temp.getTemp());
            map.put("tempYearsAvg",temp.getTempYearsAvg());
            lm.add(map);
        }
        return lm;
    }

    /** 导出查询数据判断
     * @param regionId 区域ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     * @version <1> 2018-06-28 cxw:Created
     */
    @Override
    public  ResultMessage isExistTData(Long regionId,String startDate,String endDate) {
        ResultMessage ju = ResultMessage.success();
        if(StringUtils.isNotBlank(startDate)&& StringUtils.isNotBlank(endDate)&&regionId!=null)
        {
            TtnParam param = new  TtnParam();
            param.setRegionId(regionId);
            param.setStartDate(DateUtil.strToLocalDate(startDate));
            param.setEndDate(DateUtil.strToLocalDate(endDate));

            List<TemTrmmTotalDataReturn>  list= findTListForTenDaysAndHistory(param);
            if(list.size()>0) {
                //ju.setMsg("导出数据成功");

            }
            else {
                ju = ResultMessage.fail();
                ju.setMsg("未检索到可导出的数据");
            }
        }

        else
        {
            ju = ResultMessage.fail();
            ju.setMsg("参数错误");
        }
        return  ju;
    }

    /**
     * 根据区域、作物、精度 查询数据时间列表
     * @param reportCreateParam
     * @return
     * @version<1> 2018-07-18 zhangshen: Created.
     */
    @Override
    public List<DsT> queryDateTimeList(ReportCreateParam reportCreateParam) {
        return dsTMapper.queryDateTimeList(reportCreateParam);
    }
}
