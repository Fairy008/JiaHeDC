package com.jh.collector.service;


import com.jh.collector.entity.PolicyInfo;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 * XZG 2019-07-25  14:27
 */

public interface PolicyInfoService {

    /**
     * 根据保单号查询
     * @param policyNo
     * @return
     */
    PolicyInfo findPolicyInfoByNo(Integer policyNo);

    /**
     * 查询区域查询子任务洗的所有保单
     * @param subtaskId
    * xzg 2019/7/30 15:44
    * @return
    */
    ResultMessage findPolicyListBySubtaskId(Integer subtaskId);

    public List<PolicyInfo> queryVillageDetail(Integer villageId,String keyWord);
}
