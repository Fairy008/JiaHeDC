package com.jh.forum.cache.mapping;

import com.jh.forum.cache.vo.CommentCacheVO;
import com.jh.forum.cache.vo.ForumCacheVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 社区缓存Mapper
 * @param
 * @return 
 * @version <1> 2019/3/6 10:40 zhangshen:Created.
 */
@Mapper
public interface ICacheMapper {

    /**
     * 查询每个帖子点赞数、一级评论数
     * @param
     * @return 
     * @version <1> 2019/3/6 10:40 zhangshen:Created.
     */
    List<ForumCacheVO> findFollowCommentNum();

    /**
     * 查询二级评论数
     * @param
     * @return 
     * @version <1> 2019/3/6 10:47 zhangshen:Created.
     */
    List<CommentCacheVO> findSecondCommentNum();

    /**
     * 根据帖子ID查询数据库表的点赞数、一级评论数
     * @param articleId 帖子ID
     * @return ResultMessage
     * @version <1> 2019/3/6 13:05 zhangshen:Created.
     */
    ForumCacheVO queryFollowCommentNumByArticleId(Integer articleId);

    /**
     * 根据评论ID查询数据库表的二级评论数
     * @param commentId 评论ID
     * @return ResultMessage
     * @version <1> 2019/3/6 13:11 zhangshen:Created.
     */
    CommentCacheVO querySecondCommentNumByCommentId(Integer commentId);

    /**
     * 根据帖子ID查询二级评论数
     * @param articleId 帖子ID
     * @return list
     * @version <1> 2019/3/7 11:02 zhangshen:Created.
     */
    List<CommentCacheVO> findSecondCommentNumByArticleId(Integer articleId);
}
