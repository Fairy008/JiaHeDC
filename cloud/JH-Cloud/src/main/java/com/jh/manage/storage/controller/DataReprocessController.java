package com.jh.manage.storage.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.manage.storage.entity.DataReprocess;
import com.jh.manage.storage.model.ImportReprocessParam;
import com.jh.manage.storage.service.IDataReprocessService;
import com.jh.util.DownloadUtil;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: 再加工数据导入Controller
 *
 * @version <1> 2018-04-27 9:08 zhangshen: Created.
 */
@RestController
@RequestMapping("/dataReprocess")
public class DataReprocessController extends BaseController {

    @Autowired
    private IDataReprocessService dataReprocessService;

    /**
     * 再加工数据入库操作
     * @param importReprocessParam
     * @param request
     * @return
     * @version <1> 2018-04-27 zhangshen : Created.
     */
    @ApiOperation(value = "再加工数据入库", notes = "根据文件夹路径生成再加工数据入库任务")
    @ApiImplicitParam(name = "importReprocessParam", value="再加工数据", required = true, dataType = "ImportReprocessParam")
    @PostMapping("/importReprocess")
    public ResultMessage importReprocess(@RequestBody ImportReprocessParam importReprocessParam, HttpServletRequest request){
            importReprocessParam.getDataReprocess().setCreator(getCurrentAccountId());//创建人
            importReprocessParam.getDataReprocess().setCreatorName(getCurrentNickName());//创建人名称
            importReprocessParam.getDataReprocess().setModifier(getCurrentAccountId());//修改人
            importReprocessParam.getDataReprocess().setModifierName(getCurrentNickName());//修改人人名称
        ResultMessage result = dataReprocessService.importReprocess(importReprocessParam);
        return result;
    }

    /**
     * 查询再加工数据列表
     * @param importReprocessParam
     * @param request
     * @return
     * @version <1> 2018-04-27 zhangshen:Created.
     */
    @ApiOperation(value = "查询再加工数据", notes = "查询再加工数据")
    @ApiImplicitParam(name = "importReprocessParam", value="再加工数据", required = false, dataType = "ImportReprocessParam")
    @PostMapping("/findDateReprocessByPage")
    public PageInfo<DataReprocess> findDateReprocessByPage(ImportReprocessParam importReprocessParam, HttpServletRequest request){

        return dataReprocessService.findDateReprocessByPage(importReprocessParam);
    }

    @ApiOperation("数据处理文件下载")
    @ApiImplicitParam(name = "reprocessId",value = "数据处理文件id",required = true,paramType = "query", dataType = "Integer")
    @RequestMapping("/down")
    public void dateReprocessDown(@RequestParam Integer reprocessId, HttpServletResponse response){
        DataReprocess dataReprocess = dataReprocessService.findDateReprocessById(reprocessId);
        if(null != dataReprocess){
            DownloadUtil.getInstance().downloadFile(dataReprocess.getPath(), response);
        }
    }

    /**
     * 批量发布再加工数据到图层
     * @param importReprocessParam
     * @param request
     * @return
     * @version <1> 2018-06-06 lxy: Created. zhangshen Update.
     */
    @ApiOperation(value = "批量发布再加工数据到图层", notes = "批量发布再加工数据到图层")
    @ApiImplicitParam(name = "importReprocessParam", value = "importReprocessParam",required = true,dataType = "ImportReprocessParam")
    @PostMapping("/publishReprocessData")
    public ResultMessage publishReprocessData(@RequestBody ImportReprocessParam importReprocessParam, HttpServletRequest request){


//        Integer[] reprocessIds = importReprocessParam.getReprocessIds();
//        String style = importReprocessParam.getStyle();
        importReprocessParam.setCreator(getCurrentAccountId());
        importReprocessParam.setCreatorName(getCurrentNickName());
        //批量发布再加工数据
        ResultMessage result = dataReprocessService.publishReprocessData(importReprocessParam);
        return result;
    }

    /**
     * Description: 获取geoserver样式列表
     * @param
     * @return ResultMessage
     * @version <1> 2018/6/15 10:26 zhangshen: Created.
     */
    @ApiOperation(value = "获取geoserver样式列表", notes = "获取geoserver样式列表")
    @PostMapping("/getGeoStyleList")
    public ResultMessage getGeoStyleList(){
        return dataReprocessService.getGeoStyleList();
    }

}
