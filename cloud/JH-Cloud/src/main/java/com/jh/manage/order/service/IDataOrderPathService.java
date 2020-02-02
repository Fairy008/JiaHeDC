package com.jh.manage.order.service;

import com.jh.base.service.IBaseService;
import com.jh.manage.order.model.DataOrderPath;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
*数据订单服务：
* 1.订单分发时存储分发路径
* @version <1> 2018-03-16 cxw:Created.
*/
public interface IDataOrderPathService extends IBaseService<DataOrderPath, DataOrderPath, Integer> {

	/**
	 *数据订单
	 * @param dataOrderPath 数据订单路径存储对象
	 * @return ResultMessage
	 * @version <1> 2018-03-16 cxw： Created.
	 */
	public ResultMessage saveDataOrderPath(DataOrderPath dataOrderPath);


	/**
	 *数据订单路径批量存储
	 * @param dataOrderPaths 数据订单路径存储对象
	 * @return ResultMessage
	 * @version <1> 2018-03-16 cxw： Created.
	 */
	public  ResultMessage saveDataOrderPathList(List<DataOrderPath> dataOrderPaths);

	/**
	 *根据订单ID查询分发路径
	 * @param orderId 订单ID
	 * @return ResultMessage
	 * @version <1> 2018-03-19 cxw： Created.
	 */
	public ResultMessage findDataOrderPathList(Integer orderId);


}