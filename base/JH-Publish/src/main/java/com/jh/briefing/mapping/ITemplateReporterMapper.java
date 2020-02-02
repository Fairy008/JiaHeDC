package com.jh.briefing.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.briefing.entity.TemplateReporter;
import com.jh.briefing.model.TemplateReporterParam;
import com.jh.briefing.model.TrrmRegionMaxDay;
import com.jh.briefing.model.TrrmStatisticsMultRegion;
import com.jh.briefing.model.TtnRegionTotal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 简报服务Mapper
 * @version <1> 2018-04-13 lxy： Created.
 */
@Mapper
public interface ITemplateReporterMapper extends IBaseMapper<TemplateReporterParam,TemplateReporter,Long> {
    /**
     * 简报 分页
     *
     * @param templateReporterParam 查询参数
     * @return  List<TemplateReporterParam>
     * @version <1> 2018-04-13 lxy： Created.
     */
    List<TemplateReporterParam> queryByPage(TemplateReporterParam templateReporterParam);

    /**
     * 根据区域、作物名称获得对应的简报
     * @param templateReporterParam 查询参数
     * @return  List<TemplateReporterParam>
     * @version <1> 2018-04-26 lxy： Created.
     */
    List<TemplateReporterParam> queryReporterByRegionAndCrops(TemplateReporterParam templateReporterParam);

    /**
     *  根据 regionId（区域编号） 和 dateTime （创建时间）查询地温均值信息和对应得区域
     * @param templateReporterParam 查询参数
     * @return List<TtnRegionTotal>
     * @version <1> 2018-04-13 lxy： Create
     */
    List<TtnRegionTotal> queryTtnByRegionAndDateTime(TemplateReporterParam templateReporterParam);


    /**
     *  根据 regionId（区域编号） 和 dateTime （创建时间）查询有效积温和对应的区域
     * @param templateReporterParam 查询参数
     * @return List<TtnRegionTotal>
     * @version <1> 2018-04-13 lxy： Create
     */
    List<TtnRegionTotal> queryTtnTotalByRegionAndDateTime(TemplateReporterParam templateReporterParam);

    /**
     * 根据 regionId（区域编号） 和 dateTime （创建时间）查询下雨天数
     * @param templateReporterParam 查询参数
     * @return
     * @version <1> 2018-04-13 lxy： Create
     */
    Integer countRainDays(TemplateReporterParam templateReporterParam);

    /**
     * 最大降雨量区域信息
     * @param templateReporterParam 查询参数
     * @return 记录数据
     * @version <1> 2018-04-13 lxy： Create
     */
    TrrmRegionMaxDay queryMaxTrrmRegionInDay(TemplateReporterParam templateReporterParam);

    /**
     * 查询指定时间范围，周雨量信息
     * @param templateReporterParam 查询参数
     * @return 记录数据
     * @version <1> 2018-04-13 lxy： Create
     */
    List<TrrmStatisticsMultRegion> queryTrrmStatisticsForWeek(TemplateReporterParam templateReporterParam);

    /**
     * 查询指定时间范围，月雨量信息
     * @param templateReporterParam 查询参数
     * @return 记录数据
     * @version <1> 2018-04-13 lxy： Create
     */
    List<TrrmStatisticsMultRegion> queryTrrmStatisticsForMonth(TemplateReporterParam templateReporterParam);

    /**
     * 修改简报发布状态
     * @param templateReporterParam 查询参数
     * @return 返回修改记录数
     * @version <1> 2018-06-03 lxy： Create
     */
    Integer updateAudisStatusInIds(TemplateReporterParam templateReporterParam);

    /**
     * 根据简报编号获取对应的简报
     * @param reporterId 简报编号
     * @return
     */
    TemplateReporterParam getBriefReporterByReporterId(Long reporterId);

    /**
     * 根据区域id，作物id，开始和结束时间查询简报
     * @param templateReporterParam
     * @return
     */
    List<TemplateReporterParam> queryBriefByParams(TemplateReporterParam templateReporterParam);

    /**
     * 根据区域id，作物id，开始和结束时间删除简报
     * @param templateReporterParam
     * @return
     */
    Integer deleteBriefByParams(TemplateReporterParam templateReporterParam);


}