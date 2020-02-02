package com.jh.manage.order.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.manage.order.entity.DataOrder;
import com.jh.manage.order.model.OrderParam;
import com.jh.manage.storage.model.StorageParam;
import com.jh.vo.ResultMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IDataOrderMapper extends IBaseMapper<OrderParam , DataOrder, Integer> {

    /**
     * 订单分发分页查询
     * @param orderParam
     * @return PageInfo
     * @version <1> 2018-03-07 cxw： Created.
     */
    List<DataOrder> findOrderHandleByPage(OrderParam orderParam);

    /**
     * 订单审核分页查询
     * @param orderParam
     * @return PageInfo
     * @version <1> 2018-03-07 cxw： Created.
     */
    List<DataOrder> findOrderAuditByPage(OrderParam orderParam);

    /**
     * 订单列表分页查询
     * @param orderParam
     * @return PageInfo
     * @version <1> 2018-03-07 cxw： Created.
     */
    List<DataOrder> findOrderListByPage(OrderParam orderParam);

    /**
     *订单详情查询
     * @param orderId 订单ID
     * @return DataOrder
     * @version <1> 2018-03-07 cxw： Created.
     */
    public DataOrder findOrderById(Integer orderId);

    /**
     *订单更新
     * @param orderParam 订单
     * @return DataOrder
     * @version <1> 2018-03-07 cxw： Created.
     */
    public int updateByPrimaryKeySelective(OrderParam orderParam);

    /**
     *创建数据订单
     * @param dataOrder 订单参数：
     * @return ResultMessage
     * @version <1> 2018-03-12 cxw： Created.
     */
    public Integer insertDataOrder(DataOrder dataOrder);

    /**
     *删除订单
     * @param orderId 订单Id
     * @return ResultMessage
     * @version <1> 2018-03-13 cxw： Created.
     */
    public int delOrderById(Integer orderId);

    /**
     *修改订单
     * @param dataOrder 订单对象
     * @return ResultMessage
     * @version <1> 2018-03-13 cxw： Created.
     */
    public int updateOrder(DataOrder dataOrder);

    /**
     * 查询所有过期订单
     * @param dataAccessTime
     * @return
     * @version<1> 2018-03-20 lcw :Created.
     */
    List<DataOrder> findOverdueOrder(String dataAccessTime);
    /**
     *统计系统中需求订单和数据订单总数
     * @param  orderParam 订单对象
     * @version <1> 2018-04-17 wl： Created.
     */
    List<Map<String, Object>>queryOrderCount(OrderParam orderParam);
    /**
     *统计需求订单或者数据订单 不同状态的订单数量
     * @param  orderParam 订单对象
     * @version <1> 2018-04-17 wl： Created.
     */
    List<Map<String, Object>>queryOrderDetailCount(OrderParam orderParam);
    /**
     *统计数据订单不同卫星类型的已分发数量
     * @param  orderParam 订单对象
     * @version <1> 2018-04-17 wl： Created.
     */
    List<Map<String, Object>>queryDataOrderBySatellite(OrderParam orderParam);

    /**
     *统计数据订单不同卫星类型的已分发数量前五
     * @param  orderParam 订单对象
     * @version <1> 2018-06-13 wl： Created.
     */
    List<Map<String, Object>>queryDataOrderByTopFive(OrderParam orderParam);

    /**
     *统计数据订单不同卫星类型的已分发总数
     * @version <1> 2018-06-13 wl： Created.
     */
    List<Map<String, Object>>queryDataOrderSum();

    Integer queryOrderFileNumByDay(OrderParam orderParam);

    List<Map<String,Object>> queryOrderStatistics(OrderParam orderParam);

    List<Map<String,Object>> queryNotAuditList(OrderParam orderParam);

    List<Map<String,Object>> queryAuditList(OrderParam orderParam);

    Map<String,Object> queryOrderForApp(Integer orderId);

    List<Map<String,Object>> queryIfAuditStatistics(OrderParam orderParam);

    List<Map<String,Object>> queryIfAuditByPerson(OrderParam orderParam);

    /**
     * 分状态统计订单数据，包括每种状态下的订单数，和当前时间段的订单数
     * @param orderParam
     * @return
     */
    List<Map<String,Object>> queryDataForReport(OrderParam orderParam);
}