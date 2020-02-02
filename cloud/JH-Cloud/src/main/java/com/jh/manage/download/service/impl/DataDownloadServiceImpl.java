package com.jh.manage.download.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.logs.entity.Logs;
import com.jh.logs.service.ILogsService;
import com.jh.manage.alarm.Enum.AlarmBusinessTypeEnum;
import com.jh.manage.alarm.entity.DataAlarm;
import com.jh.manage.alarm.service.IDataAlarmService;
import com.jh.manage.download.Enum.DataAcquisitionEnum;
import com.jh.manage.download.Enum.DownloadStatusEnum;
import com.jh.manage.download.entity.DataDownload;
import com.jh.manage.download.entity.DataDownloadDetail;
import com.jh.manage.download.mapping.IDataDownloadMapper;
import com.jh.manage.download.model.DownloadParam;
import com.jh.manage.download.service.IDataDownloadDetailService;
import com.jh.manage.download.service.IDataDownloadService;
import com.jh.manage.loader.service.IDataLoaderService;
import com.jh.util.DateUtil;
import com.jh.util.cache.IdTransformUtils;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description:数据下载实现类
 *
 * @version <1> 2018-01-29 lcw： Created.
 */
@Service
@Transactional
public class DataDownloadServiceImpl extends BaseServiceImpl<DownloadParam, DataDownload, Integer> implements IDataDownloadService {

    @Autowired
    private IDataDownloadMapper dataDownloadMapper;
    @Autowired
    private IDataAlarmService dataAlarmService;
    @Autowired
    private IDataDownloadDetailService dataDownloadDetailService;
    @Autowired
    private IDataLoaderService dataLoaderService;
    @Autowired
    private ILogsService logsService;


    @Override
    protected IBaseMapper<DownloadParam, DataDownload, Integer> getDao() {
        return dataDownloadMapper;
    }

    @Override
    public PageInfo<DownloadParam> findTaskPage(DownloadParam downloadParam) {
        PageHelper.startPage(downloadParam.getPage(),downloadParam.getRows());
//        PageHelper.orderBy(downloadParam.getSidx() + " " + downloadParam.getSord());
        List<DownloadParam> list = dataDownloadMapper.queryByPage(downloadParam);
        IdTransformUtils.idTransForList(list);
        PageInfo<DownloadParam> pageInfo = new PageInfo<DownloadParam>(list);
        return pageInfo;
    }

    @Override
    public ResultMessage saveDownload(DownloadParam downloadParam) {
        //校验下载必填参数不能为空
        if (downloadParam == null || downloadParam.getStartDate() == null ||
                downloadParam.getEndDate() == null || downloadParam.getDownloadMetaId() == null){
            return ResultMessage.fail(DataAcquisitionEnum.PARAM_EMPTY.getValue(), DataAcquisitionEnum.PARAM_EMPTY.getMesasge());
        }

        DataDownload dataDownload = new DataDownload();
        dataDownload.setRegionId(downloadParam.getRegionId());
        dataDownload.setStartDate(downloadParam.getStartDate());
        dataDownload.setEndDate(downloadParam.getEndDate());
        //不需要的参数
   /*     dataDownload.setDownloadMetaId(downloadParam.getDownloadMetaId());
        dataDownload.setDownloadMetaValue(downloadParam.getDownloadMetaValue());*/
        dataDownload.setCreator(downloadParam.getCreator());
        dataDownload.setCreatorName(downloadParam.getCreatorName());
        dataDownload.setCreateTime(LocalDateTime.now());
        dataDownload.setDataType(downloadParam.getDataType());

        dataDownload.setDownloadStatus(DownloadStatusEnum.WAIT.getValue());//待下载
        dataDownload.setRemark(downloadParam.getRemark());

        //设置下载任务名
        dataDownload.setDownloadName(createTaskName(downloadParam));
        try {
            //1、保存下载任务记录
            dataDownloadMapper.save(dataDownload);
            //添加下载日志
//            logsService.addDownloadLog(null,dataDownload,DownloadStatusEnum.WAIT);
            insertLog(dataDownload,DownloadStatusEnum.WAIT);
            //2、开始下载
//            startDownLoad(dataDownload);
            return ResultMessage.success(DataAcquisitionEnum.SAVE_DOWNLOAD_SUCCESS.getValue(), DataAcquisitionEnum.SAVE_DOWNLOAD_SUCCESS.getMesasge());
        } catch (Exception e){
            e.printStackTrace();
            return ResultMessage.fail(DataAcquisitionEnum.SAVE_DOWNLOAD_FAIL.getValue(), DataAcquisitionEnum.SAVE_DOWNLOAD_FAIL.getMesasge());
        }
    }

