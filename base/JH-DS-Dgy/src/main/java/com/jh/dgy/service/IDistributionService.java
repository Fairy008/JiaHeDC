package com.jh.dgy.service;

import com.jh.vo.ResultMessage;

/**
 * description:分布数据集服务: 1.根据区域、时间，作物，分辨率查询作物分布生成时间
 * 2.根据区域、时间查询当年与前一年区域及下一级区域的作物种植面积数据 3.根据区域、时间查询近三年区域及下一级区域的作物种植面积
 * 
 * @version <1> 2018-04-27 cxw: Created.
 */
public interface IDistributionService {

	/*
	 * 根据区域、时间，作物，分辨率查询作物分布生成时间
	 * 
	 * @param : regionId: 区域ID cropId: 作物ID startDate: 开始日期 endDate: 结束日期
	 * resolution: 分辨率
	 * 
	 * @return ResultMessage
	 * 
	 * @version <1> 2018-05-22 cxw: Created.
	 */
	ResultMessage findDistributionTimes(Long regionId, Integer cropId, String startDate, String endDate,
			Integer resolution);

	/*
	 * 根据区域、时间查询分布数据
	 * @param :
	 *   regionId: 区域ID
	 *  startDate: 开始日期
	 *    endDate: 结束日期
	 * @return ResultMessage
	 * @version <1> 2019-03-20 cxw: Created.
	 */
	public ResultMessage findAllDistribution(Long regionId,String startDate,String endDate);

	/*
	 * 根据区域、时间查询当年与前一年区域及下一级区域的作物种植面积数据 regionId: 区域ID cropId: 作物ID dataTime:
	 * 分布时间 resolution: 分辨率
	 * 
	 * @return ResultMessage
	 * 
	 * @version <1> 2018-04-27 27: Created.
	 */
	ResultMessage findDistributionForPrevious(Long regionId, Integer cropId, String dataTime, Integer resolution,Integer isQueryLargeArea);

	/*
	 * 根据区域、时间查询近三年区域及下一级区域的作物种植面积
	 * 
	 * @param : regionId: 区域ID cropId: 作物ID dataTime: 分布时间 resolution: 分辨率
	 * 
	 * @return ResultMessage
	 * 
	 * @version <1> 2018-04-27 cxw: Created.
	 */
	ResultMessage findDistributionForThree(Long regionId, Integer cropId, String dataTime, Integer resolution);

	/*
	 * 根据区域、作物，时间，精度查询重庆市特定分区下在重庆市的百分占比
	 *  regionId: 区域ID
	 *  cropId: 作物ID
	 *  dataTime: 分布时间点
	 *  accuracyId: 精度
	 * @return ResultMessage
	 * @version <1> 2018-10-30 liyb: Created.
	 */
	
	ResultMessage findRegionPercentage(Long regionId, Integer cropId, String dataTime, Integer resolution);

	/*
	  * 根据区域、时间，作物，分辨率查询作物分布
	  * @param :
	  *   regionId: 区域ID
	  *     cropId: 作物ID
	  *  startDate: 开始日期
	  *    endDate: 结束日期
	  *   resolution: 分辨率
	  * @return ResultMessage
	  * @version <1> 2019-03-21 cxw: Created.
	  */
	public ResultMessage queryAllDistributionByTime(Long regionId,Integer cropId,String startDate,String endDate,Integer resolution);

}
