package com.jh.briefing.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.briefing.entity.DiseaseControl;
import com.jh.briefing.entity.DiseaseControlManager;
import com.jh.briefing.entity.GrowthDiseaseManager;
import com.jh.briefing.model.DiseaseControlManagerParam;
import com.jh.briefing.model.DiseaseControlParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface IGrowthDiseaseManagerMapper extends IBaseMapper<DiseaseControlManagerParam,DiseaseControlManager,Integer> {

    /**
     * 根据物候期编号查询对应的物候期病情和病情防治措施
     * @param params 查询参数
     * @return 返回对应的病虫害防治措施
     * @version <1> 2018-05-22 lxy： Created.
     */
    List<DiseaseControlManagerParam> queryCropsDiseaseByGrowthId(Map<String, Object> params);

    /**
     * 农作物病情防治信息记录新增
     * @param diseases 新增记录集合
     * @return 操作的结果
     * @version <1> 2018-05-22 lxy： Created.
     */
    public int addBatchDiseaseControl(@Param("diseases") List<DiseaseControlManagerParam> diseases);


    /**
     * 农作物农病情防治信息记录删除
     * @param growthId 作物生育期编号
     * @return 返回删除记录数
     * @version <1> 2018-05-22 lxy： Created.
     */
    public int deleteBatchDiseaseControl(@Param("growthId") Integer growthId);
}