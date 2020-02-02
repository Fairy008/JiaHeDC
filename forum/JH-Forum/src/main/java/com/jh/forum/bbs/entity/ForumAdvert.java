package com.jh.forum.bbs.entity;

import com.jh.entity.BaseEntity;
import java.time.LocalDate;

/**
 * @author sxj
 * @Description 广告位实体
 * @Date 2019/8/13 15:25
 * @Version 1.0
 **/
public class ForumAdvert extends BaseEntity {

    private Integer advertId;

    private Integer advertise;
    private String title;
    private Integer status;
    private String picture;
    private String url;
    private Integer publisher;
    private LocalDate publishTime;


    public Integer getAdvertId() {
        return advertId;
    }

    public void setAdvertId(Integer advertId) {
        this.advertId = advertId;
    }

    public Integer getAdvertise() {
        return advertise;
    }

    public void setAdvertise(Integer advertise) {
        this.advertise = advertise;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDate publishTime) {
        this.publishTime = publishTime;
    }
}
