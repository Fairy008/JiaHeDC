package com.jh.show.report.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 图层数据业务逻辑接口
 * @version<1> 2018-01-16 cxj: Created.
 */
public interface ILayerService {
    /**
     *  叠加栅格影像
     * 1. 直接加载给定URL的图层信息
     * 2. 加载对URL过滤后的图层信息,如filter= "code like 'USA%' and level='2'"
     * @return  png : 返回图层数据
     * @version <1> 2017年11月21日 14:30-cxw: Created.
     */
    void findGeoPng(HttpServletRequest request, HttpServletResponse response, String path);

    /**
     * 查询WMS图层数据
     * @return  json : 返回图层数据
     * @version <1> 2017年11月21日 14:30-cxw: Created.
     */
    Object findGeoJson(HttpServletRequest req, HttpServletResponse res, String path);

}
