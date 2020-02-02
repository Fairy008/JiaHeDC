package com.jh.show.agric.mapping;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * description:区域服务
 * 1.根据用户ID查询区域
 * @version <1> 2018-08-10 cxw: Created.
 */
@Mapper
public interface IAreaMapper {

    /**
     * 根据用户ID查询区域
     * @param accountId 用户账号ID
     * @return ResultMessage
     * @version <1> 2018-08-10 cxw: Created.
     */
    List<Map<String,Object>> findAreaByUserId(Integer accountId);

    /**
     * 查询报告时间轴 仅查询ds_area 表中的有效数据
     * @param map
     * regionId 区域ID
     * resolution 精度
     * @return ResultMessage :
     * @version <1> 2018-08-13 cxw:Created.
     */
    List<Map<String, Object>> findTimeAxisForWx(Map<String,Object> map);
}
