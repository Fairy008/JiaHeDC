package com.jh.briefing.util;

import com.alibaba.druid.util.StringUtils;
import com.jh.briefing.entity.Humidity;
import com.jh.briefing.entity.SoilMoisture;
import com.jh.briefing.entity.TemplateModule;
import com.jh.briefing.model.*;
import com.jh.briefing.service.impl.TemplateReporterService;
import com.jh.vo.ResultMessage;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * 简报服务类辅助公共方法类
 * Created by lxy on 2018/4/18.
 */
public class TemplateReporterUtils {
    private static Integer BACK_MONTH = 2;//想前倒退多少个月
    private final TemplateReporterService templateReporterService;

    public TemplateReporterUtils(TemplateReporterService templateReporterService) {
        this.templateReporterService = templateReporterService;
    }

    /**
     * 作物病情、虫害防治措施,入模板
     *
     * @param templateReporterParam 查询参数
     * @param reporterData          模板参数
     * @version <1> 2018-04-12 lxy： Create
     */
    public void setDiseaseInfoInoTemplate(TemplateReporterParam templateReporterParam, ReporterData reporterData) {
        //获得对应的物候期编号
        Integer growthId = templateReporterParam.getGrowthId();
        List<DiseaseControlParam> diseaseControlParamList = templateReporterService.getDiseaseControlService().queryCropsDiseaseByGrowthId(growthId,1);//根据物候期查询对应的病情
        List<DiseaseControlParam> bugDiseaseControlParamList = templateReporterService.getDiseaseControlService().queryCropsDiseaseByGrowthId(growthId,2);//根据物候期查询对应的虫害
        reporterData.setDiseaseControls(diseaseControlParamList);//入模板
        reporterData.setBugDiseaseControls(bugDiseaseControlParamList);//入模板
    }

