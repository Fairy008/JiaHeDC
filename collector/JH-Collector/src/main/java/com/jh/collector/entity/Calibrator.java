package com.jh.collector.entity;


public class Calibrator {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_calibrator.c_id
     *
     * @mbggenerated
     */
    private Integer cId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_calibrator.calibrator_id
     *
     * @mbggenerated
     */
    private Integer accountId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column is_calibrator.calibrator
     *
     * @mbggenerated
     */
    private String calibrator;

    private Integer taskId;


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_calibrator.c_id
     *
     * @return the value of is_calibrator.c_id
     *
     * @mbggenerated
     */
    public Integer getcId() {
        return cId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_calibrator.c_id
     *
     * @param cId the value for is_calibrator.c_id
     *
     * @mbggenerated
     */
    public void setcId(Integer cId) {
        this.cId = cId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_calibrator.calibrator_id
     *
     * @return the value of is_calibrator.calibrator_id
     *
     * @mbggenerated
     */
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column is_calibrator.calibrator
     *
     * @return the value of is_calibrator.calibrator
     *
     * @mbggenerated
     */
    public String getCalibrator() {
        return calibrator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column is_calibrator.calibrator
     *
     * @param calibrator the value for is_calibrator.calibrator
     *
     * @mbggenerated
     */
    public void setCalibrator(String calibrator) {
        this.calibrator = calibrator == null ? null : calibrator.trim();
    }


    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
}