package com.jh.forum.bbs.vo;

import com.jh.entity.BaseEntity;
import com.jh.forum.bbs.entity.ForumExpert;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

import java.util.List;

/**
 * 专家VO
 */
public class ExpertVO extends ForumExpert {

  private Integer followerId;

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

}