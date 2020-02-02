package com.jh.collector.service.impl;

import com.jh.biz.feign.SysService;
import com.jh.collector.entity.CalibrationSubtask;
import com.jh.collector.entity.Task;
import com.jh.collector.enums.SubtaskDataPackageEnum;
import com.jh.collector.mapping.CalibrationSubtaskMapper;
import com.jh.collector.service.CalibrationSubtaskService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 子任务
 *  XZG 2019-07-26  14:56
 */
@Service
@Transactional
public class CalibrationSubtaskServiceImpl implements CalibrationSubtaskService {


    @Autowired
    private CalibrationSubtaskMapper calibrationSubtaskMapper;

    @Autowired
    private SysService sysService;

    @Override
    public CalibrationSubtask findByVillage(Integer villageId) {
        CalibrationSubtask subtask = new CalibrationSubtask();
        subtask.setVillage(villageId);
        return calibrationSubtaskMapper.selectSubtaskOne(subtask);
    }

    @Override
    public List<CalibrationSubtask> findAllSubTaskByTaskId(Integer taskId) {
        Task task = new Task();
        task.setTaskId(taskId);
        return calibrationSubtaskMapper.selectSubtaskList(task);
    }

    @Override
    public int modifySubtaskUploadStatus(SubtaskDataPackageEnum packageEnum, Integer taskId) {
        CalibrationSubtask subtask = new CalibrationSubtask();
        subtask.setSubtaskId(taskId);
        subtask.setDataPackage(packageEnum.getValue());
        return calibrationSubtaskMapper.updateSubtaskByParam(subtask);
    }

    @Override
    public ResultMessage relateSubtaskCalibrator(Integer subtaskId, String phone) {
        ResultMessage accountIdResult = sysService.getAccountIdByPhone(phone);
        if (!accountIdResult.isFlag()){
            return accountIdResult;
        }
        Integer accountId = (Integer) accountIdResult.getData();

        CalibrationSubtask subtask = new CalibrationSubtask();
        subtask.setSubtaskId(subtaskId);
        subtask.setCalibratorId(accountId);
        int update = calibrationSubtaskMapper.updateSubtaskByParam(subtask);
        return ResultMessage.success();
    }

    @Override
    public CalibrationSubtask findSubtaskByPk(Integer subtaskId) {
        CalibrationSubtask subtaskParam = new CalibrationSubtask();
        subtaskParam.setSubtaskId(subtaskId);
        return calibrationSubtaskMapper.selectSubtaskOne(subtaskParam);
    }

    @Override
    public List<CalibrationSubtask> queryTaskVillage(CalibrationSubtask calibrationSubtask) {
        return calibrationSubtaskMapper.queryTaskVillage(calibrationSubtask);
    }


}
