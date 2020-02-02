package com.jh.collection.controller.nolog;


import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.CollectionTaskInfo;
import com.jh.collection.entity.query.CollectionTaskInfoQuery;
import com.jh.collection.feign.IUploadService;
import com.jh.collection.service.ICollectionTaskInfoService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Api(value = "nolog采集任务（大分类）", description = "nolog采集任务（大分类）服务")
@RestController
@RequestMapping("/nolog/collection/taskInfo/")
public class NologCollectionTaskInfoController {

    @Autowired
    private ICollectionTaskInfoService collectionTaskInfoService;

    @Autowired
    private IUploadService uploadService;

    @ModelAttribute
    public CollectionTaskInfo get(@RequestParam(required = false) Integer id) {
        CollectionTaskInfo entity = null;
        if (id != null) {
            entity = collectionTaskInfoService.getById(id);
        }
        if (entity == null) {
            entity = new CollectionTaskInfo();
        }
        return entity;
    }

    /**
     * 采集任务模板分页查询
     *
     * @param collectionTaskInfoQuery 请求查询参数
     * @return PageInfo
     * @version <1> 2018-11-19 xy： Created.
     */
    @ApiOperation(value = "采集任务（大分类）分页查询", notes = "采集任务（大分类）分页查询")
    @ApiImplicitParam(name = "collectionTaskInfoQuery", value = "采集任务（大分类）参数", required = false, dataType = "CollectionTaskInfoQuery")
    @PostMapping("findByPage")
    public PageInfo<CollectionTaskInfo> findByPage(@RequestBody CollectionTaskInfoQuery collectionTaskInfoQuery) {
        return collectionTaskInfoService.findByPage(collectionTaskInfoQuery);
    }

    /**
     * 根据ID获取采集任务模板
     *
     * @param collectionTaskInfo 采集任务模板对象
     * @return
     * @version <1> 2018-11-19 xy： Created.
     */
    @ApiOperation(value = "根据ID获取采集任务对象", notes = "根据ID获取采集任务对象")
    @ApiImplicitParam(name = "collectionTaskInfo", value = "采集任务对象", required = true, dataType = "CollectionTaskInfo")
    @PostMapping("getById")
    public ResultMessage getById(CollectionTaskInfo collectionTaskInfo) {
        return ResultMessage.success(collectionTaskInfo);
    }

    /**
     * 查看资源文件
     * @param mediaId
     */
    @ResponseBody
    @RequestMapping(value="/{mediaId}",method= RequestMethod.GET)
    public void downloadMeidaById(@PathVariable("mediaId")Integer mediaId, HttpServletResponse response) throws IOException {
        byte[] data = uploadService.downMediaById(mediaId);
        response.setCharacterEncoding("UTF-8");
        OutputStream outputSream = response.getOutputStream();
        outputSream.write(data);
        outputSream.flush();
    }
}
