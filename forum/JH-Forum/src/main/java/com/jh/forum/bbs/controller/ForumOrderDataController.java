package com.jh.forum.bbs.controller;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.Enum.AccuracyRangeEnum;
import com.jh.forum.bbs.Enum.OrderDataStatusEnum;
import com.jh.forum.bbs.entity.ForumOrderData;
import com.jh.forum.bbs.service.IForumOrderDataService;
import com.jh.forum.bbs.vo.OrderDataVo;
import com.jh.util.DateUtil;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

import static com.jh.forum.bbs.Enum.AccuracyRangeEnum.*;

/**
 * @Description: 数据定制控制器
 * @version<1> 2019-07-01 lcw :Created.
 */
@RestController
@RequestMapping("/orderData")
public class ForumOrderDataController extends BaseController {


    @Autowired
    private IForumOrderDataService orderDataService;

    @PostMapping("save")
    public ResultMessage save( @RequestBody  OrderDataVo orderDataVo){
        orderDataVo.setOrderTel(getCurrentAccount());
        orderDataVo.setCreator(getCurrentAccountId());
        orderDataVo.setCreatorName(getCurrentNickName());
        orderDataVo.setOrderStatus(OrderDataStatusEnum.Order_status_wait.getId()); //待处理

        ResultMessage result = orderDataService.saveData(orderDataVo);

        return result;
    }

    @PostMapping("/findByPage")
    public PageInfo<OrderDataVo> findByPage(@RequestBody OrderDataVo orderDataVo){
        orderDataVo.setCreator(getCurrentAccountId());
        PageInfo<OrderDataVo> pager = orderDataService.findByPage(orderDataVo);

        return pager;
    }

    @ApiOperation(value = "后台查询制定数据订单列表",notes = "后台查询制定数据订单列表")
    @ApiImplicitParam(name = "orderData",value = "实体",required = true, dataType = "ForumOrderData")
    @PostMapping("/findPageCms")
    public PageInfo<ForumOrderData> findPageCms(ForumOrderData orderData){
        return orderDataService.findDataOrderPage(orderData);
    }

    @ApiOperation(value = "后台查询制定数据订单列表",notes = "后台查询制定数据订单列表")
    @ApiImplicitParam(name = "orderId",value = "订单id",required = true, paramType="query", dataType = "Integer")
    @GetMapping("/findOrder")
    public ResultMessage findOrderCms(@RequestParam Integer orderId){
        return orderDataService.getById(orderId);
    }

    @ApiOperation(value = "后台查询制定数据订单列表",notes = "后台查询制定数据订单列表")
    @ApiImplicitParam(name = "orderData",value = "订单对象",required = true, paramType="query", dataType = "ForumOrderData")
    @PostMapping("/handlerOrder")
    public ResultMessage handlerOrderCms(@RequestBody ForumOrderData orderData){
        return orderDataService.update(orderData);
    }



}
