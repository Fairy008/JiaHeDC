package com.jh.forum.bbs.mapping;

import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.bbs.entity.ForumFollow;
import org.apache.ibatis.annotations.Mapper;

/**
 * 关注与点赞查询接口
 */
@Mapper
public interface IForumFollowMapper extends IBaseMapper<ForumFollow,ForumFollow,Integer> {


    ForumFollow findFolowByCurrentAccountId(ForumFollow forumFollow);
}