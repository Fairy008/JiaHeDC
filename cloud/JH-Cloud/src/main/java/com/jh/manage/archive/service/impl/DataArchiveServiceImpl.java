package com.jh.manage.archive.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.logs.entity.Logs;
import com.jh.logs.service.ILogsService;
import com.jh.manage.alarm.Enum.AlarmBusinessTypeEnum;
import com.jh.manage.alarm.Enum.DataAlamStatusEnum;
import com.jh.manage.alarm.entity.DataAlarm;
import com.jh.manage.alarm.service.IDataAlarmService;
import com.jh.manage.archive.Enum.DataArchiveEnum;
import com.jh.manage.archive.entity.DataArchive;
import com.jh.manage.archive.entity.DataArchiveDetail;
import com.jh.manage.archive.mapping.IDataArchiveDetailMapper;
import com.jh.manage.archive.mapping.IDataArchiveMapper;
import com.jh.manage.archive.model.ArchiveParam;
import com.jh.manage.archive.service.IDataArchiveService;
import com.jh.manage.storage.entity.DataStorage;
import com.jh.manage.storage.mapping.IDataStorageMapper;
import com.jh.manage.storage.model.StorageParam;
import com.jh.util.DateUtil;
import com.jh.util.ceph.CephUtils;
import com.jh.util.ceph.LinkUtil;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * description:数据归档实现类
 *	1.查询数据归档信息列表
 *	2.创建数据归档任务
 *	3.执行数据归档任务
 *	4.归档明细查询
 * @version <1> 2018-01-29 lcw： Created.
 */
@Service
@Transactional
public class DataArchiveServiceImpl extends BaseServiceImpl<ArchiveParam, DataArchive, Integer> implements IDataArchiveService {

    @Autowired
    private IDataArchiveMapper dataArchiveMapper;

    @Autowired
    private IDataStorageMapper dataStorageMapper;

    @Autowired
    private IDataArchiveDetailMapper dataArchiveDetailMapper;

    @Autowired
    private ILogsService logsService;

    @Autowired
    private IDataAlarmService dataAlarmService;

    //创建LINK
    LinkUtil linkUtil = LinkUtil.getInstance();

    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    protected IBaseMapper<ArchiveParam, DataArchive, Integer> getDao() {
        return dataArchiveMapper;
    }

    @Override
    public PageInfo<DataArchive> findByPage(ArchiveParam archiveParam) {
        PageHelper.startPage(archiveParam.getPage(), archiveParam.getRows());
//        PageHelper.orderBy(archiveParam.getSidx() + " " + archiveParam.getSord());
        List<DataArchive> list=dataArchiveMapper.findAll(archiveParam);
        return new PageInfo<DataArchive>(list);

    }

    @Override
    public ResultMessage findById(Integer archiveId) {
        DataArchive dataArchive=dataArchiveMapper.findById(archiveId);
        dataArchive.setArchivePath(CephUtils.getShowPath(dataArchive.getArchivePath()));
        return ResultMessage.success(dataArchive);
    }

    @Override
    public ResultMessage saveArchive(ArchiveParam archiveParam) {
        StorageParam storageParam=new StorageParam();
        storageParam.setBeginTime(archiveParam.getBeginDate().toString());
        storageParam.setEndTime(archiveParam.getEndDate().toString());
        storageParam.setSatellite(archiveParam.getSatName());
        //获取时间段 和分类 查询  storage
        List<DataStorage> list  =dataStorageMapper.findByTime(storageParam);
        if(list.size()>0){//该时间段和该类型有相关数据
            LocalDate beginDate = LocalDate.parse(archiveParam.getBeginDate(),df);
            LocalDate endDate = LocalDate.parse(archiveParam.getEndDate(),df);
            DataArchive dataArchive=new DataArchive();
            dataArchive.setTotalNum(list.size());
            dataArchive.setBeginDate(beginDate);
            dataArchive.setEndDate(endDate);
//            dataArchive.setArchiveName(archiveParam.getSatName()+"归档任务("+beginDate+"至"+endDate+")");
            dataArchive.setArchiveName(archiveParam.getSatName()+ "归档任务_" + DateUtil.dateToString(new java.util.Date(),"yyyyMMddHHmmss"));
            dataArchive.setSatId(archiveParam.getSatId());
            dataArchive.setCreator(archiveParam.getCreator());
            dataArchive.setCreatorName(archiveParam.getCreatorName());
            dataArchive.setRemark(archiveParam.getRemark());
            dataArchiveMapper.saveArchive(dataArchive);
            Integer archiveId=dataArchive.getArchiveId();//获取数据归档主键id
            //创建数据归档link存储路径  用数据归档的主键id和创建人的名称（手机号）
            String rootPath = CephUtils.makeArchiveDirectory(archiveId, archiveParam.getPhone());
            dataArchive.setArchivePath(rootPath);
            //dataArchive.setArchiveId(archiveId);
            //更新archive表中的 路径信息
            dataArchiveMapper.updateByPrimaryKeySelective(dataArchive);
            List<DataArchiveDetail> listArchive =new ArrayList<>();
            for(int i=0;i<list.size();i++){
                DataArchiveDetail archiveDetail=new DataArchiveDetail();
                archiveDetail.setArchiveId(archiveId);
                archiveDetail.setDataName(list.get(i).getFileName());
                archiveDetail.setDataSize(list.get(i).getDataSize());
                archiveDetail.setStorageId(list.get(i).getStorageId());
                listArchive.add(archiveDetail);
                if(i%1000==0){
                    dataArchiveDetailMapper.saveDetails(listArchive);
                    listArchive.clear();//满一千条批量插入  并清空list
                }
            }
            if(list.size()%1000!=0){//有余数或者不满一千条时插入信息
                dataArchiveDetailMapper.saveDetails(listArchive);
            }
        }else {//该时间段和该类型没有相关数据
            return ResultMessage.fail();
        }
        return ResultMessage.success();
    }