    @Override
    public ResultMessage tryAgainTask(DownloadParam downloadParam) {
        if (downloadParam == null  || downloadParam.getDownloadId() == null || downloadParam.getRegionId() == null || downloadParam.getStartDate() == null ||
                downloadParam.getEndDate() == null || downloadParam.getDownloadMetaId() == null){
            return ResultMessage.fail(DataAcquisitionEnum.PARAM_EMPTY.getValue(), DataAcquisitionEnum.PARAM_EMPTY.getMesasge());
        }

        DataDownload dataDownload = dataDownloadMapper.getById(downloadParam.getDownloadId());
        if (dataDownload == null){
            return ResultMessage.fail(DataAcquisitionEnum.DOWNLOAD_NOT_EXISTS.getValue(), DataAcquisitionEnum.DOWNLOAD_NOT_EXISTS.getMesasge());
        }

        //只有下载任务失败时，才可以修改下载任务信息
        if (dataDownload.getDownloadStatus().intValue() != DownloadStatusEnum.DOWN_FAIL.getValue().intValue()){
            return ResultMessage.fail(DataAcquisitionEnum.NOT_EDIT_DOWNLOAD.getValue(), DataAcquisitionEnum.NOT_EDIT_DOWNLOAD.getMesasge());
        }

        try {
            //1、更新任务信息，状态为（待下载）
            DataDownload updateDown = new DataDownload();
            updateDown.setDownloadId(dataDownload.getDownloadId());
            updateDown.setDownloadStatus(DownloadStatusEnum.WAIT.getValue());
            updateDown.setRegionId(downloadParam.getRegionId());
            updateDown.setStartDate(downloadParam.getStartDate());
            updateDown.setEndDate(downloadParam.getEndDate());
            updateDown.setDownloadMetaId(downloadParam.getDownloadMetaId());
            updateDown.setRemark(downloadParam.getRemark());
            updateDown.setModifier(downloadParam.getModifier());
            updateDown.setModifierName(downloadParam.getModifierName());
            updateDown.setCreator(dataDownload.getCreator());
            updateDown.setCreatorName(dataDownload.getCreatorName());
//            updateDown.setDownloadName(createTaskName(downloadParam));
            updateDown.setReason("");//清空下载任务的错误信息
            dataDownloadMapper.update(updateDown);
            //2、开始下载
            DownloadParam download = dataDownloadMapper.findDownloadById(dataDownload.getDownloadId());
            updateDown.setDownloadName(download.getDownloadName());
            updateDown.setCreateTime(download.getCreateTime());
            //添加下载日志
            insertLog(dataDownload,DownloadStatusEnum.WAIT);
//            logsService.addDownloadLog(null,updateDown,DownloadStatusEnum.WAIT);
            return ResultMessage.success(DataAcquisitionEnum.TRYAGAIN_SUCCESS.getValue(), DataAcquisitionEnum.TRYAGAIN_SUCCESS.getMesasge());
        }catch (Exception e){
            e.printStackTrace();
            return ResultMessage.fail(DataAcquisitionEnum.TRYAGAIN_FAIL.getValue(), DataAcquisitionEnum.TRYAGAIN_FAIL.getMesasge());
        }
    }


