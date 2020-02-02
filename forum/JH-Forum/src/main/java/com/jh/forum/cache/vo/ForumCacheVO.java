package com.jh.forum.cache.vo;

import com.jh.forum.cache.base.ForumIdTransform;
import com.jh.util.CacheUtil;

/**
 * 点赞数、一级评论数VO
 * @param
 * @return
 * @version <1> 2019/3/6 10:35 zhangshen:Created.
 */
public class ForumCacheVO {

    //帖子ID
    private Integer articleId;

    //点赞数
    @ForumIdTransform(type = CacheUtil.CACHE_FOLLOW_FIRSTCOMMENT, propName = "articleId", transType = CacheUtil.CACHE_FORUM_TRANS_LIKE)
    private Integer followNum;

    //评论数
    @ForumIdTransform(type = CacheUtil.CACHE_FOLLOW_FIRSTCOMMENT, propName = "articleId", transType = CacheUtil.CACHE_FORUM_TRANS_COMMENT)
    private Integer commentNum;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getFollowNum() {
        return followNum;
    }

    public void setFollowNum(Integer followNum) {
        this.followNum = followNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }
}
