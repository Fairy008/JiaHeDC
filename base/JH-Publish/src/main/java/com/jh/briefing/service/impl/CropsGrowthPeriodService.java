package com.jh.briefing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.briefing.entity.CropsGrowthPeriod;
import com.jh.briefing.entity.Humidity;
import com.jh.briefing.entity.SoilMoisture;
import com.jh.briefing.mapping.ICropsGrowthPeriodMapper;
import com.jh.briefing.mapping.IDiseaseAllMapper;
import com.jh.briefing.mapping.IGrowthRelativeGroundTempMapper;
import com.jh.briefing.model.CropsGrowthPeriodParam;
import com.jh.briefing.model.DiseaseControlParam;
import com.jh.briefing.model.GrowthRelativeGroundTempParam;
import com.jh.briefing.service.ICropsGrowthPeriodService;
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
public class CropsGrowthPeriodService extends BaseServiceImpl<CropsGrowthPeriodParam,CropsGrowthPeriod,Integer> implements
        ICropsGrowthPeriodService {

    @Autowired
    private ICropsGrowthPeriodMapper cropsGrowthPeriodMapper;
    @Autowired
    private IDiseaseAllMapper diseaseAllMapper;
    @Autowired
    private IGrowthRelativeGroundTempMapper growthRelativeGroundTempMapper;

    @Override
    protected IBaseMapper<CropsGrowthPeriodParam, CropsGrowthPeriod, Integer> getDao() {
        return cropsGrowthPeriodMapper;
    }

    /**
     * 作物生育周期分页查询
     * @param cropsGrowthPeriodParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-17 cxw： update:重写方法
     */
    @Override
    public PageInfo<CropsGrowthPeriodParam> findCropsGrowthPeriodByPage(CropsGrowthPeriodParam cropsGrowthPeriodParam) {
        PageHelper.startPage(cropsGrowthPeriodParam.getPage(), cropsGrowthPeriodParam.getRows());
        List<CropsGrowthPeriodParam> list = cropsGrowthPeriodMapper.findCropsGrowthPeriodByPage(cropsGrowthPeriodParam);
        return new PageInfo<CropsGrowthPeriodParam>(list);
    }

    /**
     * 生育期配置添加
     * @param cropsGrowthPeriod 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-17 cxw: Created.
     */
    @Override
    public ResultMessage addGrowthPeriod(CropsGrowthPeriod cropsGrowthPeriod) {
        ResultMessage res = ResultMessage.success();
        //查询要保存数据是否已存在
        int findNum = cropsGrowthPeriodMapper.findGrowthPeriodCount(cropsGrowthPeriod);
        if(findNum==0)
        {
            int addnum  = cropsGrowthPeriodMapper.addGrowthPeriod(cropsGrowthPeriod);
            if(addnum>0)
            {
                res.setMsg("添加作物生育期成功");
                res.setData(cropsGrowthPeriod.getGrowthId());//返回生成的主键值
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
     * @param cropsGrowthPeriod 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-17 cxw: Created.
     */
    @Override
    public ResultMessage updateGrowthPeriod(CropsGrowthPeriod cropsGrowthPeriod) {
        ResultMessage res = ResultMessage.success();
        //查询要保存数据是否已存在
        int findNum = cropsGrowthPeriodMapper.findGrowthPeriodCount(cropsGrowthPeriod);
        if(findNum==0)
        {
            int updatenum  = cropsGrowthPeriodMapper.updateGrowthPeriod(cropsGrowthPeriod);
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
     * 根据作物编号、区域编号查询对应的物候期
     * @param cropsGrowthPeriodParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     */
    @Override
    public List<CropsGrowthPeriodParam> findGrowthPeriodListByCropsIdAndRegionId(CropsGrowthPeriodParam cropsGrowthPeriodParam) {
        return cropsGrowthPeriodMapper.findGrowthPeriodListByCropsIdAndRegionId(cropsGrowthPeriodParam);
    }

//    /**
//     *根据id查询数据字典数据
//     * @param dict 字典对象
//     * @return DictParam
//     * @version <1> 2018-04-14 lxy: Created.
//     */
//    @Override
//    public ResultMessage findDictById(DictParam dict) {
//        ResultMessage result = new ResultMessage();
//        if(dict.getDictId()!=null){
//            dict = cropsGrowthPeriodMapper.findDictById(dict.getDictId());
//            result.setData(dict);
//        } else {
//            result = ResultMessage.fail();
//            result.setMsg("字典ID不能为空");
//        }
//        return result;
//    }
//
//    /**
//     * 查询子字典数据
//     * @param parentId 字典对象
//     * @return
//     */
//    @Override
//    public ResultMessage queryDictByParentId(Long parentId) {
//        ResultMessage result = new ResultMessage();
//        if(parentId!=null){
//            List<DictParam> list = cropsGrowthPeriodMapper.queryDictByParentId(parentId);
//            result.setData(list);
//        }else{
//            result = ResultMessage.fail();
//            result.setMsg("字典ID不能为空");
//        }
//        return result;
//    }

    /**
     * 查询所有的湿度信息
     * @return List<Humidity>
     * @version <1> 2018-04-14 lxy: Created.
     */
    @Override
    public List<Humidity> queryAllHumidity() {
        return cropsGrowthPeriodMapper.queryAllHumidity();
    }

    /**
     * 查询所有的墒情信息
     * @return List<SoilMoisture>
     * @version <1> 2018-04-14 lxy: Created.
     */
    @Override
    public List<SoilMoisture> queryAllSoilMoisture() {
        return cropsGrowthPeriodMapper.queryAllSoilMoisture();
    }

    /**
     *根据区域，农作物查询生育期
     * @param cropsGrowthPeriodParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-18 cxw： Created.
     */
    @Override
    public ResultMessage findGrowthPeriodList(CropsGrowthPeriodParam cropsGrowthPeriodParam) {
        ResultMessage res = ResultMessage.success();
        List<CropsGrowthPeriod> list = new ArrayList<CropsGrowthPeriod>();
        list =  cropsGrowthPeriodMapper.findGrowthPeriodList(cropsGrowthPeriodParam);
        res.setData(list);
        return  res;
    }

    /**
     * 农作物生育周期配置记录删除
     * @param growthId 农作物生育周期ID
     * @return 返回删除的记录数
     * @version <1> 2018-04-18 cxw： Created.
     */
    @Override
    public ResultMessage deleteCropsGrowthPeriod(Integer growthId) {
        ResultMessage res = ResultMessage.success();
        int  delnum = cropsGrowthPeriodMapper.deleteCropsGrowthPeriod(growthId);
        if(delnum>0) {
            diseaseAllMapper.deleteBatchDiseaseControl(growthId);//删除病虫害
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
        CropsGrowthPeriodParam cropsGrowthPeriod = cropsGrowthPeriodMapper.getGrowthPeriodByGrowthId(growthId);//获得生育期信息
        if(cropsGrowthPeriod == null){
            return ResultMessage.fail("没有找到对应的物候期");
        }
        //查询病害信息
        Map<String,Object> params = new HashMap<>();
        params.put("growthId",growthId);
        params.put("diseaseType",1);
        List<DiseaseControlParam> diseases =  diseaseAllMapper.queryCropsDiseaseByGrowthId(params);
        //查询虫害信息
        params.remove("diseaseType");
        params.put("diseaseType",2);
        List<DiseaseControlParam> bugDiseases =  diseaseAllMapper.queryCropsDiseaseByGrowthId(params);
        //查询对应的生长条件
        List<GrowthRelativeGroundTempParam> growthConditions = growthRelativeGroundTempMapper.queryGrowthRelativeTemp(growthId);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("diseases",diseases);//病害信息
        resultMap.put("bugDiseases",bugDiseases);//虫害信息
        resultMap.put("growthConditions",growthConditions);//生长条件
        resultMap.put("growthPeriod",cropsGrowthPeriod);//生育期
        return ResultMessage.success(resultMap);//返回对应生育期信息
    }

    /**
     * Description: 根据区域id、作物id、时间点 查询生育期
     * 查询的生育期可能没有, 可能多个, 多个时取时间点靠后的生育期
     * @param regionId
     * @param cropsId
     * @param dateStr 格式2018-07-25
     * @return 物候期名称
     * @version <1> 2018/7/25 14:16 zhangshen: Created.
     */
    @Override
    public String getCropsGrowthName(Long regionId, Integer cropsId, String dateStr) {
        String cropType = null;
        List<CropsGrowthPeriod> list = cropsGrowthPeriodMapper.getCropsGrowthPeriodList(regionId, cropsId, dateStr);
        for (int i=0; i<list.size()-1; i++) {//外层循环控制排序趟数
            for(int j=0; j<list.size()-1-i; j++){//内层循环控制每一趟排序多少次
                String day1 = dateStr.substring(0, 4) + "-" + list.get(j).getRangeEnd();
                String day2 = dateStr.substring(0, 4) + "-" + list.get(j+1).getRangeEnd();
                if (list.get(j).getIfspan() == 1) {//跨年
                    day1 = (Integer.parseInt(dateStr.substring(0, 4)) + 1) + "-" + list.get(j).getRangeEnd();
                }
                if (list.get(j+1).getIfspan() == 1) {//跨年
                    day2 = (Integer.parseInt(dateStr.substring(0, 4)) + 1) + "-" + list.get(j+1).getRangeEnd();
                }
                Date date1 = DateUtil.strToDate(day1);
                Date date2 = DateUtil.strToDate(day2);
                if(date1.getTime() < date2.getTime()){
                    list = CollectionUtil.swap1(list, j,j+1);//List元素交换位置
                }
            }
        }
        if (list.size() > 0) {
            cropType = list.get(0).getGrowthName();
            System.out.println("物候期名称:" + cropType);
        }
        return cropType;
    }

}
