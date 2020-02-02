package com.jh.briefing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.briefing.entity.GrowthRelativeTempManager;
import com.jh.briefing.mapping.IGrowthRelativeTempManagerMapper;
import com.jh.briefing.model.GrowthRelativeTempManagerParam;
import com.jh.briefing.service.IGrowthRelativeTempManagerService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 作物生长情况与地温关系服务实现类
 * @version <1> 2018-05-23 lxy： Created.
 */
@Service
@Transactional
public class GrowthRelativeTempManagerService extends BaseServiceImpl<GrowthRelativeTempManagerParam,GrowthRelativeTempManager
        ,Integer> implements IGrowthRelativeTempManagerService{

    @Autowired
    private IGrowthRelativeTempManagerMapper growthRelativeTempManagerMapper;

    @Override
    protected IBaseMapper<GrowthRelativeTempManagerParam, GrowthRelativeTempManager, Integer> getDao() {
        return growthRelativeTempManagerMapper;
    }


    /**
     * 新增物候期地温关系数据
     * @param GrowthRelativeTempManager
     * @return
     */
    public ResultMessage saveGrowthRelativeTempManager(GrowthRelativeTempManager GrowthRelativeTempManager){
        ResultMessage res = ResultMessage.success();
        int num =  growthRelativeTempManagerMapper.saveGrowthRelativeTempManager(GrowthRelativeTempManager);
        if(num>0){
            res.setMsg("新增物候期地温关系信息成功");
        }else {
            res = ResultMessage.fail();
            res.setMsg("新增物候期地温关系信息失败");
        }
        return  res;
    }

    /**
     * 删除物候期地温关系数据
     * @param relativeId
     * @return
     * @version <1> 2018-04-17 lxy： Created.
     */

    /**
     * 根据主键查询物候期地温关系数据
     * @param relativeId
     * @return
     */
    public ResultMessage findGrowthRelativeTempById(Integer relativeId){
        GrowthRelativeTempManagerParam groundTemp = growthRelativeTempManagerMapper.findGrowthRelativeTempById(relativeId);
        if(groundTemp!=null){
            return ResultMessage.success(groundTemp);
        }else{
            return ResultMessage.fail();
        }
    }

    @Override
    public ResultMessage deleteGrowthRelativeTemp(Integer relativeId) {
        ResultMessage res = ResultMessage.success();
        int num =  growthRelativeTempManagerMapper.deleteGrowthRelativeTemp(relativeId);
        if(num>0) {
            res.setMsg("删除物候期地温关系信息成功");
        } else {
            res = ResultMessage.fail();
            res.setMsg("删除物候期地温关系信息失败");
        }
        return  res;
    }
    /**
     *  批量保存作物地温生长条件
     * @param growthConditions 作物地温生长条件集合
     * @return 操作结果
     * @version <1> 2018-05-23 lxy： Created.
     */
    @Override
    public ResultMessage saveBatchGrowthRelativeTempManager(List<GrowthRelativeTempManagerParam> growthConditions) {
        int effectNum = growthRelativeTempManagerMapper.saveBatchGrowthRelativeTempManager(growthConditions);
        if(effectNum>0){
            return ResultMessage.success();
        }
        return ResultMessage.fail();
    }

    /**
     * 根据作物生育期编号，批量删除对应的地温条件数据。
     * @param growthId 作物生育期编号
     * @return 删除的条数
     * @version <1> 2018-05-23 lxy： Created.
     */
    public ResultMessage deleteBatchGrowthConditionsByGrowthId(Integer growthId){
        int effectNum = growthRelativeTempManagerMapper.deleteBatchGrowthConditionsByGrowthId(growthId);
        if(effectNum>0){
            return ResultMessage.success();
        }
        return ResultMessage.fail();
    }

    /**
     * 修改物候期地温关系数据
     * @param GrowthRelativeTempManager 地温条件数据
     * @return
     */
    public ResultMessage updateGrowthRelativeTempManager(GrowthRelativeTempManager GrowthRelativeTempManager){
        ResultMessage res = ResultMessage.success();
        int num =  growthRelativeTempManagerMapper.updateGrowthRelativeTempManager(GrowthRelativeTempManager);
        if(num>0) {
            res.setMsg("修改物候期地温关系信息成功");
        } else {
            res = ResultMessage.fail();
            res.setMsg("修改物候期地温关系信息失败");
        }
        return  res;
    }

    /**
     * 分页信息
     * @param GrowthRelativeTempManagerParam 查询参数
     * @return 返回PageInfo<GrowthRelativeTempManagerParam>
     */
    @Override
    public PageInfo<GrowthRelativeTempManagerParam> findByPage(GrowthRelativeTempManagerParam GrowthRelativeTempManagerParam) {
        PageHelper.startPage(GrowthRelativeTempManagerParam.getPage(), GrowthRelativeTempManagerParam.getRows());
        List<GrowthRelativeTempManagerParam> list = growthRelativeTempManagerMapper.queryByPage(GrowthRelativeTempManagerParam);
        return new PageInfo<GrowthRelativeTempManagerParam>(list);
    }

    /**
     * 根据growthId（物候期编号）查询对应生长与地温关系信息列表
     * @param growthId
     * @return List<GrowthRelativeTempManagerParam>
     */
    @Override
    public List<GrowthRelativeTempManagerParam> queryGrowthRelativeTemp(Integer growthId) {
        return growthRelativeTempManagerMapper.queryGrowthRelativeTemp(growthId);
    }
}