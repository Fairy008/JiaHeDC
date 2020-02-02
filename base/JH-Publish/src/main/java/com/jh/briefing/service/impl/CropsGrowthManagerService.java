package com.jh.briefing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.briefing.entity.CropsGrowthManager;
import com.jh.briefing.entity.Humidity;
import com.jh.briefing.entity.SoilMoisture;
import com.jh.briefing.mapping.*;
import com.jh.briefing.model.*;
import com.jh.briefing.service.ICropsGrowthManagerService;
import com.jh.briefing.service.ICropsGrowthManagerService;
import com.jh.util.CollectionUtil;
import com.jh.util.DateUtil;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * 生育期配置操作
 * @version <1> 2018-04-12 lxy： Created.
 */
@Service
@Transactional
public class CropsGrowthManagerService extends BaseServiceImpl<CropsGrowthManagerParam,CropsGrowthManager,Integer> implements
        ICropsGrowthManagerService {

    @Autowired
    private ICropsGrowthManagerMapper cropsGrowthManagerMapper;
    @Autowired
    private IGrowthDiseaseManagerMapper growthDiseaseManagerMapper;
    @Autowired
    private IGrowthRelativeTempManagerMapper growthRelativeGroundTempMapper;

    @Override
    protected IBaseMapper<CropsGrowthManagerParam, CropsGrowthManager, Integer> getDao() {
        return cropsGrowthManagerMapper;
    }

    /**
     * 作物生育周期分页查询
     * @param cropsGrowthManagerParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-17 cxw： update:重写方法
     */
    @Override
    public PageInfo<CropsGrowthManagerParam> findCropsGrowthManagerByPage(CropsGrowthManagerParam cropsGrowthManagerParam) {
        PageHelper.startPage(cropsGrowthManagerParam.getPage(), cropsGrowthManagerParam.getRows());
        List<CropsGrowthManagerParam> list = cropsGrowthManagerMapper.findCropsGrowthManagerByPage(cropsGrowthManagerParam);
        return new PageInfo<CropsGrowthManagerParam>(list);
    }

    /**
     * 作物生育周期不分页查询
     * @param cropsGrowthManagerParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-12 lj： Created.
     */
    @Override
    public List<CropsGrowthManagerParam> findCropsGrowthManagerList(CropsGrowthManagerParam cropsGrowthManagerParam) {
        return cropsGrowthManagerMapper.findCropsGrowthManagerByPage(cropsGrowthManagerParam);
    }


    /**
     * 生育期配置添加
     * @param cropsGrowthManager 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-17 cxw: Created.
     */
    @Override
    public ResultMessage addGrowthManager(CropsGrowthManager cropsGrowthManager) {
        ResultMessage res = ResultMessage.success();
        //查询要保存数据是否已存在
        int findNum = cropsGrowthManagerMapper.findGrowthManagerCount(cropsGrowthManager);
        if(findNum==0)
        {
            int addnum  = cropsGrowthManagerMapper.addGrowthManager(cropsGrowthManager);
            if(addnum>0)
            {
                res.setMsg("添加作物生育期成功");
                res.setData(cropsGrowthManager.getId());//返回生成的主键值
            }
            else{
                res = ResultMessage.fail();
                res.setMsg("添加作物生育期失败");
            }
        }
        else
        {
            res = ResultMessage.fail();
            res.setMsg("不能重复添加作物生育期");
        }
        return  res;
    }

    /**
     * 生育期配置修改
     * @param cropsGrowthManager 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-17 cxw: Created.
     */
    @Override
    public ResultMessage updateGrowthManager(CropsGrowthManager cropsGrowthManager) {
        ResultMessage res = ResultMessage.success();
        //查询要保存数据是否已存在
        int findNum = cropsGrowthManagerMapper.findGrowthManagerCount(cropsGrowthManager);
        if(findNum==0)
        {
            int updatenum  = cropsGrowthManagerMapper.updateGrowthManager(cropsGrowthManager);
            if(updatenum>0)
            {
                res.setMsg("修改作物生育期成功");
            }
            else{
                res = ResultMessage.fail();
                res.setMsg("修改作物生育期失败");
            }
        }
        else
        {
            res = ResultMessage.fail();
            res.setMsg("不能重复修改作物生育期");
        }
        return  res;
    }

    /**
     * 农作物生育周期配置记录删除
     * @param growthId 农作物生育周期ID
     * @return 返回删除的记录数
     * @version <1> 2018-04-18 cxw： Created.
     */
    @Override
    public ResultMessage deleteCropsGrowthManager(Integer growthId) {
        ResultMessage res = ResultMessage.success();
        int  delnum = cropsGrowthManagerMapper.deleteCropsGrowthManager(growthId);
        if(delnum>0) {
            growthDiseaseManagerMapper.deleteBatchDiseaseControl(growthId);//删除病虫害
            growthRelativeGroundTempMapper.deleteBatchGrowthConditionsByGrowthId(growthId);//删除地温关系
            res.setMsg("删除作物生育期成功");
        }else{
            res = ResultMessage.fail();
            res.setMsg("删除作物生育期失败");
        }
        return  res;
    }

    /**
     * 根据生物期编号获取生育期相关新，包括病虫害信息，地温条件等信息
     * @param growthId 生育期编号
     * @return 返回生育期相关数据
     * version <1> 2018-05-22 lxy:created
     */
    @Override
    public ResultMessage findGrowthDataByGrowthId(@RequestParam Integer growthId) {
        CropsGrowthManagerParam cropsGrowthManager = cropsGrowthManagerMapper.getGrowthManagerByGrowthId(growthId);//获得生育期信息
        if(cropsGrowthManager == null){
            return ResultMessage.fail("没有找到对应的物候期");
        }
        //查询病害信息
        Map<String,Object> params = new HashMap<>();
        params.put("growthId",growthId);
        params.put("diseaseType",1);
        List<DiseaseControlManagerParam> diseases =  growthDiseaseManagerMapper.queryCropsDiseaseByGrowthId(params);
        //查询虫害信息
        params.remove("diseaseType");
        params.put("diseaseType",2);
        List<DiseaseControlManagerParam> bugDiseases =  growthDiseaseManagerMapper.queryCropsDiseaseByGrowthId(params);
        //查询对应的生长条件
        List<GrowthRelativeTempManagerParam> growthConditions = growthRelativeGroundTempMapper.queryGrowthRelativeTemp(growthId);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("diseases",diseases);//病害信息
        resultMap.put("bugDiseases",bugDiseases);//虫害信息
        resultMap.put("growthConditions",growthConditions);//生长条件
        resultMap.put("growthManager",cropsGrowthManager);//生育期
        return ResultMessage.success(resultMap);//返回对应生育期信息
    }

}
