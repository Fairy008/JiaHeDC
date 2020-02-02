package com.jh.forum.bbs.vo;

import com.jh.forum.bbs.entity.ForumOrderData;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

/**
 * @Description:
 * @version<1> 2019-07-01 lcw :Created.
 */
public class OrderDataVo extends ForumOrderData {


    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId")
    private String regionName;


    //时间标志， 1近一月 2近3月 3近6月 4其他
    private Integer timeFlag;

    public Integer getTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(Integer timeFlag) {
        this.timeFlag = timeFlag;
    }



}
