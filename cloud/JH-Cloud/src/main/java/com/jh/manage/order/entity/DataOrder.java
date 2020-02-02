package com.jh.manage.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import com.jh.manage.order.model.DataOrderPath;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "数据订单对象")
public class DataOrder extends BaseEntity {

    @ApiModelProperty(value = "订单主键")
    private Integer orderId;

    //不需要订单申请人字段， 订单创建人即为申请人 2018-02-27 lcw
//    @ApiModelProperty(value = "订单申请人")
//    private Integer orderApplyPerson;

    @ApiModelProperty(value = "订单编号")
    private String orderCode;

    @ApiModelProperty(value = "订单分类")
    private Integer orderType;

    @ApiModelProperty(value = "订单类型")
    private Integer orderAttribute;

    @ApiModelProperty(value = "订单描述")
    private String orderDescription;

    @ApiModelProperty(value = "订单审核人")
    private Integer orderAuditor;

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auditTime;

    @ApiModelProperty(value = "审核状态")
    private Integer auditStatus;

    @ApiModelProperty(value = "分发人")
    private Integer handlerPerson;

    @ApiModelProperty(value = "分发时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime handleTime;

    @ApiModelProperty(value = "分发状态")
    private Integer handleStatus;

    @ApiModelProperty(value = "分发截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dataAccessTime;

    @ApiModelProperty(value = "审核人")
    private String orderAuditorName;

    @ApiModelProperty(value = "分发人")
    private String handlerPersonName;

    @ApiModelProperty(value = "订单明细对象集合")
    private List<DataOrderDetail> orderDetailList;

    @ApiModelProperty(value="申请人用户类型")
    private Integer personType ;

    @ApiModelProperty(value = "申请人手机号")
    private String mobile ;

    @ApiModelProperty(value="订单审核意见")
    private String auditSuggestion ;

    @ApiModelProperty(value="订单分发意见")
    private String handleSuggestion ;

    /**
     * @version<2> 2018-03-20 lcw :Created.
     */
    @ApiModelProperty(value = "订单分发数据集合")
    private List<DataOrderPath> orderPathList;

    @ApiModelProperty(value = "文件路径")
    private String dataPath;

    @ApiModelProperty(value = "数据申请word路径")
    private String wordPath;

    @ApiModelProperty(value = "数据申请word名称")
    private String wordName;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public Integer getOrderAuditor() {
        return orderAuditor;
    }

    public void setOrderAuditor(Integer orderAuditor) {
        this.orderAuditor = orderAuditor;
    }

    public LocalDateTime getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(LocalDateTime auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getHandlerPerson() {
        return handlerPerson;
    }

    public void setHandlerPerson(Integer handlerPerson) {
        this.handlerPerson = handlerPerson;
    }

    public LocalDateTime getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(LocalDateTime handleTime) {
        this.handleTime = handleTime;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public LocalDateTime getDataAccessTime() {
        return dataAccessTime;
    }

    public void setDataAccessTime(LocalDateTime dataAccessTime) {
        this.dataAccessTime = dataAccessTime;
    }

    public List<DataOrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<DataOrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public String getOrderAuditorName() {
        return orderAuditorName;
    }

    public void setOrderAuditorName(String orderAuditorName) {
        this.orderAuditorName = orderAuditorName;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getHandlerPersonName() {
        return handlerPersonName;
    }

    public void setHandlerPersonName(String handlerPersonName) {
        this.handlerPersonName = handlerPersonName;
    }

    public List<DataOrderPath> getOrderPathList() {
        return orderPathList;
    }

    public void setOrderPathList(List<DataOrderPath> orderPathList) {
        this.orderPathList = orderPathList;
    }

    public Integer getPersonType() {
        return personType;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
    }

    public Integer getOrderAttribute() {
        return orderAttribute;
    }

    public void setOrderAttribute(Integer orderAttribute) {
        this.orderAttribute = orderAttribute;
    }

    public String getAuditSuggestion() {
        return auditSuggestion;
    }

    public void setAuditSuggestion(String auditSuggestion) {
        this.auditSuggestion = auditSuggestion;
    }

    public String getHandleSuggestion() {
        return handleSuggestion;
    }

    public void setHandleSuggestion(String handleSuggestion) {
        this.handleSuggestion = handleSuggestion;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWordPath() {
        return wordPath;
    }

    public void setWordPath(String wordPath) {
        this.wordPath = wordPath;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }
}