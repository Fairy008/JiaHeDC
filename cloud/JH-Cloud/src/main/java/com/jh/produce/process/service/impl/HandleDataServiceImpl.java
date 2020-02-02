package com.jh.produce.process.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.enums.DataStatusEnum;
import com.jh.enums.DelStatusEnum;
import com.jh.manage.storage.Enum.StorageTypeEnum;
import com.jh.manage.storage.entity.DataStorage;
import com.jh.manage.storage.service.IDataStorageService;
import com.jh.produce.process.Enum.ExecStatusEnum;
import com.jh.produce.process.Enum.StepFileIoTypeEnum;
import com.jh.produce.process.entity.*;
import com.jh.produce.process.mapping.IHandleDataMapper;
import com.jh.produce.process.mapping.IHandleStepFileMapper;
import com.jh.produce.process.mapping.IHandleStepMapper;
import com.jh.produce.process.model.*;
import com.jh.produce.process.service.IHandleConfigService;
import com.jh.produce.process.service.IHandleDataService;
import com.jh.util.DateUtil;
import com.jh.util.ceph.CephUtils;
import com.jh.util.ceph.LinkUtil;
import com.jh.vo.ResultMessage;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

/**
 * description:数据处理实现类
 *
 * @version <1> 2018-01-29 lcw： Created.
 */
@Service
@Transactional
public class HandleDataServiceImpl extends BaseServiceImpl<HandleDataParam, HandleData, Integer> implements IHandleDataService {
    private Logger logger = Logger.getLogger(HandleDataServiceImpl.class);

    @Autowired
    private IHandleDataMapper handleDataMapper;

    @Autowired
    private IDataStorageService dataStorageService;

    @Autowired
    private IHandleConfigService handleConfigService;

    @Autowired
    private IHandleStepMapper handleStepMapper;

    @Autowired
    private IHandleStepFileMapper handleStepFileMapper;

//    @Autowired
//    private IPermPersonService permPersonService;

    @Override
    protected IBaseMapper<HandleDataParam, HandleData, Integer> getDao() {
        return handleDataMapper;
    }

    //创建LINK
    LinkUtil linkUtil = LinkUtil.getInstance();