    /**
     * 设置雨量统计新
     *
     * @param templateReporterParam 查询参数
     * @param endDate               结束日期
     * @return 返回
     * @version <1> 2018-04-12 lxy： Create
     */
    public TrrmStatistics getTrrmStatistics(TemplateReporterParam templateReporterParam, String endDate) {
        /*
         * 雨量统计模板
         */
        TrrmStatistics trrmStatistics = new TrrmStatistics(); //定义雨量统计模板
        Integer countRainDays = templateReporterService.countRainDays(templateReporterParam);//降雨天数
        //入雨量统计模板
        trrmStatistics.setRainDays(countRainDays);
        TrrmRegionMaxDay trrmRegionMaxDay = templateReporterService.queryMaxTrrmRegionInDay(templateReporterParam);//周最大降雨量、区域信息
        if (trrmRegionMaxDay != null) {
            String rainStormLevel = rainStormLevel(trrmRegionMaxDay.getMaxDayTrrm());//获取暴风雨级别
            trrmRegionMaxDay.setRainStormLevel(rainStormLevel);//设置暴风雨级别
            trrmStatistics.setTrrmRegionMaxDay(trrmRegionMaxDay);//入雨量统计模板
        }

         /*
         * 周降雨量总和最大、最小相关信息
         */
        List<TrrmStatisticsMultRegion> sanQingList = new ArrayList<>();//获得墒情比较严重的，历史同期60天的降雨量比percentMonthHistoryYear>40的，就放入此容器中
        List<TrrmStatisticsMultRegion> ganHanList = new ArrayList<>(); //获得干旱比较严重的，历史同期60天的降雨量比percentMonthHistoryYear<-40的，就放入此容器中

        //组装选择日期，往过去退指定月份，同时获取去年年份，最后填入TemplateReporterParam中，供查询使用。
        wrapLastMonthDateTime(templateReporterParam, endDate);
        //按照区域，获取区域本周降雨量总和信息列表
        List<TrrmStatisticsMultRegion> trrmStatisticsList = templateReporterService.queryTrrmStatisticsForWeek(templateReporterParam);
        if(trrmStatisticsList.size() == 0){
            Integer level = templateReporterParam.getLevel();
            if(level < 4){//级别在省、市级别，在没有数据的情况下，就读省、市当前级别，不在作为父级别去读取子级别
                level = 4;//这里设置4，是因为在mapper查询中，level=4的时候，才读本级别，不在作为父级别来获取子级别
            }
            templateReporterParam.setLevel(level);
            trrmStatisticsList = templateReporterService.queryTrrmStatisticsForWeek(templateReporterParam);
        }

        if (trrmStatisticsList.size() > 0) {//有数据，才入模板
            TrrmStatisticsMultRegion minTrrmRegion = trrmStatisticsList.get(0); //获取最小降雨量、相关区域
            trrmStatistics.setMinTrrmRegion(minTrrmRegion); //入雨量统计模板
            TrrmStatisticsMultRegion maxTrrmRegion = trrmStatisticsList.get(trrmStatisticsList.size() - 1); //获取最大降雨量，相关区域
            trrmStatistics.setMaxTrrmRegion(maxTrrmRegion);//入雨量统计模板

            List<Humidity> humidities = templateReporterService.getCropsGrowthPeriodService().queryAllHumidity();//查询所有的湿度信息，然后根据雨量计算湿度
            List<SoilMoisture> soilMoistures = templateReporterService.getCropsGrowthPeriodService().queryAllSoilMoisture();//获取所有墒情
            for (TrrmStatisticsMultRegion trrmRegion : trrmStatisticsList) {
                calculateHumidity(humidities, trrmRegion); //循环设置湿度
                calculateSoilMoisture(soilMoistures, trrmRegion);//循环设置墒情信息
            }
            //按照周雨量降序
            Collections.sort(trrmStatisticsList, (o1, o2) -> {
                if (o1.getTrrmTotal() - o2.getTrrmTotal() > 0) {
                    return -1;
                } else if (o1.getTrrmTotal() - o2.getTrrmTotal() < 0) {
                    return 1;
                } else {
                    return 0;
                }
            });

        }else{
            //本期没有雨量，就统计60天的。
            trrmStatisticsList = templateReporterService.queryTrrmStatisticsForMonth(templateReporterParam); //按照区域，获取区域60天降雨量总和信息列表
            if(trrmStatisticsList.size() == 0){
                Integer level = templateReporterParam.getLevel();
                if(level < 4){//级别在省、市级别，在没有数据的情况下，就读省、市当前级别，不在作为父级别去读取子级别
                    level = 4;//这里设置4，是因为在mapper查询中，level=4的时候，才读本级别，不在作为父级别来获取子级别
                }
                templateReporterParam.setLevel(level);
                trrmStatisticsList = templateReporterService.queryTrrmStatisticsForMonth(templateReporterParam); //按照区域，获取区域60天降雨量总和信息列表
            }

            List<Humidity> humidities = templateReporterService.getCropsGrowthPeriodService().queryAllHumidity();//查询所有的湿度信息，然后根据雨量计算湿度
            List<SoilMoisture> soilMoistures = templateReporterService.getCropsGrowthPeriodService().queryAllSoilMoisture();//获取所有墒情,然后计算墒情情况
            for (TrrmStatisticsMultRegion trrmRegion : trrmStatisticsList) {
                calculateHumidity(humidities, trrmRegion); //循环设置湿度
                calculateSoilMoisture(soilMoistures, trrmRegion);//循环设置墒情信息
            }
        }

        //装入墒情和干旱的数据
        for (TrrmStatisticsMultRegion trrmRegion : trrmStatisticsList) {
            if(trrmRegion.getPercentMonthHistoryYear() > 40){//装入墒情的数据
                sanQingList.add(trrmRegion);
            }
            if(trrmRegion.getPercentMonthHistoryYear()<-40){//装入干旱的数据
                ganHanList.add(trrmRegion);
            }
        }

        //煽情排序 升序
        if(sanQingList.size()>0){
            Collections.sort(sanQingList, new TermStatisticsMaltRegionComparator());
            if(sanQingList.size()>1){
                trrmStatistics.setShanOne(sanQingList.get(0)); //设置墒情最轻
                trrmStatistics.setShanTwo(sanQingList.get(sanQingList.size()-1));//设置墒情最重
            }else{
                trrmStatistics.setShanOne(sanQingList.get(0));//只有一個的情况
            }
        }

        //干旱排序 升序
        if(ganHanList.size()>0){
            Collections.sort(ganHanList, new TermStatisticsMaltRegionComparator());
            if(ganHanList.size()>1){
                trrmStatistics.setGanhanOne(ganHanList.get(0));//设置干旱最轻
                trrmStatistics.setGanhanTwo(ganHanList.get(ganHanList.size()-1));//设置干旱最重
            }else{
                trrmStatistics.setGanhanOne(ganHanList.get(0));//只有一個的情况
            }
        }
        trrmStatistics.setTrrmStatisticsRegion(trrmStatisticsList);//入雨量统计模板

        return trrmStatistics;
    }

