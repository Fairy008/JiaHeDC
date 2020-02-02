package com.jh.collection.entity.vo;

/**
 * 经纬度标注信息
 */
public class CoordingatesVo {

    private Integer id;//子任务ID
    private String longitude;//经度
    private String latitude;//纬度
    private String surveyDddress;//调查地址

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSurveyDddress() {
        return surveyDddress;
    }

    public void setSurveyDddress(String surveyDddress) {
        this.surveyDddress = surveyDddress;
    }
}
