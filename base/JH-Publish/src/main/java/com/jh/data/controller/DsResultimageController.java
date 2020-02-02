package com.jh.data.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.data.entity.DsResultimage;
import com.jh.data.model.ImportParam2;
import com.jh.data.model.ProductQueryParam;
import com.jh.data.service.DataSatServiceFactory;
import com.jh.data.service.IDsResultimageService;
import com.jh.util.DownloadUtil;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:数据集数据
 *
 * @version <1> 2018-07-02  lcw : Created.
 *
 */
@RestController
@RequestMapping("/resultimage")
public class DsResultimageController extends BaseController {


    @Autowired
    public IDsResultimageService resultimageService;


    @ApiOperation(value = "分页获取数据集影像数据", notes = "分页获取数据集影像数据")
    @ApiImplicitParam(name = "dsResultimage",value = "数据集影像列表",required = false,dataType = "DsResultimage")
    @PostMapping("findByPage")
    public PageInfo<DsResultimage> findByPage(DsResultimage dsResultimage){

        PageInfo<DsResultimage> pageInfo = resultimageService.findByPage(dsResultimage);

        return pageInfo;

    }

    /**
     * 从ceph服务器的指定目录获取数据列表
     * @return
     * @version <1> 2018-07-02 lcw : Created.
     */
    @ApiOperation(value = "获取ceph服务器的指定目录的文件列表", notes = "获取ceph服务器的指定目录的文件列表")
    @ApiImplicitParam(name = "importParam",value = "待导入文件列表",required = false,dataType = "ImportParam2")
    @PostMapping("queryImportDataFromCeph")
    public PageInfo<ImportParam2> queryImportDataFromCeph(ImportParam2 importParam){
        String  path = CephUtils.getCephDatasetImport();
        importParam.setPath(path); //路径
        importParam.setIgnorePrefix(CephUtils.getCephCollectionImportPrefix()); //前缀

        PageInfo<ImportParam2> pages  = resultimageService.findImportDataFromCeph(importParam);
        return pages;
    }


    /**
     * loader dataset
     * @param resultimage
     * @param request
     * @return
     * @version<1> 2018-07-02 lcw :Created.
     */
    @ApiOperation(value = "数据集入库", notes = "对数据集进行入库操作")
    @ApiImplicitParam(name = "resultimage",value = "数据集入库对象",required = false,dataType = "DsResultimage")
    @PostMapping("loader")
    public ResultMessage loaderDataset(@RequestBody DsResultimage resultimage, HttpServletRequest request){

        ResultMessage result = ResultMessage.fail();
        resultimage.setCreator(getCurrentAccountId());
        resultimage.setCreatorName(getCurrentNickName());

        result = resultimageService.saveLoader(resultimage);

        return result;

    }

    /**
     * 数据集更新
     * @param resultimage
     * @return
     * @version<1> 2018-07-02 lcw :Created.
     */
    @ApiOperation(value = "更新影像对象", notes = "更新影像对象")
    @ApiImplicitParam(name = "resultimage",value = "影像对象",required = false,dataType = "DsResultimage")
    @PostMapping("updateResultimage")
    public ResultMessage updateResultimage(@RequestBody DsResultimage resultimage){
        ResultMessage result = ResultMessage.fail();
        result = resultimageService.updateResultimage(resultimage);
        return result;

    }

    /**
     * 查询数据集详情
     * @param rgId
     * @return
     * @version<1> 2018-07-02 lcw :Created.
     */
    @ApiOperation(value = "查看影像对象", notes = "查看影像对象")
    @ApiImplicitParam(name = "rgId",value = "影像对象ID",required = false,dataType = "Integer")
    @PostMapping("queryResultimage")
    public ResultMessage queryResultimage(Integer rgId){
        ResultMessage result = ResultMessage.fail();
        result = resultimageService.queryResultimage(rgId);
        return result;

    }

    @ApiOperation("数据处理文件下载")
    @ApiImplicitParam(name = "rgId",value = "数据集影像ID",required = true,paramType = "query", dataType = "Integer")
    @RequestMapping("/download")
    public void download(@RequestParam Integer rgId, HttpServletResponse response){
//        DataReprocess dataReprocess = dataReprocessService.findDateReprocessById(reprocessId);
        ResultMessage result = resultimageService.findResultimageById(rgId);
        if(result.isFlag()){
            DsResultimage resultimage= (DsResultimage)result.getData();
            DownloadUtil.getInstance().downloadFile(resultimage.getStoragePath(), response);
        }
    }

    @ApiOperation("批量激活数据生产任务")
    @ApiImplicitParam(name = "resultimage",value = "数据集入库对象",required = false,dataType = "DsResultimage")
    @PostMapping("/activateTask")
    public ResultMessage activateTask(@RequestBody DsResultimage resultimage, HttpServletRequest request){

            resultimage.setModifier(getCurrentAccountId());
            resultimage.setModifierName(getCurrentNickName());

        ResultMessage result = resultimageService.updateResultimageByIds(resultimage);

        return result;
    }


