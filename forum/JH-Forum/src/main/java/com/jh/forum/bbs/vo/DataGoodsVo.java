package com.jh.forum.bbs.vo;

import com.jh.forum.bbs.entity.ForumDataGoods;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

import java.util.List;


/**
 * @Description 数据订单
 * @Version <1> 2019-08-14 sxj:Create.
 **/
public class DataGoodsVo  extends ForumDataGoods {

    private String regionCode;
    private Integer accuracy;
    private Integer cropId;
    private Integer dataClassify;
    private Long regionId;

    @IdTransform(type = CacheUtil.CACHE_DICT_TYPE, propName = "dataClassify")
    private String classifyName;


    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId")
    private String regionName;


    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId")
    private String cropName;


    private List<Integer> dataIds;


    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }



    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
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

    public List<Integer> getDataIds() {
        return dataIds;
    }

    public void setDataIds(List<Integer> dataIds) {
        this.dataIds = dataIds;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public Integer getDataClassify() {
        return dataClassify;
    }

    public void setDataClassify(Integer dataClassify) {
        this.dataClassify = dataClassify;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }


}
