package com.jh.collector.vo;

import java.util.List;

/**
 * XZG 2019-07-25  14:37
 */
public class PolicyVo {
    private Integer policy_no;
    private List<CalibrationInfoVo> area_list;

    public Integer getPolicy_no() {
        return policy_no;
    }

    public void setPolicy_no(Integer policy_no) {
        this.policy_no = policy_no;
    }

    public List<CalibrationInfoVo> getArea_list() {
        return area_list;
    }

    public void setArea_list(List<CalibrationInfoVo> area_list) {
        this.area_list = area_list;
    }
}
