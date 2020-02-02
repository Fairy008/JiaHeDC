package com.jh.manage.storage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.manage.archive.mapping.IDataArchiveMapper;
import com.jh.manage.order.mapping.IDataOrderMapper;
import com.jh.manage.order.model.OrderParam;
import com.jh.manage.storage.Enum.StorageTypeEnum;
import com.jh.manage.storage.entity.DataStorage;
import com.jh.manage.storage.mapping.IDataStorageMapper;
import com.jh.manage.storage.model.StorageParam;
import com.jh.manage.storage.service.IDataStorageService;
import com.jh.util.DateUtil;
import com.jh.vo.ResultMessage;
import io.swagger.models.auth.In;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.transform.Result;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Administrator on 2018/2/27.
 */
@Service
@Transactional
public class DataStorageServiceImpl implements IDataStorageService {

    @Autowired
    private IDataStorageMapper storageMapper;

    @Autowired
    private IDataOrderMapper dataOrderMapper;

    @Autowired
    private IDataArchiveMapper dataArchiveMapper;


    /**
     * 元数据综合查询：根据查询条件检索元数据信息，通过行政区或经纬度的最大矩形区域与元数据进行交集覆盖，将交集范围内的数据检索出来
     *   1).针对行政区：先获取该行政区对应的geometry数据，然后与storage的bbox进行交集处理
     *   2).针对经纬度：前端传的参数格式：（long1 lat1, long2 lat1, long2 lat2, long1 lat2, long1 lat1）,后台先组装为polygon，
     *      然后将polygon字符串使用函数ST_PolygonFromText(#{bbox},4326)进行转换处理，z再与storage的bbox进行交集处理
     * @param storageParam
     *  查询条件包括：
     *   1.行政区的边界或经纬度
     *   2.卫星ID
     *   3.传感器（非必须）
     *   4.数据类型
     *   5.影像时间
     *   6.云盖范围（非必须）
     * @return
     * @version<1> 2018-02-28 lcw : Created.
     */
    @Override
    public ResultMessage findDatas(StorageParam storageParam) {

        ResultMessage result = StorageParam.checkStorageParam(storageParam);
        if(result.isFlag()){ //查询条件验证通过
            if(StringUtils.isNotBlank(storageParam.getBbox())){
                storageParam.setBbox("POLYGON((" + storageParam.getBbox() + "))");
            }
            storageParam.setStorageType(StorageTypeEnum.DATA_STORAGE_TYPE_YSSJ.getId());
            List<DataStorage> list  = storageMapper.findDatas(storageParam);
            if(list != null && list.size() > 0 ){
                for(DataStorage storage : list){
                    storage.setFileName(storage.getFileName());
                }
            }
            result = ResultMessage.success(list);
        }
        return result;
    }

    public List<DataStorage> findStorageByIdList(List<Integer> taskStorageIdList) {
        return storageMapper.findStorageByIdList(taskStorageIdList);
    }

    /**
     * 查询订单详情：根据订单主键查询订单关联文件详情
     * @param orderId 订单ID
     * @return
     * @version <1> 2018-03-15 cxw： Created.
     */
    @Override
    public ResultMessage findOrderDetailByOrderId(Integer orderId) {
        ResultMessage result = ResultMessage.success();
        if(orderId!=null){
            List<DataStorage> list = new ArrayList<DataStorage>();
            list = storageMapper.findOrderDetailByOrderId(orderId);
            if(list.size()==0){
                result=ResultMessage.fail();
                result.setMsg("无数据");
            }
            else{
                //拼接访问路径
                for(int i = 0;i<list.size();i++){
                    DataStorage ds = new DataStorage();
                    ds = list.get(i);
                   // String reworkFilePath= CephUtils.getAbsolutePath(ds.getStorageUrl()).replace("\\\\work","\\work");  //订单link可访问路径
                   // ds.setStorageUrl(reworkFilePath);
                /*   String reworkFilePath = PropertyUtil.getPropertiesForConfig("DOWNLOAD_URL") + File.separator + ds.getStorageUrl();
                   ds.setStorageUrl(reworkFilePath);*/
                   String url[] = ds.getStorageUrl().split("\\\\");
                    ds.setFileName(url[url.length-1]);
                }
                result.setData(list);
            }
        }
        else{
            result = ResultMessage.fail();
            result.setMsg("订单ID为空");
        }
        return result;
    }

    /**
     * 分页查询订单详情：根据订单主键查询订单关联文件详情
     * @param storageParam 元数据对象
     * @return PageInfo 分页对象
     * @version <1> 2018-03-015 cxw： Created.
     */
    @Override
    public PageInfo<DataStorage> findOrderFileByPage(StorageParam storageParam) {
        List<DataStorage> list = storageMapper.findOrderFileByPage(storageParam);
        return new PageInfo<DataStorage>(list);
    }

