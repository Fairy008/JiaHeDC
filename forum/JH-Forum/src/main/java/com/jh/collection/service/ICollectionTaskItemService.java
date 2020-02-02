package com.jh.collection.service;

import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.CollectionTaskItem;
import com.jh.collection.entity.vo.CollectionTaskItemVo;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 * 采集子任务接口
 * @version <1> 2018-12-04 xy: Created.
 */
public interface ICollectionTaskItemService {

    /**
     * 查询采集子任务
     * @param collectionTaskItem
     * @return
     * @version <1> 2018-12-04 xy： Created.
     */
    PageInfo<CollectionTaskItem> findByPage(CollectionTaskItem collectionTaskItem);

    /**
     * 新增采集任务
     * @return
     * @version <1> 2018-11-08 xy： Created.
     */
    public ResultMessage addTaskItem(CollectionTaskItem collectionTaskItem);

    /**
     * 更新采集任务
     * @return
     * @version <1> 2018-11-16 xy: Created.
     */
    public ResultMessage updateTaskItem(CollectionTaskItem collectionTaskItem);

    /**
     * 删除任务（逻辑删除）
     * @param collectionTaskItem 任务Id
     * @return
     * @version <1> 2018-11-16 xy： Created.
     */
    public ResultMessage deleteTaskItem(CollectionTaskItem collectionTaskItem);

    CollectionTaskItem getById(Integer id);

    List<CollectionTaskItemVo> getAllItemListByInfoId(Integer taskInfoId);

    ResultMessage getCoordinates(CollectionTaskItem collectionTaskItemQuery);

//    ResultMessage  getCoordinatesByTaskId(Integer taskId);
}
