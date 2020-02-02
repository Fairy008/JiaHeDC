package com.jh.collector.service.impl;

import com.jh.collector.entity.Calibrator;
import com.jh.collector.mapping.CalibratorMapper;
import com.jh.collector.service.CalibratorService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * XZG 2019-07-30  14:45
 */
@Service
@Transactional
public class CalibratorServiceImpl implements CalibratorService {

    @Autowired
    private CalibratorMapper calibratorMapper;

    @Override
    public List<Calibrator> findTaskIdByAccount(Integer accountId) {
        Calibrator calibrator = new Calibrator();
        calibrator.setAccountId(accountId);
        return calibratorMapper.selectListByParam(calibrator);
    }

    @Override
    public int insertCalibratorForTask(List<Calibrator> list) {

        return calibratorMapper.insertCalibratorForTask(list);
    }


}
