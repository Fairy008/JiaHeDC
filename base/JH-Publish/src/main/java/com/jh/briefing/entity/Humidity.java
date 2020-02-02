package com.jh.briefing.entity;

/**
 * 湿度
 */
public class Humidity {
    private Integer humId; //湿度编号（主键）

    private String humName; //湿度名称

    private Integer rangeStart; //范围开始

    private Integer rangeEnd; //范围结束

    public Integer getHumId() {
        return humId;
    }

    public void setHumId(Integer humId) {
        this.humId = humId;
    }

    public String getHumName() {
        return humName;
    }

    public void setHumName(String humName) {
        this.humName = humName == null ? null : humName.trim();
    }

    public Integer getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(Integer rangeStart) {
        this.rangeStart = rangeStart;
    }

    public Integer getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(Integer rangeEnd) {
        this.rangeEnd = rangeEnd;
    }
}