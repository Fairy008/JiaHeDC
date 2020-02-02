package com.jh.manage.storage.mapping;

import com.jh.manage.storage.entity.DataStorage;
import com.jh.manage.storage.model.StorageParam;
import com.jh.vo.ResultMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface IDataStorageMapper {

    /**
     * 根据查询条件获取元数据信息
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
    List<DataStorage> findDatas(StorageParam storageParam);

    /**
     * 通过ID数组，查询数据存储列表数据
     * @param taskStorageIdList
     * @return
     * @version<1> 2018-03-14 cxj : Created.
     */
    List<DataStorage> findStorageByIdList(@Param(value = "taskStorageIdList") List<Integer> taskStorageIdList);

    /**
     * 查询订单详情：根据订单主键查询订单详情
     * @param orderId 订单ID
     * @return
     * @version <1> 2018-03-15 cxw： Created.
     */
    List<DataStorage> findOrderDetailByOrderId(Integer orderId);

    /**
     * 分页查询订单详情：根据订单主键查询订单关联文件详情
     * @param storageParam 元数据对象
     * @return PageInfo 分页对象
     * @version <1> 2018-03-015 cxw： Created.
     */
    List<DataStorage> findOrderFileByPage(StorageParam storageParam);

    /**
     * 根据任务ID，查询关联存储文件列表数据
     * @param handleId
     * @return
     * @version <1> 2018-03-20 cxw： Created.
     */
    List<DataStorage> findTaskRelateStorageByHandleId(Integer handleId);

    /**
     * 根据步骤ID，查询关联存储文件信息
     * @param handleId
     * @return
     * @version <1> 2018-03-20 cxw： Created.
     */
    List<HashMap<Object,Object>> findStepRelateStorageListByHandleId(Integer handleId);


    /*
     *根据开始时间和结束时间 以及卫星名称 查询 相关的 datastorage 表信息
     *
     */
    List<DataStorage> findByTime(StorageParam storageParam);

    /**
     * 订单分发时添加再加工文件信息
     * @param dataStorage
     * @return int
     * @version <1> 2018-04-16 cxw： Created.
     */
    public int insertDataStorageForDistributeFile(DataStorage dataStorage);
    /**
     *统计不同卫星的元数据数量
     * @param  storageParam 订单对象
     * @version <1> 2018-06-13 wl： Created.
     */
    List<Map<String, Object>>queryDataStorageSateNum(StorageParam storageParam);

    /**
     *统计元数据数量合计
     * @param  storageParam 订单对象
     * @version <1> 2018-06-13 wl： Created.
     */
    List<Map<String, Object>>queryDataSum(StorageParam storageParam);

    /**
     * 根据源数据ID获取源数据Bean
     * @param storageId
     * @return
     */
    DataStorage findStorageById(Integer storageId);

    List<Map<String,Object>> queryTotalSatelliteEcharts(StorageParam storageParam);

    List<Map<String,Object>> queryGFDatas(StorageParam storageParam);

    List<DataStorage> querySceneDatas(StorageParam storageParam);

    /**
     * 统计高分数据总入库数、按时间段的入库景数（周报、时间阶段报告）
     * @param storageParam
     * @return
     *
     */
    List<Map<String,Object>> queryDatasForReport(StorageParam storageParam);
}