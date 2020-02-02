package com.jh.forum.bbs.service;

import com.github.pagehelper.PageInfo;
import com.jh.forum.bbs.vo.OrdersVo;
import com.jh.vo.ResultMessage;


public interface IForumOrdersService {
    /*
     * 功能描述:根据id列表 查询并计算出订单的总金额
     * @Param:
     * @Return:
     * @version<1>  2019/9/1  wangli :Created
     */
    ResultMessage createOrder(OrdersVo ordersVo);


    ResultMessage findByType(OrdersVo ordersVo);


    ResultMessage deleteOrder(String tradeNo);

    /*
     * 功能描述:根据订单id查询订单详情
     * @Param:
     * @Return:
     * @version<1>  2019/9/18  wangli :Created
     */
    OrdersVo findOrderById(String tradeNo);

    PageInfo<OrdersVo> findAllOrder(OrdersVo ordersVo);

}
