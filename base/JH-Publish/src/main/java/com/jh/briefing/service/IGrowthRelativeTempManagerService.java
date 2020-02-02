package com.jh.briefing.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseService;
import com.jh.briefing.entity.GrowthRelativeTempManager;
import com.jh.briefing.entity.GrowthRelativeTempManager;
import com.jh.briefing.model.GrowthRelativeTempManagerParam;
import com.jh.briefing.model.GrowthRelativeTempManagerParam;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 *  作物生长情况与地温关系服务类
 * @version <1> 2018-05-23 lxy： Created.
 */
public interface IGrowthRelativeTempManagerService extends IBaseService<GrowthRelativeTempManagerParam,GrowthRelativeTempManager,Integer> {

    /**
     * 新增物候期地温关系数据
     * @param growthRelativeGroundTemp
     * @return
     */
    ResultMessage saveGrowthRelativeTempManager(GrowthRelativeTempManager growthRelativeGroundTemp);

    /**
     * 删除物候期地温关系数据
     * @param relativeId
     * @return
     * @version <1> 2018-04-17 lxy： Created.
     */
    ResultMessage deleteGrowthRelativeTemp(Integer relativeId);

    /**
     *  批量保存作物地温生长条件
     * @param growthConditions 作物地温生长条件集合
     * @return 操作结果
     * @version <1> 2018-05-23 lxy： Created.
     */
    ResultMessage saveBatchGrowthRelativeTempManager(List<GrowthRelativeTempManagerParam> growthConditions);

    /**
     * 根据作物生育期编号，批量删除对应的地温条件数据。
     * @param growthId 作物生育期编号
     * @return 删除的条数
     * @version <1> 2018-05-23 lxy： Created.
     */
    ResultMessage deleteBatchGrowthConditionsByGrowthId(Integer growthId);

    /**
     * 修改物候期地温关系数据
     * @param growthRelativeGroundTemp
     * @return
     */
    ResultMessage updateGrowthRelativeTempManager(GrowthRelativeTempManager growthRelativeGroundTemp);

    /**
     * 根据主键查询物候期地温关系数据
     * @param relativeId
     * @return
     */
    ResultMessage findGrowthRelativeTempById(Integer relativeId);

    /**
     *  分页查询
     * @param growthRelativeGroundTempParam
     * @return 返回分页数据。
     * @version <1> 2018-04-17 lxy： Created.
     */
    PageInfo<GrowthRelativeTempManagerParam> findByPage(GrowthRelativeTempManagerParam growthRelativeGroundTempParam);

    /**
     * 根据growthId（物候期编号）查询对应生长与地温关系信息列表
     * @param growthId
     * @return List<GrowthRelativeTempManagerParam>
     */
    List<GrowthRelativeTempManagerParam> queryGrowthRelativeTemp(Integer growthId);

}
