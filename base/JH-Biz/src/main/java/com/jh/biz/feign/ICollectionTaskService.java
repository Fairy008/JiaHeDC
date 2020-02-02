package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;


@FeignClient(name = "JH-Collection")
public interface ICollectionTaskService {


    /**
     * 前台采集任务服务接口
     * @param phone
     *      手机号
     * @return
     */
    @GetMapping(value = "/collection/taskInfo/findApiByPage")
    ResultMessage findFrontByUser(@RequestParam(value = "phone", required = true) String phone);

    /**
     * @param phone
     *      手机号
     * @param taskName
     *      任务名
     * @param cropId
     *      作物ID
     * @param taskType
     *      任务类型
     * @param startCreateTime
     *      查询开始时间
     * @param endCreateTime
     *      查询结束时间
     * @return
     */
    @GetMapping(value = "/collection/taskInfo/findApiByPage")
    ResultMessage findManagerTask(
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "taskName", required = false) String taskName,
            @RequestParam(value = "cropId", required = false) Integer cropId,
            @RequestParam(value = "taskType", required = false) Integer taskType,
            @RequestParam(value = "startCreateTime", required = false) String startCreateTime,
            @RequestParam(value = "endCreateTime", required = false) String endCreateTime
    );

    /**
     * 删除任务采集
     * @param id
     *      任务ID
     * @return
     */
    @PostMapping(value = "/collection/taskInfo/delete")
    ResultMessage deleteCollectionTask( @RequestParam(value = "id", required = false) Integer id);


    /**
     * 更新采集任务
     * @param id
     *      任务名ID
     * @param taskName
     *       任务名
     * @param taskType
     *       任务类型
     * @param endDate
     *       结束日期
     * @return
     */
    @PostMapping(value = "/collection/taskInfo/update")
    ResultMessage updateCollectionTask(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "taskName", required = false) String taskName,
            @RequestParam(value = "workplace", required = false) String workplace,
            @RequestParam(value = "taskType", required = false) Integer taskType,
            @RequestParam(value = "endDate", required = false) Date endDate
    );

    /**
     * 保存任务
     * @param taskName
     *      任务名
     * @param workplace
     *      工作单位
     * @param taskType
     *      任务类型
     * @param templateContent
     *      模板内容
     * @param remark
     *      备注
     * @param startDate
     *      开始时间
     * @param endDate
     *      结束时间
     * @return
     */
    @PostMapping(value = "/collection/taskInfo/add")
    ResultMessage saveCollectionTask(
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "taskName", required = false) String taskName,
            @RequestParam(value = "workplace", required = false) String workplace,
            @RequestParam(value = "taskType", required = false) Integer taskType,
            @RequestParam(value = "templateContent", required = false) String templateContent,
            @RequestParam(value = "remark", required = false) String remark,
            @RequestParam(value = "startDate", required = false) Date startDate,
            @RequestParam(value = "endDate", required = false) Date endDate
    );

    /**
     * 查询用户模板
     * @param phone
     * @return
     */
    @PostMapping(value = "/collection/template/findApiByPage")
    ResultMessage findApiTemplateByPage(@RequestParam(value = "phone", required = true) String phone);

}
