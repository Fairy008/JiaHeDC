package com.jh.collection.mapping;

import com.jh.collection.entity.CollectionTemplate;
import com.jh.collection.entity.query.CollectionTemplateQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectionTemplateMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(CollectionTemplate record);

    int insertSelective(CollectionTemplate record);

    CollectionTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CollectionTemplate record);

    int updateByPrimaryKey(CollectionTemplate record);

    List<CollectionTemplate> findByPage(CollectionTemplateQuery collectionTemplateQuery);

    CollectionTemplate findUniqueByProperty(String value);
}