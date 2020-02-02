package com.jh.show.report.mapping;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * description:查询报告信息
 *
 * @version <1> 2018-06-24 wl: Created.
 */
@Mapper
public interface IReportMapper {
    /**
     * 根据发布状态查询报告列表
     * @param  reportMap
     * @return List<Map<String,Object>>
     * @version <1> 2018-06-24 wl:Created.
     */
    List<Map<String,Object>> findReportByPage(Map<String, Object> reportMap);

    /**
     * Description: 根据报告id查询报告详情
     * @param reportId
     * @return Map<String,Object>
     * @version <1> 2018-06-24 wl:Created.
     */
    Map<String,Object> findReportById(Integer reportId);

    /**
     * 查询时间轴
     * @param  reportMap
     * @return List<Map<String,Object>>
     * @version <1> 2018-06-24 wl:Created.
     */
    List<Map<String,Object>> findTimeAxis(Map<String, Object> reportMap);
    List<Map<String,Object>> findTimeAxisByDraw(List<Long> list);

    /**
     * 查询图层
     * @param  layersMap
     * @return List<Map<String,Object>>
     * @version <1> 2018-06-24 wl:Created.
     */
    List<Map<String,Object>> findLayers(Map<String, Object> layersMap);


    /**
     * @description: 保存自定义区域信息
     * @param map 自定义区域名称和边界信息
     * @return
     * @version <1> 2018-06-24 wl:Created.
     */
    Integer saveRegionByUser(Map<String, Object> map);

    /**
     * 查询作物分布情况 按年
     * @param  areasMap
     * @return List<Map<String,Object>>
     * @version <1> 2018-06-25 wl:Created.
     */
    List<Map<String,Object>> findAreaByYear(Map<String, Object> areasMap);

    /**
     * 查询绘制区域信息  根据id
     * @param  map
     * @return Map<String,Object>
     * @version <1> 2018-06-26 wl:Created.
     */
    Map<String,Object> findCustomLocale(Map<String, Object> map);

    /**
     * 查询绘制区域信息  根据id
     * @param  map
     * @return Map<String,Object>
     * @version <1> 2018-06-27 wl:Created.
     */
    List<Map<String,Object>> findCustomLocaleList(Map<String, Object> map);
    /**
     * 查询用户选择的区域内 一定时间的作物信息
     * @param  map
     * @return Map<String,Object>
     * @version <1> 2018-06-278 wl:Created.
     */
    List<Map<String,Object>> findLayersCrop(Map<String, Object> map);

    /**
     * 更新用户的关注区域
     * @param params key {regionId , userId , regionType}
     * @return
     * @version <1> 2018-07-05 xzg:Created.
     */
    Integer updateCustomLocale(Map<String, Object> params);

    /**
     * 删除用户的自定义区域
     * @param params key {userId , regionType}
     * @return
     * @version <1> 2018-07-05 xzg:Created.
     */
    Integer deleteCustomLocale(Map<String, Object> params);

}
