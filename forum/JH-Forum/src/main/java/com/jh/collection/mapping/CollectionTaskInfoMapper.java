package com.jh.collection.mapping;

import com.jh.collection.entity.CollectionTaskInfo;
import com.jh.collection.entity.query.CollectionTaskInfoQuery;
import com.jh.collection.entity.vo.BbsCollectionTaskVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 采集任务大分类
 */
@Mapper
public interface CollectionTaskInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(CollectionTaskInfo record);

    int insertSelective(CollectionTaskInfo record);

    CollectionTaskInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CollectionTaskInfo record);

    int updateByPrimaryKey(CollectionTaskInfo record);

    /**
     * 分页查询
     * @param collectionTaskInfoQuery
     * @return
     */
    List<CollectionTaskInfo> findByPage(CollectionTaskInfoQuery collectionTaskInfoQuery);

    /**
     * 分页查询
     * @param bcTask
     * @returnList<CollectionTaskInfo>
     * @version <1> 2019-03-18 cxw： Created.
     */
    List<CollectionTaskInfo> findCollectDataList(BbsCollectionTaskVo bcTask);

}