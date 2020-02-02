package com.jh.briefing.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.briefing.entity.BugDiseaseControl;
import com.jh.briefing.model.BugDiseaseControlParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IBugDiseaseControlMapper extends IBaseMapper<BugDiseaseControlParam,BugDiseaseControl,Integer> {
    /**
     * 作物害虫防治 分页
     * @param bugDiseaseControlParam 农作物害虫防治对象
     * @return List<BugDiseaseControlParam>
     * @version <1> 2018-04-12 lxy： Created.
     * @version <2> 2018-04-19 cxw： update:重写方法
     */
    List<BugDiseaseControlParam> findBugDiseaseByPage(BugDiseaseControlParam bugDiseaseControlParam);

    /**
     * 根据物候期编号查询作物虫害和虫害防治信息
     * @param growthId 生育期Id
     * @return 返回 List<BugDiseaseControlParam>
     * @version <1> 2018-04-12 lxy： Created.
     */
    List<BugDiseaseControlParam> queryCropsBugDiseaseByGrowthId(@Param("growthId") Integer growthId);

    /**
     * 农作物虫害防治信息记录新增
     * @param bugDiseaseControlParam 农作物害虫防治对象
     * @return
     * @version <1> 2018-04-19 cxw： Created.
     */
    public int addBugDiseaseControl(BugDiseaseControlParam bugDiseaseControlParam);

    /**
     * 农作物虫害防治信息记录修改
     * @param bugDiseaseControlParam 农作物害虫防治对象
     * @return
     * @version <1> 2018-04-19 cxw: Created.
     */
    public int updateBugDiseaseControl(BugDiseaseControlParam bugDiseaseControlParam);

    /**
     * 农作物虫害防治信息记录删除
     * @param diseaseId 农作物农病情防治信息ID
     * @return
     * @version <1> 2018-04-19 cxw： Created.
     */
    public  int deleteBugDiseaseControl(Integer diseaseId);

    /**
     * 查询生育配置是否存在
     * @param bugDiseaseControlParam 农作物害虫防治对象
     * @return
     * @version <1> 2018-04-19 cxw: Created.
     */
    public int findBugDiseaseControlCount(BugDiseaseControlParam bugDiseaseControlParam);
}