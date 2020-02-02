package com.jh.forum.bbs.controller;

import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.entity.ForumShoppingCar;
import com.jh.forum.bbs.service.IForumShoppingCarService;
import com.jh.forum.bbs.vo.ShoppingCarVo;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(value = "购物车",description = "购物车")
@RestController
@RequestMapping("/shoppingCar")
public class ForumShoppingCarController extends BaseController {

    @Autowired
    private IForumShoppingCarService forumShoppingCarService;

    /*
     * 功能描述:添加数据中心数据到购物车
     * @Param:
     * @Return:
     * @version<1>  2019/8/28  wangli :Created
     */
    @ApiOperation(value = "新增数据到购物车中",notes = "新增数据到购物车中")
    @ApiImplicitParam(name = "shoppingCar",value = "数据实体",required = true,dataType = "shoppingCar")
    @PostMapping("/addShoppingCar")
    public ResultMessage addShoppingCar (@RequestBody ForumShoppingCar forumShoppingCar){
        return  forumShoppingCarService.addShoppingCar(forumShoppingCar);
    }

    /*
     * 功能描述: 删除购物车数据
     * @Param:
     * @Return:
     * @version<1>  2019/8/30  wangli :Created
     */
    @ApiOperation(value = "批量删除购物车中的数据",notes = "批量删除购物车中的数据")
    @ApiImplicitParam(name = "shoppingCar",value = "数据实体",required = true,dataType = "shoppingCar")
    @PostMapping("/deleteShoppingCar")
    public ResultMessage deleteShoppingCar (@RequestBody ShoppingCarVo shoppingCarVo){
        return  forumShoppingCarService.deleteShoppingCarRecord(shoppingCarVo);
    }
    /*
     * 功能描述: 查询所有
     * @Param:
     * @Return:
     * @version<1>  2019/8/30  wangli :Created
     */
    @ApiOperation(value = "查询所有购物车信息",notes = "查询所有购物车信息")
    @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "Integer")
    @PostMapping("/queryShoppingCar")
    public ResultMessage queryShoppingCar (@RequestParam Integer userId){
        return  forumShoppingCarService.queryShoppingCar(userId);
    }
}
