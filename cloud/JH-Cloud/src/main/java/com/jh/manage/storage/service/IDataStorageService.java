package com.jh.manage.storage.service;

import com.github.pagehelper.PageInfo;
import com.jh.manage.storage.entity.DataStorage;
import com.jh.manage.storage.model.StorageParam;
import com.jh.vo.ResultMessage;

import java.util.HashMap;
import java.util.List;

/**
 * description:数据检索服务
 *  1.根据行政区域检索元数据
 *  2.根据经纬度检索元数据
 * @version <1> 2018-01-31 lcw： Created.
 */
public interface IDataStorageService {

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
     * @version<1> 2018-02-27 lcw : Created.
     */
    ResultMessage findDatas(StorageParam storageParam);

    /**
     * 通过ID数组，查询数据存储列表数据
     * @param taskStorageIdList
     * @return
     * @version<1> 2018-03-14 cxj : Created.
     */
    List<DataStorage> findStorageByIdList(List<Integer> taskStorageIdList);

    /**
     * 查询订单详情：根据订单主键查询订单关联文件详情
     * @param orderId 订单ID
     * @return
     * @version <1> 2018-03-15 cxw： Created.
     */
    ResultMessage findOrderDetailByOrderId(Integer orderId);

    /**
     * 分页查询订单详情：根据订单主键查询订单关联文件详情
     * @param storageParam 元数据对象
     * @return PageInfo 分页对象
     * @version <1> 2018-03-015 cxw： Created.
     */
    PageInfo<DataStorage> findOrderFileByPage(StorageParam storageParam);

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

    /**
     * 订单分发时添加再加工文件信息
     * @param dataStorage
     * @return int
     * @version <1> 2018-04-16 cxw： Created.
     */
    int insertDataStorageForDistributeFile(DataStorage dataStorage);
    /**
     *统计不同卫星的元数据数量
     * @param  storageParam 元数据对象
     * @version <1> 2018-04-18 wl： Created.
     */
    public ResultMessage queryDataStorageSateNum(StorageParam storageParam);

    /**
     * @description: 分页查询
     * @param storageParam
     * @return
     * @version <1> 2018-04-25 wl： Created.
     */
    PageInfo<DataStorage> findByPage(StorageParam storageParam);

    /**
     * 根据源数据ID获取源数据
     * @param storageId
     * @return
     * @version<1> 2018-05-01 lcw : Created.
     */
    ResultMessage findStorageById(Integer storageId);

    /**
     * Description: 根据ids查询首页数据,将id查询成对象
     * @param ids
     * @return
     * @version <1> 2018/5/28 16:40 zhangshen: Created.
     */
    ResultMessage findStorageByIds(String ids);


    /**
     * Description: 根据storageId,查询原始数据
     * @param storageId
     * @return
     * @version <1>2018-06-01 wl : Created.
     */
    DataStorage findDataStorageById(Integer storageId);

    /**
     *统计不同卫星数据的数量(当天和总计)
     * @param  storageParam 订单对象
     * @version <1> 2018-06-12 wl： Created.
     */
    public ResultMessage queryDataStorageSateSum(StorageParam storageParam);

    /**
     *统计原始影像数据总和，数据订单下载总和，归档数据总和
     * @param  storageParam 订单对象
     * @version <1> 2018-06-13 wl： Created.
     */
    public ResultMessage queryDataSum(StorageParam storageParam);

    /**
     * 统计高分数据入库数量（GF1、GF2、GF3、GF4）
     * @param storageParam
     * @return
     */
    ResultMessage queryGFDatas(StorageParam storageParam);

    ResultMessage querySceneDatas(StorageParam storageParam);

    /**
     * 统计高分入库数据、订单数据信息
     * 包含：总入库景数、本周入库景数，各订单状态的总数，本周数量等信息
     * @param storageParam
     * @return
     */
    ResultMessage queryDatasForReport(StorageParam storageParam);
}
