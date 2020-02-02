package com.jh.forum.bbs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.base.service.impl.BaseServiceImpl;
import com.jh.forum.bbs.mapping.IForumAdvertMapper;
import com.jh.forum.bbs.service.IForumAdvertService;
import com.jh.forum.bbs.vo.AdvertVo;
import com.jh.util.cache.IdTransformUtils;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author sxj
 * @Description 广告位实现类
 * @Date 2019/8/13 16:43
 * @Version 1.0
 **/
@Service
@Transactional
public class ForumAdvertServiceImpl extends BaseServiceImpl<AdvertVo, AdvertVo,Integer> implements IForumAdvertService{

    @Autowired
    private IForumAdvertMapper forumAdvertMapper;

    /**
     * 新增
     * @param
     * @return
     */
    @Override
    public ResultMessage saveAdvert(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        Integer advertise = Integer.parseInt(request.getParameter("advertise").toString());
        String title = request.getParameter("title");
        String url = request.getParameter("url");
        //保存图片
        String photoUrl = CephUtils.uploadAdvertImage(request);
        AdvertVo advertVo = new AdvertVo();
        advertVo.setAdvertise(advertise);
        advertVo.setPicture(photoUrl);
        advertVo.setTitle(title);
        advertVo.setUrl(url);
        forumAdvertMapper.saveAdvert(advertVo);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage findAdvertInfo(Integer advertId) {
        AdvertVo advert = forumAdvertMapper.findAdvertInfo(advertId);
        return ResultMessage.success(advert);
    }


    @Override
    public ResultMessage editAdvert(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        Integer advertise = Integer.parseInt(request.getParameter("advertise").toString());
        String title = request.getParameter("title");
        String url = request.getParameter("url");

        Integer advertId = Integer.parseInt(request.getParameter("advertId").toString());
        //保存图片
        String photoUrl = CephUtils.uploadAdvertImage(request);
        AdvertVo advertVo = new AdvertVo();
        advertVo.setAdvertise(advertise);
        advertVo.setPicture(photoUrl);
        advertVo.setTitle(title);
        advertVo.setUrl(url);
        advertVo.setAdvertId(advertId);


        forumAdvertMapper.editAdvert(advertVo);
        return ResultMessage.success();
    }


    @Override
    public ResultMessage editStatus(AdvertVo advertVo){
        Integer[] a = advertVo.getAdvertIds();
        advertVo.setAdvertId(a[0]);
        forumAdvertMapper.editStatus(advertVo);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage editOff(AdvertVo advertVo){
        Integer[] a = advertVo.getAdvertIds();
        advertVo.setAdvertId(a[0]);
        forumAdvertMapper.editOff(advertVo);
        return ResultMessage.success();
    }


    @Override
    public ResultMessage deleteAdvert(AdvertVo advertVo){
        forumAdvertMapper.deleteAdvert(advertVo);
        return ResultMessage.success();
    }


   @Override
    public ResultMessage queryAdvertList(Integer advertId) {
        forumAdvertMapper.queryAdvertList(advertId);
        return ResultMessage.success();
    }

    @Override
    public PageInfo<AdvertVo> getListByAdvert(AdvertVo advertVo) {
        PageHelper.startPage(advertVo.getPage(),advertVo.getRows());
        List<AdvertVo> list = forumAdvertMapper.getListByAdvert(advertVo);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<AdvertVo>(list);
    }


    @Override
    protected IBaseMapper<AdvertVo, AdvertVo, Integer> getDao() {
        return null;
    }


}