    /**
     * 添加数据处理任务
     * @param handleData
     * @return
     * @version<1> 2018-03-04 cxj : Created.
     * @version<2> 2018-05-10 lcw : 改造该方法，relate_task_storage表不使用storageId,直接使用文件路径进行操作
     */
    @Override
    public ResultMessage saveTask(HandleData handleData) {
        try{

            //为啥需要在数据处理功能中判断改用户是否属于外部用户？
//            ResultMessage resultMessage = permPersonService.findPersonByAccountId(handleData.getCreator());
//            PermPerson permPerson = (PermPerson) resultMessage.getData();
//
//            if(permPerson.getPersonType().intValue() == PersonTypeEnum.PERSON_TYPE_OUTER.getId().intValue()){
//                return ResultMessage.success(UserEnum.USER_NOT_PERM_ACCESS.getValue(),UserEnum.USER_NOT_PERM_ACCESS.getMesasge());
//            }
            handleData.setCreateTime(LocalDateTime.now());
            handleData.setHandleName(getTaskName(handleData.getChinaName(), handleData.getStorageType()));
            handleDataMapper.save(handleData);

            String handlePathLink = CephUtils.makerHandleDirectory(handleData.getHandleId());
            handleData.setStorageUrl(handlePathLink);
            handleDataMapper.update(handleData);

            //数据处理任务包含的原始数据的URL
            List<RelateTaskStorage> relateTaskStorageList = handleData.getRelateTaskStorageList();
            if(relateTaskStorageList == null){
                return ResultMessage.fail("未检索到原始数据，数据处理任务创建失败");
            }
            List<String> list = new ArrayList<>();
            if(relateTaskStorageList != null && relateTaskStorageList.size() > 0){
                for(RelateTaskStorage relateTaskStorage : relateTaskStorageList){
                    String storageUrl = relateTaskStorage.getFilePathLink();
                    String fileName = storageUrl.replace("\\", "/").substring(storageUrl.replace("\\", "/").lastIndexOf("/") + 1);
                    //根据storageId查询对应的源数据，然后将存储路径赋值到storageUrl
                    ResultMessage storageResult = dataStorageService.findStorageById(relateTaskStorage.getStorageId());

                    if(storageResult.isFlag()){
                        DataStorage storage = (DataStorage)storageResult.getData();
                        storageUrl = storage.getStorageUrl();

                    }

                    String dataPath = CephUtils.getOrderLink(handlePathLink,fileName);
                    //创建可在linux系统的可执行link命令
                    String link= linkUtil.makeLink(storageUrl, dataPath);
                    list.add(link);
                    relateTaskStorage.setHandleId(handleData.getHandleId());
                    relateTaskStorage.setFilePathLink(dataPath); //将最后得到的link链接保存
                }
            }
            boolean bool = linkUtil.exec(list); //执行link命令
            if(bool){
                handleDataMapper.saveTaskStorageRelate(relateTaskStorageList);
            }else{
                return ResultMessage.fail("数据处理任务创建失败");
            }


//            List<Integer> storageIdList = Arrays.asList(handleData.getStorageIdArray());
//            List<DataStorage> storageList = dataStorageService.findStorageByIdList(storageIdList);
//            List<String> list = new ArrayList<>();
//            Map<Integer,String> relateMap = new HashMap<Integer,String>();
//            for (DataStorage storage : storageList){
//                String reworkFilePath=storage.getStorageUrl();
//                String fileName = reworkFilePath.substring(reworkFilePath.lastIndexOf("\\")+1);
//                //link文件存储的相对路径，该字段存在数据库中
//                String dataPath = CephUtils.getOrderLink(handlePathLink,fileName);
//                //创建可在linux系统的可执行link命令
//                String link= linkUtil.makeLink(reworkFilePath, dataPath);
//                list.add(link);
//                relateMap.put(storage.getStorageId(),dataPath);
//            }
//            linkUtil.exec(list); //执行link命令
//            handleData.setRelateStorageLink(relateMap);
//            handleDataMapper.saveTaskStorageRelate(handleData);
            return ResultMessage.success();
        }catch (Exception e){
            logger.error("HandleDataServiceImpl saveTask method :" + e.getMessage());
            return ResultMessage.fail();
        }
    }

    @Override
    public PageInfo<HandleData> taskPage(HandleDataParam handleDataParam) {
        PageHelper.startPage(handleDataParam.getPage(),handleDataParam.getRows());
        //PageHelper.orderBy(handleDataParam.getSidx() + " " + handleDataParam.getSord());
        List<HandleData> list = handleDataMapper.taskPage(handleDataParam);
        return new PageInfo<HandleData>(list);
    }

