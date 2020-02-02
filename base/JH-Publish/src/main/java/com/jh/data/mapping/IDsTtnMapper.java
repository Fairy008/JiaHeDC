package com.jh.data.mapping;

import com.jh.data.entity.DsTtn;
import com.jh.data.model.DsTtnParam;
import com.jh.data.model.ReportCreateParam;
import com.jh.data.model.TenDaysTrmmDataReturn;
import com.jh.data.model.TtnParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * 1.地温、干旱、降雨 mapping
 *
 * @version <1> 2018-05-11 15:12 zhangshen: Created.
 */
@Mapper
public interface IDsTtnMapper {

    /**
     * 获取区域内降水Trim、地表温度T、干旱Nddi的报告生成列表
     * @param reportCreateParam
     * @return
     * @version <1> 2018-05-11 zhangshen: Created.
     */
    List<DsTtn> findDsTtnldReportCreateData(ReportCreateParam reportCreateParam);


    /**
     * @description: 分页查询地温 、降雨、干旱
     * @param dsTtnParam  地温 、降雨、干旱对象
     * @version <1> 2018-05-11 wl： Created.
     */
    List<DsTtn> findByPage(DsTtnParam dsTtnParam);

    /**
     * @description: 更新地温 、降雨、干旱明细
     * @param dsTtnParam 地温 、降雨、干旱明细对象
     * @return
     * @version <1> 2018-05-11 wl： Created.
     */
    int updateDsTtn(DsTtnParam dsTtnParam);

    /**
     * @description: 根据id查询详细信息
     * @param id 地温 、降雨、干旱明细主键
     * @return
     * @version<1> 2018-05-11 wl: Created.
     */
    DsTtn findById(Integer id);

    /**
     * @description: 地温降雨干旱明细
     * @param map 发布人名称  发布人id  发布状态（发布、撤销）
     * @return
     * @version <1> 2018-05-22 wl： Created.
     */
    int publish(HashMap<String, Object> map);

    /**
     *查询指定时间类上中下旬的雨量总和和地温的均值
     * @param param
     * @return
     * @version <1> 2018-3-21 lxy:Created
     */
    List<TenDaysTrmmDataReturn> findTrmmAndTempByTenDays(TtnParam param);


    /**
     * 查询历史时间段上中下旬的雨量和地温均值
     * @param param
     * @return
     * @version <1> 2018-3-22 lxy:Created
     */
    List<TenDaysTrmmDataReturn> findHistoryAvgForTrmmAndTempTotal(Map<String, Object> param);

    /**
     * 根据区域、作物、精度 查询数据时间列表
     * @param reportCreateParam
     * @return
     * @version<1> 2018-07-18 zhangshen: Created.
     */
    List<DsTtn> queryDateTimeList(ReportCreateParam reportCreateParam);
}
