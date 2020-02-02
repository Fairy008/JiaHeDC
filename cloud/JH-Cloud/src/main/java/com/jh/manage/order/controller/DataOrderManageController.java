package com.jh.manage.order.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.feign.IPermRoleService;
import com.jh.manage.order.Enum.PersonTypeEnum;
import com.jh.manage.order.entity.DataOrder;
import com.jh.manage.order.entity.DataOrderDetail;
import com.jh.manage.order.model.OrderParam;
import com.jh.manage.order.service.IDataOrderDetailService;
import com.jh.manage.order.service.IDataOrderService;
import com.jh.manage.storage.entity.DataStorage;
import com.jh.manage.storage.model.StorageParam;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description:数据订单服务：
 * 1.创建订单、订单明细
 *	数据订单：通过数据检索界面检索数据，生成数据订单
 *	需求订单：通过表单填写
 * 2.订单(订单明细)查询
 * 3.订单审核
 * 4.订单分发
 * @version <1> 2018-03-06 cxw： Created.
 */
@RestController
@RequestMapping("/order")
public class DataOrderManageController extends BaseController {

    @Autowired
    private IDataOrderService dataOrderService;

    @Autowired
    private IDataOrderDetailService dataOrderDetailService;

    @Autowired
    private IPermRoleService permRoleService;

    /**
     *订单分发分页查询
     * @param orderParam 订单参数
     * @return PageInfo 分页对象
     * @version <1> 2018-03-07 cxw： Created.
     */
    @ApiOperation(value = "订单分发分页查询",notes = "订单分发任务分页查询")
    @ApiImplicitParam(name = "orderParam",value = "数据订单",required = false,dataType = "OrderParam")
    @PostMapping("queryOrderHandleByPage")
    public PageInfo<DataOrder> queryOrderHandleByPage(OrderParam orderParam){
        return dataOrderService.findOrderHandleByPage(orderParam);
    }

    /**
     *订单审核分页查询
     * @param orderParam 订单参数
     * @return PageInfo 分页对象
     * @version <1> 2018-03-07 cxw： Created.
     */
    @ApiOperation(value = "订单审核分页查询",notes = "订单审核分页查询")
    @ApiImplicitParam(name = "orderParam",value = "数据订单",required = false,dataType = "OrderParam")
    @PostMapping("queryOrderAuditByPage")
    public PageInfo<DataOrder> queryOrderAuditByPage(OrderParam orderParam){
        return dataOrderService.findOrderAuditByPage(orderParam);
    }

