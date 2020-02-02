package com.jh.collector.service.impl;

import com.jh.biz.feign.SysService;
import com.jh.collector.entity.CalibrationInfo;
import com.jh.collector.entity.CalibrationSubtask;
import com.jh.collector.entity.Calibrator;
import com.jh.collector.entity.Task;
import com.jh.collector.mapping.TaskMapper;
import com.jh.collector.service.CalibrationSubtaskService;
import com.jh.collector.service.CalibratorService;
import com.jh.collector.service.TaskInfoService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskInfoServiceImpl implements TaskInfoService {

    @Autowired
    private TaskMapper isTaskMapper;

    @Autowired
    private CalibrationSubtaskService calibrationSubtaskService;

    @Autowired
    private SysService sysService;

    @Autowired
    private CalibratorService calibratorService;


    @Override
    public ResultMessage findAllTaskByPhone(String phone) {
        ResultMessage resultMessage =  sysService.getAccountIdByPhone(phone);

        if (!resultMessage.isFlag()){
            return resultMessage;
        }

        Integer accountId = (Integer) resultMessage.getData();
        List<Task> taskList = isTaskMapper.selectTaskListByAccountId(accountId);
        List<Map>  resultDataList = taskList.stream().map(task -> {
            Map<String,Object> data = new HashMap<>();
            data.put("task",task);
            List<CalibrationSubtask> subtaskList = calibrationSubtaskService.findAllSubTaskByTaskId(task.getTaskId());
            data.put("subtaskList",subtaskList);
            return data;
        }).collect(Collectors.toList());


        return ResultMessage.success(resultDataList);
    }


    @Override
    public ResultMessage deleteByTaskId(Integer taskid) {
        ResultMessage ResultMessage = new ResultMessage();
            int taskList = isTaskMapper.deleteByPrimaryKey(taskid);
            if(taskList != 0){
                return ResultMessage.success(taskList);
            }else {
                return  ResultMessage.fail();
            }

    }



    @Override
    public ResultMessage queryTaskList(Integer accountId,Long keyWord) {
        if (accountId == null){
            return ResultMessage.fail("未获取到当前用户信息");
        }
        //根据checkKey区域创建人/区域id 查找任务列表
        //List<Task> taskList = new ArrayList<Task>();
        //taskList.addAll(isTaskMapper.selectTaskListbyCreator(creatorName));
        Map<String,Object> param = new HashMap<>();
        param.put("accountId",accountId);
        param.put("keyWord",keyWord);
        List<Task> task = isTaskMapper.selectTaskListbyCreatorName(param);
        return ResultMessage.success(task);

    }

    @Override
    public Task queryTaskById(Integer taskId) {
        return isTaskMapper.selectByPrimaryKey(taskId);
    }

    @Override
    public ResultMessage addTask(Task task) {
        int taskId=isTaskMapper.insert(task);
        List<Calibrator> list = task.getCalibrators();
        calibratorService.insertCalibratorForTask(list);
        return ResultMessage.success();
    }

}
