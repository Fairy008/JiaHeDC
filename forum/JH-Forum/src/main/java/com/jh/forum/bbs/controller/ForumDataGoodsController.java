package com.jh.forum.bbs.controller;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.service.IForumDataGoodsService;
import com.jh.forum.bbs.vo.DataGoodsVo;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @Description 数据订单
 * @Version <1> 2019-08-14 sxj:Create.
 **/
@Api(value = "订单数据接口",description = "订单数据接口")
@RestController
@RequestMapping("/dataGoods")
public class ForumDataGoodsController extends BaseController {

    @Autowired
    private IForumDataGoodsService forumDataGoodsService;

    /**
     * 新增数据订单
     */
    @ApiOperation(value = "新增数据订单",notes = "新增数据订单")
    @ApiImplicitParam(name = "DataGoodsVo",value = "数据订单实体",required = true,dataType = "DataGoodsVo")
    @PostMapping("/saveDataGoods")
    public ResultMessage saveDataGoods(@RequestBody DataGoodsVo dataGoodsVo){
        dataGoodsVo.setCreator(getCurrentAccountId());
        dataGoodsVo.setCreatorName(getCurrentNickName());
        dataGoodsVo.setCreateTime(LocalDateTime.now());
        dataGoodsVo.setModifier(getCurrentAccountId());
        dataGoodsVo.setModifierName(getCurrentNickName());
        return forumDataGoodsService.saveDataGoods(dataGoodsVo);
    }


    /**
     * 删除订单
     */
    @ApiOperation(value = "删除订单",notes = "删除订单")
    @ApiImplicitParam(name = "DataGoodsVo",value = "数据订单实体",required = true,dataType = "DataGoodsVo")
    @PostMapping("/deleteDataGoods")
    public ResultMessage deleteDataGoods(@RequestBody DataGoodsVo dataGoodsVo){
        return forumDataGoodsService.deleteDataGoods(dataGoodsVo);
    }

    /**
     * 查询订单
     */
    @ApiOperation(value = "查询订单",notes = "查询订单")
    @ApiImplicitParam(name = "id",value = "数据订单id",required = true,paramType="query",dataType = "Integer")
    @GetMapping("/queryDataDoodsList")
    public ResultMessage queryDataDoodsList(@RequestParam Integer id){
        return forumDataGoodsService.queryDataDoodsList(id);
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "订单数据分页查询",notes = "订单数据分页查询")
    @ApiImplicitParam(name = "DataGoodsVo",value = "订单数据实体",required = true,paramType = "query",dataType = "DataGoodsVo")
    @PostMapping("/getListByDataGoods")
    public PageInfo<DataGoodsVo> getListByDataGoods(DataGoodsVo dataGoodsVo){
        return forumDataGoodsService.getListByOrderGoods(dataGoodsVo);
    }

}

