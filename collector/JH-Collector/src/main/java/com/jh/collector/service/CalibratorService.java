package com.jh.collector.service;

import com.jh.collector.entity.Calibrator;

import java.util.List;

/**
 * 主任务 与 定标人的关系
 * XZG 2019-07-30  14:40
 */
public interface CalibratorService {

    /**
     * 获取用户的所有主任务id
     *  @param accountId
    * xzg 2019/7/30 14:44
    * @return
    */
    List<Calibrator> findTaskIdByAccount(Integer accountId);


    int insertCalibratorForTask(List<Calibrator> list);

}