    @Override
    public ResultMessage findTaskParams(HandleDataParam handleDataParam) {
        try{
            Map<String,Object> resultMap = new HashMap<String,Object>();

            HandleData handleData = handleDataMapper.getById(handleDataParam.getHandleId());
            /*if (StringUtils.isNotEmpty(handleData.getStorageUrl())) {
                handleData.setStorageUrl(CephUtils.getAbsolutePath(handleData.getStorageUrl()));
            }*/
            resultMap.put("handleData",handleData);


            /*//List<String> handleFileLinks = handleDataMapper.findHandleFileLink(handleDataParam.getHandleId());
            List<String> handleFileNameList = new ArrayList<String>();//指定路径下的所有文件名称
            if (StringUtils.isNotEmpty(handleData.getStorageUrl())) {
                List<Map<String, Object>> handleFileLinks = CephUtils.listAllFiles(handleData.getStorageUrl(), null);//获取指定路径下的所有文件
                for (Map<String, Object> map : handleFileLinks) {
                    handleFileNameList.add(map.get("fileName").toString());
                }
            }
            resultMap.put("handleFileLinks",handleFileNameList);*/

//            List<DataStorage> dataStorageList = dataStorageService.findTaskRelateStorageByHandleId(handleDataParam.getHandleId());
//            resultMap.put("dataStorageList",dataStorageList);

            List<HandleStep> handleStepList = handleStepMapper.findListByHandleId(handleDataParam.getHandleId());

            List<HashMap<Object,Object>> stepRelateDataStorageList = dataStorageService.findStepRelateStorageListByHandleId(handleDataParam.getHandleId());

            for(HandleStep hs : handleStepList) {
                List<DataParam> dataParamList = new ArrayList<DataParam>();
                for (HashMap<Object, Object> hm : stepRelateDataStorageList) {
                    if(hs.getHandleDetailId().intValue() == Integer.parseInt(hm.get("handledetailid").toString())){
                        DataParam dataParam = new DataParam();
                        Object fileName = hm.get("filename");
                        if(fileName != null) {
                            dataParam.setFileName(fileName.toString());
                        }
                        Object handleDetailId = hm.get("handledetailid");
                        if(handleDetailId != null) {
                            dataParam.setHandleDetailId(Integer.parseInt(handleDetailId.toString()));
                        }
                        Object ioType = hm.get("iotype");
                        if(ioType != null) {
                            dataParam.setIoType(Integer.parseInt(ioType.toString()));
                        }
                        dataParamList.add(dataParam);
                    }
                }
                hs.setDataParamList(dataParamList);
            }

            resultMap.put("handleStepList",handleStepList);

            List<HandleConfig> handleConfigList = handleConfigService.findHandlesBySatId(handleDataParam.getSatId());
            resultMap.put("handleConfigList", handleConfigList);

            return ResultMessage.success(resultMap);
        }catch (Exception e){
            logger.error("HandleDataServiceImpl findTaskParams method :" + e.getMessage());
            return ResultMessage.fail();
        }
    }


    /**
     * 查询要处理的数据及参数信息,数据信息是查询文件夹
     * @param handleDataParam
     * @return
     * @version <1> 2019-10-08 cxw： Created.
     */
    @Override
    public ResultMessage findHandleFileInfo(HandleDataParam handleDataParam) {
        try{
            Map<String,Object> resultMap = new HashMap<String,Object>();

            HandleData handleData = handleDataMapper.getById(handleDataParam.getHandleId());

            List<String> handleFileNameList = new ArrayList<String>();//指定路径下的所有文件名称
            if (StringUtils.isNotEmpty(handleData.getStorageUrl())) {
                List<Map<String, Object>> handleFileLinks = CephUtils.listAllFiles(CephUtils.getAbsolutePath(CephUtils.changePathSlash(handleData.getStorageUrl())), null);//获取指定路径下的所有文件
                for (Map<String, Object> map : handleFileLinks) {
                    handleFileNameList.add(map.get("fileName").toString());
                }
            }

            if (StringUtils.isNotEmpty(handleData.getStorageUrl())) {
                handleData.setStorageUrl(CephUtils.getShowPath(CephUtils.changePathSlash(handleData.getStorageUrl())));
            }
            resultMap.put("handleData",handleData);
            resultMap.put("handleFileLinks",handleFileNameList);

            return ResultMessage.success(resultMap);
        }catch (Exception e){
            logger.error("HandleDataServiceImpl findHandleFileInfo method :" + e.getMessage());
            return ResultMessage.fail();
        }
    }

