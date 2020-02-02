package com.jh.forum.bbs.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class ForumOrderDetail {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_order_detail.order_id
     *
     * @mbggenerated
     */
    private Integer orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_order_detail.data_status
     *
     * @mbggenerated
     */
    private String dataStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_order_detail.del_flag
     *
     * @mbggenerated
     */
    private String delFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_order_detail.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_order_detail.creator_name
     *
     * @mbggenerated
     */
    private String creatorName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_order_detail.creator
     *
     * @mbggenerated
     */
    private Integer creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_order_detail.modify_time
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_order_detail.modifier_name
     *
     * @mbggenerated
     */
    private String modifierName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_order_detail.modifier
     *
     * @mbggenerated
     */
    private Integer modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_order_detail.data_id
     *
     * @mbggenerated
     */
    private Integer dataId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_order_detail.classify_name
     *
     * @mbggenerated
     */
    private String classifyName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_order_detail.region_name
     *
     * @mbggenerated
     */
    private String regionName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_order_detail.accuracy_name
     *
     * @mbggenerated
     */
    private String accuracyName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_order_detail.unit_price
     *
     * @mbggenerated
     */
    private BigDecimal unitPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_order_detail.num
     *
     * @mbggenerated
     */
    private Integer num;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_order_detail.price 
     *
     * @mbggenerated
     */
    private BigDecimal price;

    private String orderNo;


    private String cropName;


    private LocalDate dataTime;


    private String dataName;

    private String dataPath;

    private String dataPackagePath;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_order_detail.order_id
     *
     * @return the value of forum_order_detail.order_id
     *
     * @mbggenerated
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_order_detail.order_id
     *
     * @param orderId the value for forum_order_detail.order_id
     *
     * @mbggenerated
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_order_detail.data_status
     *
     * @return the value of forum_order_detail.data_status
     *
     * @mbggenerated
     */
    public String getDataStatus() {
        return dataStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_order_detail.data_status
     *
     * @param dataStatus the value for forum_order_detail.data_status
     *
     * @mbggenerated
     */
    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus == null ? null : dataStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_order_detail.del_flag
     *
     * @return the value of forum_order_detail.del_flag
     *
     * @mbggenerated
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_order_detail.del_flag
     *
     * @param delFlag the value for forum_order_detail.del_flag
     *
     * @mbggenerated
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_order_detail.create_time
     *
     * @return the value of forum_order_detail.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_order_detail.create_time
     *
     * @param createTime the value for forum_order_detail.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_order_detail.creator_name
     *
     * @return the value of forum_order_detail.creator_name
     *
     * @mbggenerated
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_order_detail.creator_name
     *
     * @param creatorName the value for forum_order_detail.creator_name
     *
     * @mbggenerated
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_order_detail.creator
     *
     * @return the value of forum_order_detail.creator
     *
     * @mbggenerated
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_order_detail.creator
     *
     * @param creator the value for forum_order_detail.creator
     *
     * @mbggenerated
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_order_detail.modify_time
     *
     * @return the value of forum_order_detail.modify_time
     *
     * @mbggenerated
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_order_detail.modify_time
     *
     * @param modifyTime the value for forum_order_detail.modify_time
     *
     * @mbggenerated
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_order_detail.modifier_name
     *
     * @return the value of forum_order_detail.modifier_name
     *
     * @mbggenerated
     */
    public String getModifierName() {
        return modifierName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_order_detail.modifier_name
     *
     * @param modifierName the value for forum_order_detail.modifier_name
     *
     * @mbggenerated
     */
    public void setModifierName(String modifierName) {
        this.modifierName = modifierName == null ? null : modifierName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_order_detail.modifier
     *
     * @return the value of forum_order_detail.modifier
     *
     * @mbggenerated
     */
    public Integer getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_order_detail.modifier
     *
     * @param modifier the value for forum_order_detail.modifier
     *
     * @mbggenerated
     */
    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_order_detail.data_id
     *
     * @return the value of forum_order_detail.data_id
     *
     * @mbggenerated
     */
    public Integer getDataId() {
        return dataId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_order_detail.data_id
     *
     * @param dataId the value for forum_order_detail.data_id
     *
     * @mbggenerated
     */
    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_order_detail.classify_name
     *
     * @return the value of forum_order_detail.classify_name
     *
     * @mbggenerated
     */
    public String getClassifyName() {
        return classifyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_order_detail.classify_name
     *
     * @param classifyName the value for forum_order_detail.classify_name
     *
     * @mbggenerated
     */
    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName == null ? null : classifyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_order_detail.region_name
     *
     * @return the value of forum_order_detail.region_name
     *
     * @mbggenerated
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_order_detail.region_name
     *
     * @param regionName the value for forum_order_detail.region_name
     *
     * @mbggenerated
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_order_detail.accuracy_name
     *
     * @return the value of forum_order_detail.accuracy_name
     *
     * @mbggenerated
     */
    public String getAccuracyName() {
        return accuracyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_order_detail.accuracy_name
     *
     * @param accuracyName the value for forum_order_detail.accuracy_name
     *
     * @mbggenerated
     */
    public void setAccuracyName(String accuracyName) {
        this.accuracyName = accuracyName == null ? null : accuracyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_order_detail.unit_price
     *
     * @return the value of forum_order_detail.unit_price
     *
     * @mbggenerated
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_order_detail.unit_price
     *
     * @param unitPrice the value for forum_order_detail.unit_price
     *
     * @mbggenerated
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_order_detail.num
     *
     * @return the value of forum_order_detail.num
     *
     * @mbggenerated
     */
    public Integer getNum() {
        return num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_order_detail.num
     *
     * @param num the value for forum_order_detail.num
     *
     * @mbggenerated
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_order_detail.price 
     *
     * @return the value of forum_order_detail.price 
     *
     * @mbggenerated
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_order_detail.price 
     *
     * @param price the value for forum_order_detail.price 
     *
     * @mbggenerated
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public LocalDate getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDate dataTime) {
        this.dataTime = dataTime;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getDataPackagePath() {
        return dataPackagePath;
    }

    public void setDataPackagePath(String dataPackagePath) {
        this.dataPackagePath = dataPackagePath;
    }
}