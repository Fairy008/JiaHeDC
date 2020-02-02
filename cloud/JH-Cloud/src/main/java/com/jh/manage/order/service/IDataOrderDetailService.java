package com.jh.manage.order.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseService;
import com.jh.manage.order.entity.DataOrderDetail;
import com.jh.manage.storage.entity.DataStorage;
import com.jh.manage.storage.model.StorageParam;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
*数据订单服务：
* 1.创建订单明细
* @version <1> 2018-03-12 cxw:Created.
*/
public interface IDataOrderDetailService extends IBaseService<DataOrderDetail, DataOrderDetail, Integer> {

	/**
	 *创建数据订单详情
	 * @param dataOrderDetail 数据订单详情对象：元数据对象ID参数为必须获取参数，不能为空
	 * @return ResultMessage
	 * @version <1> 2018-03-12 cxw： Created.
	 */
	public ResultMessage saveDataOrderDetail(DataOrderDetail dataOrderDetail);

	/**
	 *批量插入数据订单详情
	 * @param dataOrderDetails 数据订单详情对象：元数据对象ID参数为必须获取参数，不能为空
	 * @return ResultMessage
	 * @version <1> 2018-03-16 cxw： Created.
	 */
//	public ResultMessage saveDataOrderDetailList(List<DataOrderDetail> dataOrderDetails);

	/**
	 *删除订单详情
	 * @param orderId 订单Id
	 * @return ResultMessage
	 * @version <1> 2018-03-13 cxw： Created.
	 */
	public ResultMessage delOrderDetailByOrderId(Integer orderId);

	/**
	 * 查询订单详情：根据订单主键查询订单关联文件详情
	 * @param orderId 订单ID
	 * @return
	 * @version <1> 2018-03-15 cxw： Created.
	 */
	ResultMessage findOrderDetailByOrderId(Integer orderId);

	/**
	 * 分页查询订单详情：根据订单主键查询订单关联文件详情
	 * @param storageParam 元数据对象
	 * @return PageInfo 分页对象
	 * @version <1> 2018-03-015 cxw： Created.
	 */
	PageInfo<DataStorage> findOrderFileByPage(StorageParam storageParam);

	ResultMessage findOrderDetailById(Integer orderDetailId);
}