package com.jh.briefing.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseService;
import com.jh.briefing.entity.TemplateReporter;
import com.jh.briefing.model.TemplateReporterParam;
import com.jh.briefing.model.TrrmRegionMaxDay;
import com.jh.briefing.model.TrrmStatisticsMultRegion;
import com.jh.briefing.model.TtnRegionTotal;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 *  简报服务接口类
 * Created by lxy on 2018/4/13.
 */
public interface ITemplateReporterService extends IBaseService<TemplateReporterParam,TemplateReporter,Long> {
    /**
     * 简报分页查询
     * @param templateReporterParam
     * @return
     * @version <1> 2018-04-13 lxy： Created.
     */
    PageInfo<TemplateReporterParam> findByPage(TemplateReporterParam templateReporterParam);

    /**
     * 根据区域、作物名称获得对应的简报
     * @param templateReporterParam 查询参数
     * @return  List<TemplateReporterParam> 模板
     * @version <1> 2018-04-26 lxy： Created.
     */
    List<TemplateReporterParam> queryReporterByRegionAndCrops(TemplateReporterParam templateReporterParam);

    /**
     *  根据 regionId（区域编号） 和 dateTime （创建时间）查询地温均值信息和对应得区域
     * @param templateReporterParam 查询参数
     * @return List<TtnRegionTotal> 地温数据
     * @version <1> 2018-04-13 lxy： Created.
     */
    List<TtnRegionTotal> queryTtnByRegionAndDateTime(TemplateReporterParam templateReporterParam);


    /**
     *  根据 regionId（区域编号） 和 dateTime （创建时间）查询有效积温和对应的区域
     * @param templateReporterParam 查询参数
     * @return List<TtnRegionTotal> 地温数据
     * @version <1> 2018-04-13 lxy： Created.
     */
    List<TtnRegionTotal> queryTtnTotalByRegionAndDateTime(TemplateReporterParam templateReporterParam);

    /**
     * 根据 regionId（区域编号） 和 dateTime （创建时间）查询下雨天数
     * @param templateReporterParam 简报参数
     * @return 统计下雨天数
     * @version <1> 2018-04-13 lxy： Created.
     */
    Integer countRainDays(TemplateReporterParam templateReporterParam);

    /**
     * 最大降雨量、区域信息
     * @param templateReporterParam 简报参数
     * @return 最大降雨量
     * @version <1> 2018-04-13 lxy： Created.
     */
    TrrmRegionMaxDay queryMaxTrrmRegionInDay(TemplateReporterParam templateReporterParam);


    /**
     * 查询指定时间范围，周、月雨量信息
     * @param templateReporterParam 简报参数
     * @return 雨量统计多区域
     */
    List<TrrmStatisticsMultRegion> queryTrrmStatisticsForWeek(TemplateReporterParam templateReporterParam);

    /**
     * 生成简报
     * @param templateReporterParam 简报参数
     * @return 操作结果
     * @version <1> 2018-04-13 lxy： Created.
     */
    public ResultMessage generatorReporter(TemplateReporterParam templateReporterParam);

    /**
     * 批量生成简报
     * @param templateReporterParam 简报参数
     * @return 操作结果
     * @version <1> 2018-06-05 lxy： Created.
     */
    public ResultMessage batchGeneratorReporter(TemplateReporterParam templateReporterParam);

    /**
     * 修改简报内容
     * @param templateReporterParam 简报参数
     * @return
     * @version <1> 2018-04-13 lxy： Created.
     */
    public ResultMessage updateReporter(TemplateReporterParam templateReporterParam);

    /**
     * 查询指定时间范围，月雨量信息
     * @param templateReporterParam 简报参数
     * @return 雨量统计多区域
     * @version <1> 2018-04-13 lxy： Created.
     */
    List<TrrmStatisticsMultRegion> queryTrrmStatisticsForMonth(TemplateReporterParam templateReporterParam);

    /**
     * 修改简报发布状态
     * @param templateReporterParam 查询参数
     * @return 返回修改记录数
     * @version <1> 2018-06-03 lxy： Create
     */
    ResultMessage updateAudisStatusInIds(TemplateReporterParam templateReporterParam);

    /**
     * 根据简报编号获取对应的简报
     * @param reporterId 简报编号
     * @param flag 获取简报标志分类：1：预览，2：编辑
     * @return 返回对应的简报
     * @version <1> 2018-06-03 lxy： Create
     */
    ResultMessage getBriefReporterByReporterId(Long reporterId, String flag);

    /**
     * 根据简报编号获取对应的简报
     * @param reporterId 简报编号
     * @param type 获取简报标志分类：1：pc，0:mobile
     * @return 返回对应的简报
     * @version <1> 2018-07-27 wl： Create
     */
    ResultMessage findBriefReportById(Long reporterId, Integer type);

    /**
     * 根据简报编号获取对应的简报
     * @param reporterId 简报编号
     * @param type 获取简报标志分类：1：pc，0:mobile
     * @return 返回对应的简报
     * @version <1> 2018-07-27 wl： Create
     */
    ResultMessage findBriefReportByType(Long reporterId, Integer type);

    /**
     * 根据简报编号获取对应的简报(不包含降雨量和墒情)
     * @param reporterId 简报编号
     * @param type 获取简报标志分类：1：pc，0:mobile
     * @return 返回对应的简报
     * @version <1> 2018-07-27 wl： Create
     */
    ResultMessage findBriefReportByTypeNew(Long reporterId, Integer type);

    /**
     * 根据简报编号获取对应的简报(降雨量和墒情)
     * @param reporterId 简报编号
     * @param type 获取简报标志分类：1：pc，0:mobile
     * @return 返回对应的简报
     * @version <1> 2018-07-27 wl： Create
     */
    ResultMessage findBriefReportByTypeTrrm(Long reporterId, Integer type);

    /**
     * 根据生成简报的参数查询该简报是否已存在
     * @param templateReporterParam 模板参数
     * @return ResultMessage
     * @version <1> 2018-11-19 Roach： Created.
     */
    ResultMessage checkBriefIsExist(TemplateReporterParam templateReporterParam);


}
