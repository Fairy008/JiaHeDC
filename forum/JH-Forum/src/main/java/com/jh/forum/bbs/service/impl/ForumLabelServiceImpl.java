package com.jh.forum.bbs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.base.service.impl.BaseServiceImpl;
import com.jh.forum.bbs.constant.ForumConstant;
import com.jh.forum.bbs.entity.ForumLabel;
import com.jh.forum.bbs.mapping.IForumLabelMapper;
import com.jh.forum.bbs.service.IForumLabelService;
import com.jh.forum.bbs.util.CommonUtils;
import com.jh.util.cache.IdTransformUtils;
import com.jh.vo.ResultMessage;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 论坛标签相关方法
 */
@Service
@Transactional
public class ForumLabelServiceImpl extends BaseServiceImpl<ForumLabel,ForumLabel,Integer> implements IForumLabelService {

    @Autowired
    private IForumLabelMapper forumLabelMapper;

    /**
     * 继承基础接口的增删改查功能
     * @param
     * @return
     * @version <1> 2019/3/5 mason:Created.
     */
    @Override
    protected IBaseMapper<ForumLabel, ForumLabel, Integer> getDao() {
        return forumLabelMapper;
    }

    /**
     * 查询标签列表
     * @param forumLabel
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @Override
    public PageInfo<ForumLabel> findLabelList(ForumLabel forumLabel) {
        PageHelper.startPage(forumLabel.getPage(), forumLabel.getRows());
        List<ForumLabel> list = forumLabelMapper.findByPage(forumLabel);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<ForumLabel>(list);
    }

    /**
     * 根据标签名称查询对应标签颜色
     * @param labelName 标签名称
     * @return
     * @version <1> 2019/3/8 15:14 zhangshen:Created.
     */
    @Override
    public List<ForumLabel> findLabelByName(String labelName) {
        return forumLabelMapper.findLabelByName(labelName);
    }

    @Override
    public ResultMessage findAllLabel() {
        List<ForumLabel> listLabel = forumLabelMapper.findAllLabel();
        return ResultMessage.success(listLabel);
    }

    /**
     * 随机查询标签列表
     * @param
     * @return
     * @version <1> 2019/3/13 mason:Created.
     */
    @Override
    public ResultMessage findRandomLabelList() {
        List<ForumLabel> listLabel = forumLabelMapper.findAllLabel();
        if (CollectionUtils.isNotEmpty(listLabel) && listLabel.size() > ForumConstant.CHANGE_BATCHES_LABEL_COUNT){
            listLabel = CommonUtils.getSubStringByRadom(listLabel,ForumConstant.CHANGE_BATCHES_LABEL_COUNT);
        }
        return ResultMessage.success(listLabel);
    }

    /**
     * 查询标签列表
     * @param forumLabel
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @Override
    public PageInfo<ForumLabel> findLabelListCms(ForumLabel forumLabel) {
        PageHelper.startPage(forumLabel.getPage(), forumLabel.getRows());
        List<ForumLabel> list = forumLabelMapper.findByPageCms(forumLabel);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<ForumLabel>(list);
    }
}
