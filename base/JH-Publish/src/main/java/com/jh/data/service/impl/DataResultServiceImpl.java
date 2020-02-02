package com.jh.data.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.data.entity.DataResult;
import com.jh.data.mapping.IDataResultMapper;
import com.jh.data.model.DataResultParam;
import com.jh.data.service.IDataResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 * 1.成果数据实现类
 *
 * @version <1> 2018-05-02 15:58 zhagnshen: Created.
 */
@Service
@Transactional
public class DataResultServiceImpl implements IDataResultService {

    @Autowired
    private IDataResultMapper dataResultMapper;

    /**
     * 查询成果数据转换数据列表
     * @param dataResultParam
     * @return
     * @version <1> 2018-05-02 zhagnshen: Created.
     */
    @Override
    public PageInfo<DataResult> findByPage(DataResultParam dataResultParam){
        PageHelper.startPage(dataResultParam.getPage(), dataResultParam.getRows());
        List<DataResult> list = dataResultMapper.findByPage(dataResultParam);
        return new PageInfo<DataResult>(list);
    }
}
