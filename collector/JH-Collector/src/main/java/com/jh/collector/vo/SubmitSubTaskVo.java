package com.jh.collector.vo;

import java.util.List;

/**
 * XZG 2019-07-25  14:00
 */
// 子任务
public class SubmitSubTaskVo {
    private Integer sub_task_id;
//    private Long village_id;
    private List<PolicyVo> policy_list;

    public Integer getSub_task_id() {
        return sub_task_id;
    }

    public void setSub_task_id(Integer sub_task_id) {
        this.sub_task_id = sub_task_id;
    }

//    public Long getVillage_id() {
//        return village_id;
//    }
//
//    public void setVillage_id(Long village_id) {
//        this.village_id = village_id;
//    }

    public List<PolicyVo> getPolicy_list() {
        return policy_list;
    }

    public void setPolicy_list(List<PolicyVo> policy_list) {
        this.policy_list = policy_list;
    }
}

