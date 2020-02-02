package com.jh.briefingNew.model;

import java.time.LocalDateTime;

/**
 * description:
 *
 * @version <1> 2018-07-16 wl: Created.
 */
public class chartDatas {
    private Double avgData;//平均值
    private String dataTime;//日期

    public Double getAvgData() {
        return avgData;
    }

    public void setAvgData(Double avgData) {
        this.avgData = avgData;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
}
