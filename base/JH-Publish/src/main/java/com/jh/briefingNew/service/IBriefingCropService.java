package com.jh.briefingNew.service;

import com.jh.vo.ResultMessage;

/**
 * description:
 *
 * @version <1> 2018-07-23 wl: Created.
 */
public interface IBriefingCropService {
    /**
     * 根据区域加载作物
     * @param regionId : 区域ID
     * @return
     * @version <1> 2018-07-23 wl : Created.
     */
    ResultMessage findCropList(Long regionId);
}
