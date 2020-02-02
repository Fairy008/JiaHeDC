package com.jh.manage.loader.model;


import com.jh.entity.PageEntity;

/**
 * description:
 *
 * @version <1> 2018-01-24 lcw： Created.
 */

public class LoaderParam extends PageEntity{

    private String loaderName;

    private Integer loaderType;

    private Integer loaderStatus;

    private String creatorName;

    private String beginTime;

    private String endTime;

    private Integer creator;

    public Integer getLoaderType() {
        return loaderType;
    }

    public void setLoaderType(Integer loaderType) {
        this.loaderType = loaderType;
    }

    public Integer getLoaderStatus() {
        return loaderStatus;
    }

    public void setLoaderStatus(Integer loaderStatus) {
        this.loaderStatus = loaderStatus;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLoaderName() {
        return loaderName;
    }

    public void setLoaderName(String loaderName) {
        this.loaderName = loaderName;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }
}
