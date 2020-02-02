package com.jh.manage.loader.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.manage.loader.entity.DataLoader;
import com.jh.manage.loader.model.ImportParam;
import com.jh.manage.loader.model.LoaderParam;
import com.jh.manage.loader.service.IDataLoaderService;
import com.jh.manage.storage.Enum.StorageTypeEnum;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 数据入库控制器
 *
 * @version <1> 2018-03-01 lcw: Created
 */
@RestController
@RequestMapping("/loader")
public class LoaderController extends BaseController {

    @Autowired
    private IDataLoaderService dataLoaderService;

    /**
     *入库任务分页查询
     * @param loaderParam
     * @return
     * @version <1> 2018-03-01 lcw： Created.
     */
    @ApiOperation(value = "入库任务列表查询",notes = "入库任务列表查询")
    @ApiImplicitParam(name = "loaderParam",value = "入库任务对象",required = false,dataType = "LoaderParam")
    @PostMapping("findByPage")
    public PageInfo<DataLoader> findByPage(LoaderParam loaderParam, HttpServletRequest request){
        if(loaderParam.isUserFlag()){ //添加当前登录人信息查询
                loaderParam.setCreator(getCurrentAccountId());
        }

        return dataLoaderService.findByPage(loaderParam);
    }

    /**
     * 开始入库任务
     * @param loaderId
     * @return ResultMessage
     * @version<1> 2018-03-06 lcw : Created.
     */
    @ApiOperation(value = "执行入库任务",notes = "执行入库任务")
    @ApiImplicitParam(name = "loaderId",value = "入库任务ID",required = true,dataType = "Integer")
    @PostMapping("start")
    public ResultMessage start(@RequestParam Integer loaderId, HttpServletRequest request){

        DataLoader dataLoader = new DataLoader();
        dataLoader.setLoaderId(loaderId);
        dataLoader.setModifier(getCurrentAccountId());
        dataLoader.setModifierName(getCurrentNickName());

        ResultMessage result = dataLoaderService.startDataLoaderTask(dataLoader);

        return result;
    }


    /**
     * 从ceph服务器的指定目录获取数据列表
     * @return
     * @version <1> 2018-03-07 lcw : Created.
     */
    @ApiOperation(value = "获取ceph服务器的指定目录的文件列表", notes = "获取ceph服务器的指定目录的文件列表")
    @ApiImplicitParam(name = "importParam",value = "待导入文件列表",required = false,dataType = "ImportParam")
    @PostMapping("queryImportDataFromCeph")
    public PageInfo<ImportParam> queryImportDataFromCeph(ImportParam importParam){
        String path = "";
        if(StorageTypeEnum.DATA_STORAGE_TYPE_YSSJ.getId().equals(importParam.getStorageType())){ //原始数据
            path = CephUtils.getCephCollectionImport();
        }else if(StorageTypeEnum.DATA_STORAGE_TYPE_CGSJ.getId().equals(importParam.getStorageType())){ //再加工数据
            path = CephUtils.getCephReprocessImport();
        }else if(StorageTypeEnum.DATA_STORAGE_TYPE_SLSJ.getId().equals(importParam.getStorageType())){ //矢量数据
            path = CephUtils.getCephStaticImport();

        }
        importParam.setPath(path); //路径
        importParam.setIgnorePrefix(CephUtils.getCephCollectionImportPrefix()); //前缀

        PageInfo<ImportParam> pages  = dataLoaderService.findImportDataFromCeph(importParam);
        return pages;
    }



    /**
     * 将导入数据转换为入库任务
     * @param importParam
     * @param request
     * @return
     * @version<1> 2018-03-07 lcw : Created.
     */
    @ApiOperation(value = "生成入库任务", notes = "根据文件夹路径生成入库任务")
    @ApiImplicitParam(name = "importParam", value="文件夹路径", required = true, dataType = "ImportParam")
    @PostMapping("makeImportLoaderTask")
    public ResultMessage makeImportLoaderTask(@RequestBody ImportParam importParam, HttpServletRequest request){
//        PermAccount permAccount = getCurrentPermAccount(request);
        DataLoader loader = new DataLoader();
        String path = "";
//        if(StorageTypeEnum.DATA_STORAGE_TYPE_YSSJ.getId().equals(importParam.getStorageType())){ //再加工数据
//            path = CephUtils.getCephCollectionImport();
//        }else if(StorageTypeEnum.DATA_STORAGE_TYPE_CGSJ.getId().equals(importParam.getStorageType())){ //原始数据
//            path = CephUtils.getCephReprocessImport();
//        }
        path = CephUtils.getAbsolutePath("");
        importParam.setPath(path);
//        if(permAccount != null){
//            loader.setCreator(permAccount.getAccountId());
//            loader.setCreatorName(permAccount.getNickName());
//        }

        loader.setCreator(getCurrentAccountId());
        loader.setCreatorName(getCurrentNickName());

        ResultMessage result = dataLoaderService.convertToDataLoadTask(loader, importParam);
        return result;
    }

    @PostMapping("checkNull")
    public ResultMessage checkNull(@RequestBody ImportParam importParam){
        return dataLoaderService.checkNull(importParam.getPath());
    }


