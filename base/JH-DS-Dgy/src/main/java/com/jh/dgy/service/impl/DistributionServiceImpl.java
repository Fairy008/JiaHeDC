package com.jh.dgy.service.impl;

import com.jh.dgy.mapping.IDistributionMapper;
import com.jh.dgy.service.IDistributionService;
import com.jh.dgy.entity.DistributionAndYieldEntity;
import com.jh.dgy.vo.DistributionAndYieldVo;
import com.jh.util.ArithUtil;
import com.jh.util.DateUtil;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:分布数据集服务: 1.根据区域、时间，作物，分辨率查询作物分布生成时间
 * 2.根据区域、时间查询当年与前一年区域及下一级区域的作物种植面积数据 3.根据区域、时间查询近三年区域及下一级区域的作物种植面积
 * 
 * @version <1> 2018-05-03 cxw: Created.
 */
@Transactional
@Service
public class DistributionServiceImpl implements IDistributionService {

	@Autowired
	private IDistributionMapper distributionMapper;

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
	@Override
	public ResultMessage findDistributionTimes(Long regionId, Integer cropId, String startDate, String endDate,
			Integer resolution) {
		// 校验参数
		ResultMessage result = checkDistributionParamTime(regionId, cropId, startDate, endDate, resolution);
		if (result.isFlag()) {
			DistributionAndYieldEntity dyp = new DistributionAndYieldEntity();
			dyp.setStartDate(DateUtil.strToLocalDate(startDate));
			dyp.setRegionId(regionId);
			dyp.setCropId(cropId);
			dyp.setResolution(resolution);
			dyp.setEndDate(DateUtil.strToLocalDate(endDate));
			List<LocalDate> dataTimes = new ArrayList<LocalDate>();
			dataTimes = distributionMapper.findDistributionTimes(dyp);
			if (dataTimes.size() > 0) {
				result.setData(dataTimes);
			} else {
				result = ResultMessage.fail("无分布数据");
			}
		}
		return result;
	}

	/*
	* 根据区域、时间查询分布数据
	* @param :
	*   regionId: 区域ID
	*  startDate: 开始日期
	*    endDate: 结束日期
	* @return ResultMessage
	* @version <1> 2019-03-20 cxw: Created.
	*/
	public ResultMessage findAllDistribution(Long regionId,String startDate,String endDate){
		// 校验参数
		ResultMessage result = checkDistribution(regionId, startDate, endDate);
		if (result.isFlag()) {
			DistributionAndYieldEntity dyp = new DistributionAndYieldEntity();
			dyp.setStartDate(DateUtil.strToLocalDate(startDate));
			dyp.setRegionId(regionId);
			dyp.setEndDate(DateUtil.strToLocalDate(endDate));
			List<DistributionAndYieldVo> list = new ArrayList<DistributionAndYieldVo>();
			list = distributionMapper.findAllDistribution(dyp);
			result.setData(list);
		}
		return result;
	}

