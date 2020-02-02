package com.jh.forum.bbs.vo;

import com.jh.forum.bbs.entity.ForumDownloadData;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @Description:
 * @version<1> 2019-07-01 lcw :Created.
 */
public class DownloadDataVo extends ForumDownloadData {

    //搜索关键词
    private String keyWords ;

    private Integer timeFlag ;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer accuracyBegin;

    private Integer accuracyEnd;

    @IdTransform(type = CacheUtil.CACHE_DICT_TYPE, propName = "dataClassify")
    private String classifyName;


    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId")
    private String regionName;


    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId")
    private String cropName;

    private String imageUrl;

    private List<Object> fileList;


    private List<Integer> dataIds;


    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "pushStatus")
    private String pushStatusName;

    private Integer checkNum;

    private Integer collectNum;

    private Integer downloadNum;

    private BigDecimal price;

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "accuracy")
    private String accuracyName;

    private List<Integer> idList;

    private String dataPackagePath;

    private Integer userId;

    private Integer dictId;

    private Integer tagType;

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

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Integer getTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(Integer timeFlag) {
        this.timeFlag = timeFlag;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getAccuracyBegin() {
        return accuracyBegin;
    }

    public void setAccuracyBegin(Integer accuracyBegin) {
        this.accuracyBegin = accuracyBegin;
    }

    public Integer getAccuracyEnd() {
        return accuracyEnd;
    }

    public void setAccuracyEnd(Integer accuracyEnd) {
        this.accuracyEnd = accuracyEnd;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Object> getFileList() {
        return fileList;
    }

    public void setFileList(List<Object> fileList) {
        this.fileList = fileList;
    }


    public String getPushStatusName() {
        return pushStatusName;
    }

    public void setPushStatusName(String pushStatusName) {
        this.pushStatusName = pushStatusName;
    }


    public List<Integer> getDataIds() {
        return dataIds;
    }

    public void setDataIds(List<Integer> dataIds) {
        this.dataIds = dataIds;
    }

    @Override
    public Integer getCollectNum() {
        return collectNum;
    }

    @Override
    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    @Override
    public Integer getDownloadNum() {
        return downloadNum;
    }

    @Override
    public void setDownloadNum(Integer downloadNum) {
        this.downloadNum = downloadNum;
    }

    public Integer getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Integer checkNum) {
        this.checkNum = checkNum;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAccuracyName() {
        return accuracyName;
    }

    public void setAccuracyName(String accuracyName) {
        this.accuracyName = accuracyName;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public String getDataPackagePath() {
        return dataPackagePath;
    }

    public void setDataPackagePath(String dataPackagePath) {
        this.dataPackagePath = dataPackagePath;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDictId() {
        return dictId;
    }

    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }

    public Integer getTagType() {
        return tagType;
    }

    public void setTagType(Integer tagType) {
        this.tagType = tagType;
    }
}
