package com.jh.briefingNew.service.impl;

import com.jh.briefingNew.entity.BriefingCrop;
import com.jh.briefingNew.mapping.IBriefingCropMapper;
import com.jh.briefingNew.service.IBriefingCropService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-07-23 wl: Created.
 */
@Service
public class BriefingCropServiceImpl implements IBriefingCropService{
    @Autowired
    private IBriefingCropMapper briefingCropMapper;

    /**
     * 根据区域加载作物
     * @param regionId : 区域ID
     * @return
     * @version <1> 2017-12-06 cxw : Created.
     */
    @Override
    public ResultMessage findCropList(Long regionId) {
        ResultMessage result = null;
        if(regionId!=null)
        {
            List<BriefingCrop> dsList = briefingCropMapper.findCropList(regionId);
            result = ResultMessage.success(dsList);
        }
        else{
              result = ResultMessage.fail();
            result.setMsg("区域不能为空.");
        }
        return result;
    }
}