	/*
	 * 根据区域、时间查询当年与前一年区域及下一级区域的作物种植面积数据 regionId: 区域ID cropId: 作物ID dataTime:
	 * 分布时间 resolution: 分辨率
	 * 
	 * @return ResultMessage
	 * 
	 * @version <1> 2018-04-27 27: Created.
	 */
	@Override
	public ResultMessage findDistributionForPrevious(Long regionId, Integer cropId, String dataTime, Integer resolution,
			Integer isQueryLargeArea) {
		// 校验参数
		ResultMessage result = checkDistributionParam(regionId, cropId, dataTime, resolution);
		if (result.isFlag()) {
			int days = Integer.parseInt(PropertyUtil.getPropertiesForConfig("calculation_days"));
			DistributionAndYieldEntity dyp = new DistributionAndYieldEntity();
			dyp.setRegionId(regionId);
			dyp.setCropId(cropId);
			dyp.setResolution(resolution);
			dyp.setStartDate(DateUtil.strToLocalDate(dataTime).minusDays(days));
			dyp.setEndDate(DateUtil.strToLocalDate(dataTime).plusDays(days));

			// 先查询改区域下是否有local_name字段，有，则根据local_name字段分组求和
			if (isQueryLargeArea == 0) {
				List<DistributionAndYieldVo> resultList = distributionMapper.findBeyondDistributionList(dyp);
				if (resultList.size() > 1 && !"".equals(resultList.get(0).getRegionName())) {
					result.setData(resultList);
					return result;
				}
			}

			// 求查询已选择时间值
			List<DistributionAndYieldVo> curList = distributionMapper.findDistributionList(dyp);
			
			
			
			
			//查出重庆市的数据
			dyp.setRegionId(regionId);
			dyp.setCropId(cropId);
			dyp.setDataTime(DateUtil.strToLocalDate(dataTime));
			dyp.setResolution(resolution);
			// 先查出传入区域的分布面积
			DistributionAndYieldVo distribution = distributionMapper.findDistribution(dyp);
			
			
			
			
			// 求上一年值
			// String sDate =
			// dyp.getStartDate().minusYears(1).getYear()+"01-01";
			// String eDate =
			// dyp.getStartDate().minusYears(1).getYear()+"12-31";
			dyp.setStartDate(dyp.getStartDate().minusYears(1));
			dyp.setEndDate(dyp.getEndDate().minusYears(1));
			List<DistributionAndYieldVo> lastList = distributionMapper.findDistributionList(dyp);
			
			
			
			
			// 将前一年的数据放入lastValue字段中，并计算percent.
			for (DistributionAndYieldVo ds : curList) {
				DistributionAndYieldVo lastDs = findEqualRegion(ds, lastList);
				if (lastDs != null) {

					ds.setLastValue(lastDs.getValue());
					if (ds.getValue() != null && lastDs.getValue() != null) {
						double temp = ArithUtil.div(ds.getValue() - lastDs.getValue(), lastDs.getValue(), 4);
						BigDecimal percent = new BigDecimal(temp);
						ds.setPercent(percent.floatValue());
					}
				}
				if(distribution.getValue()!=null &&distribution.getValue()>0){
					if(ds.getValue()!=null){
						ds.setPercentAge((ds.getValue()/distribution.getValue())*100);
					}else{
						ds.setPercentAge(new Float(0));
					}
				}
				
			}
			result.setData(curList);
		}
		return result;
	}

	/*
	 * 根据区域、时间查询近三年区域及下一级区域的作物种植面积
	 * 
	 * @param : regionId: 区域ID cropId: 作物ID dataTime: 分布时间 resolution: 分辨率
	 * 
	 * @return ResultMessage
	 * 
	 * @version <1> 2018-04-27 cxw: Created.
	 */
	@Override
	public ResultMessage findDistributionForThree(Long regionId, Integer cropId, String dataTime, Integer resolution) {
		// 校验参数
		ResultMessage result = checkDistributionParam(regionId, cropId, dataTime, resolution);
		if (result.isFlag()) {
			int days = Integer.parseInt(PropertyUtil.getPropertiesForConfig("calculation_days"));
			int yearNum = 3;// 显示最近三年数据
			int avgYearNum = 10;// 10年平均
			DistributionAndYieldEntity dyp = new DistributionAndYieldEntity();
			if(dataTime.substring(5,7).equals("01"))
			{
				dyp.setStartDate(DateUtil.strToLocalDate(dataTime).minusDays(0));
			}
			else{
				dyp.setStartDate(DateUtil.strToLocalDate(dataTime).minusDays(days));
			}
			dyp.setEndDate(DateUtil.strToLocalDate(dataTime).plusDays(days));
			dyp.setRegionId(regionId);
			dyp.setCropId(cropId);
			dyp.setResolution(resolution);
			// 用于保存三年及十年均值
			Map<String, Object> allMap = new HashMap<String, Object>();
			// 临时保存十年均值
			List<DistributionAndYieldVo> avgList = new ArrayList<DistributionAndYieldVo>();
			// 获取查询年份日期的数据
			for (int i = 0; i < avgYearNum; i++) {
				// 获取每一年的数据，并将年做为键，保存三年的数据

				List<DistributionAndYieldVo> dataList = distributionMapper.findDistributionList(dyp);

				int year = dyp.getStartDate().getYear();
				String key = year + "";
				if (i < yearNum) {
					allMap.put(key, dataList);
				}

				addByTenYears(dataList, avgList); // 按查询日期和区域分组（SQL中分组），对最近10年的值进行累加

				// 用于循环每年数据
				dyp.setStartDate(dyp.getStartDate().minusYears(1));
				dyp.setEndDate(dyp.getEndDate().minusYears(1));
			}

			avgByTenYears(avgList); // 计算10年平均值
			allMap.put("10", avgList);
			// 设置返回值
			result = ResultMessage.success(allMap);
		}
		return result;
	}

