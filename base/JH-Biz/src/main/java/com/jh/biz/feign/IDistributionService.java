package com.jh.biz.feign;
import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description:分布数据集服务:
 * 1.根据区域、时间，作物，分辨率查询作物分布时间
 * 2.根据区域、作物，时间，精度查询当年与前一年区域及下一级区域的作物种植面积数据
 * 3.根据区域、作物，时间，精度查询当前区域及下一级区域的作物种植面积近三年及历年10年均值
 * @version <1> 2018-08-10 cxw: Created.
 */
@FeignClient(name="jh-ds-dgy")
public interface IDistributionService{ 
	/**
	* 根据区域、时间，作物，分辨率查询作物分布时间点
	* @version Hayden 2018-08-07 16:18:10 : Created.
	*/
	@GetMapping(value = "/distribution/queryDistributionTimes")
	public ResultMessage queryDistributionTimes(
						@RequestParam(name="regionId")Long regionId,
						@RequestParam(name="cropId")Integer cropId,
						@RequestParam(name="startDate")String startDate,
						@RequestParam(name="endDate")String endDate,
						@RequestParam(name="resolution")Integer resolution
	);

	/*
	 * 根据区域、作物，时间，精度查询当年与前一年区域及下一级区域的作物种植面积数据
	 * regionId: 区域ID
	 * cropId: 作物ID
	 * dataTime: 分布时间点
	 *  accuracyId: 精度
	 * @return ResultMessage
	 * @version <1> 2018-08-10 cxw: Created.
	 */
	@GetMapping(value = "/distribution/distributionForPrevious")
	public ResultMessage queryDistributionForPrevious(
			@RequestParam(name="regionId")Long regionId,
			@RequestParam(name="cropId")Integer cropId,
			@RequestParam(name="dataTime")String dataTime,
			@RequestParam(name="accuracyId")Integer accuracyId,
			@RequestParam(name="isQueryLargeArea")Integer isQueryLargeArea
	);

	/*
    * 根据区域、作物，时间，精度查询当前区域及下一级区域的作物种植面积近三年及历年10年均值
    *  regionId: 区域ID
	*  cropId: 作物ID
	*  dataTime: 分布时间点
	*  accuracyId: 精度
	* @return ResultMessage
	* @version <1> 2018-08-10 cxw: Created.
    */
	@GetMapping(value = "/distribution/distributionForThree")
	public ResultMessage queryDistributionForThree(
			@RequestParam(name="regionId")Long regionId,
			@RequestParam(name="cropId")Integer cropId,
			@RequestParam(name="dataTime")String dataTime,
			@RequestParam(name="accuracyId")Integer accuracyId
	);

	/*
	 * 根据区域、作物，时间，精度查询重庆特定区域当年与前一年区域及下一级区域的作物种植面积数据
	 * regionId: 区域ID
	 * cropId: 作物ID
	 * dataTime: 分布时间点
	 *  accuracyId: 精度
	 * @return ResultMessage
	 * @version <1> 2018-08-10 huxiaoqiang: Created.
	 */
	@GetMapping(value = "/distribution/queryBeyondDistribution")
	public ResultMessage queryBeyondDistribution(
			@RequestParam(name="regionId")Long regionId,
			@RequestParam(name="cropId")Integer cropId,
			@RequestParam(name="dataTime")String dataTime,
			@RequestParam(name="accuracyId")Integer accuracyId
	);

	
	/*
	 * 根据区域、作物，时间，精度查询重庆市特定分下在重庆市的百分占比
	 *  regionId: 区域ID
	 *  cropId: 作物ID
	 *  dataTime: 分布时间点
	 *  accuracyId: 精度
	 * @return ResultMessage
	 * @version <1> 2018-10-30 liyb: Created.
	 */
	
	@GetMapping(value = "/distribution/queryRegionPercentage")
	public ResultMessage queryRegionPercentage(
			@RequestParam(name="regionId")Long regionId,
			@RequestParam(name="cropId")Integer cropId,
			@RequestParam(name="dataTime")String dataTime,
			@RequestParam(name="accuracyId")Integer accuracyId
	);

	/**
	 * 根据区域、时间查询所有分布数据
	 * @version cxw 2019-03-20: Created.
	 */
	@GetMapping(value = "/distribution/queryAllDistribution")
	public ResultMessage queryAllDistribution(
			@RequestParam(name="regionId")Long regionId,
			@RequestParam(name="startDate")String startDate,
			@RequestParam(name="endDate")String endDate
	);

	/**
	 * 根据区域、时间查询所有分布数据
	 * @version cxw 2019-03-21: Created.
	 */
	@GetMapping(value = "/distribution/queryAllDistributionByTime")
	public ResultMessage queryAllDistributionByTime(
			@RequestParam(name="regionId")Long regionId,
			@RequestParam(name="cropId")Integer cropId,
			@RequestParam(name="startDate")String startDate,
			@RequestParam(name="endDate")String endDate,
			@RequestParam(name="resolution")Integer resolution
	);

}