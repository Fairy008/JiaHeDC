package com.jh.briefing.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseService;
import com.jh.briefing.entity.CropsGrowthManager;
import com.jh.briefing.entity.Humidity;
import com.jh.briefing.entity.SoilMoisture;
import com.jh.briefing.model.CropsGrowthManagerParam;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 *  作物生育周期服务接口类
 * Created by lxy on 2018/8/10.
 */
public interface ICropsGrowthManagerService extends IBaseService<CropsGrowthManagerParam,CropsGrowthManager,Integer> {
    /**
     * 作物生育周期分页查询
     * @param cropsGrowthManagerParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-08-10 lj: Created.
     */
    PageInfo<CropsGrowthManagerParam> findCropsGrowthManagerByPage(CropsGrowthManagerParam cropsGrowthManagerParam);

    /**
     * 作物生育周期不分页查询
     * @param cropsGrowthManagerParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-08-13 lj： Created.
     */
    public List<CropsGrowthManagerParam> findCropsGrowthManagerList(CropsGrowthManagerParam cropsGrowthManagerParam);

    /**
     * 生育期配置添加
     * @param cropsGrowthManager 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-08-10 lj: Created.
     */
    public ResultMessage addGrowthManager(CropsGrowthManager cropsGrowthManager);

    /**
     * 生育期配置修改
     * @param cropsGrowthManager 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-08-10 lj: Created.
     */
    public ResultMessage updateGrowthManager(CropsGrowthManager cropsGrowthManager);

    /**
     * 农作物生育周期配置记录删除
     * @param growthId 农作物生育周期ID
     * @return
     * @version <1> 2018-08-10 lj： Created.
     */
    public  ResultMessage deleteCropsGrowthManager(Integer growthId);

    /**
     * 根据生物期编号获取生育期相关新，包括病虫害信息，地温条件等信息
     * @param growthId 生育期编号
     * @return 返回生育期相关数据
     * version <1> 2018-08-10 lxy:created
     */
    public ResultMessage findGrowthDataByGrowthId(Integer growthId);


}
