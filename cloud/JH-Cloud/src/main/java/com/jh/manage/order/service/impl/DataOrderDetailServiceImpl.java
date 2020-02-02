package com.jh.manage.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.manage.order.entity.DataOrderDetail;
import com.jh.manage.order.mapping.IDataOrderDetailMapper;
import com.jh.manage.order.service.IDataOrderDetailService;
import com.jh.manage.storage.entity.DataStorage;
import com.jh.manage.storage.model.StorageParam;
import com.jh.manage.storage.service.IDataStorageService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *数据订单服务：
 * 1.创建订单明细
 * @version <1> 2018-03-12 cxw:Created.
 */
@Service
@Transactional
public class DataOrderDetailServiceImpl extends BaseServiceImpl<DataOrderDetail, DataOrderDetail, Integer> implements IDataOrderDetailService {

    @Autowired
    private IDataOrderDetailMapper dataOrderDetailMapper;

    @Autowired
    private IDataStorageService dataStorageService;


    /**
     *创建数据订单详情
     * @param dataOrderDetail 数据订单详情对象：元数据对象ID参数为必须获取参数，不能为空
     * @return ResultMessage
     * @version <1> 2018-03-12 cxw： Created.
     */
    @Override
    public ResultMessage saveDataOrderDetail(DataOrderDetail dataOrderDetail) {
        ResultMessage result = ResultMessage.success();
        int num = dataOrderDetailMapper.insertDataOrderDetail(dataOrderDetail);
        if(num==0){
            result = ResultMessage.fail();
            result.setMsg("订单详情添加失败");
        }
        return result;
    }

    /**
     *批量插入数据订单详情
     * @param dataOrderDetails 数据订单详情对象：元数据对象ID参数为必须获取参数，不能为空
     * @return ResultMessage
     * @version <1> 2018-03-16 cxw： Created.
     */
//    public ResultMessage saveDataOrderDetailList(List<DataOrderDetail> dataOrderDetails){
//        ResultMessage result = ResultMessage.success();
//        if(dataOrderDetails.size()>0)
//        {
//            int num = dataOrderDetailMapper.insertDataOrderDetailList(dataOrderDetails);
//            if(num==0){
//                result = ResultMessage.fail();
//                result.setMsg("订单详情添加失败");
//            }
//        }
//        else{
//            result = ResultMessage.fail();
//            result.setMsg("订单详情对象为空");
//        }
//        return result;
//    }

    /**
     *删除订单详情
     * @param orderId 订单Id
     * @return ResultMessage
     * @version <1> 2018-03-13 cxw： Created.
     */
    @Override
    public ResultMessage delOrderDetailByOrderId(Integer orderId) {
        ResultMessage result = ResultMessage.success();
        int num = dataOrderDetailMapper.delOrderDetailByOrderId(orderId);
        if(num==0){
            result = ResultMessage.fail();
            result.setMsg("订单删除失败");
        }
        else{
            result.setMsg("订单删除成功");
        }
        return result;
    }

    /**
     * 查询订单详情：根据订单主键查询订单关联文件详情
     * @param orderId 订单ID
     * @return
     * @version <1> 2018-03-15 cxw： Created.
     */
    @Override
    public ResultMessage findOrderDetailByOrderId(Integer orderId) {

        //查询明细
        List<DataOrderDetail> list = dataOrderDetailMapper.findOrderListByOrderId(orderId);

        return ResultMessage.success(list);

//        return dataStorageService.findOrderDetailByOrderId(orderId);
    }

    /**
     * 分页查询订单详情：根据订单主键查询订单关联文件详情
     * @param storageParam 元数据对象
     * @return PageInfo 分页对象
     * @version <1> 2018-03-015 cxw： Created.
     */
    @Override
    public PageInfo<DataStorage> findOrderFileByPage(StorageParam storageParam) {
        PageHelper.startPage(storageParam.getPage(), storageParam.getRows());
        return dataStorageService.findOrderFileByPage(storageParam);
    }

    @Override
    public ResultMessage findOrderDetailById(Integer orderDetailId) {
        if(orderDetailId == null){
            return ResultMessage.fail("订单明细ID不能为空");
        }

        DataOrderDetail orderDetail = dataOrderDetailMapper.findOrderDetailById(orderDetailId);

        return ResultMessage.success(orderDetail);
    }

    @Override
    protected IBaseMapper<DataOrderDetail, DataOrderDetail, Integer> getDao() {
        return null;
    }
}
