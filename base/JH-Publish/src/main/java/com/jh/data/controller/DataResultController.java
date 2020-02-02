package com.jh.data.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.data.entity.DataResult;
import com.jh.data.model.DataResultParam;
import com.jh.data.service.IDataResultService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 * 1.成果数据Controller
 *
 * @version <1> 2018-05-02 15:47 zhangshen: Created.
 */
@RestController
@RequestMapping("/dataResult")
public class DataResultController extends BaseController {

    @Autowired
    private IDataResultService dateResultService;

    @ApiOperation(value = "获取成果数据转换列表", notes = "获取成果数据转换列表")
    @ApiImplicitParam(name = "dataResultParam",value = "成果数据转换参数",required = false, dataType = "DataResultParam")
    @PostMapping("findDataResultChangeByPage")
    public PageInfo<DataResult> findDateResultChangeByPage(DataResultParam dataResultParam, HttpServletRequest request){
        //alarmParam.setCreator(account.getAccountId());
        PageInfo<DataResult> pages = dateResultService.findByPage(dataResultParam);

        return pages;
    }
}
