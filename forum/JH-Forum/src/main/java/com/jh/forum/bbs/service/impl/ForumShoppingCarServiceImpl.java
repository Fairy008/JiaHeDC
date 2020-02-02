package com.jh.forum.bbs.service.impl;

import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.base.service.impl.BaseServiceImpl;
import com.jh.forum.bbs.entity.ForumShoppingCar;
import com.jh.forum.bbs.mapping.IForumShoppingCarMapper;
import com.jh.forum.bbs.service.IForumShoppingCarService;
import com.jh.forum.bbs.vo.DownloadDataVo;
import com.jh.forum.bbs.vo.ShoppingCarVo;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ForumShoppingCarServiceImpl extends BaseServiceImpl<ForumShoppingCar, ForumShoppingCar,Integer> implements IForumShoppingCarService{

    @Autowired
    private IForumShoppingCarMapper forumShoppingCarMapper;

    @Override
    protected IBaseMapper<ForumShoppingCar, ForumShoppingCar, Integer> getDao() {
        return null;
    }

    @Override
    public ResultMessage addShoppingCar(ForumShoppingCar forumShoppingCar) {
        ResultMessage resultMessage = new ResultMessage();
        JSONObject jsonObject = new JSONObject();
        //判断该用户是否增加过同一个商品到购物车中
        Integer goods =forumShoppingCarMapper.queryCarGoods(forumShoppingCar);
        if(goods==0){//避免重复添加相同的商品
            //添加到购物车
            forumShoppingCarMapper.addShoppingCar(forumShoppingCar);
            jsonObject.put("repeat",false);
        }else{

            jsonObject.put("repeat",true);

        }
        //查询购物车商品数据的数量
        ForumShoppingCar newforumShoppingCar = new ForumShoppingCar();
        newforumShoppingCar.setUserId(forumShoppingCar.getUserId());
        Integer countGoods = forumShoppingCarMapper.queryCarGoods(newforumShoppingCar);
        jsonObject.put("total",countGoods);
        resultMessage.setData(jsonObject);
        return resultMessage;
    }

    @Override
    public ResultMessage deleteShoppingCarRecord(ShoppingCarVo shoppingCarVo) {
        forumShoppingCarMapper.deleteShoppingCarRecord(shoppingCarVo);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage queryShoppingCar(Integer userId) {
        List<ShoppingCarVo> list = forumShoppingCarMapper.queryShoppingCar(userId);
        list = setImageUrlForNewPath(list);
        return ResultMessage.success(list);
    }


    /*
     * 功能描述:将查询到的图片路径中的/ 替换成\
     * @Param:
     * @Return:
     * @version<1>  2019/9/5  wangli :Created
     */
    public List<ShoppingCarVo> setImageUrlForNewPath(List<ShoppingCarVo> list){
        if(CollectionUtils.isNotEmpty(list)){
            for(ShoppingCarVo shoppingCarVo : list){
                if(shoppingCarVo.getDataPath()!=null){
                    shoppingCarVo.setDataPath(CephUtils.getShowPath1(shoppingCarVo.getDataPath()));
                }
            }
        }
        return list;
    }
}
