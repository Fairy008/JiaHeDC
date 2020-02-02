package com.jh.layer.mapping;

import com.jh.layer.entity.DsLayer;
import com.jh.layer.model.DsLayerParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * 1.图层数据信息 Mapper
 *
 * @version <1> 2018-05-16 14:55 zhangshen: Created.
 */
@Mapper
public interface IDsLayerMapper {
    /**
     * @description: 查询图层明细
     * @param dsLayerParam 图层明细对象
     * @version <1> 2018-05-18 wl： Created.
     */
    List<DsLayer> findByPage(DsLayerParam dsLayerParam);

    /**
     * 发布和撤回图层
     * @param dsLayerParam 发布人名称  发布人id  发布状态（发布、撤销）
     * @return 修改操作记录数据
     * @version <1> 2018-05-17 wl： Created.
     * @version <1> 2018-06-07 lxy： Updated. 修改了传过来的参数
     */
    int publish(DsLayerParam dsLayerParam);

    /**
     * @description: 发布单条图层
     * @param dsLayer 分布明细对象
     * @return
     * @version <1> 2018-05-17 wl： Created.
     */
    int updateDsLayer(DsLayer dsLayer);

//    /**
//     * Description: 报告生成图层明细列表查询
//     * @param reportCreateParam
//     * @return
//     * @version <1> 2018/5/22 16:49 zhangshen: Created.
//     */
//    List<DsLayer> findReportCreateLayer(ReportCreateParam reportCreateParam);

    /**
     * 保存图层
     * @param layer 图层表
     * @return 返回对应的主键
     * @version <1> 2018-06-06 lxy: Created.
     */
    int saveLayer(DsLayer layer);

    /**
     * 根据图层编号返回对应的图层
     * @param layerId 图层编号
     * @return 返回图层
     * @version <1> 2018-06-06 lxy: Created.
     */
    DsLayer findDsLayerByLayerId(Integer layerId);

    /**
     * 根据图层编号删除图层
     * @param layerId 图层编号
     * @return 返回删除记录数
     * @version <1> 2018-06-06 lxy: Created.
     */
    int deleteDsLayerByLayerId(@Param("layerId") Integer layerId);

    /**
     * 保存关联数据
     * @param layers 图层关联数据
     * @return 保存的记录数
     * @version <1> 2018-06-06 lxy: Created.
     */
    int insertRelateReprocessLayer(@Param("layers") List<Map<String, Object>> layers);

    /**
     * 批量删除再加工数据与图层关系-按照ReprocessId集合
     * @param layers 图层关联数据
     * @return 返回删除记录条数
     * @version <1> 2018-06-06 lxy: Created.
     */
    int deleteRelateReprocessLayerByReprocessIds(@Param("layers") List<Map<String, Object>> layers);

    /**
     * 批量删除再加工数据与图层关系-按照layerIds集合
     * @param layers 图层关联数据
     * @return 返回删除记录条数
     * @version <1> 2018-06-06 lxy: Created.
     */
    int deleteRelateReprocessLayerByLayerIds(@Param("layers") List<Map<String, Object>> layers);

    /**
     * 根据再处理加工数据来删除图层数据
     * @param reprocessId 再处理加工数据编号
     * @return 返回删除的数据
     * @version <1> 2018-06-06 lxy: Created.
     */
    int deleteDsLayerByRelateReprocessId(@Param("reprocessId") Integer reprocessId);

    DsLayer findDsLayer(DsLayer dsLayer);

    List<Map<String,Object>> findLayer(Map<String, Object> param);

    /**
     * 根据id集合查询图层名称集合
     * @param productIds
     * @return
     */
    List<DsLayer> findLayerByProductIds(Integer [] productIds);
}
