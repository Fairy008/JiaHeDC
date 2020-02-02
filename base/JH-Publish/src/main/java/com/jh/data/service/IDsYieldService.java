package com.jh.data.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseDataSetService;
import com.jh.data.entity.DsYield;
import com.jh.data.model.DsYieldParam;
import com.jh.data.model.ReportCreateParam;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 * Description:
 * 1.估产接口
 *
 * @version <1> 2018-05-11 12:42 zhangshen: Created.
 */
public interface IDsYieldService extends IBaseDataSetService {

    /**
     * 获取估产的报告生成列表
     * @param reportCreateParam
     * @return
     * @version <1> 2018-05-11 zhangshen: Created.
     */
    PageInfo<DsYield> findDsYieldReportCreateData(ReportCreateParam reportCreateParam);

    /**
     * 查询估产明细
     * @param dsYieldParam
     * @return
     * @version<1> 2018-05-11 wl: Created.
     */
    public PageInfo<DsYield> findByPage(DsYieldParam dsYieldParam);

    /**
     * @description: 编辑估产明细
     * @param dsYieldParam 估产明细实体
     * @return
     * @version<1> 2018-05-11 wl: Created.
     */
    ResultMessage updateDsYield(DsYieldParam dsYieldParam);

    /**
     * @description: 根据id查询详细信息
     * @param id  估产明细主键
     * @return
     * @version<1> 2018-05-11 wl: Created.
     */
    ResultMessage findById(Integer id);

    /**
     * @description: 估产分布明细
     * @param dsYieldParam 估产明细实体
     * @return
     * @version<1> 2018-05-22 wl: Created.
     */
    ResultMessage publish(DsYieldParam dsYieldParam);

    /**
     * 根据区域、作物、精度 查询数据时间列表
     * @param reportCreateParam
     * @return
     * @version<1> 2018-07-18 zhangshen: Created.
     */
    List<DsYield> queryDateTimeList(ReportCreateParam reportCreateParam);
}
