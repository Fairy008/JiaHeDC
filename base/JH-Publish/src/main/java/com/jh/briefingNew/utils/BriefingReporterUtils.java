package com.jh.briefingNew.utils;

import com.jh.briefingNew.entity.*;
import com.jh.briefingNew.enums.BriefingReportEnum;
import com.jh.briefingNew.model.BriefingReportParam;
import com.jh.briefingNew.model.TrrmStatisticsMultRegionNew;
import com.jh.briefingNew.model.TtnRegionAvg;
import com.jh.briefingNew.model.chartDatas;
import com.jh.briefingNew.service.impl.BriefingReportServiceImpl;
import com.jh.util.CollectionUtil;
import com.jh.util.DateUtil;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * 简报服务类辅助公共方法类
 * Created by wl on 2018-07-19.
 */
public class BriefingReporterUtils {

    private static Integer BACK_MONTH = 2;//想前倒退多少个月

    private static String beginByYear = "01 - 01";

    private static String endByYear = "12 - 31";

    private static String outPutPath = CephUtils.getAbsolutePath(CephUtils.getCephBrief());//输出文件夹地址

    private static  BriefingReportServiceImpl briefingReportService;

    public BriefingReporterUtils(BriefingReportServiceImpl briefingReportService) {
        this.briefingReportService = briefingReportService;
    }