    /**
     * 查询要处理的数据及参数信息,数据信息是查询数据库
     * @param handleDataParam
     * @return
     * @version <1> 2019-10-08 cxw： Created.
     */
    @Override
    public ResultMessage findHandleDataInfo(HandleDataParam handleDataParam) {
        try{
            Map<String,Object> resultMap = new HashMap<String,Object>();

            HandleData handleData = handleDataMapper.getById(handleDataParam.getHandleId());
            List<HandleRelateDataParam> handleFilePathList = new ArrayList<HandleRelateDataParam>();//指定路径下的所有文件名称
            handleFilePathList = handleDataMapper.findHandleFileLinkList(handleData.getHandleId());

            if (StringUtils.isNotEmpty(handleData.getStorageUrl())) {
                handleData.setStorageUrl(CephUtils.getShowPath(CephUtils.changePathSlash(handleData.getStorageUrl())));
            }
            resultMap.put("handleData",handleData);
            resultMap.put("handleFileLinks",handleFilePathList);

            return ResultMessage.success(resultMap);
        }catch (Exception e){
            logger.error("HandleDataServiceImpl findHandleFileInfo method :" + e.getMessage());
            return ResultMessage.fail();
        }
    }

    /**
     * 批量保存步骤相关文件信息
     * @param handleStepList
     * @param pendingDataPathList
     * @param handleStep
     * @return
     * @version <1> 2018-03-20 cxj： Created.
     */
    private void batchSaveForHandleStepFile(List<HandleStep> handleStepList, List<String> pendingDataPathList, HandleStep handleStep){
        if(!handleStepList.isEmpty()) {
            List<HandleStepFile> hsfList = new ArrayList<HandleStepFile>();
            for(String fileLink : pendingDataPathList){
                HandleStepFile hsf = new HandleStepFile();
                hsf.setHandleDetailId(handleStep.getHandleDetailId());
                hsf.setFilePath(fileLink);
                hsf.setIoType(StepFileIoTypeEnum.INPUT.getKey());
                hsfList.add(hsf);
            }
            handleStepFileMapper.saveForHandleStepFileList(hsfList);
        }
    }

    @Override
    public ResultMessage addHandleData(DataStepParam dataStepParam) {
        try {
            Integer satId = dataStepParam.getSatId();
            Integer handleId = dataStepParam.getHandleId();
            Long regionId = dataStepParam.getRegionId();
            String satName = dataStepParam.getSatName();

            HandleData handleData = new HandleData();
            handleData.setStartTime(LocalDateTime.now());
            handleData.setHandleId(handleId);
            handleData.setRegionId(regionId);
            handleData.setSatId(satId);
            handleData.setSatName(satName);

//            RelateTaskStorageParam[] pendingDataArray = dataStepParam.getPendingDataList();
//            List<String> pendingDataPathList = getPendingDataPathList(pendingDataArray,handleData);
            //List<String> pendingDataPathList = handleDataMapper.findHandleFileLink(dataStepParam.getHandleId());
            HandleData handleDataObj = handleDataMapper.getById(handleId);
            List<String> pendingDataPathList = new ArrayList<String>();//指定路径下的所有文件
            if (StringUtils.isNotEmpty(handleDataObj.getStorageUrl())) {
                List<Map<String, Object>> handleFileLinks = CephUtils.listAllFiles(CephUtils.getAbsolutePath(CephUtils.changePathSlash(handleDataObj.getStorageUrl())), null);//获取指定路径下的所有文件
                for (Map<String, Object> map : handleFileLinks) {
                    pendingDataPathList.add(CephUtils.changePathSlash(handleDataObj.getStorageUrl()) + File.separator + map.get("fileName").toString());
                }
            }
            //先删除已存在的步骤
            handleDataMapper.deleteStepByHandleId(handleId);
            DataStepInfoParam[] operatorList = dataStepParam.getOperatorList();
            List<HandleStep> handleStepList = getHandleStepList(operatorList,handleId);
            handleData.setHandleStepList(handleStepList);

            Collections.sort(handleStepList, new Comparator<HandleStep>() {
                @Override
                public int compare(HandleStep o1, HandleStep o2) {
                    return o1.getStepIndex() - o2.getStepIndex();
                }
            });

            for(HandleStep hs : handleStepList) {
                hs.setSaveStatus(hs.getStepIndex().intValue() == handleStepList.size());//只保存最后一步结果数据
                handleStepMapper.save(hs);
            }

            HandleStep handleStep = handleStepList.get(0);
            handleStep.setPendingDataPathList(pendingDataPathList);

            batchSaveForHandleStepFile(handleStepList,pendingDataPathList,handleStep);

            handleData.setHandleStatus(ExecStatusEnum.TOBEEXECUTED.getValue());
            handleDataMapper.updateHandleStatus(handleData);

            return ResultMessage.success();
        }catch (Exception e){
            logger.error("HandleDataServiceImpl addHandleData method :" + e.getMessage());
            return ResultMessage.fail();
        }
    }