	/*
	 * 往前/往后15天推算与当前时间点对应的分布，往前往后估算值最好去天数差值最小的值，如往前推算1天有值，往后推算3天有值，则取往前推算1天的值
	 * 
	 * @param dye 分布估算对象
	 * 
	 * @return DistributionAndYieldVo
	 * 
	 * @version <1> 2018-05-29 cxw : created.
	 */
	// public DistributionAndYieldVo
	// calculationDistributionByDay(DistributionAndYieldEntity dytemp,LocalDate
	// dataTime){
	// int numOne = 0,numTwo = 0;
	// DistributionAndYieldVo dataOne = new DistributionAndYieldVo();
	// DistributionAndYieldVo dataTwo = new DistributionAndYieldVo();
	// int days =
	// Integer.parseInt(PropertyUtil.getPropertiesForConfig("calculation_days"));
	// for(int j=0;j<days;j++){
	// dytemp.setDataTime(dataTime.plusDays(-j));
	// dataOne = distributionMapper.findDistribution(dytemp);
	// if(dataOne.getValue()!=null){
	// numOne = j;
	// break;
	// }
	// }
	// for(int j=0;j<days;j++){
	// dytemp.setDataTime(dataTime.plusDays(j));
	// dataTwo = distributionMapper.findDistribution(dytemp);
	// if(dataTwo.getValue()!=null){
	// numTwo = j;
	// break;
	// }
	// }
	// if(numOne==0&&numTwo==0)
	// {
	// return dataOne;
	// }
	// else if(numOne<=numTwo&&numOne!=0){
	// return dataOne;
	// }
	// else if(numOne>=numTwo&&numTwo!=0){
	// return dataTwo;
	// }
	// else if(numOne!=0&&numTwo==0){
	// return dataOne;
	// }
	// else if(numOne==0&&numTwo!=0){
	// return dataTwo;
	// }
	// else{
	// return dataOne;
	// }
	//
	// }

	/*
	 * 提取各接口中计算10年平均的公共部分 实现10年数据的平均计算
	 * 
	 * @param avgList 临时保存十年均值
	 * 
	 * @version <1> 2018-05-28 cxw : created.
	 */
	private void avgByTenYears(List<DistributionAndYieldVo> avgList) {
		if (avgList != null && avgList.size() > 0) {
			// 计算十年均值，并保存
			for (DistributionAndYieldVo ds : avgList) {
				if (ds.getValue() != null) {
					double temp = ArithUtil.div(ds.getValue(), ds.getPercent(), 2);
					BigDecimal avg = new BigDecimal(Double.toString(temp));
					ds.setValue(avg.floatValue());
				}
			}
		}
	}

