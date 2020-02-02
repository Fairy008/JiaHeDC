package com.jh.forum.bbs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.base.service.impl.BaseServiceImpl;
import com.jh.forum.bbs.mapping.IForumDataGoodsMapper;
import com.jh.forum.bbs.service.IForumDataGoodsService;
import com.jh.forum.bbs.vo.DataGoodsVo;
import com.jh.util.cache.IdTransformUtils;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description 数据订单
 * @Version <1> 2019-08-14 sxj:Create.
 **/
@Service
@Transactional
public class ForumDataGoodsServiceImpl extends BaseServiceImpl<DataGoodsVo,DataGoodsVo,Integer> implements IForumDataGoodsService {

    @Autowired
    private IForumDataGoodsMapper forumDataGoodsMapper;


    /**
     * 新增
     * @param dataGoodsVo
     * @return
     */
    @Override
    public ResultMessage saveDataGoods(DataGoodsVo dataGoodsVo) {
        forumDataGoodsMapper.saveDataGoods(dataGoodsVo);
        return ResultMessage.success();
    }

    /**
     * 删除
     * @param dataGoodsVo
     * @return
     */
    @Override
    public ResultMessage deleteDataGoods(DataGoodsVo dataGoodsVo) {
        forumDataGoodsMapper.deleteDataGoods(dataGoodsVo);
        return ResultMessage.success();
    }

    /**
     * 查询
     * @param id
     * @return
     */
    @Override
    public ResultMessage queryDataDoodsList(Integer id) {
        forumDataGoodsMapper.queryDataDoodsList(id);
        return ResultMessage.success();
    }

    @Override
    public PageInfo<DataGoodsVo> getListByOrderGoods(DataGoodsVo dataGoodsVo) {
        PageHelper.startPage(dataGoodsVo.getPage(),dataGoodsVo.getRows());
        List<DataGoodsVo> list = forumDataGoodsMapper.getListByOrderGoods(dataGoodsVo);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<DataGoodsVo>(list);
    }


    @Override
    protected IBaseMapper<DataGoodsVo, DataGoodsVo, Integer> getDao() {
        return null;
    }


}
