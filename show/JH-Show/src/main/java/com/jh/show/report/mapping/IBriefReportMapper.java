package com.jh.show.report.mapping;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * description:简报查询
 *
 * @version <1> 2018-06-24 wl: Created.
 */
@Mapper
public interface IBriefReportMapper {
    /**
     * 根据发布状态查询简报列表
     * @param  briefMap
     * @return List<Map<String,Object>>
     * @version <1> 2018-06-24 wl:Created.
     */
    List<Map<String,Object>> findBriefReportByPage(Map<String, Object> briefMap);

    /**
     * Description: 根据简报id查询简报详情
     * @param reportId
     * @return Map<String,Object>
     * @version <1> 2018-06-24 wl:Created.
     */
    Map<String,Object> findBriefReportById(Integer reportId);

    /**
     * 分页查询简报数据
     * @param briefMap
     * @return
     */
    List<Map<String,Object>> findBriefPage(Map<String,Object> briefMap);


}
