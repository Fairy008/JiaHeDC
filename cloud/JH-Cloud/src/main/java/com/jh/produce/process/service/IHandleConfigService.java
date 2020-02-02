package com.jh.produce.process.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseService;
import com.jh.produce.process.entity.HandleConfig;
import com.jh.produce.process.model.HandleConfigParam;
import com.jh.vo.ResultMessage;

import java.util.List;
import java.util.Map;

/**
 * description:  算法配置管理
 *
 * @version <1> 2018-01-26 lcw： Created.
 */
public interface IHandleConfigService extends IBaseService<HandleConfigParam, HandleConfig, Integer> {


    /**
     * 算法配置分页查询
     * @param handleConfigParam
     * @return
     * @version <1> 2018-02-06 lcw： Created.
     * @version <2> 2018-02-07 djh： 修改返回值的类型.
     */
    PageInfo<HandleConfigParam> findByPage(HandleConfigParam handleConfigParam);

    /**
     * 查询所有算法
     * @param handleConfigParam
     * @return
     * @version <1> 2018-03-07 cxj： Created.
     */
    ResultMessage queryAll(HandleConfigParam handleConfigParam);

    /**
     * 通过ID集合，查询算法列表数据
     * @param handleConfigList
     * @return
     * @version <1> 2018-03-14 cxj： Created.
     */
    List<HandleConfig> findHandleConfigByIdList(List<Integer> handleConfigList);

    /**
     * 通过卫星ID，查询相关算法列表数据
     * @param satId
     * @return
     * @version <1> 2018-03-19 cxj： Created.
     */
    List<HandleConfig> findHandleConfigListBySatId(Integer satId);

    /**
     * 通过卫星ID ，查询有效的 正常 算法列表数据
     * @param satId
     * @return
     * @version <1> 2018-04-11 xzg： Created.
     */
    List<HandleConfig> findHandlesBySatId(Integer satId);


    /**
     * 保存卫星关联数据
     * @param handleConfigs
     * @return
     */
    Boolean saveRelateHandleSat(List<HandleConfigParam> handleConfigs);


    /**
     * 根据handleMetaId查询指定的卫星
     * @param handleMetaId
     * @return
     * @version <1> 2018/4/3 lxy :created
     */
    List<Map<String,Object>> findRelateHandleSatByHandleMetaId(Integer handleMetaId);

    /**
     * 按照handleMetaId 删除指定的卫星
     * @param handleMetaId
     * @version <1> 2018/4/3 lxy :created
     */
    void deleteRelateHandleSatByHandleMetaId(Integer handleMetaId);
}
