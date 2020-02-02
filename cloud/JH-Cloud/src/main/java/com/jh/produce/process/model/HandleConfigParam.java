package com.jh.produce.process.model;

import com.jh.entity.PageEntity;
import com.jh.produce.process.entity.HandleConfig;

/**
 * description:
 *
 * @version <1> 2018-01-24 lcw： Created.
 * @version <2> 2018-02-07 djh： 新增算法配置相关参数.
 */

public class HandleConfigParam extends PageEntity{
    private HandleConfig handleConfig;

    private Integer handleMetaId;
    private String handleMetaName;
    private String handleMetaClass;
    private String handleMetaParams;
    private Integer ioType;
    private String ioTypeName;
    private String dataStatus;
    private String dataStatusName;
    private String handleMethodName;
    private String handleFileParamname;
    private Integer satId;//添加 卫星编号  2018/4/3 lxy created

    public HandleConfig getHandleConfig() {
        return handleConfig;
    }

    public void setHandleConfig(HandleConfig handleConfig) {
        this.handleConfig = handleConfig;
    }

    public Integer getHandleMetaId() {
        return handleMetaId;
    }

    public void setHandleMetaId(Integer handleMetaId) {
        this.handleMetaId = handleMetaId;
    }

    public String getHandleMetaName() {
        return handleMetaName;
    }

    public void setHandleMetaName(String handleMetaName) {
        this.handleMetaName = handleMetaName;
    }

    public String getHandleMetaClass() {
        return handleMetaClass;
    }

    public void setHandleMetaClass(String handleMetaClass) {
        this.handleMetaClass = handleMetaClass;
    }

    public String getHandleMetaParams() {
        return handleMetaParams;
    }

    public void setHandleMetaParams(String handleMetaParams) {
        this.handleMetaParams = handleMetaParams;
    }

    public Integer getIoType() {
        return ioType;
    }

    public void setIoType(Integer ioType) {
        this.ioType = ioType;
    }

    public String getIoTypeName() {
        return ioTypeName;
    }

    public void setIoTypeName(String ioTypeName) {
        this.ioTypeName = ioTypeName;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getDataStatusName() {
        return dataStatusName;
    }

    public void setDataStatusName(String dataStatusName) {
        this.dataStatusName = dataStatusName;
    }

    public String getHandleMethodName() {
        return handleMethodName;
    }

    public void setHandleMethodName(String handleMethodName) {
        this.handleMethodName = handleMethodName;
    }

    public String getHandleFileParamname() {
        return handleFileParamname;
    }

    public void setHandleFileParamname(String handleFileParamname) {
        this.handleFileParamname = handleFileParamname;
    }

    public Integer getSatId() {
        return satId;
    }

    public void setSatId(Integer satId) {
        this.satId = satId;
    }
}
