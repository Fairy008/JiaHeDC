package com.jh.manage.order.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.manage.order.entity.DataOrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IDataOrderDetailMapper extends IBaseMapper<DataOrderDetail , DataOrderDetail , Integer> {

    /**
     *创建数据订单详情
     * @param dataOrderDetail 数据订单详情对象：
     * @return ResultMessage
     * @version <1> 2018-03-12 cxw： Created.
     */
    int  insertDataOrderDetail(DataOrderDetail dataOrderDetail);

    /**
     *删除订单详情
     * @param orderId 订单Id
     * @return ResultMessage
     * @version <1> 2018-03-13 cxw： Created.
     */
    int delOrderDetailByOrderId(Integer orderId);

    /**
     *数据订单详情批量插入
     * @param dataOrderDetails 数据订单详情对象：
     * @return ResultMessage
     * @version <1> 2018-03-16 cxw： Created.
     */
    int insertDataOrderDetailList(List<DataOrderDetail> dataOrderDetails);

    /**
     * 根据订单ID查询订单明细
     * @param orderId
     * @return
     */
    List<DataOrderDetail> findOrderListByOrderId(Integer orderId);

    DataOrderDetail findOrderDetailById(Integer orderDetailId);
}