package com.jh.briefing.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseService;
import com.jh.briefing.entity.DiseaseControlManager;
import com.jh.briefing.model.DiseaseControlManagerParam;
import com.jh.briefing.model.DiseaseControlManagerParam;
import com.jh.briefing.model.DiseaseControlParam;
import com.jh.vo.ResultMessage;

import java.util.List;
import java.util.Map;

/**
 *  作物生病防治服务接口类
 * Created by lxy on 2018/4/11.
 */
public interface IDiseaseControlManagerService extends IBaseService<DiseaseControlManagerParam,DiseaseControlManager,Integer> {

    /**
     * 根据物候期编号查询对应的物候期病情和病情防治措施
     * @param growthId 生育期ID
     * @return
     * @version <1> 2018-04-13 lxy： Created.
     */
    List<DiseaseControlManagerParam> queryCropsDiseaseByGrowthId(Integer growthId, Integer diseaseType);

    /**
     * 农作物病情防治信息记录新增
     * @param diseases 新增记录集合
     * @return 操作的结果
     * @version <1> 2018-05-22 lxy： Created.
     */
    public ResultMessage addBatchDiseaseControl(List<DiseaseControlManagerParam> diseases);

    /**
     * 农作物农病情防治信息记录删除
     * @param growthId 农作物生育期编号
     * @return 返回删除记录数
     * @version <1> 2018-05-23 lxy： Created.
     */
    public int deleteBatchDiseaseControl(Integer growthId);

}
