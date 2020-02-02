package com.jh.dgy.service;


import org.springframework.web.bind.annotation.RequestParam;

import com.jh.vo.ResultMessage;

/**
 * 估产数据集服务:
 * 1. 根据区域、时间，作物，分辨率查询作物估产生成时间
 * 2. 根据年份查询区域及子区域作物当年与上一年的估产数据
 * 3. 根据年份查询区域及子区域作物三年的估产及十年的估产均值
 * @version <1> 2018-04-27 cxw: Created.
 */
public interface IYieldService {

	/*
	* 根据区域、时间，作物，分辨率查询作物估产生成时间
	* @param :
	*   regionId: 区域ID
	*     cropId: 作物ID
	*  startDate: 开始日期
	*    endDate: 结束日期
	*   resolution: 分辨率
	* @return ResultMessage
	* @version <1> 2018-05-22 cxw: Created.
	*/
	ResultMessage findYieldTimes(Long regionId,Integer cropId,String startDate,String endDate,Integer resolution);

	/*
	* 根据区域、时间查询所有估产
	* @param :
	*   regionId: 区域ID
	*  startDate: 开始日期
	*    endDate: 结束日期
	* @return ResultMessage
	* @version <1> 2019-03-20 cxw: Created.
	*/
	ResultMessage findAllYield(Long regionId,String startDate,String endDate);

	/*
	* 根据年份查询区域及子区域作物当年与上一年的估产数据
	* @param param :数据集查询参数对象
	*   regionId: 区域ID
	*     cropId: 作物ID
	*  dataTime: 估产时间点
	*   resolution: 分辨率
	* @return ResultMessage ：区域及子区域作物当年与上一年的估产数据
	* @version <1> 2018-04-27 cxw: Created.
	*/
	ResultMessage findYieldForPrevious(Long regionId,Integer cropId,String dataTime,Integer resolution);

	/*
    * 根据年份查询区域及子区域作物三年的估产及十年的估产均值
    * @param param :数据集查询参数对象
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  dataTime: 估产时间点
    *   resolution: 分辨率
    * @return ResultMessage ：区域及子区域作物三年的估产及十年的估产均值
    * @version <1> 2018-04-27 cxw: Created.
    */
	ResultMessage findYieldForThree(Long regionId,Integer cropId,String dataTime,Integer resolution);


	/*
	 * 根据年份查询区域及子区域作物当年的估产数据
	 * @param param :数据集查询参数对象
	 *   regionId: 区域ID
	 *   cropId: 作物ID
	 *   dataTime: 估产时间点
	 *   resolution: 分辨率
	 * @return ResultMessage ：区域及子区域作物当年估产数据
	 * @version <1> 2018-09-29 lcw: Created.
	 */
    ResultMessage findYieldInRegion(Long regionId, Integer cropId, String dataTime, Integer accuracyId,Integer isQueryLargeArea);
    
    
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
	public ResultMessage queryAllYieldByTime(Long regionId,Integer cropId,String startDate,String endDate,Integer resolution);
    
}
