package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
* 简报的查询接口，业务工程调用publish中的简报接口.通过feign方式
* 
*/
@FeignClient(name="jh-publish")
public interface IBriefService{
        /**
        * 珈和产品查询简报
        */

        @GetMapping(value = "/templateReporter/findBriefReportByRegionId")
        ResultMessage findBriefReportByRegionId(@RequestParam(name="regionId") Long regionId);



        /**
         * 根据reportId 获取简报的信息
         * @version wangli [2018-08-13 10:47:09] : Created.
         */
        @GetMapping(value = "/templateReporter/findBriefReportByType")
        ResultMessage findBriefReportByType(@RequestParam(name="reportId") Long reportId,@RequestParam(name="type") Integer type);


        /**
         * 根据regionId 查询简报列表 用于pc端产品
         * @version wangli [2018-08-20 10:47:09] : Created.
         */
        @GetMapping(value = "/templateReporter/findPublishBriefReportByPage")
        ResultMessage findPublishBriefReportByPage(@RequestParam(name="regionId") Long regionId,@RequestParam(name="rows") Integer rows,@RequestParam(name="page") Integer page);

        /**
         * 根据reportId 获取简报的信息(不包含降雨量和墒情)
         * @version wangli [2018-08-13 10:47:09] : Created.
         */
        @GetMapping(value = "/templateReporter/findBriefReportByTypeNew")
        ResultMessage findBriefReportByTypeNew(@RequestParam(name="reportId") Long reportId,@RequestParam(name="type") Integer type);


        /**
         * 根据reportId 获取简报的信息（降雨量和墒情）
         * @version wangli [2018-08-13 10:47:09] : Created.
         */
        @GetMapping(value = "/templateReporter/findBriefReportByTypeTrrm")
        ResultMessage findBriefReportByTypeTrrm(@RequestParam(name="reportId") Long reportId,@RequestParam(name="type") Integer type);



}