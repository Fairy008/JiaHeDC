package com.jh.data.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.data.entity.DsArea;
import com.jh.data.entity.DsGrowth;
import com.jh.data.model.DsAreaParam;
import com.jh.data.model.DsGrowthParam;
import com.jh.data.model.ReportCreateParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * description:长势明细mapper
 *
 * @version <1> 2018-05-08 wl: Created.
 */
@Mapper
public interface IDsGrowthMapper {
    /**
     * @description: 分页查询长势
     * @param dsGrowthParam  长势对象
     * @version <1> 2018-05-09 wl： Created.
     */
    List<DsGrowth> findByPage(DsGrowthParam dsGrowthParam);

    /**
     * @description: 根据生产ID查询长势明细
     * @param dsGrowthParam 长势明细对象
     * @version <1> 2019-02-07 lijie： Created.
     */
    List<DsGrowth> findListByProductId(DsGrowthParam dsGrowthParam);

    /**
     * @description: 更新长势明细
     * @param dsGrowthParam 长势明细对象
     * @return
     * @version <1> 2018-05-09 wl： Created.
     */
    int updateDsGrowth(DsGrowthParam dsGrowthParam);

    /**
     * @description: 根据id查询详细信息
     * @param id 长势明细主键
     * @return
     * @version<1> 2018-05-09 wl: Created.
     */
    DsGrowth findById(Integer id);

    /**
     * 获取长势的报告生成列表
     * @param reportCreateParam
     * @return
     * @version <1> 2018-05-11 zhangshen: Created.
     */
    List<DsGrowth> findDsGrowthReportCreateData(ReportCreateParam reportCreateParam);

    /**
     * @description: 长势明细
     * @param dsGrowthParam 发布人名称  发布人id  发布状态（发布、撤销）
     * @return
     * @version <1> 2018-05-22 wl： Created.
     */
    int publish(DsGrowthParam dsGrowthParam);

    /**
     * 根据区域、作物、精度 查询数据时间列表
     * @param reportCreateParam
     * @return
     * @version<1> 2018-07-18 zhangshen: Created.
     */
    List<DsGrowth> queryDateTimeList(ReportCreateParam reportCreateParam);

    /**
     * 根据数据生产ID删除明细
     * @param productId
     */
    void deleteByProductId(Integer productId);
}
