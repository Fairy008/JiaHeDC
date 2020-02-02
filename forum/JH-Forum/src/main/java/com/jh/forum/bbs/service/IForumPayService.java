package com.jh.forum.bbs.service;

/**
 * @Description
 * @Version <1> 2019/8/28 sxj:Create.
 **/


import com.alipay.api.AlipayApiException;
import com.jh.forum.bbs.vo.OrdersVo;
import com.jh.vo.ResultMessage;


public interface IForumPayService {

    /**
     * 支付接口
     * @param ordersVo
     * @return
     * @throws AlipayApiException
     */
    ResultMessage aliPay(OrdersVo ordersVo);

    /**
     * 查询接口
     */
    ResultMessage updateByPay(OrdersVo ordersVo);


}
