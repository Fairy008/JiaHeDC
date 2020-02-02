package com.jh.forum.bbs.service.impl;

/**
 * @Description
 * @Version <1> 2019/8/28 sxj:Create.
 **/

import com.jh.forum.bbs.Enum.OrderStatusEnum;
import com.jh.forum.bbs.mapping.IForumOrdersMapper;
import com.jh.forum.bbs.service.IForumPayService;
import com.jh.forum.bbs.vo.OrdersVo;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class IForumPayServiceImpl implements IForumPayService {

    @Autowired
    IForumOrdersMapper iForumOrdersMapper;

    @Override
    public ResultMessage aliPay(OrdersVo ordersVo){
        ordersVo.setOrderNo(ordersVo.getOrder_no());
        ordersVo.setPayStatus(OrderStatusEnum.Order_status_pay.getId());
        int i = iForumOrdersMapper.updateByStatus(ordersVo);
        return ResultMessage.success(i);

    }



    @Override
    public ResultMessage updateByPay(OrdersVo ordersVo) {
        ordersVo.setOrderNo(ordersVo.getOrder_no());
        ordersVo.setPayStatus(OrderStatusEnum.Order_status_pay.getId());
        int i = iForumOrdersMapper.updateByPay(ordersVo);
        return ResultMessage.success(i);
    }


}
