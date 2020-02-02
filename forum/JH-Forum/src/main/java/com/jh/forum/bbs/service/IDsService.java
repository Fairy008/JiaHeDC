package com.jh.forum.bbs.service;

import com.jh.forum.bbs.vo.DsVo;
import com.jh.vo.ResultMessage;

/**
 * @Description: 数据集查询
 * @version <1> 2019-03-13 cxw:Created.
 */
public interface IDsService {

    /**
     * 根据区域查询所有数据集
     * regionId 区域ID
     * <1> 2019-03-13 cxw: Created.
     */
    public ResultMessage findDsListByRegionId( ResultMessage  cropResult, ResultMessage  dsResolutionResult,Long regionId) ;


    ResultMessage findDsList(DsVo dsVo);
}
