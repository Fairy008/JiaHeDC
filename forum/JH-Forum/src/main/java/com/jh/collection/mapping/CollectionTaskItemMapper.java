package com.jh.collection.mapping;

import com.jh.collection.entity.CollectionTaskItem;
import com.jh.collection.entity.vo.CollectionTaskItemVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectionTaskItemMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(CollectionTaskItem record);

    int insertSelective(CollectionTaskItem record);

    CollectionTaskItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CollectionTaskItem record);

    int updateByPrimaryKey(CollectionTaskItem record);

    List<CollectionTaskItem> findByPage(CollectionTaskItem collectionTaskItem);

    /**
     * 查看大任务下子任务数量
     * @param taskInfoid
     * @return
     */
    int findTaskItemCount(Integer taskInfoid);

  List<String> findAllTaskItemMedia(Integer taskInfoId);

  List<CollectionTaskItemVo>  getAllItemListByInfoId(Integer taskInfoId);
//    List<CollectionTaskItem> getCoordinatesByTaskId(Integer taskId);
}