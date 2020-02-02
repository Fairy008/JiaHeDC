package com.jh.forum.bbs.entity;

import com.jh.entity.BaseEntity;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

import java.util.Date;
import java.util.List;

/**
 * 专家实体类
 */
public class ForumExpert extends BaseEntity {

    //专家ID
    private Integer expertId;

    //用户ID
    private Integer accountId;

    //专家姓名
    private String expertName;

    //行业
    private String industry;

    //性别（301男，302女）
    private Integer sex;

    //单位
    private String company;

    //手机号
    private String phone;

    //简介
    private String introduction;

    //照片地址
    private String photoUrl;

    //认证状态
    private Integer authStatus;

    //评级
    private Integer grade;

    //认证状态
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "authStatus")
    private String authStatusName;

    //性别名称
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "sex")
    private String sexName;

    //专家级别名称
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "grade")
    private String gradeName;

    private List<Integer> expertIds;

    //创建日期开始
    private String startTime;

    //创建时间结束
    private String endTime;

    public Integer getExpertId() {
        return expertId;
    }

    public void setExpertId(Integer expertId) {
        this.expertId = expertId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getAuthStatusName() {
        return authStatusName;
    }

    public void setAuthStatusName(String authStatusName) {
        this.authStatusName = authStatusName;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public List<Integer> getExpertIds() {
        return expertIds;
    }

    public void setExpertIds(List<Integer> expertIds) {
        this.expertIds = expertIds;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }
}