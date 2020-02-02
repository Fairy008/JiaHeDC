package com.jh.produce.process.entity;

import com.jh.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "数据处理配置对象")
public class HandleConfig extends BaseEntity {

    @ApiModelProperty(value = "数据处理配置主键")
    private Integer handleMetaId;

    @ApiModelProperty(value = "算法名称")
    private String handleMetaName;

    @ApiModelProperty(value = "算法类")
    private String handleMetaClass;

    @ApiModelProperty(value = "执行参数")
    private String handleMetaParams;

    @ApiModelProperty(value = "输入输出关系")
    private Integer ioType;

    @ApiModelProperty(value = "算法方法名")
    private String handleMethodName;

    @ApiModelProperty(value = "接收的文件参数名")
    private String handleFileParamname;

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
}