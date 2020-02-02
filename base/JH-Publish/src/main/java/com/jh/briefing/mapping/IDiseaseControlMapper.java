package com.jh.briefing.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.briefing.entity.DiseaseControl;
import com.jh.briefing.model.DiseaseControlParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDiseaseControlMapper extends IBaseMapper<DiseaseControlParam,DiseaseControl,Integer> {
    /**
     * 作物病情防治
     *
     * @param diseaseControlParam 查询参数
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     */
    List<DiseaseControlParam> findDiseaseByPage(DiseaseControlParam diseaseControlParam);

    /**
     * 根据物候期编号查询对应的物候期病情和病情防治措施
     * @param growthId 作物编号
     * @return
     */
    List<DiseaseControlParam> queryCropsDiseaseByGrowthId(@Param("growthId") Integer growthId);

    /**
     * 农作物病情防治信息记录新增
     * @param diseaseControlParam
     * @return
     * @version <1> 2018-04-18 cxw： Created.
     */
    public int addDiseaseControl(DiseaseControlParam diseaseControlParam);

    /**
     * 农作物病情防治信息记录修改
     * @param diseaseControlParam
     * @return
     * @version <1> 2018-04-18 cxw: Created.
     */
    public int updateDiseaseControl(DiseaseControlParam diseaseControlParam);

    /**
     * 农作物农病情防治信息记录删除
     * @param diseaseId 农作物农病情防治信息ID
     * @return
     * @version <1> 2018-04-18 cxw： Created.
     */
    public  int deleteDiseaseControl(Integer diseaseId);

    /**
     * 查询生育配置是否存在
     * @param diseaseControlParam
     * @return
     * @version <1> 2018-04-18 cxw: Created.
     */
    public int findDiseaseControlCount(DiseaseControlParam diseaseControlParam);
}