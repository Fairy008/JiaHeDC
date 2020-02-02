package com.jh.show.report.service;

import com.github.pagehelper.PageInfo;
import com.jh.vo.ResultMessage;

import java.util.List;
import java.util.Map;

/**
 * description:查询报告信息
 *
 * @version <1> 2018-06-24 wl: Created.
 */
public interface IReportService {
    /**
     * 按照时间倒序查询报告列表
     * @param
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
    PageInfo<Map<String,Object>> findReportByPage(Map<String, Object> map);

    /**
     * 根据报告id查询报告详情
     * @param  reportId
     * @return Map<String,Object>
     * @version <1> 2018-06-24 wl:Created.
     */
    Map<String,Object> findReportById(Integer reportId);

    /**
     * 查询报告时间轴 仅查询ds_area 表中的有效数据
     * @param map
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
    public ResultMessage findTimeAxis(Map<String, Object> map);

    public ResultMessage findTimeAxisByDraw(List<Long> list);

    /**
     * 查询图层
     * @param  map   //数据时间
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
    public ResultMessage findLayers(Map<String, Object> map);

    /**
     * @description: 保存用户绘制的区域边界信息
     * @param map 用户绘制边界  自定义区域名称
     * @return
     * @version <1> 2018-06-25 wl:Created.
     */
    ResultMessage saveRegionByUser(Map<String, Object> map);

    /**
     * 查询去年分布信息和今年分布信息
     * @param  bbox //区域边界信息
     * @return ResultMessage :
     * @version <1> 2018-06-25 wl:Created.
     */
    public ResultMessage findAreas(String bbox);


    /**
     * 根据id查询用户自定义区域的信息
     * @param  map
     * @return Map<String,Object>
     * @version <1> 2018-06-26 wl:Created.
     */
    Map<String,Object> findCustomLocale(Map<String, Object> map);

    /**
     * 根据id查询用户自定义区域的信息
     * @param  map
     * @return Map<String,Object>
     * @version <1> 2018-06-26 wl:Created.
     */
    List<Map<String, Object>> findCustomLocaleList(Map<String, Object> map);

    ResultMessage findCustomRegionArea(String bbox, Long regionId, String dataTime);

    /**
     * 修改用户关注区域（删除关注区域下的自定义区域）
     * @param userRegion
     * @return
     */
    ResultMessage updateUserRegion(Map<String, Object> userRegion);

    /**
     * 新增用户关注区域
     * @param userRegion
     * @return
     */
    ResultMessage insertUserRegion(Map<String,Object> userRegion);


}
