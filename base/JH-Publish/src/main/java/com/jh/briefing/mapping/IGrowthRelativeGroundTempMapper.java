package com.jh.briefing.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.briefing.entity.GrowthRelativeGroundTemp;
import com.jh.briefing.model.GrowthRelativeGroundTempParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IGrowthRelativeGroundTempMapper extends IBaseMapper<GrowthRelativeGroundTempParam,GrowthRelativeGroundTemp,Integer> {

    /**
     * 新增物候期地温关系数据
     * @param growthRelativeGroundTemp
     * @return
     */
    int saveGrowthRelativeGroundTemp(GrowthRelativeGroundTemp growthRelativeGroundTemp);

    /**
     * 删除物候期地温关系数据
     * @param relativeId
     * @return
     */
    int deleteGrowthRelativeTemp(@Param("relativeId") Integer relativeId);

    /**
     *  批量保存作物地温生长条件
     * @param growthConditions 作物地温生长条件集合
     * @return
     */
    int saveBatchGrowthRelativeGroundTemp(@Param("growthConditions") List<GrowthRelativeGroundTempParam> growthConditions);

    /**
     * 根据作物生育期编号，批量删除对应的地温条件数据。
     * @param growthId 作物生育期编号
     * @return 删除的条数
     */
    int deleteBatchGrowthConditionsByGrowthId(@Param("growthId") Integer growthId);

    /**
     * 修改物候期地温关系数据
     * @param growthRelativeGroundTemp
     * @return
     */
    int updateGrowthRelativeGroundTemp(GrowthRelativeGroundTemp growthRelativeGroundTemp);

    /**
     * 根据主键查询物候期地温关系数据
     * @param relativeId
     * @return
     */
    GrowthRelativeGroundTempParam findGrowthRelativeTempById(@Param("relativeId") Integer relativeId);

    /**
     * 分页查询
     * @param growthRelativeGroundTempParam 参数参数
     * @return 返回 List<GrowthRelativeGroundTempParam>
     */
    List<GrowthRelativeGroundTempParam> queryByPage(GrowthRelativeGroundTempParam growthRelativeGroundTempParam);

    /**
     * 根据growthId（物候期编号）查询对应生长与地温关系信息 列表
     * @param growthId
     * @return List<GrowthRelativeGroundTempParam>
     */
    List<GrowthRelativeGroundTempParam> queryGrowthRelativeTemp(@Param("growthId") Integer growthId);
}