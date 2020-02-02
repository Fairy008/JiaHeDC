package com.jh.briefingNew.mapping;

import com.jh.briefingNew.entity.BriefingCrop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-07-23 wl: Created.
 */
@Mapper
public interface IBriefingCropMapper {
    /**
     * 根据区域加载作物
     * @param regionId : 区域ID
     * @return
     * @version <1> 2018-07-23 wl : Created.
     */
    List<BriefingCrop> findCropList(Long regionId);
}
