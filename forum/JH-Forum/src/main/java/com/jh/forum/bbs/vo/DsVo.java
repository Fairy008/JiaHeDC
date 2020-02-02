package com.jh.forum.bbs.vo;

import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

/**
 * @Description: 数据集精度作物区域对象
 * @version <1> 2019-03-13 cxw:Created.
 */
public class DsVo {
    //区域ID
    private Long regionId;

    //开始日期
    private String startDate;

    //结束日期
    private String endDate;

    //降雨、地温、气温数据所属年月
    private String dataMonth;


    //区域名称
    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId",transType = CacheUtil.CACHE_TRANS_NAME)
    private String regionName;
    //作物ID
    private Integer cropId;
    //作物名称
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId",transType = CacheUtil.CACHE_TRANS_NAME)
    private String cropName;
    //数据集ID
    private Integer dataSetId;
    //精度ID
    private Integer resolutionId;//精度id
    //数据集名称
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "dataSetId",transType = CacheUtil.CACHE_TRANS_NAME)
    private String dataSetName;
    //数据集code
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "dataSetId",transType = CacheUtil.CACHE_TRANS_CODE)
    private String dataSetCode;
    //精度名称
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "resolutionId",transType = CacheUtil.CACHE_TRANS_NAME)
    private String resolutionName;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public Integer getDataSetId() {
        return dataSetId;
    }

    public void setDataSetId(Integer dataSetId) {
        this.dataSetId = dataSetId;
    }

    public Integer getResolutionId() {
        return resolutionId;
    }

    public void setResolutionId(Integer resolutionId) {
        this.resolutionId = resolutionId;
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public String getResolutionName() {
        return resolutionName;
    }

    public void setResolutionName(String resolutionName) {
        this.resolutionName = resolutionName;
    }

    public String getDataSetCode() {
        return dataSetCode;
    }

    public void setDataSetCode(String dataSetCode) {
        this.dataSetCode = dataSetCode;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDataMonth() {
        return dataMonth;
    }

    public void setDataMonth(String dataMonth) {
        this.dataMonth = dataMonth;
    }
}
