package com.jh.forum.bbs.service;

import com.alipay.api.domain.OrderDetail;
import com.github.pagehelper.PageInfo;
import com.jh.forum.bbs.entity.ForumOrderDetail;
import com.jh.forum.bbs.vo.OrdersDetailVo;

public interface IForumOrderDetailService {

    PageInfo<ForumOrderDetail> findOrderDetails(OrdersDetailVo ordersDetailVo);
}