    public ResultMessage findById(Integer downloadId){
        if (downloadId == null){
            return ResultMessage.fail(DataAcquisitionEnum.PARAM_EMPTY.getValue(), DataAcquisitionEnum.PARAM_EMPTY.getMesasge());
        }
        DownloadParam downloadParam = dataDownloadMapper.findDownloadById(downloadId);
        IdTransformUtils.idTransForObj(downloadParam);
        return ResultMessage.success(downloadParam);
    }

    public DownloadParam findDownloadById(Integer downloadId){
        DownloadParam downloadParam = null;
        if (downloadId != null){
            downloadParam = dataDownloadMapper.findDownloadById(downloadId);
        }
        return downloadParam;
    }

    public void saveDetail(JSONObject jsonObject, DataDownload dataDownload){
        DataDownload updateDownload = new DataDownload();
        updateDownload.setDownloadId(dataDownload.getDownloadId());
        updateDownload.setCreator(dataDownload.getCreator());
        updateDownload.setCreatorName(dataDownload.getCreatorName());
        updateDownload.setDownloadName(dataDownload.getDownloadName());
        updateDownload.setDownloadName(dataDownload.getDownloadName());
        DataAlarm dataAlarm = new DataAlarm();//告警信息
        dataAlarm.setCreator(dataDownload.getCreator());
        dataAlarm.setCreatorName(dataDownload.getCreatorName());
        dataAlarm.setBusinessId(dataDownload.getDownloadId());
        dataAlarm.setBusinessName(dataDownload.getDownloadName());
        dataAlarm.setBusinessType(AlarmBusinessTypeEnum.BUSINESS_TYPE_DOWNLOAD.getId());//下载告警
        try {
            boolean flag = jsonObject.getBoolean("flag");
            String msg = jsonObject.getString("msg");



            if (flag){
                JSONObject resultData = jsonObject.getJSONObject("result");
                Integer totalNum = resultData.getInt("totalNum");
                String storageDir = resultData.getString("storageDir");
                JSONArray successFile = resultData.getJSONArray("successFile");
                JSONArray failFile = resultData.getJSONArray("failFile");

                //更新 文件总数 、完成量
                updateDownload.setTotalNum(totalNum);
                //保存数据存储位置
                updateDownload.setStoragePath(CephUtils.getCephDownloadPath() + File.separator +  storageDir);
                updateDownload.setOkNum(totalNum.intValue() - failFile.size());

                //修改当前现在任务状态为（成功）
                updateDownload.setDownloadStatus(DownloadStatusEnum.DOWN_SUCCESS.getValue());

                //保存下载文件明细
                List<DataDownloadDetail> detailList = new ArrayList<DataDownloadDetail>();
                JSONObject fileObj = null;
                for (int i =0 ,j = successFile.size(); i < j;i++){
                    fileObj = (JSONObject)(successFile.get(i));
                    DataDownloadDetail detail  = new DataDownloadDetail();
                    detail.setDownloadId(dataDownload.getDownloadId());
                    String fileName = fileObj.getString("localFilePath");
                    detail.setProductName(fileName.substring(fileName.indexOf(storageDir) + storageDir.length()).replace(".tmp",""));
                    detail.setFileSize(fileObj.getString("fileSize"));
                    String[] fileDate = fileObj.getString("fileDate").split("-");
                    detail.setProductTime(LocalDate.of(Integer.valueOf(fileDate[0]),Integer.valueOf(fileDate[1]),Integer.valueOf(fileDate[2])));
                    detailList.add(detail);
                }
                //批量添加下载明细
                dataDownloadDetailService.saveBatch(detailList);

                //转换入库任务
                dataLoaderService.convertToDataLoadTask(updateDownload);

                //添加下载日志
                insertLog(dataDownload,DownloadStatusEnum.DOWN_SUCCESS);
//                logsService.addDownloadLog(null,dataDownload,DownloadStatusEnum.DOWN_SUCCESS);

                dataAlarm.setReason("下载任务执行成功");
            } else {
                //告警信息
                dataAlarm.setReason(msg);
                //修改当前现在任务状态为（失败）
                updateDownload.setDownloadStatus(DownloadStatusEnum.DOWN_FAIL.getValue());
            }
        }catch (Exception e){
            //告警信息
            dataAlarm.setReason("下载完成，保存记录失败，请联系技术人员");
            //修改当前现在任务状态为（失败）
            updateDownload.setDownloadStatus(DownloadStatusEnum.DOWN_FAIL.getValue());
        } finally {
            //更新下载任务
            dataDownloadMapper.update(updateDownload);
            //保存告警信息
            dataAlarmService.saveAlarm(dataAlarm);
            //添加下载日志
//            logsService.addDownloadLog(null,updateDownload,DownloadStatusEnum.DOWN_FAIL);
            insertLog(dataDownload,DownloadStatusEnum.DOWN_FAIL);
        }
    }

