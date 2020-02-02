package com.jh.forum.bbs.service;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.service.IBaseService;
import com.jh.forum.bbs.entity.ForumLabel;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 * 论坛标签相关接口
 */
public interface IForumLabelService extends IBaseService<ForumLabel,ForumLabel,Integer> {


    /**
     * 查询标签列表
     * @param forumLabel
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    PageInfo<ForumLabel> findLabelList(ForumLabel forumLabel);

    /**
     * 根据标签名称查询对应标签颜色
     * @param labelName 标签名称
     * @return 
     * @version <1> 2019/3/8 15:14 zhangshen:Created.
     */
    List<ForumLabel> findLabelByName(String labelName);



    /**
     * 写报告写调研时查询所有的内置标签
     * @return
     * @version <1> 2019/3/11 13:20 wangli:Created.
     */
    ResultMessage findAllLabel();

    /**
     * 随机查询标签列表
     * @param
     * @return
     * @version <1> 2019/3/13 mason:Created.
     */
    ResultMessage findRandomLabelList();

    /**
     * 后台分页查询标签
     * @param forumLabel
     * @return
     */
    PageInfo<ForumLabel> findLabelListCms(ForumLabel forumLabel);

}
