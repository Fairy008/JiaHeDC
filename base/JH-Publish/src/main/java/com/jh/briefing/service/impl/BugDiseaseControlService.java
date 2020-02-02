package com.jh.briefing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.briefing.entity.BugDiseaseControl;
import com.jh.briefing.mapping.IBugDiseaseControlMapper;
import com.jh.briefing.model.BugDiseaseControlParam;
import com.jh.briefing.service.IBugDiseaseControlService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**作物虫害防治服务类
 * Created by lxy on 2018/4/13.
 */
@Service
@Transactional
public class BugDiseaseControlService extends BaseServiceImpl<BugDiseaseControlParam,BugDiseaseControl,Integer> implements
        IBugDiseaseControlService{

    @Autowired
    private IBugDiseaseControlMapper bugDiseaseControlMapper;

    @Override
    protected IBaseMapper<BugDiseaseControlParam, BugDiseaseControl, Integer> getDao() {
        return bugDiseaseControlMapper;
    }

    /**
     * 作物害虫防治分页查询
     * @param bugDiseaseControlParam 农作物害虫防治对象
     * @return
     * @version <1> 2018-04-13 lxy： Created.
     * @version <2> 2018-04-19 cxw： update:重写方法
     */
    @Override
    public PageInfo<BugDiseaseControlParam> findBugDiseaseByPage(BugDiseaseControlParam bugDiseaseControlParam) {
        PageHelper.startPage(bugDiseaseControlParam.getPage(), bugDiseaseControlParam.getRows());
        List<BugDiseaseControlParam> list = bugDiseaseControlMapper.findBugDiseaseByPage(bugDiseaseControlParam);
        return new PageInfo<BugDiseaseControlParam>(list);}

    /**
     * 根据物候期编号查询作物虫害和虫害防治信息
     * @param growthId 生育期Id
     * @return 返回List<BugDiseaseControlParam>
     * @version <1> 2018-04-13 lxy： Created.
     */
    @Override
    public List<BugDiseaseControlParam> queryCropsBugDiseaseByGrowthId(Integer growthId) {
        return bugDiseaseControlMapper.queryCropsBugDiseaseByGrowthId(growthId);
    }


    /**
     * 农作物害虫防治信息记录新增
     * @param bugDiseaseControlParam 农作物害虫防治对象
     * @return
     * @version <1> 2018-04-19 cxw： Created.
     */
    @Override
    public ResultMessage addBugDiseaseControl(BugDiseaseControlParam bugDiseaseControlParam) {
        ResultMessage res = ResultMessage.success();
        //查询要保存数据是否已存在
        int findNum = bugDiseaseControlMapper.findBugDiseaseControlCount(bugDiseaseControlParam);
        if(findNum==0)
        {
            int addnum  = bugDiseaseControlMapper.addBugDiseaseControl(bugDiseaseControlParam);
            if(addnum>0)
            {
                res.setMsg("添加农作物害虫防治信息成功");
            }
            else{
                res = ResultMessage.fail();
                res.setMsg("添加农作物害虫防治信息失败");
            }
        }
        else
        {
            res = ResultMessage.fail();
            res.setMsg("不能重复添加农作物害虫防治信息");
        }
        return  res;
    }

    /**
     * 农作物害虫防治信息记录修改
     * @param bugDiseaseControlParam 农作物害虫防治对象
     * @return
     * @version <1> 2018-04-19 cxw: Created.
     */
    @Override
    public ResultMessage updateBugDiseaseControl(BugDiseaseControlParam bugDiseaseControlParam) {
        ResultMessage res = ResultMessage.success();
        //查询要保存数据是否已存在
        int findNum = bugDiseaseControlMapper.findBugDiseaseControlCount(bugDiseaseControlParam);
        if(findNum==0)
        {
            int updatenum  = bugDiseaseControlMapper.updateBugDiseaseControl(bugDiseaseControlParam);
            if(updatenum>0)
            {
                res.setMsg("修改农作物害虫防治信息成功");
            }
            else{
                res = ResultMessage.fail();
                res.setMsg("修改农作物害虫防治信息失败");
            }
        }
        else
        {
            res = ResultMessage.fail();
            res.setMsg("不能重复修改农作物害虫防治信息");
        }
        return  res;
    }

    /**
     * 农作物害虫防治信息记录删除
     * @param diseaseId 农作物害虫防治信息ID
     * @return
     * @version <1> 2018-04-19 cxw： Created.
     */
    @Override
    public ResultMessage deleteBugDiseaseControl(Integer diseaseId) {
        ResultMessage res = ResultMessage.success();
        int  delnum = bugDiseaseControlMapper.deleteBugDiseaseControl(diseaseId);
        if(delnum>0)
        {
            res.setMsg("删除农作物害虫防治信息成功");
        }
        else
        {
            res = ResultMessage.fail();
            res.setMsg("删除农作物害虫防治信息失败");
        }
        return  res;
    }
}