    @ApiOperation("批量发布图层")
    @ApiImplicitParam(name = "resultimage",value = "数据集对象",required = false,dataType = "DsResultimage")
    @PostMapping("/publishTif")
    public ResultMessage publishTif(@RequestBody DsResultimage resultimage, HttpServletRequest request){

        ResultMessage result = ResultMessage.fail();
        resultimage.setCreator(getCurrentAccountId());
        resultimage.setCreatorName(getCurrentNickName());
        resultimage.setModifier(getCurrentAccountId());
        resultimage.setModifierName(getCurrentNickName());
        result = resultimageService.publishTif(resultimage);

        return result;
    }

    @ApiOperation("批量发布图层和数据")
    @ApiImplicitParam(name = "resultimage",value = "数据集对象",required = false,dataType = "DsResultimage")
    @PostMapping("/publishTifAndData")
    public ResultMessage publishTifAndData(@RequestBody DsResultimage resultimage, HttpServletRequest request){

        ResultMessage result = ResultMessage.fail();
        resultimage.setCreator(getCurrentAccountId());
        resultimage.setCreatorName(getCurrentNickName());
        resultimage.setModifier(getCurrentAccountId());
        resultimage.setModifierName(getCurrentNickName());
        result = resultimageService.publishTifAndData(resultimage);

        return result;
    }



    @PostMapping("checkNull")
    public ResultMessage checkNull(@RequestBody ImportParam2 importParam){
        return resultimageService.checkNull(importParam.getPath());
    }

    @PostMapping("queryImportDataDetail")
    public ResultMessage queryImportDataDetail(@RequestBody ImportParam2 importParam){
        String suffixs = "";
        importParam.setPath(importParam.getFileName());
        return resultimageService.findImportDataDetail(importParam, suffixs);
    }

    /**
     * 根据任务ID查询数据集列表
     *
     * @param productQueryParam 指数对象
     * @return
     * @version <1> 2018-09-26 lijie： Created.
     */
    @ApiOperation(value = "根据任务ID查询生态质量指数", notes = "根据任务ID查询生态质量指数")
    @ApiImplicitParam(name = "productQueryParam", value = "指数对象", required = false, dataType = "ProductQueryParam")
    @PostMapping("findDataSatListByTaskId")
    public PageInfo<Object> findIndexByTaskId(ProductQueryParam productQueryParam) {
        PageInfo<Object> list = DataSatServiceFactory.getIndexService(productQueryParam.getDsType()).findDataSatListByTaskId(productQueryParam);
        return list;
    }

    /**
     * 根据生产ID更新数据集明细
     * @param dsResultimage 任务对象
     * @return
     * @version <1> 2019-02-19 lijie： Created.
     */
    @ApiOperation(value = "据生产ID更新数据集明细", notes = "据生产ID更新数据集明细")
    @ApiImplicitParam(name = "dsResultimage", value = "任务对象", required = false, dataType = "DsResultimage")
    @PostMapping("updateDataSat")
    public ResultMessage updateDataSat(@RequestBody DsResultimage dsResultimage) {
        return DataSatServiceFactory.getIndexService(dsResultimage.getDsType()).updateDataSat(dsResultimage.getDataStr());
    }

    /**
     * 根据生产任务Ids撤回或删除图层
     * @param dsResultimage
     * @return
     * @version <1> 2019-02-19 lijie： Created.
     */
    @ApiOperation(value = "根据生产ID撤回图层和数据", notes = "根据生产ID撤回图层和数据")
    @ApiImplicitParam(name = "dsResultimage", value = "任务对象", required = false, dataType = "DsResultimage")
    @PostMapping("backTifAndData")
    public ResultMessage backTifAndData(@RequestBody DsResultimage dsResultimage){
        return resultimageService.backTifAndData(dsResultimage);
    }

    /**
     * 根据生产任务Ids撤回或删除图层
     * @param dsResultimage
     * @return
     * @version <1> 2019-02-19 lijie： Created.
     */
    @ApiOperation(value = "根据生产ID删除图层和数据", notes = "根据生产ID删除图层和数据")
    @ApiImplicitParam(name = "dsResultimage", value = "任务对象", required = false, dataType = "DsResultimage")
    @PostMapping("deleteTifAndData")
    public ResultMessage deleteTifAndData(@RequestBody DsResultimage dsResultimage){
        return resultimageService.deleteTifAndData(dsResultimage);
    }

    /**
     * 数据处理中的数据集入库
     * @param resultimage
     * @param request
     * @return
     * @version<1> 2018-02-19 zhangshen :Created.
     */
    @ApiOperation(value = "数据处理中的数据集入库", notes = "数据处理中的数据集入库")
    @ApiImplicitParam(name = "resultimage",value = "数据集入库对象",required = false,dataType = "DsResultimage")
    @PostMapping("handleDataLoader")
    public ResultMessage handleDataLoader(@RequestBody DsResultimage resultimage, HttpServletRequest request){
        resultimage.setCreator(getCurrentAccountId());
        resultimage.setCreatorName(getCurrentNickName());
        return resultimageService.saveHandleDataLoader(resultimage);
    }
}
