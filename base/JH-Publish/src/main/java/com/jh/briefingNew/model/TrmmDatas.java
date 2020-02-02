package com.jh.briefingNew.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-07-16 wl: Created.
 */
public class TrmmDatas {
    private Long regionId;//区域id
    private Double trmm;//平均值
    private String regionName;//区域名
    private String parentRegionName;//父级区域名
    private LocalDateTime dateTime;//日期

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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Double getTrmm() {
        return trmm;
    }

    public void setTrmm(Double trmm) {
        this.trmm = trmm;
    }

    public String getParentRegionName() {
        return parentRegionName;
    }

    public void setParentRegionName(String parentRegionName) {
        this.parentRegionName = parentRegionName;
    }
}
