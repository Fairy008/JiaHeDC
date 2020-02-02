package com.jh.show.agric.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * description:客户区域定制表
 * @version <1> 2018-08-10 cxw: Created.
 */
//@Table(name = "custom_locale")
public class CustomLocaleEntity {

  //  @Column(name="user_id")
    private Integer userId;
    //@Column(name="region_id")
    private String regionId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
}
