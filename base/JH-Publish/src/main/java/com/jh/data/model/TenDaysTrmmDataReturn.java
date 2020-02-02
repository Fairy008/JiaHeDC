package com.jh.data.model;

/**
 *  装载上中下旬的雨量总和以及地温的均值
 *  返回对象
 *  Created by lxy on 2018-3-22.
 */

public class TenDaysTrmmDataReturn {

    private String date; //日期（YYYY-MM格式）
    private long regionId;//区域编号
    private int level;//行政区级别
    private String regionName;//区域中文名

    /**
     * 雨量
     */
    private double waterFirst; //雨量每月上旬值 （总和）
    private double waterSecond; //雨量每月中旬值（总和）
    private double waterThird;//雨量每月下旬值（总和）

    /**
     * 地热
     */
    private double tempFirst;//地热温度每月上旬值 （均值）
    private double tempSecond;//地热温度每月中旬值 （均值）
    private double tempThird;//地热温度每月下旬值 （均值）


    /**
     * 历史 雨量平均值  地热平均值
     */

    private double waterFirstAvg; //历史雨量上旬均值
    private double waterSecondAvg; //历史雨量中旬均值
    private double waterThirdAvg;//历史雨量下旬均值

    private double tempFirstAvg;//历史地热温度上旬均值
    private double tempSecondAvg;//历史地热温度中旬均值
    private double tempThirdAvg;//历史地热温度下旬均值




    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public double getWaterFirst() {
        return waterFirst;
    }

    public void setWaterFirst(double waterFirst) {
        this.waterFirst = waterFirst;
    }

    public double getWaterSecond() {
        return waterSecond;
    }

    public void setWaterSecond(double waterSecond) {
        this.waterSecond = waterSecond;
    }

    public double getWaterThird() {
        return waterThird;
    }

    public void setWaterThird(double waterThird) {
        this.waterThird = waterThird;
    }

    public double getTempFirst() {
        return tempFirst;
    }

    public void setTempFirst(double tempFirst) {
        this.tempFirst = tempFirst;
    }

    public double getWaterFirstAvg() {
        return waterFirstAvg;
    }

    public void setWaterFirstAvg(double waterFirstAvg) {
        this.waterFirstAvg = waterFirstAvg;
    }

    public double getWaterSecondAvg() {
        return waterSecondAvg;
    }

    public void setWaterSecondAvg(double waterSecondAvg) {
        this.waterSecondAvg = waterSecondAvg;
    }

    public double getWaterThirdAvg() {
        return waterThirdAvg;
    }

    public void setWaterThirdAvg(double waterThirdAvg) {
        this.waterThirdAvg = waterThirdAvg;
    }

    public double getTempFirstAvg() {
        return tempFirstAvg;
    }

    public void setTempFirstAvg(double tempFirstAvg) {
        this.tempFirstAvg = tempFirstAvg;
    }

    public double getTempSecondAvg() {
        return tempSecondAvg;
    }

    public void setTempSecondAvg(double tempSecondAvg) {
        this.tempSecondAvg = tempSecondAvg;
    }

    public double getTempThirdAvg() {
        return tempThirdAvg;
    }

    public void setTempThirdAvg(double tempThirdAvg) {
        this.tempThirdAvg = tempThirdAvg;
    }

    public double getTempSecond() {
        return tempSecond;
    }

    public void setTempSecond(double tempSecond) {
        this.tempSecond = tempSecond;
    }

    public double getTempThird() {
        return tempThird;
    }

    public void setTempThird(double tempThird) {
        this.tempThird = tempThird;
    }

    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
}
