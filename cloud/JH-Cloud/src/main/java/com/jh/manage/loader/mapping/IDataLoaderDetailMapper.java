package com.jh.manage.loader.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.manage.loader.entity.DataLoaderDetail;
import com.jh.manage.loader.model.LoaderParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IDataLoaderDetailMapper extends IBaseMapper<LoaderParam, DataLoaderDetail, Integer> {
}