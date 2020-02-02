package com.jh.dgy.service.impl;

import com.jh.dgy.entity.GrowthEntity;
import com.jh.dgy.mapping.IGrowthMapper;
import com.jh.dgy.service.IGrowthService;
import com.jh.dgy.vo.GrowthVo;
import com.jh.util.ArithUtil;
import com.jh.util.DateUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 长势分辨率服务:
 * 1. 根据区域、日期段查询区域及下一级区域在日期段内的长势数据
 * 2. 根据年份查询区域及子区域作物当年与上一年的长势数据
 * 3. 根据区域及指定日期段，查询近三年及近十年的长势数据
 * @version <1> 2018-04-27 cxw: Created.
 */
@Transactional
@Service
public class GrowthServiceImpl implements IGrowthService {

    @Autowired
    private IGrowthMapper growthMapper;

  /*
   * 根据区域、日期段查询区域及子区域作物当年与上一年的长势数据
   * @param  :
   *   regionId: 区域ID
   *     cropId: 作物ID
   *  startDate: 结束日期
   *    endDate: 结束日期
   *   resolution: 分辨率
   * @return ResultMessage ：返回区域作物当年与上一年的长势数据
   * @version <1> 2018-05-03 cxw: Created.
   */
//    @Override
//    public ResultMessage findGrowthForPrevious(Long regionId, Integer cropId,String startDate, String endDate,Integer resolution) {
//        //校验参数
//        ResultMessage result = checkGrowthParam(regionId,cropId,startDate,endDate,resolution);
//        if(result.isFlag()){
//            GrowthEntity gp = new GrowthEntity();
//            gp.setStartDate(DateUtil.strToLocalDate(startDate));
//            gp.setEndDate(DateUtil.strToLocalDate(endDate));
//            gp.setRegionId(regionId);
//            gp.setCropId(cropId);
//            gp.setResolution(resolution);
//            //求查询年份值
//            List<GrowthVo> curList = growthMapper.findGrowth(gp);
//            //求上一年值
//            LocalDate lastStartDate =  gp.getStartDate().plusYears(-1);
//            LocalDate lastEndDate =  gp.getEndDate().plusYears(-1);
//            int syear = gp.getEndDate().getYear();
//            int mon = gp.getEndDate().getMonthValue();
//            //润年平年判断（当前选择的年为平年则三年都为平年日期统计，若为润年都为润年统计）
//            gp = changeDay(gp,lastStartDate,lastEndDate,syear,mon);
//            List<GrowthVo> lastList = growthMapper.findGrowth(gp);
//            //将前一年的数据放入lastValue字段中，并计算percent.
//            for(GrowthVo ds : curList){
//                GrowthVo lastDs = findEqualRegion(ds,lastList);
//                if(lastDs!=null){
//                    ds.setLastValue(lastDs.getValue());
//                    if(ds.getValue()!=null && lastDs.getValue()!=null){
//                        double temp = ArithUtil.div(ds.getValue() - lastDs.getValue(), lastDs.getValue(),4);
//                        BigDecimal percent = new BigDecimal(Double.toString(temp));
//                        ds.setPercent(percent.floatValue());
//                    }
//                }
//            }
//            result.setData(curList);
//        }
//        return result;
//    }