	/*
	 * 提取分组10年数据累加公共部分 按照查询时间（段）和区域分组，对10年数据进行累加（若当年没有数据，则不作为平均年份计算）
	 * 
	 * @param dataList 一年数据
	 * 
	 * @param avgList 临时保存十年均值
	 * 
	 * @version <1> 2018-05-28 cxw: created.
	 */
	private void addByTenYears(List<DistributionAndYieldVo> dataList, List<DistributionAndYieldVo> avgList) {
		if (dataList != null && dataList.size() > 0) {
			// 获取十年均值，如果当年没有值，则不计入平均值的计算。
			for (int j = 0; j < dataList.size(); j++) {
				DistributionAndYieldVo avgDs = null;
				if (j < avgList.size()) {
					avgDs = avgList.get(j);
				} else {
					avgDs = new DistributionAndYieldVo();
					avgList.add(avgDs);
				}
				DistributionAndYieldVo tempDs = dataList.get(j);
				avgDs.setRegionId(tempDs.getRegionId());
				avgDs.setDataTime(tempDs.getDataTime()); // 横坐标为查询时间段内的月日（mm-dd）
				avgDs.setRegionName(tempDs.getRegionName());
				avgDs.setRegionCode(tempDs.getRegionCode());
				float total = 0.0f, avgNum;
				total = avgDs.getValue() == null ? total : total + avgDs.getValue();
				total = tempDs.getValue() == null ? total : total + tempDs.getValue();
				avgNum = avgDs.getPercent() == null ? 0.0f : avgDs.getPercent();
				avgNum = tempDs.getValue() == null ? avgNum : avgNum + 1;
				// 累加每年的值
				avgDs.setValue(total);
				// 计算累加的年份数目
				avgDs.setPercent(avgNum);
			}
		}
	}

	/**
	 * 检查作物分布时间点查询服务所需的参数是否为空
	 * 
	 * @param: regionId:
	 *             区域ID cropId: 作物ID startDate：开始时间 endDate: 结束时间 resolution:
	 *             分辨率
	 * @return ResultMessage :
	 * @version <1> 2018-05-03 cxw:Created.
	 */
	public ResultMessage checkDistributionParamTime(Long regionId, Integer cropId, String startDate, String endDate,
			Integer resolution) {
		ResultMessage result = ResultMessage.success();
		if (regionId == null) {
			result = ResultMessage.fail("区域不能为空");
		}
		if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
			result = ResultMessage.fail(null, "日期不能为空", null);
		}

		if (cropId == null) {
			result = ResultMessage.fail("查询作物不能为空");
		}

		if (resolution == null) {
			result = ResultMessage.fail("查询分辨率不能为空");
		}

