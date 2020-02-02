package com.jh.data.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.data.entity.DsTrmm;
import com.jh.data.mapping.IDsTrmmMapper;
import com.jh.data.model.*;
import com.jh.data.service.IDsTrmmService;
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
 * @version <1> 2018-06-08 wl: Created.
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class DsTrmmServiceImpl implements IDsTrmmService{

    @Autowired
    private IDsTrmmMapper dsTrmmMapper;


    @Override
    public PageInfo<DsTrmm> findByPage(DsTrmmParam dsTrmmParam) {
        PageHelper.startPage(dsTrmmParam.getPage(), dsTrmmParam.getRows());
        List<DsTrmm> list = dsTrmmMapper.findByPage(dsTrmmParam);
        return new PageInfo<DsTrmm>(list);
    }

    @Override
    public ResultMessage updateDsTrmm(DsTrmmParam dsTrmmParam) {
        dsTrmmMapper.updateDsTrmm(dsTrmmParam);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage findById(Integer id) {
        DsTrmm dsTrmm=dsTrmmMapper.findById(id);
        return ResultMessage.success(dsTrmm);
    }

    @Override
    public ResultMessage publish(DsTrmmParam dsTrmmParam) {
        int num = dsTrmmMapper.publish(dsTrmmParam);
        if(num > 0){
            return ResultMessage.success();
        }else{
            return ResultMessage.fail();
        }
    }

    /**
     * 查询指定时间内上中下旬的雨量以及历史的均值
     * @param param 区域编号，开始时间，结束时间
     * @return
     * @version <1> 2018-3-21 lxy:Created
     */
    @Override
    public  PageInfo<TemTrmmTotalDataReturn> findTrmmForTenDaysAndHistory(TtnParam param) {
        //获得regionId 区域，用于重新组装一個 Param Map ,传给queryHistoryAvgForTrmmAndTempTotal(Map param)方法
        Long regionId = param.getRegionId();

        //获得时间段，每个月上中下旬的雨量总和
        List<TemTrmmTotalDataReturn> tenDaysTrmmList = new ArrayList<TemTrmmTotalDataReturn>();
        PageHelper.startPage(param.getPage(), param.getRows());
        tenDaysTrmmList =  dsTrmmMapper.findTrmmByTenDays(param);

        //装载组装的数据容器，用于返回给前端
        List<TemTrmmTotalDataReturn> temTrmmTotalDataList = new ArrayList<TemTrmmTotalDataReturn>();

        //遍历上中下旬雨量总和、地温均值  起名规则： tenTrmm 十年雨量总和和地温均值
        for(TemTrmmTotalDataReturn tenTrmmData : tenDaysTrmmList){

            /**
             * 上中下旬雨量均值，地温均值基础信息
             */
            /*//装载总数据Pojo (上旬数据) -- 起名规则 tem：地温  trmm 雨量
            TemTrmmTotalDataReturn temTrmmTotal1 = new TemTrmmTotalDataReturn();
            //设置日期标识
            temTrmmTotal1.setDateFlag(tenTrmmData.getDate()+"上旬");
            //设置区域编号
            temTrmmTotal1.setRegionId(tenTrmmData.getRegionId());
            //设置级别编号
            temTrmmTotal1.setLevel(tenTrmmData.getLevel());
            //设置区域名称
            temTrmmTotal1.setRegionName(tenTrmmData.getRegionName());
            //设置上旬雨量总和
            double waterFirst = 0d;
            waterFirst =    tenTrmmData.getWaterFirst();
            temTrmmTotal1.setTrmm(tenTrmmData.getWaterFirst());


            //装载总数据Pojo (中旬数据) -- 起名规则 tem：地温  trmm 雨量
            TemTrmmTotalDataReturn temTrmmTotal2 = new TemTrmmTotalDataReturn();
            //设置日期标识
            temTrmmTotal2.setDateFlag(tenTrmmData.getDate()+"中旬");
            //设置区域编号
            temTrmmTotal2.setRegionId(tenTrmmData.getRegionId());
            //设置级别编号
            temTrmmTotal2.setLevel(tenTrmmData.getLevel());
            //设置区域名称
            temTrmmTotal2.setRegionName(tenTrmmData.getRegionName());
            //设置中旬雨量总和
            double waterSecond = 0d;
            waterSecond =  tenTrmmData.getWaterSecond();
            temTrmmTotal2.setTrmm(tenTrmmData.getWaterSecond());


            //装载总数据Pojo (下旬数据)   -- 起名规则 tem：地温  trmm 雨量
            TemTrmmTotalDataReturn temTrmmTotal3 = new TemTrmmTotalDataReturn();
            //设置日期标识
            temTrmmTotal3.setDateFlag(tenTrmmData.getDate()+"下旬");
            //设置区域编号
            temTrmmTotal3.setRegionId(tenTrmmData.getRegionId());
            //设置级别编号
            temTrmmTotal3.setLevel(tenTrmmData.getLevel());
            //设置区域名称
            temTrmmTotal3.setRegionName(tenTrmmData.getRegionName());
            //设置下旬雨量总和
            double waterThird = 0d;
            waterThird = tenTrmmData.getWaterThird();
            temTrmmTotal3.setTrmm(tenTrmmData.getWaterThird());*/
            /**
             * 查询历史雨量均值，雨量距平均值，雨量距平均值百分
             */
            //组装一個 HashMap<String,String>的参数 , 用于queryHistoryAvgForTrmmAndTempTotal(Map param)方法
            //放置区域编号
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("regionId",tenTrmmData.getRegionId());
            //获得当前遍历到的上中下旬雨量总和的月份。
            String endDate = tenTrmmData.getDate();
            //设置月份
            LocalDate localDate = LocalDate.parse(endDate+"-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int month = localDate.getMonth().getValue();
            paramMap.put("month",month);
            //设置年份参数
            int endDateYear = localDate.getYear();
            paramMap.put("endDate",endDateYear+"");

            //根据 区域编号，日期，月份，查询历史 雨量均值数据
            List<TenDaysTrmmDataReturn> tenYearTotalList = new ArrayList<TenDaysTrmmDataReturn>();
            tenYearTotalList =  dsTrmmMapper.findHistoryAvgForTrmmTotal(paramMap);
            TenDaysTrmmDataReturn tenYearTotal = new TenDaysTrmmDataReturn();
            if(tenYearTotalList.size()>0) {
                tenYearTotal = tenYearTotalList.get(0);
            }
        /*  Double trmmAvgOne =  0d,trmmAvgTwo = 0d,trmmAvgThree = 0d;
            int tNumOne=0,tNumTwo=0,tNumThree=0;
            for(int i = 0;i<tenYearTotalList.size();i++){
                    trmmAvgOne = trmmAvgOne+tenYearTotalList.get(i).getWaterFirstAvg();
                    if(trmmAvgOne!=null) {
                        tNumOne = tNumOne + 1;
                    }

                    trmmAvgTwo = trmmAvgTwo+tenYearTotalList.get(i).getWaterSecondAvg();
                     if(trmmAvgTwo!=null) {
                         tNumTwo = tNumTwo + 1;
                     }

                    trmmAvgThree =trmmAvgThree+ tenYearTotalList.get(i).getWaterThirdAvg();
                    if(trmmAvgThree!=null) {
                        tNumThree = tNumThree + 1;
                    }
            }
            Double waterFirstAvg = 0d;
            Double waterSecondAvg = 0d;
            Double waterThirdAvg = 0d;
            if(tNumOne!=0){
                waterFirstAvg = Arith.round(trmmAvgOne/tNumOne,2);
            }
            if(tNumTwo!=0){
                waterSecondAvg = Arith.round(trmmAvgTwo/tNumTwo,2);
            }
            if(tNumThree!=0){
                waterThirdAvg = Arith.round(trmmAvgThree/tNumThree,2);
            }*/
            /**
             * 历史雨量均值
             */
            //历史雨量均值（上）
            /*double waterFirstAvg = 0d;
            if(tenYearTotal!=null)
                waterFirstAvg = tenYearTotal.getWaterFirstAvg();
            temTrmmTotal1.setTrmmYeasAvg(waterFirstAvg);
            //历史雨量均值（中）
            double waterSecondAvg = 0d;
            if(tenYearTotal!=null)
                waterSecondAvg = tenYearTotal.getWaterSecondAvg();
            temTrmmTotal2.setTrmmYeasAvg(waterSecondAvg);
            //历史雨量均值（下）
            double waterThirdAvg = 0d;
            if(tenYearTotal!=null)
                waterThirdAvg = tenYearTotal.getWaterThirdAvg();
            temTrmmTotal3.setTrmmYeasAvg(waterThirdAvg);*/
            double waterFirstAvg = 0d;
            double waterSecondAvg = 0d;
            double waterThirdAvg = 0d;
            if(tenYearTotal!=null){
                if("1".equals(tenTrmmData.getDateFlag())){
                    waterFirstAvg = tenYearTotal.getWaterFirstAvg();
                    tenTrmmData.setTrmmYeasAvg(waterFirstAvg);
                }else if("2".equals(tenTrmmData.getDateFlag())){
                    waterSecondAvg = tenYearTotal.getWaterSecondAvg();
                    tenTrmmData.setTrmmYeasAvg(waterSecondAvg);
                }else if("3".equals(tenTrmmData.getDateFlag())) {
                    waterThirdAvg = tenYearTotal.getWaterThirdAvg();
                    tenTrmmData.setTrmmYeasAvg(waterThirdAvg);
                }
            }

            /**
             * 距平均值
             */

            //上旬距平均值
            double distanceFirst = ArithUtil.sub(tenTrmmData.getTrmm(),tenTrmmData.getTrmmYeasAvg());
            tenTrmmData.setDistance(distanceFirst);
            //中旬距平均值
            //double distanceSecond = ArithUtil.sub(waterSecond,waterSecondAvg);
            //tenTrmmData.setDistance(distanceSecond);
            //下旬距平均值
            //double distanceThird = ArithUtil.sub(waterThird,waterThirdAvg);
            //tenTrmmData.setDistance(distanceThird);


            /**
             * 距平均值百分比
             */

            //距平均值百分比上旬
            double percentDistanceFirst = ArithUtil.div(tenTrmmData.getDistance(),tenTrmmData.getTrmmYeasAvg(),2);
            percentDistanceFirst = ArithUtil.mul(percentDistanceFirst,100d);
            tenTrmmData.setPercent(percentDistanceFirst+"%");
            //距平均值百分比上旬
            //double percentDistanceSecond = ArithUtil.div(distanceSecond,waterSecondAvg,2);
            //percentDistanceSecond = ArithUtil.mul(percentDistanceSecond,100d);
            //tenTrmmData.setPercent(percentDistanceSecond+"%");
            //距平均值百分比上旬
            //double percentDistanceThird = ArithUtil.div(distanceThird,waterThirdAvg,2);
            //percentDistanceThird = ArithUtil.mul(percentDistanceThird,100d);
            //tenTrmmData.setPercent(percentDistanceThird+"%");

            //最后装入容器
            //temTrmmTotalDataList.add(temTrmmTotal1);//上旬数据
            //temTrmmTotalDataList.add(temTrmmTotal2);//中旬旬数据
           // temTrmmTotalDataList.add(temTrmmTotal3);//下旬数据
            //组装dataFlag的值
            String dateFlag=tenTrmmData.getDate();
            if("1".equals(tenTrmmData.getDateFlag())){
                dateFlag+="上旬";
            }else if("2".equals(tenTrmmData.getDateFlag())){
                dateFlag+="中旬";
            }else if("3".equals(tenTrmmData.getDateFlag())){
                dateFlag+="下旬";
            }
            tenTrmmData.setDateFlag(dateFlag);

        }
        return new PageInfo<TemTrmmTotalDataReturn>(tenDaysTrmmList);
    }

    /**
     * 查询指定时间内上中下旬的雨量和地温的均值以及历史的均值
     * @param param 区域编号，开始时间，结束时间
     * @return
     * @version <1> 2018-3-21 lxy:Created
     */
    @Override
    public  List<TemTrmmTotalDataReturn> findTrmmListForTenDaysAndHistory(TtnParam param) {
        //获得regionId 区域，用于重新组装一個 Param Map ,传给queryHistoryAvgForTrmmAndTempTotal(Map param)方法
        Long regionId = param.getRegionId();

        //获得时间段，每个月上中下旬的雨量总和
        List<TemTrmmTotalDataReturn> tenDaysTrmmList = new ArrayList<TemTrmmTotalDataReturn>();
        tenDaysTrmmList =  dsTrmmMapper.findTrmmByTenDays(param);

        //装载组装的数据容器，用于返回给前端
        List<TemTrmmTotalDataReturn> temTrmmTotalDataList = new ArrayList<TemTrmmTotalDataReturn>();

        //遍历上中下旬雨量总和、地温均值  起名规则： tenTrmm 十年雨量总和和地温均值
        for(TemTrmmTotalDataReturn tenTrmmData : tenDaysTrmmList){

            /**
             * 上中下旬雨量均值，地温均值基础信息
             */
            /*//装载总数据Pojo (上旬数据) -- 起名规则 tem：地温  trmm 雨量
            TemTrmmTotalDataReturn temTrmmTotal1 = new TemTrmmTotalDataReturn();
            //设置日期标识
            temTrmmTotal1.setDateFlag(tenTrmmData.getDate()+"上旬");
            //设置区域编号
            temTrmmTotal1.setRegionId(tenTrmmData.getRegionId());
            //设置级别编号
            temTrmmTotal1.setLevel(tenTrmmData.getLevel());
            //设置区域名称
            temTrmmTotal1.setRegionName(tenTrmmData.getRegionName());
            //设置上旬雨量总和
            double waterFirst = 0d;
            waterFirst =    tenTrmmData.getWaterFirst();
            temTrmmTotal1.setTrmm(tenTrmmData.getWaterFirst());


            //装载总数据Pojo (中旬数据) -- 起名规则 tem：地温  trmm 雨量
            TemTrmmTotalDataReturn temTrmmTotal2 = new TemTrmmTotalDataReturn();
            //设置日期标识
            temTrmmTotal2.setDateFlag(tenTrmmData.getDate()+"中旬");
            //设置区域编号
            temTrmmTotal2.setRegionId(tenTrmmData.getRegionId());
            //设置级别编号
            temTrmmTotal2.setLevel(tenTrmmData.getLevel());
            //设置区域名称
            temTrmmTotal2.setRegionName(tenTrmmData.getRegionName());
            //设置中旬雨量总和
            double waterSecond = 0d;
            waterSecond =  tenTrmmData.getWaterSecond();
            temTrmmTotal2.setTrmm(tenTrmmData.getWaterSecond());


            //装载总数据Pojo (下旬数据)   -- 起名规则 tem：地温  trmm 雨量
            TemTrmmTotalDataReturn temTrmmTotal3 = new TemTrmmTotalDataReturn();
            //设置日期标识
            temTrmmTotal3.setDateFlag(tenTrmmData.getDate()+"下旬");
            //设置区域编号
            temTrmmTotal3.setRegionId(tenTrmmData.getRegionId());
            //设置级别编号
            temTrmmTotal3.setLevel(tenTrmmData.getLevel());
            //设置区域名称
            temTrmmTotal3.setRegionName(tenTrmmData.getRegionName());
            //设置下旬雨量总和
            double waterThird = 0d;
            waterThird = tenTrmmData.getWaterThird();
            temTrmmTotal3.setTrmm(tenTrmmData.getWaterThird());*/
            /**
             * 查询历史雨量均值，雨量距平均值，雨量距平均值百分
             */
            //组装一個 HashMap<String,String>的参数 , 用于queryHistoryAvgForTrmmAndTempTotal(Map param)方法
            //放置区域编号
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("regionId",tenTrmmData.getRegionId());
            //获得当前遍历到的上中下旬雨量总和的月份。
            String endDate = tenTrmmData.getDate();
            //设置月份
            LocalDate localDate = LocalDate.parse(endDate+"-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int month = localDate.getMonth().getValue();
            paramMap.put("month",month);
            //设置年份参数
            int endDateYear = localDate.getYear();
            paramMap.put("endDate",endDateYear+"");

            //根据 区域编号，日期，月份，查询历史 雨量均值数据
            List<TenDaysTrmmDataReturn> tenYearTotalList = new ArrayList<TenDaysTrmmDataReturn>();
            tenYearTotalList =  dsTrmmMapper.findHistoryAvgForTrmmTotal(paramMap);
            TenDaysTrmmDataReturn tenYearTotal = new TenDaysTrmmDataReturn();
            if(tenYearTotalList.size()>0) {
                tenYearTotal = tenYearTotalList.get(0);
            }
        /*  Double trmmAvgOne =  0d,trmmAvgTwo = 0d,trmmAvgThree = 0d;
            int tNumOne=0,tNumTwo=0,tNumThree=0;
            for(int i = 0;i<tenYearTotalList.size();i++){
                    trmmAvgOne = trmmAvgOne+tenYearTotalList.get(i).getWaterFirstAvg();
                    if(trmmAvgOne!=null) {
                        tNumOne = tNumOne + 1;
                    }

                    trmmAvgTwo = trmmAvgTwo+tenYearTotalList.get(i).getWaterSecondAvg();
                     if(trmmAvgTwo!=null) {
                         tNumTwo = tNumTwo + 1;
                     }

                    trmmAvgThree =trmmAvgThree+ tenYearTotalList.get(i).getWaterThirdAvg();
                    if(trmmAvgThree!=null) {
                        tNumThree = tNumThree + 1;
                    }
            }
            Double waterFirstAvg = 0d;
            Double waterSecondAvg = 0d;
            Double waterThirdAvg = 0d;
            if(tNumOne!=0){
                waterFirstAvg = Arith.round(trmmAvgOne/tNumOne,2);
            }
            if(tNumTwo!=0){
                waterSecondAvg = Arith.round(trmmAvgTwo/tNumTwo,2);
            }
            if(tNumThree!=0){
                waterThirdAvg = Arith.round(trmmAvgThree/tNumThree,2);
            }*/
            /**
             * 历史雨量均值
             */
            //历史雨量均值（上）
            /*double waterFirstAvg = 0d;
            if(tenYearTotal!=null)
                waterFirstAvg = tenYearTotal.getWaterFirstAvg();
            temTrmmTotal1.setTrmmYeasAvg(waterFirstAvg);
            //历史雨量均值（中）
            double waterSecondAvg = 0d;
            if(tenYearTotal!=null)
                waterSecondAvg = tenYearTotal.getWaterSecondAvg();
            temTrmmTotal2.setTrmmYeasAvg(waterSecondAvg);
            //历史雨量均值（下）
            double waterThirdAvg = 0d;
            if(tenYearTotal!=null)
                waterThirdAvg = tenYearTotal.getWaterThirdAvg();
            temTrmmTotal3.setTrmmYeasAvg(waterThirdAvg);*/
            double waterFirstAvg = 0d;
            double waterSecondAvg = 0d;
            double waterThirdAvg = 0d;
            if(tenYearTotal!=null){
                if("1".equals(tenTrmmData.getDateFlag())){
                    waterFirstAvg = tenYearTotal.getWaterFirstAvg();
                    tenTrmmData.setTrmmYeasAvg(waterFirstAvg);
                }else if("2".equals(tenTrmmData.getDateFlag())){
                    waterSecondAvg = tenYearTotal.getWaterSecondAvg();
                    tenTrmmData.setTrmmYeasAvg(waterSecondAvg);
                }else if("3".equals(tenTrmmData.getDateFlag())) {
                    waterThirdAvg = tenYearTotal.getWaterThirdAvg();
                    tenTrmmData.setTrmmYeasAvg(waterThirdAvg);
                }
            }

            /**
             * 距平均值
             */

            //上旬距平均值
            double distanceFirst = ArithUtil.sub(tenTrmmData.getTrmm(),tenTrmmData.getTrmmYeasAvg());
            tenTrmmData.setDistance(distanceFirst);
            //中旬距平均值
            //double distanceSecond = ArithUtil.sub(waterSecond,waterSecondAvg);
            //tenTrmmData.setDistance(distanceSecond);
            //下旬距平均值
            //double distanceThird = ArithUtil.sub(waterThird,waterThirdAvg);
            //tenTrmmData.setDistance(distanceThird);


            /**
             * 距平均值百分比
             */

            //距平均值百分比上旬
            double percentDistanceFirst = ArithUtil.div(tenTrmmData.getDistance(),tenTrmmData.getTrmmYeasAvg(),2);
            percentDistanceFirst = ArithUtil.mul(percentDistanceFirst,100d);
            tenTrmmData.setPercent(percentDistanceFirst+"%");
            //距平均值百分比上旬
            //double percentDistanceSecond = ArithUtil.div(distanceSecond,waterSecondAvg,2);
            //percentDistanceSecond = ArithUtil.mul(percentDistanceSecond,100d);
            //tenTrmmData.setPercent(percentDistanceSecond+"%");
            //距平均值百分比上旬
            //double percentDistanceThird = ArithUtil.div(distanceThird,waterThirdAvg,2);
            //percentDistanceThird = ArithUtil.mul(percentDistanceThird,100d);
            //tenTrmmData.setPercent(percentDistanceThird+"%");

            //最后装入容器
            //temTrmmTotalDataList.add(temTrmmTotal1);//上旬数据
            //temTrmmTotalDataList.add(temTrmmTotal2);//中旬旬数据
            // temTrmmTotalDataList.add(temTrmmTotal3);//下旬数据
            //组装dataFlag的值
            String dateFlag=tenTrmmData.getDate();
            if("1".equals(tenTrmmData.getDateFlag())){
                dateFlag+="上旬";
            }else if("2".equals(tenTrmmData.getDateFlag())){
                dateFlag+="中旬";
            }else if("3".equals(tenTrmmData.getDateFlag())){
                dateFlag+="下旬";
            }
            tenTrmmData.setDateFlag(dateFlag);

        }

        return  tenDaysTrmmList;
    }



    /** 导出查询数据
     * @param regionId 区域ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param regionName 区域名
     * @return
     * @version <1> 2018-06-28 cxw:Created
     */
    @Override
    public ResultMessage exportTrmmData(HttpServletRequest request, HttpServletResponse response, Long regionId, String regionName, String startDate, String endDate) throws IOException, ParseException {
        ResultMessage ju = ResultMessage.success();
        if(StringUtils.isNotBlank(regionName)&& StringUtils.isNotBlank(startDate)&& StringUtils.isNotBlank(endDate)&&regionId!=null)
        {
            TtnParam param = new  TtnParam();
            param.setRegionId(regionId);
            param.setStartDate(DateUtil.strToLocalDate(startDate));
            param.setEndDate(DateUtil.strToLocalDate(endDate));

            //调用导出excel方法
            if (null != request || null != response) {
                List<TemTrmmTotalDataReturn>  list= findTrmmListForTenDaysAndHistory(param);
                if(list.size()>0) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ExcelUtil.exportExcel(out, listObjectToListMap(list), 1l,"降雨","历年降雨均值");
                    String fileName = regionName+"("+DateUtil.yyyymmddToYYYYmm(startDate) +"至"+DateUtil.yyyymmddToYYYYmm(endDate)+ ")度" + "气象对比" ;
                    if (fileName == null || "".equals(fileName)) {
                        fileName = UUID.randomUUID().toString();
                    }

                    byte[] content = out.toByteArray();
                    InputStream is = new ByteArrayInputStream(content);
                    // 设置response参数，可以打开下载页面
                    response.reset();
                    //office 2007 excel文档
                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
                    response.setContentType("application/octet-stream;charset=utf-8");
                    response.setHeader("Content-Disposition", "attachment;filename="
                            + new String(fileName.getBytes(),"iso-8859-1") + ".xls");
                    response.setContentLength(content.length);
                    ServletOutputStream outputStream = response.getOutputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                    byte[] buff = new byte[8192];
                    int bytesRead;
                    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                        bos.write(buff, 0, bytesRead);
                    }
                    bis.close();
                    bos.close();
                    outputStream.flush();
                    outputStream.close();
                    ju.setMsg("导出数据成功");
                }
                else{
                    ju = ResultMessage.fail();
                    ju.setMsg("导出数据失败");
                }
            }
            else {
                ju = ResultMessage.fail();
                ju.setMsg("导出数据失败");
            }

        }

        else
        {
            ju = ResultMessage.fail();
            ju.setMsg("参数错误");
        }
        return  ju;
    }


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
            map.put("trmm",temp.getTrmm());
            map.put("trmmYeasAvg",temp.getTrmmYeasAvg());
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
    public  ResultMessage isExistTrmmData(Long regionId,String startDate,String endDate) {
        ResultMessage ju = ResultMessage.success();
        if(StringUtils.isNotBlank(startDate)&& StringUtils.isNotBlank(endDate)&&regionId!=null)
        {
            TtnParam param = new  TtnParam();
            param.setRegionId(regionId);
            param.setStartDate(DateUtil.strToLocalDate(startDate));
            param.setEndDate(DateUtil.strToLocalDate(endDate));

            List<TemTrmmTotalDataReturn>  list= findTrmmListForTenDaysAndHistory(param);
            if(list.size()>0) {
                //ju.setMsg("导出数据成功");

            }
            else {
                ju = ResultMessage.fail();
                ju.setMsg("查询数据为空");
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
    public List<DsTrmm> queryDateTimeList(ReportCreateParam reportCreateParam) {
        return dsTrmmMapper.queryDateTimeList(reportCreateParam);
    }
}
