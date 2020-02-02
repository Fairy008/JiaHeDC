package com.jh.manage.alarm.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.manage.alarm.entity.DataAlarm;
import com.jh.manage.alarm.model.AlarmParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IDataAlarmMapper extends IBaseMapper<AlarmParam, DataAlarm, Integer> {
    /**
     * 获取当前登录人告警条数
     * @param accountId
     * @return
     * @version <1> 2018-02-05 lcw： Created.
     */
    Integer getAlarmNum(Integer accountId);

    /**
     *查询告警消息
     * @param alarm
     *   业务类型
     *   业务ID
     *   未读
     * @return
     * @version<1> 2018-03-15 lcw :Created.
     */
    DataAlarm findAlarm(DataAlarm alarm);

    /**
     * 告警消息状态更新为已读
     * @param alarmId
     * @return
     * @version<1> 2018-03-15 lcw : Created.
     */
    void updateDataStatus(Integer alarmId);
}