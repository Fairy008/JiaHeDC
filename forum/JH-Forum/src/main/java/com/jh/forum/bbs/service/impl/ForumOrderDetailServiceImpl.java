package com.jh.forum.bbs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.forum.bbs.entity.ForumOrderDetail;
import com.jh.forum.bbs.mapping.IForumOrderDetailMapper;
import com.jh.forum.bbs.service.IForumOrderDetailService;
import com.jh.forum.bbs.vo.OrdersDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ForumOrderDetailServiceImpl implements IForumOrderDetailService {
    @Autowired
    private IForumOrderDetailMapper forumOrderDetailMapper;

    @Override
    public PageInfo<ForumOrderDetail> findOrderDetails(OrdersDetailVo ordersDetailVo) {
        PageHelper.startPage(ordersDetailVo.getPage(), ordersDetailVo.getRows());
        List<ForumOrderDetail> list = forumOrderDetailMapper.findDetailsByOrderNo(ordersDetailVo.getOrderNo());
        return new PageInfo<ForumOrderDetail>(list);
    }
}