    /**
     *用户订单列表分页查询
     * @param orderParam 订单参数
     * @return PageInfo 分页对象
     * @version <1> 2018-03-07 cxw： Created.
     * @version<2> 2018-03-18 lcw : 非管理组角色对应的用户只能查询本人申请的订单
     */
   @ApiOperation(value = "订单列表分页查询",notes = "用户订单列表分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "request请求对象", required = true, dataType = "HttpServletRequest"),
            @ApiImplicitParam(name = "orderParam", value = "数据订单", required = false, dataType = "OrderParam")
    })
    @PostMapping("queryOrderListByPage")
    public PageInfo<DataOrder> queryOrderListByPage( OrderParam orderParam,HttpServletRequest request){

       if(orderParam.isUserFlag()){ //userFlag为true，则查询当前登录人对象的数据
           orderParam.setCreator(getCurrentAccountId());
       }

        return dataOrderService.findOrderListByPage(orderParam);
    }

    /**
     *订单关联文件分页查询
     * @param storageParam 元数据对象
     * @return PageInfo 分页对象
     * @version <1> 2018-03-07 cxw： Created.
     */
    @ApiOperation(value = "订单关联文件分页查询",notes = "订单关联文件分页查询")
    @ApiImplicitParam(name = "storageParam",value = "数据订单",required = false,dataType = "StorageParam")
    @PostMapping("queryOrderFileByPage")
    public PageInfo<DataStorage> queryOrderFileByPage(StorageParam storageParam){
        return dataOrderDetailService.findOrderFileByPage(storageParam);
    }

    /**
     *订单分发详情查询
     * @param orderId 订单ID
     * @return ResultMessage
     * @version <1> 2018-03-23 cxw： Created.
     */
    @ApiOperation(value = "根据订单ID查询订单详情",notes = "根据订单ID查询订单详情")
    @ApiImplicitParam(name = "orderId",value = "数据订单ID",required = true,dataType = "Integer")
    @PostMapping("queryOrderDistributeDetailById")
    public ResultMessage queryOrderDistributeDetailById(@RequestParam Integer orderId){
        return dataOrderService.findOrderDistributeDetailById(orderId);
    }

    /**
     *订单详情查询
     * @param orderId 订单ID
     * @return ResultMessage
     * @version <1> 2018-03-07 cxw： Created.
     */
    @ApiOperation(value = "根据订单ID查询订单详情",notes = "根据订单ID查询订单详情")
    @ApiImplicitParam(name = "orderId", value = "数据订单ID", required = true, dataType = "Integer")
    @PostMapping("queryOrderById")
    public ResultMessage queryOrderById(@RequestParam Integer orderId){
        return dataOrderService.findOrderById(orderId);
    }


    /**
     *修改订单信息
     * @param dataOrder
     * @return ResultMessage
     * @version <1> 2018-03-07 cxw： Created.
     */
    @ApiOperation(value = "根据订单ID修改订单信息",notes = "根据订单ID修改订单信息")
    @ApiImplicitParam(name = "dataOrder",value = "数据订单",required = true,dataType = "DataOrder")
    @PostMapping("updateOrderById")
    public ResultMessage updateOrderById(@RequestBody DataOrder dataOrder){
        //return dataOrderService.updateOrderById(dataOrder);
        return null;
    }

    /**
     *订单分发
     * @param orderParam 订单参数
     * @return ResultMessage
     * @version <1> 2018-03-07 cxw： Created.
     */
    @ApiOperation(value = "订单分发",notes = "订单分发")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "request请求对象", required = true, dataType = "HttpServletRequest"),
            @ApiImplicitParam(name = "orderParam", value = "数据订单", required = true, dataType = "OrderParam")
    })
    @PostMapping("distributeOrder")
    public ResultMessage distributeOrder(@RequestBody OrderParam orderParam, HttpServletRequest request){
            orderParam.setOperatorName(getCurrentNickName());
            orderParam.setOperator(getCurrentAccountId());
        return dataOrderService.updateDistributeOrder(orderParam);
    }


    /**
     *订单审核
     * @param  orderParam 订单对象
     * @return ResultMessage
     * @version <1> 2018-03-14 cxw： Created.
     * @version<2> 2018-04-12 lcw : 用户信息从request中获取，不应在service中处理</2>
     */
    @ApiOperation(value = "订单审核",notes = "订单审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "request请求对象", required = true, dataType = "HttpServletRequest"),
            @ApiImplicitParam(name = "orderParam", value = "订单对象", required = true, dataType = "OrderParam")
    })
    @PostMapping("auditOrder")
    public ResultMessage auditOrder(@RequestBody OrderParam orderParam , HttpServletRequest request){

            orderParam.setOperatorName(getCurrentNickName());
            orderParam.setOperator(getCurrentAccountId());

        return dataOrderService.updateAuditOrder(orderParam);
    }


    /**
     *删除订单
     * @param orderId 订单Id
     * @return ResultMessage
     * @version <1> 2018-03-13 cxw： Created.
     */
    @ApiOperation(value = "删除订单",notes = "删除订单")
    @ApiImplicitParam(name = "orderId",value = "订单Id",required = true,dataType = "Integer")
    @PostMapping("delOrderById")
    public ResultMessage delOrderById(@RequestParam Integer orderId){
        return dataOrderService.delOrderById(orderId);
    }

