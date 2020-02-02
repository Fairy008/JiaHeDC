package com.jh.briefingNew.service.impl;

import com.jh.briefingNew.entity.BriefingDiseaseAll;
import com.jh.briefingNew.mapping.IBriefingDiseaseAllMapper;
import com.jh.briefingNew.service.IBriefingDiseaseAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:病虫害
 *
 * @version <1> 2018-07-24 wl: Created.
 */
@Service
public class BriefingDiseaseAllServiceImpl implements IBriefingDiseaseAllService {

    @Autowired
    private IBriefingDiseaseAllMapper briefingDiseaseAllMapper;

    @Override
    public List<BriefingDiseaseAll> queryCropsDiseaseByGrowthId(Integer growthId, Integer diseaseType) {
        Map<String,Object> param = new HashMap<>();
        param.put("growthId",growthId);
        param.put("diseaseType",diseaseType);
        return briefingDiseaseAllMapper.queryCropsDiseaseByGrowthId(param);
    }
}
