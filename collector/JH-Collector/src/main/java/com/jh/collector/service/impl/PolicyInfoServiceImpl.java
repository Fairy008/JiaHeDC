package com.jh.collector.service.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.jh.collector.entity.CalibrationSubtask;
import com.jh.collector.entity.PolicyInfo;
import com.jh.collector.mapping.PolicyInfoMapper;
import com.jh.collector.service.PolicyInfoService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * XZG 2019-07-25  14:27
 */
@Service
@Transactional
public class PolicyInfoServiceImpl implements PolicyInfoService {

    @Autowired
    private PolicyInfoMapper policyInfoMapper;

    @Autowired
    private CalibrationSubtaskServiceImpl subtaskService;

    @Override
    public PolicyInfo findPolicyInfoByNo(Integer policyNo) {
        return policyInfoMapper.selectByPrimaryKey(policyNo);
    }

    @Override
    public ResultMessage findPolicyListBySubtaskId(Integer subtaskId) {
        // 1、 子任务是否存在
        CalibrationSubtask subtask = subtaskService.findSubtaskByPk(subtaskId);
        if (subtask == null){
            return ResultMessage.fail("子任务不存在");
        }

        //2、获得子任务区域下的所有保单
        PolicyInfo policyInfo = new PolicyInfo();
        policyInfo.setRegionId(subtask.getVillage());
        List<PolicyInfo> policyInfoList = policyInfoMapper.selectPolicyInfoList(policyInfo);
        return ResultMessage.success(policyInfoList);
    }


    @Override
    public List<PolicyInfo> queryVillageDetail(Integer villageId,String keyWord) {
        Map<String,Object> param = new HashMap<>();
        param.put("villageId",villageId);
        param.put("keyWord", keyWord);
        return policyInfoMapper.queryVillageDetail(param);
    }

}
