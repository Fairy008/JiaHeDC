package com.jh.manage.loader.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.manage.download.entity.DataDownload;
import com.jh.manage.download.service.IDataDownloadService;
import com.jh.manage.loader.Enum.LoaderEnum;
import com.jh.manage.loader.Enum.LoaderStatusEnum;
import com.jh.manage.loader.Enum.LoaderTypeEnum;
import com.jh.manage.loader.entity.DataLoader;
import com.jh.manage.loader.entity.DataLoaderDetail;
import com.jh.manage.loader.mapping.IDataLoaderMapper;
import com.jh.manage.loader.model.ImportParam;
import com.jh.manage.loader.model.LoaderParam;
import com.jh.manage.loader.service.IDataLoaderService;
import com.jh.manage.storage.Enum.StorageTypeEnum;
import com.jh.util.ArithUtil;
import com.jh.util.DateUtil;
import com.jh.util.LinuxStateForShell;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FilenameFilter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:数据入库处理
 * 1.下载任务转换为入库任务
 * 2.数据导入转化为入库任务
 * 3.执行入库任务
 * 4.入库任务查询
 * 5.根据入库任务ID查询入库明细
 *
 * @version <1> 2018-01-29 lcw： Created.
 */
@Service
@Transactional
public class DataLoaderServiceImpl extends BaseServiceImpl<LoaderParam, DataLoader, Integer> implements IDataLoaderService {

    @Autowired
    private IDataLoaderMapper dataLoaderMapper;

    @Autowired
    private IDataDownloadService dataDownloadService;

    @Override
    protected IBaseMapper<LoaderParam, DataLoader, Integer> getDao() {
        return dataLoaderMapper;
    }

    /**
     * 将下载任务转化为入库任务
     *     1.下载任务记录转化为入库任务记录
     * @param  downloadObj:
     * @return ResultMessage :
     * @version <1> 2018-03-01 lcw:Created.
     */
    @Override
    public ResultMessage convertToDataLoadTask(DataDownload downloadObj) {
        ResultMessage result = ResultMessage.fail();
        if(downloadObj != null){
            DataLoader dataLoader = new DataLoader();
            dataLoader.setLoaderName(getLoaderName(LoaderTypeEnum.DATA_LOADER_TYPE_DOWNLOAD)); //任务名称
            dataLoader.setDataStoragePath(downloadObj.getStoragePath()); //数据存储位置（入库数据来源）
            dataLoader.setLoaderType(LoaderTypeEnum.DATA_LOADER_TYPE_DOWNLOAD.getId()); //自动入库
            dataLoader.setLoaderStatus(LoaderStatusEnum.DATA_LOADER_STATUS_READY.getId());//待入库
            dataLoader.setStorageType(StorageTypeEnum.DATA_STORAGE_TYPE_YSSJ.getId()); //原始数据
            dataLoader.setTotalNum(downloadObj.getTotalNum()); //总数
            dataLoader.setCreator(downloadObj.getCreator()); //创建人
            dataLoader.setCreatorName(downloadObj.getCreatorName());
            dataLoaderMapper.save(dataLoader);
            result = ResultMessage.success();
        }
        return result;
    }



    @Override
    public ResultMessage checkNull(String path) {
        if(StringUtils.isBlank(path)){
            return ResultMessage.fail("待导入路径下无文件，无法入库");
        }

        List<File> list = CephUtils.listAllFilesForDG(CephUtils.getAbsolutePath(path), null);
        if(list != null && list.size() > 0){
            return ResultMessage.success();
        }else{

            return ResultMessage.fail("待导入路径下无文件，无法入库");
        }

    }


