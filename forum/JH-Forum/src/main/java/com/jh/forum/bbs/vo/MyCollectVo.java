package com.jh.forum.bbs.vo;

import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

import java.math.BigDecimal;

public class MyCollectVo {

    private Integer dataId;

    private String dataTitle;

    private String dataClassify;

    private String dataPath;


    @IdTransform(type = CacheUtil.CACHE_DICT_TYPE, propName = "dataClassify")
    private String classifyName;

    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId")
    private String regionName;

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId")
    private String cropName;

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "accuracy")
    private String accuracyName;

    private BigDecimal price;

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public String getDataClassify() {
        return dataClassify;
    }

    public void setDataClassify(String dataClassify) {
        this.dataClassify = dataClassify;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
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

    public String getAccuracyName() {
        return accuracyName;
    }

    public void setAccuracyName(String accuracyName) {
        this.accuracyName = accuracyName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
