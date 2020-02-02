package com.jh.forum.bbs.mapping;

import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.bbs.entity.ForumDownloadData;
import com.jh.forum.bbs.entity.ForumOrderData;
import com.jh.forum.bbs.vo.DownloadDataVo;
import com.jh.forum.bbs.vo.OrderDataVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IForumOrderDataMapper extends IBaseMapper<OrderDataVo,ForumOrderData,Integer> {


    List<OrderDataVo> queryByPage(OrderDataVo orderDataVo);

    /**
     * 后台查询，支持分页和排序
     * @param orderData
     * @return
     */
    List<ForumOrderData> findByPageCms(ForumOrderData orderData);
}