//    /**
//     *修改数据订单
//     * @param orderParam 订单对象
//     * @return ResultMessage
//     * @version <1> 2018-03-13 cxw： Created.
//     */
//    @ApiOperation(value = "修改订单",notes = "修改订单")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "request", value = "request请求对象", required = true, dataType = "HttpServletRequest"),
//            @ApiImplicitParam(name = "orderParam", value = "订单对象", required = true, dataType = "OrderParam")
//    })
//    @PostMapping("updateOrder")
//    public ResultMessage updateOrder(@RequestBody OrderParam orderParam , HttpServletRequest request){
//            orderParam.setOperatorName(getCurrentNickName());
//            orderParam.setOperator(getCurrentAccountId());
//        return dataOrderService.updateOrder(orderParam);
//    }


    /**
     * 查询订单详情：根据订单主键查询订单关联文件详情
     * @param orderId 订单ID
     * @return ResultMessage
     * @version <1> 2018-03-15 cxw： Created.
     */
    @ApiOperation(value = "查询订单详情",notes = "根据订单主键查询订单关联文件详情")
    @ApiImplicitParam(name = "orderId",value = "订单Id",required = true,dataType = "Integer")
    @PostMapping("queryOrderDetailByOrderId")
    public ResultMessage queryOrderDetailByOrderId(@RequestParam Integer orderId){
        return dataOrderDetailService.findOrderDetailByOrderId(orderId);
    }

    /**
     * 订单分发时查询文件详情：根据订单主键查询订单关联文件详情
     * @param orderId 订单ID
     * @return ResultMessage
     * @version <1> 2018-03-22 cxw： Created.
     */
    @ApiOperation(value = "查询订单详情",notes = "根据订单主键查询订单关联文件详情")
    @ApiImplicitParam(name = "orderId",value = "订单Id",required = true,dataType = "Integer")
    @PostMapping("queryFileDetailByOrderId")
    public ResultMessage queryFileDetailByOrderId(@RequestParam Integer orderId){
        return dataOrderService.findFileDetailByOrderId(orderId);
    }

    /**
     * 判断下载订单数据是否过期
     * @param orderId 订单Id
     *  根据订单ID判断下载订单数据是否过期
     * 	1.根据订单ID查询订单任务信息，获取数据访问期限
     * 	2.若当前时间在数据访问期限内，则读取/order/订单ID/目录下的数据，并进行下载
     *  3.若超出期限，则不可访问该订单的数据
     * @return ResultMessage
     * @version <1> 2018-03-16 cxw： Created.
     */
    @ApiOperation(value = "判断下载订单数据是否过期",notes = "判断下载订单数据是否过期")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "request请求对象", required = true, dataType = "HttpServletRequest"),
            @ApiImplicitParam(name = "orderId", value = "订单ID", required = true, dataType = "Integer")
    })
    @PostMapping("downLoadOrderDataById")
    public ResultMessage  downLoadOrderDataById(@RequestParam Integer orderId, HttpServletRequest request){
        OrderParam orderParam = new OrderParam();
        orderParam.setOrderId(orderId);
        orderParam.setOperatorName(getCurrentNickName());
        orderParam.setOperator(getCurrentAccountId());

        return dataOrderService.downLoadOrderDataById(orderParam);
    }

    /**
     * 下载订单数据
     *  根据订单地址下载该订单的数据
     * @return ResultMessage
     * @version <1> 2018-03-16 cxw： Created.
     */
    @ApiOperation(value = "下载订单数据",notes = "根据订单地址下载订单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "orderDetailId", value = "订单明细ID", required = true, dataType = "Integer")
    })
    @RequestMapping("downloadByDetailId")
    public  void downloadByDetailId(Integer  orderId ,Integer orderDetailId, HttpServletResponse response){
        OrderParam orderParam = new OrderParam();
        orderParam.setOperatorName(getCurrentNickName());
        orderParam.setOperator(getCurrentAccountId());
        orderParam.setOrderId(orderId);

        dataOrderService.downLoadOrderData(orderParam,orderDetailId, response);
    }


    /**
     *统计系统中需求订单和数据订单总数
     * @param orderParam
     * @return ResultMessage
     * @version <1> 2018-04-17 wl： Created.
     */
    @ApiOperation(value = "统计系统中需求订单和数据订单总数",notes = "统计系统中需求订单和数据订单总数")
    @ApiImplicitParam(name = "orderParam",value = "订单对象",required = true,dataType = "OrderParam")
    @PostMapping("queryOrderCount")
    public ResultMessage queryOrderCount(@RequestBody OrderParam orderParam ){
        return dataOrderService.queryOrderCount(orderParam);
    }

    /**
     *统计需求订单或者数据订单 不同状态的订单数量
     * @param orderParam
     * @return ResultMessage
     * @version <1> 2018-04-17 wl： Created.
     */
    @ApiOperation(value = "统计需求订单或者数据订单不同状态的订单数量",notes = "统计需求订单或者数据订单不同状态的订单数量")
    @ApiImplicitParam(name = "orderParam",value = "订单对象",required = true,dataType = "OrderParam")
    @PostMapping("queryOrderDetailCount")
    public ResultMessage queryOrderDetailCount(@RequestBody OrderParam orderParam ){
        return dataOrderService.queryOrderDetailCount(orderParam);
    }

    /**
     *统计数据订单不同卫星类型的已分发数量
     * @param orderParam
     * @return ResultMessage
     * @version <1> 2018-04-17 wl： Created.
     */
    @ApiOperation(value = "统计数据订单不同卫星类型的已分发数量",notes = "统计数据订单不同卫星类型的已分发数量")
    @ApiImplicitParam(name = "orderParam",value = "订单对象",required = true,dataType = "OrderParam")
    @PostMapping("queryDataOrderBySatellite")
    public ResultMessage queryDataOrderBySatellite(@RequestBody OrderParam orderParam ){
        return dataOrderService.queryDataOrderBySatellite(orderParam);
    }

    /**
     *统计数据订单不同卫星类型的已分发数量(当天和总计)
     * @param orderParam
     * @return ResultMessage
     * @version <1> 2018-06-12 wl： Created.
     */
    @ApiOperation(value = "统计数据订单不同卫星类型的已分发数量(当天和总计)",notes = "统计数据订单不同卫星类型的已分发数量(当天和总计)")
    @ApiImplicitParam(name = "orderParam",value = "订单对象",required = true,dataType = "OrderParam")
    @PostMapping("queryDataOrderSum")
    public ResultMessage queryDataOrderSum(@RequestBody OrderParam orderParam ){
        return dataOrderService.queryDataOrderSum(orderParam);
    }

    /**
     *创建数据订单
     * @param order
     * @return ResultMessage
     * @version <1> 2018-03-12 cxw： Created.
     */
