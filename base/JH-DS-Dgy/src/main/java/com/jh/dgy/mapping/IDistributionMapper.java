package com.jh.dgy.mapping;

import com.jh.dgy.entity.DistributionAndYieldEntity;
import com.jh.dgy.vo.DistributionAndYieldVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * description:分布数据集服务:
 * 1.根据区域、时间，作物，分辨率查询作物分布生成时间
 * 2.根据区域、指定时间查询区域及下一级区域的作物种植面积
 * 3.求指定区域指定起止时间的作物分布均值
 * @version <1> 2018-05-03 cxw: Created.
 */
@Component
@Mapper
public interface IDistributionMapper {

    /*
    * 根据区域、时间，作物，分辨率查询作物分布生成时间
    * @param :param
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    *   resolution: 分辨率
    * @return List<LocalDate>
    * @version <1> 2018-05-23 cxw: Created.
    */
    List<LocalDate> findDistributionTimes(DistributionAndYieldEntity param);

    /*
    * 根据区域、时间，作物，分辨率查询作物分布生成时间
    * @param :param
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    *   resolution: 分辨率
    * @return List<LocalDate>
    * @version <1> 2018-05-23 cxw: Created.
    */
    List<String> findDistributionTimesString(DistributionAndYieldEntity param);

    /*
   * 根据区域、时间查询分布数据
   * @param :
   *   regionId: 区域ID
   *  startDate: 开始日期
   *    endDate: 结束日期
   * @return ResultMessage
   * @version <1> 2019-03-20 cxw: Created.
   */
    List<DistributionAndYieldVo> findAllDistribution(DistributionAndYieldEntity param);

    /*
    * 根据区域、指定时间查询区域及下一级区域的作物种植面积
    * @param param :
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  dataTime: 分布时间
    *   resolution: 分辨率
    * @return List<DistributionAndYieldVo> ：查询时间指定的作物种植面积(指定区域及下一级区域)
    * @version <1> 2018-05-03 cxw: Created.
    */
    List<DistributionAndYieldVo> findDistributionList(DistributionAndYieldEntity param);

    /*
    * 根据区域、指定时间查询区域的作物种植面积
    * @param param :
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  dataTime: 分布时间
    *   resolution: 分辨率
    * @return DistributionAndYieldVo ：查询时间指定的作物种植面积(指定区域)
    * @version <1> 2018-05-29 cxw: Created.
    */
    DistributionAndYieldVo findDistribution(DistributionAndYieldEntity param);


    /**
    * 求指定区域指定起止时间的作物分布均值
    * @param param:
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  dataTime: 分布时间
    *  startDate: 开始日期
    *    endDate: 结束日期
    *   resolution: 分辨率
    * @return List<DistributionAndYieldVo>:
    * @version <1> 2018-05-03 cxw: Created.
    */
    List<DistributionAndYieldVo> findDistributionForAvg(DistributionAndYieldEntity param);

    /**
     * 根据区域、时间查询重庆特定分区当年与前一年区域及下一级区域的作物种植面积数据
     * @param param
     * @return
     */
    List<DistributionAndYieldVo> findBeyondDistributionList(DistributionAndYieldEntity param);

}
