package com.jh.data.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.data.entity.DsArea;
import com.jh.data.model.DatasetReturn;
import com.jh.data.model.DsAreaParam;
import com.jh.data.model.ReportCreateParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-05-07 wl: Created.
 */
@Mapper
public interface IDsAreaMapper extends IBaseMapper<DsAreaParam, DsArea, Integer> {
    /**
     * @description: 查询分布明细
     * @param dsAreaParam 分布明细对象
     * @version <1> 2018-05-07 wl： Created.
     */
    List<DsArea> findByPage(DsAreaParam dsAreaParam);

    /**
     * @description: 根据生产ID查询分布明细
     * @param dsAreaParam 分布明细对象
     * @version <1> 2019-02-07 lijie： Created.
     */
    List<DsArea> findListByProductId(DsAreaParam dsAreaParam);

    /**
     * @description: 更新分布明细
     * @param dsAreaParam 分布明细对象
     * @return
     * @version <1> 2018-05-08 wl： Created.
     */
    int updateDsArea(DsAreaParam dsAreaParam);

    /**
     * @description: 根据id查询详细信息
     * @param id 分布明细主键
     * @return
     * @version<1> 2018-05-08 wl: Created.
     */
    DsArea findById(Integer id);

    /**
     * 获取分布的报告生成列表
     * @param reportCreateParam
     * @return
     * @version<1> 2018-05-11 zhangshen: Created.
     */
    List<DsArea> findDsAreaReportCreateData(ReportCreateParam reportCreateParam);

    /**
     * Description: 根据数据时间段,查询分布十年平均值
     * @param reportCreateParam 
     * @return 
     * @version <1> 2018/5/14 9:52 zhangshen: Created.
     */
    List<DatasetReturn> findDistributionForAvg(ReportCreateParam reportCreateParam);

    /**
     * Description: 根据数据时间段,查询近三年数据
     * @param reportCreateParam 
     * @return 
     * @version <1> 2018/5/14 10:10 zhangshen: Created.
     */
    List<DatasetReturn> findDistribution(ReportCreateParam reportCreateParam);

    /**
     * @description: 发布分布明细
     * @param dsAreaParam 发布人名称  发布人id  发布状态（发布、撤销）
     * @return
     * @version <1> 2018-05-17 wl： Created.
     */
    int publish(DsAreaParam dsAreaParam);

    /**
     * 根据区域、作物、精度 查询数据时间列表
     * @param reportCreateParam
     * @return
     * @version<1> 2018-07-18 zhangshen: Created.
     */
    List<DsArea> queryDateTimeList(ReportCreateParam reportCreateParam);

    /**
     * 根据区域、作物、精度 查询数据
     * @param reportCreateParam
     * @param calculationDays null为查当前，其他值为同期
     * @return
     * @version<1> 2018-07-18 zhangshen: Created.
     */
    List<DsArea> findDsAreaByCondition(@Param("reportCreateParam") ReportCreateParam reportCreateParam, @Param("calculationDays")Integer calculationDays);

    /**
     * 根据dsArea查询子级信息
     * @param dsArea
     * @return
     * @version<1> 2018-07-23 zhangshen: Created.
     */
    List<DsArea> getDsAreaListByParent(DsArea dsArea);

    /**
     * 根据数据生产ID删除明细
     * @param productId
     */
    void deleteByProductId(Integer productId);
}
