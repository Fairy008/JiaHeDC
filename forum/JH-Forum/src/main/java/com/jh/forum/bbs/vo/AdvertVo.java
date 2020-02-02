package com.jh.forum.bbs.vo;

import com.jh.forum.bbs.entity.ForumAdvert;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;


/**
 * @author sxj
 * @Description 广告位
 * @Date 2019/8/13 16:17
 * @Version 1.0
 **/
public class AdvertVo extends ForumAdvert {

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "advertise")
    private String advertiseName;

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "status")
    private String statusName;

    private String titleName;
    private String pictureName;
    private String urlName;
    private Integer[] advertIds;


    public String getAdvertiseName() {
        return advertiseName;
    }

    public void setAdvertiseName(String advertiseName) {
        this.advertiseName = advertiseName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public Integer[] getAdvertIds() {
        return advertIds;
    }

    public void setAdvertIds(Integer[] advertIds) {
        this.advertIds = advertIds;
    }
}
