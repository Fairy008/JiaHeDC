package com.jh.show.report.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * description:
 *
 * @version <1> 2018-06-24 wl: Created.
 */
public interface IBriefReportService {
    /**
     * 按照时间倒序查询简报列表
     * @param
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
     PageInfo<Map<String,Object>> findBriefReportByPage(Map<String, Object> map);

    /**
     * 根据区域获取最新简报ID
     * @param
     * @return ResultMessage :
            * @version <1> 2018-06-24 wl:Created.
     */
    public Long getNewBriefReport(Long regionId);

    /**
     * 分页查询简报数据
     * @param map
     * @return
     */
    PageInfo<Map<String,Object>> findBriefPage(Map<String, Object> map);


}
