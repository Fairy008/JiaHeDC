package com.jh.manage.alarm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.manage.alarm.entity.DataAlarm;
import com.jh.manage.alarm.mapping.IDataAlarmMapper;
import com.jh.manage.alarm.model.AlarmParam;
import com.jh.manage.alarm.service.IDataAlarmService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description: 告警消息服务
 * 1.新增告警消息
 * 2.告警消息分页查询
 * 3.告警消息条数查询
 * @version <1> 2018-01-31 lcw： Created.
 */
@Service
@Transactional
public class DataAlarmServiceImpl extends BaseServiceImpl<AlarmParam, DataAlarm, Integer> implements IDataAlarmService {

    @Autowired
    private IDataAlarmMapper dataAlarmMapper;

    @Override
    protected IBaseMapper<AlarmParam, DataAlarm, Integer> getDao() {
        return dataAlarmMapper;
    }

    /**
     * 获取当前登录人告警条数
     * @param accountId
     * @return
     * @version <1> 2018-02-05 lcw： Created.
     */
    @Override
    public ResultMessage getAlarmNum(Integer accountId) {
        ResultMessage resultMessage = ResultMessage.success();
        Integer num = dataAlarmMapper.getAlarmNum(accountId);
        resultMessage.setData(num == null ? 0 : num );
        return resultMessage;
    }

    /**
     * 保存信息
     *  根据业务ID、业务类型、是否未读三个字段校验该数据是否存在，若存在，则直接更新该条数据，若不存在，则新增
     * @param alarm
     *  businessType: 业务类型
     *  tableName : 业务表名
     *  businessId: 业务ID
     *  alarmTime: 告警时间
     *  reason: 告警原因
     *  creator: 创建人
     *  creatorName:创建人
     * @version<1> 2018-03-08 lcw : Created.
     * @version<1> 2018-04-10 xzg : update.  告警信息只添加
     */
    @Override
    public void saveAlarm(DataAlarm alarm) {
//        DataAlarm dataAlarm = dataAlarmMapper.findAlarm(alarm);
//        if(dataAlarm != null){ //若存在相同数据，则修改
//            alarm.setAlarmId(dataAlarm.getAlarmId());
//            dataAlarmMapper.update(alarm);
//        }else{ //不存在记录，则新增
            dataAlarmMapper.save(alarm);
//        }
    }


    /**
     * 告警消息列表分页查询
     * @param alarmParam
     * @return
     * @version<1> 2018-03-15 lcw : Created.
     */
    @Override
    public PageInfo<DataAlarm> findByPage(AlarmParam alarmParam) {
        PageHelper.startPage(alarmParam.getPage(), alarmParam.getRows());
        List<DataAlarm> list = dataAlarmMapper.findByPage(alarmParam);
        return new PageInfo<DataAlarm>(list);
    }
    /**
     * 告警消息状态更新为已读，已读状态为0
     * @param alarmId
     * @return
     * @version<1> 2018-03-15 lcw : Created.
     */
    @Override
    public ResultMessage read(Integer alarmId) {
        if(alarmId== null){
            return ResultMessage.fail();
        }
        dataAlarmMapper.updateDataStatus(alarmId);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage findById(Integer alarmId) {
        DataAlarm alarm=new DataAlarm();
        alarm.setAlarmId(alarmId);
        DataAlarm dataAlarm = dataAlarmMapper.findAlarm(alarm);
        //更新状态
        dataAlarmMapper.updateDataStatus(alarmId);
        return ResultMessage.success(dataAlarm);
    }
}
