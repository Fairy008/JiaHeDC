package com.jh.forum.bbs.mapping;

import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.bbs.entity.ForumShoppingCar;
import com.jh.forum.bbs.vo.ShoppingCarVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IForumShoppingCarMapper extends IBaseMapper<ForumShoppingCar, ForumShoppingCar, Integer> {

    //新增数据商品
    void addShoppingCar(ForumShoppingCar forumShoppingCar);

    int queryCarGoods(ForumShoppingCar forumShoppingCar);

    int deleteShoppingCarRecord(ShoppingCarVo shoppingCarVo);

    List<ShoppingCarVo> queryShoppingCar(Integer userId);
}
