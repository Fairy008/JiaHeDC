package com.jh.collector.service;

import com.jh.collector.entity.CalibrationSubtask;
import com.jh.collector.entity.Task;
import com.jh.collector.enums.SubtaskDataPackageEnum;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 * 定验标子任务接口
 * XZG 2019-07-26  14:53
 */
public interface CalibrationSubtaskService {

    /**
     * 根据行政村查询
     * @param villageId
     * @return
     */
    public CalibrationSubtask findByVillage(Integer villageId);

    /**
     * 查询主任务下的所有子任务
     * @param taskId 任务id
     * @return
     */
    public List<CalibrationSubtask> findAllSubTaskByTaskId(Integer taskId);

    /**
     * 更新子任务的 上传数据包状态
     * @param packageEnum
     * @param taskId
     * @return
     */
    public int modifySubtaskUploadStatus(SubtaskDataPackageEnum packageEnum, Integer taskId);

    /**
     * 绑定自认的定标人
    * XZG 2019-07-29  13:09
    * @return
    */
    public ResultMessage relateSubtaskCalibrator(Integer subtaskId,String phone);

    /**
     * 根据子任务主键查询子任务
     * @param subtaskId
    * xzg 2019/7/30 16:24
    * @return
    */
    public CalibrationSubtask findSubtaskByPk(Integer subtaskId);


    public List<CalibrationSubtask> queryTaskVillage(CalibrationSubtask calibrationSubtask);




}
