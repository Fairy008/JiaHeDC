package com.jh.layer.service;

import com.github.pagehelper.PageInfo;
import com.jh.layer.entity.DsLayer;
import com.jh.layer.model.DsLayerParam;
import com.jh.vo.ResultMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 * 1.图层数据信息 接口
 *
 * @version <1> 2018-05-16 14:53 zhangshen: Created.
 */
public interface IDsLayerService{

    /**
     * 分页查询所有的图层信息
     * @param dsLayerParam 图层查询参数
     * @return 分页记录
     * @version<1> 2018-06-07 lxy: Created.
     */
    PageInfo<DsLayer> findLayerPageInfo(DsLayerParam dsLayerParam);

    /**
     * 根据图层编号查询对应的图层
     * @param layerId
     * @return 返回对应的图层（DsLayer）
     * @version<1> 2018-06-07 lxy: Created.
     */
    ResultMessage findDsLayerByLayerId(Integer layerId);

    /**
     * 发布图层
     * @param dsLayerParam 图层实体
     * @return
     * @version<1> 2018-05-17 wl: Created.
     * @version<2> 2018-06-07 lxy: Updated:修改了方法
     */
    ResultMessage publish(DsLayerParam dsLayerParam);

    /**
     * 发布图层
     * @param dsLayer
     * @return
     * @version<1> 2018-07-04 lcw :Created.
     *    说明：写该方法的原因是为了将DsLayerParam替换掉，每个bean不需要重新复制一个相同的bean
     */
    ResultMessage saveLayer(DsLayer dsLayer);

    /**
     * 修改图层样式
     * @param dsLayerParam
     * @return
     * @version<1> 2018-06-20 xzg: Created.
     */
    ResultMessage modifyLayerStyle(DsLayerParam dsLayerParam);

    /**
     * @description: 编辑图层明细
     * @param dsLayer 图层明细实体
     * @return
     * @version<1> 2018-05-18 wl: Created.
     * @version<2> 2018-07-04 lcw : 修改传递的对象
     */
    ResultMessage updateDsLayer(DsLayer dsLayer);

    /**
     * Description: 报告生成图层明细列表查询
     * @param reportCreateParam
     * @return
     * @version <1> 2018/5/22 16:49 zhangshen: Created.
     */
//    PageInfo<DsLayer> findReportCreateLayer(ReportCreateParam reportCreateParam);

    /**
     * 图层删除
     * 1、从geoserver 中删除图层
     * 2、删除图层表记录
     * 3、修改再加工数据表 数据状态为未发布
     * @param layerId 图层编号
     * @return 返回操作的记录数
     * @version<1> 2018-06-08 lxy: Created.
     */
    ResultMessage deleteDsLayerByLayerId(Integer layerId);

    /**
     * 查询图层
     * @param layer
     * @return
     * @verion<1> 2018-07-04 lcw :Created.
     */
    ResultMessage findDsLayer(DsLayer layer);



    /**
     *  @description: 叠加栅格影像
     *  1. 直接加载给定URL的图层信息
     *  2. 加载对URL过滤后的图层信息,如filter= "code like 'USA%' and level='2'"
     *  @return  png  返回地图底图
     *  @version<1> 2018-01-16 cxj: Created.
     */
    void findGeoPng(HttpServletRequest request, HttpServletResponse response, String path);

    /**
     * @description: 查询WMS图层数据
     * @return  json : 返回图层数据
     * @version<1> 2018-01-16 cxj: Created.
     */
    Object findGeoJson(String path);


    /**
     * 根据区域、作物、时间查询图层
     * 在当前区域下查询图层，如果没有查询父级区域
     * @param
     * cropId:作物ID
     * resolution：分辨率
     * dataSet:数据集
     * regionId：区域ID
     * layerDate：图层时间
     * @version <1> 2018-05-22 cxw: Created.
     */
    ResultMessage findLayer(Integer cropId, Integer dataSet, Integer resolution, Long regionId, String layerDate);

    /**
     * 新增或修改图层信息(通过再加工数据发布或者通过数据集影像数据发布图层)
     * @param dsLayer
     * @return
     * @version<1> 2018-7-17 lcw : Created.
     */
    ResultMessage saveOrUpdateLayer(DsLayer dsLayer);

    /**
     * 根据生产任务Ids获取图层名称
     * @param productIds
     * @return
     */
    ResultMessage findLayerByProductIds(Integer [] productIds);
    /**
     * 根据生产任务Ids撤回或删除图层
     * @param productIds
     * @return
     */
    ResultMessage backTif(Integer [] productIds,Integer type);
}
