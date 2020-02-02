package com.jh.cltApp.vo;

import com.jh.cltApp.entity.CltTaskInfo;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

public class CltTaskInfoVO extends CltTaskInfo {

    private Integer accountId;//参与人id

    private String isShoulder;//是否是负责人

    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId")
    private String regionNameCn;//区域中文名

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId")
    private String cropNameCn;//作物中文名

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "taskType")
    private String taskTypeNameCn;//数据集中文名

    private Integer itemCount;//子集数据数量，用于分页下载按钮控制

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getIsShoulder() {
        return isShoulder;
    }

    public void setIsShoulder(String isShoulder) {
        this.isShoulder = isShoulder;
    }

    public String getRegionNameCn() {
        return regionNameCn;
    }

    public void setRegionNameCn(String regionNameCn) {
        this.regionNameCn = regionNameCn;
    }

    public String getCropNameCn() {
        return cropNameCn;
    }

    public void setCropNameCn(String cropNameCn) {
        this.cropNameCn = cropNameCn;
    }

    public String getTaskTypeNameCn() {
        return taskTypeNameCn;
    }

    public void setTaskTypeNameCn(String taskTypeNameCn) {
        this.taskTypeNameCn = taskTypeNameCn;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }
}
