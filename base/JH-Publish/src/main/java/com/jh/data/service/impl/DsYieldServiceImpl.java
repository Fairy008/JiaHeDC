package com.jh.data.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.data.entity.DsResultimage;
import com.jh.data.entity.DsYield;
import com.jh.data.mapping.IDsYieldMapper;
import com.jh.data.model.DsYieldParam;
import com.jh.data.model.ProductQueryParam;
import com.jh.data.model.ReportCreateParam;
import com.jh.data.service.IDsYieldService;
import com.jh.util.DateUtil;
import com.jh.util.JsonUtils;
import com.jh.util.cache.IdTransformUtils;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * 1.估产实现类
 *
 * @version <1> 2018-05-11 12:44 zhangshen: Created.
 */
@Service
@Transactional
public class DsYieldServiceImpl implements IDsYieldService{

    @Autowired
    private IDsYieldMapper dsYieldMapper;

    /**
     * 获取估产的报告生成列表
     * @param reportCreateParam
     * @return
     * @version <1> 2018-05-11 zhangshen: Created.
     */
    @Override
    public PageInfo<DsYield> findDsYieldReportCreateData(ReportCreateParam reportCreateParam){
        PageHelper.startPage(reportCreateParam.getPage(), reportCreateParam.getRows());
        List<DsYield> list = dsYieldMapper.findDsYieldReportCreateData(reportCreateParam);
        return new PageInfo<DsYield>(list);
    }

    @Override
    public PageInfo<DsYield> findByPage(DsYieldParam dsYieldParam) {
        PageHelper.startPage(dsYieldParam.getPage(), dsYieldParam.getRows());
        List<DsYield> list = dsYieldMapper.findByPage(dsYieldParam);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<DsYield>(list);
    }

    @Override
    public ResultMessage updateDsYield(DsYieldParam dsYieldParam) {
        dsYieldMapper.updateDsYield(dsYieldParam);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage findById(Integer id) {
        DsYield dsYield=dsYieldMapper.findById(id);
        return ResultMessage.success(dsYield);
    }

    @Override
    public ResultMessage publish(DsYieldParam dsYieldParam) {
        int num = dsYieldMapper.publish(dsYieldParam);
        if(num > 0){
            return ResultMessage.success();
        }else{
            return ResultMessage.fail();
        }
    }

    /**
     * 根据区域、作物、精度 查询数据时间列表
     * @param reportCreateParam
     * @return
     * @version<1> 2018-07-18 zhangshen: Created.
     */
    @Override
    public List<DsYield> queryDateTimeList(ReportCreateParam reportCreateParam) {
        return dsYieldMapper.queryDateTimeList(reportCreateParam);
    }

    /**
     * 根据任务ID当年指数
     * @param productQueryParam
     * @return
     */
    @Override
    public PageInfo<Object> findDataSatListByTaskId(ProductQueryParam productQueryParam){
        PageHelper.startPage(productQueryParam.getPage(), productQueryParam.getRows());
        DsYieldParam yield=new DsYieldParam();
        yield.setProductId(productQueryParam.getProductId());
        yield.setSord(productQueryParam.getSord());
        yield.setSidx(productQueryParam.getSidx());
        List<DsYield>list=dsYieldMapper.findListByProductId(yield);
        //Id转名称
        IdTransformUtils.idTransForList(list);
        return new PageInfo<Object>((List)list);
    }


    /**
     * 根据任务ID更新各项指数发布信息
     * @version <1> 2018-09-26 lijie： Created.
     */
    @Override
    public ResultMessage updatePublishStatusByTaskId(DsResultimage product){
        DsYieldParam yield=new DsYieldParam();
        yield.setProductId(product.getRgId());
        yield.setPublishStatus(product.getPublishStatus());
        yield.setPublishTime(DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
        yield.setPublisher(product.getPublisher());
        dsYieldMapper.updateDsYield(yield);
        return ResultMessage.success();
    }

    /**
     * 根据任务ID删除各项指数发布信息
     * @version <1> 2018-09-26 lijie： Created.
     */
    @Override
    public ResultMessage deleteDataSetByTaskId(Integer productId){
        dsYieldMapper.deleteByProductId(productId);
        return ResultMessage.success();
    }

    /**
     * 修改数据集数据
     * @param dataStr 具体指数
     * @return
     * @version <1> 2018-09-26 lijie： Created.
     */
    @Override
    public ResultMessage updateDataSat(String dataStr){
        DsYieldParam area = JsonUtils.jsonToPojo(dataStr, DsYieldParam.class);
        dsYieldMapper.updateDsYield(area);
        return ResultMessage.success();
    }
}
