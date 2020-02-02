package com.jh.data.service;

import com.github.pagehelper.PageInfo;
import com.jh.data.entity.DataResult;
import com.jh.data.model.DataResultParam;

/**
 * Description:
 * 1.成果数据接口
 *
 * @version <1> 2018-05-02 15:57 zhangshen: Created.
 */
public interface IDataResultService {

    /**
     * 查询成果数据转换数据列表
     * @param dataResultParam
     * @return
     * @version <1> 2018-05-02 zhangshen: Created.
     */
    PageInfo<DataResult> findByPage(DataResultParam dataResultParam);

}