    /**
     * 将导入任务转化为入库任务
     *  对入库文件夹进行标记（加入前缀）
     * @param  dataLoader:入库文件夹在存储中的名称
     * @return ResultMessage :
     * @version <1> 2018-03-01 lcw:Created.
     */
    @Override
    public ResultMessage convertToDataLoadTask(DataLoader dataLoader, ImportParam importParam) {
        ResultMessage result = ResultMessage.fail();
        if(dataLoader != null && StringUtils.isNotBlank(importParam.getFileName())){
            dataLoader.setDataStoragePath(importParam.getFileName());
            if(importParam.getFileType() == 1){ //文件夹

                List<File> list = CephUtils.listAllFilesForDG(importParam.getPath() + File.separator + importParam.getFileName(), null);

                if(list != null && list.size() > 0){
                    //获取文件数量
                    int filesNum = list.size();
                    dataLoader.setTotalNum(filesNum);
                }else{
                    result = ResultMessage.fail("待导入路径中无文件，无法转换为入库任务");
                    return result;
                }
            }else{
                dataLoader.setTotalNum(1);
            }

            dataLoader.setLoaderName(getLoaderName(LoaderTypeEnum.DATA_LOADER_TYPE_IMPORT));
            //保存数据
            dataLoader.setLoaderType(LoaderTypeEnum.DATA_LOADER_TYPE_IMPORT.getId()); //数据导入
            dataLoader.setLoaderStatus(LoaderStatusEnum.DATA_LOADER_STATUS_READY.getId());//待入库
            dataLoader.setStorageType(importParam.getStorageType()); //数据类型
            //再加工数据
//            if(StorageTypeEnum.DATA_STORAGE_TYPE_CGSJ.getId().intValue() == importParam.getStorageType()){
//                try {
//                    dataLoader.setRegionId(importParam.getRegionId());
//                    dataLoader.setSatellite(importParam.getSatellite());
//                    dataLoader.setSensor(importParam.getSensor());
//                    dataLoader.setWords(importParam.getWords());
//                    if(StringUtils.isNotBlank(importParam.getDataTime())){
//                        DateFormat dd=new SimpleDateFormat("yyyy-MM-dd");
//                        dataLoader.setDataTime(dd.parse(importParam.getDataTime()));
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }

            //操作Linux，将文件（夹）添加前缀
            String importFileName  = CephUtils.setPrefix(importParam.getPath() , importParam.getFileName() , CephUtils.getCephCollectionImportPrefix());
            if(importFileName != null){

                dataLoader.setDataStoragePath(importFileName);

                dataLoaderMapper.save(dataLoader);
                result = ResultMessage.success("成功转换为入库任务");
            }else{
                result = ResultMessage.fail(LoaderEnum.DATA_LOADER_PREFIX_ERROR.getValue(), LoaderEnum.DATA_LOADER_PREFIX_ERROR.getMessage());
            }
        }else{
            result = ResultMessage.fail(LoaderEnum.DATA_LOADER_STORAGE_PATH_NULL.getValue(), LoaderEnum.DATA_LOADER_STORAGE_PATH_NULL.getMessage());
        }
        return result;
    }

    /**
     * 获取任务名称
     * @param loaderTypeEnum
     * @return
     */
    private String getLoaderName(LoaderTypeEnum loaderTypeEnum){
        return  loaderTypeEnum.getMsg() + "_" + DateUtil.dateToString(new java.util.Date(),"yyyyMMddHHmmss");

    }

    /**
     * 根据入库任务ID获取入库任务明细列表
     * @param  dataLoaderId:
     * @return ResultMessage:
     * @version <1> 2018-03-01 lcw:Created.
     */
    @Override
    public ResultMessage findDataLoaderDetailById(Integer dataLoaderId) {
        ResultMessage result = ResultMessage.fail();
        if(dataLoaderId == null){
            result = ResultMessage.fail(LoaderEnum.DATA_LOADER_ID_NULL.getValue(), LoaderEnum.DATA_LOADER_ID_NULL.getMessage());
        }else{
            List<DataLoaderDetail> list = dataLoaderMapper.findDataLoaderDetailById(dataLoaderId);
            result = ResultMessage.success(list);
        }

        return result;
    }

    /**
     * 执行数据入库任务
     * 1.任务状态从失败状态更新为正在入库中
     * 2.将入库明细数据进行更新
     * 		1）重新读取元数据并入库（检查元数据是否入库，若入库，则需要覆盖）
     * 		2）复制缩略图至指定存放位置（若存在该缩略图， 则直接覆盖，若不存在，则直接移入）
     * 		3）复制影像至指定存放位置
     * 3.入库明细全部完成入库后，任务状态更新为入库完成
     *    入库若失败，则需记录告警信息，插入告警信息表中
     * 4.删除临时目录数据
     * @version <1> 2018-03-01 lcw:Created.
     */
    @Override
    public ResultMessage startDataLoaderTask(DataLoader loader) {

        ResultMessage result = null;
        if(loader.getLoaderId() == null){
            result = ResultMessage.fail(LoaderEnum.DATA_LOADER_ID_NULL.getValue(), LoaderEnum.DATA_LOADER_ID_NULL.getMessage());
            return result;
        }

        DataLoader dataLoader = dataLoaderMapper.getById(loader.getLoaderId());//根据ID查询入库任务

        //更新入库状态为入库中
        dataLoader.setLoaderStatus(LoaderStatusEnum.DATA_LOADER_STATUS_ON.getValue());
        dataLoaderMapper.updateStatus(dataLoader);

        //调用python脚本，执行数据入库


        List<DataLoaderDetail> details = new ArrayList<DataLoaderDetail>();
        //批量保存入库明细
        dataLoaderMapper.saveOrderDetail(details);


        //更新入库状态为入库成功或入库失败
        dataLoader.setLoaderStatus(LoaderStatusEnum.DATA_LOADER_STATUS_OK.getValue());
        dataLoaderMapper.updateStatus(dataLoader);

        //如果入库失败，插入告警消息


        return result;
    }

    /**
     * 入库任务列表分页查询
     * @param loaderParam
     * @return
     * @version<1> 2018-03-01 lcw : Created.
     */
    @Override
    public PageInfo<DataLoader> findByPage(LoaderParam loaderParam) {
        PageHelper.startPage(loaderParam.getPage(), loaderParam.getRows());

        List<DataLoader> list = dataLoaderMapper.findByPage(loaderParam);

        return new PageInfo<DataLoader>(list);
    }

    /**
     * 从ceph服务器中获取未转化为入库任务的待导入数据列表
     * 	1.指定目录：从config.properties中获取
     * 	2.过滤文件名包含“import_"前缀的文件或文件夹
     * @return
     * @version<1> 2018-03-07 lcw : Created.
     */
    @Override
    public PageInfo<ImportParam> findImportDataFromCeph(ImportParam importParam) {

        List<ImportParam> dir = list(importParam.getPath(),importParam.getIgnorePrefix());

        return setPage(importParam,dir);

    }

    @Override
    public ResultMessage findImportDataDetail(ImportParam importParam, String suffixs) {

        List<Map<String,Object>> list = CephUtils.listAllFiles(CephUtils.getAbsolutePath(importParam.getPath()), suffixs);
        return ResultMessage.success(list);
    }

    @Override
    public ResultMessage reloadFile(DataLoader dataLoader) {
        dataLoader.setLoaderStatus(LoaderStatusEnum.DATA_LOADER_STATUS_READY.getId());
        dataLoaderMapper.updateStatus(dataLoader);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage findById(Integer loaderId) {
        DataLoader dataLoader = dataLoaderMapper.getById(loaderId);//根据ID查询入库任务
        return ResultMessage.success(dataLoader);
    }


    /**
     * 获取文件路径下的所有文件
     * @param path
     * @return
     * @version<1> 2018-03-08 lcw : Created.
     */
    private List<ImportParam> getFiles(String path ){
        List<ImportParam> list = new ArrayList<ImportParam>();
        File file = new File(path);
        File[] files = file.listFiles();

        if(files != null && files.length > 0){
            for(File obj : files){
                long fileSize = obj.length();
                String fileName = obj.getName();
                ImportParam importParam = new ImportParam();
                importParam.setFileName(fileName);
                importParam.setFileSize(fileSize);
                list.add(importParam);
            }
        }
        return list;
    }


    /**
     * 遍历指定文件夹下的文件和文件夹
     * @param path ： 访问路径
     * @param ignorePrefix : 过滤以ignorePrefix为前缀的文件
     * @return
     * @version<1> 2018-03-07 lcw : Created.
     */
    private  List<ImportParam> list(String path, String ignorePrefix){

        List<ImportParam> list = new ArrayList<ImportParam>();
        File file = new File(CephUtils.getAbsolutePath(path));
        file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {

                if(name.startsWith(ignorePrefix)){
                    return false;
                }
                ImportParam importParam = new ImportParam();
                importParam.setFileName(name);
//                importParam.setFileType(bool ? 1 : 2);
//                importParam.setFileTypeName(bool ? "文件夹" : "文件");
                importParam.setFileName(path + File.separator + name);
//                importParam.setPath(dir.getAbsolutePath()+File.separator+name);
                boolean bool = new File(dir.getAbsolutePath() + File.separator + name).isDirectory();
                if(bool){
                    importParam.setFileType(1);
                    importParam.setFileTypeName("文件夹");
                    list.add(importParam);
                }
                return true;
            }
        });
        return list;
    }

    /**
     * 设置分页信息
     * @param dir
     * @version<1> 2018-03-07 lcw : Created.
     */
    private PageInfo<ImportParam> setPage(ImportParam importParam, List<ImportParam> dir){
        PageInfo<ImportParam> pageInfo= new PageInfo<ImportParam>();
        if(dir != null && dir.size() > 0 && importParam != null){
            if(importParam.getRows() == 0 ){
                importParam.setRows(10);
            }
            int pages = dir.size() / importParam.getRows() + ( (dir.size() % importParam.getRows()) == 0 ? 0 : 1 );

            //开始条
            int startRow =( importParam.getPage() - 1 ) * importParam.getRows() + 1 ;

            int endRow = importParam.getPage() * importParam.getRows();

            List<ImportParam> list = new ArrayList<ImportParam>();
            for(int i = 0 ; i < dir.size() ; i++){
                if(startRow <= i+1 && i+1 <= endRow){
                    dir.get(i).setStorageType(importParam.getStorageType());
                    list.add(dir.get(i));
                }
            }
            pageInfo.setList(list);
            pageInfo.setStartRow(startRow);
            pageInfo.setEndRow(endRow);
            pageInfo.setTotal(dir.size());
            pageInfo.setPages(pages);
        }
        return pageInfo;
    }


    /**
     * Description: 当天入库数据统计
     * @param loaderParam
     * @return ResultMessage
     * @version <1> 2018/6/26 21:30 zhangshen: Created.
     */
    @Override
    public ResultMessage queryLoaderSateSum(LoaderParam loaderParam) {
        //入库数据统计
        LocalDate localDate = LocalDate.now();
        loaderParam.setBeginTime(localDate.toString());
        loaderParam.setEndTime(localDate.toString());
        List<Map<String, Object>> totalLoader = dataLoaderMapper.queryLoaderSateSumEcharts(loaderParam);
        Map map=new HashMap();
        map.put("totalLoader",totalLoader);//入库数据统计
        return ResultMessage.success(map);
    }


    /**
     * Description: 当天入库数据统计table
     * @param loaderParam
     * @return ResultMessage
     * @version <1> 2018/6/26 22:42 zhangshen: Created.
     */
    @Override
    public ResultMessage queryLoaderSateSumTable(LoaderParam loaderParam) {
        //入库数据统计
        LocalDate localDate = LocalDate.now();
        loaderParam.setBeginTime(localDate.toString());
        loaderParam.setEndTime(localDate.toString());
        List<Map<String, Object>> list = dataLoaderMapper.queryLoaderSateSumTableEcharts(loaderParam);
        Map map=new HashMap();
        map.put("list",list);//入库数据统计
        return ResultMessage.success(map);
    }

    /**
     * Description: 获取大屏显示所需的数据(入库和下载数据)
     * @param
     * @return
     * @version <1> 2018/8/16 13:57 zhangshen: Created.
     */
    @Override
    public ResultMessage getEchartsShowData() {
        Map<String, Object> data = new HashMap<String, Object>();

        //1.获取七天GF1~GF4入库数量
        List<Map<String, Object>> listsLoaderSeven = new ArrayList<Map<String, Object>>();
        Integer loaderTotalToday = 0;
        Integer loaderTotalYesterday = 0;
        for (int i=0; i<7; i++) {
            LoaderParam loaderParam = new LoaderParam();
            LocalDate localDate = LocalDate.now();
            localDate = localDate.minusDays(i);//减i天
            loaderParam.setBeginTime(localDate.toString());
            loaderParam.setEndTime(localDate.toString());
            List<Map<String, Object>> list = dataLoaderMapper.queryLoaderEcharts(loaderParam);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("loaderDataList", list);
            listsLoaderSeven.add(map);
            if(i==0){//今天
                for (Map<String, Object> m : list) {
                    loaderTotalToday += ((Long)m.get("value")).intValue();
                }
            }
            if(i==1){//昨天
                for (Map<String, Object> m : list) {
                    loaderTotalYesterday += ((Long)m.get("value")).intValue();
                }
            }
        }
        data.put("listsLoaderSeven", listsLoaderSeven);

        //2.获取七天GF1~GF4下载数量
        List<Map<String, Object>> listsDownloadSeven = new ArrayList<Map<String, Object>>();
        Integer downloadTotalToday = 0;
        Integer downloadTotalYesterday = 0;
        for (int i=0; i<7; i++) {
            LocalDate localDate = LocalDate.now();
            localDate = localDate.minusDays(i);//减i天
            List<Map<String, Object>> list = dataDownloadService.getEchartsDownloadData(localDate.toString(), localDate.toString());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dataDownDataList", list);
            listsDownloadSeven.add(map);
            if(i==0){//今天
                for (Map<String, Object> m : list) {
                    downloadTotalToday += ((Long)m.get("value")).intValue();
                }
            }
            if(i==1){//昨天
                for (Map<String, Object> m : list) {
                    downloadTotalYesterday += ((Long)m.get("value")).intValue();
                }
            }
        }
        data.put("listsDownloadSeven", listsDownloadSeven);

        //3.获取入库历史总数量
        Integer loaderTotal = dataLoaderMapper.queryLoaderEchartsTotal();
        data.put("loaderTotal", loaderTotal);

        //4.获取下载历史总数量
        Integer downloadTotal = dataDownloadService.queryDownloadEchartsTotal();
        data.put("downloadTotal", downloadTotal);

        //5.获取今天入库数量
        data.put("loaderTotalToday", loaderTotalToday);
        //6.获取昨天入库数量
        data.put("loaderTotalYesterday", loaderTotalYesterday);
        //7.获取今天下载数量
        data.put("downloadTotalToday", downloadTotalToday);
        //8.获取昨天下载数量
        data.put("downloadTotalYesterday", downloadTotalYesterday);

        /*//9.查询存储总量
        data.put("total", 1000);
        //10.查询存储剩余量
        data.put("surplus", 600);
        //11.查询存储已使用百分比
        data.put("use", (1000-600)*100/1000);*/

        return ResultMessage.success(data);
    }

    /**
     * Description: 存储使用情况
     * @param
     * @return
     * @version <1> 2018/8/17 13:43 zhangshen: Created.
     */
    @Override
    public ResultMessage getEchartsShowData2() {
        Map<String, Object> data = new HashMap<String, Object>();
        try {
             data = LinuxStateForShell.getInfo();//查询存储
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        return ResultMessage.success(data);
    }
}
