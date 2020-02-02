package com.jh.briefing.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.briefing.entity.CropsGrowthPeriod;
import com.jh.briefing.model.CropsGrowthPeriodParam;
import com.jh.briefing.model.DiseaseControlParam;
import com.jh.briefing.model.GrowthData;
import com.jh.briefing.model.GrowthRelativeGroundTempParam;
import com.jh.briefing.service.ICropsGrowthPeriodService;
import com.jh.briefing.service.IDiseaseControlService;
import com.jh.briefing.service.IGrowthRelativeGroundTempService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * description:农作物生育期 Controller类
 *1.根据区域，作物新增生育期
 * @version <1> 2018-04-12 lxy： Created.
 * @version <2> 2018-04-17 cxw： update:1.修改增删改查方法；2.更新完善注释.
 */
@RestController
@RequestMapping("/growthPeriod")
public class CropsGrowthPeriodController extends BaseController {

    @Autowired
    private ICropsGrowthPeriodService cropsGrowthPeriodService;
    @Autowired
    private IDiseaseControlService diseaseControlService;
    @Autowired
    private IGrowthRelativeGroundTempService growthConditionService;

    /**
     * 作物生育周期分页查询
     * @param cropsGrowthPeriodParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-17 cxw： update:重写方法
     */
    @ApiOperation(value = "农作物生育周期分页查询",notes = "农作物生育周期配置信息列表")
    @ApiImplicitParam(name = "cropsGrowthPeriodParam",value = "农作物生育周期配置参数对象",required = false,dataType = "CropsGrowthPeriodParam")
    @PostMapping("findByPage")
    public PageInfo<CropsGrowthPeriodParam> findCropsGrowthPeriodByPage(CropsGrowthPeriodParam cropsGrowthPeriodParam){
        return cropsGrowthPeriodService.findCropsGrowthPeriodByPage(cropsGrowthPeriodParam);
    }

    /**
     * 农作物生育周期配置记录新增
     * @param growthData 生育期组合数据
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-17 cxw： update:重写方法
     */
    @ApiOperation(value = "新增农作物生育周期配置参数信息",notes = "新增农作物生育周期配置参数信息")
    @ApiImplicitParam(name = "cropsGrowthPeriod",value = "农作物生育周期配置参数对象",required = false,dataType = "cropsGrowthPeriod")
    @PostMapping("add")
    public ResultMessage add(@RequestBody GrowthData growthData){
        //默认访问错误信息
        ResultMessage result = ResultMessage.fail();
        //保存生育期
        CropsGrowthPeriod cropsGrowthPeriod = growthData.getGrowthPeriod();
        result = cropsGrowthPeriodService.addGrowthPeriod(cropsGrowthPeriod);
        //保存成功
        if(result.isFlag()){
            Integer growthId = (Integer) result.getData();//获得保存生育期数据后，返回的主键，下面就可以用了
            saveGrowthData(growthData, growthId,false);//农作物生育周期配置记录保存
            return ResultMessage.success();
        }
        return result;
    }

    /**
     * 农作物生育周期配置记录保存
     * @param growthData 生育期组合数据
     * @param growthId 生育期编号
     * @param isUpdate 是否为修改标志
     * @version <1> 2018-05-23 lxy： Created.
     */
    private void saveGrowthData(GrowthData growthData, Integer growthId,boolean isUpdate) {
        List<DiseaseControlParam> diseases =  growthData.getDiseases();//获取前台端提交的病虫害防治措施
        //用户填了病虫害信息，才可以保存
        if(diseases != null && diseases.size()>0){
            for(DiseaseControlParam diseaseControlParam : diseases){
                diseaseControlParam.setGrowthId(growthId);//设置growthId编号
            }
            if(isUpdate){//为修改标志，才删除旧数据
                //先根据生育期编号删除旧的数据
                diseaseControlService.deleteBatchDiseaseControl(growthId);
            }
            //保存病虫信息
            diseaseControlService.addBatchDiseaseControl(diseases);
        }
        //保存 地温条件关系
        List<GrowthRelativeGroundTempParam> growthConditions = growthData.getGrowthConditions();
        if(growthConditions!=null && growthConditions.size()>0){
            for(GrowthRelativeGroundTempParam groundTempParam : growthConditions){
                groundTempParam.setGrowthId(growthId);//设置growthId编号
            }
            if(isUpdate){
                //先根据生育期编号，删除旧的数据。
                growthConditionService.deleteBatchGrowthConditionsByGrowthId(growthId);
            }
            growthConditionService.saveBatchGrowthRelativeGroundTemp(growthConditions);
        }
    }


    /**
     * 根据ID获取农作物生育周期配置对象
     * @param growthId 生育期编号
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-17 cxw： update:重写方法
     */
    @ApiOperation(value = "根据ID获取农作物生育周期配置对象",notes = "根据ID获取农作物生育周期配置对象")
    @ApiImplicitParam(name = "cropsGrowthPeriod",value = "农作物生育周期配置对象",required = true,dataType = "CropsGrowthPeriod")
    @PostMapping("getById")
    public ResultMessage getById(Integer growthId){
        return cropsGrowthPeriodService.findGrowthDataByGrowthId(growthId);
    }

    /**
     * 农作物生育周期配置记录修改
     * @param growthData 农作物生育周期配置对象
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     */
    @ApiOperation(value = "农作物生育周期配置记录修改",notes = "农作物生育周期配置记录修改")
    @ApiImplicitParam(name = "cropsGrowthPeriod",value = "农作物生育周期配置参数对象",required = false,dataType = "CropsGrowthPeriod")
    @PostMapping("update")
    public ResultMessage update(@RequestBody GrowthData growthData){
        int growthId = growthData.getGrowthId();//获取生育期编号
        CropsGrowthPeriod cropsGrowthPeriod = growthData.getGrowthPeriod();//获取前端提交过来的生育期数据
        cropsGrowthPeriod.setGrowthId(growthId);//生育期编号，用于修改
        ResultMessage resultMessage = cropsGrowthPeriodService.updateGrowthPeriod(cropsGrowthPeriod);//更新生育期数据
        if(resultMessage.isFlag()){
            saveGrowthData(growthData, growthId,true);//农作物生育周期配置记录保存
            return ResultMessage.success();
        }
        return resultMessage;
    }

    /**
     * 农作物生育周期配置记录删除
     * @param growthId 农作物生育周期ID
     * @return
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-17 cxw： update:1.将农作物生育周期对象参数更换为农作物生育周期ID；2.重写方法
     */
    @ApiOperation(value = "删除农作物生育周期配置",notes = "删除农作物生育周期配置")
    @ApiImplicitParam(name = "growthId",value = "农作物生育周期ID",required = false,dataType = "Integer")
    @PostMapping("delete")
    public ResultMessage delete(@RequestParam Integer growthId){
        return  cropsGrowthPeriodService.deleteCropsGrowthPeriod(growthId);
    }

    /**
     *根据区域，农作物查询生育期
     * @param cropsGrowthPeriodParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-18 cxw： Created.
     */
    @ApiOperation(value = "农作物生育周期查询",notes = "农作物生育周期查询")
    @ApiImplicitParam(name = "cropsGrowthPeriodParam",value = "农作物生育周期配置配置对象",required = false,dataType = "CropsGrowthPeriodParam")
    @PostMapping("queryGrowthPeriodList")
    public ResultMessage queryGrowthPeriodList(@RequestBody CropsGrowthPeriodParam cropsGrowthPeriodParam){
        return cropsGrowthPeriodService.findGrowthPeriodList(cropsGrowthPeriodParam);
    }
}
