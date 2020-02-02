package com.jh.collection.mapping;

import com.jh.collection.entity.TaskItemFeild;
import com.jh.collection.entity.vo.CollectionAnalysisVo;
import com.jh.collection.entity.vo.CollectionFieldModelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskItemFeildMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TaskItemFeild record);

    int insertSelective(TaskItemFeild record);

    TaskItemFeild selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskItemFeild record);

    int updateByPrimaryKey(TaskItemFeild record);

    /**
     * 批量插入采集子任务和采集字段的关联关系
     * @param taskItemFeildList
     * @return
     */
    int batchInsertTaskItemFeild(@Param("taskItemFeildList") List<TaskItemFeild> taskItemFeildList);

    int batchUpdateFeildValue(@Param("taskItemFeildList") List<TaskItemFeild> taskItemFeildList);

    List<CollectionFieldModelVo> findByItemId(Integer taskItemId);

    /**
     * 根据大任务Id查询所有子任务采集数据
     * @param taskInfoId
     * @return
     */
    List<TaskItemFeild> findListsByTaskInfo(Integer taskInfoId);

    /**
     * 统计分布
     * @param taskItemId
     * @return
     */
    List<CollectionAnalysisVo> queryDistributionHistory(Integer taskItemId);

}