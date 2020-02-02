package com.jh.forum.bbs.controller;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.entity.ForumOrderDetail;
import com.jh.forum.bbs.service.IForumOrderDetailService;
import com.jh.forum.bbs.vo.OrdersDetailVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderDetails")
public class ForumOrderDetailController extends BaseController {
    @Autowired
    private IForumOrderDetailService forumOrderDetailService;

    @ApiOperation(value = "查询订单",notes = "查询订单")
    @ApiImplicitParam(name = "ordersVo",value = "订单实体",required = true,dataType = "OrdersVo")
    @PostMapping("/findOrderDetails")
    public PageInfo<ForumOrderDetail> findOrderDetails(OrdersDetailVo ordersDetailVo){
        return forumOrderDetailService.findOrderDetails(ordersDetailVo);
    }
}
