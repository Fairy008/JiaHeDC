package com.jh.show.agric.service;

import com.jh.biz.persist.IBaseService;
import com.jh.show.agric.entity.CustomLocaleEntity;
import com.jh.vo.ResultMessage;

import java.util.Map;

/**
 * description:区域服务
 * 1.根据用户ID查询区域
 * @version <1> 2018-08-10 cxw: Created.
 */
public interface IAreaService  {

    /**
     * 根据用户ID查询区域
     * @param token 用户标识
     * @return ResultMessage
     * @version <1> 2018-08-10 cxw: Created.
     */
    public ResultMessage findAreaByUserId(String token);

    /**
     * 查询报告时间轴 仅查询ds_area 表中的有效数据
     * @param
     * regionId 区域ID
     * resolution 精度
     * @return ResultMessage :
     * @version <1> 2018-08-13 cxw:Created.
     */
    public ResultMessage findTimeAxisForWx(Long regionId,Integer resolution);
}
