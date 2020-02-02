package com.jh.data.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.data.entity.DataResult;
import com.jh.data.model.DataResultParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description:
 * 1.成果数据Mapper
 *
 * @version <1> 2018-05-02 16:10 zhangshen: Created.
 */
@Mapper
public interface IDataResultMapper extends IBaseMapper<DataResultParam, DataResult, Integer> {
    /**
     * @description: 查询成果数据
     * @param dataResultParam 成果数据对象
     * @version <1> 2018-05-03 wl： Created.
     */
    List<DataResult> findByPage(DataResultParam dataResultParam);


}