    /**
     * 根据选中的日期，与当前作物、区域对应的物候期的开始、结束时间比较，获取对应的物候期、播种日期
     *
     * @param endDate   结束时间
     * @param cropsGrowthPeriodParamList 物候期列表
     * @param briefReportData      模板参数
     * @version <1> 2018-07-19 wl： Create
     */
    public void calculateCropsGrowthName(String endDate, List<BriefingCropsGrowthPeriod> cropsGrowthPeriodParamList, BriefReportData briefReportData,BriefingReportParam briefingReportParam ,ResultMessage resultMessage) {
        if(cropsGrowthPeriodParamList.size() == 0){
            return;
        }

        String growthStartDate = "";//播种时间  物候期开始时间
        String growthEndDate = "";//物候期结束时间
        String growthName = "";//查询到物候期之后，根据选择的时间，判断在哪个物候期
        //组装结束时间
        LocalDate endLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        for (BriefingCropsGrowthPeriod cropsGrowthPeriodParam : cropsGrowthPeriodParamList) {
            String rangeStart = cropsGrowthPeriodParam.getRangeStart();//物候期开始区间
            String rangeEnd = cropsGrowthPeriodParam.getRangeEnd();//物候期结束区间
            Short ifGrowthStart = cropsGrowthPeriodParam.getIfGrowthStart();//是否为播种期

            Integer growthId = null;//物候期编号

            //将区间时间重新组装成时间格式，开始时间、结束时间
            Integer currentYear = endLocalDate.getYear();//获取选中时间的年份
            rangeStart = currentYear + "-" + rangeStart;
            rangeEnd = currentYear + "-" + rangeEnd;

            //组装开始、结束时间
            LocalDate rangeStartDate = LocalDate.parse(rangeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate rangeEndDate = LocalDate.parse(rangeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            /**
             * 判断是跨年
             */
            //开始判断物候期开始时间与结束时间的大小，决定是否跨年
          /*  if (rangeStartDate.compareTo(rangeEndDate) > 0) {//物候期跨年
                //物候期开始时间大于结束时间，说明开始时间处于跨年段
                rangeStartDate = rangeStartDate.minus(1, ChronoUnit.YEARS);
            }
*/
            /*
             * 判断是播种期，
             * 需要获得播种期的日期，这里需要判断，当前选中的日期，与播种期进行比较。
             * 如果 选中的日期 > 播种期，那么就以播种期为主。
             * 如果 选中的日期 < 播种期，那么播种期需要减去一年，
             */
            if (ifGrowthStart == 1) {
                //组装播种时间
                growthStartDate = currentYear + "-" + cropsGrowthPeriodParam.getRangeStart();
                LocalDate growthStartLocalDate = LocalDate.parse(growthStartDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                //开始判断播种时间与选中日期的大小
                if (endLocalDate.compareTo(growthStartLocalDate) >= 0) {
                    //如果选中时间大于播种时间，说明选中的时间和播种时间在同一年
                    growthStartDate = growthStartLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } else {
                    //如果小于，说明选中的时间在当前年，小于播种期，那么就是跨年了，播种期时间需要减1。
                    growthStartLocalDate = growthStartLocalDate.minusYears(1); //跨年，需要往过去退一年
                    growthStartDate = growthStartLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
                //设置物候期开始时间
                briefingReportParam.setGrowthStartDate(growthStartDate);
            }

            if(rangeEndDate.compareTo(rangeStartDate) <0){//生育期跨年
                //如果简报时间小于生育期开始时间 如简报时间未2017-10-01 生育期开始是2017-11-11   2017-01-20
                //如果简报时间小于生育期的结束时间  则生育期的开始时间减少一年   如果简报时间大于生育期结束时间  则生育期的结束时间加一年
               /* if(endLocalDate.compareTo(rangeEndDate) >= 0){
                    rangeEndDate = rangeEndDate.plus(1,ChronoUnit.YEARS);
                }else{
                    rangeStartDate = rangeStartDate.minus(1,ChronoUnit.YEARS);
                }*/
                rangeStartDate = rangeStartDate.minus(1,ChronoUnit.YEARS);
            }



            //后面将选中的 endLocalDate 判断是否在 rangeStartDate 和 rangeEndDate范围内，就可以选中对应的物候期了。
            if (endLocalDate.compareTo(rangeStartDate) >= 0 && endLocalDate.compareTo(rangeEndDate) <= 0) {
                growthName = cropsGrowthPeriodParam.getGrowthName();//物候期名称
              //  growthId = briefingCropsGrowthPeriod.getGrowthId();//物候期编号
                String  remark = cropsGrowthPeriodParam.getRemark();//备注信息
                briefReportData.setGrowthPeriodName(growthName);//设置物候期名称
                briefReportData.setGrowthId(cropsGrowthPeriodParam.getGrowthId());//物候期编号
                briefReportData.setRemark(remark);//设置备注信息到模板
            }
        }
        //获取最后一個生育期
        BriefingCropsGrowthPeriod cropsGrowthPeriodParam = cropsGrowthPeriodParamList.get(cropsGrowthPeriodParamList.size()-1);
        String rangeEnd = cropsGrowthPeriodParam.getRangeEnd();//最后区间
        Integer currentYear = endLocalDate.getYear();//获取选中时间的年份
        String lastGrowthDate =currentYear+"-"+rangeEnd;//最后物候期时间
        LocalDate growthEndDateT = LocalDate.parse(lastGrowthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));//最后的物候期
        if (endLocalDate.compareTo(growthEndDateT) >= 0) {//简报时间比物候期的最后时间大 则物候期的年份+1
            growthEndDateT =growthEndDateT.plus(1,ChronoUnit.YEARS);
        }


       /* String rangeEnd = briefingCropsGrowthPeriod.getRangeEnd();//最后区间
        Integer currentYear = endLocalDate.getYear();//获取选中时间的年份
        growthEndDate =currentYear+"-"+rangeEnd;//最后物候期时间*/
        if(growthName.equals("")){
            resultMessage.setFlag(false);
            resultMessage.setMsg("选择的时间不在物候期内（"+growthStartDate+"~"+growthEndDateT+"）");
        }
        briefingReportParam.setLastGrowthDate(lastGrowthDate);//设置物候期最后时间
    }


    /**
     * 查询日均地温信息  查询有效积温信息
     * @param briefReportData 简报参数
     * @return 地温统计信息
     * @version <1> 2018-07-20 wl： Create
     */
    public  void avgTtn(BriefingReportParam briefingReportParam,BriefReportData briefReportData){
        //查询日均地温最大最小值
        List<TtnRegionAvg> ttnRegionAvgList = briefingReportService.queryTtnByRegionAndDateTime(briefingReportParam); //根据 regionId（区域编号） 和 dateTime （创建时间）查询地温均值信息
        if (ttnRegionAvgList.size() ==  0) {//有数据，才入模板
            Integer level = briefingReportParam.getLevel();//获取区域级别；
            if(level < 4){//级别在省、市级别，在没有数据的情况下，就读省、市当前级别，不在作为父级别去读取子级别
                level = 4;//这里设置4，是因为在mapper查询中，level=4的时候，才读本级别，不在作为父级别来获取子级别
            }
            briefingReportParam.setLevel(level);
            ttnRegionAvgList = briefingReportService.queryTtnByRegionAndDateTime(briefingReportParam); //根据 regionId（区域编号） 和 dateTime （创建时间）查询地温均值信息
        }
        //设置当前地温数据
        if(ttnRegionAvgList != null && ttnRegionAvgList.size()>0){
            TtnRegionAvg ttnAvgMin = ttnRegionAvgList.get(0);//最小日均地温信息
            briefReportData.setTtnAvgMin(ttnAvgMin.getTtnAvg());//将最小日均地温信息入地温统计模板
            briefReportData.setTtnAvgMinRegionName(ttnAvgMin.getRegionName());
            if(ttnRegionAvgList.size()>1){
                TtnRegionAvg ttnAvgMax = ttnRegionAvgList.get(ttnRegionAvgList.size() - 1);//最大日均地温信息
                briefReportData.setTtnAvgMax(ttnAvgMax.getTtnAvg());//将最大日均地温信息入地温统计模板
                briefReportData.setTtnAvgMaxRegionName(ttnAvgMax.getRegionName());
            }
        }

        /*
         * 周最大、最小日有效积温信息、及区域(自播种日期到选择日期的有效积温)
         */
        List<TtnRegionAvg> ttnRegionTotalList = briefingReportService.queryTtnTotalByRegionAndDateTime(briefingReportParam);//根据 regionId（区域编号） 和 dateTime （创建时间）查询有效积温值信息
        if(ttnRegionTotalList.size() == 0){
            Integer level = briefingReportParam.getLevel();//获取区域级别；
            if(level < 4){//级别在省、市级别，在没有数据的情况下，就读省、市当前级别，不在作为父级别去读取子级别
                level = 4;//这里设置4，是因为在mapper查询中，level=4的时候，才读本级别，不在作为父级别来获取子级别
            }
            briefingReportParam.setLevel(level);
            ttnRegionTotalList = briefingReportService.queryTtnTotalByRegionAndDateTime(briefingReportParam);//根据 regionId（区域编号） 和 dateTime （创建时间）查询有效积温值信息
        }

        if (ttnRegionTotalList.size() > 0) {//有数据，才入模板
            TtnRegionAvg ttnTotalMin = ttnRegionTotalList.get(0);//最小有效积温信息
            briefReportData.setTtnTotalMin(ttnTotalMin.getTtnAvg()); //将最大有效积温信息入地温统计模板
            briefReportData.setTtnTotalMinRegionName(ttnTotalMin.getRegionName());
            if(ttnRegionTotalList.size()>1){
                TtnRegionAvg ttnTotalMax = ttnRegionTotalList.get(ttnRegionTotalList.size() - 1); //最大有效积温信息
                briefReportData.setTtnTotalMax(ttnTotalMax.getTtnAvg()); //将最大有效积温信息入地温统计模板
                briefReportData.setTtnTotalMaxRegionName(ttnTotalMax.getRegionName());
            }
        }
    }



    /**
     * 设置地温统计信息
     * @param briefReportData 简报参数
     * @return 地温统计信息
     * @version <1> 2018-07-19 wl： Create
     */
    public void statisticsTtn(BriefingReportParam briefingReportParam,BriefReportData briefReportData){
        //首先判断日期是否跨年  如果跨年需要另做处理
        String start=briefingReportParam.getStartDate();
        String end = briefingReportParam.getEndDate();
        LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Integer ifSpan = endDate.getYear() - startDate.getYear();
        List<chartDatas> historyTtnList=new ArrayList<>();
        if (ifSpan>0) {//说明日期范围跨年 查询历史记录时需要处理
            briefingReportParam.setHistory(BriefingReportEnum.IS_HISTORY.getValue());
           briefingReportParam.setHistory(BriefingReportEnum.IS_HISTORY.getValue());
           briefingReportParam.setStartDateNoYear(startDate.format(DateTimeFormatter.ofPattern("MM-dd")));
           briefingReportParam.setEndDateNoYear(endByYear);
           List<chartDatas>  historyTtnListBefore=briefingReportService.queryTtnStatistics(briefingReportParam);
           briefingReportParam.setStartDateNoYear(beginByYear);
           briefingReportParam.setEndDateNoYear(endDate.format(DateTimeFormatter.ofPattern("MM-dd")));
           List<chartDatas> historyTtnListLate=briefingReportService.queryTtnStatistics(briefingReportParam);
           historyTtnList.addAll(historyTtnListBefore);
           historyTtnList.addAll(historyTtnListLate);

        } else {
           briefingReportParam.setStartDateNoYear(startDate.format(DateTimeFormatter.ofPattern("MM-dd")));
           briefingReportParam.setEndDateNoYear(endDate.format(DateTimeFormatter.ofPattern("MM-dd")));
           briefingReportParam.setHistory(BriefingReportEnum.IS_HISTORY.getValue());
           historyTtnList = briefingReportService.queryTtnStatistics(briefingReportParam);
        }
        //查询当前地温记录信息
        briefingReportParam.setHistory(BriefingReportEnum.NO_HISTORY.getValue());
        briefingReportParam.setHistory(BriefingReportEnum.NO_HISTORY.getValue());
        List<chartDatas> nowTtnList = briefingReportService.queryTtnStatistics(briefingReportParam);


        List<String> ttnDate = new ArrayList<>();//地温统计图时间
        List<Double> ttnNow = new ArrayList<>();//地温统计图当前变化
        List<Double> ttnHistory = new ArrayList<>();//地温统计图历史均值

        if(historyTtnList.size()>0){
            for(int i = 0 ;i<historyTtnList.size();i++){
                ttnDate.add(historyTtnList.get(i).getDataTime());
                ttnHistory.add(historyTtnList.get(i).getAvgData());
                for(int j=0 ; j<nowTtnList.size();j++)
                if(nowTtnList.size()>0 && nowTtnList.get(j).getDataTime().equals(historyTtnList.get(i).getDataTime())){
                    ttnNow.add(nowTtnList.get(j).getAvgData());
                }
            }
            //--设置数据到模板中
            briefReportData.setTtnDateList(ttnDate);
            briefReportData.setTtnNowList(ttnNow);
            briefReportData.setTtnHistoryList(ttnHistory);
            //设置数据到模板中
            briefReportData.setTtnDate(ttnDate.toString());
            briefReportData.setTtnNow(ttnNow.toString());
            briefReportData.setTtnHistory(ttnHistory.toString());
            briefReportData.setTtnDateList(ttnDate);
        }
        //统计图y轴最大值
        Double maxTtn=0.0;
        		
        Double minTtn=0.0;
        if(ttnHistory.size()>0){
        	maxTtn = Collections.max(ttnHistory)+20;//在最大值的基础上加10
        	minTtn = Collections.min(ttnHistory)-20;//在最小值的基础上减10
        }
        briefReportData.setMaxTtn(maxTtn);
        briefReportData.setMinTtn(minTtn);
        briefingReportParam.setStartDateNoYear(startDate.format(DateTimeFormatter.ofPattern("MM-dd")));
        briefingReportParam.setEndDateNoYear(endDate.format(DateTimeFormatter.ofPattern("MM-dd")));

    }

    /**
     * 设置降雨统计信息
     * @param briefReportData 简报参数
     * @return 降雨统计信息
     * @version <1> 2018-07-23 wl： Create
     */
    public void totalTrmm(BriefingReportParam briefingReportParam, String endDate,BriefReportData briefReportData) {
           /*
         * 雨量统计模板
         */
       // TrrmStatistics trrmStatistics = new TrrmStatistics(); //定义雨量统计模板
        //组装选择日期，往过去退指定月份，同时获取去年年份，最后填入TemplateReporterParam中，供查询使用。
        wrapLastMonthDateTime(briefingReportParam, endDate);
        //按照区域，获取区域本周降雨量总和信息列表
        List<TrrmStatisticsMultRegionNew> trrmStatisticsList = briefingReportService.queryTrrmStatisticsForWeek(briefingReportParam);
        Integer level = briefingReportParam.getLevel();
        if(trrmStatisticsList.size() == 0){
            if(level < 4){//级别在省、市级别，在没有数据的情况下，就读省、市当前级别，不在作为父级别去读取子级别
                level = 4;//这里设置4，是因为在mapper查询中，level=4的时候，才读本级别，不在作为父级别来获取子级别
            }
            briefingReportParam.setLevel(level);
            trrmStatisticsList = briefingReportService.queryTrrmStatisticsForWeek(briefingReportParam);
        }

        List<BriefingHumidity> humidities = briefingReportService.queryAllHumidity();//查询所有的湿度信息，然后根据雨量计算湿度
        List<BriefingSoilMoisture> soilMoistures = briefingReportService.queryAllSoilMoisture();//获取所有墒情
        if (trrmStatisticsList.size() > 0) {//有数据，才入模板
            TrrmStatisticsMultRegionNew minTrrmRegion = trrmStatisticsList.get(0); //获取最小降雨量、相关区域
            briefReportData.setTrmmTotalMin(minTrrmRegion.getTrrmTotal());
            briefReportData.setTrmmTotalMinRegionName(minTrrmRegion.getRegionName());
           // trrmStatistics.setMinTrrmRegion(minTrrmRegion); //入雨量统计模板
            if(trrmStatisticsList.size() > 1){//数据记录数超过一条时才设置最大降雨
                TrrmStatisticsMultRegionNew maxTrrmRegion = trrmStatisticsList.get(trrmStatisticsList.size() - 1); //获取最大降雨量，相关区域
                briefReportData.setTrmmTotalMax(maxTrrmRegion.getTrrmTotal());
                briefReportData.setTrmmTotalMaxRegionName(maxTrrmRegion.getRegionName());
            }
           // trrmStatistics.setMaxTrrmRegion(maxTrrmRegion);//入雨量统计模板
            for (TrrmStatisticsMultRegionNew trrmRegion : trrmStatisticsList) {
                calculateHumidity(humidities, trrmRegion); //循环设置湿度
                calculateSoilMoisture(soilMoistures, trrmRegion);//循环设置墒情信息
            }

        }else{
            //本期没有雨量，就统计60天的。
            trrmStatisticsList = briefingReportService.queryTrrmStatisticsForMonth(briefingReportParam); //按照区域，获取区域60天降雨量总和信息列表
            if(trrmStatisticsList.size() == 0){
                level = briefingReportParam.getLevel();
                if(level < 4){//级别在省、市级别，在没有数据的情况下，就读省、市当前级别，不在作为父级别去读取子级别
                    level = 4;//这里设置4，是因为在mapper查询中，level=4的时候，才读本级别，不在作为父级别来获取子级别
                }
                briefingReportParam.setLevel(level);
                trrmStatisticsList = briefingReportService.queryTrrmStatisticsForMonth(briefingReportParam); //按照区域，获取区域60天降雨量总和信息列表
            }
            if(trrmStatisticsList.size()>0){
                TrrmStatisticsMultRegionNew minTrrmRegion = trrmStatisticsList.get(0); //获取最小降雨量、相关区域
                briefReportData.setTrmmTotalMin(minTrrmRegion.getTrrmTotal());
                briefReportData.setTrmmTotalMinRegionName(minTrrmRegion.getRegionName());
            }
            // trrmStatistics.setMinTrrmRegion(minTrrmRegion); //入雨量统计模板
            if(trrmStatisticsList.size() > 1){//数据记录数超过一条时才设置最大降雨
                TrrmStatisticsMultRegionNew maxTrrmRegion = trrmStatisticsList.get(trrmStatisticsList.size() - 1); //获取最大降雨量，相关区域
                briefReportData.setTrmmTotalMax(maxTrrmRegion.getTrrmTotal());
                briefReportData.setTrmmTotalMaxRegionName(maxTrrmRegion.getRegionName());
            }

            for (TrrmStatisticsMultRegionNew trrmRegion : trrmStatisticsList) {
                calculateHumidity(humidities, trrmRegion); //循环设置湿度
                calculateSoilMoisture(soilMoistures, trrmRegion);//循环设置墒情信息
            }
        }



        //该区域的墒情情况，如果环比都是大于0的那么就取最大的两个值和区域
        //如果环比都是小于0的那么就取最小的两个值，如果是两者都有则取一个最大一个最小
        String comparedLastYear="";
        //组装墒情评价
        String soilMoisture = "";
        if(trrmStatisticsList.size()>1){//有多个区域的记录
            //按照历史环比排序
            Collections.sort(trrmStatisticsList, (o1, o2) -> {//排序 从小到大
                if (o1.getPercentHistoryYear() - o2.getPercentHistoryYear() > 0) {
                    return 1;
                } else if (o1.getPercentHistoryYear() - o2.getPercentHistoryYear() < 0) {
                    return -1;
                } else {
                    return 0;
                }
            });
            if(trrmStatisticsList.get(0).getPercentHistoryYear()>0){//最小的大于0
                //降雨同比增加最多的
                Double top1 = trrmStatisticsList.get(trrmStatisticsList.size() - 1).getPercentHistoryYear();
                String top1Region = trrmStatisticsList.get(trrmStatisticsList.size() - 1).getRegionName();
                // 降雨同比增加第二多的
                Double top2 = trrmStatisticsList.get(trrmStatisticsList.size() - 2).getPercentHistoryYear();
                String top2Region = trrmStatisticsList.get(trrmStatisticsList.size() - 2).getRegionName();
                comparedLastYear += top1Region+"比历史同期多"+Math.abs(top1)+"%,"+top2Region+"比历史同期多"+Math.abs(top2)+"%";
            }else if(trrmStatisticsList.get(trrmStatisticsList.size() - 1).getPercentHistoryYear()<0){//最大的小于0
                //降雨同比减少最多的
                Double top1 = trrmStatisticsList.get(0).getPercentHistoryYear();
                String top1Region = trrmStatisticsList.get(0).getRegionName();
                //降雨同比减少第二多的
                Double top2 = trrmStatisticsList.get(0).getPercentHistoryYear();
                String top2Region = trrmStatisticsList.get(0).getRegionName();
                comparedLastYear += top1Region+"比历史同期少"+Math.abs(top1)+"%,"+top2Region+"比历史同期少"+Math.abs(top2)+"%";
            }else {
                Double min = trrmStatisticsList.get(0).getPercentHistoryYear();
                String minRegion = trrmStatisticsList.get(0).getRegionName();
                Double max = trrmStatisticsList.get(trrmStatisticsList.size() - 1).getPercentHistoryYear();
                String maxRegion = trrmStatisticsList.get(trrmStatisticsList.size() - 1).getRegionName();
                comparedLastYear += maxRegion+"比历史同期多"+max+"%,"+minRegion+"比历史同期少"+Math.abs(min)+"%";
            }

            //按墒情值排序
            Collections.sort(trrmStatisticsList, (o1, o2) -> {
            	if(o1.getPercentMonthHistoryYear()==null||o2.getPercentMonthHistoryYear()==null){
            		return 0;
            	}
                if (o1.getPercentMonthHistoryYear() - o2.getPercentMonthHistoryYear()> 0) {
                    return 1;
                } else if (o1.getPercentMonthHistoryYear() - o2.getPercentMonthHistoryYear() < 0) {
                    return -1;
                } else {
                    return 0;
                }
            });
            //如果最小值>0则 查询最大的两个 如果最大值<0则查询最小的两个  否则取一个最大一个最小
            if(trrmStatisticsList.get(0).getPercentMonthHistoryYear()>0){//最小的大于0
                //降雨同比增加最多的
                Double top1 = trrmStatisticsList.get(trrmStatisticsList.size() - 1).getPercentMonthHistoryYear();
                String top1Region = trrmStatisticsList.get(trrmStatisticsList.size() - 1).getRegionName();
                String top1Detail = trrmStatisticsList.get(trrmStatisticsList.size() - 1).getSoilMoisture();
                // 降雨同比增加第二多的
                Double top2 = trrmStatisticsList.get(trrmStatisticsList.size() - 2).getPercentMonthHistoryYear();
                String top2Region = trrmStatisticsList.get(trrmStatisticsList.size() - 2).getRegionName();
                String top2Detail = trrmStatisticsList.get(trrmStatisticsList.size() - 2).getSoilMoisture();
                soilMoisture += top1Region + "、"  + top2Region + "等地较历年比多" + top1+ "%、" + top2 +"%,"+ top1Detail + "~" + top2Detail;
            }else if(trrmStatisticsList.get(trrmStatisticsList.size() - 1).getPercentMonthHistoryYear()<0){//最大的小于0
                //降雨同比减少最多的
                Double top1 = trrmStatisticsList.get(0).getPercentMonthHistoryYear();
                String top1Region = trrmStatisticsList.get(0).getRegionName();
                String top1Detail = trrmStatisticsList.get(0).getSoilMoisture();
                //降雨同比减少第二多的
                Double top2 = trrmStatisticsList.get(0).getPercentMonthHistoryYear();
                String top2Region = trrmStatisticsList.get(0).getRegionName();
                String top2Detail = trrmStatisticsList.get(0).getSoilMoisture();
                soilMoisture += top1Region + "、" + top2Region + "等地较历年比少" + Math.abs(top1) + "%、" + Math.abs(top2) +"%,"+ top1Detail + "~" + top2Detail;
            }else {
                Double min = trrmStatisticsList.get(0).getPercentMonthHistoryYear();
                String minRegion = trrmStatisticsList.get(0).getRegionName();
                String minDetail = trrmStatisticsList.get(0).getSoilMoisture();
                Double max = trrmStatisticsList.get(trrmStatisticsList.size() - 1).getPercentMonthHistoryYear();
                String maxRegion = trrmStatisticsList.get(trrmStatisticsList.size() - 1).getRegionName();
                String maxDetail = trrmStatisticsList.get(trrmStatisticsList.size() - 1).getSoilMoisture();
                soilMoisture += maxRegion + "较历年比多" + max + "%," + minRegion + "较历年比少" + Math.abs(min) +"%," + maxDetail + "~" + minDetail;
            }
        }else{//仅有一个区域时
            if (CollectionUtil.isNotEmpty(trrmStatisticsList)) {//判断不为空
                Double top1Soil = trrmStatisticsList.get(0).getPercentMonthHistoryYear();
                Double top1Com = trrmStatisticsList.get(0).getPercentHistoryYear();
                String top1Region = trrmStatisticsList.get(0).getRegionName();
                String top1Detail = trrmStatisticsList.get(0).getSoilMoisture();
                if(top1Com>0){
                    comparedLastYear += top1Region + "比历史同期多" + top1Com +"%,"+ top1Detail ;
                }else{
                    comparedLastYear += top1Region + "比历史同期少" + Math.abs(top1Com) +"%,"+ top1Detail ;
                }

                if(top1Soil>0){
                    soilMoisture += top1Region + "等地较历年比多" + top1Soil +"%,"+ top1Detail ;
                }else{
                    soilMoisture += top1Region + "等地较历年比少" + Math.abs(top1Soil) +"%,"+ top1Detail ;
                }
            }
        }

        briefReportData.setComparedLastYear(comparedLastYear);//环比降雨
        briefReportData.setSoilMoisture(soilMoisture);//墒情

    }


    /**
     * 设置降雨统计信息
     * @param briefReportData 简报参数
     * @return 降雨统计信息
     * @version <1> 2018-07-24 wl： Create
     */
    public void statisticsTrmm(BriefingReportParam briefingReportParam,BriefReportData briefReportData){
        //首先判断日期是否跨年  如果跨年需要另做处理
        String start=briefingReportParam.getStartDate();
        String end = briefingReportParam.getEndDate();
        LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Integer ifSpan = endDate.getYear() - startDate.getYear();
        List<chartDatas> historyTrmmList=new ArrayList<>();
        if (ifSpan>0) {//说明日期范围跨年 查询历史记录时需要处理
            briefingReportParam.setHistory(BriefingReportEnum.IS_HISTORY.getValue());
            briefingReportParam.setStartDateNoYear(startDate.format(DateTimeFormatter.ofPattern("MM-dd")));
            briefingReportParam.setEndDateNoYear(endByYear);
            List<chartDatas>  historyTtnListBefore=briefingReportService.queryTrmmStatistics(briefingReportParam);
            briefingReportParam.setStartDateNoYear(beginByYear);
            briefingReportParam.setEndDateNoYear(endDate.format(DateTimeFormatter.ofPattern("MM-dd")));
            List<chartDatas> historyTtnListLate=briefingReportService.queryTrmmStatistics(briefingReportParam);
            historyTrmmList.addAll(historyTtnListBefore);
            historyTrmmList.addAll(historyTtnListLate);

        } else {
            briefingReportParam.setStartDateNoYear(startDate.format(DateTimeFormatter.ofPattern("MM-dd")));
            briefingReportParam.setEndDateNoYear(endDate.format(DateTimeFormatter.ofPattern("MM-dd")));
            briefingReportParam.setHistory(BriefingReportEnum.IS_HISTORY.getValue());
            historyTrmmList = briefingReportService.queryTrmmStatistics(briefingReportParam);
        }
        //查询当前j降雨记录信息
        briefingReportParam.setHistory(BriefingReportEnum.NO_HISTORY.getValue());
        List<chartDatas> nowTrmmList = briefingReportService.queryTrmmStatistics(briefingReportParam);


        List<String> trmmDate = new ArrayList<>();//降雨统计图时间
        List<Double> trmmNow = new ArrayList<>();//降雨统计图当前变化
        List<Double> trmmHistory = new ArrayList<>();//降雨统计图历史均值

        if(historyTrmmList.size()>0){
            for(int i = 0 ;i<historyTrmmList.size();i++){
                trmmDate.add(historyTrmmList.get(i).getDataTime());
                trmmHistory.add(historyTrmmList.get(i).getAvgData());
                for(int j=0 ;j<nowTrmmList.size();j++){
                    if(nowTrmmList.get(j).getDataTime().equals(historyTrmmList.get(i).getDataTime())){
                        trmmNow.add(nowTrmmList.get(j).getAvgData());
                    }
                }

            }
            //--设置数据到模板中
            briefReportData.setTrmmDateList(trmmDate);
            briefReportData.setTrmmHistoryList(trmmHistory);
            briefReportData.setTrmmNowList(trmmNow);

            //设置数据到模板中
            briefReportData.setTrmmDate(trmmDate.toString());
            briefReportData.setTrmmNow(trmmNow.toString());
            briefReportData.setTrmmHistory(trmmHistory.toString());
            //统计图y轴最大值
            if(trmmNow.size()>0){
                Double maxHistory=Collections.max(trmmHistory);//在最大值的基础上加10
                Double maxNow = Collections.max(trmmNow);
                Double maxTrmm=100d;
                if(maxHistory>maxNow){
                    maxTrmm = maxHistory+10;
                }else{
                    maxTrmm = maxNow + 10;
                }
                briefReportData.setMaxTrmm(maxTrmm);
            }else if(trmmHistory.size()>0){
                Double maxHistory=Collections.max(trmmHistory);//在最大值的基础上加10
                briefReportData.setMaxTrmm(maxHistory+10);
            }else{
                briefReportData.setMaxTrmm(20d);
            }


        }
    }

    /**
     * 作物病情、虫害防治措施,入模板
     *
     * @param briefingReportParam 查询参数
     * @param briefReportData          模板参数
     * @version <1> 2018-07-24 wl： Create
     */
    public void setDiseaseInfoInoTemplate(BriefingReportParam briefingReportParam, BriefReportData briefReportData) {
        //获得对应的物候期编号
        Integer growthId = briefReportData.getGrowthId();
        List<BriefingDiseaseAll> diseaseControlParamList = briefingReportService.getBriefingDiseaseAllService().queryCropsDiseaseByGrowthId(growthId,BriefingReportEnum.DISEASE.getValue());//根据物候期查询对应的病情
        List<BriefingDiseaseAll> bugDiseaseControlParamList = briefingReportService.getBriefingDiseaseAllService().queryCropsDiseaseByGrowthId(growthId,BriefingReportEnum.BUG_TYPE.getValue());//根据物候期查询对应的虫害
        String diseaseDetail = "";
        String bugDetail = "";
        for (BriefingDiseaseAll disease : diseaseControlParamList) {
            diseaseDetail +=disease.getDiseaseName() + ",";
        }
        for (BriefingDiseaseAll bug : bugDiseaseControlParamList) {
            bugDetail +=bug.getDiseaseName() + ",";
        }
        if(!diseaseDetail.equals("")){
            diseaseDetail= diseaseDetail.substring(0,(diseaseDetail.length()-1));
        }
        if(!bugDetail.equals("")){
            bugDetail= bugDetail.substring(0,(bugDetail.length()-1));
        }
        briefReportData.setDisease(diseaseDetail);
        briefReportData.setInsectPests(bugDetail);
        List<BriefingDiseaseAll> diseaseAllList = new ArrayList<>();
        diseaseAllList.addAll(diseaseControlParamList);
        diseaseAllList.addAll(bugDiseaseControlParamList);
        briefReportData.setDiseaseAllList(diseaseAllList);
    }



    public static void createBriefReport(BriefingReportParam briefingReportParam,BriefingTemplate briefingTemplate,BriefReportData briefReportData){
        Integer Year = LocalDate.now().getYear();
        String outPutFloder = briefReportData.getRegionCode()+File.separator+Year;//简报输出地址  区域code层级 加上当前年份
        String pcReportName = "";//pc端模板名称
        String mobileReportName = "";//mobile端模板名称
        String type = "";
        if(briefingReportParam.getLevel()>4){//生成区县简报
            pcReportName = briefingTemplate.getTemplateCounty();//pc端
            mobileReportName = briefingTemplate.getTemplateMobileCounty();//mobile端
            type = "county";

        }else {//生成省市简报
            pcReportName = briefingTemplate.getTemplateFilePosition();//pc端
            mobileReportName = briefingTemplate.getTemplateMobileFilePosition();//mobile端
            type = "provice";
        }
        //输出地址加文件名   文件名生成规则需要定义
        String outPutPcName=File.separator+"PC-"+getFileName(type)+".html";
        String outPutMobileName=File.separator+"MOBILE-"+getFileName(type)+".html";
        createHtml(pcReportName,outPutFloder,outPutPcName,briefReportData);//生成pc端简报
        createHtml(mobileReportName,outPutFloder,outPutMobileName,briefReportData);//生成mobile端简报
        briefingReportParam.setPcBriefReportUrl(CephUtils.getCephBrief() + File.separator +outPutFloder+outPutPcName);
        briefingReportParam.setMobileBriefReportUrl(CephUtils.getCephBrief() + File.separator + outPutFloder+outPutMobileName);
    }

    public static void createHtml(String ftlName, String outPutFloder,String outPutName, BriefReportData briefReportData){//模板文件名  输出html地址 填充模板数据
        FileWriter out = null;
        try {
            //  String ftlPath = "c:\\";//模板文件地址
            // 通过FreeMarker的Confuguration读取相应的模板文件
            //Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
            //configuration.setDirectoryForTemplateLoading(new File(ftlPath));
            // 设置默认字体
           // configuration.setDefaultEncoding("utf-8");

           //Template template =configuration.getTemplate(ftlName); //framemaker.ftl为要装载的模板
            // 获取模板
            //Template template = configuration.getTemplate(ftlName);
            Template template = briefingReportService.getConfiguration().getTemplate(ftlName);
            File docFloder = new File(outPutPath+File.separator+outPutFloder);// \\192.168.1.223\mnt\data\briefReport      \区域code\年份
            if(!docFloder.exists()){
                docFloder.mkdirs();//如果文件夹不存在则创建一个
                //docFile.createNewFile();
            }

            out = new FileWriter(docFloder+outPutName);

            //模板输出静态文件
            template.process(briefReportData, out);
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            if(null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 构建html文件名
     * @param type
     * @return
     * @version<1> 2018-07-20 wl : 生成html简报的文件名</1>
     */
    private static String getFileName(String type){//省市或者区县
        StringBuffer buffer = new StringBuffer();
        buffer.append(type+"-");
        buffer.append((DateUtil.dateToString(new Date(),"yyyyMMddHHmmss")));
        return buffer.toString();
    }

public static void main (String args[]) throws IOException {
        StringBuilder sb = new StringBuilder();
        String s ="";
        BufferedReader br = new BufferedReader(new FileReader("c:\\CHN\\CHN-HU\\new.html"));

        while( (s = br.readLine()) != null) {
            sb.append(s + "\n");
        }

        br.close();
        String str = sb.toString();
        System.out.print(str);

    /*FileWriter out = null;
    try {
        String ftlPath = "c:\\";
        // 通过FreeMarker的Confuguration读取相应的模板文件
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDirectoryForTemplateLoading(new File(ftlPath));
        // 设置默认字体
        configuration.setDefaultEncoding("utf-8");
        File f2 = new File("c:\\CHN\\CHN-HU\\");//    \\192.168.1.210\mnt\data\report\区域code\年份\
        if (!f2.exists()) {
            f2.mkdirs();
        }
        // 获取模板
        Template template = configuration.getTemplate("countyBriefReport.ftl");
        String outFilePath = "c:\\CHN\\CHN-HU\\new.html";
        File docFile = new File(outFilePath);
        if(docFile.exists()){
            docFile.createNewFile();
        }
        out = new FileWriter(docFile);

        BriefReportData briefReportData=new BriefReportData();
        briefReportData.setGrowthPeriodName("物候期测试");
        briefReportData.setRegionName("武汉市");

        //模板输出静态文件
        template.process(briefReportData, out);
    } catch (TemplateNotFoundException e) {
        e.printStackTrace();
    } catch (MalformedTemplateNameException e) {
        e.printStackTrace();
    } catch (ParseException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (TemplateException e) {
        e.printStackTrace();
    } finally {
        if(null != out) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
}

    /**
     * 组装选择日期，往过去退指定月份，同时获取去年年份，最后填入TemplateReporterParam中，供查询使用。
     *
     * @param briefingReportParam 模板参数
     * @param endDate               结束日期
     * @version <1> 2018-04-12 lxy： Create
     */
    public void wrapLastMonthDateTime(BriefingReportParam briefingReportParam, String endDate) {
        LocalDate endLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));//endDate转换LocalDate类型
        LocalDate startLocalDate = endLocalDate.minus(BriefingReporterUtils.BACK_MONTH, ChronoUnit.MONTHS);//将结束时间，往过去退指定月份的时间。

        String startDateMonth = startLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));//开始时间（转字符串）
        String endDateMonth = endLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));//结束时间（转字符串）

        String startDateMonthNoYear = startLocalDate.format(DateTimeFormatter.ofPattern("MM-dd"));//开始时间（无年份）
        String endDateMonthNoYear = endLocalDate.format(DateTimeFormatter.ofPattern("MM-dd"));//结束时间（无年份）

        int spanYearForMonth = endLocalDate.getYear()-startLocalDate.getYear();//计算按月的时间段，跨年间隔数
        briefingReportParam.setSpanYearForMonth(spanYearForMonth);//计算选择的时间段，跨年间隔数,设置到模板参数中

        //设置月份开始时间、月份结束时间、设置月份开始时间(不带年)、设置月份开始时间(不带年)
        briefingReportParam.setStartDateMonth(startDateMonth); //设置月份开始时间
        briefingReportParam.setEndDateMonth(endDateMonth);//设置月份结束时间
        briefingReportParam.setStartDateMonthNoYear(startDateMonthNoYear);//设置月份开始时间(不带年)
        briefingReportParam.setEndDateMonthNoYear(endDateMonthNoYear);//设置月份开始时间(不带年)

        //获取选中日期去年的年份
        endLocalDate = endLocalDate.minus(1, ChronoUnit.YEARS);
        Integer lastYear = endLocalDate.getYear();
        briefingReportParam.setLastYear(lastYear);//放入TemplateReporterParam中,供查询使用
    }

    /**
     * 根据周雨量获取湿度
     *
     * @param humidities 湿度列表
     * @param trrmRegion 区域雨量统计信息
     * @version <1> 2018-04-12 lxy： Create
     */
    public void calculateHumidity(List<BriefingHumidity> humidities, TrrmStatisticsMultRegionNew trrmRegion) {
        for (BriefingHumidity humidity : humidities) {
            Integer start = humidity.getRangeStart();//开始区间
            Integer end = humidity.getRangeEnd();//结束区间
            Double trrmTotal = trrmRegion.getTrrmTotal();//获得周雨量
            if (start != null && end != null) {
                if (trrmTotal > new Double(start) && trrmTotal <= new Double(end)) {
                    trrmRegion.setAirHumidity(humidity.getHumName());//设置湿度名称
                }
            } else if (start != null) {
                if (trrmTotal > new Double(start)) {
                    trrmRegion.setAirHumidity(humidity.getHumName());//设置湿度名称
                }
            }
            //默认情况
            if(trrmTotal == 0.0){
                trrmRegion.setAirHumidity("较小");//如果雨量信息为0，表示湿度较小
            }
        }

    }

    /**
     * 根据距历史同期比列获取墒情（（当月雨量-历史同月雨量均值）/历史同月雨量均值）
     *
     * @param soilMoistures 墒情信息
     * @param trrmRegion    区域雨量统计信息
     * @version <1> 2018-04-12 lxy： Create
     */
    public void calculateSoilMoisture(List<BriefingSoilMoisture> soilMoistures, TrrmStatisticsMultRegionNew trrmRegion) {
        for (BriefingSoilMoisture soilMoisture : soilMoistures) {
            Integer start = soilMoisture.getAvgPercentageStart();//开始区间
            Integer end = soilMoisture.getAvgPercentageEnd();//结束区间
            Double percentHistoryYear = trrmRegion.getPercentMonthHistoryYear(); //获得月雨量 距历史同期比列
            if (start != null && end != null && percentHistoryYear !=null) {
                if (percentHistoryYear > new Double(start) && percentHistoryYear <= new Double(end)) {
                    trrmRegion.setSoilMoisture(soilMoisture.getSoidName());//设置墒情名称
                }
            } else if (start != null && percentHistoryYear !=null) {
                if (percentHistoryYear > new Double(start)) {
                    trrmRegion.setSoilMoisture(soilMoisture.getSoidName());//设置墒情名称
                }
            } else if (end != null && percentHistoryYear !=null) {
                if (percentHistoryYear <= new Double(end)) {
                    trrmRegion.setSoilMoisture(soilMoisture.getSoidName());//设置墒情名称
                }
            }
        }
    }

    /**
     * 组装开始时间、结束时间
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @version <1> 2018-04-12 lxy： Create
     */
    public void wrapperStartAndEndDate(String startDate, String endDate, BriefingReportParam briefingReportParam) {
        //去掉年份的开始时间、结束时间
        String startDateNoYear = null;//模板数据字段
        String endDateNoYear = null;//模板数据字段

        //转换成LocalDate
        LocalDate startLocalDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //获得去掉年份的开始时间、结束时间
        startDateNoYear = startLocalDate.format(DateTimeFormatter.ofPattern("MM-dd"));//入简报模板
        endDateNoYear = endLocalDate.format(DateTimeFormatter.ofPattern("MM-dd"));//入简报模板

        //相等表示：选择的是同一天，重新组装开始时间(从选择的时间往过去退7天)
        if (startDate.equals(endDate)) {
            //转换成LocalDate
            startLocalDate = endLocalDate.minus(7, ChronoUnit.DAYS);//组装开始时间(从选择的时间往过去退7天)
            //重新获得开始时间(字符串类型）
            startDate = startLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            //获得去掉年份的开始时间、结束时间
            startDateNoYear = startLocalDate.format(DateTimeFormatter.ofPattern("MM-dd"));
        }

        //计算选择的时间段，跨年间隔数
        int spanYearForWeek = endLocalDate.getYear()-startLocalDate.getYear();
        briefingReportParam.setSpanYearForWeek(spanYearForWeek);//计算选择的时间段，跨年间隔数,设置到模板参数中

        /**
         * 将相关开始时间、结束时间设置到TemplateReporterParam中，供查询使用
         */
        briefingReportParam.setStartDate(startDate);//重新设置开始日期
        briefingReportParam.setEndDate(endDate);//重新设置结束日期
        briefingReportParam.setStartDateNoYear(startDateNoYear);//开始时间（不带年份）
        briefingReportParam.setEndDateNoYear(endDateNoYear);//结束时间（不带年份）
        briefingReportParam.setStartLocalDate(startLocalDate);//开始时间（LocalDate）
        briefingReportParam.setEndLocalDate(endLocalDate);//结束时间（LocalDate）
    }

}