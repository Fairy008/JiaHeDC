package com.jh.manage.storage.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.manage.storage.entity.DataStatic;
import com.jh.manage.storage.model.ImportStaticParam;
import com.jh.manage.storage.service.IDataStaticService;
import com.jh.util.DownloadUtil;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 * 1.矢量数据Controller
 *
 * @version <1> 2018-04-27 9:38 zhangshen: Created.
 */
@RestController
@RequestMapping("/dataStatic")
public class DataStaticController extends BaseController {

    @Autowired
    private IDataStaticService dataStaticService;

    /**
     * 矢量数据入库操作
     * @param importStaticParam
     * @param request
     * @return
     * @version <1> 2018-04-27 zhangshen : Created.
     */
    @ApiOperation(value = "矢量数据入库", notes = "根据文件夹路径生成矢量数据入库任务")
    @ApiImplicitParam(name = "importStaticParam", value="矢量数据", required = true, dataType = "ImportStaticParam")
    @PostMapping("/importStatic")
    public ResultMessage importStatic(@RequestBody ImportStaticParam importStaticParam, HttpServletRequest request){
        importStaticParam.getDataStatic().setCreator(getCurrentAccountId());//创建人
        importStaticParam.getDataStatic().setCreatorName(getCurrentNickName());//创建人名称
        ResultMessage result = dataStaticService.importStatic(importStaticParam);
        return result;
    }

    /**
     * 查询矢量数据列表
     * @param importStaticParam
     * @param request
     * @return
     * @version <1> 2018-04-28 zhangshen:Created.
     */
    @ApiOperation(value = "查询矢量数据", notes = "查询矢量数据")
    @ApiImplicitParam(name = "importStaticParam", value="矢量数据", required = false, dataType = "ImportStaticParam")
    @PostMapping("/findDateStaticByPage")
    public PageInfo<DataStatic> findDateStaticByPage(ImportStaticParam importStaticParam, HttpServletRequest request){

        return dataStaticService.findDateStaticByPage(importStaticParam);
    }

    @ApiOperation("数据处理文件下载")
    @ApiImplicitParam(name = "staticId",value = "数据处理文件id",required = true,paramType = "query", dataType = "Integer")
    @RequestMapping("/down")
    public void dateStaticDown(@RequestParam Integer staticId, HttpServletResponse response){
        DataStatic dataStatic = dataStaticService.findDateStaticById(staticId);
        if(null != dataStatic){
            DownloadUtil.getInstance().downloadFile(dataStatic.getFilePath(), response);
        }
    }
}
