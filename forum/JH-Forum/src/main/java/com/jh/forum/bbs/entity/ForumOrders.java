package com.jh.forum.bbs.entity;

import com.jh.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class ForumOrders extends BaseEntity{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_orders.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_orders.order_no
     *
     * @mbggenerated
     */
    private String orderNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_orders.order_status
     *
     * @mbggenerated
     */
    private Integer orderStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_orders.trade_time
     *
     * @mbggenerated
     */
    private Date tradeTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_orders.total_price
     *
     * @mbggenerated
     */
    private BigDecimal totalPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_orders.pay_type
     *
     * @mbggenerated
     */
    private Integer payType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_orders.pay_no
     *
     * @mbggenerated
     */
    private Integer payNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_orders.pay_status
     *
     * @mbggenerated
     */
    private Integer payStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_orders.pay_time
     *
     * @mbggenerated
     */
    private Date payTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_orders.send_time
     *
     * @mbggenerated
     */
    private Date sendTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_orders.goods_num
     *
     * @mbggenerated
     */
    private Integer goodsNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_orders.order_type
     *
     * @mbggenerated
     */
    private Integer orderType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_orders.invoice_id
     *
     * @mbggenerated
     */
    private String invoiceId;


    private String subject;




    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_orders.id
     *
     * @return the value of forum_orders.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_orders.id
     *
     * @param id the value for forum_orders.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_orders.data_status
     *
     * @return the value of forum_orders.data_status
     *
     * @mbggenerated
     */


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_orders.order_no
     *
     * @return the value of forum_orders.order_no
     *
     * @mbggenerated
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_orders.order_no
     *
     * @param orderNo the value for forum_orders.order_no
     *
     * @mbggenerated
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_orders.order_status
     *
     * @return the value of forum_orders.order_status
     *
     * @mbggenerated
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_orders.order_status
     *
     * @param orderStatus the value for forum_orders.order_status
     *
     * @mbggenerated
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_orders.trade_time
     *
     * @return the value of forum_orders.trade_time
     *
     * @mbggenerated
     */
    public Date getTradeTime() {
        return tradeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_orders.trade_time
     *
     * @param tradeTime the value for forum_orders.trade_time
     *
     * @mbggenerated
     */
    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_orders.total_price
     *
     * @return the value of forum_orders.total_price
     *
     * @mbggenerated
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_orders.total_price
     *
     * @param totalPrice the value for forum_orders.total_price
     *
     * @mbggenerated
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_orders.pay_type
     *
     * @return the value of forum_orders.pay_type
     *
     * @mbggenerated
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_orders.pay_type
     *
     * @param payType the value for forum_orders.pay_type
     *
     * @mbggenerated
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_orders.pay_no
     *
     * @return the value of forum_orders.pay_no
     *
     * @mbggenerated
     */
    public Integer getPayNo() {
        return payNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_orders.pay_no
     *
     * @param payNo the value for forum_orders.pay_no
     *
     * @mbggenerated
     */
    public void setPayNo(Integer payNo) {
        this.payNo = payNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_orders.pay_status
     *
     * @return the value of forum_orders.pay_status
     *
     * @mbggenerated
     */
    public Integer getPayStatus() {
        return payStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_orders.pay_status
     *
     * @param payStatus the value for forum_orders.pay_status
     *
     * @mbggenerated
     */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_orders.pay_time
     *
     * @return the value of forum_orders.pay_time
     *
     * @mbggenerated
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_orders.pay_time
     *
     * @param payTime the value for forum_orders.pay_time
     *
     * @mbggenerated
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_orders.send_time
     *
     * @return the value of forum_orders.send_time
     *
     * @mbggenerated
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_orders.send_time
     *
     * @param sendTime the value for forum_orders.send_time
     *
     * @mbggenerated
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_orders.goods_num
     *
     * @return the value of forum_orders.goods_num
     *
     * @mbggenerated
     */
    public Integer getGoodsNum() {
        return goodsNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_orders.goods_num
     *
     * @param goodsNum the value for forum_orders.goods_num
     *
     * @mbggenerated
     */
    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_orders.order_type
     *
     * @return the value of forum_orders.order_type
     *
     * @mbggenerated
     */
    public Integer getOrderType() {
        return orderType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_orders.order_type
     *
     * @param orderType the value for forum_orders.order_type
     *
     * @mbggenerated
     */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_orders.invoice_id
     *
     * @return the value of forum_orders.invoice_id
     *
     * @mbggenerated
     */
    public String getInvoiceId() {
        return invoiceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_orders.invoice_id
     *
     * @param invoiceId the value for forum_orders.invoice_id
     *
     * @mbggenerated
     */
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId == null ? null : invoiceId.trim();
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}