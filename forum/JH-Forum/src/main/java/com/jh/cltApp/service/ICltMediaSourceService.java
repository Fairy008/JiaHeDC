package com.jh.cltApp.service;

import com.jh.cltApp.entity.CltMediaSource;
import com.jh.vo.ResultMessage;

/**
 * 媒体资源信息接口
 * @version <1> 2019/4/8 19:54 zhangshen:Created.
 */
public interface ICltMediaSourceService {

    /**
     * 根据采集点id查询媒体资源信息
     * @param
     * @return
     * @version <1> 2019/4/8 18:47 zhangshen:Created.
     */
    ResultMessage findCltMediaSourceListByItemId(Integer itemId);

    /**
     * 新增媒体资源
     * @param 
     * @return 
     * @version <1> 2019/4/8 19:47 zhangshen:Created.
     */
    ResultMessage cerateMediaSource(CltMediaSource cltMediaSource);

    /**
     * 根据任务id删除媒体资源
     * @param
     * @return 
     * @version <1> 2019/4/8 19:49 zhangshen:Created.
     */
    ResultMessage deleteMediaSourceByTaskId(Integer taskId);

    /**
     * 根据itemId删除媒体资源
     * @param
     * @return
     * @version <1> 2019/4/8 19:49 zhangshen:Created.
     */
    ResultMessage deleteMediaSourceByItemId(Integer itemId);

    /**
     * 根据id删除媒体资源
     * @param
     * @return
     * @version <1> 2019/4/8 19:50 zhangshen:Created.
     */
    ResultMessage deleteMediaSourceById(Integer id);
}