/*    @ApiOperation(value = "创建数据订单",notes = "创建订数据单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order", value = "订单", required = true, dataType = "DataOrder")
    })
    @PostMapping("createOrder")
    public ResultMessage createOrder(@RequestBody DataOrder order, HttpServletRequest request){

        order.setCreatorName(getCurrentNickName());
        order.setCreator(getCurrentAccountId());
        order.setPersonType(getCurrentPersonType());

        return dataOrderService.createOrder(order);
    }*/

    /**
     *创建数据订单
     * @param request
     * @return ResultMessage
     * @version <1> 2018-03-12 cxw： Created.
     * @version <2> 2018-07-26 cxw： update；加入数据申请单上传，并整合创建数据申请单与创建需求订单.
     */
    @ApiOperation(value = "创建数据订单",notes = "创建订数据单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "request请求对象", required = true, dataType = "HttpServletRequest"),
    })
    @PostMapping("createOrder")
    public ResultMessage createOrder( HttpServletRequest request) throws UnsupportedEncodingException {


        List<DataOrderDetail> dataOrderDetails = new ArrayList<DataOrderDetail>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("dataOrderFile");//数据申请单
        //如果数据申请单文件名为空或者大小为空则认为未上传申请单
        if ("".equals(file.getOriginalFilename())){
            file = null;
        }
        Integer orderType = null;
        String str = request.getParameter("storageIds");//数据存储ID
        String orderDescription = request.getParameter("orderDescription");//需求描述
        JSONArray jsonArray = JSONArray.fromObject(str);//把String转换为json
        dataOrderDetails = (List<DataOrderDetail>)JSONArray.toCollection(jsonArray,DataOrderDetail.class);//这里的t是Class<T>
        OrderParam orderParam = new OrderParam();
        if((request.getParameter("orderType"))!=null) {
             orderType = Integer.parseInt(request.getParameter("orderType"));
        }
        if ((null != getCurrentAccountId())&&(orderType!=null)) {
            orderParam.setOperatorName(getCurrentNickName());
            orderParam.setOperator(getCurrentAccountId());
            orderParam.setPersonType(getCurrentPersonType());
            orderParam.setCreatorName(getCurrentAccount());
            orderParam.setOrderType(orderType);
            orderParam.setOrderDescription(orderDescription);
        }

        //外部用户创建订单市，需要检索当日订单阈值
        if(PersonTypeEnum.PERSON_TYPE_OUTER.getId().intValue() == getCurrentPersonType().intValue()){
            //文件阈值
            Integer orderFileNum = getOrderFileNum();
            orderParam.setOrderFileNum(orderFileNum);
        }

        //文件阈值
//        Integer orderFileNum = getOrderFileNum();
//        orderParam.setOrderFileNum(orderFileNum);
        return dataOrderService.createOrder(orderParam,dataOrderDetails,file);
    }


    /**
     *修改需求订单
     * @param  orderParam 订单对象
     * @return ResultMessage
     * @version <1> 2018-03-14 cxw： Created.
     */
   /* @ApiOperation(value = "修改需求订单",notes = "修改需求订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order", value = "订单对象", required = true, dataType = "DataOrder")
    })
    @PostMapping("updateNeedOrder")
    public ResultMessage updateNeedOrder(@RequestBody OrderParam orderParam){
        orderParam.setOperatorName(getCurrentNickName());
        orderParam.setOperator(getCurrentAccountId());
        return dataOrderService.updateNeedOrder(orderParam);
    }*/


    /**
     *修改需求订单
     * @param  request
     * @return ResultMessage
     * @version <1> 2018-03-14 cxw： Created.
     */
    @ApiOperation(value = "修改需求订单",notes = "修改需求订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "request请求对象", required = true, dataType = "HttpServletRequest"),
    })
    @PostMapping("updateNeedOrder")
    public ResultMessage updateNeedOrder(HttpServletRequest request) throws UnsupportedEncodingException {
        OrderParam orderParam = new OrderParam();
        String orderDescription = request.getParameter("orderDescription");
        String orderId = request.getParameter("orderId");
        String orderCode = request.getParameter("orderCode");
        orderParam.setOrderDescription(orderDescription);
        orderParam.setOrderId(Integer.parseInt(orderId));
        orderParam.setOrderCode(orderCode);
        if (null != getCurrentAccountId()) {
            orderParam.setOperatorName(getCurrentNickName());
            orderParam.setOperator(getCurrentAccountId());
            orderParam.setPersonType(getCurrentPersonType());
            orderParam.setCreatorName(getCurrentAccount());
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("needOrderFile");
        return dataOrderService.updateNeedOrder(orderParam,file);
    }

    /**
     * 下载数据订单申请文件
     *  根据地址下载该数据订单申请文件的数据
     * @return ResultMessage
     * @version <1> 2018-06-26 cxw： Created.
     */
    @ApiOperation(value = "下载数据订单申请文件",notes = "根据订单地址下载数据订单申请文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "response", value = "response响应对象", required = true, dataType = "HttpServletResponse"),
            @ApiImplicitParam(name = "url", value = "下载路径", required = true, dataType = "String")
    })
    @GetMapping("downLoadOrderWordById")
    public  void downLoadOrderWordById(@RequestParam String  url ,HttpServletResponse response){
        dataOrderService.downLoadOrderWordById(url,response);
    }


    /**
     * 获取默认角色的阈值
     * @return
     */
    public Integer getOrderFileNum(){

        ResultMessage result = permRoleService.getDefaultRoleId();
        if(result.isFlag()){

            Integer defaultRoleId = Integer.parseInt(result.getData()+"") ;
            List<Map<String , Object>> roleList = getRoleList();
            if(roleList != null && roleList.size() > 0){
                for(int i = 0 ; i < roleList.size() ; i++){
                    Map<String,Object> roleInfo = roleList.get(i);
                    if(defaultRoleId.equals(roleInfo.get("roleId"))){
                        return roleInfo.get("orderFileNum") == null ? null : (Integer)roleInfo.get("orderFileNum");
                    }
                }
            }
        }
        return null;
    }


    /**
     * 订单数据统计
     * @param orderParam
     * @return
     * @version<1> 2018-12-13 lcw :Created.
     */
    @PostMapping("queryOrderStatistics")
    public ResultMessage queryOrderStatistics(@RequestBody OrderParam orderParam){
        return dataOrderService.queryOrderStatistics(orderParam);

    }
}
