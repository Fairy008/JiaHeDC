package com.jh.collection.entity.vo;

/**
 * @Description: 采集任务查询参数对象
 * @version <1> 2019-03-18 cxw:Created.
 */
public class BbsCollectionTaskVo {
    private String phone;//手机号
    private String startCreateTime;//结束创建时间
    private String endCreateTime;//结束创建时间
    private Long RegionId;//区域ID

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(String startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public Long getRegionId() {
        return RegionId;
    }

    public void setRegionId(Long regionId) {
        RegionId = regionId;
    }
}
