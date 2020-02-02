package com.jh.data.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.data.entity.DsArea;
import com.jh.data.entity.DsResultimage;
import com.jh.data.mapping.IDsAreaMapper;
import com.jh.data.model.DsAreaParam;
import com.jh.data.model.ProductQueryParam;
import com.jh.data.model.ReportCreateParam;
import com.jh.data.service.IDsAreaService;
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
 * description:分布数据集明细实现类
 *
 * @version <1> 2018-05-07 wl: Created.
 */
@Service
@Transactional
public class DsAreaServiceImpl implements IDsAreaService {
    @Autowired
    private IDsAreaMapper dsAreaMapper;

    @Override
    public PageInfo<DsArea> findByPage(DsAreaParam dsAreaParam) {
        PageHelper.startPage(dsAreaParam.getPage(), dsAreaParam.getRows());
        List<DsArea> list = dsAreaMapper.findByPage(dsAreaParam);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<DsArea>(list);
    }

    @Override
    public ResultMessage updateDsArea(DsAreaParam dsAreaParam) {
        dsAreaMapper.updateDsArea(dsAreaParam);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage findById(Integer id) {
        DsArea dsArea=dsAreaMapper.findById(id);
        return ResultMessage.success(dsArea);
    }

    /**
     * 获取分布的报告生成列表
     * @param reportCreateParam
     * @return
     * @version<1> 2018-05-11 zhangshen: Created.
     */
    @Override
    public PageInfo<DsArea> findDsAreaReportCreateData(ReportCreateParam reportCreateParam) {
        PageHelper.startPage(reportCreateParam.getPage(), reportCreateParam.getRows());
        List<DsArea> list = dsAreaMapper.findDsAreaReportCreateData(reportCreateParam);
        return new PageInfo<DsArea>(list);
    }

    @Override
    public ResultMessage publish(DsAreaParam dsAreaParam) {
        int num = dsAreaMapper.publish(dsAreaParam);
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
    public List<DsArea> queryDateTimeList(ReportCreateParam reportCreateParam) {
        return dsAreaMapper.queryDateTimeList(reportCreateParam);
    }

    /**
     * 根据区域、作物、精度 查询数据
     * @param reportCreateParam
     * @param calculationDays null为查当前，其他值为同期
     * @return
     * @version<1> 2018-07-18 zhangshen: Created.
     */
    @Override
    public List<DsArea> findDsAreaByCondition(ReportCreateParam reportCreateParam, Integer calculationDays) {
        return dsAreaMapper.findDsAreaByCondition(reportCreateParam, calculationDays);
    }

    /**
     * 根据dsArea查询子级信息
     * @param dsArea
     * @return
     * @version<1> 2018-07-23 zhangshen: Created.
     */
    @Override
    public List<DsArea> getDsAreaListByParent(DsArea dsArea) {
        return dsAreaMapper.getDsAreaListByParent(dsArea);
    }

    /**
     * 根据任务ID当年指数
     * @param productQueryParam
     * @return
     */
    @Override
    public PageInfo<Object> findDataSatListByTaskId(ProductQueryParam productQueryParam){
        PageHelper.startPage(productQueryParam.getPage(), productQueryParam.getRows());
        DsAreaParam area=new DsAreaParam();
        area.setProductId(productQueryParam.getProductId());
        area.setSord(productQueryParam.getSord());
        area.setSidx(productQueryParam.getSidx());
        List<DsArea>list=dsAreaMapper.findListByProductId(area);
        //Id转化
        IdTransformUtils.idTransForList(list);
        return new PageInfo<Object>((List)list);
    }


    /**
     * 根据任务ID更新各项指数发布信息
     * @version <1> 2018-09-26 lijie： Created.
     */
    @Override
    public ResultMessage updatePublishStatusByTaskId(DsResultimage product){
        DsAreaParam area=new DsAreaParam();
        area.setProductId(product.getRgId());
        area.setPublishStatus(product.getPublishStatus());
        area.setPublishTime(DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
        area.setPublisher(product.getPublisher());
        dsAreaMapper.updateDsArea(area);
        return ResultMessage.success();
    }

    /**
     * 根据任务ID删除各项指数发布信息
     * @version <1> 2018-09-26 lijie： Created.
     */
    @Override
    public ResultMessage deleteDataSetByTaskId(Integer productId){
        dsAreaMapper.deleteByProductId(productId);
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
        DsAreaParam area = JsonUtils.jsonToPojo(dataStr, DsAreaParam.class);
        dsAreaMapper.updateDsArea(area);
        return ResultMessage.success();
    }
}
