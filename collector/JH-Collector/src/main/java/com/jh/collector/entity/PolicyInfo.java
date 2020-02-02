package com.jh.collector.entity;



import java.math.BigDecimal;
import java.util.Date;

public class PolicyInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.policy_no
     *
     * @mbggenerated
     */
    private Integer policyNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.data_status
     *
     * @mbggenerated
     */
    private String dataStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.del_flag
     *
     * @mbggenerated
     */
    private String delFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.creator_name
     *
     * @mbggenerated
     */
    private String creatorName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.creator
     *
     * @mbggenerated
     */
    private Integer creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.modify_time
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.modifier_name
     *
     * @mbggenerated
     */
    private String modifierName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.modifier
     *
     * @mbggenerated
     */
    private Integer modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.policy_start_date
     *
     * @mbggenerated
     */
    private Date policyStartDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.policy_end_date
     *
     * @mbggenerated
     */
    private Date policyEndDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.region_id
     *
     * @mbggenerated
     */
    private Integer regionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.farmers_name
     *
     * @mbggenerated
     */
    private String farmersName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.id_card
     *
     * @mbggenerated
     */
    private String idCard;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.telphone
     *
     * @mbggenerated
     */
    private String telphone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.crop_type
     *
     * @mbggenerated
     */
    private Integer cropType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_policy_info.insured_area
     *
     * @mbggenerated
     */
    private BigDecimal insuredArea;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.policy_no
     *
     * @return the value of is_policy_info.policy_no
     *
     * @mbggenerated
     */
    public Integer getPolicyNo() {
        return policyNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.policy_no
     *
     * @param policyNo the value for is_policy_info.policy_no
     *
     * @mbggenerated
     */
    public void setPolicyNo(Integer policyNo) {
        this.policyNo = policyNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.data_status
     *
     * @return the value of is_policy_info.data_status
     *
     * @mbggenerated
     */
    public String getDataStatus() {
        return dataStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.data_status
     *
     * @param dataStatus the value for is_policy_info.data_status
     *
     * @mbggenerated
     */
    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus == null ? null : dataStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.del_flag
     *
     * @return the value of is_policy_info.del_flag
     *
     * @mbggenerated
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.del_flag
     *
     * @param delFlag the value for is_policy_info.del_flag
     *
     * @mbggenerated
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.create_time
     *
     * @return the value of is_policy_info.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.create_time
     *
     * @param createTime the value for is_policy_info.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.creator_name
     *
     * @return the value of is_policy_info.creator_name
     *
     * @mbggenerated
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.creator_name
     *
     * @param creatorName the value for is_policy_info.creator_name
     *
     * @mbggenerated
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.creator
     *
     * @return the value of is_policy_info.creator
     *
     * @mbggenerated
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.creator
     *
     * @param creator the value for is_policy_info.creator
     *
     * @mbggenerated
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.modify_time
     *
     * @return the value of is_policy_info.modify_time
     *
     * @mbggenerated
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.modify_time
     *
     * @param modifyTime the value for is_policy_info.modify_time
     *
     * @mbggenerated
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.modifier_name
     *
     * @return the value of is_policy_info.modifier_name
     *
     * @mbggenerated
     */
    public String getModifierName() {
        return modifierName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.modifier_name
     *
     * @param modifierName the value for is_policy_info.modifier_name
     *
     * @mbggenerated
     */
    public void setModifierName(String modifierName) {
        this.modifierName = modifierName == null ? null : modifierName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.modifier
     *
     * @return the value of is_policy_info.modifier
     *
     * @mbggenerated
     */
    public Integer getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.modifier
     *
     * @param modifier the value for is_policy_info.modifier
     *
     * @mbggenerated
     */
    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.remark
     *
     * @return the value of is_policy_info.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.remark
     *
     * @param remark the value for is_policy_info.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.policy_start_date
     *
     * @return the value of is_policy_info.policy_start_date
     *
     * @mbggenerated
     */
    public Date getPolicyStartDate() {
        return policyStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.policy_start_date
     *
     * @param policyStartDate the value for is_policy_info.policy_start_date
     *
     * @mbggenerated
     */
    public void setPolicyStartDate(Date policyStartDate) {
        this.policyStartDate = policyStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.policy_end_date
     *
     * @return the value of is_policy_info.policy_end_date
     *
     * @mbggenerated
     */
    public Date getPolicyEndDate() {
        return policyEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.policy_end_date
     *
     * @param policyEndDate the value for is_policy_info.policy_end_date
     *
     * @mbggenerated
     */
    public void setPolicyEndDate(Date policyEndDate) {
        this.policyEndDate = policyEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.region_id
     *
     * @return the value of is_policy_info.region_id
     *
     * @mbggenerated
     */
    public Integer getRegionId() {
        return regionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.region_id
     *
     * @param regionId the value for is_policy_info.region_id
     *
     * @mbggenerated
     */
    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.farmers_name
     *
     * @return the value of is_policy_info.farmers_name
     *
     * @mbggenerated
     */
    public String getFarmersName() {
        return farmersName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.farmers_name
     *
     * @param farmersName the value for is_policy_info.farmers_name
     *
     * @mbggenerated
     */
    public void setFarmersName(String farmersName) {
        this.farmersName = farmersName == null ? null : farmersName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.id_card
     *
     * @return the value of is_policy_info.id_card
     *
     * @mbggenerated
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.id_card
     *
     * @param idCard the value for is_policy_info.id_card
     *
     * @mbggenerated
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.telphone
     *
     * @return the value of is_policy_info.telphone
     *
     * @mbggenerated
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.telphone
     *
     * @param telphone the value for is_policy_info.telphone
     *
     * @mbggenerated
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.crop_type
     *
     * @return the value of is_policy_info.crop_type
     *
     * @mbggenerated
     */
    public Integer getCropType() {
        return cropType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.crop_type
     *
     * @param cropType the value for is_policy_info.crop_type
     *
     * @mbggenerated
     */
    public void setCropType(Integer cropType) {
        this.cropType = cropType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_policy_info.insured_area
     *
     * @return the value of is_policy_info.insured_area
     *
     * @mbggenerated
     */
    public BigDecimal getInsuredArea() {
        return insuredArea;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_policy_info.insured_area
     *
     * @param insuredArea the value for is_policy_info.insured_area
     *
     * @mbggenerated
     */
    public void setInsuredArea(BigDecimal insuredArea) {
        this.insuredArea = insuredArea;
    }
}