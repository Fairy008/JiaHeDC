package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description:7天降雨数据集服务:
 * 1.根据区域，日期构造7天降雨数据查询服务(91期货)
 * @version <1> 2018-06-12 cxw: Created.
 */
@FeignClient(name="jh-ds-ttn")
public interface IDsTrmmForSevenService {

    /*
  * 根据区域，日期构造7天降雨数据查询服务
   * @param 数据集查询参数
   *   regionId: 区域ID
   *       date: 日期
  * @return ResultMessage
  * @version <1> 2018-09-03 cxw: Created.
  */
    @GetMapping("/trmmForSeven")
    public ResultMessage queryTrmmForSeven( @RequestParam(name="regionId")Long[] regionId,
                                            @RequestParam(name="date")String date);

}
