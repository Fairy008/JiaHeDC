package com.jh.report.service;

import com.github.pagehelper.PageInfo;
import com.jh.data.model.ReportCreateParam;
import com.jh.report.entity.DsReport;
import com.jh.report.model.DsReportParam;
import com.jh.vo.ResultMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Description:
 * 1.报告 接口
 *
 * @version <1> 2018-05-13 11:27 zhangshen: Created.
 */
public interface IDsReportService {

    /**
     * Description: 根据条件查询报告是否已经生成
     * @param reportCreateParam
     * @return
     * @version <1> 2018/7/19 13:40 zhangshen: Created.
     */
    ResultMessage checkReportIsExist(ReportCreateParam reportCreateParam);

    /**
    * Description: 创建报告
    * @version <1> 2018-5-13 11:29 zhangshen: Created.
    */
    ResultMessage createReportFun(ReportCreateParam reportCreateParam);

    /**
     * Description: 查询报告信息
     * @param dsReportParam
     * @return
     * @version <1> 2018/5/15 14:56 zhangshen: Created.
     */
    PageInfo<DsReport> findDsReportByPage(DsReportParam dsReportParam);

    /**
     * Description: 预览pdf文件报告
     * @param filePath 文件路径
     * @param response
     * @return
     * @version <1> 2018/5/16 15:26 zhangshen: Created.
     */
    void previewReportPdf(String filePath, HttpServletResponse response);

    /**
     * Description: 根据reportId查询DsReport
     * @param reportId
     * @return
     * @version <1> 2018/5/18 10:07 zhangshen: Created.
     */
    DsReport findDsReportById(Integer reportId);

    /**
     * Description: 根据报告ids,批量发布报告
     * @param reportIds
     * @return
     * @version <1> 2018/5/18 15:34 zhangshen: Created.
     */
    ResultMessage batchPublishReport(String reportIds, DsReport dsReport);

    /**
     * Description: 编辑报告
     * @param request
     * @param dsReport
     * @return
     * @version <1> 2018-05-21 zhangshen : Created.
     */
    ResultMessage updateReportById(HttpServletRequest request, DsReport dsReport);

    /**
     * Description: 根据报告ids,批量撤回报告
     * @param reportIds
     * @return
     * @version <1> 2018/5/24 11:34 zhangshen: Created.
     */
    ResultMessage batchCancelReport(String reportIds);

    /**
     * Description: 根据报告ids,批量删除报告
     * @param reportIds
     * @return
     * @version <1> 2018/5/24 11:34 zhangshen: Created.
     */
    ResultMessage batchDeleteReport(String reportIds);

    /**
     * Description: 根据路径查看文件word文件是否存在
     * @param path 路径
     * @param suffix 后缀
     * @return 
     * @version <1> 2018/6/6 14:02 zhangshen: Created.
     */
    boolean findWordIsExist(String path, String suffix);

    /**
     * Description: 根据条件查询数据时间
     * @param reportCreateParam
     * @return
     * @version <1> 2018/7/18 17:10 zhangshen: Created.
     */
    ResultMessage queryDateTimeList(ReportCreateParam reportCreateParam);


    /**
     * 分页条件查询审批通过的报告
     * @param pageNum 当前页
     * @param pageSize 每页显示多少条
     * @param regionId  区域ID
     * @param dataSetId 数据集ID
     * @param accuracyId 分辨率ID
     * @param cropId   作物ID
     * @param startDate  报告数据开始时间
     * @param endDate    报告数据结束时间
     * @param token    用户登录token 标识
     * @return
     * @version <1> 2018-05-10 xzg : Created.
     */
    PageInfo<Map<String,Object>> findReportPage(int pageNum,int pageSize,Long regionId,Integer dataSetId,Integer accuracyId,Integer cropId,String startDate,String endDate,String token);

    /**
     * 分页条件查询审批通过的报告
     * @param pageNum 当前页
     * @param pageSize 每页显示多少条
     * @param regionId  区域ID
     * @param dataSetId 数据集ID
     * @param accuracyId 分辨率ID
     * @param cropId   作物ID
     * @param startDate  报告数据开始时间
     * @param endDate    报告数据结束时间
     * @return
     * @version <1> 2018-08-10 cxw : Created.
     */
    public PageInfo<Map<String, Object>> findDsReportPage(int pageNum, int pageSize, Long regionId, Integer dataSetId, Integer accuracyId, Integer cropId, String startDate,String endDate);

    /**
     * 查询所有审批通过的报告
     * @param regionId  区域ID
     * @param dataSetId 数据集ID
     * @param accuracyId 分辨率ID
     * @param cropId   作物ID
     * @param dataTime  报告时间（年）
     * @return
     * @version <1> 2018-08-10 cxw : Created.
     */
    ResultMessage findAllReport(Long regionId,Integer dataSetId,Integer accuracyId,Integer cropId,String  dataTime);

    /**
     * 按照时间倒序查询报告列表
     * @param
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
    PageInfo<Map<String,Object>> findReportShowByPage(Map<String, Object> map);

    /**
     * 根据报告id查询报告详情
     * @param  reportId
     * @return Map<String,Object>
     * @version <1> 2018-06-24 wl:Created.
     */
    ResultMessage findReportShowById(Integer reportId);

    /**
     * 返回所有的报告种类
     * @return
     */
    ResultMessage findAllReportTempType();


}
