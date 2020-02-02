package com.jh.briefingNew.service;

import com.github.pagehelper.PageInfo;
import com.jh.briefingNew.entity.BriefReportData;
import com.jh.briefingNew.entity.BriefingHumidity;
import com.jh.briefingNew.entity.BriefingReport;
import com.jh.briefingNew.entity.BriefingSoilMoisture;
import com.jh.briefingNew.model.BriefingReportParam;
import com.jh.briefingNew.model.TrrmStatisticsMultRegionNew;
import com.jh.briefingNew.model.TtnRegionAvg;
import com.jh.briefingNew.model.chartDatas;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-07-18 wl: Created.
 */
public interface IBriefingReportService {
    /**
     * 生成简报
     * @param briefingReportParam 简报参数
     * @return 操作结果
     * @version <1> 2018-07-18 wl： Created.
     */
    public ResultMessage createReport(BriefingReportParam briefingReportParam);

    /**
     *  根据 regionId（区域编号） 和 dateTime （创建时间）查询地温均值信息和对应得区域
     * @param briefingReportParam 查询参数
     * @return List<TtnRegionAvg> 地温数据
     * @version <1> 2018-07-20 wl： Created.
     */
    List<TtnRegionAvg> queryTtnByRegionAndDateTime(BriefingReportParam briefingReportParam);

    /**
     *  根据 regionId（区域编号） 和 dateTime （创建时间）查询有效积温和对应的区域
     * @param briefingReportParam 查询参数
     * @return List<TtnRegionAvg> 地温数据
     * @version <1> 2018-07-20 wl： Created.
     */
    List<TtnRegionAvg> queryTtnTotalByRegionAndDateTime(BriefingReportParam briefingReportParam);


    /**
     *  根据 regionId（区域编号） 和 dateTime （创建时间）查询地温均值信息和历史地温信息
     * @param briefingReportParam 查询参数
     * @return List<TtnDatas> 地温数据
     * @version <1> 2018-07-20 wl： Created.
     */
    List<chartDatas> queryTtnStatistics(BriefingReportParam briefingReportParam);

    /**
     *  根据 regionId（区域编号） 和 dateTime （创建时间）查询降雨均值信息和历史降雨信息
     * @param briefingReportParam 查询参数
     * @return List<chartDatas> 降雨数据
     * @version <1> 2018-07-24 wl： Created.
     */
    List<chartDatas> queryTrmmStatistics(BriefingReportParam briefingReportParam);

    /**
     * 简报分页查询
     * @param briefingReportParam
     * @return
     * @version <1> 2018-07-23 wl： Created.
     */
    PageInfo<BriefingReport> findByPage(BriefingReportParam briefingReportParam);

    /**
     * 查询指定时间范围，周、月雨量信息
     * @param briefingReportParam 简报参数
     * @return 雨量统计多区域
     */
    List<TrrmStatisticsMultRegionNew> queryTrrmStatisticsForWeek(BriefingReportParam briefingReportParam);

    /**
     * 查询指定时间范围，月雨量信息
     * @param briefingReportParam 简报参数
     * @return 雨量统计多区域
     * @version <1> 2018-07-24 wl： Created.
     */
    List<TrrmStatisticsMultRegionNew> queryTrrmStatisticsForMonth(BriefingReportParam briefingReportParam);

    /**
     * 查询所有的湿度信息
     * @return List<BriefingHumidity>
     */
    List<BriefingHumidity> queryAllHumidity();

    /**
     * 查询所有的墒情信息
     * @return List<BriefingSoilMoisture>
     */
    List<BriefingSoilMoisture> queryAllSoilMoisture();

    /**
     * 批量生成图表简报
     * @param briefingReportParam 简报参数
     * @return 操作结果
     * @version <1> 2018-07-25 wl： Created.
     */
    public ResultMessage batchCreateReport(BriefingReportParam briefingReportParam,BriefReportData briefReportData);


    /**
     * 生成简报
     * @param briefingReportParam 简报参数
     * @return 操作结果
     * @version <1> 2018-07-18 wl： Created.
     */
    public ResultMessage queryReport(BriefingReportParam briefingReportParam);

    /**
     * 生成简报（不包含降雨量和墒情）
     * @param briefingReportParam 简报参数
     * @return 操作结果
     * @version <1> 2018-07-18 wl： Created.
     */
    public ResultMessage queryReportNew(BriefingReportParam briefingReportParam,String isTrrm);

}