    /**
     * 根据区域、日期段查询近三年及近十年的长势数据
     * @param  :
     *   regionId: 区域ID
     *     cropId: 作物ID
     *  startDate: 结束日期
     *    endDate: 结束日期
     *   resolution: 分辨率
     * @return : ResultMessage :返回近三年及近十年，区域日期段内的长势数据及均值。
     * @version <1> 2018-04-27 cxw: Created.
     */
//    @Override
//    public ResultMessage findGrowthThreeList(Long regionId, Integer cropId,String startDate, String endDate,Integer resolution) {
//        //校验参数
//        ResultMessage result = checkGrowthParam(regionId,cropId,startDate,endDate,resolution);
//        int avgYearNum = 10;
//        int showYearNum = 3;
//        int syear =   DateUtil.strToLocalDate(endDate).getYear();
//        int mon =  DateUtil.strToLocalDate(endDate).getMonthValue();
//        //用于保存三年及十年均值
//        Map<String, Object> allMap = new HashMap<String, Object>();
//        //临时保存十年均值
//        List<GrowthVo> avgList = new ArrayList<GrowthVo>();
//        if(result.isFlag()){
//            GrowthEntity gp = new GrowthEntity();
//            gp.setStartDate(DateUtil.strToLocalDate(startDate));
//            gp.setEndDate(DateUtil.strToLocalDate(endDate));
//            gp.setRegionId(regionId);
//            gp.setCropId(cropId);
//            gp.setResolution(resolution);
//
//            //获取查询年份日期的数据
//            for (int i = 0; i < avgYearNum; i++) {
//                //获取每一年的数据，并将年做为键，保存三年的数据
//                List<GrowthVo> dataList = growthMapper.findGrowthByYear(gp);
//                //润年平年日期转换
//                dataList = changeMonthValue(dataList,gp);
//                int year = gp.getStartDate().getYear();
//                String key = year + "";
//                if (i < showYearNum)   allMap.put(key, dataList);
//                //获取十年均值，如果当年没有值，则不计入平均值的计算。
//                for (int j = 0; j < dataList.size(); j++) {
//                    GrowthVo avgDs = null;
//                    if (j < avgList.size()) {
//                        avgDs = avgList.get(j);
//                    } else {
//                        avgDs = new GrowthVo();
//                        avgList.add(avgDs);
//                    }
//                    GrowthVo tempDs = dataList.get(j);
//                    avgDs.setMonthAndDay(tempDs.getMonthAndDay());
//                    avgDs.setRegionId(tempDs.getRegionId());
//                    avgDs.setRegionName(tempDs.getRegionName());
//                    float total = 0.0f, avgNum;
//                    total = avgDs.getValue() == null ? total : total + avgDs.getValue();
//                    total = tempDs.getValue() == null ? total : total + tempDs.getValue();
//                    avgNum = avgDs.getPercent() == null ? 0.0f : avgDs.getPercent();
//                    avgNum = tempDs.getValue() == null ? avgNum : avgNum + 1;
//                    //累加每年的值
//                    avgDs.setValue(total);
//                    //计算累加的年份数目
//                    avgDs.setPercent(avgNum);
//                }
//                //用于循环每年数据
//                LocalDate lastStartDate =  gp.getStartDate().plusYears(-1);
//                LocalDate lastEndDate =  gp.getEndDate().plusYears(-1);
//                //求上一年值
//                //润年平年判断（当前选择的年为平年则三年都为平年日期统计，若为润年都为润年统计）
//                gp = changeDay(gp,lastStartDate,lastEndDate,syear,mon);
//            }
//            //计算十年均值，并保存
//            for (GrowthVo ds : avgList) {
//                if (ds.getValue() != null) {
//                    double temp = ArithUtil.div(ds.getValue(), ds.getPercent(), 2);
//                    BigDecimal avg = new BigDecimal(Double.toString(temp));
//                    ds.setValue(avg.floatValue());
//                }
//            }
//            allMap.put("10", avgList);
//            //设置返回值
//            result = ResultMessage.success(null,null,allMap);
//        }
//        return result;
//    }


    /**
     * 检查长势查询服务所需的参数是否为空
     * @param  :
     *   regionId: 区域ID
     *     cropId: 作物ID
     *  startDate: 结束日期
     *    endDate: 结束日期
     *   resolution: 分辨率
     * @return ResultMessage :
     * @version <1> 2018-10-18 huxiaoqiang: Created.
     */
    private ResultMessage checkGrowthParam(Long regionId, Integer cropId,String startDate, String endDate,Integer resolution, String dataTime,String checkType){
        ResultMessage result = ResultMessage.success(null,null,null);
        if ("startAndEnd".equals(checkType)){
            if(startDate==null|| endDate==null){
                result = ResultMessage.fail(null,null,null);
                result.setMsg("查询年份不能为空.");
            }
        }else{
            if (dataTime == null){
                result = ResultMessage.fail(null,null,null);
                result.setMsg("查询日期不能为空.");
            }
        }

        if(regionId==null){
            result = ResultMessage.fail(null,null,null);
            result.setMsg("区域不能为空.");
        }

        if(cropId==null){
            result = ResultMessage.fail(null,null,null);
            result.setMsg("查询作物不能为空.");
        }

        if(resolution == null){
            result = ResultMessage.fail(null,null,null);
            result.setMsg("查询数据源不能为空.");
        }

        return result;
    }

