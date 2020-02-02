package com.jh.briefing.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.briefing.entity.CropsGrowthManager;
import com.jh.briefing.entity.CropsGrowthManager;
import com.jh.briefing.entity.Humidity;
import com.jh.briefing.entity.SoilMoisture;
import com.jh.briefing.model.CropsGrowthManagerParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ICropsGrowthManagerMapper extends IBaseMapper<CropsGrowthManagerParam,CropsGrowthManager,Integer> {
    /**
     * 作物生育周期分页查询
     * @param cropsGrowthManagerParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-17 cxw： update:重写方法
     */
    List<CropsGrowthManagerParam> findCropsGrowthManagerByPage(CropsGrowthManagerParam cropsGrowthManagerParam);

    /**
     * 生育期配置添加
     * @param cropsGrowthManager 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-17 cxw: Created.
     */
    public int  addGrowthManager(CropsGrowthManager cropsGrowthManager);

    /**
     * 查询生育配置是否存在
     * @param cropsGrowthManager 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-17 cxw: Created.
     */
    public int findGrowthManagerCount(CropsGrowthManager cropsGrowthManager);

    /**
     * 生育期配置修改
     * @param cropsGrowthManager 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-17 cxw: Created.
     */
    public int updateGrowthManager(CropsGrowthManager cropsGrowthManager);

    /**
     * 农作物生育周期配置记录删除
     * @param growthId 农作物生育周期ID
     * @return
     * @version <1> 2018-04-18 cxw： Created.
     */
    public int deleteCropsGrowthManager(@Param("growthId") Integer growthId);

    /**
     * 根据生育期编号 查询对应的生育期
     * @param id 生育期编号
     * @return
     */
    public CropsGrowthManagerParam getGrowthManagerByGrowthId(@Param("id") Integer id);


}