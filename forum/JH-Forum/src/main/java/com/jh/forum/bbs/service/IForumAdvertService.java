package com.jh.forum.bbs.service;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.service.IBaseService;
import com.jh.forum.bbs.vo.AdvertVo;
import com.jh.vo.ResultMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sxj
 * @Description 广告位
 * @Date 2019/8/13 16:15
 * @Version 1.0
 **/
public interface IForumAdvertService extends IBaseService<AdvertVo, AdvertVo,Integer> {

    ResultMessage saveAdvert(HttpServletRequest request);

    ResultMessage findAdvertInfo(Integer advertId);

    ResultMessage editAdvert(HttpServletRequest request);

    ResultMessage editStatus(AdvertVo advertVo);

    ResultMessage editOff(AdvertVo advertVo);

    ResultMessage deleteAdvert(AdvertVo advertVo);

    ResultMessage queryAdvertList(Integer advertId);

    PageInfo<AdvertVo> getListByAdvert(AdvertVo advertVo);

}
