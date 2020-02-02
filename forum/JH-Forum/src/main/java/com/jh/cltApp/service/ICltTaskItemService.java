package com.jh.cltApp.service;

import com.github.pagehelper.PageInfo;
import com.jh.cltApp.entity.CltMediaSource;
import com.jh.cltApp.entity.CltTaskItem;
import com.jh.cltApp.vo.CltTaskItemVO;
import com.jh.vo.ResultMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * 任务采集点数据接口
 * @version <1> 2019/4/8 17:25 zhangshen:Created.
 */
public interface ICltTaskItemService {

    /**
     * 根据采集点数据id查询任务明细数据
     * @param 
     * @return 
     * @version <1> 2019/4/9 15:09 zhangshen:Created.
     */
    ResultMessage findTaskItemByItemId(CltTaskItem cltTaskItem);

    /**
     * 根据任务id查询任务明细数据列表(带分页)
     * @param
     * @return
     * @version <1> 2019/4/8 17:33 zhangshen:Created.
     */
    PageInfo<CltTaskItemVO> findTaskItemPageInfo(CltTaskItem cltTaskItem);

    PageInfo<CltTaskItemVO> findAllTaskItemPageInfo(CltTaskItem cltTaskItem);

    /**
     * 根据任务id查询任务明细数据列表(不带分页)
     * @param
     * @return
     * @version <1> 2019/4/8 17:33 zhangshen:Created.
     */
    ResultMessage findTaskItemList(CltTaskItem cltTaskItem);

    /**
     * 根据任务id查询任务明细数据列表(不带分页),无需登录
     * @param
     * @return
     * @version <1> 2019/4/8 17:33 zhangshen:Created.
     */
    ResultMessage findAllTaskItemList(CltTaskItem cltTaskItem);

    /**
     * 新建采集点数据
     * @param 
     * @return 
     * @version <1> 2019/4/8 19:36 zhangshen:Created.
     */
    ResultMessage createTaskItem(CltTaskItemVO cltTaskItemVO);

    /**
     * 删除采集点数据
     * @param
     * @return
     * @version <1> 2019/4/8 19:36 zhangshen:Created.
     */
    ResultMessage deleteTaskItemByItemId(Integer itemId);

    /**
     * 修改采集点数据
     * @param
     * @return 
     * @version <1> 2019/4/8 19:38 zhangshen:Created.
     */
    ResultMessage updateTaskItemByItemId(CltTaskItemVO cltTaskItem);
    
    /**
     * 根据任务id和用户查看自己的采集点数据列表
     * @param 
     * @return 
     * @version <1> 2019/4/17 15:26 zhangshen:Created.
     */
    ResultMessage findTaskItemListSelf(CltTaskItem cltTaskItem);

    /**
     * mui上传多个文件
     * 1.根据itemId删除此采集点下的所有媒体文件
     * 2.上传文件
     * 3.插入媒体文件信息到媒体表
     * 4.返回是否插入成功
     * @param
     * @return
     * @version <1> 2019/4/20 10:03 zhangshen:Created.
     */
    ResultMessage muiUploadFile(CltMediaSource cltMediaSource, String phone, Integer taskId, Integer itemId, HttpServletRequest request);
}
