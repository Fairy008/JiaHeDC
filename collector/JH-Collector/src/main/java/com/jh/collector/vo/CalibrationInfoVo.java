package com.jh.collector.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * XZG 2019-07-25  14:37
 */
public class CalibrationInfoVo {

    private String bsm;
    private List<Integer> img_url;
    private Integer calibrateCrop;
    private BigDecimal calibratorArea;

    public String getBsm() {
        return bsm;
    }

    public void setBsm(String bsm) {
        this.bsm = bsm;
    }

    public List<Integer> getImg_url() {
        return img_url;
    }

    public void setImg_url(List<Integer> img_url) {
        this.img_url = img_url;
    }

    public Integer getCalibrateCrop() {
        return calibrateCrop;
    }

    public void setCalibrateCrop(Integer calibrateCrop) {
        this.calibrateCrop = calibrateCrop;
    }

    public BigDecimal getCalibratorArea() {
        return calibratorArea;
    }

    public void setCalibratorArea(BigDecimal calibratorArea) {
        this.calibratorArea = calibratorArea;
    }
}
