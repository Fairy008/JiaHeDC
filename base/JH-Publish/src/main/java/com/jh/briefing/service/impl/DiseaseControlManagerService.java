package com.jh.briefing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.briefing.entity.DiseaseControl;
import com.jh.briefing.entity.DiseaseControlManager;
import com.jh.briefing.mapping.IDiseaseAllMapper;
import com.jh.briefing.mapping.IDiseaseControlMapper;
import com.jh.briefing.mapping.IGrowthDiseaseManagerMapper;
import com.jh.briefing.model.DiseaseControlManagerParam;
import com.jh.briefing.model.DiseaseControlParam;
import com.jh.briefing.service.IDiseaseControlManagerService;
import com.jh.briefing.service.IDiseaseControlService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lj on 2018/8/10.
 */
@Service
@Transactional
public class DiseaseControlManagerService extends BaseServiceImpl<DiseaseControlManagerParam,DiseaseControlManager,Integer> implements
        IDiseaseControlManagerService {

    @Autowired
    private IGrowthDiseaseManagerMapper growthDiseaseManagerMapper;

    @Override
    protected IBaseMapper<DiseaseControlManagerParam,DiseaseControlManager, Integer> getDao() {
        return growthDiseaseManagerMapper;
    }

    /**
     * 根据物候期编号查询对应的物候期病情和病情防治措施
     * @param growthId 作物编号
     * @param diseaseType 病虫害类型
     * @return 对应的病虫害信息
     * @version <1> 2018-04-13 lxy： Created.
     */
    @Override
    public List<DiseaseControlManagerParam> queryCropsDiseaseByGrowthId(Integer growthId, Integer diseaseType) {
        Map<String,Object> param = new HashMap<>();
        param.put("growthId",growthId);
        param.put("diseaseType",diseaseType);
        return growthDiseaseManagerMapper.queryCropsDiseaseByGrowthId(param);
    }


    /**
     * 农作物病情防治信息记录新增
     * @param diseases 新增记录集合
     * @return 操作的结果
     * @version <1> 2018-05-22 lxy： Created.
     */
    @Override
    public ResultMessage addBatchDiseaseControl(List<DiseaseControlManagerParam> diseases) {
        try{
            growthDiseaseManagerMapper.addBatchDiseaseControl(diseases);
        }catch(Exception e){
            e.printStackTrace();
            return ResultMessage.fail(e.getMessage());
        }

        return ResultMessage.success();
    }

    /**
     * 农作物农病情防治信息记录删除
     * @param growthId 农作物生育期编号
     * @return 返回删除记录数
     * @version <1> 2018-05-23 lxy： Created.
     */
    @Override
    public int deleteBatchDiseaseControl(Integer growthId) {
        return growthDiseaseManagerMapper.deleteBatchDiseaseControl(growthId);
    }
}
