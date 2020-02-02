package com.jh.api;

import com.jh.base.controller.BaseController;
import com.jh.constant.SysConstant;
import com.jh.feign.IPermRoleService;
import com.jh.manage.order.Enum.OrderAuditStatusEnum;
import com.jh.manage.order.entity.DataOrder;
import com.jh.manage.order.model.OrderParam;
import com.jh.manage.order.service.IDataOrderService;
import com.jh.manage.storage.model.StorageParam;
import com.jh.util.DateUtil;
import com.jh.util.DownloadUtil;
import com.jh.vo.ResultMessage;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单审核功能模块API接口
 *
 *
 * @version<1> 2018-12-17 lcw :Created.
 */
@RestController
@RequestMapping("/api/order")
public class ApiOrderController extends BaseController {


    @Autowired
    public IDataOrderService orderService;

    /**
     * 查询审核订单列表
     *      订单状态：
     *       1101	待审核
     * @return
     */
    @ApiOperation(value = "待审核订单列表",notes = "待审核订单列表查询")
    @PostMapping("/queryNotAuditList")
    public ResultMessage queryNotAuditList(OrderParam orderParam){

        //查询当前登录人

        if(StringUtils.isNotBlank(orderParam.getOrderCode())){
            orderParam.setOrderCode(orderParam.getOrderCode().toUpperCase());
        }


        ResultMessage result = orderService.queryNotAuditList(orderParam);


        return result;
    }


    /**
     * 查询已审核的订单列表
     *   订单状态:
     *       1102	审核未通过
     *       1103	待分发
     *       1104	已分发
     *       1105	拒绝分发
     *
     * @return
     */
    @ApiOperation(value = "已审核订单列表",notes = "已审核订单列表查询")
    @PostMapping("/queryAuditList")
    public ResultMessage queryAuditList(OrderParam orderParam){

        if(StringUtils.isNotBlank(orderParam.getOrderCode())){
            orderParam.setOrderCode(orderParam.getOrderCode().toUpperCase());
        }

        ResultMessage result = orderService.queryAuditList(orderParam);


        return result;
    }


    /**
     * 根据订单ID查询订单详细列表
     * @param orderId
     * @return
     */
    @ApiOperation(value = "订单明细查询", notes = "根据订单ID查询订单列表")
    @PostMapping("queryOrderDetail")
    public ResultMessage queryOrderDetail(Integer orderId){
        ResultMessage result = orderService.queryOrderForApp(orderId);

        return result;
    }


    @ApiOperation(value = "订单审核", notes = "对订单进行审核")
    @PostMapping("audit")
    public ResultMessage auditOrder(OrderParam orderParam){

        //获取当前登录人名称
        ResultMessage result = orderService.updateAuditOrder(orderParam);

        return result;
    }


    @ApiOperation(value = "统计订单审核情况", notes = "统计订单审核情况")
    @PostMapping("queryOrderStatistics")
    public ResultMessage queryOrderStatistics(OrderParam orderParam){

        ResultMessage result = orderService.queryIfAuditStatistics(orderParam);

        return result;

    }


    @ApiOperation(value = "按申请人统计订单状态情况", notes = "按申请人统计订单状态情况")
    @PostMapping("queryIfAuditByPerson")
    public ResultMessage queryIfAuditByPerson(OrderParam orderParam){

        ResultMessage result = orderService.queryIfAuditByPerson(orderParam);
        return result;
    }

    @ApiImplicitParam(name = "orderId",value = "订单ID",required = true,paramType = "query", dataType = "Integer")
    @GetMapping("/downloadByOrderId")
    public void downloadByOrderId(@RequestParam Integer orderId, HttpServletResponse response){


        ResultMessage result = orderService.findOrderById(orderId);
        if(result.isFlag()){

            DataOrder order = (DataOrder)result.getData();
            DownloadUtil.getInstance().downloadFile(order.getWordPath(), response);

        }

    }




}
