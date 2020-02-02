package com.jh.forum.bbs.mapping;


import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.bbs.entity.ForumLabel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 论坛标签相关接口
 */
@Mapper
public interface IForumLabelMapper extends IBaseMapper<ForumLabel,ForumLabel,Integer> {

    /**
     * 根据标签名称查询对应标签颜色
     * @param labelName 标签名称
     * @return
     * @version <1> 2019/3/8 15:14 zhangshen:Created.
     */
    List<ForumLabel> findLabelByName(String labelName);

    /**
     * 查询所有内置标签
     * @return
     * @version <1> 2019/3/11 13:34 wangli:Created.
     */
    List<ForumLabel> findAllLabel();

    /**
     * 后台分页查询标签
     * @param forumLabel
     * @return
     */
    List findByPageCms(ForumLabel forumLabel);

}
