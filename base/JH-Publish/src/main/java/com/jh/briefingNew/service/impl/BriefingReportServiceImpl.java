package com.jh.briefingNew.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.briefingNew.entity.*;
import com.jh.briefingNew.enums.BriefingReportEnum;
import com.jh.briefingNew.mapping.IBriefingReportMapper;
import com.jh.briefingNew.model.*;
import com.jh.briefingNew.service.IBriefingCropsGrowthPeriodService;
import com.jh.briefingNew.service.IBriefingDiseaseAllService;
import com.jh.briefingNew.service.IBriefingReportService;
import com.jh.briefingNew.service.IBriefingTemplateService;
import com.jh.briefingNew.utils.BriefingReporterUtils;
import com.jh.feign.IRegionFeignService;
import com.jh.vo.ResultMessage;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * description:简报生成
 *
 * @version <1> 2018-07-18 wl: Created.
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class BriefingReportServiceImpl implements IBriefingReportService {

    private static Logger logger = LoggerFactory.getLogger(BriefingReportServiceImpl.class);
    //简报服务类公共方法
    private final BriefingReporterUtils briefingReporterUtils = new BriefingReporterUtils(this);
    @Autowired
    private IBriefingCropsGrowthPeriodService briefingCropsGrowthPeriodService;//作物物候期服务

    @Autowired
    private IBriefingDiseaseAllService briefingDiseaseAllService;//病虫害服务

    @Autowired
    private IBriefingTemplateService briefingTemplateService;//简报模板

    @Autowired
    private IBriefingReportMapper briefingReportMapper;

    @Autowired
    private Configuration configuration;//freemarker的模板配置

    @Autowired
    private IRegionFeignService regionFeignService;//引用jh-sys中的regionService

    @Override
    public ResultMessage createReport(BriefingReportParam briefingReportParam) {
        ResultMessage result = new ResultMessage();//定义返回的ResultMessage
        /*
         * 区域编号、区域名称、作物编号、作物名称、开始时间、结束时间
         */
        Integer level = briefingReportParam.getLevel();//区域级别
        Long regionId = briefingReportParam.getRegionId();//区域编号
        String regionName = briefingReportParam.getRegionName();//区域名称
        Integer cropId = briefingReportParam.getCropsId();  //作物编号
        String cropName = briefingReportParam.getCropsName();  //作物名称
        String startDate = briefingReportParam.getStartDate();//开始时间
        String endDate = briefingReportParam.getEndDate();//结束时间

        briefingReporterUtils.wrapperStartAndEndDate(startDate, endDate, briefingReportParam);//组装开始时间、结束时间
        //选择的区域级别不能小于2，只能选择省或省以下的区域
        if(level < 2) {
            return ResultMessage.fail(null,"请选择省或省以下区域");
        }
        /*
         * 根据作物、区域查询指定的物候期，获取物候期名字
         */
        BriefingCropsGrowthPeriodParam briefingCropsGrowthPeriodParam = new BriefingCropsGrowthPeriodParam();
        briefingCropsGrowthPeriodParam.setCropsId(cropId);//作物编号
        briefingCropsGrowthPeriodParam.setRegionId(regionId);//区域编号
        //根据当前区域编号、作物查询对应得生育期数据
        //根据当前区域编号、作物查询对应得生育期数据
        List<BriefingCropsGrowthPeriod> briefingCropsGrowthPeriodParamList = briefingCropsGrowthPeriodService.findGrowthPeriodListByCropsIdAndRegionId(briefingCropsGrowthPeriodParam);
        //循环获取向上区域对应的生育期
        if(briefingCropsGrowthPeriodParamList == null || briefingCropsGrowthPeriodParamList.size() == 0){
            briefingCropsGrowthPeriodParamList = getCropsGrowthPeriodParams(level, regionId, briefingCropsGrowthPeriodParam);
        }
        if(briefingCropsGrowthPeriodParamList == null || briefingCropsGrowthPeriodParamList.size() == 0){
            return ResultMessage.fail(null,"选择的区域作物没有对应的物候期");
        }

      /*  List<BriefingCropsGrowthPeriod> briefingCropsGrowthPeriodParamList = briefingCropsGrowthPeriodService.findGrowthPeriodListByCropsIdAndRegionId(briefingCropsGrowthPeriodParam);
        if(briefingCropsGrowthPeriodParamList == null || briefingCropsGrowthPeriodParamList.size() == 0){
            return ResultMessage.fail(null,"选择的区域作物没有对应的物候期");
        }*/
        BriefReportData briefReportData=new BriefReportData();
        briefReportData.setRegionName(briefingReportParam.getRegionName());
        briefReportData.setBeginDate(briefingReportParam.getStartDate());
        briefReportData.setEndDate(briefingReportParam.getEndDate());
        //计算chinaName和regionCode
        //RegionListParam region=briefingReportMapper.findParentRegion(regionId);
        ResultMessage resultMessage= regionFeignService.queryRegionById(regionId);
        Map<String,Object> initRegion = (Map<String,Object>)resultMessage.getData();
        Map<String,Object> regionMap= new HashMap();
        regionMap.put("chinaName",initRegion.get("chinaName"));
        regionMap.put("regionCode",initRegion.get("regionCode"));
        regionMap=this.codeAndName(Integer.parseInt(initRegion.get("level").toString()),Long.parseLong(initRegion.get("parentId").toString()),regionMap);
        briefReportData.setChinaName(regionMap.get("chinaName").toString());
        briefReportData.setRegionCode(regionMap.get("regionCode").toString());
        briefReportData.setCropsName(cropName);
        //计算作物生育期
       // briefingReporterUtils.calculateCropsGrowthName(endDate, briefingCropsGrowthPeriodParamList, briefReportData,result);
        if(result.isFlag()){//选择的时间不在物候期内
            return result;
        }
        //查询日均地温最低、最高
        briefingReporterUtils.avgTtn(briefingReportParam,briefReportData);
        //查询地温统计图数据
        briefingReporterUtils.statisticsTtn(briefingReportParam,briefReportData);
        //查询降雨总量  降雨同比
        briefingReporterUtils.totalTrmm(briefingReportParam,endDate,briefReportData);
        //查询降雨统计图数据
        briefingReporterUtils.statisticsTrmm(briefingReportParam,briefReportData);
        //病虫害防治
        briefingReporterUtils.setDiseaseInfoInoTemplate(briefingReportParam,briefReportData);
        //查询简报模板
        BriefingTemplate briefingTemplate=briefingTemplateService.findBriefingTemplateByType(BriefingReportEnum.CHART_TEMPLATE.getValue());
        briefingReporterUtils.createBriefReport(briefingReportParam,briefingTemplate,briefReportData);
        return ResultMessage.success(briefingReportParam);
    }


    @Override
    public ResultMessage queryReport(BriefingReportParam briefingReportParam) {
        ResultMessage result = new ResultMessage();//定义返回的ResultMessage
        /*
         * 区域编号、区域名称、作物编号、作物名称、开始时间、结束时间
         */
        Integer level = briefingReportParam.getLevel();//区域级别
        Long regionId = briefingReportParam.getRegionId();//区域编号
        String regionName = briefingReportParam.getRegionName();//区域名称
        Integer cropId = briefingReportParam.getCropsId();  //作物编号
        String cropName = briefingReportParam.getCropsName();  //作物名称
        String startDate = briefingReportParam.getStartDate();//开始时间
        String endDate = briefingReportParam.getEndDate();//结束时间

        briefingReporterUtils.wrapperStartAndEndDate(startDate, endDate, briefingReportParam);//组装开始时间、结束时间
        //选择的区域级别不能小于2，只能选择省或省以下的区域
        if(level < 2) {
            return ResultMessage.fail(null,"请选择省或省以下区域");
        }
        /*
         * 根据作物、区域查询指定的物候期，获取物候期名字
         */
        BriefingCropsGrowthPeriodParam briefingCropsGrowthPeriodParam = new BriefingCropsGrowthPeriodParam();
        briefingCropsGrowthPeriodParam.setCropsId(cropId);//作物编号
        briefingCropsGrowthPeriodParam.setRegionId(regionId);//区域编号
        //根据当前区域编号、作物查询对应得生育期数据
        //根据当前区域编号、作物查询对应得生育期数据
        List<BriefingCropsGrowthPeriod> briefingCropsGrowthPeriodParamList = briefingCropsGrowthPeriodService.findGrowthPeriodListByCropsIdAndRegionId(briefingCropsGrowthPeriodParam);
        //循环获取向上区域对应的生育期
        if(briefingCropsGrowthPeriodParamList == null || briefingCropsGrowthPeriodParamList.size() == 0){
            briefingCropsGrowthPeriodParamList = getCropsGrowthPeriodParams(level, regionId, briefingCropsGrowthPeriodParam);
        }
        if(briefingCropsGrowthPeriodParamList == null || briefingCropsGrowthPeriodParamList.size() == 0){
            return ResultMessage.fail(null,"选择的区域作物没有对应的物候期");
        }

       /* List<BriefingCropsGrowthPeriod> briefingCropsGrowthPeriodParamList = briefingCropsGrowthPeriodService.findGrowthPeriodListByCropsIdAndRegionId(briefingCropsGrowthPeriodParam);
        if(briefingCropsGrowthPeriodParamList == null || briefingCropsGrowthPeriodParamList.size() == 0){
            return ResultMessage.fail(null,"选择的区域作物没有对应的物候期");
        }*/
        BriefReportData briefReportData=new BriefReportData();
        briefReportData.setRegionName(briefingReportParam.getRegionName());
        briefReportData.setBeginDate(briefingReportParam.getStartDate());
        briefReportData.setEndDate(briefingReportParam.getEndDate());
        //计算chinaName和regionCode
        //RegionListParam region=briefingReportMapper.findParentRegion(regionId);
        ResultMessage resultMessage= regionFeignService.queryRegionById(regionId);
        Map<String,Object> initRegion = (Map<String,Object>)resultMessage.getData();
        Map<String,Object> regionMap= new HashMap();
        regionMap.put("chinaName",initRegion.get("chinaName"));
        regionMap.put("regionCode",initRegion.get("regionCode"));
        regionMap=this.codeAndName(Integer.parseInt(initRegion.get("level").toString()),Long.parseLong(initRegion.get("parentId").toString()),regionMap);
        briefReportData.setChinaName(regionMap.get("chinaName").toString());
        briefReportData.setRegionCode(regionMap.get("regionCode").toString());
        briefReportData.setCropsName(cropName);
        //计算作物生育期
         briefingReporterUtils.calculateCropsGrowthName(endDate, briefingCropsGrowthPeriodParamList, briefReportData,briefingReportParam,result);
        if(result.isFlag()){//选择的时间不在物候期内
            return result;
        }
        //查询日均地温最低、最高
        briefingReporterUtils.avgTtn(briefingReportParam,briefReportData);
        //查询地温统计图数据
        briefingReporterUtils.statisticsTtn(briefingReportParam,briefReportData);
        //查询降雨总量  降雨同比
        briefingReporterUtils.totalTrmm(briefingReportParam,endDate,briefReportData);
        //查询降雨统计图数据
        briefingReporterUtils.statisticsTrmm(briefingReportParam,briefReportData);
        //病虫害防治
        briefingReporterUtils.setDiseaseInfoInoTemplate(briefingReportParam,briefReportData);
        //查询简报模板
      /*  BriefingTemplate briefingTemplate=briefingTemplateService.findBriefingTemplateByType(BriefingReportEnum.CHART_TEMPLATE.getValue());
        briefingReporterUtils.createBriefReport(briefingReportParam,briefingTemplate,briefReportData);*/
        return ResultMessage.success(briefReportData);
    }

    @Override
    public ResultMessage queryReportNew(BriefingReportParam briefingReportParam,String isTrrm) {
        ResultMessage result = new ResultMessage();//定义返回的ResultMessage
        /*
         * 区域编号、区域名称、作物编号、作物名称、开始时间、结束时间
         */
        Integer level = briefingReportParam.getLevel();//区域级别
        Long regionId = briefingReportParam.getRegionId();//区域编号
        String regionName = briefingReportParam.getRegionName();//区域名称
        Integer cropId = briefingReportParam.getCropsId();  //作物编号
        String cropName = briefingReportParam.getCropsName();  //作物名称
        String startDate = briefingReportParam.getStartDate();//开始时间
        String endDate = briefingReportParam.getEndDate();//结束时间

        briefingReporterUtils.wrapperStartAndEndDate(startDate, endDate, briefingReportParam);//组装开始时间、结束时间
        //选择的区域级别不能小于2，只能选择省或省以下的区域
        if(level < 2) {
            return ResultMessage.fail(null,"请选择省或省以下区域");
        }
        /*
         * 根据作物、区域查询指定的物候期，获取物候期名字
         */
        BriefingCropsGrowthPeriodParam briefingCropsGrowthPeriodParam = new BriefingCropsGrowthPeriodParam();
        briefingCropsGrowthPeriodParam.setCropsId(cropId);//作物编号
        briefingCropsGrowthPeriodParam.setRegionId(regionId);//区域编号
        //根据当前区域编号、作物查询对应得生育期数据
        //根据当前区域编号、作物查询对应得生育期数据
        List<BriefingCropsGrowthPeriod> briefingCropsGrowthPeriodParamList = briefingCropsGrowthPeriodService.findGrowthPeriodListByCropsIdAndRegionId(briefingCropsGrowthPeriodParam);
        //循环获取向上区域对应的生育期
        if(briefingCropsGrowthPeriodParamList == null || briefingCropsGrowthPeriodParamList.size() == 0){
            briefingCropsGrowthPeriodParamList = getCropsGrowthPeriodParams(level, regionId, briefingCropsGrowthPeriodParam);
        }
        if(briefingCropsGrowthPeriodParamList == null || briefingCropsGrowthPeriodParamList.size() == 0){
            return ResultMessage.fail(null,"选择的区域作物没有对应的物候期");
        }

       /* List<BriefingCropsGrowthPeriod> briefingCropsGrowthPeriodParamList = briefingCropsGrowthPeriodService.findGrowthPeriodListByCropsIdAndRegionId(briefingCropsGrowthPeriodParam);
        if(briefingCropsGrowthPeriodParamList == null || briefingCropsGrowthPeriodParamList.size() == 0){
            return ResultMessage.fail(null,"选择的区域作物没有对应的物候期");
        }*/
        BriefReportData briefReportData=new BriefReportData();
        briefReportData.setRegionName(briefingReportParam.getRegionName());
        briefReportData.setBeginDate(briefingReportParam.getStartDate());
        briefReportData.setEndDate(briefingReportParam.getEndDate());
        //计算chinaName和regionCode
        //RegionListParam region=briefingReportMapper.findParentRegion(regionId);
        ResultMessage resultMessage= regionFeignService.queryRegionById(regionId);
        Map<String,Object> initRegion = (Map<String,Object>)resultMessage.getData();
        Map<String,Object> regionMap= new HashMap();
        regionMap.put("chinaName",initRegion.get("chinaName"));
        regionMap.put("regionCode",initRegion.get("regionCode"));
        regionMap=this.codeAndName(Integer.parseInt(initRegion.get("level").toString()),Long.parseLong(initRegion.get("parentId").toString()),regionMap);
        briefReportData.setChinaName(regionMap.get("chinaName").toString());
        briefReportData.setRegionCode(regionMap.get("regionCode").toString());
        briefReportData.setCropsName(cropName);
        //计算作物生育期
        briefingReporterUtils.calculateCropsGrowthName(endDate, briefingCropsGrowthPeriodParamList, briefReportData,briefingReportParam,result);
        if(result.isFlag()){//选择的时间不在物候期内
            return result;
        }
        if("Y".equals(isTrrm)){
            //查询降雨总量  降雨同比
            briefingReporterUtils.totalTrmm(briefingReportParam,endDate,briefReportData);
        }else{
            //查询日均地温最低、最高
            briefingReporterUtils.avgTtn(briefingReportParam,briefReportData);
            //查询地温统计图数据
            briefingReporterUtils.statisticsTtn(briefingReportParam,briefReportData);
            //查询降雨统计图数据
            briefingReporterUtils.statisticsTrmm(briefingReportParam,briefReportData);
            //病虫害防治
            briefingReporterUtils.setDiseaseInfoInoTemplate(briefingReportParam,briefReportData);
        }
        return ResultMessage.success(briefReportData);
    }

    /**
     *  根据 regionId（区域编号） 和 dateTime （创建时间）查询地温均值信息和对应得区域
     * @param briefingReportParam 查询参数
     * @return List<TtnRegionTotal>
     */
    @Override
    public List<TtnRegionAvg> queryTtnByRegionAndDateTime(BriefingReportParam briefingReportParam) {
        return briefingReportMapper.queryTtnByRegionAndDateTime(briefingReportParam);
    }

    /**
     *  根据 regionId（区域编号） 和 dateTime （创建时间）查询有效积温和对应的区域
     * @param briefingReportParam 查询参数
     * @return List<TtnRegionAvg>
     */
    @Override
    public List<TtnRegionAvg> queryTtnTotalByRegionAndDateTime(BriefingReportParam briefingReportParam) {
        return briefingReportMapper.queryTtnTotalByRegionAndDateTime(briefingReportParam);
    }

    @Override
    public List<chartDatas> queryTtnStatistics(BriefingReportParam briefingReportParam) {
        return briefingReportMapper.queryTtnStatistics(briefingReportParam);
    }

    @Override
    public List<chartDatas> queryTrmmStatistics(BriefingReportParam briefingReportParam) {
        return briefingReportMapper.queryTrmmStatistics(briefingReportParam);
    }

    @Override
    public PageInfo<BriefingReport> findByPage(BriefingReportParam briefingReportParam) {
        PageHelper.startPage(briefingReportParam.getPage(), briefingReportParam.getRows());
        List<BriefingReport> list = briefingReportMapper.queryByPage(briefingReportParam);
        return new PageInfo<BriefingReport>(list);
    }

    /**
     * 查询指定时间范围，周雨量信息
     * @param briefingReportParam
     * @version <1> 2018-07-24 wl： Created.
     * @return
     */
    @Override
    public List<TrrmStatisticsMultRegionNew> queryTrrmStatisticsForWeek(BriefingReportParam briefingReportParam) {
        List<TrrmStatisticsMultRegionNew> trmmList = briefingReportMapper.queryTrrmStatisticsForWeek(briefingReportParam);
        Long regionId = briefingReportParam.getRegionId();//区域id
        LocalDate startDate = LocalDate.parse(briefingReportParam.getStartDateMonth());//开始日期
        LocalDate endDate = LocalDate.parse(briefingReportParam.getEndDate());//结束日期
        List<BriefingReportParam> params = new ArrayList<BriefingReportParam>();
        //求最近十年的改区域下的各个区域的降雨量的均值
        for (int i=1;i<=10;i++){
            BriefingReportParam param = new BriefingReportParam();
            param.setRegionId(regionId);
            param.setStartDate(startDate.minusYears(i).toString());
            param.setEndDate(endDate.minusYears(i).toString());
            params.add(param);
        }
        List<TrrmStatisticsMultRegionNew> avgTrrmMonthList = briefingReportMapper.queryAvgTrrmMonthHistory(params);
        //按照区域id分组
        Map<Long, List<TrrmStatisticsMultRegionNew>> groupByRegionMap =
                avgTrrmMonthList.stream().collect(Collectors.groupingBy(TrrmStatisticsMultRegionNew::getRegionId));


        List<TrrmStatisticsMultRegionNew> avgHistoryList = new ArrayList<TrrmStatisticsMultRegionNew>();

        //按照区域分组后，将各个区域的降雨总量求和处以条目数，得到所有10年内的平均降雨量
        for (Long key:groupByRegionMap.keySet()){
            List<TrrmStatisticsMultRegionNew> trmmRegionList = groupByRegionMap.get(key);

            Iterator<TrrmStatisticsMultRegionNew> iterator = trmmRegionList.iterator();
            while (iterator.hasNext()){
                TrrmStatisticsMultRegionNew regionNew = iterator.next();
                if (null == regionNew.getTrrmTotal())   iterator.remove();
            }

            int regionSize = trmmRegionList.size();
            Double trmmSum = trmmRegionList.stream().mapToDouble(TrrmStatisticsMultRegionNew::getTrrmTotal).sum();
            Double avgTrmm = trmmSum/regionSize;
            TrrmStatisticsMultRegionNew regionHistory = new TrrmStatisticsMultRegionNew();
            regionHistory.setRegionId(key);
            regionHistory.setAvgTrrmMonthHistory(avgTrmm);
            avgHistoryList.add(regionHistory);
        }

        for (int i=0;i<trmmList.size();i++){
            for (int j=0;j<avgHistoryList.size();j++){
                if (trmmList.get(i).getRegionId().equals(avgHistoryList.get(j).getRegionId())){
                    Double avgTrrmMonthHistory = avgHistoryList.get(j).getAvgTrrmMonthHistory();
                    Double trrmTotalMonth = trmmList.get(i).getTrrmTotalMonth();
                    Double percentMonthHistoryYear = 0D;
                    if (avgTrrmMonthHistory.equals(0D)){
                        percentMonthHistoryYear=100D;
                    }else {
                        percentMonthHistoryYear =  (trrmTotalMonth - avgTrrmMonthHistory )/avgTrrmMonthHistory * 100;
                    }
                    BigDecimal bd = new BigDecimal(percentMonthHistoryYear);
                    percentMonthHistoryYear = bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    //设置月度降雨量
                    trmmList.get(i).setAvgTrrmMonthHistory(avgTrrmMonthHistory);
                    //该区域选定时间段相对该时间段内降雨十年均值的增长/减少百分比
                    trmmList.get(i).setPercentMonthHistoryYear(percentMonthHistoryYear);
                }
            }
        }

        return trmmList;
    }


    /**
     * 查询指定时间范围，月雨量信息
     * @param briefingReportParam
     * @return
     * @version <1> 2018-07-24 wl： Created.
     */
    @Override
    public List<TrrmStatisticsMultRegionNew> queryTrrmStatisticsForMonth(BriefingReportParam briefingReportParam){
        return briefingReportMapper.queryTrrmStatisticsForMonth(briefingReportParam);
    }

    @Override
    public List<BriefingHumidity> queryAllHumidity() {
        return briefingReportMapper.queryAllHumidity();
    }

    @Override
    public List<BriefingSoilMoisture> queryAllSoilMoisture() {
        return briefingReportMapper.queryAllSoilMoisture();
    }

    @Override
    public ResultMessage batchCreateReport(BriefingReportParam briefingReportParam,BriefReportData briefReportData) {
        ResultMessage result = new ResultMessage();//定义返回的ResultMessage
        /*
         * 区域编号、区域名称、作物编号
         */
        Integer level = briefingReportParam.getLevel();//区域级别
        Integer cropsId = briefingReportParam.getCropsId();//作物编号
        Long regionId = briefingReportParam.getRegionId();//区域编号
        String startDate = briefingReportParam.getStartDate();//开始时间
        String endDate = briefingReportParam.getEndDate();//结束时间
        //选择的区域级别不能小于2，只能选择省或省一下的区域
        if(level < 2) {
            return ResultMessage.fail(null,"请选择省或省以下区域");
        }
         /*
         * 根据作物、区域查询指定的物候期，获取物候期名字
         */
        briefingReporterUtils.wrapperStartAndEndDate(startDate, endDate, briefingReportParam);//组装开始时间、结束时间
        //选择的区域级别不能小于2，只能选择省或省以下的区域
        if(level < 2) {
            return ResultMessage.fail(null,"请选择省或省以下区域");
        }
        /*
         * 根据作物、区域查询指定的物候期，获取物候期名字
         */
        BriefingCropsGrowthPeriodParam briefingCropsGrowthPeriodParam = new BriefingCropsGrowthPeriodParam();
        briefingCropsGrowthPeriodParam.setCropsId(cropsId);//作物编号
        briefingCropsGrowthPeriodParam.setRegionId(regionId);//区域编号
        //根据当前区域编号、作物查询对应得生育期数据
        List<BriefingCropsGrowthPeriod> briefingCropsGrowthPeriodParamList = briefingCropsGrowthPeriodService.findGrowthPeriodListByCropsIdAndRegionId(briefingCropsGrowthPeriodParam);
        if(briefingCropsGrowthPeriodParamList == null || briefingCropsGrowthPeriodParamList.size() == 0){
            return ResultMessage.fail(null,"选择的区域作物没有对应的物候期");
        }

        //计算作物生育期
        briefingReporterUtils.calculateCropsGrowthName(endDate, briefingCropsGrowthPeriodParamList, briefReportData,briefingReportParam,result);
        if(result.isFlag()){//选择的时间不在物候期内
            return result;
        }
        //查询本区域及下级所有区域列表
        List<Map<String,Object>> regionList = new ArrayList<>();//装入所有区域编号
        Map<String,Object> regionMap=new HashMap<>();

        regionMap.put("level",briefingReportParam.getLevel());
        regionMap.put("regionId",briefingReportParam.getRegionId());
        regionMap.put("regionName",briefingReportParam.getRegionName());
        regionList.add(regionMap);
        this.recursiveRegion(regionList,regionId);//递归获取所有区域编号

        for(Map<String,Object> regionListParam : regionList){
            Long rid = Long.parseLong(regionListParam.get("regionId").toString());
            String regionName =regionListParam.get("regionName").toString();
            Integer lvl  = Integer.parseInt(regionListParam.get("level").toString());
            briefingReportParam.setRegionId(rid);//区域编号
            briefingReportParam.setRegionName(regionName);//区域名称
            briefingReportParam.setLevel(lvl);//级别
            ResultMessage generatorMessage = createReport(briefingReportParam);//循环去生成简报
            if(!generatorMessage.isFlag()){
                logger.error("区域："+briefingReportParam.getRegionName()+",作物："+briefingReportParam.getCropsName()+"生成图表简报有问题，错误信息："+generatorMessage.getMsg());

            }
            logger.info("区域："+briefingReportParam.getRegionName()+",作物："+briefingReportParam.getCropsName()+"生成图表简报成功!");
        }


        return ResultMessage.success();
    }



    /**
     *
     * @param ridList 装载所有符合的区域编号
     * @param regionId 区域编号
     */
    private void recursiveRegion(List<Map<String,Object>> ridList, Long regionId){
     /*List<RegionListParam> regionListNext = briefingReportMapper.queryRegionList(regionId);
            if(regionListNext.size()>0){
                for(RegionListParam regionParam : regionListNext){
                    regionList.add(regionParam);
                    recursiveRegion(regionList,regionParam.getRegionId());
                }
            }
*/
        ResultMessage regionMessage = regionFeignService.queryRegionListByParentId(regionId);
        if(regionMessage.isFlag()){
            List<Map<String,Object>> regionList = (List<Map<String,Object>> ) regionMessage.getData();
            if(regionList.size()>0){
                for(Map<String,Object> map : regionList){
                    Map<String,Object> mapRegion = new HashMap<>();
                   /* Long rid = regionParam.getRegionId();
                    Integer level = regionParam.getLevel();
                    String regionName = regionParam.getChinaName();
                    regionParam.setRegionId(regionId);//区域id
                    regionParam.setRegionName(regionName);//区域名称
                    regionParam.setLevel(level);//区域级别
                    ridList.add(regionParam);*/
                    Long rid = Long.parseLong(map.get("regionId").toString());
                    Integer level = Integer.parseInt(map.get("level").toString());
                    String regionName = map.get("chinaName").toString();
                    mapRegion.put("regionId",rid);
                    mapRegion.put("regionName",regionName);
                    mapRegion.put("level",level);
                    /*regionParam.setRegionId(regionId);//区域id
                    regionParam.setRegionName(regionName);//区域名称
                    regionParam.setLevel(level);//区域级别*/

                    ridList.add(mapRegion);
                    recursiveRegion(ridList,rid);
                }
            }
        }
    }

    public IBriefingDiseaseAllService getBriefingDiseaseAllService() {
        return briefingDiseaseAllService;
    }

    //递归查询拼接简报名称和简报的输出地址
    Map<String,Object> codeAndName(Integer level,Long parentId,Map<String,Object> map){
        if(level!=1){
            ResultMessage resultMessage=regionFeignService.queryRegionById(parentId);
            if(resultMessage.getData()!=null){
                Map<String,Object> region=(Map<String,Object>)resultMessage.getData();
                map.put("chinaName",region.get("chinaName")+"|"+map.get("chinaName").toString());
                map.put("regionCode",region.get("regionCode") + File.separator + map.get("regionCode").toString());
                if(region.get("parentId")!=null){
                    return codeAndName(Integer.parseInt(region.get("level").toString()),Long.parseLong(region.get("parentId").toString()),map);
                }else{
                    return codeAndName(Integer.parseInt(region.get("level").toString()),parentId,map);
                }

            }

        }
        return map;
    }


    /**
     * 向上获取对应的生育期。
     * @param level 级别
     * @param regionId 区域编号
     * @param briefingCropsGrowthPeriodParam 生育期查询周期
     * @return 对应的生育期
     */
    private List<BriefingCropsGrowthPeriod> getCropsGrowthPeriodParams(Integer level, Long regionId, BriefingCropsGrowthPeriodParam briefingCropsGrowthPeriodParam) {
        //如果当前区域没有生育期，且区域级别大于2，那么就得向上查询上一级别的区域,保证能获取上一级别物候期
        Long parentRegionId = regionId;
        do{
            //  InitRegion initRegion = new InitRegion();
            // initRegion.setRegionId(parentRegionId);//区域编号
            ResultMessage resultMessage = regionFeignService.queryRegionById(parentRegionId);
            Object obj = (Object)resultMessage.getData();
            if(obj != null){
                Map<String,Object> map =(Map<String,Object>)obj;//区域数据
                //map = (Map<String,Object>)obj;//区域数据
                parentRegionId = Long.parseLong(map.get("parentId").toString()); //重置区域编号，取上一级别
                briefingCropsGrowthPeriodParam.setRegionId(parentRegionId);//区域编号
                List<BriefingCropsGrowthPeriod> cropsGrowthPeriodParamList = briefingCropsGrowthPeriodService.findGrowthPeriodListByCropsIdAndRegionId(briefingCropsGrowthPeriodParam);
                if(cropsGrowthPeriodParamList != null && cropsGrowthPeriodParamList.size() > 0){
                    return cropsGrowthPeriodParamList;
                }
            }
            level--;
        }while(level>2);
        return null;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
