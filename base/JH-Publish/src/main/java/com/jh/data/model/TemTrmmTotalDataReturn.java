package com.jh.data.model;

/**
 * 装载最后统计好的 上中下旬的雨量均值地热总和
 * 以及历年上中下旬的雨量平均值和地温平均值
 * Created by lxy on 2018/3/22.
 */
public class TemTrmmTotalDataReturn{
    //区域编号
    private long regionId;
    //行政区级别
    private int level;
    //区域名称
    private String regionName;
    //日期（YYYY-MM格式）
    private String date;
    //日期（YYYY-MM上中下旬格式）
    private String dateFlag;
    //雨量（上中下旬）
    private double trmm;
    //历年雨量平均值
    private double trmmYeasAvg;
    //地温/雨量 - 历年地温/雨量平均值 所获得差值
    private double distance;
    //雨量 / 历年雨量平均值 百分表
    private String percent;
    //地温（上中下旬）
    private double temp;
    //历年地温平均值
    private double tempYearsAvg;

    public String getRegionName() {
        return regionName;
    }

    public String getDateFlag() {
        return dateFlag;
    }

    public void setDateFlag(String dateFlag) {
        this.dateFlag = dateFlag;
    }

    public double getTrmm() {
        return trmm;
    }

    public void setTrmm(double trmm) {
        this.trmm = trmm;
    }

    public double getTrmmYeasAvg() {
        return trmmYeasAvg;
    }

    public void setTrmmYeasAvg(double trrmYeasAvg) {
        this.trmmYeasAvg = trrmYeasAvg;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempYearsAvg() {
        return tempYearsAvg;
    }

    public void setTempYearsAvg(double tempYearsAvg) {
        this.tempYearsAvg = tempYearsAvg;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
