package com.jh.collection.entity.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 前端展示采集数据统计
 */
public class CollectionStatisticsVo {
    private List<String> collectionDateList = new ArrayList<String>();//年份
    private List<Double> areaList = new ArrayList<Double>();//面积
    private List<CollectionFieldModelVo> currentYearCollectionData = new ArrayList<CollectionFieldModelVo>();
    private Double maxArea = 0d;//最大面积

    public List<String> getCollectionDateList() {
        return collectionDateList;
    }

    public void setCollectionDateList(List<String> collectionDateList) {
        this.collectionDateList = collectionDateList;
    }
    public List<Double> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Double> areaList) {
        this.areaList = areaList;
    }

    public List<CollectionFieldModelVo> getCurrentYearCollectionData() {
        return currentYearCollectionData;
    }

    public void setCurrentYearCollectionData(List<CollectionFieldModelVo> currentYearCollectionData) {
        this.currentYearCollectionData = currentYearCollectionData;
    }

    public Double getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(Double maxArea) {
        this.maxArea = maxArea;
    }
}
