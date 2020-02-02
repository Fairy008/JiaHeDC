package com.jh.data.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseDataSetService;
import com.jh.data.entity.DsGrowth;
import com.jh.data.model.DsGrowthParam;
import com.jh.data.model.ReportCreateParam;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 * description:长势明细接口
 *
 * @version <1> 2018-05-09 wl: Created.
 */
public interface IDsGrowthService extends IBaseDataSetService {

    /**
     * 查询长势明细
     * @param dsGrowthParam
     * @return
     * @version<1> 2018-05-09 wl: Created.
     */
    public PageInfo<DsGrowth> findByPage(DsGrowthParam dsGrowthParam);

    /**
     * @description: 编辑长势明细
     * @param dsGrowthParam 长势明细实体
     * @return
     * @version<1> 2018-05-09 wl: Created.
     */
    ResultMessage updateDsGrowth(DsGrowthParam dsGrowthParam);

    /**
     * @description: 根据id查询详细信息
     * @param id  长势明细主键
     * @return
     * @version<1> 2018-05-09 wl: Created.
     */
    ResultMessage findById(Integer id);

    /**
     * 获取长势的报告生成列表
     * @param reportCreateParam
     * @return
     * @version <1> 2018-05-11 zhangshen: Created.
     */
    PageInfo<DsGrowth> findDsGrowthReportCreateData(ReportCreateParam reportCreateParam);

    /**
     * @description: 发布长势明细
     * @param dsGrowthParam 长势明细实体
     * @return
     * @version<1> 2018-05-22 wl: Created.
     */
    ResultMessage publish(DsGrowthParam dsGrowthParam);

    /**
     * 根据区域、作物、精度 查询数据时间列表
     * @param reportCreateParam
     * @return
     * @version<1> 2018-07-18 zhangshen: Created.
     */
    List<DsGrowth> queryDateTimeList(ReportCreateParam reportCreateParam);
}
