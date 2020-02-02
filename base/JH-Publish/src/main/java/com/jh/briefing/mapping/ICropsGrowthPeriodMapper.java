package com.jh.briefing.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.briefing.entity.CropsGrowthPeriod;
import com.jh.briefing.entity.Humidity;
import com.jh.briefing.entity.SoilMoisture;
import com.jh.briefing.model.CropsGrowthPeriodParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 作物生育期Mapper
 */
@Mapper
public interface ICropsGrowthPeriodMapper extends IBaseMapper<CropsGrowthPeriodParam,CropsGrowthPeriod,Integer> {
    /**
     * 作物生育周期分页查询
     * @param cropsGrowthPeriodParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-17 cxw： update:重写方法
     */
    List<CropsGrowthPeriodParam> findCropsGrowthPeriodByPage(CropsGrowthPeriodParam cropsGrowthPeriodParam);

    /**
     * 根据作物编号、区域编号查询对应的物候期
     * @param cropsGrowthPeriodParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     */
    List<CropsGrowthPeriodParam> findGrowthPeriodListByCropsIdAndRegionId(CropsGrowthPeriodParam cropsGrowthPeriodParam);


//    /**
//     *根据id查询数据字典数据
//     * @param id 字典主键
//     * @return DictParam
//     * @version <1> 2018-04-14 lxy: Created.
//     */
//    DictParam findDictById(Integer id);
//
//    /**
//     * @description: 查询子字典数据
//     * @param parentId 字典对象
//     * @version <1> 2018-04-14 lxy: Created.
//     */
//    List<DictParam> queryDictByParentId(Long parentId);

    /**
     * 查询所有的湿度信息
     * @return List<Humidity>
     */
    List<Humidity> queryAllHumidity();

    /**
     * 查询所有的墒情信息
     * @return List<SoilMoisture>
     */
    List<SoilMoisture> queryAllSoilMoisture();

    /**
     * 生育期配置添加
     * @param cropsGrowthPeriod 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-17 cxw: Created.
     */
    public int  addGrowthPeriod(CropsGrowthPeriod cropsGrowthPeriod);

    /**
     * 查询生育配置是否存在
     * @param cropsGrowthPeriod 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-17 cxw: Created.
     */
    public int findGrowthPeriodCount(CropsGrowthPeriod cropsGrowthPeriod);

    /**
     * 生育期配置修改
     * @param cropsGrowthPeriod 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-17 cxw: Created.
     */
    public int updateGrowthPeriod(CropsGrowthPeriod cropsGrowthPeriod);

    /**
     *根据区域，农作物查询生育期
     * @param cropsGrowthPeriodParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-18 cxw： Created.
     */
    public List<CropsGrowthPeriod> findGrowthPeriodList(CropsGrowthPeriodParam cropsGrowthPeriodParam);

    /**
     * 农作物生育周期配置记录删除
     * @param growthId 农作物生育周期ID
     * @return
     * @version <1> 2018-04-18 cxw： Created.
     */
    public int deleteCropsGrowthPeriod(@Param("growthId") Integer growthId);

    /**
     * 根据生育期编号 查询对应的生育期
     * @param growthId 生育期编号
     * @return
     */
    public CropsGrowthPeriodParam getGrowthPeriodByGrowthId(@Param("growthId") Integer growthId);

    /**
     * Description: 根据区域id、作物id、时间点 查询生育期
     * 查询的生育期可能没有, 可能多个, 多个时取时间点靠后的生育期
     * @param regionId
     * @param cropsId
     * @param dateStr 格式2018-07-25
     * @return 生育期列表
     * @version <1> 2018/7/25 14:16 zhangshen: Created.
     */
    List<CropsGrowthPeriod> getCropsGrowthPeriodList(@Param("regionId") Long regionId, @Param("cropsId") Integer cropsId, @Param("dateStr") String dateStr);
}