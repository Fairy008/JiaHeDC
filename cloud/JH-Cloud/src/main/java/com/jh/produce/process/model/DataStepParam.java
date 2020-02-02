package com.jh.produce.process.model;

/**
 * description: 数据步骤参数
 * @version <1> 2018-03-22 cxj： Created.
 */
public class DataStepParam {
    private String areaCode;
    private Long regionId;
    private Integer handleId;
    private Integer satId;
    private String satName;

    private DataStepInfoParam[] operatorList;
    private RelateTaskStorageParam[] pendingDataList;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Integer getHandleId() {
        return handleId;
    }

    public void setHandleId(Integer handleId) {
        this.handleId = handleId;
    }

    public Integer getSatId() {
        return satId;
    }

    public void setSatId(Integer satId) {
        this.satId = satId;
    }

    public String getSatName() {
        return satName;
    }

    public void setSatName(String satName) {
        this.satName = satName;
    }

    public DataStepInfoParam[] getOperatorList() {
        return operatorList;
    }

    public void setOperatorList(DataStepInfoParam[] operatorList) {
        this.operatorList = operatorList;
    }

    public RelateTaskStorageParam[] getPendingDataList() {
        return pendingDataList;
    }

    public void setPendingDataList(RelateTaskStorageParam[] pendingDataList) {
        this.pendingDataList = pendingDataList;
    }
}
