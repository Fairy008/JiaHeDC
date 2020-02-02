package com.jh.briefingNew.mapping;

import com.jh.briefingNew.entity.BriefingHumidity;
import com.jh.briefingNew.entity.BriefingReport;
import com.jh.briefingNew.entity.BriefingSoilMoisture;
import com.jh.briefingNew.model.BriefingReportParam;
import com.jh.briefingNew.model.TrrmStatisticsMultRegionNew;
import com.jh.briefingNew.model.TtnRegionAvg;
import com.jh.briefingNew.model.chartDatas;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-07-20 wl: Created.
 */
@Mapper
public interface IBriefingReportMapper {

    /**
     *  根据 regionId（区域编号） 和 dateTime （创建时间）查询地温均值信息和对应得区域
     * @param briefingReportParam 查询参数
     * @return List<TtnRegionAvg>
     * @version <1> 2018-07-20 wl： Create
     */
    List<TtnRegionAvg> queryTtnByRegionAndDateTime(BriefingReportParam briefingReportParam);

    /**
     *  根据 regionId（区域编号） 和 dateTime （创建时间）查询有效积温和对应的区域
     * @param briefingReportParam 查询参数
     * @return List<TtnRegionAvg>
     * @version <1> 2018-07-20 wl： Create
     */
    List<TtnRegionAvg> queryTtnTotalByRegionAndDateTime(BriefingReportParam briefingReportParam);

    /**
     *  根据 regionId（区域编号） 和 dateTime （创建时间）查询地温均值信息 和历史均值信息
     * @param briefingReportParam 查询参数
     * @return List<chartDatas>
     * @version <1> 2018-07-20 wl： Create
     */
    List<chartDatas> queryTtnStatistics(BriefingReportParam briefingReportParam);

    /**
     *  根据 regionId（区域编号） 和 dateTime （创建时间）查询降雨均值信息 和历史均值信息
     * @param briefingReportParam 查询参数
     * @return List<chartDatas>
     * @version <1> 2018-07-24 wl： Create
     */
    List<chartDatas> queryTrmmStatistics(BriefingReportParam briefingReportParam);

    /**
     * 简报 分页
     *
     * @param briefingReportParam 查询参数
     * @return  List<BriefingReport>
     * @version <1> 2018-07-23 wl： Created.
     */
    List<BriefingReport> queryByPage(BriefingReportParam briefingReportParam);

    /**
     * 查询指定时间范围，周雨量信息
     * @param briefingReportParam 查询参数
     * @return 记录数据
     * @version <1> 2018-07-24 wl： Create
     */
    List<TrrmStatisticsMultRegionNew> queryTrrmStatisticsForWeek(BriefingReportParam briefingReportParam);


    /**
     * 查询指定时间范围，月雨量信息
     * @param briefingReportParam 查询参数
     * @return 记录数据
     * @version <1> 2018-07-24 wl： Create
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
     * 查询指定区域下指定时间段内的降雨总量
     * @return
     */
    List<TrrmStatisticsMultRegionNew> queryAvgTrrmMonthHistory( List<BriefingReportParam> params);


}
