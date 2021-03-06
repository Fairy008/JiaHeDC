package com.jh.data.service;

import com.github.pagehelper.PageInfo;
import com.jh.data.entity.DsTrmm;
import com.jh.data.model.DsTrmmParam;
import com.jh.data.model.ReportCreateParam;
import com.jh.data.model.TemTrmmTotalDataReturn;
import com.jh.data.model.TtnParam;
import com.jh.vo.ResultMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-06-08 wl: Created.
 */
public interface IDsTrmmService {


    /**
     * 查询地温、降雨明细
     * @param dsTrmmParam
     * @return
     * @version<1> 2018-06-08 wl: Created.
     */
    public PageInfo<DsTrmm> findByPage(DsTrmmParam dsTrmmParam);

    /**
     * @description: 编辑降雨明细
     * @param dsTrmmParam 降雨明细实体
     * @return
     * @version<1> 2018-06-08 wl: Created.
     */
    ResultMessage updateDsTrmm(DsTrmmParam dsTrmmParam);

    /**
     * @description: 根据id查询详细信息
     * @param id  降雨明细主键
     * @return
     * @version<1> 2018-06-08 wl: Created.
     */
    ResultMessage findById(Integer id);

    /**
     * @description: 降雨分布明细
     * @param dsTrmmParam 降雨明细实体
     * @return
     * @version<1> 2018-06-08 wl: Created.
     */
    ResultMessage publish(DsTrmmParam dsTrmmParam);

    /**
     *查询指定时间类上中下旬的雨量和地温的均值以及十年的均值
     * @param param 查询开始和结束时间 、regionId
     * @return
     * @version <1> 2018-3-21 lxy:Created
     */
    PageInfo<TemTrmmTotalDataReturn> findTrmmForTenDaysAndHistory(TtnParam param);

    /**
     *查询指定时间类上中下旬的雨量和地温的均值以及十年的均值
     * @param param 查询开始和结束时间 、regionId
     * @return
     * @version <1> 2018-3-28 cxw:Created
     */
    List<TemTrmmTotalDataReturn> findTrmmListForTenDaysAndHistory(TtnParam param);

    /** 导出查询数据
     * @param regionId 区域ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param regionName 区域名
     * @return
     * @version <1> 2018-06-28 cxw:Created
     */
    ResultMessage exportTrmmData(HttpServletRequest request, HttpServletResponse response, Long regionId, String regionName, String startDate, String endDate) throws IOException, ParseException;


    /** 导出查询数据判断
     * @param regionId 区域ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     * @version <1> 2018-06-28 cxw:Created
     */
    ResultMessage isExistTrmmData(Long regionId, String startDate, String endDate);

    /**
     * 根据区域、作物、精度 查询数据时间列表
     * @param reportCreateParam
     * @return
     * @version<1> 2018-07-18 zhangshen: Created.
     */
    List<DsTrmm> queryDateTimeList(ReportCreateParam reportCreateParam);
}
