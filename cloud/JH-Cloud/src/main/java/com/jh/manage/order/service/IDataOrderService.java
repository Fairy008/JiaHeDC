package com.jh.manage.order.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseService;
import com.jh.manage.order.entity.DataOrder;
import com.jh.manage.order.entity.DataOrderDetail;
import com.jh.manage.order.model.OrderParam;
import com.jh.manage.storage.model.StorageParam;
import com.jh.vo.ResultMessage;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
*数据订单服务：
* 1.创建订单、订单明细
*	数据订单：通过数据检索界面检索数据，生成数据订单
*	需求订单：通过表单填写
* 2.订单(订单明细)查询
* 3.订单审核
* 4.订单分发
* @version <1> 2018-01-29 Hayden:Created.
*/
public interface IDataOrderService  extends IBaseService<OrderParam, DataOrder, Integer> {

	/**
	 * 订单分发分页查询
	 * @param orderParam
	 * @return PageInfo
	 * @version <1> 2018-03-07 cxw： Created.
	 */
	PageInfo<DataOrder> findOrderHandleByPage(OrderParam orderParam);

	/**
	 * 订单审核分页查询
	 * @param orderParam
	 * @return PageInfo
	 * @version <1> 2018-03-07 cxw： Created.
	 */
	PageInfo<DataOrder> findOrderAuditByPage(OrderParam orderParam);

	/**
	 * 订单列表分页查询
	 * @param orderParam
	 * @return PageInfo
	 * @version <1> 2018-03-07 cxw： Created.
	 */
	PageInfo<DataOrder> findOrderListByPage(OrderParam orderParam);

	/**
	 *订单详情查询
	 * @param orderId 订单ID
	 * @return ResultMessage
	 * @version <1> 2018-03-07 cxw： Created.
	 */
	public ResultMessage findOrderById(Integer orderId);

	/**
	 *订单分发详情查询
	 * @param orderId 订单ID
	 * @return ResultMessage
	 * @version <1> 2018-03-23 cxw： Created.
	 */
	public ResultMessage findOrderDistributeDetailById(Integer orderId);

	/**
 	* 分发订单
 	*  1.创建订单目录（账户/订单号）
 	*  2.在订单目录中创建文件存储路径link
 	*  3.设置订单目录有效期，过期后订单目录自动消失（定时任务扫描过期订单目录删除）
	* @param  orderParam:订单参数对象
	* @return ResultMessage :
	 * @version <1> 2018-3-08 cxw:Created.
 	*/
 	public ResultMessage updateDistributeOrder(OrderParam orderParam);

	/**
	 *创建数据订单
	 * @param order
	 * @return ResultMessage
	 * @version <1> 2018-03-12 cxw： Created.
	 */
	//public ResultMessage createOrder(DataOrder order);


	/**
	 *创建数据订单
	 * @param orderParam 订单用户信息
	 * @param dataOrderDetails 选择数据订单列表
	 * @param file 上传文件
	 * @return ResultMessage
	 * @version <1> 2018-03-12 cxw： Created.
	 */
	public ResultMessage createOrder(OrderParam orderParam, List<DataOrderDetail> dataOrderDetails,MultipartFile file);

//	/**
//	 *创建需求订单
//	 * @param  orderParam 订单对象
//	 * @return ResultMessage
//	 * @version <1> 2018-03-14 cxw： Created.
//	 */
//	public ResultMessage createNeedOrder(OrderParam orderParam);

	/**
	 *修改需求订单
	 * @param  orderParam 订单对象
	 * @param file 上传文件
	 * @return ResultMessage
	 * @version <1> 2018-03-14 cxw： Created.
	 */
	public ResultMessage updateNeedOrder(OrderParam orderParam,MultipartFile file);

	/**
	 *订单审核
	 *  1.数据订单审核： 数据订单审核通过后，直接分发数据
	 *  2.需求订单审核：需求订单审核通过后，推送给数据处理人员做数据分发处理
	 * @param  orderParam 订单对象
	 * @version <1> 2018-03-14 cxw： Created.
	 * @version<2> 2018-04-12 lcw : 移除request对象， 用户信息在controller中进行获取
	 */
	public ResultMessage updateAuditOrder(OrderParam orderParam);

