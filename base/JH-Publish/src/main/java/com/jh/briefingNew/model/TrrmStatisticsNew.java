package com.jh.briefingNew.model;


import java.util.List;

/** 雨量统计信息
 * Created by lxy on 2018/4/13.
 */
public class TrrmStatisticsNew {

    /**
     * 周最大降雨量区域 最小降雨量区域 和相关雨量
     */
    TrrmStatisticsMultRegionNew maxTrrmRegion;
    TrrmStatisticsMultRegionNew minTrrmRegion;

    /**
     *  雨量按区域统计信息
     */
    List<TrrmStatisticsMultRegionNew> trrmStatisticsRegion;

    //干旱雨量信息
    TrrmStatisticsMultRegionNew ganhanOne;
    //干旱雨量信息
    TrrmStatisticsMultRegionNew ganhanTwo;

    //墒情雨量信息
    TrrmStatisticsMultRegionNew shanOne;
    //墒情雨量信息
    TrrmStatisticsMultRegionNew shanTwo;

    public TrrmStatisticsMultRegionNew getMaxTrrmRegion() {
        return maxTrrmRegion;
    }

    public void setMaxTrrmRegion(TrrmStatisticsMultRegionNew maxTrrmRegion) {
        this.maxTrrmRegion = maxTrrmRegion;
    }

    public TrrmStatisticsMultRegionNew getMinTrrmRegion() {
        return minTrrmRegion;
    }

    public void setMinTrrmRegion(TrrmStatisticsMultRegionNew minTrrmRegion) {
        this.minTrrmRegion = minTrrmRegion;
    }

    public List<TrrmStatisticsMultRegionNew> getTrrmStatisticsRegion() {
        return trrmStatisticsRegion;
    }

    public void setTrrmStatisticsRegion(List<TrrmStatisticsMultRegionNew> trrmStatisticsRegion) {
        this.trrmStatisticsRegion = trrmStatisticsRegion;
    }

    public TrrmStatisticsMultRegionNew getGanhanOne() {
        return ganhanOne;
    }

    public void setGanhanOne(TrrmStatisticsMultRegionNew ganhanOne) {
        this.ganhanOne = ganhanOne;
    }

    public TrrmStatisticsMultRegionNew getGanhanTwo() {
        return ganhanTwo;
    }

    public void setGanhanTwo(TrrmStatisticsMultRegionNew ganhanTwo) {
        this.ganhanTwo = ganhanTwo;
    }

    public TrrmStatisticsMultRegionNew getShanOne() {
        return shanOne;
    }

    public void setShanOne(TrrmStatisticsMultRegionNew shanOne) {
        this.shanOne = shanOne;
    }

    public TrrmStatisticsMultRegionNew getShanTwo() {
        return shanTwo;
    }

    public void setShanTwo(TrrmStatisticsMultRegionNew shanTwo) {
        this.shanTwo = shanTwo;
    }
}
