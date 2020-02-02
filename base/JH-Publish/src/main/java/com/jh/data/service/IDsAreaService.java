package com.jh.data.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseDataSetService;
import com.jh.data.entity.DsArea;
import com.jh.data.model.DsAreaParam;
import com.jh.data.model.ReportCreateParam;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 * description:分布明细接口
 *
 * @version <1> 2018-05-07 wl: Created.
 */
public interface IDsAreaService extends IBaseDataSetService {

    /**
     * 查询分布明细
     * @param dsAreaParam
     * @return
     * @version<1> 2018-05-07 wl: Created.
     */
    public PageInfo<DsArea> findByPage(DsAreaParam dsAreaParam);

    /**
     * @description: 编辑分布明细
     * @param dsAreaParam 分布明细实体
     * @return
     * @version<1> 2018-05-08 wl: Created.
     */
    ResultMessage updateDsArea(DsAreaParam dsAreaParam);

    /**
     * @description: 根据id查询详细信息
     * @param id  分布明细主键
     * @return
     * @version<1> 2018-05-08 wl: Created.
     */
    ResultMessage findById(Integer id);

    /**
     * 获取分布的报告生成列表
     * @param reportCreateParam
     * @return
     * @version<1> 2018-05-11 zhangshen: Created.
     */
    PageInfo<DsArea> findDsAreaReportCreateData(ReportCreateParam reportCreateParam);

    /**
     * @description: 发布分布明细
     * @param dsAreaParam 分布明细实体
     * @return
     * @version<1> 2018-05-17 wl: Created.
     */
    ResultMessage publish(DsAreaParam dsAreaParam);

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
    List<DsArea> findDsAreaByCondition(ReportCreateParam reportCreateParam, Integer calculationDays);

    /**
     * 根据dsArea查询子级信息
     * @param dsArea
     * @return
     * @version<1> 2018-07-23 zhangshen: Created.
     */
    List<DsArea> getDsAreaListByParent(DsArea dsArea);
}