    /**
     * 从ceph服务器的指定目录获取数据列表文件夹详情
     * @return
     * @version <1> 2018-03-12 wl : Created.
     */
    @ApiOperation(value = "获取ceph服务器的指定目录的文件列表文件夹详情", notes = "获取ceph服务器的指定目录的文件列表文件夹详情")
    @ApiImplicitParam(name = "importParam", value="文件夹路径", required = true, dataType = "ImportParam")
    @PostMapping("queryImportDataDetail")
    public ResultMessage queryImportDataDetail(@RequestBody ImportParam importParam){
        String suffixs = "";
        if(StorageTypeEnum.DATA_STORAGE_TYPE_YSSJ.getId().equals(importParam.getStorageType())){ //原始数据
            //suffixs = CephUtils.getSuffixs();
        }else if(StorageTypeEnum.DATA_STORAGE_TYPE_CGSJ.getId().equals(importParam.getStorageType())){ //再加工数据
//            suffixs = CephUtils.getSuffixs();
        }else if(StorageTypeEnum.DATA_STORAGE_TYPE_SLSJ.getId().equals(importParam.getStorageType())){ //矢量数据
            suffixs = CephUtils.getDataStaticSuffixs();

        }
        importParam.setPath(importParam.getFileName());
        return dataLoaderService.findImportDataDetail(importParam, suffixs);
    }


    /**
     * 开始入库任务
     * @param loaderId
     * @return ResultMessage
     * @version<1> 2018-03-14 wl : Created.
     */
    @ApiOperation(value = "重新入库",notes = "文件重新入库")
    @ApiImplicitParam(name = "loaderId",value = "入库任务ID",required = true,dataType = "Integer")
    @PostMapping("reload")
    public ResultMessage reload(@RequestParam Integer loaderId, HttpServletRequest request){

        DataLoader dataLoader = new DataLoader();
        dataLoader.setLoaderId(loaderId);
//        PermAccount permAccount = getCurrentPermAccount(request);
//        if(permAccount != null){
//            dataLoader.setModifier(permAccount.getAccountId());
//            dataLoader.setModifierName(permAccount.getNickName());
//        }

        dataLoader.setModifier(getCurrentAccountId());
        dataLoader.setModifierName(getCurrentNickName());

        ResultMessage result = dataLoaderService.reloadFile(dataLoader);

        return result;
    }

    /**
     * @description: 根据ID查询入库任务信息
     * @param request
     * @param loaderId
     * @return
     * @version <1> 2018-03-22 wl : created.
     */
    @ApiOperation(value = "入库任务查询",notes = "按入库任务主键查询")
    @ApiImplicitParam(name = "loaderId",value = "入库任务主键",required = true,paramType = "query", dataType = "Integer")
    @PostMapping("/getById")
    public ResultMessage getdById(HttpServletRequest request, @RequestParam Integer loaderId){
        return dataLoaderService.findById(loaderId);
    }


    /**
     * 从ceph服务器的指定目录获取数据列表文件夹详情
     * @return
     * @version <1> 2018-03-12 wl : Created.
     */
    @ApiOperation(value = "获取入库文件详情", notes = "获取入库文件详情")
    @ApiImplicitParam(name = "loaderId", value="入库订单id", required = true, dataType = "Integer")
    @PostMapping("queryLoaderDetail")
    public ResultMessage queryLoaderDetail(HttpServletRequest request, @RequestParam Integer loaderId){
        return dataLoaderService.findDataLoaderDetailById(loaderId);
    }


    /**
     * Description: 当天入库数据统计
     * @param loaderParam
     * @return ResultMessage
     * @version <1> 2018/6/26 21:30 zhangshen: Created.
     */
    @ApiOperation(value = "当天入库数据统计",notes = "当天入库数据统计")
    @ApiImplicitParam(name = "loaderParam",value = "入库任务对象",required = false,dataType = "LoaderParam")
    @PostMapping("/nolog/queryLoaderSateSum")
    public ResultMessage queryLoaderSateSum(@RequestBody LoaderParam loaderParam){
        return dataLoaderService.queryLoaderSateSum(loaderParam);
    }


    /**
     * Description: 当天入库数据统计table
     * @param loaderParam
     * @return ResultMessage
     * @version <1> 2018/6/26 22:42 zhangshen: Created.
     */
    @ApiOperation(value = "当天入库数据统计table",notes = "当天入库数据统计table")
    @ApiImplicitParam(name = "loaderParam",value = "入库任务对象",required = false,dataType = "LoaderParam")
    @PostMapping("/nolog/queryLoaderSateSumTable")
    public ResultMessage queryLoaderSateSumTable(@RequestBody LoaderParam loaderParam){
        return dataLoaderService.queryLoaderSateSumTable(loaderParam);
    }

    /**
     * Description: 获取大屏显示所需的数据(入库和下载数据)
     * @param
     * @return
     * @version <1> 2018/8/16 13:57 zhangshen: Created.
     */
    @PostMapping("/nolog/getEchartsShowData")
    public ResultMessage getEchartsShowData(){
        return dataLoaderService.getEchartsShowData();
    }

    /**
     * Description: 存储使用情况
     * @param
     * @return
     * @version <1> 2018/8/17 13:43 zhangshen: Created.
     */
    @PostMapping("/nolog/getEchartsShowData2")
    public ResultMessage getEchartsShowData2(){
        return dataLoaderService.getEchartsShowData2();
    }

}
