package com.jh.dgy.mapping;

import com.jh.dgy.entity.DistributionAndYieldEntity;
import com.jh.dgy.vo.DistributionAndYieldVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * 估产数据集服务:
 * 1. 根据区域、时间，作物，分辨率查询作物估产生成时间
 * 2. 根据年份查询区域及子区域的作物估产数据
 * 3. 根据年份查询区域及子区域作物当年与上一年的估产数据
 * @version <1> 2018-10-24 huxiaoqiang: Created.
 */
@Component
@Mapper
public interface IYieldMapper {

    /*
    * 根据区域、时间，作物，分辨率查询作物估产生成时间
    * @param :param
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    *   resolution: 分辨率
    * @return List<LocalDate>
    * @version <1> 2018-05-23 huxiaoqiang: Created.
    */
    List<LocalDate> findYieldTimes(DistributionAndYieldEntity param);

    /*
    * 根据区域、时间，作物，分辨率查询作物估产生成时间
    * @param :param
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    *   resolution: 分辨率
    * @return List<LocalDate>
    * @version <1> 2018-05-23 huxiaoqiang: Created.
    */
    List<String> findYieldTimesString(DistributionAndYieldEntity param);


    /*
    * 根据区域、时间查询所有估产
    * @param :
    *   regionId: 区域ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    * @return ResultMessage
    * @version <1> 2019-03-20 cxw: Created.
    */
    List<DistributionAndYieldVo>  findAllYield(DistributionAndYieldEntity param);

    /*
    * 根据年份查询区域及子区域的作物估产数据
    * @param DatasetParam : 
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  dataTime: 估产时间
    *  startDate: 开始日期
    *    endDate: 结束日期
    *   resolution: 分辨率
    * @return ResultMessage ：作物估产数据
    * @version <1> 2018-10-24 huxiaoqiang: Created.
    */
    List<DistributionAndYieldVo> findYieldList(DistributionAndYieldEntity param);

    /*
    * 根据年份查询区域的作物估产数据
    * @param DatasetParam :
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  dataTime: 估产时间
    *   resolution: 分辨率
    * @return ResultMessage ：作物估产数据
    * @version <1> 2018-05-29 huxiaoqiang: Created.
    */
    DistributionAndYieldVo findYield(DistributionAndYieldEntity param);

    /*
    * 根据年份查询区域及子区域作物当年与上一年的估产数据
    * @param DatasetParam : 
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  dataTime: 估产时间
    *  startDate: 开始日期
    *    endDate: 结束日期
    *   resolution: 分辨率
    * @return ResultMessage ：区域及子区域作物当年与上一年的估产数据
    * @version <1> 2018-10-24 huxiaoqiang: Created.
    */
    List<DistributionAndYieldVo> findYieldForAvg(DistributionAndYieldEntity param);

    /*
     * 根据local_name字段分组求分布数据
     * @param DatasetParam :
     *   regionId: 区域ID
     *     cropId: 作物ID
     *  dataTime: 估产时间
     *  startDate: 开始日期
     *    endDate: 结束日期
     *   resolution: 分辨率
     * @return ResultMessage ：作物估产数据
     * @version <1> 2018-10-24 huxiaoqiang: Created.
     */
    List<DistributionAndYieldVo> findYieldBeyond(DistributionAndYieldEntity param);
}
