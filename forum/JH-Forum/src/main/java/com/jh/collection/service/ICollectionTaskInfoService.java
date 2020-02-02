package com.jh.collection.service;

import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.CollectionTaskInfo;
import com.jh.collection.entity.TaskItemFeild;
import com.jh.collection.entity.query.CollectionTaskInfoQuery;
import com.jh.vo.ResultMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 采集任务（大分类）接口
 * @version <1> 2018-12-04 xy: Created.
 */
public interface ICollectionTaskInfoService {

    /**
     * 查询采集任务描述
     * @param collectionTaskInfoQuery
     * @return
     * @version <1> 2018-12-04 xy： Created.
     */
    PageInfo<CollectionTaskInfo> findByPage(CollectionTaskInfoQuery collectionTaskInfoQuery);

    /**
     * 新增采集任务描述（大分类任务）
     * @return
     * @version <1> 2018-12-04 xy： Created.
     */
    public ResultMessage addTaskInfo(CollectionTaskInfo collectionTaskInfo);

    /**
     * 更新采集任务描述（大分类任务）
     * @return
     * @version <1> 2018-12-04: Created.
     */
    public ResultMessage updateTaskInfo(CollectionTaskInfo collectionTaskInfo);

    /**
     * 删除任务（逻辑删除）
     * @param collectionTaskInfo 任务Id
     * @return
     * @version <1> 2018-11-16 xy： Created.
     */
    public ResultMessage deleteTaskInfo(CollectionTaskInfo collectionTaskInfo);

    CollectionTaskInfo getById(Integer id);

    Map<Integer,List<TaskItemFeild>> getTaskItemFeildMap(Integer taskInfoId);

    String getAllMedisId(Integer taskInfoId);

    /**
     * 用户app端注册后默认给其创建个大任务
     * @param phone
     * @return
     */
    ResultMessage registerToAllocation(String phone);

    /**
     * 首页轮播
     * @param collectionTaskInfo 首页轮播
     * @return
     * @version <1> 2019-03-18 lijie： Created.
     */
    ResultMessage updateIndexShow(HttpServletRequest request);

    /**
     * 采集任务查询
     * @param regionId 区域ID
     * @param phone 手机号
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return ResultMessage
     * @version <1> 2019-03-18 cxw： Created.
     */
    public ResultMessage findCollectDataList(String phone,Long regionId,String startDate,String endDate);

}
