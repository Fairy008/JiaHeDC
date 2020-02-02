package com.jh.forum.bbs.service;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.service.IBaseService;
import com.jh.forum.bbs.vo.DataGoodsVo;
import com.jh.vo.ResultMessage;

/**
 * @Description 数据订单
 * @Version <1> 2019-08-14 sxj:Create.
 **/
public interface IForumDataGoodsService extends IBaseService<DataGoodsVo,DataGoodsVo,Integer> {

    ResultMessage saveDataGoods(DataGoodsVo dataGoodsVo);

    ResultMessage deleteDataGoods(DataGoodsVo dataGoodsVo);

    ResultMessage queryDataDoodsList(Integer id);

    PageInfo<DataGoodsVo> getListByOrderGoods(DataGoodsVo dataGoodsVo);

}