    @Override
    public ResultMessage updateStatus(HandleDataParam handleDataParam) {
        try{
            HandleData handleData = new HandleData();
            handleData.setHandleId(handleDataParam.getHandleId());
            handleData.setHandleStatus(handleDataParam.getHandleStatus());

            handleDataMapper.updateHandleStatus(handleData);
            handleStepMapper.updateHandleStepStatus(handleData.getHandleId(),handleData.getHandleStatus());

            return ResultMessage.success();
        }catch (Exception e){
            logger.error("HandleDataServiceImpl saveTask method :" + e.getMessage());
            return ResultMessage.fail();
        }
    }

    private List<HandleStep> getHandleStepList(DataStepInfoParam[] operatorList, Integer handleId){
        List<HandleStep> handleStepList = new ArrayList<HandleStep>();
        for(DataStepInfoParam dsip : operatorList){
            Integer handleMetaId = dsip.getHandleMetaId();
            Integer orderInTask = dsip.getOrderInTask();
            String handleParams = dsip.getHandleParams();

            Map<String, String> tmpMap = new HashMap<>();
            String[] handleParamArray = handleParams.split(",");
            for (String param : handleParamArray) {
                String[] paramArray = param.split(":");
                if (paramArray.length == 2) {
                    tmpMap.put(paramArray[0], paramArray[1]);
                }
            }
            JSONObject handleParamObj = JSONObject.fromObject(tmpMap);

            HandleStep handleStep = new HandleStep();
            handleStep.setHandleId(handleId);
            handleStep.setHandleMetaId(handleMetaId);
            HandleConfig handleConfig = (HandleConfig) handleConfigService.getById(handleMetaId).getData();
            handleStep.setHandleConfig(handleConfig);
            handleStep.setStepIndex(orderInTask);
            handleStep.setStepParam(handleParamObj.toString());
            handleStep.setHandleStatus(ExecStatusEnum.TOBEEXECUTED.getValue());
            handleStepList.add(handleStep);
        }

        return handleStepList;
    }

    /**
     * 得到任务关联的存储文件列表数据
     * @param pendingDataArray
     * @param handleData
     * @return
     * @version <1> 2018-03-22 cxj： Created.
     */
    private List<String> getPendingDataPathList(RelateTaskStorageParam[] pendingDataArray, HandleData handleData){
        List<Integer> storageIdList = new ArrayList<Integer>();

        for(RelateTaskStorageParam rtsp : pendingDataArray){
            storageIdList.add(rtsp.getStorageId());
        }

        List<String> pendingDataPathList = new ArrayList<>();
        List<DataStorage> dataStorageList = dataStorageService.findStorageByIdList(storageIdList);
        if(dataStorageList != null && !dataStorageList.isEmpty()) {
            for (DataStorage ds : dataStorageList) {
                pendingDataPathList.add(ds.getStorageUrl());
            }
            handleData.setDataStorageList(dataStorageList);
        }
        return pendingDataPathList;
    }


    /**
     * 构建数据处理任务名称
     * @param chinaName
     * @param storageType
     * @return
     * @version<1> 2018-05-10 lcw : 将获取数据处理任务名称的代码提取出来</1>
     */
    private String getTaskName(String chinaName, Integer storageType){
        StringBuffer buffer = new StringBuffer();
        buffer.append(chinaName);
        if(StorageTypeEnum.DATA_STORAGE_TYPE_CGSJ.getId().intValue() == storageType){
            buffer.append("_" + StorageTypeEnum.DATA_STORAGE_TYPE_CGSJ.getMsg() + "处理任务");
        }else{
            buffer.append("_数据处理任务");
        }

        buffer.append((DateUtil.dateToString(new Date(),"yyyyMMddHHmmss")));

        return buffer.toString();
    }

