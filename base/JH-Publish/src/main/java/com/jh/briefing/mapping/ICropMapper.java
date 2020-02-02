package com.jh.briefing.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.briefing.entity.CropData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;



/**
 * 查询作物
 * @version <1> 2018-04-17 cxw : Created.
 */
@Mapper
public interface ICropMapper extends IBaseMapper<Long,Long,Integer> {

    /**
     * 根据区域加载作物
     * @param regionId : 区域ID
     * @return
     * @version <1> 2018-04-17 cxw : Created.
     */
    List<CropData> findCropList(Long regionId);
}