    /**
     * 根据周雨量获取湿度
     *
     * @param humidities 湿度列表
     * @param trrmRegion 区域雨量统计信息
     * @version <1> 2018-04-12 lxy： Create
     */
    public void calculateHumidity(List<Humidity> humidities, TrrmStatisticsMultRegion trrmRegion) {
        for (Humidity humidity : humidities) {
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
    public void calculateSoilMoisture(List<SoilMoisture> soilMoistures, TrrmStatisticsMultRegion trrmRegion) {
        for (SoilMoisture soilMoisture : soilMoistures) {
            Integer start = soilMoisture.getAvgPercentageStart();//开始区间
            Integer end = soilMoisture.getAvgPercentageEnd();//结束区间
            Double percentHistoryYear = trrmRegion.getPercentMonthHistoryYear(); //获得月雨量 距历史同期比列
            if (start != null && end != null) {
                if (percentHistoryYear > new Double(start) && percentHistoryYear <= new Double(end)) {
                    trrmRegion.setSoilMoisture(soilMoisture.getSoidName());//设置墒情名称
                }
            } else if (start != null) {
                if (percentHistoryYear > new Double(start)) {
                    trrmRegion.setSoilMoisture(soilMoisture.getSoidName());//设置墒情名称
                }
            } else if (end != null) {
                if (percentHistoryYear <= new Double(end)) {
                    trrmRegion.setSoilMoisture(soilMoisture.getSoidName());//设置墒情名称
                }
            }
        }
    }

    /**
     * 设置雨量计算暴风雨级别
     *
     * @param maxTrrm 雨量
     * @version <1> 2018-04-12 lxy： Create
     */
    public String rainStormLevel(Double maxTrrm) {
        String rainStormLevel = "无降水";
        if(maxTrrm == null){
            return "无降水";
        }
        if (maxTrrm <= 0) {
            rainStormLevel = "无降水";
        } else if (maxTrrm > 0 && maxTrrm <= 10) {
            rainStormLevel = "小雨";
        } else if (maxTrrm > 10 && maxTrrm <= 25) {
            rainStormLevel = "中雨";
        } else if (maxTrrm > 25 && maxTrrm <= 50) {
            rainStormLevel = "大雨";
        } else if (maxTrrm > 50 && maxTrrm <= 100) {
            rainStormLevel = "暴雨";
        } else if (maxTrrm > 100 && maxTrrm <= 250) {
            rainStormLevel = "大暴雨";
        } else {
            rainStormLevel = "特大暴雨";
        }
        return rainStormLevel;
    }

    /**
     * 设置地温统计信息
     * @param templateReporterParam 查询参数
     * @return 地温统计信息
     * @version <1> 2018-04-12 lxy： Create
     */
    public TtnStatistics getTtnStatistics(TemplateReporterParam templateReporterParam) {
        /*
         * 周最大、最小日均地温、及区域
         */
        TtnStatistics ttnStatistics = new TtnStatistics(); //定义地温模板信息
        List<TtnRegionTotal> ttnRegionAvgList = templateReporterService.queryTtnByRegionAndDateTime(templateReporterParam); //根据 regionId（区域编号） 和 dateTime （创建时间）查询地温均值信息
        if (ttnRegionAvgList.size() ==  0) {//有数据，才入模板
            Integer level = templateReporterParam.getLevel();//获取区域级别；
            if(level < 4){//级别在省、市级别，在没有数据的情况下，就读省、市当前级别，不在作为父级别去读取子级别
                level = 4;//这里设置4，是因为在mapper查询中，level=4的时候，才读本级别，不在作为父级别来获取子级别
            }
            templateReporterParam.setLevel(level);
            ttnRegionAvgList = templateReporterService.queryTtnByRegionAndDateTime(templateReporterParam); //根据 regionId（区域编号） 和 dateTime （创建时间）查询地温均值信息
        }
        //设置当前地温数据
        if(ttnRegionAvgList != null && ttnRegionAvgList.size()>0){
            TtnRegionTotal ttnAvgMin = ttnRegionAvgList.get(0);//最小日均地温信息
            TtnRegionTotal ttnAvgMax = ttnRegionAvgList.get(ttnRegionAvgList.size() - 1);//最大日均地温信息
            ttnStatistics.setMinTtnAvg(ttnAvgMin);//将最小日均地温信息入地温统计模板
            ttnStatistics.setMaxTtnAvg(ttnAvgMax);//将最大日均地温信息入地温统计模板
            Integer growthId = templateReporterParam.getGrowthId();//获取当前选择的物候期
            //获取作物的生长适宜情况信息
            List<String> instructions = calculatePeriodCondition(ttnRegionAvgList, growthId);
            ttnStatistics.setInstructions(instructions);
        }

        /*
         * 周最大、最小日有效积温信息、及区域(自播种日期到选择日期的有效积温)
         */
        List<TtnRegionTotal> ttnRegionTotalList = templateReporterService.queryTtnTotalByRegionAndDateTime(templateReporterParam);//根据 regionId（区域编号） 和 dateTime （创建时间）查询有效积温值信息
        if(ttnRegionTotalList.size() == 0){
            Integer level = templateReporterParam.getLevel();//获取区域级别；
            if(level < 4){//级别在省、市级别，在没有数据的情况下，就读省、市当前级别，不在作为父级别去读取子级别
                level = 4;//这里设置4，是因为在mapper查询中，level=4的时候，才读本级别，不在作为父级别来获取子级别
            }
            templateReporterParam.setLevel(level);
            ttnRegionTotalList = templateReporterService.queryTtnTotalByRegionAndDateTime(templateReporterParam);//根据 regionId（区域编号） 和 dateTime （创建时间）查询有效积温值信息
        }

        if (ttnRegionTotalList.size() > 0) {//有数据，才入模板
            TtnRegionTotal ttnTotalMin = ttnRegionTotalList.get(0);//最小有效积温信息
            TtnRegionTotal ttnTotalMax = ttnRegionTotalList.get(ttnRegionTotalList.size() - 1); //最大有效积温信息
            ttnStatistics.setMinTtnTotal(ttnTotalMin);//将最小、最大有效积温信息入地温统计模板
            ttnStatistics.setMaxTtnTotal(ttnTotalMax);//将最小、最大有效积温信息入地温统计模板
        }

        return ttnStatistics;
    }

    /**
     * 获取作物的生长适宜情况信息
     * 根据所有区域地温进行求和，再平均，与物候期生长关系数据进行比较。
     *
     * @param ttnRegionAvgList 所有区域的地温平均数据
     * @param growthId         作物编号
     * @return List<String> 返回所有的生长条件信息
     * @version <1> 2018-04-12 lxy： Create
     */
    public List<String> calculatePeriodCondition(List<TtnRegionTotal> ttnRegionAvgList, Integer growthId) {
        double ttnTotal = 0.0d;//统计平均温度总和（有效积温）
        for (TtnRegionTotal ttnAvg : ttnRegionAvgList) {
            ttnTotal += ttnAvg.getTtnAvg();//累加所有平均地温
        }
        BigDecimal ttnTotalBigDecimal = new BigDecimal(ttnTotal);//平均地温总和，转化成BigDecimal类型
        ttnTotalBigDecimal = ttnTotalBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);//向上取整，保留2位
        BigDecimal size = new BigDecimal(ttnRegionAvgList.size()); //获得记录条数,转换成BigDecimal类型
        BigDecimal avgTtnTotal = ttnTotalBigDecimal.divide(size, 2, BigDecimal.ROUND_HALF_UP);//求取平均数，平均地温总和/总记录数

        //获取当前作物期所对应的地温关系信息
        List<GrowthRelativeGroundTempParam> relateTemps = templateReporterService.getGrowthRelativeGroundTempService().queryGrowthRelativeTemp(growthId);

        //装载地温长势关系信息
        List<String> instructionInfos = new ArrayList<String>();
        //循环遍历所有地温关系信息，
        for (GrowthRelativeGroundTempParam tempParam : relateTemps) {
            Integer start = tempParam.getTempRangeStart();//开始区间
            Integer end = tempParam.getTempRangeEnd();//结束区间
            Short ifAvg = tempParam.getIfavg();//是否按平均值比较
            if(ifAvg == null){
                ifAvg = 1;
            }
            if (ifAvg == 1) {//表示 按平均数来比较
                if (start != null && end != null) {
                    if (avgTtnTotal.doubleValue() > new Double(start) && avgTtnTotal.doubleValue() <= end) {
                        instructionInfos.add(tempParam.getInstruction());
                    }
                } else if (start != null) {
                    if (avgTtnTotal.doubleValue() > new Double(start)) {
                        instructionInfos.add(tempParam.getInstruction());
                    }
                } else if (end != null) {
                    if (avgTtnTotal.doubleValue() <= new Double(end)) {
                        instructionInfos.add(tempParam.getInstruction());
                    }
                }
            } else {//按积温判断
                if (start != null && end != null) {
                    if (ttnTotalBigDecimal.compareTo(new BigDecimal(start)) > 0 && ttnTotalBigDecimal.compareTo(new BigDecimal(end)) <= 0) {
                        instructionInfos.add(tempParam.getInstruction());
                    }
                } else if (start != null) {
                    if (ttnTotalBigDecimal.compareTo(new BigDecimal(start)) > 0) {
                        instructionInfos.add(tempParam.getInstruction());
                    }
                } else if (end != null) {
                    if (ttnTotalBigDecimal.compareTo(new BigDecimal(end)) <= 0) {
                        instructionInfos.add(tempParam.getInstruction());
                    }
                }
            }

        }
        return instructionInfos;
    }

    /**
     * freemarker生成静态网页
     * @param reporterData 模板数据
     * @param templateId 模板编号
     * @param isMobileEdition 是否手机版
     * @param level 区域级别
     * @return 静态网页全部内容
     * @version <1> 2018-04-12 lxy： Create
     */
    public String generatorTemplate(ReporterData reporterData, Integer templateId, boolean isMobileEdition, Integer level) {
        try {
            /*
             *  处理模板数据，并返回字符串
             */
            String templateName = "provinceBriefReporter.html";//省市
            if(isMobileEdition){
                templateName = "provinceBriefReporterMobile.html"; //手机端模板文件名
            }
            if(templateId == null){//没有选择模板，可以按区域级别判断
                if(level>3){//表示区县
                    templateName = "countyBriefReporter.html";//区县
                    if(isMobileEdition){
                        templateName = "countyBriefReporterMobile.html"; //手机端模板文件名
                    }
                }
            }else{
                //获取模板数据
                ResultMessage resultMessage = templateReporterService.getTemplateModuleService().findTemplateModuleByTemplateId(templateId);
                Object data = resultMessage.getData();
                if(data != null){
                    //如果模板数据不为空，就获取指定模板的文件名
                    TemplateModule templateModule = (TemplateModule)data;
                    //web端模板文件名
                    templateName = templateModule.getTemplateFilePosition();
                    if(isMobileEdition){
                        templateName = templateModule.getTemplateMobileFilePosition(); //手机端模板文件名
                    }
                    if(StringUtils.isEmpty(templateName)){
                        return null;
                    }
                }
            }
            //模板
            Template template = templateReporterService.getConfiguration().getTemplate(templateName);
            Writer out = new StringWriter();//定义字符串写入流
            template.process(reporterData, out);//将模板内容写入到字符写入流中。
            String result = out.toString();//返回结果数据
            out.close();
            return result;
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 组装选择日期，往过去退指定月份，同时获取去年年份，最后填入TemplateReporterParam中，供查询使用。
     *
     * @param templateReporterParam 模板参数
     * @param endDate               结束日期
     * @version <1> 2018-04-12 lxy： Create
     */
    public void wrapLastMonthDateTime(TemplateReporterParam templateReporterParam, String endDate) {
        LocalDate endLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));//endDate转换LocalDate类型
        LocalDate startLocalDate = endLocalDate.minus(TemplateReporterUtils.BACK_MONTH, ChronoUnit.MONTHS);//将结束时间，往过去退指定月份的时间。

        String startDateMonth = startLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));//开始时间（转字符串）
        String endDateMonth = endLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));//结束时间（转字符串）

        String startDateMonthNoYear = startLocalDate.format(DateTimeFormatter.ofPattern("MM-dd"));//开始时间（无年份）
        String endDateMonthNoYear = endLocalDate.format(DateTimeFormatter.ofPattern("MM-dd"));//结束时间（无年份）

        int spanYearForMonth = endLocalDate.getYear()-startLocalDate.getYear();//计算按月的时间段，跨年间隔数
        templateReporterParam.setSpanYearForMonth(spanYearForMonth);//计算选择的时间段，跨年间隔数,设置到模板参数中

        //设置月份开始时间、月份结束时间、设置月份开始时间(不带年)、设置月份开始时间(不带年)
        templateReporterParam.setStartDateMonth(startDateMonth); //设置月份开始时间
        templateReporterParam.setEndDateMonth(endDateMonth);//设置月份结束时间
        templateReporterParam.setStartDateMonthNoYear(startDateMonthNoYear);//设置月份开始时间(不带年)
        templateReporterParam.setEndDateMonthNoYear(endDateMonthNoYear);//设置月份开始时间(不带年)

        //获取选中日期去年的年份
        endLocalDate = endLocalDate.minus(1, ChronoUnit.YEARS);
        Integer lastYear = endLocalDate.getYear();
        templateReporterParam.setLastYear(lastYear);//放入TemplateReporterParam中,供查询使用
    }

    /**
     * 组装开始时间、结束时间
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @version <1> 2018-04-12 lxy： Create
     */
    public void wrapperStartAndEndDate(String startDate, String endDate, TemplateReporterParam templateReporterParam) {
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
        templateReporterParam.setSpanYearForWeek(spanYearForWeek);//计算选择的时间段，跨年间隔数,设置到模板参数中

        /**
         * 将相关开始时间、结束时间设置到TemplateReporterParam中，供查询使用
         */
        templateReporterParam.setStartDate(startDate);//重新设置开始日期
        templateReporterParam.setEndDate(endDate);//重新设置结束日期
        templateReporterParam.setStartDateNoYear(startDateNoYear);//开始时间（不带年份）
        templateReporterParam.setEndDateNoYear(endDateNoYear);//结束时间（不带年份）
        templateReporterParam.setStartLocalDate(startLocalDate);//开始时间（LocalDate）
        templateReporterParam.setEndLocalDate(endLocalDate);//结束时间（LocalDate）
    }

    /**
     * 根据选中的日期，与当前作物、区域对应的物候期的开始、结束时间比较，获取对应的物候期、播种日期
     *
     * @param endDate                    结束时间
     * @param cropsGrowthPeriodParamList 物候期列表
     * @param templateReporterParam      模板参数
     * @version <1> 2018-04-12 lxy： Create
     */
    public void calculateCropsGrowthName(String endDate, List<CropsGrowthPeriodParam> cropsGrowthPeriodParamList, TemplateReporterParam templateReporterParam) {
        if(cropsGrowthPeriodParamList.size() == 0){
            return;
        }
        String growthStartDate = "";//播种时间  物候期开始时间
        String growthEndDate = "";//物候期结束时间
        String growthName = "";//查询到物候期之后，根据选择的时间，判断在哪个物候期
        //组装结束时间
        LocalDate endLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        for (CropsGrowthPeriodParam cropsGrowthPeriod : cropsGrowthPeriodParamList) {//循环获取所有的生育期数据
            String rangeStart = cropsGrowthPeriod.getRangeStart();//开始区间
            String rangeEnd = cropsGrowthPeriod.getRangeEnd();//结束区间
            Short ifGrowthStart = cropsGrowthPeriod.getIfGrowthStart();//是否为播种期
           // String growthStartDate = "";//播种时间
           // String growthName = "播种期";//查询到物候期之后，根据选择的时间，判断在哪个物候期
            Integer growthId = null;//物候期编号
            Integer currentYear = endLocalDate.getYear();//获取选中时间的年份
            String remark=null;//备注信息

            //将区间时间重新组装成时间格式，开始时间、结束时间
            rangeStart = currentYear + "-" + rangeStart;
            rangeEnd = currentYear + "-" + rangeEnd;

            //组装开始、结束时间
            LocalDate rangeStartDate = LocalDate.parse(rangeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate rangeEndDate = LocalDate.parse(rangeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            /**
             * 判断是跨年
             */
            //开始判断物候期开始时间与结束时间的大小，决定是否跨年
            /*if (rangeStartDate.compareTo(rangeEndDate) > 0) {
                //物候期开始时间大于结束时间，说明开始时间处于跨年段
                rangeStartDate = rangeStartDate.minus(1, ChronoUnit.YEARS);
            }*/

            /*
             * 判断是播种期，
             * 需要获得播种期的日期，这里需要判断，当前选中的日期，与播种期进行比较。
             * 如果 选中的日期 > 播种期，那么就以播种期为主。
             * 如果 选中的日期 < 播种期，那么播种期需要减去一年，
             */
            if (ifGrowthStart == 1) {  //2017 1 1
                //组装播种时间
                growthStartDate = currentYear + "-" + cropsGrowthPeriod.getRangeStart();
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
                //设置播种期 到模板参数中
                templateReporterParam.setGrowthStartDate(growthStartDate);

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
                growthName = cropsGrowthPeriod.getGrowthName();//物候期名称
                growthId = cropsGrowthPeriod.getGrowthId();//物候期编号
                remark = cropsGrowthPeriod.getRemark();//备注信息
                templateReporterParam.setGrowthName(growthName);//设置物候期名称
                templateReporterParam.setGrowthId(growthId);//物候期编号
                templateReporterParam.setRemark(remark);//设置备注信息到模板

            }
        }
        //获取最后一個生育期
        CropsGrowthPeriodParam periodPara = cropsGrowthPeriodParamList.get(cropsGrowthPeriodParamList.size()-1);
        String rangeEnd = periodPara.getRangeEnd();//最后区间
        Integer currentYear = endLocalDate.getYear();//获取选中时间的年份
        String lastGrowthDate =currentYear+"-"+rangeEnd;//最后物候期时间
        LocalDate growthEndDateT = LocalDate.parse(lastGrowthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));//最后的物候期
        if (endLocalDate.compareTo(growthEndDateT) >= 0) {//简报时间比物候期的最后时间大 则物候期的年份+1
            growthEndDateT =growthEndDateT.plus(1,ChronoUnit.YEARS);
        }
        templateReporterParam.setLastGrowthDate(growthEndDateT.toString());//设置物候期最后时间
    }

    /**
     * 根据作物编号获得对应的作物名称
     *
     * @param cropsId 作物编号
     * @return 返回物候期名称
     * @version <1> 2018-04-12 lxy： Create
     */
    public String findCropsNameByDicId(Integer cropsId) {
        String cropsName = "";
//        DictParam dictParam = new DictParam();
//        dictParam.setDictId(cropsId);
//        Object cropsObj = templateReporterService.getCropsGrowthPeriodService().findDictById(dictParam).getData();
//        if (cropsObj != null) {
//            dictParam = (DictParam) cropsObj;
//            cropsName = dictParam.getDataName();//作物名称
//        }
        return cropsName;
    }
    
    /**
     * 墒情干旱比较排序
     * @version <1> 2018-04-12 lxy： Create
     */
    private static class TermStatisticsMaltRegionComparator implements Comparator<TrrmStatisticsMultRegion> {
        @Override
        public int compare(TrrmStatisticsMultRegion o1, TrrmStatisticsMultRegion o2) {
            if(o1.getPercentMonthHistoryYear() - o2.getPercentMonthHistoryYear()>0){
                return 1;
            }else if(o1.getPercentMonthHistoryYear() - o2.getPercentMonthHistoryYear()<0){
                return -1;
            }else{
                return 0;
            }
        }
    }
}