package com.jh.logs.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.logs.entity.LogParam;
import com.jh.logs.entity.Logs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ILogsMapper extends IBaseMapper<LogParam,Logs,Integer> {

    /**
     * 分页查询日志记录
     * @param logParam
     * @return
     * @version <I> 2018-03-12 XZG: Created
     */
    List<Logs> queryByPage(LogParam logParam);


}