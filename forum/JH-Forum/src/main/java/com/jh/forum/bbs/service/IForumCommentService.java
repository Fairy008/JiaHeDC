package com.jh.forum.bbs.service;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.service.IBaseService;
import com.jh.forum.bbs.entity.ForumArticle;
import com.jh.forum.bbs.entity.ForumComment;
import com.jh.forum.bbs.vo.CommentVo;
import com.jh.vo.ResultMessage;


/**
 * @Description:  用户评论接口
 * @version<1> 2019-3-5 lcw :Created.
 */
public interface IForumCommentService extends IBaseService<ForumComment, ForumComment,Integer> {


    /**
     * 发表评论
     * @param forumComment
     * @return
     * @version<1> 2019-03-05 lcw : Created.
     */
    public ResultMessage addFirstComment(ForumComment forumComment);


    /**
     * 发表基于第一级评论的下级评论（支持多级，对评论的回复采用“我的评论内容  \\某某用户  某用户的评论内容”的格式进行关联）
     * @param forumComment
     * @return
     * @version<1> 2019-03-05 lcw : Created.
     */
    public ResultMessage addCommentInFirstCommentId(ForumComment forumComment);

    /**
     * 根据帖子ID分页查询评论数据(查询一级评论)
     * @param CommentVo : 帖子ID 分页字段
     * @return
     * @version<1> 2019-03-05 lcw : Created.
     */
    public PageInfo<CommentVo> findCommentsByPage(CommentVo CommentVo);


    /**
     * 根据帖子第一级的评论ID查询所有下级的评论
     * @param firstCommentId  第一级评论ID
     * @return
     * @version<1> 2019-03-05 lcw : Created.
     */
    public ResultMessage findCommentsByFirstCommentId(Integer firstCommentId);


    /**
     * 删除评论
     * @param CommentVo 评论ID
     * @return
     * @version<1> 2019-03-05 lcw : Created.
     */
    public ResultMessage deleteComment(CommentVo CommentVo);


    /**
     * 根据帖子删除评论
     * @param articleId  帖子ID
     * @return
     * @version<1> 2019-03-05 lcw : Created.
     */
    public ResultMessage deleteCommentByArticleId(Integer articleId);


}
