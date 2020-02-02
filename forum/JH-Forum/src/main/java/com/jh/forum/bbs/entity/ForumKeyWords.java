package com.jh.forum.bbs.entity;

import com.jh.entity.BaseEntity;

public class ForumKeyWords extends BaseEntity {
    private Integer kId;

    private String keyWord;

    public Integer getkId() {
        return kId;
    }

    public void setkId(Integer kId) {
        this.kId = kId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