    //下载失败 修改任务状态、添加告警信息
    public void downloadFail(DataDownload dataDownload, DataAlarm dataAlarm){
        dataAlarm.setBusinessType(AlarmBusinessTypeEnum.BUSINESS_TYPE_DOWNLOAD.getId());//下载告警
        dataAlarm.setBusinessName(dataDownload.getDownloadName());
        //修改当前现在任务状态为（失败）
        dataDownload.setDownloadStatus(DownloadStatusEnum.DOWN_FAIL.getValue());
        dataDownload.setReason(dataAlarm.getReason());//下载的错误信息
        dataAlarm.setCreator(dataDownload.getCreator());
        dataAlarm.setCreatorName(dataDownload.getCreatorName());
        try {
            //更新下载任务
            dataDownloadMapper.update(dataDownload);
            //保存告警信息
            dataAlarmService.saveAlarm(dataAlarm);
            //添加下载日志
            insertLog(dataDownload,DownloadStatusEnum.DOWN_FAIL);
//            logsService.addDownloadLog(null,dataDownload,DownloadStatusEnum.DOWN_FAIL);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<DataDownload> findByParam(DownloadParam downloadParam){
        return dataDownloadMapper.findByParam(downloadParam);
    }

    @Override
    public ResultMessage updateDownload(DownloadParam downloadParam) {
         dataDownloadMapper.updateDownload(downloadParam);
        return ResultMessage.success();
    }

    /**
     * 创建下载任务名
     * @param downloadParam
     * @return
     * @version <I> 2018-03-16 XZG: Created
     */
    private String createTaskName(DownloadParam downloadParam){
        StringBuilder taskName = new StringBuilder();
        taskName.append(downloadParam.getRegionChinaName());
        if(StringUtils.isNotBlank(downloadParam.getDownloadMetaValue())){
            taskName.append(downloadParam.getSatName() +"["+ downloadParam.getDownloadMetaValue() +"]" + "_");
        }else{
            taskName.append(downloadParam.getSatName()  + "_");
        }
        taskName.append(DateUtil.dateToString(new java.util.Date(),"yyyyMMdd"));
        return taskName.toString();
    }



    /**
     * 插入日志
     * @param dataDownload
     * @param downloadStatusEnum
     * @version<1> 2018-04-12 lcw : Created.</1>
     */
    private void insertLog(DataDownload dataDownload, DownloadStatusEnum downloadStatusEnum){
        Logs downloadLog = new Logs();
        downloadLog.setOperator(dataDownload.getCreator());
        downloadLog.setOperatorName(dataDownload.getCreatorName());
        downloadLog.setOpContent(dataDownload.getDownloadName() + downloadStatusEnum.getStatusName());
        logsService.addLog(downloadLog);
    }

    /**
     * Description: 获取大屏显示所需的数据(入库和下载数据)
     * @param
     * @return
     * @version <1> 2018/8/16 20:57 zhangshen: Created.
     */
    @Override
    public List<Map<String, Object>> getEchartsDownloadData(String beginTime, String endTime) {
        return dataDownloadMapper.getEchartsDownloadData(beginTime, endTime);
    }

    /**
     * Description: 获取下载历史总数量
     * @param
     * @return
     * @version <1> 2018/8/16 20:41 zhangshen: Created.
     */
    @Override
    public Integer queryDownloadEchartsTotal() {
        return dataDownloadMapper.queryDownloadEchartsTotal();
    }
}
