package com.jh.collector.service.impl;

import com.jh.collector.entity.MassifInfo;
import com.jh.collector.mapping.MassifInfoMapper;
import com.jh.collector.service.MassifInfoService;
import com.jh.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * XZG 2019-07-25  15:45
 */
@Service
@Transactional
public class MassifInfoServiceImpl implements MassifInfoService {

    @Autowired
    private MassifInfoMapper massifInfoMapping;

    @Override
    public String addMassifInfo(MassifInfo massifInfo) {
        if (massifInfo == null){
            return StringUtils.EMPTY;
        }
        massifInfo.setBsm(generateBsm());
        massifInfoMapping.insert(massifInfo);
        return massifInfo.getBsm();
    }

    /**
     * 生产地块编号 规则
     * @return
     */
    private String generateBsm(){
        return UUIDUtil.generateUUID();
    }

}
