package com.jh.produce.process.mapping;

import com.jh.produce.process.entity.HandleConfig;
import com.jh.produce.process.model.HandleConfigParam;
import com.jh.base.repository.IBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface IHandleConfigMapper extends IBaseMapper<HandleConfigParam, HandleConfig, Integer>{
    /**
     * 算法配置分页查询
     *
     * @param handleConfigParam 算法配置参数
     * @return
     * @version <1> 2018-02-07 djh： Created.
     */
    List<HandleConfigParam> queryByPage(HandleConfigParam handleConfigParam);

    /**
     * 通过ID集合，查询算法列表数据
     * @param handleConfigList
     * @return
     * @version <1> 2018-03-14 cxj： Created.
     */
    List<HandleConfig> findHandleConfigByIdList(@Param(value = "handleConfigList") List<Integer> handleConfigList);

    /**
     * 通过卫星ID，查询相关算法列表数据
     * @param satId
     * @return
     * @version <1> 2018-03-19 cxj： Created.
     */
    List<HandleConfig> findHandleConfigListBySatId(Integer satId);

    /**
     * 保存卫星关联数据
     * @param stats
     * @return
     * @version <1> 2018/4/3 lxy :created
     */
    void saveRelateHandleSat(@Param(value = "stats") List<HandleConfigParam> stats);

    /**
     * 按照handleMetaId 删除指定的卫星
     * @param handleMetaId
     * @version <1> 2018/4/3 lxy :created
     */
    void deleteRelateHandleSatByHandleMetaId(Integer handleMetaId);

    /**
     * 根据handleMetaId查询指定的卫星
     * @param handleMetaId
     * @return
     * @version <1> 2018/4/3 lxy :created
     */
    List<Map<String,Object>> findRelateHandleSatByHandleMetaId(Integer handleMetaId);

    /**
     * 根据卫星ID ，查询正常的 ，有效算法配置列表数据
     * @param satId
     * @return
     */
    List<HandleConfig> findHandlesBySatId(Integer satId);
}