package com.jh.forum.bbs.service;

import com.jh.forum.base.service.IBaseService;
import com.jh.forum.bbs.entity.ForumShoppingCar;
import com.jh.forum.bbs.vo.ShoppingCarVo;
import com.jh.vo.ResultMessage;

import java.util.List;

public interface IForumShoppingCarService  extends IBaseService<ForumShoppingCar, ForumShoppingCar, Integer> {
    ResultMessage addShoppingCar(ForumShoppingCar forumShoppingCar);

    ResultMessage deleteShoppingCarRecord(ShoppingCarVo shoppingCarVo);

    ResultMessage queryShoppingCar (Integer userId);
}
