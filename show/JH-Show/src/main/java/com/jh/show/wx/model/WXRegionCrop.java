package com.jh.show.wx.model;

/**
 * 微信用户区域作物配置
 * Created by XZG on 2018/4/27.
 */
public class WXRegionCrop {

    private Integer id;
    private String tel;//手机号
    private Long regionId;//区域ID
    private Integer[] cropIdList;//作物ID
    private String wxId;//微信ID
    private String regionName;//区域名称
    private String cropName;//作物名称

    private Integer cropId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Integer[] getCropIdList() {
        return cropIdList;
    }

    public void setCropIdList(Integer[] cropIdList) {
        this.cropIdList = cropIdList;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }


    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }
}
