package com.jh.forum.bbs.mapping;

import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.bbs.vo.AdvertVo;
import com.jh.vo.ResultMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author sxj
 * @Description 操作广告位
 * @Date 2019/8/13 15:23
 * @Version 1.0
 **/
@Mapper
public interface IForumAdvertMapper extends IBaseMapper<AdvertVo,AdvertVo,Integer> {

    //新增广告位
    void saveAdvert(AdvertVo advertVo);

    //修改广告位
    void editAdvert(AdvertVo AdvertVo);

    //修改状态
    void editStatus(AdvertVo advertVo);

    //下架
    void editOff(AdvertVo advertVo);

    //删除广告位
    int deleteAdvert(AdvertVo advertVo);

    //查询广告列表
    List<AdvertVo> queryAdvertList(Integer advertId);

    //分页查询
    List<AdvertVo> getListByAdvert(AdvertVo advertVo);

    //广告位详情
    AdvertVo findAdvertInfo(@Param("advertId") Integer advertId);

}