	/**
	 *删除订单
	 * @param orderId 订单Id
	 * @return ResultMessage
	 * @version <1> 2018-03-13 cxw： Created.
	 */
	public ResultMessage delOrderById(Integer orderId);

	/**
	 *修改数据订单
	 * @param orderParam 订单对象
	 * @return ResultMessage
	 * @version <1> 2018-03-13 cxw： Created.
	 */
//	public ResultMessage updateOrder(OrderParam orderParam);

	/**
	 * 下载订单数据
	 * @param orderParam
	 * @return ResultMessage
	 * @version <1> 2018-03-16 cxw： Created.
	 */
	public ResultMessage downLoadOrderDataById(OrderParam orderParam);

	/**
	 * 下载订单数据
	 * @param orderParam 订单对象
	 * @return ResultMessage
	 * @version <1> 2018-03-16 cxw： Created.
	 */
	public void downLoadOrderData(OrderParam orderParam,Integer orderDetailId,  HttpServletResponse response);

	/**
	 *订单列表文件详情查询（拼接下载路径专用）
	 * @param orderId 订单ID
	 * @return ResultMessage
	 * @version <1> 2018-04-09 cxw： Created.
	 */
//	public ResultMessage findOrderFilePathDetailById( Integer orderId);

	/**
	 * 订单分发时查询文件详情：根据订单主键查询订单关联文件详情
	 * @param orderId 订单ID
	 * @return ResultMessage
	 * @version <1> 2018-03-22 cxw： Created.
	 */
	public ResultMessage findFileDetailByOrderId(Integer orderId);

	public ResultMessage findOverdueOrder(String dataAccessTime);

	/**
	 *统计系统中需求订单和数据订单总数
	 * @param  orderParam 订单对象
	 * @version <1> 2018-04-17 wl： Created.
	 */
	public ResultMessage queryOrderCount(OrderParam orderParam);

	/**
	 *统计需求订单或者数据订单 不同状态的订单数量
	 * @param  orderParam 订单对象
	 * @version <1> 2018-04-17 wl： Created.
	 */
	public ResultMessage queryOrderDetailCount(OrderParam orderParam);

	/**
	 *统计数据订单不同卫星类型的已分发数量
	 * @param  orderParam 订单对象
	 * @version <1> 2018-04-17 wl： Created.
	 */
	public ResultMessage queryDataOrderBySatellite(OrderParam orderParam);

	/**
	 *统计数据订单不同卫星类型的已分发数量(当天和总计)
	 * @param  orderParam 订单对象
	 * @version <1> 2018-06-12 wl： Created.
	 */
	public ResultMessage queryDataOrderSum(OrderParam orderParam);

	/**
	 * 下载数据订单申请文件
	 * @param url 文件路径
	 * @return ResultMessage
	 * @version <1> 2018-06-26 cxw： Created.
	 */
	public void downLoadOrderWordById(String url,HttpServletResponse response);

	/**
	 * 订单数据统计
	 * @param orderParam
	 * @return
	 * @version<1> 2018-12-13 lcw :Created.
	 */
    ResultMessage queryOrderStatistics(OrderParam orderParam);

	/**
	 * app接口：查询所有待审核订单列表（默认情况查询最新的10条数据）
	 * @param orderParam
	 * @return
	 * @version<1> 2018-12-26 lcw :Created.
	 */
	ResultMessage queryNotAuditList(OrderParam orderParam);
	/**
	 * app接口：查询所有已审核订单列表（默认情况查询最新的10条数据）
	 * @param orderParam
	 * @return
	 * @version<1> 2018-12-26 lcw :Created.
	 */
	ResultMessage queryAuditList(OrderParam orderParam);

	/**
	 * app接口：单明细
	 * @param orderId
	 * @return
	 */
	ResultMessage queryOrderForApp(Integer orderId);

	/**
	 * api接口： 是否审核数据统计
	 * @param orderParam
	 * @return
	 */
    ResultMessage queryIfAuditStatistics(OrderParam orderParam);

	ResultMessage queryIfAuditByPerson(OrderParam orderParam);
}