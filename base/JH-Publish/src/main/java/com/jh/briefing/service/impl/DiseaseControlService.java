package com.jh.briefing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.briefing.entity.DiseaseControl;
import com.jh.briefing.mapping.IDiseaseAllMapper;
import com.jh.briefing.mapping.IDiseaseControlMapper;
import com.jh.briefing.model.DiseaseControlParam;
import com.jh.briefing.service.IDiseaseControlService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lxy on 2018/4/12.
 */
@Service
@Transactional
public class DiseaseControlService extends BaseServiceImpl<DiseaseControlParam,DiseaseControl,Integer> implements
        IDiseaseControlService{

    @Autowired
    private IDiseaseControlMapper diseaseControlMapper;
    @Autowired
    private IDiseaseAllMapper diseaseAllMapper;

    @Override
    protected IBaseMapper<DiseaseControlParam, DiseaseControl, Integer> getDao() {
        return diseaseControlMapper;
    }

    /**
     * 作物生病防治信息分页查询
     * @param diseaseControlParam 农作物病情防治对象
     * @return
     * @version <1> 2018-04-13 lxy： Created.
     * @version <2> 2018-04-18 cxw： update:重写方法
     */
    @Override
    public PageInfo<DiseaseControlParam> findDiseaseByPage(DiseaseControlParam diseaseControlParam) {
        PageHelper.startPage(diseaseControlParam.getPage(), diseaseControlParam.getRows());
        List<DiseaseControlParam> list = diseaseControlMapper.findDiseaseByPage(diseaseControlParam);
        return new PageInfo<DiseaseControlParam>(list);
    }

    /**
     * 根据物候期编号查询对应的物候期病情和病情防治措施
     * @param growthId 作物编号
     * @param diseaseType 病虫害类型
     * @return 对应的病虫害信息
     * @version <1> 2018-04-13 lxy： Created.
     */
    @Override
    public List<DiseaseControlParam> queryCropsDiseaseByGrowthId(Integer growthId, Integer diseaseType) {
        Map<String,Object> param = new HashMap<>();
        param.put("growthId",growthId);
        param.put("diseaseType",diseaseType);
        return diseaseAllMapper.queryCropsDiseaseByGrowthId(param);
    }

    /**
     * 农作物病情防治信息记录新增
     * @param diseaseControlParam
     * @return
     * @version <1> 2018-04-18 cxw： Created.
     */
    @Override
    public ResultMessage addDiseaseControl(DiseaseControlParam diseaseControlParam) {
        ResultMessage res = ResultMessage.success();
        //查询要保存数据是否已存在
        int findNum = diseaseControlMapper.findDiseaseControlCount(diseaseControlParam);
        if(findNum==0)
        {
            int addnum  = diseaseControlMapper.addDiseaseControl(diseaseControlParam);
            if(addnum>0)
            {
                res.setMsg("添加农作物病情防治信息成功");
            }
            else{
                res = ResultMessage.fail();
                res.setMsg("添加农作物病情防治信息失败");
            }
        }
        else
        {
            res = ResultMessage.fail();
            res.setMsg("不能重复添加农作物病情防治信息");
        }
        return  res;
    }

    /**
     * 农作物病情防治信息记录修改
     * @param diseaseControlParam
     * @return
     * @version <1> 2018-04-18 cxw: Created.
     */
    @Override
    public ResultMessage updateDiseaseControl(DiseaseControlParam diseaseControlParam) {
        ResultMessage res = ResultMessage.success();
        //查询要保存数据是否已存在
        int findNum = diseaseControlMapper.findDiseaseControlCount(diseaseControlParam);
        if(findNum==0)
        {
            int updatenum  = diseaseControlMapper.updateDiseaseControl(diseaseControlParam);
            if(updatenum>0)
            {
                res.setMsg("修改农作物病情防治信息成功");
            }
            else{
                res = ResultMessage.fail();
                res.setMsg("修改农作物病情防治信息失败");
            }
        }
        else
        {
            res = ResultMessage.fail();
            res.setMsg("不能重复修改农作物病情防治信息");
        }
        return  res;
    }

    /**
     * 农作物农病情防治信息记录删除
     * @param diseaseId 农作物农病情防治信息ID
     * @return
     * @version <1> 2018-04-18 cxw： Created.
     */
    @Override
    public ResultMessage deleteDiseaseControl(Integer diseaseId) {
        ResultMessage res = ResultMessage.success();
        int  delnum = diseaseControlMapper.deleteDiseaseControl(diseaseId);
        if(delnum>0)
        {
            res.setMsg("删除农作物病情防治信息成功");
        }
        else
        {
            res = ResultMessage.fail();
            res.setMsg("删除农作物病情防治信息失败");
        }
        return  res;
    }

    /**
     * 农作物病情防治信息记录新增
     * @param diseases 新增记录集合
     * @return 操作的结果
     * @version <1> 2018-05-22 lxy： Created.
     */
    @Override
    public ResultMessage addBatchDiseaseControl(List<DiseaseControlParam> diseases) {
        try{
            diseaseAllMapper.addBatchDiseaseControl(diseases);
        }catch(Exception e){
            e.printStackTrace();
            return ResultMessage.fail(e.getMessage());
        }

        return ResultMessage.success();
    }

    /**
     * 农作物农病情防治信息记录删除
     * @param growthId 农作物生育期编号
     * @return 返回删除记录数
     * @version <1> 2018-05-23 lxy： Created.
     */
    @Override
    public int deleteBatchDiseaseControl(Integer growthId) {
        return diseaseAllMapper.deleteBatchDiseaseControl(growthId);
    }
}
