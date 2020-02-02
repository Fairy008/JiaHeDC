package com.jh.produce.process.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.produce.process.entity.HandleStepFile;
import com.jh.produce.process.model.HandleDataParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IHandleStepFileMapper  extends IBaseMapper<HandleDataParam, HandleStepFile, Integer> {
    /**
     * 批量插入处理相关存储文件
     * @param hsfList
     * @version <1> 2018-03-19 cxj： Created.
     */
    void saveForHandleStepFileList(@Param(value = "hsfList") List<HandleStepFile> hsfList);

    /**
     * 根据步骤ID查看数据处理步骤文件信息表
     * @param handleDetailId
     * @version <1> 2019-02-18 zhangshen： Created.
     */
    List<HandleStepFile> findHandleStepFileByHandleDetailId(@Param(value = "handleDetailId") Integer handleDetailId);
}