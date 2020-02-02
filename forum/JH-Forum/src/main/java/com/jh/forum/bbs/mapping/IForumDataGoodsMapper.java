package com.jh.forum.bbs.mapping;

import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.bbs.vo.DataGoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 数据订单
 * @Version <1> 2019-08-14 sxj:Create.
 **/
@Mapper
public interface IForumDataGoodsMapper extends IBaseMapper<DataGoodsVo,DataGoodsVo,Integer> {

    //新增数据商品
    void saveDataGoods(DataGoodsVo dataGoodsVo);

    //删除数据商品
    int deleteDataGoods(DataGoodsVo dataGoodsVo);

    //查询数据商品
    List<Integer> queryDataDoodsList(@Param("id") Integer id);

    //查询分页信息列表
    List<DataGoodsVo> getListByOrderGoods(DataGoodsVo dataGoodsVo);

}
