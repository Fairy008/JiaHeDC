package com.jh.forum.bbs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 论坛文章实体类
 */
public class ForumArticle extends BaseEntity {

    //帖子ID
    private Integer articleId;

    //报告、约调研、遥感问答、其他
    private Integer articleType;

    //帖子标题
    private String articleTitle;

    //摘要
    private String articleSummary;

    //html格式
    private String articleContent;

    //审核人
    private Integer auditor;

    //审核时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auditTime;

    //帖子状态：草稿、待审核、已审核、已发布、撤回
    private Integer articleStatus;

    //发布人
    private Integer publisher;

    //发布时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date publishTime;

    //最热报告标记（默认为0，最热为1）
    private String articleHot;

    //最新调研标记（默认为0，最新为1）
    private String articleNew;

    //标签，以逗号分隔
    private String articleLabel;

    //报告pdf路径
    private String reportUrl;


    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getArticleType() {
        return articleType;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleSummary() {
        return articleSummary;
    }

    public void setArticleSummary(String articleSummary) {
        this.articleSummary = articleSummary;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public Integer getAuditor() {
        return auditor;
    }

    public void setAuditor(Integer auditor) {
        this.auditor = auditor;
    }

    public LocalDateTime getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(LocalDateTime auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
    }

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getArticleHot() {
        return articleHot;
    }

    public void setArticleHot(String articleHot) {
        this.articleHot = articleHot;
    }

    public String getArticleNew() {
        return articleNew;
    }

    public void setArticleNew(String articleNew) {
        this.articleNew = articleNew;
    }

    public String getArticleLabel() {
        return articleLabel;
    }

    public void setArticleLabel(String articleLabel) {
        this.articleLabel = articleLabel;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

}