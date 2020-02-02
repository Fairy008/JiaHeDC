package com.jh.briefing.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseService;
import com.jh.briefing.entity.DiseaseControl;
import com.jh.briefing.model.DiseaseControlParam;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 *  作物生病防治服务接口类
 * Created by lxy on 2018/4/11.
 */
public interface IDiseaseControlService extends IBaseService<DiseaseControlParam,DiseaseControl,Integer> {
    /**
     * 作物生病防治信息分页查询
     * @param diseaseControlParam 农作物病情防治对象
     * @return
     * @version <1> 2018-04-13 lxy： Created.
     * @version <2> 2018-04-18 cxw： update:重写方法
     */
    PageInfo<DiseaseControlParam> findDiseaseByPage(DiseaseControlParam diseaseControlParam);

    /**
     * 根据物候期编号查询对应的物候期病情和病情防治措施
     * @param growthId 生育期ID
     * @return
     * @version <1> 2018-04-13 lxy： Created.
     */
    List<DiseaseControlParam> queryCropsDiseaseByGrowthId(Integer growthId, Integer diseaseType);

    /**
     * 农作物病情防治信息记录新增
     * @param diseaseControlParam 农作物病情防治对象
     * @return
     * @version <1> 2018-04-18 cxw： Created.
     */
    public ResultMessage addDiseaseControl(DiseaseControlParam diseaseControlParam);

    /**
     * 农作物病情防治信息记录修改
     * @param diseaseControlParam 农作物病情防治对象
     * @return
     * @version <1> 2018-04-18 cxw: Created.
     */
    public ResultMessage updateDiseaseControl(DiseaseControlParam diseaseControlParam);

    /**
     * 农作物农病情防治信息记录删除
     * @param diseaseId 农作物农病情防治信息ID
     * @return
     * @version <1> 2018-04-18 cxw： Created.
     */
    public ResultMessage deleteDiseaseControl(Integer diseaseId);


    /**
     * 农作物病情防治信息记录新增
     * @param diseases 新增记录集合
     * @return 操作的结果
     * @version <1> 2018-05-22 lxy： Created.
     */
    public ResultMessage addBatchDiseaseControl(List<DiseaseControlParam> diseases);

    /**
     * 农作物农病情防治信息记录删除
     * @param growthId 农作物生育期编号
     * @return 返回删除记录数
     * @version <1> 2018-05-23 lxy： Created.
     */
    public int deleteBatchDiseaseControl(Integer growthId);

}
