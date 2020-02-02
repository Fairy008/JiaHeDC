package com.jh.forum.bbs.entity;

import com.jh.entity.BaseEntity;
import java.time.LocalDate;

/**
 * @Description 数据订单
 * @Version <1> 2019-08-14 sxj:Create.
 **/
public class ForumDataGoods extends BaseEntity {


    private Integer id;
    private Integer dataId;
    private float orderMoney;
    private String phone;
    private Integer orderStatus;
    private Integer invoiceId;
    private Integer orderOprator;
    private LocalDate orderCheckdate;
    private Integer payType;
    private LocalDate payTime;
    private LocalDate sendTime;
    private Integer orderNum;
    private Integer evaluatedId;
    private Integer orderType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public float getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(float orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getOrderOprator() {
        return orderOprator;
    }

    public void setOrderOprator(Integer orderOprator) {
        this.orderOprator = orderOprator;
    }

    public LocalDate getOrderCheckdate() {
        return orderCheckdate;
    }

    public void setOrderCheckdate(LocalDate orderCheckdate) {
        this.orderCheckdate = orderCheckdate;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public LocalDate getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDate payTime) {
        this.payTime = payTime;
    }

    public LocalDate getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDate sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getEvaluatedId() {
        return evaluatedId;
    }

    public void setEvaluatedId(Integer evaluatedId) {
        this.evaluatedId = evaluatedId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
}
