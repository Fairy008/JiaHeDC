package com.jh.layer.service;

import com.github.pagehelper.PageInfo;
import com.jh.layer.entity.DsGeoStyle;
import com.jh.layer.model.DsGeoStyleParam;
import com.jh.vo.ResultMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 * 1.样式管理service接口
 *
 * @version <1> 2018-06-19 15:28 zhangshen: Created.
 */
public interface IDsGeoStyleService {
    
    /**
     * Description: 查询样式列表
     * @param dsGeoStyleParam 
     * @return PageInfo<DsGeoStyle>
     * @version <1> 2018/6/19 15:32 zhangshen: Created.
     */
    PageInfo<DsGeoStyle> findDsGeoStylePageInfo(DsGeoStyleParam dsGeoStyleParam);

    /**
     * Description: 查询geoserver工作区列表, 将配置文件中config.properties配置的geoserver工作区 放在list第一个
     * @param
     * @return ResultMessage
     * @version <1> 2018/6/20 9:24 zhangshen: Created.
     */
    ResultMessage findGeoWorkspaceList();

    /**
     * Description: 根据id查询样式信息
     * @param styleId
     * @return ResultMessage 样式信息
     * @version <1> 2018/6/20 11:13 zhangshen: Created.
     */
    ResultMessage findDsGeoStyleById(Integer styleId);

    /**
     * Description: 保存样式信息
     * @param request
     * @param dsGeoStyle
     * @return ResultMessage
     * @version <1> 2018/6/20 13:34 zhangshen: Created.
     */
    ResultMessage saveStyleInfo(HttpServletRequest request, DsGeoStyle dsGeoStyle);

    /**
     * Description: 根据样式id集删除样式
     * @param styleIds 样式id集
     * @param geoStyle
     * @return 
     * @version <1> 2018/6/20 16:40 zhangshen: Created.
     */
    ResultMessage batchDeleteStyle(String styleIds, DsGeoStyle geoStyle);

    /**
     * 获取geoserver的样式
     * @return
     */
    ResultMessage getGeoStyleList();

    /**
     * Description: 根据styleName查询样式信息
     * @param styleName
     * @return ResultMessage 样式信息
     * @version <1> 2018/7/31 17:13 zhangshen: Created.
     */
    ResultMessage findDsGeoStyleByName(String styleName);
}
