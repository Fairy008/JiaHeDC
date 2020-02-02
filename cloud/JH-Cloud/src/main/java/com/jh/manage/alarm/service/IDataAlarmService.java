package com.jh.manage.alarm.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseService;
import com.jh.manage.alarm.entity.DataAlarm;
import com.jh.manage.alarm.model.AlarmParam;
import com.jh.manage.order.entity.DataOrder;
import com.jh.vo.ResultMessage;

/**
 * description:告警服务
 *  1.创建告警信息
 *  2.查询告警列表
 *  3.获取告警信息条数
 * @version <1> 2018-01-31 lcw： Created.
 */
public interface IDataAlarmService extends IBaseService<AlarmParam, DataAlarm, Integer> {


    /**
     * 获取告警信息条数
     * @param accountId
     * @return
     * @version <1> 2018-02-05 lcw： Created.
     */
    ResultMessage getAlarmNum(Integer accountId);

    /**
     * 插入告警消息
     * @param alarm
     *  businessType: 业务类型1：下载，2入库，3归档
     *  tableName : 业务表名
     *  businessId: 业务ID
     *  alarmTime: 告警时间
     *  reason: 告警原因
     *  creator: 创建人
     *  creatorName:创建人
     *  createTime: 创建时间
     * @version<1> 2018-03-08 lcw : Created.
     */
    void saveAlarm(DataAlarm alarm);


    /**
     * 告警消息分页查询
     * @param alarmParam
     * @return
     * @version<1> 2018-03-15 lcw : Created.
     */
    PageInfo<DataAlarm> findByPage(AlarmParam alarmParam);

    /**
     * 告警消息状态更新为已读
     * @param alarmId
     * @return
     * @version<1> 2018-03-15 lcw : Created.
     */
    ResultMessage read(Integer alarmId);

    /**
     * @description: 根据id查询详细信息
     * @param alarmId 数据归档主键
     * @return
     * @version<1> 2018-04-02 wl: Created.
     */
    ResultMessage findById(Integer alarmId);
}
