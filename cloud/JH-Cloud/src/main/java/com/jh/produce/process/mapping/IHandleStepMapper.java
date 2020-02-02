package com.jh.produce.process.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.produce.process.entity.HandleStep;
import com.jh.produce.process.model.HandleDataParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IHandleStepMapper extends IBaseMapper<HandleDataParam, HandleStep, Integer> {

    /**
     * 通过任务ID，查询处理步骤列表数据
     * @param handleId
     * @version <1> 2018-03-19 cxj： Created.
     */
    List<HandleStep> findListByHandleId(@Param(value = "handleId") Integer handleId);

    void updateHandleStepStatus(@Param(value = "handleId") Integer handleId, @Param(value = "handleStatus") Integer handleStatus);
}