    /**
     * 检查长势查询服务所需的参数是否为空
     * @param  :
     *   regionId: 区域ID
     *     cropId: 作物ID
     *  startDate: 结束日期
     *    endDate: 结束日期
     *   resolution: 分辨率
     * @return ResultMessage :
     * @version <1> 2019-03-20 huxiaoqiang: Created.
     */
    private ResultMessage checkGrowthParam(Long regionId,String startDate, String endDate){
        ResultMessage result = ResultMessage.success(null,null,null);
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            result = ResultMessage.fail(null, "日期不能为空", null);
        }
        if(regionId==null){
            result = ResultMessage.fail(null,null,null);
            result.setMsg("区域不能为空.");
        }

        return result;
    }



//    /**
//     * 润年平年日期转换
//     * @param list  :
//     * @return  List<DatasetReturn> :返回近三年及近十年，区域日期段内的长势数据及均值。
//     * @version <1> 2018-05-03 cxw: Created.
//     */
//    public static  List<GrowthVo>  changeMonthValue(List<GrowthVo>  list,GrowthEntity param)
//    {
//        List<GrowthVo> dataList = new ArrayList<GrowthVo>();
//        int syear = param.getStartDate().getYear();
//        Boolean res = false;
//        //润年平年判断（当前选择的年为平年则三年都为平年日期统计，若为润年都为润年统计）
//        if((syear%4==0&&syear%100!=0)||syear%400==0)   res = true;
//        for(int i=0;i<list.size();i++)
//        {
//            GrowthVo mp = list.get(i);
//            String dtime[] = mp.getDataTime().toString().split("-");
//            String time =mp.getDataTime().toString();
//            int year = Integer.parseInt(dtime[0]);
//            int mon = Integer.parseInt(dtime[1]);
//            if(((year%4==0&&year%100!=0)||year%400==0))
//            {
//                if(!res&&mon>2)   time =  DateUtil.getNextDay(time,"+1");
//            }
//            else {
//                if(res&&mon>2)     time =  DateUtil.getNextDay(time,"-1");
//            }
//            mp.setDataTime(LocalDate.parse(time));
//            dataList.add(mp);
//        }
//        return dataList;
//    }
//
//    /**
//     * 在列表中找区域相同的对象
//     * @param
//     * gv:本期值对象
//     * dataList：上一期值对象集合
//     * @return  GrowthVo 分布与估产返回对象
//     * @version <1> 2018-05-03 cxw:Created.
//     */
//    public GrowthVo findEqualRegion(GrowthVo  gv,List<GrowthVo> dataList){
//        for(GrowthVo ds : dataList){
//            if(gv.getRegionId().equals(ds.getRegionId())){
//                return ds;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 润年平年判断（当前选择的年为平年则三年都为平年日期统计，若为润年都为润年统计）
//     * @param :
//     * lastStartDate 上一年开始日期
//     * lastEndDate 上一年结束日期
//     * syear 年
//     * mon 月
//     * @return GrowthEntity
//     * @version <1> 2018-05-03 cxw: Created.
//     */
//    public GrowthEntity changeDay(GrowthEntity param, LocalDate lastStartDate, LocalDate lastEndDate, int syear, int mon)
//    {
//        Boolean res = false;
//        //润年平年判断（当前选择的年为平年则三年都为平年日期统计，若为润年都为润年统计）
//        if((syear%4==0&&syear%100!=0)||syear%400==0)
//        {
//            res = true;
//        }
//        if((((syear-1)%4==0&&(syear-1)%100!=0)||(syear-1)%400==0))
//        {
//            if(!res&&mon>2) {
//                lastStartDate = lastStartDate.plusMonths(+1);
//                lastEndDate = lastEndDate.plusMonths(+1);
//            }
//        }
//        else {
//            if(res&&mon>2){
//                lastStartDate = lastStartDate.plusMonths(-1);
//                lastEndDate = lastEndDate.plusMonths(-1);
//            }
//        }
//        param.setStartDate(lastStartDate);
//        param.setEndDate(lastEndDate);
//        return param;
//    }





    /*******************************************************************************************************************/


    /**
     * 查询指定日期范围内所有长势数据的时间点
     * @param regionId  区域id
     * @param cropId    作物id
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param resolution    数据精度
     * @return
     * @version <1> 2018-10-18 huxiaoqiang: Created.
     */
    @Override
    public ResultMessage findGrowthTimes(Long regionId, Integer cropId, String startDate, String endDate, Integer resolution) {
        ResultMessage result = checkGrowthParam(regionId,cropId,startDate,endDate,resolution,null,"startAndEnd");
        if (result.isFlag()){
            GrowthEntity growthEntity = new GrowthEntity();
            growthEntity.setRegionId(regionId);
            growthEntity.setCropId(cropId);
            growthEntity.setStartDate(DateUtil.strToLocalDate(startDate));
            growthEntity.setEndDate(DateUtil.strToLocalDate(endDate));
            growthEntity.setResolution(resolution);
            List<LocalDate> dataTimes = new ArrayList<LocalDate>();
            dataTimes = growthMapper.findGrowthTimes(growthEntity);
            if (dataTimes.size() >0 ){
                result.setData(dataTimes);
            }else{
                result = ResultMessage.fail("无长势数据");
            }
        }
        return result;
    }

    /**
     * 查询所有长势
     * @param regionId  区域id
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     * @version <1> 2019-03-20 cxw: Created.
     */
    public ResultMessage findAllGrowth(Long regionId,String startDate,String endDate){
        ResultMessage result = checkGrowthParam(regionId,startDate,endDate);
        if (result.isFlag()){
            GrowthEntity growthEntity = new GrowthEntity();
            growthEntity.setRegionId(regionId);
            growthEntity.setStartDate(DateUtil.strToLocalDate(startDate));
            growthEntity.setEndDate(DateUtil.strToLocalDate(endDate));
            List<GrowthVo> list = new ArrayList<GrowthVo>();
            list = growthMapper.findAllGrowth(growthEntity);
            result.setData(list);
        }
        return result;
    }

    /**
     * 查询该区域作物各种长势情况的面积分布和占比数据
     * @param regionId  区域id
     * @param cropId    作物id
     * @param dataTime  数据时间
     * @param resolution    数据精度
     * @return
     * @version <1> 2018-10-18 huxiaoqiang: Created.
     */
    @Override
    public ResultMessage queryGrowthArea(Long regionId, Integer cropId, String dataTime, Integer resolution) {
        ResultMessage result = checkGrowthParam(regionId,cropId,null,null,resolution,dataTime,"dataTime");
        if (result.isFlag()){
            GrowthEntity growthEntity = new GrowthEntity();
            growthEntity.setRegionId(regionId);
            growthEntity.setCropId(cropId);
            growthEntity.setDataTime(DateUtil.strToLocalDate(dataTime));
            growthEntity.setResolution(resolution);
            List<GrowthVo> grows = new ArrayList<GrowthVo>();
            grows = growthMapper.queryGrowthArea(growthEntity);

            if (grows.size() > 0){
                for (GrowthVo grow:grows){
                    Float total = grow.getValue();
                    Float lowestPercent;//长势-很差(百分比)
                    Float lowerPercent;//长势-差(百分比)
                    Float lowPercent;//长势-较差(百分比)
                    Float normalPercent;//长势-中等(百分比)
                    Float highPercent;//长势-较好(百分比)
                    Float higherPercent;//长势-好(百分比)
                    Float highestPercent;//长势-很好(百分比)
                    if (0f == total){
                        highestPercent = higherPercent = highPercent = normalPercent = lowPercent = lowerPercent = lowestPercent = 100f;
                    }else {
                        lowestPercent = grow.getLowest()/total*100;
                        lowerPercent = grow.getLower()/total*100;
                        lowPercent = grow.getLow()/total*100;
                        normalPercent = grow.getNormal()/total*100;
                        highPercent = grow.getHigh()/total*100;
                        higherPercent = grow.getHigher()/total*100;
                        highestPercent = grow.getHighest()/total*100;
                    }
                    grow.setHighestPercent(highestPercent);
                    grow.setHigherPercent(higherPercent);
                    grow.setHighPercent(highPercent);
                    grow.setNormalPercent(normalPercent);
                    grow.setLowPercent(lowPercent);
                    grow.setLowerPercent(lowerPercent);
                    grow.setLowestPercent(lowestPercent);
                }
                result.setData(grows);
            }else{
                result = ResultMessage.fail("无长势数据");
            }
        }
        return result;
    }

    /**
     * 查询该区域下各级区域的作物长势数据
     * @param regionId  区域id
     * @param cropId    作物id
     * @param dataTime  数据时间
     * @param resolution    数据精度
     * @return
     * @version <1> 2018-10-18 huxiaoqiang: Created.
     */
    @Override
    public ResultMessage queryGrowthInRegion(Long regionId, Integer cropId, String dataTime, Integer resolution) {
        ResultMessage result = checkGrowthParam(regionId,cropId,null,null,resolution,dataTime,"dataTime");
        if (result.isFlag()){
            GrowthEntity growthEntity = new GrowthEntity();
            growthEntity.setRegionId(regionId);
            growthEntity.setCropId(cropId);
            growthEntity.setDataTime(DateUtil.strToLocalDate(dataTime));
            growthEntity.setResolution(resolution);
            List<GrowthVo> grows = new ArrayList<GrowthVo>();
            grows = growthMapper.queryGrowthInRegion(growthEntity);
            if (grows.size() > 0){
                result.setData(grows);
            }else{
                result = ResultMessage.fail("无长势数据");
            }
        }
        return result;
    }


    /**
     * 查询该区域下各级各级区域的作物长势数据及上年同期作物长势数据
     * @param regionId  区域id
     * @param cropId    作物id
     * @param resolution    数据精度
     * @return
     * @version <1> 2018-10-18 huxiaoqiang: Created.
     */
