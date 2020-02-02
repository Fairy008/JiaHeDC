package com.jh.collection.service.impl;

import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.TaskItemFeild;
import com.jh.collection.entity.vo.CollectionFeildValue;
import com.jh.collection.entity.vo.CollectionTaskFeildItem;
import com.jh.collection.mapping.TaskItemFeildMapper;
import com.jh.collection.service.ITaskItemFeildService;
import com.jh.util.JsonUtils;
import com.jh.vo.ResultMessage;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 采集子任务关联采集接口
 * @version <1> 2018-12-04: Created.
 */
@Service
public class TaskItemFeildServiceImpl implements ITaskItemFeildService {

    private static final Logger LOG = LoggerFactory.getLogger(TaskItemFeildServiceImpl.class);

    @Autowired
    private TaskItemFeildMapper taskItemFeildMapper ;


    @Override
    public PageInfo<TaskItemFeild> findByPage(TaskItemFeild taskItemFeild) {
        return null;
    }

    @Override
    public ResultMessage addTaskItemFeild(TaskItemFeild taskItemFeild) {
        return null;
    }

    @Override
    public ResultMessage updateTaskItemFeild(TaskItemFeild taskItemFeild) {
        return null;
    }

    @Override
    public ResultMessage deleteTaskItemFeild(TaskItemFeild taskItemFeild) {
        return null;
    }

    @Override
    public TaskItemFeild getById(Integer id) {
        return null;
    }

    @Transactional(readOnly = false)
    @Override
    public ResultMessage updateData(CollectionFeildValue collectionFeildValue) {
        LOG.info(">>>update CollectionFeildValue params:{}", JsonUtils.objectToJson(collectionFeildValue));
        if(collectionFeildValue == null || CollectionUtils.isEmpty(collectionFeildValue.getCollectionTaskFeildItemList())){
            return ResultMessage.fail("采集任务数据不能为空");
        }
        List<TaskItemFeild> taskItemFeildList = new ArrayList<TaskItemFeild>();
        for(CollectionTaskFeildItem collectionTaskFeildItem : collectionFeildValue.getCollectionTaskFeildItemList()){
            TaskItemFeild taskItemFeild = new TaskItemFeild();
            taskItemFeild.setTaskItemId(collectionTaskFeildItem.getTaskItemId());//子任务Id
            taskItemFeild.setFeildNameCh(collectionTaskFeildItem.getFeildNameCh());//中文名
            taskItemFeild.setFeildNameEn(collectionTaskFeildItem.getFeildNameEn());//英文名
            taskItemFeild.setCollectionValue(collectionTaskFeildItem.getCollectionValue());//采集值
            taskItemFeild.setUpdateTime(new Date());
            taskItemFeildList.add(taskItemFeild);
        }
        taskItemFeildMapper.batchUpdateFeildValue(taskItemFeildList);
        return ResultMessage.success();
    }
}
