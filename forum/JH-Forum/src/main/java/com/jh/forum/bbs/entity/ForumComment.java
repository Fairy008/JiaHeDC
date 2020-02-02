package com.jh.forum.bbs.entity;

import com.jh.entity.BaseEntity;

import java.util.Date;

/**
 * 论坛评论实体类
 */
public class ForumComment extends BaseEntity {

    //评论id
    private Integer commentId;

    //文章id
    private Integer articleId;

    //第一级评论ID
    private Integer firstCommentId;

    //评论层级
    private String level;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //评论内容
    private String content;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getFirstCommentId() {
        return firstCommentId;
    }

    public void setFirstCommentId(Integer firstCommentId) {
        this.firstCommentId = firstCommentId;
    }
}