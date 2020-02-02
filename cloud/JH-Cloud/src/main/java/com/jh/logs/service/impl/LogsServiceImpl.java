package com.jh.logs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.logs.entity.LogParam;
import com.jh.logs.entity.Logs;
import com.jh.logs.mapping.ILogsMapper;
import com.jh.logs.service.ILogsService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-01-25 lcw： Created.
 */

@Service
@Transactional
public class LogsServiceImpl extends BaseServiceImpl<LogParam,Logs,Integer> implements ILogsService {

    @Autowired
    private ILogsMapper logsMapper;

    @Override
    protected IBaseMapper<LogParam, Logs, Integer> getDao() {
        return logsMapper;
    }

    @Override
    public ResultMessage addLog(Logs logs) {
        if (logs == null || StringUtils.isEmpty(logs.getOperatorName()) || logs.getOperator() == null
                || StringUtils.isEmpty(logs.getOpContent())){
            return ResultMessage.fail();
        }
        logs.setOpTime(LocalDateTime.now());
        logsMapper.save(logs);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage addLog(HttpServletRequest request, Logs logs) {
        if (logs == null || StringUtils.isEmpty(logs.getOperatorName()) || logs.getOperator() == null
                || StringUtils.isEmpty(logs.getOpContent())){
            return ResultMessage.fail();
        }

        if (request != null){
            logs.setOpIp(request.getRemoteAddr());
        }
        logs.setOpTime(LocalDateTime.now());
        logsMapper.save(logs);
        return ResultMessage.success();
    }

    @Override
    public PageInfo<Logs> findByPage(LogParam logParam) {
        PageHelper.startPage(logParam.getPage(),logParam.getRows());
        List<Logs> list = logsMapper.queryByPage(logParam);
        return new PageInfo<Logs>(list);
    }


}
