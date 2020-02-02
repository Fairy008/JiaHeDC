package com.jh.collection.service;

import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.TaskItemFeild;
import com.jh.collection.entity.vo.CollectionFeildValue;
import com.jh.vo.ResultMessage;

/**
 * 采集子任务与字段接口
 * @version <1> 2018-12-04 xy: Created.
 */
public interface ITaskItemFeildService {

    /**
     * 查询采集子任务字段
     * @param taskItemFeild
     * @return
     * @version <1> 2018-12-04 xy： Created.
     */
    PageInfo<TaskItemFeild> findByPage(TaskItemFeild taskItemFeild);

    /**
     * 新增采集子任务字段
     * @return
     * @version <1> 2018-12-04 xy： Created.
     */
    public ResultMessage addTaskItemFeild(TaskItemFeild taskItemFeild);

    /**
     * 更新采集子任务字段
     * @return
     * @version <1> 2018-11-16 xy: Created.
     */
    public ResultMessage updateTaskItemFeild(TaskItemFeild taskItemFeild);

    /**
     * 删除采集子任务字段（逻辑删除）
     * @param taskItemFeild 任务Id
     * @return
     * @version <1> 2018-11-16 xy： Created.
     */
    public ResultMessage deleteTaskItemFeild(TaskItemFeild taskItemFeild);

    TaskItemFeild getById(Integer id);

    public ResultMessage updateData(CollectionFeildValue collectionFeildValue);

}
