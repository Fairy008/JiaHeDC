package com.jh.briefingNew.model;

/** 雨量统计信息-展示不同区域的降雨量信息
 * Created by lxy on 2018/4/13.
 */
public class TrrmStatisticsMultRegionNew {
    private Long regionId;//区域编号
    private String regionName;//区域名称
    private Double trrmTotal;//总降雨量
    private Double trrmTotalMonth;//月降雨量
    private Double percentLastYear;//较去年同期降雨量增幅比率
    private Double percentHistoryYear;//较历史同期降雨量增幅比率。
    private Double percentMonthHistoryYear=0.0;//较历史月降雨量均值增幅比率。avgTrrmLastYear,avgTrrmHistory,avgTrrmMonthHistory,
    private Double avgTrrmLastYear;//去年同期雨量
    private Double avgTrrmHistory;//历史同期雨量
    private Double avgTrrmMonthHistory;//历史月度同期雨量
    private String airHumidity;//空气湿度（按照上面周雨量判断）
    private String soilMoisture;//墒情级别

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

    public Double getTrrmTotal() {
        return trrmTotal;
    }

    public void setTrrmTotal(Double trrmTotal) {
        this.trrmTotal = trrmTotal;
    }

    public Double getPercentLastYear() {
        return percentLastYear;
    }

    public void setPercentLastYear(Double percentLastYear) {
        this.percentLastYear = percentLastYear;
    }

    public Double getPercentHistoryYear() {
        return percentHistoryYear;
    }

    public void setPercentHistoryYear(Double percentHistoryYear) {
        this.percentHistoryYear = percentHistoryYear;
    }

    public String getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(String airHumidity) {
        this.airHumidity = airHumidity;
    }

    public String getSoilMoisture() {
        return soilMoisture;
    }

    public void setSoilMoisture(String soilMoisture) {
        this.soilMoisture = soilMoisture;
    }

    public Double getTrrmTotalMonth() {
        return trrmTotalMonth;
    }

    public void setTrrmTotalMonth(Double trrmTotalMonth) {
        this.trrmTotalMonth = trrmTotalMonth;
    }

    public Double getPercentMonthHistoryYear() {
        return percentMonthHistoryYear;
    }

    public void setPercentMonthHistoryYear(Double percentMonthHistoryYear) {
        this.percentMonthHistoryYear = percentMonthHistoryYear;
    }

    public Double getAvgTrrmLastYear() {
        return avgTrrmLastYear;
    }

    public void setAvgTrrmLastYear(Double avgTrrmLastYear) {
        this.avgTrrmLastYear = avgTrrmLastYear;
    }

    public Double getAvgTrrmHistory() {
        return avgTrrmHistory;
    }

    public void setAvgTrrmHistory(Double avgTrrmHistory) {
        this.avgTrrmHistory = avgTrrmHistory;
    }

    public Double getAvgTrrmMonthHistory() {
        return avgTrrmMonthHistory;
    }

    public void setAvgTrrmMonthHistory(Double avgTrrmMonthHistory) {
        this.avgTrrmMonthHistory = avgTrrmMonthHistory;
    }
}
