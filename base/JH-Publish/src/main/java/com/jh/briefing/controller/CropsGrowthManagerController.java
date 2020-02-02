package com.jh.briefing.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.briefing.entity.CropsGrowthManager;
import com.jh.briefing.entity.CropsGrowthPeriod;
import com.jh.briefing.model.*;
import com.jh.briefing.service.*;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * description:农作物生育期配置管理 Controller类
 *1.根据区域，作物新增生育期
 * @version <1> 2018-08-10 lj： Created.
 */
@RestController
@RequestMapping("/growthManager")
public class CropsGrowthManagerController extends BaseController {

    @Autowired
    private ICropsGrowthManagerService cropsGrowthManagerService;
    @Autowired
    private IDiseaseControlManagerService diseaseControlManagerService;
    @Autowired
    private IGrowthRelativeTempManagerService growthRelativeTempManagerService;

    /**
     * 作物生育周期分页查询
     * @param cropsGrowthManagerParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-12 lj： Created.
     */
    @ApiOperation(value = "农作物生育周期分页查询",notes = "农作物生育周期配置信息列表")
    @ApiImplicitParam(name = "cropsGrowthManagerParam",value = "农作物生育周期配置参数对象",required = false,dataType = "CropsGrowthManagerParam")
    @PostMapping("findByPage")
    public PageInfo<CropsGrowthManagerParam> findCropsGrowthManagerByPage(CropsGrowthManagerParam cropsGrowthManagerParam){
        return cropsGrowthManagerService.findCropsGrowthManagerByPage(cropsGrowthManagerParam);
    }

    /**
     * 作物生育周期不分页查询
     * @param cropsGrowthManagerParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-04-12 lj： Created.
     */
    @ApiOperation(value = "农作物生育周期不分页查询",notes = "农作物生育周期配置信息列表")
    @ApiImplicitParam(name = "cropsGrowthManagerParam",value = "农作物生育周期配置参数对象",required = false,dataType = "CropsGrowthManagerParam")
    @PostMapping("findList")
    public ResultMessage findCropsGrowthManagerList(CropsGrowthManagerParam cropsGrowthManagerParam){
        //默认访问错误信息
        ResultMessage result = ResultMessage.success();
        List<CropsGrowthManagerParam>list=cropsGrowthManagerService.findCropsGrowthManagerList(cropsGrowthManagerParam);
        result.setData(list);
        return result;
    }

    /**
     * 农作物生育周期配置记录新增
     * @param growthManagerData 生育期组合数据
     * @version <1> 2018-04-12 lj： Created.
     */
    @ApiOperation(value = "新增农作物生育周期配置参数信息",notes = "新增农作物生育周期配置参数信息")
    @ApiImplicitParam(name = "cropsGrowthManager",value = "农作物生育周期配置参数对象",required = false,dataType = "cropsGrowthManager")
    @PostMapping("add")
    public ResultMessage add(@RequestBody GrowthManagerData growthManagerData){
        //默认访问错误信息
        ResultMessage result = ResultMessage.fail();
        //保存生育期
        CropsGrowthManager cropsGrowthManager = growthManagerData.getGrowthManager();
        cropsGrowthManager.setCreator(this.getCurrentAccountId());
        result = cropsGrowthManagerService.addGrowthManager(cropsGrowthManager);
        //保存成功
        if(result.isFlag()){
            Integer growthId = (Integer) result.getData();//获得保存生育期数据后，返回的主键，下面就可以用了
            saveGrowthManagerData(growthManagerData, growthId,false);//农作物生育周期配置记录保存
            return ResultMessage.success();
        }
        return result;
    }

