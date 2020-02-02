package com.jh.briefing.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseService;
import com.jh.briefing.entity.BugDiseaseControl;
import com.jh.briefing.model.BugDiseaseControlParam;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 *  作物虫害防治服务接口类
 * Created by lxy on 2018/4/13.
 */
public interface IBugDiseaseControlService extends IBaseService<BugDiseaseControlParam,BugDiseaseControl,Integer> {
    /**
     * 作物虫害防治信息分页查询
     * @param bugDiseaseControlParam 农作物害虫防治对象
     * @return
     * @version <1> 2018-04-13 lxy： Created.
     * @version <2> 2018-04-19 cxw： update:重写方法
     */
    PageInfo<BugDiseaseControlParam> findBugDiseaseByPage(BugDiseaseControlParam bugDiseaseControlParam);

    /**
     * 根据物候期编号查询作物虫害和虫害防治信息
     * @param growthId 生育期Id
     * @return 返回List<BugDiseaseControlParam>
     * @version <1> 2018-04-13 lxy： Created.
     */
    List<BugDiseaseControlParam> queryCropsBugDiseaseByGrowthId(Integer growthId);

    /**
     * 农作物害虫防治信息记录新增
     * @param bugDiseaseControlParam 农作物害虫防治对象
     * @return
     * @version <1> 2018-04-19 cxw： Created.
     */
    public ResultMessage addBugDiseaseControl(BugDiseaseControlParam bugDiseaseControlParam);

    /**
     * 农作物害虫防治信息记录修改
     * @param bugDiseaseControlParam 农作物害虫防治对象
     * @return
     * @version <1> 2018-04-19 cxw: Created.
     */
    public ResultMessage updateBugDiseaseControl(BugDiseaseControlParam bugDiseaseControlParam);

    /**
     * 农作物害虫防治信息记录删除
     * @param bugId 农作物害虫防治信息ID
     * @return
     * @version <1> 2018-04-19 cxw： Created.
     */
    public  ResultMessage deleteBugDiseaseControl(Integer bugId);

}