    /**
     *
     * 1.更新数据归档任务实体信息
     * 2.移除明细表中的原信息
     * 3.增加新的数据归档任务明细
     * 4.删除原来的连接 并增加新的连接
     *
     * @param  archiveParam:
     * @return ResultMessage :
     * @version <1> 2018-03-21 wl:Created.
     */
    @Override
    public ResultMessage updateArchive(ArchiveParam archiveParam) {

        //删除data_archive_detail表相关信息
        dataArchiveDetailMapper.deleteDetail(archiveParam.getArchiveId());
        //新增detail
        StorageParam storageParam=new StorageParam();
        storageParam.setBeginTime(archiveParam.getBeginDate().toString());
        storageParam.setEndTime(archiveParam.getEndDate().toString());
        storageParam.setSatellite(archiveParam.getSatName());
        //获取时间段 和分类 查询  storage
        List<DataStorage> list  =dataStorageMapper.findByTime(storageParam);
        if(list.size()>0) {
            List<DataArchiveDetail> listArchive =new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                DataArchiveDetail archiveDetail = new DataArchiveDetail();
                archiveDetail.setArchiveId(archiveParam.getArchiveId());
                archiveDetail.setDataName(list.get(i).getFileName());
                archiveDetail.setDataSize(list.get(i).getDataSize());
                archiveDetail.setStorageId(list.get(i).getStorageId());
                listArchive.add(archiveDetail);
                if(i%1000==0){
                    dataArchiveDetailMapper.saveDetails(listArchive);
                    listArchive.clear();//满一千条批量插入  并清空list
                }
                //dataArchiveDetailMapper.save(archiveDetail);
            }
            if(list.size()%1000!=0){//有余数或者不满一千条时插入信息
                dataArchiveDetailMapper.saveDetails(listArchive);
            }
            DataArchive dataArchive=new DataArchive();
            dataArchive.setArchiveId(archiveParam.getArchiveId());
            //更新data_archive
            LocalDate beginDate = LocalDate.parse(archiveParam.getBeginDate(),df);
            LocalDate endDate = LocalDate.parse(archiveParam.getEndDate(),df);
            dataArchive.setTotalNum(list.size());
            dataArchive.setBeginDate(beginDate);
            dataArchive.setEndDate(endDate);
            dataArchive.setModifier(archiveParam.getModifier());
            dataArchive.setModifierName(archiveParam.getModifierName());
            dataArchive.setRemark(archiveParam.getRemark());
            dataArchive.setSatId(archiveParam.getSatId());
            dataArchiveMapper.updateByPrimaryKeySelective(dataArchive);
        }else {//该时间段和该类型没有相关数据
            return ResultMessage.fail();
        }
        return ResultMessage.success();
    }

    /**
     * 创建数据归档任务,
     * 1.检索数据存储文件夹，
     * 2.创建数据归档批处理命令
     * 3.保存数据归档明细
     *
     * @param  dataArchiveObj:
     * @return ResultMessage :
     * @version <1> 2018-01-29 Hayden:Created.
     */
    @Override
    public ResultMessage saveDataArchiveTask(DataArchive dataArchiveObj) {
        return null;
    }

    /**
     * 执行数据归档任务 （执行归档过程中不可修改归档任务信息）
     *	1.指定归档路径
     *	2.调用批处理命令执行数据归档
     *	3.若归档路径中存在文件，则直接进行覆盖（防止数据丢失）
     *	4.归档完成后，更新归档任务状态，更新归档明细
     * @param  dataArchiveId:
     * @return ResultMessage :
     * @version <1> 2018-01-29 Hayden:Created.
     */
    @Override
    public ResultMessage startDataArchiveTask(Integer dataArchiveId) {
        return null;
    }

    /**
     * 归档明细查询
     * @param  dataArchiveId:
     * @return ResultMessage :
     * @version <1> 2018-01-29 Hayden:Created.
     */
    @Override
    public ResultMessage findDataArchiveDetailById(Integer dataArchiveId) {
        DataArchive dataArchive=dataArchiveMapper.findById(dataArchiveId);
        return ResultMessage.success(dataArchive);
    }

    @Override
    public ResultMessage findDetail(Integer archiveId) {
        //查询 data_archive_detail 表中的文件明细
        List<DataArchiveDetail> list=dataArchiveDetailMapper.selectByArchiveId(archiveId);
        return ResultMessage.success(list);
    }

    @Override
    public ResultMessage exec(Integer archiveId) {
        ResultMessage result = ResultMessage.fail();
        //根据archiveId查询数据归档信息
        DataArchive dataArchive=dataArchiveMapper.findById(archiveId);
        //数据归档开始时间
        dataArchive.setStartTime(LocalDateTime.now());
        //获取数据归档link存储路径
        String rootPath =dataArchive.getArchivePath();
        //根据archiveId 查询 detail表中的所有  storageId
        List<DataArchiveDetail> list=dataArchiveDetailMapper.selectByArchiveId(archiveId);
        List<Integer> storageIdList =new ArrayList<Integer>();
        for(int i=0;i<list.size();i++){
            storageIdList.add(list.get(i).getStorageId());
        }

        if(storageIdList != null && storageIdList.size() > 0){

            //根据data_archive_detail 表查询相关联的 data_storage 表数据信息
            List<DataStorage> dataStorageList = dataStorageMapper.findStorageByIdList(storageIdList);
            List<String> listLinks = new ArrayList<>();

            if(dataStorageList.size()>0){
                for(int i=0;i<dataStorageList.size();i++){
                    //link文件存储的相对路径
                    String dataPath = CephUtils.getOrderLink(rootPath,dataStorageList.get(i).getFileName());
                    String reworkFilePath=dataStorageList.get(i).getStorageUrl();
                    //创建可在linux系统的可执行link命令
                    String link= LinkUtil.makeLink(reworkFilePath, dataPath);
                    listLinks.add(link);
                }
                boolean bool = linkUtil.exec(listLinks); //执行link命令

                if(bool){
                    //数据归档结束时间
//                dataArchive.setEndTime(LocalDateTime.now());
                    dataArchive.setArchiveStatus(DataArchiveEnum.DATA_ARCHIVE_SUCCESS.getValue());
                    //数据归档成功 更新archive表中的 路径信息
                    result = ResultMessage.success();  //归档成功
                    result.setMsg("归档任务执行成功，归档路径："+rootPath);
                    dataArchive.setReason("");
                }else{
                    dataArchive.setReason("执行归档失败");
                    result.setMsg("执行归档失败");
                    //数据归档失败 更新archive表中状态信息
                    dataArchive.setArchiveStatus(DataArchiveEnum.DATA_ARCHIVE_FAIL.getValue());
                }
            }else {
                dataArchive.setReason("没有符合条件的数据");
                result.setMsg("没有符合条件的数据");
                //数据归档失败 更新archive表中状态信息
                dataArchive.setArchiveStatus(DataArchiveEnum.DATA_ARCHIVE_FAIL.getValue());
            }
        }else{
            dataArchive.setReason("没有符合条件的数据");
            result.setMsg("没有符合条件的数据");
            //数据归档失败 更新archive表中状态信息
            dataArchive.setArchiveStatus(DataArchiveEnum.DATA_ARCHIVE_FAIL.getValue());
        }
        //数据归档结束时间
        dataArchive.setEndTime(LocalDateTime.now());
        //数据归档结束 更新archive表中的 路径信息
        dataArchiveMapper.updateByPrimaryKeySelective(dataArchive);
        insertLog(dataArchive ); //日志消息
        insertAlarm(dataArchive,result); //告警消息
        return result;
    }

    @Override
    public ResultMessage queryArchiveSateNum(ArchiveParam archiveParam) {
        //判断timeSlot是否为空
        if(archiveParam.getTimeSlot()!=null && archiveParam.getTimeSlot()!=""){
            LocalDate localDate=LocalDate.now();
            switch (archiveParam.getTimeSlot()){
                case "day":
                    archiveParam.setBeginDate(localDate.toString());
                    break;
                case "threeDay":
                    archiveParam.setBeginDate(localDate.plusDays(-2).toString());
                    break;
                case "week"://例如今天周五  查询上周五到今天的数据
                    archiveParam.setBeginDate(localDate.plusWeeks(-1).toString());
                    break;
                case "month"://5月31日  查询的是4月30日到  5月31日的数据
                    archiveParam.setBeginDate(localDate.plusMonths(-1).toString());
                    break;
            }
            archiveParam.setEndDate(localDate.toString());
        }
        return ResultMessage.success(dataArchiveMapper.queryArchiveSateNum(archiveParam));
    }




    /**
     * 日志消息
     * @param dataArchive
     * @version<1> 2018-04-12 lcw : Created.
     */
    private void insertLog(DataArchive dataArchive){
        Logs archiveLog = new Logs();
        archiveLog.setOperator(dataArchive.getCreator());
        archiveLog.setOperatorName(dataArchive.getCreatorName());

        if(dataArchive.getArchiveStatus().intValue() == DataArchiveEnum.DATA_ARCHIVE_FAIL.getValue()){
            archiveLog.setOpContent(dataArchive.getArchiveName() + DataArchiveEnum.DATA_ARCHIVE_FAIL.getMsg());
        }else if(dataArchive.getArchiveStatus().intValue() == DataArchiveEnum.DATA_ARCHIVE_SUCCESS.getValue()){
            archiveLog.setOpContent(dataArchive.getArchiveName() + DataArchiveEnum.DATA_ARCHIVE_SUCCESS.getMsg());
        }

        logsService.addLog(archiveLog);
    }


    /**
     * 告警消息
     * @param dataArchive
     */
    private void insertAlarm(DataArchive dataArchive, ResultMessage result){
        DataAlarm dataAlarm = new DataAlarm();//告警信息
        dataAlarm.setCreator(dataArchive.getCreator());
        dataAlarm.setCreatorName(dataArchive.getCreatorName());
        dataAlarm.setBusinessId(dataArchive.getArchiveId());
        dataAlarm.setBusinessName(dataArchive.getArchiveName());
        dataAlarm.setBusinessType(AlarmBusinessTypeEnum.BUSINESS_TYPE_ARCHIVE.getId());//归档告警
        dataAlarm.setReason(result.getMsg());
        if(dataArchive.getArchiveStatus().intValue() == DataArchiveEnum.DATA_ARCHIVE_FAIL.getValue()){
            dataAlarm.setBusStatus(DataAlamStatusEnum.DATA_ALAM_FAIL.getValue());
        }else if(dataArchive.getArchiveStatus().intValue() == DataArchiveEnum.DATA_ARCHIVE_SUCCESS.getValue()){
            dataAlarm.setBusStatus(DataAlamStatusEnum.DATA_ALAM_SUCCESS.getValue());
        }

        dataAlarmService.saveAlarm(dataAlarm);

    }

    @Override
    public ResultMessage queryArchiveSateSum(ArchiveParam archiveParam) {
        //统计归档成功数据
        List<Map<String, Object>> totalArchive = dataArchiveMapper.queryTotalArchiveEcharts(archiveParam);
        //统计卫星数据
        StorageParam storageParam = new StorageParam();
        List<Map<String, Object>> totalSatellite = dataStorageMapper.queryTotalSatelliteEcharts(storageParam);
        Map map=new HashMap();
        map.put("totalArchive",totalArchive);//归档成功数据
        map.put("totalSatellite",totalSatellite);//卫星数据
        return ResultMessage.success(map);
    }


    /**
     * 卫星数据及归档统计table
     * @param archiveParam
     * @return ResultMessage
     * @version <1> 2018-06-26 zhangshen: Created.
     */
    @Override
    public ResultMessage queryArchiveSateSumTable(ArchiveParam archiveParam) {
        //统计卫星数据
        StorageParam storageParam = new StorageParam();
        List<Map<String, Object>> totalSatellite = dataStorageMapper.queryTotalSatelliteEcharts(storageParam);
        //统计归档成功数据
        List<Map<String, Object>> totalArchive = dataArchiveMapper.queryTotalArchiveEcharts(archiveParam);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map1 : totalSatellite) {
            for (Map<String, Object> map2 : totalArchive) {
                if(map1.get("id").equals(map2.get("id")) && map1.get("name").equals(map2.get("name"))){
                    Map<String, Object> map3 = new HashMap<String, Object>();
                    map3.put("id", map1.get("id"));
                    map3.put("name", map1.get("name"));
                    map3.put("valueSatellite", map1.get("value"));
                    map3.put("valueArchive", map2.get("value"));
                    list.add(map3);
                }
            }
        }

        Map map=new HashMap();
        map.put("list",list);//归档成功数据 和 卫星数据
        return ResultMessage.success(map);
    }
}
