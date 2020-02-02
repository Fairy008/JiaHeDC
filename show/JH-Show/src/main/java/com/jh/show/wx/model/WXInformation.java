package com.jh.show.wx.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 最新资讯关键字
 * Created by XZG on 2018/4/27.
 */
public class WXInformation {

    private Integer id;
    private String wxId;//微信ID
    private String keyWord;//关键字
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date pushTime;//最新推送时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date followTime;//关注时间


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Date getFollowTime() {
        return followTime;
    }

    public void setFollowTime(Date followTime) {
        this.followTime = followTime;
    }
}
