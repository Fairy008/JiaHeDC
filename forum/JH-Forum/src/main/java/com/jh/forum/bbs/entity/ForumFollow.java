package com.jh.forum.bbs.entity;

import com.jh.entity.BaseEntity;

import java.util.Date;
import java.util.Map;

/**
 * 点赞与评论实体类
 */
public class ForumFollow extends BaseEntity {

    //主键
    private Integer followId;

    //文章id
    private Integer articleId;

    //点赞、关注
    private Integer followType;


    private Map<String,Object> personMap;

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getFollowType() {
        return followType;
    }

    public void setFollowType(Integer followType) {
        this.followType = followType;
    }


    public Map<String, Object> getPersonMap() {
        return personMap;
    }

    public void setPersonMap(Map<String, Object> personMap) {
        this.personMap = personMap;
    }
}