package com.jh.briefingNew.model;

/**
 * description:日均地温
 *
 * @version <1> 2018-07-20 wl: Created.
 */
public class TtnRegionAvg {
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