    /**
     * 创建数据生产任务
     * @param handleData
     * @return
     * @version <1> 2019-02-14 zhangshen： Created.
     */
    @Override
    public ResultMessage createHandleTask(HandleData handleData) {
        try{
            handleData.setCreateTime(LocalDateTime.now());
            handleData.setHandleName(getTaskName(handleData.getChinaName(), handleData.getStorageType()));
            handleDataMapper.save(handleData);

            String storageUrl = CephUtils.makerHandleDirectory(handleData.getHandleId());
            handleData.setStorageUrl(storageUrl);
            handleDataMapper.update(handleData);

            return ResultMessage.success();
        }catch (Exception e){
            logger.error("创建数据处理任务失败:" + e.getMessage());
            return ResultMessage.fail("创建数据处理任务失败");
        }
    }

    /**
     * 数据集入库
     * @param handleData
     * @return
     * @version <1> 2019-02-18 zhangshen： Created.
     */
    @Override
    public ResultMessage findTaskByHandleId(HandleData handleData) {
        try{
            Map<String,Object> resultMap = new HashMap<String,Object>();

            HandleData taskObj = handleDataMapper.getById(handleData.getHandleId());
            resultMap.put("taskObj",taskObj);//任务详情

            String filePath = null;
            List<HandleStep> handleStepList = handleStepMapper.findListByHandleId(handleData.getHandleId());
            for (HandleStep handleStep : handleStepList) {
                if (handleStepList.size() == handleStep.getStepIndex()) {
                    List<HandleStepFile> handleStepFileList = handleStepFileMapper.findHandleStepFileByHandleDetailId(handleStep.getHandleDetailId());
                    if (handleStepFileList.size()>0) {
                        HandleStepFile handleStepFile = handleStepFileList.get(0);
                        //filePath = handleStepFile.getFilePath().replace("\\", "/").replace("/", File.separator);
                        filePath = CephUtils.changePathSlash(handleStepFile.getFilePath());
                        if (filePath.startsWith(File.separator)) {
                            filePath = filePath.substring(1,filePath.lastIndexOf(File.separator));
                        }else{
                            filePath = filePath.substring(0,filePath.lastIndexOf(File.separator));
                        }
                    }
                }
            }
            resultMap.put("filePath",filePath);

            return ResultMessage.success(resultMap);

        }catch (Exception e){
            logger.error("HandleDataServiceImpl findTaskByHandleId method :" + e.getMessage());
            return ResultMessage.fail();
        }
    }

    @Override
    public ResultMessage deleteHandle(Integer handleId) {
        try{
            HandleData handleData = new HandleData();
            handleData.setHandleId(handleId);
            handleData.setDataStatus(DataStatusEnum.Invalid.getCode());
            handleData.setDelFlag(DelStatusEnum.Delete.getCode());
            handleDataMapper.update(handleData);
            return ResultMessage.success();
        }catch (Exception e){
            logger.error("HandleDataServiceImpl deleteHandle method :" + e.getMessage());
            return ResultMessage.fail();
        }
    }

    /**
     * 更新任务数据执行顺序
     * @param dataIdList
     * @return
     * @version <1> 2019-10-08 cxw： Created.
     */
    @Override
    public ResultMessage updateHandleDataIndex(List<HandleRelateDataParam> dataIdList){
        try{
            if(dataIdList.size()>0)
            {
                int num = handleDataMapper.updateHandleDataIndex(dataIdList);
                if(num>0){
                    return ResultMessage.success();
                }
                else{
                    return ResultMessage.fail("更新任务数据执行顺序失败");
                }
            }
            else{
                return ResultMessage.fail("参数异常");
            }

        }catch (Exception e){
            logger.error("HandleDataServiceImpl updateHandleDataIndex method :" + e.getMessage());
            return ResultMessage.fail();
        }

    }
}