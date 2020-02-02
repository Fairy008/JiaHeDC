package com.jh.forum.bbs.mapping;

import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.bbs.entity.ForumComment;
import com.jh.forum.bbs.vo.CommentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 论坛文章评论查询接口
 */
@Mapper
public interface IForumCommentMapper extends IBaseMapper<ForumComment, ForumComment,Integer> {


    /**
     * 根据帖子ID查询评论
     * @param commentVo  帖子ID  分页字段
     * @return
     */
    List<CommentVo> findCommentsByPage(CommentVo commentVo);

    /**
     * 根据第一级的评论ID查询所有第二级评论
     * @param firstCommentId
     * @return
     */
    List<CommentVo> findCommentsByFirstCommentId(Integer firstCommentId);

    /**
     * 根据评论ID删除评论
     * @param commentId
     */
    void deleteByCommentId(Integer commentId);

    /**
     * 根据帖子ID删除评论
     * @param articleId
     */
    void deleteCommentByArticleId(Integer articleId);
}