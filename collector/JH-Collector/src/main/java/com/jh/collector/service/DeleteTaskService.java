package com.jh.collector.service;

import com.jh.vo.ResultMessage;

/**
 * @author shuxinjie
 * @Description TODO
 * @Date 2019/7/30 13:57
 **/
public interface DeleteTaskService {

    /**
     * 任务删除
     * @param taskId
     * @return
     */
    public ResultMessage delete(Integer taskId);

}
