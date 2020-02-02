package com.jh.briefing.service;


import com.jh.base.service.IBaseService;
import com.jh.vo.ResultMessage;

/**
 * 查询作物
 * @version <1> 2018-04-17 cxw : Created.
 */
public interface ICropService extends IBaseService<Long,Long,Integer> {

    /**
     * 根据区域加载作物
     * @param regionId : 区域ID
     * @return
     * @version <1> 2018-04-17 cxw : Created.
     */
    ResultMessage findCropList(Long regionId);
}
