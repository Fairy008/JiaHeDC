package com.jh.forum.bbs.entity;

import com.jh.entity.BaseEntity;

import java.time.LocalDate;

public class ForumShoppingCar extends BaseEntity {

    private Integer cId;

    private Integer userId;

    private Integer dataId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

}
