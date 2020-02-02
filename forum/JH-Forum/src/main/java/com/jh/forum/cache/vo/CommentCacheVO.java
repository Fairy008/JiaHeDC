package com.jh.forum.cache.vo;

/**
 * 评论数缓存VO
 * @param
 * @return 
 * @version <1> 2019/3/6 10:50 zhangshen:Created.
 */
public class CommentCacheVO {

    //评论ID
    private Integer commentId;

    //帖子ID
    private Integer articleId;

    //评论数
    private Integer commentNum;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }
}
