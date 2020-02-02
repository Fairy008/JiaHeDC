package com.jh.report.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 * 1.图形参数
 *
 * @version <1> 2018-05-10 17:05 zhangshen: Created.
 */
public class ImageParam {
    private String regionName;
    private String cropName;
    private String description;
    private String unit;
    private Integer year;
    private String storagePth;
    private List<ImageData> imageDataList = new LinkedList<ImageData>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getStoragePth() {
        return storagePth;
    }

    public void setStoragePth(String storagePth) {
        this.storagePth = storagePth;
    }

    public List<ImageData> getImageDataList() {
        return imageDataList;
    }

    public void setImageDataList(List<ImageData> imageDataList) {
        this.imageDataList = imageDataList;
    }
}
