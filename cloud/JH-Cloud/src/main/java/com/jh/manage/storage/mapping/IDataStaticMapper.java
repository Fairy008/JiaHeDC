package com.jh.manage.storage.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.manage.storage.entity.DataStatic;
import com.jh.manage.storage.model.ImportStaticParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description:
 * 1.矢量数据Mapper
 *
 * @version <1> 2018-04-26 17:37 zhangshen: Created.
 */
@Mapper
public interface IDataStaticMapper extends IBaseMapper<ImportStaticParam, DataStatic, Integer> {

    /**
     * 插入矢量数据导入明细
     * @param list
     */
    void insertDataStatic(List<DataStatic> list);

    /**
     * Description: 根据staticId,查询矢量数据
     * @param staticId
     * @return
     * @version <1> 2018/5/24 16:25 zhangshen: Created.
     */
    DataStatic findDateStaticById(Integer staticId);
}
