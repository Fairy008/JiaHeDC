package com.jh.forum.bbs.entity;

import java.util.Date;

public class ForumFreeDownload {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_free_download.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_free_download.dataId
     *
     * @mbggenerated
     */
    private Integer dataId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_free_download.userId
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum_free_download.download_time
     *
     * @mbggenerated
     */
    private Date downloadTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_free_download.id
     *
     * @return the value of forum_free_download.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_free_download.id
     *
     * @param id the value for forum_free_download.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_free_download.dataId
     *
     * @return the value of forum_free_download.dataId
     *
     * @mbggenerated
     */
    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum_free_download.download_time
     *
     * @return the value of forum_free_download.download_time
     *
     * @mbggenerated
     */
    public Date getDownloadTime() {
        return downloadTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum_free_download.download_time
     *
     * @param downloadTime the value for forum_free_download.download_time
     *
     * @mbggenerated
     */
    public void setDownloadTime(Date downloadTime) {
        this.downloadTime = downloadTime;
    }
}