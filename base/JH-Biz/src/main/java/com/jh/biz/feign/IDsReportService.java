package com.jh.biz.feign;

import com.github.pagehelper.PageInfo;
import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * description:报告查询服务:
 * 1.根据区域、作物，时间，精度分页查询所有报告
 * 2.根据区域、作物，时间，精度查询所有报告（时间倒序）
 * 3.预览pdf文件报告
 * @version <1> 2018-08-10 cxw: Created.
 */
@FeignClient(name="jh-publish")
public interface IDsReportService {

    @GetMapping("/dsReport/queryReportPage")
    public PageInfo<Map<String,Object>> queryReportPage(	@RequestParam(name="pageNum")int pageNum,
                                                            @RequestParam(name="pageSize")int pageSize,
                                                            @RequestParam(name="regionId")Long regionId,
                                                            @RequestParam(name="dsId")Integer dsId,
                                                            @RequestParam(name="accuracyId")Integer accuracyId,
                                                            @RequestParam(name="cropId")Integer cropId,
                                                            @RequestParam(name="startDate")String startDate,
                                                            @RequestParam(name="endDate")String endDate);

    @GetMapping("/dsReport/queryAllReport")
    public ResultMessage queryAllReport(@RequestParam(name="regionId")Long regionId,
                                        @RequestParam(name="dsId")Integer dsId,
                                        @RequestParam(name="accuracyId")Integer accuracyId,
                                        @RequestParam(name="cropId")Integer cropId,
                                        @RequestParam(name="dataTime")String dataTime
                                                             );

    @GetMapping("/dsReport/previewReportPdf")
    public void previewReportPdf(@RequestParam(name="reportId") Integer reportId , @RequestParam(name="response") HttpServletResponse  response);

    /**
     * 按照时间倒序查询报告列表
     * @param
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
    @GetMapping("/dsReport/findReportShowByPage")
    public PageInfo<Map<String,Object>> findReportShowByPage(@RequestParam(name="page")Integer page,
                                                             @RequestParam(name="rows")Integer rows,@RequestParam(name="publish_status") Integer publish_statusm,@RequestParam(name="regionId") Long regionId);

    /**
     * 根据报告id查询报告详情
     * @param  reportId
     * @return Map<String,Object>
     * @version <1> 2018-06-24 wl:Created.
     */
    @GetMapping("/dsReport/findReportShowById")
    public ResultMessage findReportShowById(@RequestParam(name="reportId")Integer reportId);

}
