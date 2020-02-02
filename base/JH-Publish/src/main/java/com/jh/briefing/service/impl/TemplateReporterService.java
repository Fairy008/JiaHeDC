package com.jh.briefing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.enums.PublishStatusEnum;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.briefing.entity.TemplateReporter;
import com.jh.briefing.mapping.ITemplateReporterMapper;
import com.jh.briefing.model.*;
import com.jh.briefing.service.*;
import com.jh.briefing.util.TemplateReporterUtils;
import com.jh.briefingNew.enums.BriefingReportEnum;
import com.jh.briefingNew.model.BriefingReportParam;
import com.jh.briefingNew.service.IBriefingReportService;
import com.jh.feign.IRegionFeignService;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简报服务类
 * Created by lxy on 2018/4/13.
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class TemplateReporterService extends BaseServiceImpl<TemplateReporterParam,TemplateReporter,Long> implements
        ITemplateReporterService {
    private static Logger logger = LoggerFactory.getLogger(TemplateReporterService.class);
    //简报服务类公共方法
    private final TemplateReporterUtils templateReporterUtils = new TemplateReporterUtils(this);
    @Autowired
    private ITemplateReporterMapper templateReporterMapper;
    @Autowired
    private ICropsGrowthPeriodService cropsGrowthPeriodService;//作物物候期服务
    @Autowired
    private IDiseaseControlService diseaseControlService;//作物物候期病情防治服务
    @Autowired
    private IBugDiseaseControlService bugDiseaseControlService;//作物物候期虫害防治服务
    @Autowired
    private IGrowthRelativeGroundTempService growthRelativeGroundTempService;//作物生长与地温关联服务类
    @Autowired
    private ITemplateModuleService templateModuleService;//模板服务接口

    @Autowired
    private IBriefingReportService briefingReportService;//生成html简报

    @Autowired
    private IRegionFeignService regionFeignService;//引用jh-sys中的regionService
//    @Autowired
//    private IPermAccountMapper permAccountMapper;

    @Autowired
    private Configuration configuration;//freemarker的模板配置

    @Override
    protected IBaseMapper<TemplateReporterParam, TemplateReporter, Long> getDao() {
        return templateReporterMapper;
    }

    /**
     * 简报分页查询
     * @param templateReporterParam
     * @return PageInfo
     * @version <1> 2018-04-13 lxy： Created.
     */
    @Override
    public PageInfo<TemplateReporterParam> findByPage(TemplateReporterParam templateReporterParam) {
        PageHelper.startPage(templateReporterParam.getPage(), templateReporterParam.getRows());
        List<TemplateReporterParam> list = templateReporterMapper.queryByPage(templateReporterParam);
        return new PageInfo<TemplateReporterParam>(list);
    }

    /**
     * 根据区域、作物名称获得对应的简报
     * @param templateReporterParam 查询参数
     * @return  List<TemplateReporterParam>
     * @version <1> 2018-04-26 lxy： Created.
     */
    @Override
    public List<TemplateReporterParam> queryReporterByRegionAndCrops(TemplateReporterParam templateReporterParam) {
        return templateReporterMapper.queryReporterByRegionAndCrops(templateReporterParam);
    }

    /**
     *  根据 regionId（区域编号） 和 dateTime （创建时间）查询地温均值信息和对应得区域
     * @param templateReporterParam 查询参数
     * @return List<TtnRegionTotal>
     */
    @Override
    public List<TtnRegionTotal> queryTtnByRegionAndDateTime(TemplateReporterParam templateReporterParam) {
        return templateReporterMapper.queryTtnByRegionAndDateTime(templateReporterParam);
    }

    /**
     *  根据 regionId（区域编号） 和 dateTime （创建时间）查询有效积温和对应的区域
     * @param templateReporterParam 查询参数
     * @return List<TtnRegionTotal>
     */
    @Override
    public List<TtnRegionTotal> queryTtnTotalByRegionAndDateTime(TemplateReporterParam templateReporterParam) {
        return templateReporterMapper.queryTtnTotalByRegionAndDateTime(templateReporterParam);
    }

    /**
     * 根据 regionId（区域编号） 和 dateTime （创建时间）查询下雨天数
     * @param templateReporterParam
     * @return
     */
    @Override
    public Integer countRainDays(TemplateReporterParam templateReporterParam) {
        return templateReporterMapper.countRainDays(templateReporterParam);
    }

    /**
     * 最大降雨量、区域信息
     * @param templateReporterParam
     * @return
     */
    public TrrmRegionMaxDay queryMaxTrrmRegionInDay(TemplateReporterParam templateReporterParam){
        return templateReporterMapper.queryMaxTrrmRegionInDay(templateReporterParam);
    }

    /**
     * 查询指定时间范围，周雨量信息
     * @param templateReporterParam
     * @return
     */
    @Override
    public List<TrrmStatisticsMultRegion> queryTrrmStatisticsForWeek(TemplateReporterParam templateReporterParam) {
        return templateReporterMapper.queryTrrmStatisticsForWeek(templateReporterParam);
    }


    /**
     * 查询指定时间范围，月雨量信息
     * @param templateReporterParam
     * @return
     * @version <1> 2018-04-13 lxy： Created.
     */
    @Override
    public List<TrrmStatisticsMultRegion> queryTrrmStatisticsForMonth(TemplateReporterParam templateReporterParam){
        return templateReporterMapper.queryTrrmStatisticsForMonth(templateReporterParam);
    }

    /**
     * 生成简报
     * @param templateReporterParam
     * @return
     * @version <1> 2018-04-13 lxy： Created.
     * @version <1> 2018-11-19 Roach： Modified.
     */
    @Override
    @Transactional
    public ResultMessage generatorReporter(TemplateReporterParam templateReporterParam) {
        /*BriefingReportParam briefingReportParam = new BriefingReportParam();
        briefingReportParam.setStartDate(templateReporterParam.getStartDate());
        briefingReportParam.setEndDate(templateReporterParam.getEndDate());
        briefingReportParam.setCropsId(templateReporterParam.getCropsId());
        briefingReportParam.setCropsName(templateReporterParam.getCropsName());
        briefingReportParam.setLevel(templateReporterParam.getLevel());
        briefingReportParam.setRegionId(templateReporterParam.getRegionId());
        briefingReportParam.setCreator(templateReporterParam.getCreatorNickName());
        briefingReportParam.setRegionName(templateReporterParam.getRegionName());
        briefingReportParam.setRegionParentId(templateReporterParam.getRegionParentId());
        ResultMessage resultMessage=briefingReportService.createReport(briefingReportParam);
        if(resultMessage.getData()!=null){
            BriefingReportParam briefingReport = (BriefingReportParam)resultMessage.getData();
            templateReporterParam.setReportChartPc(briefingReport.getPcBriefReportUrl());
            templateReporterParam.setReportChartMobile(briefingReport.getMobileBriefReportUrl());
        }*/

        ResultMessage result = ResultMessage.fail();//定义返回的ResultMessage
       // ReporterData reporterData = new ReporterData(); //简报模板载体，填入数据，供生成模板使用
        /*
         * 区域编号、区域名称、作物编号
         */
        Integer level = templateReporterParam.getLevel();//区域级别
        Long regionId = templateReporterParam.getRegionId();//区域编号
        String regionName = templateReporterParam.getRegionName();//区域名称
        Integer cropsId = templateReporterParam.getCropsId();//作物编号
        String cropsName = templateReporterParam.getCropsName();//作物名称
        templateReporterParam.setCropsName(cropsName);//作物名

        /*
         * 时间的设定
         * 选择的时间（开始，结束）
         */
        String startDate = templateReporterParam.getStartDate();//开始时间
        String endDate = templateReporterParam.getEndDate();//结束时间
        if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate) || StringUtils.isEmpty(regionId) || StringUtils.isEmpty(cropsId)){
            result.setMsg("生成简报的参数缺失");
            result.setFlag(false);
            return result;
        }
        templateReporterUtils.wrapperStartAndEndDate(startDate, endDate, templateReporterParam);//组装开始时间、结束时间

        //选择的区域级别不能小于2，只能选择省或省以下的区域
        if(level < 2) {
            result.setFlag(false);
            result.setMsg("请选择省或省以下区域");
            return result;
        }
        /*
         * 根据作物、区域查询指定的物候期，获取物候期名字
         */
        CropsGrowthPeriodParam cropsGrowthPeriodParam = new CropsGrowthPeriodParam();
        cropsGrowthPeriodParam.setCropsId(cropsId);//作物编号
        cropsGrowthPeriodParam.setRegionId(regionId);//区域编号
        //根据当前区域编号、作物查询对应得生育期数据
        List<CropsGrowthPeriodParam> cropsGrowthPeriodParamList = cropsGrowthPeriodService.findGrowthPeriodListByCropsIdAndRegionId(cropsGrowthPeriodParam);
        //循环获取向上区域对应的生育期
        if(cropsGrowthPeriodParamList == null || cropsGrowthPeriodParamList.size() == 0){
            cropsGrowthPeriodParamList = getCropsGrowthPeriodParams(level, regionId, cropsGrowthPeriodParam);
        }
        if(cropsGrowthPeriodParamList == null || cropsGrowthPeriodParamList.size() == 0){
            result.setMsg("选择的区域作物没有对应的物候期");
            result.setFlag(false);
            return result;
        }
        /*
         * 基础数据入农情模板
         * 相关参数填入 简报模板 数据载体中 --  貌似有点零散，侵入性大
         */
        templateReporterUtils.calculateCropsGrowthName(endDate, cropsGrowthPeriodParamList, templateReporterParam);//计算作物生育期
        String growthName = templateReporterParam.getGrowthName(); //物候期名称
        String startDateNoYear = templateReporterParam.getStartDateNoYear();//开始时间
        String endDateNoYear = templateReporterParam.getEndDateNoYear();//结束时间
        if(StringUtils.isEmpty(growthName)){
            String startGrowthDate = templateReporterParam.getGrowthStartDate();//物候期开始时间
            String endGrowthDate = templateReporterParam.getLastGrowthDate();//物候期结束时间
            result.setFlag(false);
            if (StringUtils.isEmpty(startGrowthDate)) {
                result.setMsg("请配置作物的播种期");
            }else {
                result.setMsg("选择的时间不在物候期内（" + startGrowthDate + "~" + endGrowthDate + "）");
            }
            return result;
        }
      /*  reporterData.setGrowthPeriodName(growthName);//物候期名称
        reporterData.setRegionName(regionName);//区域名称
        reporterData.setDateTimeSpan(startDateNoYear+"~"+endDateNoYear);//选择的时间
        reporterData.setCropsName(cropsName);//设置作物名称
        reporterData.setRegionLevel(templateReporterParam.getLevel());//设置区域级别
        reporterData.setRemark(templateReporterParam.getRemark());//设置备注信息*/

        /*
         * 设置地温、雨量统计信息,入模板
         */
        /*TtnStatistics ttnStatistics = templateReporterUtils.getTtnStatistics(templateReporterParam);//地温统计模板
        TrrmStatistics trrmStatistics = templateReporterUtils.getTrrmStatistics(templateReporterParam, endDate);//雨量统计模板
        reporterData.setTtnStatistics(ttnStatistics);//地温统计模板入总模板
        reporterData.setTrrmStatistics(trrmStatistics);//雨量统计模板入总模板

        *//*
         * 作物病情、虫害防治措施,入模板
         *//*
        templateReporterUtils.setDiseaseInfoInoTemplate(templateReporterParam, reporterData);
        String reporterName = "珈和农情("+templateReporterParam.getStartDate()+"~"+ templateReporterParam.getEndDate()+")"+regionName+cropsName+"简报";//简报名称
        reporterData.setDateTimeSpan(reporterName);
        *//*
         * 生成静态Html页面
         *//*
        Integer templateId = templateReporterParam.getTemplateId();//模板编号
        String resultData = templateReporterUtils.generatorTemplate(reporterData,templateId,false,templateReporterParam.getLevel());//返回静态页面内容
        if(StringUtils.isEmpty(resultData)){
            return ResultMessage.fail(null,"生成模板出错，没有网页版模板！");
        }
        String resultMobileData = templateReporterUtils.generatorTemplate(reporterData,templateId,true,templateReporterParam.getLevel());//返回手机版的模板内容
        if(StringUtils.isEmpty(resultMobileData)){
            return ResultMessage.fail(null,"生成模板出错，没有提供手机模板！");
        }*/

        //根据所选生成简报的参数删除之前存在的简报
        templateReporterMapper.deleteBriefByParams(templateReporterParam);

        String reporterName = "珈和农情("+templateReporterParam.getStartDate()+"~"+ templateReporterParam.getEndDate()+")"+regionName+cropsName+"简报";//简报名称

        /**
         * 保存到模板数据库中
         */
        TemplateReporter templateReporter = new TemplateReporter();

        templateReporter.setReporterName(reporterName);//简报名称
       // templateReporter.setReporterContent(resultData);//简报内容
       // templateReporter.setReporterMobileContent(resultMobileData);//手机简报内容
        templateReporter.setCropsId(cropsId);//选择的作物编号
        templateReporter.setRegionId(regionId);//区域编号
        LocalDate startLocalDate = templateReporterParam.getStartLocalDate();//开始时间
        LocalDate endLocalDate = templateReporterParam.getEndLocalDate();//结束时间
        templateReporter.setReportDataTimeStart(startLocalDate);//模板数据开始时间
        templateReporter.setReportDataTimeEnd(endLocalDate);//模板数据结束时间
        templateReporter.setCreateTime(LocalDateTime.now());//创建时间
       // templateReporter.setModifyTime(LocalDateTime.now());//修改时间
        String creator = templateReporterParam.getCreator();//获取当前登录的用户
        templateReporter.setCreator(creator);//设置创建者

        templateReporter.setAudisState(PublishStatusEnum.PUBLISH_STATUS_N.getValue());//发布状态：2201已发布，2202未发布
       // templateReporter.setTemplateId(templateId);//设置模板编号
        //图表类型的简报路径
       /* templateReporter.setReportChartPc(templateReporterParam.getReportChartPc());
        templateReporter.setReportChartMobile(templateReporterParam.getReportChartMobile());*/
        //保存入库
        templateReporterMapper.save(templateReporter);
        return ResultMessage.success();
    }



    public ResultMessage batchGeneratorReporter(TemplateReporterParam templateReporterParam) {
        /*
         * 区域编号、区域名称、作物编号
         */
        Integer level = templateReporterParam.getLevel();//区域级别
        Integer cropsId = templateReporterParam.getCropsId();//作物编号
        Long regionId = templateReporterParam.getRegionId();//区域编号
        String endDate = templateReporterParam.getEndDate();//结束时间
        //选择的区域级别不能小于2，只能选择省或省一下的区域
        if(level < 2) {
            return ResultMessage.fail("请选择省或省以下区域");
        }
         /*
         * 根据作物、区域查询指定的物候期，获取物候期名字
         */
        CropsGrowthPeriodParam cropsGrowthPeriodParam = new CropsGrowthPeriodParam();
        cropsGrowthPeriodParam.setCropsId(cropsId);//作物编号
        cropsGrowthPeriodParam.setRegionId(regionId);//区域编号
        //根据当前区域编号、作物查询对应得生育期数据
        List<CropsGrowthPeriodParam> cropsGrowthPeriodParamList = cropsGrowthPeriodService.findGrowthPeriodListByCropsIdAndRegionId(cropsGrowthPeriodParam);
        //循环获取向上区域对应的生育期
        if(cropsGrowthPeriodParamList == null || cropsGrowthPeriodParamList.size() == 0){
            cropsGrowthPeriodParamList = getCropsGrowthPeriodParams(level, regionId, cropsGrowthPeriodParam);
        }
        if(cropsGrowthPeriodParamList == null || cropsGrowthPeriodParamList.size() == 0){
            return ResultMessage.fail("选择的区域作物没有对应的物候期");
        }
        /*
         * 基础数据入农情模板
         * 相关参数填入 简报模板 数据载体中 --  貌似有点零散，侵入性大
         */
        templateReporterUtils.calculateCropsGrowthName(endDate, cropsGrowthPeriodParamList, templateReporterParam);//计算作物生育期
        String growthName = templateReporterParam.getGrowthName(); //物候期名称
        if(StringUtils.isEmpty(growthName)){
            String startGrowthDate = templateReporterParam.getGrowthStartDate();//物候期开始时间
            String endGrowthDate = templateReporterParam.getLastGrowthDate();//物候期结束时间
            return ResultMessage.fail("选择的时间不在物候期内（"+startGrowthDate+"~"+endGrowthDate+"）");
        }
       // List<Map<String,Object>> ridList = new ArrayList<>();//装入所有区域编号




        //这是什么？？？？  2018-07-14  lcw
//        //获取自身的区域，不能漏掉
//        InitRegion initRegion = new InitRegion();
//        initRegion.setRegionId(regionId);//区域编号
//        Object obj = regionService.queryInitRegionByInitRegionId(initRegion).getData();
//        if(obj != null){
//            initRegion = (InitRegion)obj;
//            Map<String,Object> map = new HashMap<>();
//            map.put("regionId",initRegion.getRegionId());//区域编号
//            map.put("regionName",initRegion.getChinaName());//区域名称
//            map.put("level",initRegion.getLevel());//级别
//            ridList.add(map);
//
//        }
       // this.recursiveRegion(ridList,regionId);//递归获取所有区域编号



        List<Map<String,Object>> regionList = new ArrayList<>();//装入所有区域编号
        Map<String,Object> regionMap=new HashMap<>();

        regionMap.put("level",templateReporterParam.getLevel());
        regionMap.put("regionId",templateReporterParam.getRegionId());
        regionMap.put("regionName",templateReporterParam.getRegionName());
        regionList.add(regionMap);
        this.recursiveRegion(regionList,regionId);//递归获取所有区域编号


        for(Map<String,Object> regionListParam : regionList){
            Long rid = Long.parseLong(regionListParam.get("regionId").toString());
            String regionName =regionListParam.get("regionName").toString();
            Integer lvl  = Integer.parseInt(regionListParam.get("level").toString());
           /* Long rid = new Long(regionMap.get("regionId").toString());
            String regionName = regionMap.get("regionName").toString();
            Integer lvl  = new Integer(regionMap.get("level").toString());*/
            templateReporterParam.setRegionId(rid);//区域编号
            templateReporterParam.setRegionName(regionName);//区域名称
            templateReporterParam.setLevel(lvl);//级别
            ResultMessage generatorMessage = generatorReporter(templateReporterParam);//循环去生成简报
            if(!generatorMessage.isFlag()){
                logger.error("区域："+templateReporterParam.getRegionName()+",作物："+templateReporterParam.getCropsName()+"生成简报有问题，错误信息："+generatorMessage.getMsg());

            }
            logger.info("区域："+templateReporterParam.getRegionName()+",作物："+templateReporterParam.getCropsName()+"生成简报成功!");
        }
        return ResultMessage.success();
    }

    /**
     * 向上获取对应的生育期。
     * @param level 级别
     * @param regionId 区域编号
     * @param cropsGrowthPeriodParam 生育期查询周期
     * @return 对应的生育期
     */
    private List<CropsGrowthPeriodParam> getCropsGrowthPeriodParams(Integer level, Long regionId, CropsGrowthPeriodParam cropsGrowthPeriodParam) {
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
                cropsGrowthPeriodParam.setRegionId(parentRegionId);//区域编号
                List<CropsGrowthPeriodParam> cropsGrowthPeriodParamList = cropsGrowthPeriodService.findGrowthPeriodListByCropsIdAndRegionId(cropsGrowthPeriodParam);
                if(cropsGrowthPeriodParamList != null && cropsGrowthPeriodParamList.size() > 0){
                    return cropsGrowthPeriodParamList;
                }
            }
            level--;
       }while(level>2);
        return null;
    }

    /**
     *
     * @param ridList 装载所有符合的区域编号
     * @param regionId 区域编号
     */
   /* private void recursiveRegion(List<Map<String,Object>> ridList, Long regionId){
        ResultMessage regionMessage = regionFeignService.queryRegionListByParentId(regionId);
        if(regionMessage.isFlag()){
            List<InitRegion> regionList = (List<InitRegion>) regionMessage.getData();
            if(regionList.size()>0){
                for(InitRegion regionParam : regionList){
                    Map<String,Object> map = new HashMap<>();
                    Long rid = regionParam.getRegionId();
                    Integer level = regionParam.getLevel();
                    String regionName = regionParam.getChinaName();
                    map.put("regionId",rid);//区域编号
                    map.put("regionName",regionName);//区域名称
                    map.put("level",level);//级别
                    ridList.add(map);
                    recursiveRegion(ridList,rid);
                }
            }
        }
    }*/
    private void recursiveRegion(List<Map<String,Object>> ridList, Long regionId){
        ResultMessage regionMessage = regionFeignService.queryRegionListByParentId(regionId);
        if(regionMessage.isFlag()){
            Object regionListObj = regionMessage.getData();
            List<Map<String,Object>> regionList = (List<Map<String,Object>>)regionListObj;
            if(regionList.size()>0){

                for(Map<String,Object> map : regionList){
                    Map<String,Object> mapRegion = new HashMap<>();
                    Long rid = Long.parseLong(map.get("regionId").toString());
                    Integer level = Integer.parseInt(map.get("level").toString());
                    String regionName = map.get("chinaName").toString();
                    mapRegion.put("regionId",rid);
                    mapRegion.put("regionName",regionName);
                    mapRegion.put("level",level);

                    ridList.add(mapRegion);
                    recursiveRegion(ridList,rid);
                }


            }
        }
    }
    /**
     * 修改简报内容
     * @param templateReporterParam
     * @return ResultMessage
     * @version <1> 2018-04-13 lxy： Created.
     */
    @Override
    public ResultMessage updateReporter(TemplateReporterParam templateReporterParam) {
        TemplateReporter templateReporter = new TemplateReporter();
        templateReporter.setReporterId(templateReporterParam.getReporterId());//设置报表编号
        templateReporter.setModifyTime(LocalDateTime.now());//设置修改修改时间
        templateReporter.setModifier(templateReporterParam.getModifier());//设置修改人
        templateReporter.setReporterContent(templateReporterParam.getReporterContent());//设置简报内容
        templateReporter.setReporterMobileContent(templateReporterParam.getReporterMobileContent());//设置手机简报内容
        templateReporter.setAudisState(templateReporterParam.getAudisState());//设置审核状态
        templateReporterMapper.update(templateReporter);//修改模板
        return ResultMessage.success();
    }

    /**
     * 修改简报发布状态
     * @param templateReporterParam 查询参数
     * @return 返回修改记录数
     * @version <1> 2018-06-03 lxy： Create
     */
    @Override
    public ResultMessage updateAudisStatusInIds(TemplateReporterParam templateReporterParam) {
        Integer num = templateReporterMapper.updateAudisStatusInIds(templateReporterParam);
        if(num>0){
            return ResultMessage.success();
        }else{
            return ResultMessage.fail();
        }
    }

    /**
     * 根据简报编号获取对应的简报
     * @param reporterId 简报编号
     * @param flag 获取简报标志分类：1：预览，2：编辑
     * @return 返回对应的简报
     * @version <1> 2018-06-03 lxy： Create
     */
    @Override
    public ResultMessage getBriefReporterByReporterId(Long reporterId, String flag) {
        TemplateReporterParam reporter = templateReporterMapper.getBriefReporterByReporterId(reporterId);
        if(reporter != null){
            if("1".equals(flag)){
                String mobileContent = reporter.getReporterMobileContent();//手机简报内容
                mobileContent = mobileContent.replaceAll("\\r\\n|\\n|\\r","<br>");//将文本的换行符换成网页的换行符
                reporter.setReporterMobileContent(mobileContent);//手机简报内容
            }
            return ResultMessage.success(reporter);
        }else{
            return ResultMessage.fail();
        }
    }

    @Override
    public ResultMessage findBriefReportById(Long reporterId, Integer type) {
        TemplateReporterParam reporter = templateReporterMapper.getBriefReporterByReporterId(reporterId);

        if(type==2){//pc
            if(reporter.getReportChartPc() != null && !reporter.getReportChartPc().equals("")){
                BufferedReader br = null;
                InputStreamReader isr = null;
                String childPath = CephUtils.getAbsolutePath(reporter.getReportChartPc());
                //System.out.println("文件路径:" + childPath);
                try{
                    File file = new File(childPath);
                    if (file.exists()) {
                        StringBuffer buffer = new StringBuffer();
                        isr = new InputStreamReader(new FileInputStream(file),"utf-8");
                        br = new BufferedReader(isr);
                        int s;
                        while((s = br.read())!=-1){
                            buffer.append((char)s);
                        }
                        reporter.setReportChartPc(buffer.toString());
                        //System.out.println(buffer.toString());
                    }

                }catch(Exception e){
                    e.printStackTrace();
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        isr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return ResultMessage.success(reporter);
            }
        }else{//mobile
            if(reporter.getReportChartMobile() != null  && !reporter.getReportChartMobile().equals("")){
                BufferedReader br = null;
                InputStreamReader isr = null;
                String childPath = CephUtils.getAbsolutePath(reporter.getReportChartMobile());
                System.out.println("文件路径:" + childPath);
                try{
                    File file = new File(childPath);
                    if (file.exists()) {
                        StringBuffer buffer = new StringBuffer();
                        isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                        br = new BufferedReader(isr);
                        int s;
                        while ((s = br.read()) != -1) {
                            buffer.append((char) s);
                        }
                        reporter.setReportChartMobile(buffer.toString());
                        //System.out.println(buffer.toString());
                    }

                }catch(Exception e){
                    e.printStackTrace();
                } finally {
                    try {
                        if (br != null) {
                            br.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (br != null) {
                            isr.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return ResultMessage.success(reporter);
            }
        }
        return ResultMessage.fail();
    }


    /**
     * 下面提供getter方法，供TemplateReporerUtils类，或其他类访问
     */

    public ICropsGrowthPeriodService getCropsGrowthPeriodService() {
        return cropsGrowthPeriodService;
    }

    public IDiseaseControlService getDiseaseControlService() {
        return diseaseControlService;
    }

    public IBugDiseaseControlService getBugDiseaseControlService() {
        return bugDiseaseControlService;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

//    public IRegionService getRegionService() {
//        return regionService;
//    }

    public IGrowthRelativeGroundTempService getGrowthRelativeGroundTempService() {
        return growthRelativeGroundTempService;
    }

    public ITemplateModuleService getTemplateModuleService() {
        return templateModuleService;
    }



    @Override
    public ResultMessage findBriefReportByType(Long reporterId, Integer type) {
        BriefingReportParam briefingReportParam=decorateData(reporterId,type);
        return  briefingReportService.queryReport(briefingReportParam);
    }

    @Override
    public ResultMessage findBriefReportByTypeNew(Long reporterId, Integer type) {
        BriefingReportParam briefingReportParam = decorateData(reporterId, type);
        String isTrrm="N";//不包含降水数据
        return briefingReportService.queryReportNew(briefingReportParam,isTrrm);
    }

    @Override
    public ResultMessage findBriefReportByTypeTrrm(Long reporterId, Integer type) {
        BriefingReportParam briefingReportParam = decorateData(reporterId, type);
        String isTrrm="Y";//降水数据
        return briefingReportService.queryReportNew(briefingReportParam,isTrrm);
    }

    private BriefingReportParam decorateData(Long reporterId, Integer type){
        //根据id  查询简报的条件信息
        TemplateReporterParam templateReporterParam = templateReporterMapper.getBriefReporterByReporterId(reporterId);
        /*
         * 区域编号、区域名称、作物编号
         */
        BriefingReportParam briefingReportParam = new BriefingReportParam();
        Integer cropsId = templateReporterParam.getCropsId();//作物编号
        if(cropsId==511 || cropsId == 506){//油菜和冬小麦  是冬季作物
            briefingReportParam.setIfWinter(BriefingReportEnum.IS_WINTER_CROP.getValue());
        }else {//其他为非冬季作物
            briefingReportParam.setIfWinter(BriefingReportEnum.NO_WINTER_CROP.getValue());
        }
        briefingReportParam.setStartDate(templateReporterParam.getReportDataTimeStart().toString());
        briefingReportParam.setEndDate(templateReporterParam.getReportDataTimeEnd().toString());
        briefingReportParam.setCropsId(templateReporterParam.getCropsId());
        briefingReportParam.setCropsName(templateReporterParam.getCropsName());
        briefingReportParam.setLevel(templateReporterParam.getLevel());
        briefingReportParam.setRegionId(templateReporterParam.getRegionId());
        briefingReportParam.setCreator(templateReporterParam.getCreatorNickName());
        briefingReportParam.setRegionName(templateReporterParam.getRegionName());
        briefingReportParam.setRegionParentId(templateReporterParam.getRegionParentId());
        return briefingReportParam;
    }



    /**
     * 根据生成简报的参数查询该简报是否已存在
     * @param templateReporterParam 模板参数
     * @return ResultMessage
     * @version <1> 2018-11-19 Roach： Created.
     */
    @Override
    public ResultMessage checkBriefIsExist(TemplateReporterParam templateReporterParam) {
        List<TemplateReporterParam> briefList =  templateReporterMapper.queryBriefByParams(templateReporterParam);
        return ResultMessage.success(briefList);
    }

}
