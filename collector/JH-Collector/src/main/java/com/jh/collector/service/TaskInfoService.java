package com.jh.collector.service;

import com.jh.collector.entity.Task;
import com.jh.vo.ResultMessage;

public interface TaskInfoService {

    /**
     * 移动端获取当前登录用户的所有的主任务 和 关联的子任务 数据
     * XZG 2019-07-29  13:09
     * @return
     */
    public ResultMessage findAllTaskByPhone(String phone);

    public ResultMessage deleteByTaskId(Integer taskId);

    /**
     * @Author：sxj
     * @Date：2019-07-31
     * 根据区域创建人或区域id获取 任务信息列表
     * @return
     */
    public ResultMessage queryTaskList(Integer accountId,Long keyWord);


    public Task queryTaskById(Integer taskId);


    public ResultMessage addTask(Task task);

}
