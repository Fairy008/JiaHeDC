package com.jh.show.agric.controller;

import org.apache.log4j.Logger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jh.biz.persist.IBaseService;
import com.jh.show.agric.entity.DictEntity;
import com.jh.show.agric.service.IDictTestService;
import com.jh.vo.ResultMessage;

/**
 * 加载作物接口
 * @version <1> 2017-12-06 cxw : Created.
 */
@Api(value = "Dict Service Interface",description="字典测试操作接口")
@RestController
@RequestMapping("/nolog/dict")
public class DictController {
    private static Logger logger = Logger.getLogger(DictController.class);

    @Autowired
    private IBaseService<DictEntity> service;

    // @Autowired
    // private IDictTestService service;

    /**
     * 根据区域加载作物
     * @param dictId : 字典ID
     * @return
     * @version <1> 2017-12-06 cxw : Created.
     */
    @ApiOperation(value="根据主键查询字典信息",notes="根据主键查询字典信息" )
    @ApiImplicitParam(name = "dictId",value = "字典ID",required = true, dataType = "Long" ,paramType="query",defaultValue="500")
    @RequestMapping(value="/queryDictById", method= RequestMethod.GET)
    public ResultMessage queryDictById(Long dictId)
    {
        DictEntity dict = service.queryById(dictId);
        ResultMessage msg = ResultMessage.success(dict);
        return msg;
    }
}
