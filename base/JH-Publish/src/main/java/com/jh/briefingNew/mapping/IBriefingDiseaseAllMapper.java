package com.jh.briefingNew.mapping;

import com.jh.briefingNew.entity.BriefingDiseaseAll;
import com.jh.briefingNew.model.BriefingDiseaseAllParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * description:病虫害
 *
 * @version <1> 2018-07-19 wl: Created.
 */
@Mapper
public interface IBriefingDiseaseAllMapper {

    /**
     * 根据物候期编号查询对应的物候期病情和病情防治措施
     * @param params 作物编号 虫害类别
     * @return
     */
    List<BriefingDiseaseAll> queryCropsDiseaseByGrowthId(Map<String, Object> params);

}
