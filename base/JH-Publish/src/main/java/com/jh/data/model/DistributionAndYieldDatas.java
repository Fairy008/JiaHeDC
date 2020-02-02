package com.jh.data.model;

/**
 * Description:
 * 1.用于定义分布或估产数据集合
 *
 * @version <1> 2018-05-10 16:37 zhangshen: Created.
 */
public class DistributionAndYieldDatas {
    private Float area;//面积
    private Float compare;//面积增减值
    private Float percent;//百分比
    private Integer year;//年
    private String regionName;//区域名称
    private Long regionId;

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public Float getCompare() {
        return compare;
    }

    public void setCompare(Float compare) {
        this.compare = compare;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
}
