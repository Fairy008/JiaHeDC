package com.jh.data.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.data.entity.DsGrowth;
import com.jh.data.entity.DsYield;
import com.jh.data.model.DatasetReturn;
import com.jh.data.model.DsGrowthParam;
import com.jh.data.model.DsYieldParam;
import com.jh.data.model.ReportCreateParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description:
 * 1.估产mapper
 *
 * @version <1> 2018-05-11 12:48 zhangshen: Created.
 */
@Mapper
public interface IDsYieldMapper {

    /**
     * 获取估产的报告生成列表
     * @param reportCreateParam
     * @return
     * @version <1> 2018-05-11 zhangshen: Created.
     */
    List<DsYield> findDsYieldReportCreateData(ReportCreateParam reportCreateParam);

    /**
     * @description: 分页查询估产
     * @param dsYieldParam  估产对象
     * @version <1> 2018-05-11 wl： Created.
     */
    List<DsYield> findByPage(DsYieldParam dsYieldParam);

    /**
     * @description: 根据生产ID查询估产明细
     * @param dsYieldParam 估产明细对象
     * @version <1> 2019-02-07 lijie： Created.
     */
    List<DsYield> findListByProductId(DsYieldParam dsYieldParam);

    /**
     * @description: 更新估产明细
     * @param dsYieldParam 估产明细对象
     * @return
     * @version <1> 2018-05-11 wl： Created.
     */
    int updateDsYield(DsYieldParam dsYieldParam);

    /**
     * @description: 根据id查询详细信息
     * @param id 估产明细主键
     * @return
     * @version<1> 2018-05-11 wl: Created.
     */
    DsYield findById(Integer id);

    /**
     * Description: 估产 十年平均值
     * @param reportCreateParam
     * @return
     * @version <1> 2018/5/15 10:38 zhangshen: Created.
     */
    List<DatasetReturn> findYieldForAvg(ReportCreateParam reportCreateParam);

    /**
     * Description: 估产 三年值
     * @param reportCreateParam 
     * @return 
     * @version <1> 2018/5/15 10:40 zhangshen: Created.
     */
    List<DatasetReturn> findYield(ReportCreateParam reportCreateParam);

    /**
     * @description: 估产明细
     * @param dsYieldParam 发布人名称  发布人id  发布状态（发布、撤销）
     * @return
     * @version <1> 2018-05-22 wl： Created.
     */
    int publish(DsYieldParam dsYieldParam);

    /**
     * 根据区域、作物、精度 查询数据时间列表
     * @param reportCreateParam
     * @return
     * @version<1> 2018-07-18 zhangshen: Created.
     */
    List<DsYield> queryDateTimeList(ReportCreateParam reportCreateParam);

    /**
     * 根据数据生产ID删除明细
     * @param productId
     */
    void deleteByProductId(Integer productId);
}
