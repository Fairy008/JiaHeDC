package com.jh.manage.order.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.manage.order.model.DataOrderPath;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *数据订单服务：
 * 1.创建订单明细
 * @version <1> 2018-03-12 cxw:Created.
 */
@Mapper
public interface IDataOrderPathMapper extends IBaseMapper<DataOrderPath, DataOrderPath , Integer> {

    /**
     *数据订单路径存储
     * @param dataOrderPath 数据订单路径存储对象
     * @return ResultMessage
     * @version <1> 2018-03-16 cxw： Created.
     */
     int insertDataOrderPath(DataOrderPath dataOrderPath);

    /**
     *数据订单路径批量存储
     * @param dataOrderPaths 数据订单路径存储对象
     * @return ResultMessage
     * @version <1> 2018-03-16 cxw： Created.
     */
    int insertDataOrderPathList(List<DataOrderPath> dataOrderPaths);

    /**
     *根据订单ID查询分发路径
     * @param orderId 订单ID
     * @return list
     * @version <1> 2018-03-19 cxw： Created.
     */
    public List<DataOrderPath> findDataOrderPathList(Integer orderId);
}
