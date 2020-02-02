package com.jh.briefing.service.impl;

import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.briefing.entity.CropData;
import com.jh.briefing.mapping.ICropMapper;
import com.jh.briefing.service.ICropService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 查询作物
 * @version <1> 2018-04-17 cxw : Created.
 */
@Service
public class CropService extends BaseServiceImpl<Long,Long,Integer> implements ICropService {

    @Autowired
    private ICropMapper cropMapper;

    /**
     * 根据区域加载作物
     * @param regionId : 区域ID
     * @return
     * @version <1> 2017-12-06 cxw : Created.
     */
    @Override
    public ResultMessage findCropList(Long regionId) {
        ResultMessage result = null;
        if(regionId!=null){
            List<CropData> dsList = cropMapper.findCropList(regionId);
           result = ResultMessage.success(dsList);
        }else{
            result = ResultMessage.fail();
            result.setMsg("区域不能为空.");
        }
        return result;
    }

    @Override
    protected IBaseMapper<Long, Long, Integer> getDao() {
        return null;
    }
}