		return result;
	}


	/**
	 * 检查分布时间查询服务所需的参数是否为空
	 *
	 * @param: regionId:区域ID startDate：开始时间 endDate: 结束时间
	 * @return ResultMessage :
	 * @version <1> 2018-05-03 cxw:Created.
	 */
	public ResultMessage checkDistribution(Long regionId,  String startDate, String endDate) {
		ResultMessage result = ResultMessage.success();
		if (regionId == null) {
			result = ResultMessage.fail("区域不能为空");
		}
		if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
			result = ResultMessage.fail(null, "日期不能为空", null);
		}
		return result;
	}

	/**
	 * 检查作物分布查询服务所需的参数是否为空
	 * 
	 * @param: regionId:
	 *             区域ID cropId: 作物ID dataTime：分布时间点 resolution: 分辨率
	 * @return ResultMessage :
	 * @version <1> 2018-05-03 cxw:Created.
	 */
	public ResultMessage checkDistributionParam(Long regionId, Integer cropId, String dataTime, Integer resolution) {
		ResultMessage result = ResultMessage.success();
		if (regionId == null) {
			result = ResultMessage.fail("区域不能为空");
		}
		if (StringUtils.isBlank(dataTime)) {
			result = ResultMessage.fail(null, "日期不能为空", null);
		}

		if (cropId == null) {
			result = ResultMessage.fail("查询作物不能为空");
		}

		if (resolution == null) {
			result = ResultMessage.fail("查询分辨率不能为空");
		}

		return result;
	}

	/**
	 * 在列表中找区域相同的对象
	 * 
	 * @param dyv:本期值对象
	 *            dataList：上一期值对象集合
	 * @return DistributionAndYieldVo 分布与估产返回对象
	 * @version <1> 2018-05-03 cxw:Created.
	 */
	public DistributionAndYieldVo findEqualRegion(DistributionAndYieldVo dyv, List<DistributionAndYieldVo> dataList) {
		for (DistributionAndYieldVo ds : dataList) {
			if (dyv.getRegionId().equals(ds.getRegionId())) {
				return ds;
			}
		}
		return null;
	}

	@Override
	public ResultMessage findRegionPercentage(Long regionId, Integer cropId, String dataTime, Integer resolution) {
		// 校验参数
		ResultMessage result = checkDistributionParam(regionId, cropId, dataTime, resolution);
		if (result.isFlag()) {

			Map<String, Object> allMap = new HashMap<String, Object>();
			DistributionAndYieldEntity param = new DistributionAndYieldEntity();
			param.setRegionId(regionId);
			param.setCropId(cropId);
			param.setDataTime(DateUtil.strToLocalDate(dataTime));
			param.setResolution(resolution);
			// 先查出传入区域的分布面积
			DistributionAndYieldVo distribution = distributionMapper.findDistribution(param);
			allMap.put("chinaName", distribution.getRegionName());
			allMap.put("regionCode", distribution.getRegionCode());
			allMap.put("regionId", regionId);
			allMap.put("dataTime", dataTime);
			Float region = distribution.getValue();

			// 再查传入区域的父级的面积
			param = new DistributionAndYieldEntity();
			param.setRegionId(Long.parseLong(distribution.getRegionParent()));
			param.setCropId(cropId);
			param.setDataTime(DateUtil.strToLocalDate(dataTime));
			param.setResolution(resolution);
			// 先查出传入区域的分布面积
			distribution = distributionMapper.findDistribution(param);
			Float city = distribution.getValue();
			// 创建一个数值格式化对象
			NumberFormat numberFormat = NumberFormat.getInstance();
			// 设置精确到小数点后2位
			numberFormat.setMaximumFractionDigits(2);
			String Percentage = "";
			if (region != null && city != null) {
				Percentage = numberFormat.format(region / city * 100);
			}
			// 所选区域在城市中的占比
			allMap.put("percentage", Percentage);
			// 所选区域的分布数
			allMap.put("regionValue", region);
			allMap.put("value", region);
			// 整个城市的总分布数
			allMap.put("city", city);
			// 剩下所有区域的百分比
			if(!"".equals(Percentage)){
				allMap.put("otherPercentage", 100 - Float.parseFloat(Percentage));
			}else{
				allMap.put("otherPercentage",0);
			}
			
			if (region != null && city != null) {
				// 剩下的其它所有区域的分布数值
				allMap.put("otherRegionValue", city - region);
			}else{
				allMap.put("otherRegionValue",0);
			}
			

			// 设置返回值
			result = ResultMessage.success(allMap);
		}
		return result;
	}

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
	@Override
	public ResultMessage queryAllDistributionByTime(Long regionId,Integer cropId,String startDate,String endDate,Integer resolution){
		// 校验参数
		ResultMessage result = checkDistributionParamTime(regionId, cropId, startDate, endDate, resolution);
		if (result.isFlag()) {
			DistributionAndYieldEntity dyp = new DistributionAndYieldEntity();
			dyp.setStartDate(DateUtil.strToLocalDate(startDate));
			dyp.setRegionId(regionId);
			dyp.setCropId(cropId);
			dyp.setResolution(resolution);
			dyp.setEndDate(DateUtil.strToLocalDate(endDate));
			List<String> dataTimes = new ArrayList<String>();
			dataTimes = distributionMapper.findDistributionTimesString(dyp);
			List<DistributionAndYieldVo> curList =new ArrayList<DistributionAndYieldVo>();
			if (dataTimes.size() > 0) {
				for(String dataTime:dataTimes) {
					ResultMessage resultData = findDistributionForPrevious(regionId, cropId, dataTime, resolution, 1);
					if(resultData.isFlag()){
						curList.addAll((List<DistributionAndYieldVo>)resultData.getData());
					}
				}
				result = ResultMessage.success(curList);
			} else {
				result = ResultMessage.fail("无分布数据");
			}
		}
		return result;
	}
}