    /**
     * 农作物生育周期配置记录保存
     * @param GrowthManagerData 生育期组合数据
     * @param growthId 生育期编号
     * @param isUpdate 是否为修改标志
     * @version <1> 2018-05-23 lj： Created.
     */
    private void saveGrowthManagerData(GrowthManagerData GrowthManagerData, Integer growthId,boolean isUpdate) {
        if(isUpdate){//为修改标志，才删除旧数据
            //先根据生育期编号删除旧的数据
            diseaseControlManagerService.deleteBatchDiseaseControl(growthId);
        }
        if(isUpdate){
            //先根据生育期编号，删除旧的数据。
            growthRelativeTempManagerService.deleteBatchGrowthConditionsByGrowthId(growthId);
        }
        List<DiseaseControlManagerParam> diseases =  GrowthManagerData.getDiseases();//获取前台端提交的病虫害防治措施
        //用户填了病虫害信息，才可以保存
        if(diseases != null && diseases.size()>0){
            for(DiseaseControlManagerParam DiseaseControlManagerParam : diseases){
                DiseaseControlManagerParam.setGrowthId(growthId);//设置growthId编号
            }
            //保存病虫信息
            diseaseControlManagerService.addBatchDiseaseControl(diseases);
        }
        //保存 地温条件关系
        List<GrowthRelativeTempManagerParam> growthConditions = GrowthManagerData.getGrowthConditions();
        if(growthConditions!=null && growthConditions.size()>0){
            for(GrowthRelativeTempManagerParam groundTempParam : growthConditions){
                groundTempParam.setGrowthId(growthId);//设置growthId编号
            }
            growthRelativeTempManagerService.saveBatchGrowthRelativeTempManager(growthConditions);
        }
    }


    /**
     * 根据ID获取农作物生育周期配置对象
     * @param growthId 生育期编号
     * @return
     * @version <1> 2018-04-12 lj： Created.
     */
    @ApiOperation(value = "根据ID获取农作物生育周期配置对象",notes = "根据ID获取农作物生育周期配置对象")
    @ApiImplicitParam(name = "cropsGrowthManager",value = "农作物生育周期配置对象",required = true,dataType = "CropsGrowthManager")
    @PostMapping("getById")
    public ResultMessage getById(Integer growthId){
        return cropsGrowthManagerService.findGrowthDataByGrowthId(growthId);
    }

    /**
     * 农作物生育周期配置记录修改
     * @param GrowthManagerData 农作物生育周期配置对象
     * @return
     * @version <1> 2018-04-12 lj： Created.
     */
    @ApiOperation(value = "农作物生育周期配置记录修改",notes = "农作物生育周期配置记录修改")
    @ApiImplicitParam(name = "cropsGrowthManager",value = "农作物生育周期配置参数对象",required = false,dataType = "CropsGrowthManager")
    @PostMapping("update")
    public ResultMessage update(@RequestBody GrowthManagerData GrowthManagerData){
        int growthId = GrowthManagerData.getGrowthId();//获取生育期编号
        CropsGrowthManager cropsGrowthManager = GrowthManagerData.getGrowthManager();//获取前端提交过来的生育期数据
        cropsGrowthManager.setId(growthId);//生育期编号，用于修改
        cropsGrowthManager.setModifier(this.getCurrentAccountId());
        ResultMessage resultMessage = cropsGrowthManagerService.updateGrowthManager(cropsGrowthManager);//更新生育期数据
        if(resultMessage.isFlag()){
            saveGrowthManagerData(GrowthManagerData, growthId,true);//农作物生育周期配置记录保存
            return ResultMessage.success();
        }
        return resultMessage;
    }

    /**
     * 农作物生育周期配置记录删除
     * @param growthId 农作物生育周期ID
     * @return
     * @version <1> 2018-04-12 lj： Created.
     */
    @ApiOperation(value = "删除农作物生育周期配置",notes = "删除农作物生育周期配置")
    @ApiImplicitParam(name = "growthId",value = "农作物生育周期ID",required = false,dataType = "Integer")
    @PostMapping("delete")
    public ResultMessage delete(@RequestParam Integer growthId){
        return  cropsGrowthManagerService.deleteCropsGrowthManager(growthId);
    }

}
