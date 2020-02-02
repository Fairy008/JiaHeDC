package com.jh.briefing.model;

/**
 * 地温信息，装载地温日均值，有效积温值，共用这个类
 * Created by lxy on 2018/4/15.
 */
public class TtnRegionTotal {
    private Double ttnAvg; //地温
    private String regionName;//区域名称

    public Double getTtnAvg() {
        return ttnAvg;
    }

    public void setTtnAvg(Double ttnAvg) {
        this.ttnAvg = ttnAvg;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