    @Override
    public List<DataStorage> findTaskRelateStorageByHandleId(Integer handleId) {
        return storageMapper.findTaskRelateStorageByHandleId(handleId);
    }

    @Override
    public List<HashMap<Object, Object>> findStepRelateStorageListByHandleId(Integer handleId) {
        return storageMapper.findStepRelateStorageListByHandleId(handleId);
    }

    /**
     * 订单分发时添加再加工文件信息
     * @param dataStorage
     * @return int
     * @version <1> 2018-04-16 cxw： Created.
     */
    @Override
    public int insertDataStorageForDistributeFile(DataStorage dataStorage) {
        return storageMapper.insertDataStorageForDistributeFile(dataStorage);
    }

    @Override
    public ResultMessage queryDataStorageSateNum(StorageParam storageParam) {
        //判断timeSlot是否为空
        if(StringUtils.isNotBlank(storageParam.getTimeSlot())){
            LocalDate localDate=LocalDate.now();
            switch (storageParam.getTimeSlot()){
                case "day":
                    storageParam.setBeginTime(localDate.toString());
                    storageParam.setBeginCreateTime(localDate.toString());
                    break;
                case "threeDay":
                    storageParam.setBeginTime(localDate.plusDays(-2).toString());
                    storageParam.setBeginCreateTime(localDate.plusDays(-2).toString());
                    break;
                case "week"://例如今天周五  查询上周五到今天的数据
                    storageParam.setBeginTime(localDate.plusWeeks(-1).toString());
                    storageParam.setBeginCreateTime(localDate.plusWeeks(-1).toString());
                    break;
                case "month"://5月31日  查询的是4月30日到  5月31日的数据
                    storageParam.setBeginTime(localDate.plusMonths(-1).toString());
                    storageParam.setBeginCreateTime(localDate.plusMonths(-1).toString());
                    break;
            }
            storageParam.setEndTime(localDate.toString());
            storageParam.setEndCreateTime(localDate.toString());
        }
        return ResultMessage.success(storageMapper.queryDataStorageSateNum(storageParam));
    }

    @Override
    public PageInfo<DataStorage> findByPage(StorageParam storageParam) {
        ResultMessage result = StorageParam.checkStorageParam(storageParam);
        if(StringUtils.isNotBlank(storageParam.getBbox())){
            storageParam.setBbox("POLYGON((" + storageParam.getBbox() + "))");
        }
        storageParam.setStorageType(StorageTypeEnum.DATA_STORAGE_TYPE_YSSJ.getId());
        PageHelper.startPage(storageParam.getPage(), storageParam.getRows());
        if (StringUtils.isNotBlank(storageParam.getSensor())) {
            storageParam.setSensorArr(storageParam.getSensor().split(","));
        }

        if(StringUtils.isNotBlank(storageParam.getSceneId())){
            storageParam.setSceneIdArr(storageParam.getSceneId().split(","));
        }

        if(StringUtils.isNotBlank(storageParam.getProductLevel())){
            storageParam.setProductLevelArr(storageParam.getProductLevel().split(","));
        }


        if(StringUtils.isNotBlank(storageParam.getSatellites())){
            storageParam.setSatelliteArr(storageParam.getSatellites().split(","));
        }


        List<DataStorage> list=  storageMapper.findDatas(storageParam);
        return new PageInfo<DataStorage>(list);
    }

    @Override
    public ResultMessage findStorageById(Integer storageId) {

        if(storageId == null){
            return ResultMessage.fail();
        }
        DataStorage storage = storageMapper.findStorageById(storageId);
        return ResultMessage.success(storage);
    }

    /**
     * Description: 根据ids查询首页数据,将id查询成对象
     * @param ids
     * @return
     * @version <1> 2018/5/28 16:40 zhangshen: Created.
     */
    @Override
    public ResultMessage findStorageByIds(String ids){
        //数据转换
        JSONObject json = JSONObject.fromObject(ids);
        JSONArray jsonArray = JSONArray.fromObject(json.getString("ids"));
        Object[] idsObj = jsonArray.toArray();
        List<Integer> idList = new ArrayList<Integer>();//报告id集合
        for(Object id : idsObj){
            if(!"null".equals(id.toString())){
                idList.add(Integer.parseInt(id.toString()));
            }
        }
        if(idList.size()>0){
            List<DataStorage> list = storageMapper.findStorageByIdList(idList);
            return ResultMessage.success(list);
        }else{
            return ResultMessage.fail();
        }
    }

