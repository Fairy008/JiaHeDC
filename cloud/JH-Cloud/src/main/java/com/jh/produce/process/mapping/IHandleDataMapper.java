package com.jh.produce.process.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.produce.process.entity.HandleData;
import com.jh.produce.process.entity.RelateTaskStorage;
import com.jh.produce.process.model.HandleDataParam;
import com.jh.produce.process.model.HandleRelateDataParam;
import com.jh.produce.process.model.RelateHandleSatParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IHandleDataMapper extends IBaseMapper<HandleDataParam, HandleData, Integer> {
    /**
     * 任务分页查询
     * @param handleDataParam
     * @return
     * @version <1> 2018-03-12 cxj： Created.
     */
    List<HandleData> taskPage(HandleDataParam handleDataParam);
    /**
     * 保存任务存储关联
     * @param relateTaskStorageList
     * @version <1> 2018-03-13 cxj： Created.
     */
//    void saveTaskStorageRelate(HandleData handleData);
    void saveTaskStorageRelate(List<RelateTaskStorage> relateTaskStorageList);


    /**
     * 通过任务ID，查询关联存储数据
     * @param handleId
     * @version <1> 2018-03-14 cxj： Created.
     */
//    List<RelateTaskStorageParam> findTaskAndStorageList(Integer handleId);

    /**
     * 通过卫星ID，查询关联算法数据
     * @param satId
     * @version <1> 2018-03-14 cxj： Created.
     */
    List<RelateHandleSatParam> findHandleConfigBySatId(Integer satId);

    /**
     * 通过任务ID，修改任务状态
     * @param handleData
     */
    void updateHandleStatus(HandleData handleData);

    /**
     * 根据数据处理任务ID ，查询处理的文件link地址
     * @param handleId
     * @return
     * @version <1> 2018-04-08 xzg： Created.
     */
    List<String> findHandleFileLink(Integer handleId);


    /**
     * 根据任务删除步骤
     * @param handleId
     */
    void deleteStepByHandleId(Integer handleId);

    /**
     * 更新任务数据执行顺序
     * @param dataIdList
     * @return
     * @version <1> 2019-10-08 cxw： Created.
     */
    int updateHandleDataIndex(List<HandleRelateDataParam> dataIdList);


    /**
     * 根据数据处理任务ID ，查询处理的文件link地址
     * @param handleId
     * @return
     * @version <1> 2019-10-08 cxw： Created.
     */
    List<HandleRelateDataParam> findHandleFileLinkList(Integer handleId);
}