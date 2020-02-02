package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "jh-ds-dgy")
public interface IYieldService {

    /**
     * 根据区域、时间，作物，分辨率查询作物估产生成时间
     * @param regionId  区域id
     * @param cropId    作物id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param resolution    数据精度
     * @return
     */
    @GetMapping(value = "/yield/queryYieldTimes")
    ResultMessage queryYieldTimes(@RequestParam(name = "regionId") Long regionId,
                                  @RequestParam(name = "cropId") Integer cropId,
                                  @RequestParam(name = "startDate") String startDate,
                                  @RequestParam(name = "endDate") String endDate,
                                  @RequestParam(name = "resolution") Integer resolution);



    /**
     * 根据年份查询区域及子区域作物当年与上一年的估产数据
     * @param regionId  区域id
     * @param cropId    作物id
     * @param dataTime  数据时间
     * @param resolution    数据精度
     * @return
     */
    @GetMapping(value = "/yield/yieldForPrevious")
    ResultMessage queryYieldForPrevious(@RequestParam(name = "regionId") Long regionId,
                                        @RequestParam(name = "cropId") Integer cropId,
                                        @RequestParam(name = "dataTime") String dataTime,
                                        @RequestParam(name = "resolution") Integer resolution
                                        );


    /**
     * 根据年份查询区域及子区域作物三年的估产及十年的估产均值
     * @param regionId  区域id
     * @param cropId    作物id
     * @param dataTime  数据时间
     * @param resolution    数据精度
     * @return
     */
    @GetMapping(value = "/yield/yieldForThree")
    ResultMessage queryYieldForThree(@RequestParam(name = "regionId") Long regionId,
                                     @RequestParam(name = "cropId") Integer cropId,
                                     @RequestParam(name = "dataTime") String dataTime,
                                     @RequestParam(name = "resolution") Integer resolution);


    /**
     * 查询该区域下各级区域的作物估产情况
     * @param regionId 区域id
     * @param cropId    作物id
     * @param dataTime  数据时间
     * @param resolution    数据精度
     * @return
     */
    @GetMapping(value = "/yield/queryYieldInRegion")
    ResultMessage queryYieldInRegion(@RequestParam(name = "regionId") Long regionId,
                                     @RequestParam(name = "cropId") Integer cropId,
                                     @RequestParam(name = "dataTime") String dataTime,
                                     @RequestParam(name = "resolution") Integer resolution,
                                     @RequestParam(name = "isQueryLargeArea") Integer isQueryLargeArea
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
	
	@GetMapping(value = "/yield/queryRegionPercentage")
	public ResultMessage queryRegionPercentage(
			@RequestParam(name="regionId")Long regionId,
			@RequestParam(name="cropId")Integer cropId,
			@RequestParam(name="dataTime")String dataTime,
			@RequestParam(name="accuracyId")Integer accuracyId
	);

    /**
     * 根据区域、时间查询所有估产数据
     * @version cxw 2019-03-20: Created.
     */
    @GetMapping(value = "/yield/queryAllYield")
    public ResultMessage queryAllYield(
            @RequestParam(name="regionId")Long regionId,
            @RequestParam(name="startDate")String startDate,
            @RequestParam(name="endDate")String endDate
    );

    /**
     * 根据区域、时间，作物，分辨率查询作物分布
     * @param regionId  区域id
     * @param cropId    作物id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param resolution    数据精度
     * @return
     */
    @GetMapping(value = "/yield/queryAllYieldByTime")
    ResultMessage queryAllYieldByTime(@RequestParam(name = "regionId") Long regionId,
                                  @RequestParam(name = "cropId") Integer cropId,
                                  @RequestParam(name = "startDate") String startDate,
                                  @RequestParam(name = "endDate") String endDate,
                                  @RequestParam(name = "resolution") Integer resolution);

}
