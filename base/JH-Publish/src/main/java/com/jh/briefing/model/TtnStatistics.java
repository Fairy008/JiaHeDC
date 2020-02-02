package com.jh.briefing.model;

import java.util.List;

/**
 * 地温统计信息 包括最低地温、最高地温，有效最高积温，最低积温
 * Created by lxy on 2018/4/13.
 */
public class TtnStatistics {

    //物候期作物适宜情况说明，如是否利于拔节
    private List<String> instructions;

    /**
     * 最高、最小日均地温，及区域名称
     */
    private TtnRegionTotal maxTtnAvg;
    private TtnRegionTotal minTtnAvg;


    /**
     * 麦播以来地温有效积温,包括最高、最低
     */
    private TtnRegionTotal maxTtnTotal;
    private TtnRegionTotal minTtnTotal;

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public TtnRegionTotal getMaxTtnAvg() {
        return maxTtnAvg;
    }

    public void setMaxTtnAvg(TtnRegionTotal maxTtnAvg) {
        this.maxTtnAvg = maxTtnAvg;
    }

    public TtnRegionTotal getMinTtnAvg() {
        return minTtnAvg;
    }

    public void setMinTtnAvg(TtnRegionTotal minTtnAvg) {
        this.minTtnAvg = minTtnAvg;
    }

    public TtnRegionTotal getMaxTtnTotal() {
        return maxTtnTotal;
    }

    public void setMaxTtnTotal(TtnRegionTotal maxTtnTotal) {
        this.maxTtnTotal = maxTtnTotal;
    }

    public TtnRegionTotal getMinTtnTotal() {
        return minTtnTotal;
    }

    public void setMinTtnTotal(TtnRegionTotal minTtnTotal) {
        this.minTtnTotal = minTtnTotal;
    }
}
