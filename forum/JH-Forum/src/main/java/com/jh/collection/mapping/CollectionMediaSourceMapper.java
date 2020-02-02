package com.jh.collection.mapping;

import com.jh.collection.entity.CollectionMediaSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 采集媒体资源Mapper
 * @version <1> 2018-11-15 xy： Created.
 */
@Mapper
public interface CollectionMediaSourceMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(CollectionMediaSource record);

    int insertSelective(CollectionMediaSource record);

    CollectionMediaSource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CollectionMediaSource record);

    int updateByPrimaryKeyWithBLOBs(CollectionMediaSource record);

    int updateByPrimaryKey(CollectionMediaSource record);

    /**
     * 根据mediaIds 查询媒体资源文件List
     * @param list
     * @return
     */
    List<CollectionMediaSource> getMediaList(List<String> list);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int batchInsert(@Param("list") List<CollectionMediaSource> list);
}