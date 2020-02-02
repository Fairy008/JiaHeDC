package com.jh.collection.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 资源文件接口，通过feign方式
 *
 */
@FeignClient(name="jh-upload")
public interface IUploadService {
    /**
     * 采集注册用户注册后默认创建模板和任务
     */
    @PostMapping(value = "/file/downloadZip")
    byte[] downloadZip(@RequestParam(name = "mediasId") String mediasId, @RequestParam(name = "proPath") String proPath);

    /**
     * 大任务下子任务分文件夹进行存
     * @param taskItemJson
     * @param proPath
     * @return
     */
    @PostMapping(value = "/file/downloadMedia")
    byte[] downloadMedia(@RequestParam(name = "taskItemJson") String taskItemJson, @RequestParam(name = "proPath") String proPath);

    @GetMapping(value = "/file/downMediaById")
    byte[] downMediaById(@RequestParam(name = "mediaId") Integer mediaId);
}
