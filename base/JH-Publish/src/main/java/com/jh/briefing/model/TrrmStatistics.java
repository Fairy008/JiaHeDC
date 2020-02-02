package com.jh.briefing.model;

import java.util.List;

/** 雨量统计信息
 * Created by lxy on 2018/4/13.
 */
public class TrrmStatistics {
    /**
     * 降雨量基础信息
     */
    private Integer rainDays; //本周降雨天数，降雨量大于5mm
    private TrrmRegionMaxDay trrmRegionMaxDay; //日最大降雨量


    /**
     * 周最大降雨量区域 最小降雨量区域 和相关雨量
     */
    TrrmStatisticsMultRegion maxTrrmRegion;
    TrrmStatisticsMultRegion minTrrmRegion;

    /**
     *  雨量按区域统计信息
     */
    List<TrrmStatisticsMultRegion> trrmStatisticsRegion;

    //干旱雨量信息
    TrrmStatisticsMultRegion ganhanOne;
    //干旱雨量信息
    TrrmStatisticsMultRegion ganhanTwo;

    //墒情雨量信息
    TrrmStatisticsMultRegion shanOne;
    //墒情雨量信息
    TrrmStatisticsMultRegion shanTwo;



    public Integer getRainDays() {
        return rainDays;
    }

    public void setRainDays(Integer rainDays) {
        this.rainDays = rainDays;
    }

    public TrrmStatisticsMultRegion getMaxTrrmRegion() {
        return maxTrrmRegion;
    }

    public void setMaxTrrmRegion(TrrmStatisticsMultRegion maxTrrmRegion) {
        this.maxTrrmRegion = maxTrrmRegion;
    }

    public TrrmStatisticsMultRegion getMinTrrmRegion() {
        return minTrrmRegion;
    }

    public void setMinTrrmRegion(TrrmStatisticsMultRegion minTrrmRegion) {
        this.minTrrmRegion = minTrrmRegion;
    }

    public TrrmRegionMaxDay getTrrmRegionMaxDay() {
        return trrmRegionMaxDay;
    }

    public void setTrrmRegionMaxDay(TrrmRegionMaxDay trrmRegionMaxDay) {
        this.trrmRegionMaxDay = trrmRegionMaxDay;
    }

    public List<TrrmStatisticsMultRegion> getTrrmStatisticsRegion() {
        return trrmStatisticsRegion;
    }

    public void setTrrmStatisticsRegion(List<TrrmStatisticsMultRegion> trrmStatisticsRegion) {
        this.trrmStatisticsRegion = trrmStatisticsRegion;
    }

    public TrrmStatisticsMultRegion getGanhanOne() {
        return ganhanOne;
    }

    public void setGanhanOne(TrrmStatisticsMultRegion ganhanOne) {
        this.ganhanOne = ganhanOne;
    }

    public TrrmStatisticsMultRegion getGanhanTwo() {
        return ganhanTwo;
    }

    public void setGanhanTwo(TrrmStatisticsMultRegion ganhanTwo) {
        this.ganhanTwo = ganhanTwo;
    }

    public TrrmStatisticsMultRegion getShanOne() {
        return shanOne;
    }

    public void setShanOne(TrrmStatisticsMultRegion shanOne) {
        this.shanOne = shanOne;
    }

    public TrrmStatisticsMultRegion getShanTwo() {
        return shanTwo;
    }

    public void setShanTwo(TrrmStatisticsMultRegion shanTwo) {
        this.shanTwo = shanTwo;
    }
}