    @Override
    public DataStorage findDataStorageById(Integer storageId) {
        DataStorage storage = storageMapper.findStorageById(storageId);
        return storage;
    }

    @Override
    public ResultMessage queryDataStorageSateSum(StorageParam storageParam) {
        List<Map<String, Object>> total=storageMapper.queryDataStorageSateNum(storageParam);
      //  List<Map<String, Object>> totalSensor=storageMapper.queryDataStorageSensorNum(storageParam);
        LocalDate localDate=LocalDate.now();
        storageParam.setBeginTime(localDate.toString());
        storageParam.setEndTime(localDate.toString());
        List<Map<String, Object>> today=storageMapper.queryDataStorageSateNum(storageParam);
        Map map=new HashMap();
        map.put("total",total);
        //map.put("totalSensor",totalSensor);
        map.put("today",today);
        return ResultMessage.success(map);
    }

    @Override
    public ResultMessage queryDataSum(StorageParam storageParam) {
        List<Map<String, Object>> storageSum=storageMapper.queryDataSum(storageParam);
        List<Map<String, Object>> downSum=dataOrderMapper.queryDataOrderSum();
        List<Map<String, Object>> archiveSum=dataArchiveMapper.queryDataArchiveSum();
        Map map=new HashMap();
        map.put("storageSum",storageSum);//原始影像总计
        map.put("archiveSum",archiveSum);//归档总计
        map.put("downSum",downSum);//下载总计
        return ResultMessage.success(map);
    }

    @Override
    public ResultMessage queryGFDatas(StorageParam storageParam) {
        if(StringUtils.isNotBlank(storageParam.getSatellites())){
            storageParam.setSatelliteArr(storageParam.getSatellites().split(","));
        }

        List<Map<String,Object>> list =  storageMapper.queryGFDatas(storageParam);
        return ResultMessage.success(list);
    }

    @Override
    public ResultMessage querySceneDatas(StorageParam storageParam) {
        List<DataStorage> list = storageMapper.querySceneDatas(storageParam);


        //形成数组序列
        List<String> sceneList = new ArrayList<String>();
        if(storageParam != null){
            String sceneStart = storageParam.getSceneStart();
            String sceneEnd = storageParam.getSceneEnd();
            if(StringUtils.isNotBlank(sceneStart) && StringUtils.isNotBlank(sceneEnd)){
                Integer s1 = Integer.parseInt(sceneStart);
                Integer s2 = Integer.parseInt(sceneEnd);
                while(s1 <= s2){
                    sceneList.add(s1.toString());
                    s1 += 1;
                }
            }
        }

        if(list != null && list.size() > 0 && sceneList.size() > 0){
             for(DataStorage storage : list){
                    sceneList.remove(storage.getSceneId());
            }
        }

        return ResultMessage.success(sceneList);
    }


    @Override
    public ResultMessage queryDatasForReport(StorageParam storageParam) {
        Map<String,Object> voMap = new HashMap<String ,Object>();
        //本周
        Date date = new Date();  //当天
        String curDate = DateUtil.dateToString(date,"yyyy-MM-dd");

        OrderParam orderParam = new OrderParam();
        if(StringUtils.isBlank(storageParam.getBeginTime())){

            //获取周报
            //获取当前星期几
            int weekDay = DateUtil.weekOfDate(date); //一周的第一天
            // 周一
            String firstDate = DateUtil.dateToString(DateUtil.subDay(date , weekDay -1),"yyyy-MM-dd");
            //入库时间起止
            storageParam.setBeginTime(firstDate);

            orderParam.setBeginTime(firstDate);
        }else{
            orderParam.setBeginTime(storageParam.getBeginTime());
        }


        if(StringUtils.isBlank(storageParam.getEndTime())){
            orderParam.setEndTime(curDate);
            storageParam.setEndTime(curDate);
        }else{
            orderParam.setEndTime(storageParam.getEndTime());
        }

        if(StringUtils.isNotBlank(storageParam.getSatellites())){
            storageParam.setSatelliteArr(storageParam.getSatellites().split(","));
        }


        //查询数据的起止时间
        voMap.put("beginTime", storageParam.getBeginTime() );
        voMap.put("endTime", storageParam.getEndTime());

        //统计入库数据
        List<Map<String,Object>> storageList = storageMapper.queryDatasForReport(storageParam);
        voMap.put("storageList" , storageList);


        //统计订单数据
        List<Map<String,Object>> orderList = dataOrderMapper.queryDataForReport(orderParam);
        voMap.put("orderList", orderList);


        return ResultMessage.success(voMap);
    }
}
