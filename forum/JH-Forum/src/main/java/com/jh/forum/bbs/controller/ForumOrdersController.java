package com.jh.forum.bbs.controller;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.service.IForumOrdersService;
import com.jh.forum.bbs.vo.OrdersVo;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class ForumOrdersController extends BaseController {

    @Autowired
    private IForumOrdersService forumOrdersService;

    @ApiOperation(value = "创建订单",notes = "创建订单")
    @ApiImplicitParam(name = "ordersVo",value = "实体",required = true, dataType = "OrdersVo")
    @PostMapping("/createOrder")
    public ResultMessage createOrder(@RequestBody OrdersVo ordersVo){
        return forumOrdersService.createOrder(ordersVo);
    }

    @ApiOperation(value = "查询订单",notes = "查询订单")
    @ApiImplicitParam(name = "ordersVo",value = "实体",required = true, dataType = "OrdersVo")
    @PostMapping("/findByType")
    public ResultMessage findByType(@RequestBody OrdersVo ordersVo){
        return forumOrdersService.findByType(ordersVo);
    }


    @ApiOperation(value = "删除订单",notes = "删除订单")
    @ApiImplicitParam(name = "ordersVo",value = "实体",required = true, dataType = "OrdersVo")
    @PostMapping("/deleteOrder")
    public ResultMessage deleteOrder(@RequestParam String tradeNo){
        return forumOrdersService.deleteOrder(tradeNo);
    }

    @ApiOperation(value = "查询订单",notes = "查询订单")
    @ApiImplicitParam(name = "ordersVo",value = "订单实体",required = true,dataType = "OrdersVo")
    @PostMapping("/findAllOrder")
    public PageInfo<OrdersVo> findAllOrder(OrdersVo ordersVo){
        return forumOrdersService.findAllOrder(ordersVo);
    }

}
