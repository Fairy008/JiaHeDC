package com.jh.forum.bbs.service;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.service.IBaseService;
import com.jh.forum.bbs.entity.ForumOrderData;
import com.jh.forum.bbs.vo.OrderDataVo;
import com.jh.vo.ResultMessage;

/**
 * 数据定制业务类
 */
public interface IForumOrderDataService extends IBaseService<OrderDataVo, ForumOrderData, Integer> {
    ResultMessage saveData(OrderDataVo orderDataVo);

    PageInfo<OrderDataVo> findByPage(OrderDataVo orderDataVo);

    /**
     * 后台查询定制数据订单
     * @param orderData
     * @return
     */
    PageInfo<ForumOrderData> findDataOrderPage(ForumOrderData orderData);

}
