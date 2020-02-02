package com.jh.manage.storage.model;

import com.jh.entity.PageEntity;
import com.jh.manage.storage.Enum.StorageEnum;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;

/**
 * description:
 *
 * @version <1> 2018-01-24 lcw： Created.
 */
public class StorageParam extends PageEntity {

    private Long areaId; //行政区ID

    private String bbox; // 矩形坐标

    private Integer satId; //w卫星ID

    private Integer sensorId; //传感器

    private String satellite; //卫星名称

    private String satellites;

    private String[] satelliteArr;

    private String sensor ;  //传感器名称

    private Integer storageType; //数据类型

    private String beginTime; //开始时间

    private String endTime; //结束时间

    private Double cloudPercent1; //云盖范围起

    private Double cloudPercent2; //云盖范围止

    private Integer orderId; //订单Id

    private Double cloudPercent;

    private String timeSlot;//时间段

    private String beginCreateTime; //开始创建时间

    private String endCreateTime; //结束创建时间

    private String[] sensorArr ;  //传感器名称

    private String sceneId ; //景序列号
    private String[] sceneIdArr;


    //景序列号起
    private String sceneStart;

    //景序列号止
    private String sceneEnd;

    private String productLevel; //产品序列号
    private String[] productLevelArr;

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getBbox() {
        return bbox;
    }

    public void setBbox(String bbox) {
        this.bbox = bbox;
    }

    public Integer getSatId() {
        return satId;
    }

    public void setSatId(Integer satId) {
        this.satId = satId;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public Integer getStorageType() {
        return storageType;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getCloudPercent1() {
        return cloudPercent1;
    }

    public void setCloudPercent1(Double cloudPercent1) {
        this.cloudPercent1 = cloudPercent1;
    }

    public Double getCloudPercent2() {
        return cloudPercent2;
    }

    public void setCloudPercent2(Double cloudPercent2) {
        this.cloudPercent2 = cloudPercent2;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getSatellite() {
        return satellite;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public Double getCloudPercent() {
        return cloudPercent;
    }

    public void setCloudPercent(Double cloudPercent) {
        this.cloudPercent = cloudPercent;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(String beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public String[] getSensorArr() {
        return sensorArr;
    }

    public void setSensorArr(String[] sensorArr) {
        this.sensorArr = sensorArr;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getProductLevel() {
        return productLevel;
    }

    public void setProductLevel(String productLevel) {
        this.productLevel = productLevel;
    }

    public String[] getSceneIdArr() {
        return sceneIdArr;
    }

    public void setSceneIdArr(String[] sceneIdArr) {
        this.sceneIdArr = sceneIdArr;
    }

    public String[] getProductLevelArr() {
        return productLevelArr;
    }

    public void setProductLevelArr(String[] productLevelArr) {
        this.productLevelArr = productLevelArr;
    }

    public String getSatellites() {
        return satellites;
    }

    public void setSatellites(String satellites) {
        this.satellites = satellites;
    }

    public String[] getSatelliteArr() {
        return satelliteArr;
    }

    public void setSatelliteArr(String[] satelliteArr) {
        this.satelliteArr = satelliteArr;
    }

    public String getSceneStart() {
        return sceneStart;
    }

    public void setSceneStart(String sceneStart) {
        this.sceneStart = sceneStart;
    }

    public String getSceneEnd() {
        return sceneEnd;
    }

    public void setSceneEnd(String sceneEnd) {
        this.sceneEnd = sceneEnd;
    }

    /**
     * 验证查询条件是否为空
     * @param storageParam
     * @return
     */
    public static ResultMessage checkStorageParam(StorageParam storageParam){
        ResultMessage result = ResultMessage.success();

        if(storageParam.getAreaId() == null && StringUtils.isBlank(storageParam.getBbox())){
            result = ResultMessage.fail(StorageEnum.STORAGE_AREA_BBOX_NULL.getValue(), StorageEnum.STORAGE_AREA_BBOX_NULL.getMessage());
            return result;
        }

        if(storageParam.getSatellite() == null){
            result = ResultMessage.fail(StorageEnum.STORAGE_SAT_NULL.getValue(), StorageEnum.STORAGE_SAT_NULL.getMessage());
            return result;
        }

//        if(storageParam.getStorageType() == null){
//            result = ResultMessage.fail(StorageEnum.STORAGE_STORAGETYPE_NULL.getValue(), StorageEnum.STORAGE_STORAGETYPE_NULL.getMessage());
//            return result;
//        }

        if(StringUtils.isBlank(storageParam.getBeginTime()) || StringUtils.isBlank(storageParam.getEndTime())){
            result = ResultMessage.fail(StorageEnum.STORAGE_DATATIME_NULL.getValue(), StorageEnum.STORAGE_DATATIME_NULL.getMessage());
            return result;
        }

        return result;
    }
}
