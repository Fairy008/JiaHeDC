package com.jh.report.mapping;

import com.jh.data.model.ReportCreateParam;
import com.jh.report.entity.DsReport;
import com.jh.report.model.DsReportParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * 1.报告Mapper
 *
 * @version <1> 2018-05-14 14:45 zhangshen: Created.
 */
@Mapper
public interface IDsReportMapper {

    /**
     * Description: 根据条件查询报告是否已经生成
     * @param reportCreateParam
     * @return
     * @version <1> 2018/7/19 13:40 zhangshen: Created.
     */
    List<DsReport> checkReportIsExist(ReportCreateParam reportCreateParam);

    /**
     * Description: 保存报告信息
     * @param dsReport
     * @return
     * @version <1> 2018/5/14 14:47 zhangshen: Created.
     */
    void saveReportInfo(DsReport dsReport);

    /**
     * Description: 查询报告信息
     * @param dsReportParam
     * @return
     * @version <1> 2018/5/15 14:59 zhangshen: Created.
     */
    List<DsReport> findDsReportByPage(DsReportParam dsReportParam);

    /**
     * Description: 根据reportId查询DsReport
     * @param reportId
     * @return
     * @version <1> 2018/5/18 10:12 zhangshen: Created.
     */
    DsReport findDsReportById(Integer reportId);

    /**
     * Description: 批量发布报告
     * @param rIds reportId集合
     * @param dsReport 报告参数
     * @return 
     * @version <1> 2018/5/18 15:50 zhangshen: Created.
     */
    void batchPublishReport(@Param("rIds") List<Integer> rIds, @Param("dsReport") DsReport dsReport);

    /**
     * Description: 上传更新报告信息
     * @param dsReport
     * @return
     * @version <1> 2018/5/21 16:47 zhangshen: Created.
     */
    void updateReportInfo(DsReport dsReport);

    /**
     * Description: 根据报告ids,批量撤回报告
     * @param rIds reportId集合
     * @param dsReport 报告参数
     * @return
     * @version <1> 2018/5/24 13:50 zhangshen: Created.
     */
    void batchCancelReport(@Param("rIds") List<Integer> rIds, @Param("dsReport") DsReport dsReport);

    /**
     * Description: 根据报告ids,批量删除报告
     * @param rIds reportId集合
     * @param dsReport 报告参数
     * @return
     * @version <1> 2018/5/24 13:50 zhangshen: Created.
     */
    void batchDeleteReport(@Param("rIds") List<Integer> rIds, @Param("dsReport") DsReport dsReport);

    List<Map<String,Object>> findReportPageByParam(Map<String, Object> params);

    List<Map<String,Object>> findReportPage(Map<String, Object> params);

    List<Map<String,Object>> findAllReport(Map<String, Object> params);

    /**
     * 根据发布状态查询报告列表
     * @param  reportMap
     * @return List<Map<String,Object>>
     * @version <1> 2018-06-24 wl:Created.
     */
    List<Map<String,Object>> findReportShowByPage(Map<String, Object> reportMap);

    /**
     * Description: 根据报告id查询报告详情
     * @param reportId
     * @return Map<String,Object>
     * @version <1> 2018-06-24 wl:Created.
     */
    Map<String,Object> findReportShowById(Integer reportId);
}
