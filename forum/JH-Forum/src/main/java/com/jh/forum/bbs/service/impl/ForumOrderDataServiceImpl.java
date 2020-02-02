package com.jh.forum.bbs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.base.service.impl.BaseServiceImpl;
import com.jh.forum.bbs.Enum.AccuracyRangeEnum;
import com.jh.forum.bbs.entity.ForumLabel;
import com.jh.forum.bbs.entity.ForumOrderData;
import com.jh.forum.bbs.mapping.IForumOrderDataMapper;
import com.jh.forum.bbs.service.IForumOrderDataService;
import com.jh.forum.bbs.util.CommonUtils;
import com.jh.forum.bbs.vo.OrderDataVo;
import com.jh.util.cache.IdTransformUtils;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @Description:  数据定制实现类
 * @version<1> 2019-07-01 lcw :Created.
 */
@Service
@Transactional
public class ForumOrderDataServiceImpl extends BaseServiceImpl<OrderDataVo, ForumOrderData,Integer> implements IForumOrderDataService {

    @Autowired
    private IForumOrderDataMapper orderDataMapper;


    @Override
    protected IBaseMapper<OrderDataVo, ForumOrderData, Integer> getDao() {
        return orderDataMapper;
    }

    @Override
    public ResultMessage saveData(OrderDataVo orderDataVo) {

        if(StringUtils.isBlank(orderDataVo.getCrop())){
            return ResultMessage.fail("作物不能为空");
        }

        if(StringUtils.isBlank(orderDataVo.getAccuracyRange())){
            return ResultMessage.fail("精度不能为空");
        }

        if(StringUtils.isBlank(orderDataVo.getDataClassify())){
            return ResultMessage.fail("分类不能为空");
        }

        if(orderDataVo.getTimeFlag() == null){

            return ResultMessage.fail("时间不能为空");
        }

        if(orderDataVo.getDataShow() == null){
            return ResultMessage.fail("展现形式不能为空");
        }


        ForumOrderData orderData = new ForumOrderData();
        LocalDate endDate = LocalDate.now();
        //将前端传递过来的值进行转换
        orderDataVo.setAccuracyRange(CommonUtils.getAccuracyRange(orderDataVo.getAccuracyRange()));
        if(orderDataVo.getTimeFlag() != 5){ //将起止时间填入开始和截止日期中
            LocalDate startDate = CommonUtils.getDate(orderDataVo.getTimeFlag());
            orderDataVo.setStartDate(startDate);
            orderDataVo.setEndDate(endDate);
        }

        orderDataVo.setCreateTime(LocalDateTime.now());
        BeanUtils.copyProperties(orderDataVo, orderData);

        orderDataMapper.save(orderData);

        return ResultMessage.success("数据定制成功");
    }

    @Override
    public PageInfo<OrderDataVo> findByPage(OrderDataVo orderDataVo) {
        PageHelper.startPage(orderDataVo.getPage(), orderDataVo.getRows());
        List<OrderDataVo> list = orderDataMapper.queryByPage(orderDataVo);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<OrderDataVo>(list) ;
    }

    @Override
    public PageInfo<ForumOrderData> findDataOrderPage(ForumOrderData orderData) {
        PageHelper.startPage(orderData.getPage(), orderData.getRows());
        List<ForumOrderData> list = orderDataMapper.findByPageCms(orderData);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<ForumOrderData>(list);
    }

}
