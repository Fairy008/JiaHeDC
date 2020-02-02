package com.jh.manage.alarm.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.manage.alarm.entity.DataAlarm;
import com.jh.manage.alarm.model.AlarmParam;
import com.jh.manage.alarm.service.IDataAlarmService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * description:告警信息控制层
 * 1.检索告警信息条数
 * 2.检索告警信息列表
 * @version <1> 2018-02-05 lcw： Created.
 */
@RestController
@RequestMapping("/alarm")
public class DataAlarmController extends BaseController {

    @Autowired
    private IDataAlarmService alarmService;

    /**
     * 获取当前登录人的告警信息条数
     * @return ResultMessage
     * @version <1> 2018-02-05 lcw： Created.
     */
    @ApiOperation(value = "获取当前登录人的告警信息条数",notes = "获取当前登录人的告警信息条数")
    @PostMapping("getAlarmNum")
    public ResultMessage getAlarmNum(){


        return alarmService.getAlarmNum(getCurrentAccountId());
    }

    @ApiOperation(value = "获取告警信息列表", notes = "获取告警信息列表")
    @ApiImplicitParam(name = "alarmParam",value = "告警参数",required = true, dataType = "AlarmParam")
    @PostMapping("findByPage")
    public PageInfo<DataAlarm> findByPage(AlarmParam alarmParam, HttpServletRequest request){
//        PermAccount account = getCurrentPermAccount(request);
//        if(account == null){
//            return null;
//        }
        alarmParam.setCreator(getCurrentAccountId());
        PageInfo<DataAlarm> pages = alarmService.findByPage(alarmParam);

        return pages;
    }



    @ApiOperation(value = "更新消息阅读状态", notes = "更新消息阅读状态")
    @ApiImplicitParam(name = "alarmId",value = "告警ID",required = true, dataType = "Integer")
    @PostMapping("read")
    public ResultMessage read(Integer alarmId){

        return alarmService.read(alarmId);

    }

    /**
     * @description: 根据ID查询实体
     * @param request
     * @param alarmId
     * @return
     ** @version <1> 2018-04-02 wl : created.
     */
    @ApiOperation(value = "告警信息任务查询",notes = "按告警信息id查询")
    @ApiImplicitParam(name = "alarmId",value = "告警信息主键",required = true,paramType = "query", dataType = "Integer")
    @PostMapping("/findById")
    public ResultMessage findById(HttpServletRequest request, @RequestParam Integer alarmId){
        return alarmService.findById(alarmId);
    }


}