//    @Override
//    public ResultMessage queryGrowthWithLastYear(Long regionId, Integer cropId, String dataTime, Integer resolution) {
//        ResultMessage result = checkGrowthParamWithDataTime(regionId,cropId,dataTime,resolution);
//        if (result.isFlag()){
//            GrowthEntity growthEntity = new GrowthEntity();
//            growthEntity.setRegionId(regionId);
//            growthEntity.setCropId(cropId);
//            growthEntity.setDataTime(DateUtil.strToLocalDate(dataTime));
//            growthEntity.setResolution(resolution);
//            List<GrowthVo> grows = new ArrayList<GrowthVo>();
//            grows = growthMapper.queryGrowthWithLastYear(growthEntity);
//            if (grows.size() > 0){
//                result.setData(grows);
//            }else{
//                result = ResultMessage.fail("无长势数据");
//            }
//        }
//        return result;
//    }
/*
    * 根据区域、时间，作物，分辨率查询作物长势
    * @param :
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    *   resolution: 分辨率
    * @return ResultMessage
    * @version <1> 2019-03-21 cxw: Created.
    */
    @Override
    public ResultMessage queryAllGrowthByTime(Long regionId,Integer cropId,String startDate,String endDate,Integer resolution){
        // 校验参数
        ResultMessage result = checkGrowthParam(regionId,cropId,startDate,endDate,resolution,null,"startAndEnd");
        if (result.isFlag()) {
            GrowthEntity dyp = new GrowthEntity();
            dyp.setStartDate(DateUtil.strToLocalDate(startDate));
            dyp.setRegionId(regionId);
            dyp.setCropId(cropId);
            dyp.setResolution(resolution);
            dyp.setEndDate(DateUtil.strToLocalDate(endDate));
            List<String> dataTimes = new ArrayList<String>();
            dataTimes = growthMapper.findGrowthTimesString(dyp);
            List<GrowthVo> curList =new ArrayList<GrowthVo>();
            if (dataTimes.size() > 0) {
                for(String dataTime:dataTimes) {
                    ResultMessage resultData = queryGrowthArea(regionId, cropId, dataTime, resolution);
                    if(resultData.isFlag()){
                        curList.addAll((List<GrowthVo>)resultData.getData());
                    }
                }
                result = ResultMessage.success(curList);
            } else {
                result = ResultMessage.fail("无长势数据");
            }
        }
        return result;
    }

}
