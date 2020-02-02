package com.jh.manage.order.service.impl;

import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.manage.order.mapping.IDataOrderPathMapper;
import com.jh.manage.order.model.DataOrderPath;
import com.jh.manage.order.service.IDataOrderPathService;
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
public class DataOrderPathServiceImpl extends BaseServiceImpl<DataOrderPath, DataOrderPath, Integer> implements IDataOrderPathService {

    @Autowired
    private IDataOrderPathMapper dataOrderPathMapper;

    /**
     *数据订单
     * @param dataOrderPath 数据订单路径存储对象
     * @return ResultMessage
     * @version <1> 2018-03-16 cxw： Created.
     */
    @Override
    public ResultMessage saveDataOrderPath(DataOrderPath dataOrderPath) {
        ResultMessage result = ResultMessage.success();
        if(dataOrderPath.checkParam().isFlag())
        {
            int num  = dataOrderPathMapper.insertDataOrderPath(dataOrderPath);
            if(num==0){
                result = ResultMessage.fail();
                result.setMsg("订单详情添加失败");
            }
            return result;
        }
        else{
            result = ResultMessage.fail();
            result.setMsg(dataOrderPath.checkParam().getMsg());
            return result;
        }
    }
    /**
     *数据订单路径批量存储
     * @param dataOrderPaths 数据订单路径存储对象
     * @return ResultMessage
     * @version <1> 2018-03-16 cxw： Created.
     */
    @Override
    public ResultMessage saveDataOrderPathList(List<DataOrderPath> dataOrderPaths){
        ResultMessage result = ResultMessage.success();
        if(dataOrderPaths.size()>0){
             int num =  dataOrderPathMapper.insertDataOrderPathList(dataOrderPaths);
            if(num==0){
                result = ResultMessage.fail();
                result.setMsg("订单详情添加失败");
            }
        }
        else{
            result = ResultMessage.fail();
            result.setMsg("订单路径为空");
        }
        return result;
    }

    /**
     *根据订单ID查询分发路径
     * @param orderId 订单ID
     * @return ResultMessage
     * @version <1> 2018-03-19 cxw： Created.
     */
    @Override
    public ResultMessage findDataOrderPathList(Integer orderId) {
        ResultMessage result = ResultMessage.success();
        if(orderId!=null){
            List<DataOrderPath> dops = dataOrderPathMapper.findDataOrderPathList(orderId);
            if(dops.size()>0){
                result.setData(dops);
            }
            else {
                result = ResultMessage.fail();
                result.setMsg("下载文件不存在");
            }
        }
        else{
            result = ResultMessage.fail();
            result.setMsg("订单不存在");
        }
        return result;
    }

    @Override
    protected IBaseMapper<DataOrderPath, DataOrderPath, Integer> getDao() {
        return null;
    }
}
