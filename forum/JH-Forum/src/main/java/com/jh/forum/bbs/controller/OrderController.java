package com.jh.forum.bbs.controller;

/**
 * @Description
 * @Version <1> 2019/8/28 sxj:Create.
 **/

import com.jh.forum.bbs.service.IForumPayService;
import com.jh.forum.bbs.vo.OrdersVo;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(value = "支付接口",description = "支付接口")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IForumPayService IForumPayService;

    /**
     * 阿里支付
     * @param orderNo
     * @param subject
     * @param totalAmount
     * @param goodsDetail
     * @return
     */
    @ApiOperation(value = "支付接口",notes = "支付接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo",value = "商户订单号",required = true, dataType = "String" ,paramType="query"),
            @ApiImplicitParam(name = "subject",value = "订单名称",required = true, dataType = "String" ,paramType="query"),
            @ApiImplicitParam(name = "totalAmount",value = "付款金额",required = true, dataType = "String" ,paramType="query"),
            @ApiImplicitParam(name = "goodsDetail",value = "商品描述",required = false, dataType = "String" ,paramType="query")
    })
    @PostMapping(value = "/alipay")
    public ResultMessage alipay(String orderNo, String subject, String totalAmount, String goodsDetail){
        OrdersVo ordersVo = new OrdersVo();
        ordersVo.setOrder_no(orderNo);
        ordersVo.setSubject(subject);
        ordersVo.setTotal_amount(totalAmount);
        ordersVo.setGoods_detail(goodsDetail);
        return IForumPayService.aliPay(ordersVo);
    }


    /**
     * 修改支付状态
     * @param orderNo
     * @return
     */
    @ApiOperation(value = "修改支付状态",notes = "修改支付状态")
    @ApiImplicitParam(name = "ordersVo",value = "订单号",required = false,dataType = "ordersVo")
    @PostMapping("/updateByPay")
    public ResultMessage updateByPay(@RequestParam String orderNo){
        OrdersVo ordersVo = new OrdersVo();
        ordersVo.setOrder_no(orderNo);
        return IForumPayService.updateByPay(ordersVo);
    }

}
