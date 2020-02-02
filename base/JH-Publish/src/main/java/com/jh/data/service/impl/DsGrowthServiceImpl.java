package com.jh.data.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.data.entity.DsGrowth;
import com.jh.data.entity.DsResultimage;
import com.jh.data.mapping.IDsGrowthMapper;
import com.jh.data.model.DsGrowthParam;
import com.jh.data.model.ProductQueryParam;
import com.jh.data.model.ReportCreateParam;
import com.jh.data.service.IDsGrowthService;
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
 * description:
 *
 * @version <1> 2018-05-09 wl: Created.
 */
@Service
@Transactional
public class DsGrowthServiceImpl implements IDsGrowthService {

    @Autowired
    private IDsGrowthMapper dsGrowthMapper;

    @Override
    public PageInfo<DsGrowth> findByPage(DsGrowthParam dsGrowthParam) {
        PageHelper.startPage(dsGrowthParam.getPage(), dsGrowthParam.getRows());
        List<DsGrowth> list = dsGrowthMapper.findByPage(dsGrowthParam);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<DsGrowth>(list);
    }

    @Override
    public ResultMessage updateDsGrowth(DsGrowthParam dsGrowthParam) {
        dsGrowthMapper.updateDsGrowth(dsGrowthParam);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage findById(Integer id) {
        DsGrowth dsGrowth=dsGrowthMapper.findById(id);
        return ResultMessage.success(dsGrowth);
    }

    /**
     * 获取长势的报告生成列表
     * @param reportCreateParam
     * @return
     * @version <1> 2018-05-11 zhangshen: Created.
     */
    @Override
    public PageInfo<DsGrowth> findDsGrowthReportCreateData(ReportCreateParam reportCreateParam){
        PageHelper.startPage(reportCreateParam.getPage(), reportCreateParam.getRows());
        List<DsGrowth> list = dsGrowthMapper.findDsGrowthReportCreateData(reportCreateParam);
        return new PageInfo<DsGrowth>(list);
    }

    @Override
    public ResultMessage publish(DsGrowthParam dsGrowthParam) {
        int num =   dsGrowthMapper.publish(dsGrowthParam);
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
    public List<DsGrowth> queryDateTimeList(ReportCreateParam reportCreateParam) {
        return dsGrowthMapper.queryDateTimeList(reportCreateParam);
    }

    /**
     * 根据任务ID当年指数
     * @param productQueryParam
     * @return
     */
    @Override
    public PageInfo<Object> findDataSatListByTaskId(ProductQueryParam productQueryParam){
        PageHelper.startPage(productQueryParam.getPage(), productQueryParam.getRows());
        DsGrowthParam growth=new DsGrowthParam();
        growth.setProductId(productQueryParam.getProductId());
        growth.setSord(productQueryParam.getSord());
        growth.setSidx(productQueryParam.getSidx());
        List<DsGrowth>list=dsGrowthMapper.findListByProductId(growth);
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
        DsGrowthParam growth=new DsGrowthParam();
        growth.setProductId(product.getRgId());
        growth.setPublishStatus(product.getPublishStatus());
        growth.setPublishTime(DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
        growth.setPublisher(product.getPublisher());
        dsGrowthMapper.updateDsGrowth(growth);
        return ResultMessage.success();
    }

    /**
     * 根据任务ID删除各项指数发布信息
     * @version <1> 2018-09-26 lijie： Created.
     */
    @Override
    public ResultMessage deleteDataSetByTaskId(Integer productId){
        dsGrowthMapper.deleteByProductId(productId);
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
        DsGrowthParam area = JsonUtils.jsonToPojo(dataStr, DsGrowthParam.class);
        dsGrowthMapper.updateDsGrowth(area);
        return ResultMessage.success();
    }
}
