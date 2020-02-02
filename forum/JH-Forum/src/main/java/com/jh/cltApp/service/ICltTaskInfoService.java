package com.jh.cltApp.service;

import com.github.pagehelper.PageInfo;
import com.jh.cltApp.entity.CltTaskInfo;
import com.jh.cltApp.vo.CltTaskInfoParamsVO;
import com.jh.cltApp.vo.CltTaskItemVO;
import com.jh.vo.ResultMessage;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 任务信息接口
 * @version <1> 2019/4/8 17:25 zhangshen:Created.
 */
public interface ICltTaskInfoService {

    /**
     * 查询我的任务列表(全部, 进行中, 已完成)(带分页)
     * @param
     * @return
     * @version <1> 2019/4/8 17:27 zhangshen:Created.
     */
    PageInfo<CltTaskInfo> findCltTaskInfoPageInfo(CltTaskInfo cltTaskInfo);

    /**
     * 查询后台任务列表(全部, 进行中, 已完成)(带分页)
     * @param
     * @return
     * @version <1> 2019/4/8 17:27 lijie:Created.
     */
    PageInfo<CltTaskInfo> findCltTaskInfoPageInfoCms(CltTaskInfo cltTaskInfo);

    /**
     * 查询我的任务列表(全部, 进行中, 已完成)(带分页)
     * @param
     * @return
     * @version <1> 2019/4/8 17:27 lijie:Created.
     */
    PageInfo<CltTaskInfo> findCltTaskInfoPageInfoBbs(CltTaskInfo cltTaskInfo);

    /**
     * 查询后台任务列表(全部, 进行中, 已完成)(带分页)
     * @param
     * @return
     * @version <1> 2019/4/8 17:27 lijie:Created.
     */
    ResultMessage findCltTaskInfoListInfoCms(CltTaskInfo cltTaskInfo);

    /**
     * 查询我的任务列表(全部, 进行中, 已完成)(不带分页)
     * @param
     * @return
     * @version <1> 2019/4/8 17:27 zhangshen:Created.
     */
    ResultMessage findCltTaskInfoList(CltTaskInfo cltTaskInfo);

    /**
     * 新建任务
     * @param
     * @return 
     * @version <1> 2019/4/8 18:45 zhangshen:Created.
     */
    ResultMessage createTaskInfo(CltTaskInfoParamsVO cltTaskInfoParamsVO);

    /**
     * 修改状态(任务状态[进行中、已完成], 发布状态[未发布, 待审核])
     * @param
     * @return 
     * @version <1> 2019/4/8 19:28 zhangshen:Created.
     */
    ResultMessage updateTaskStatusByTaskId(CltTaskInfo cltTaskInfo);

    /**
     * 批量修改状态(任务状态[进行中、已完成], 发布状态[未发布, 待审核])
     * @param
     * @return
     * @version <1> 2019/4/8 19:28 zhangshen:Created.
     */
    ResultMessage auditTaskList(CltTaskInfo cltTaskInfo);

    /**
     * 修改任务
     * @param
     * @return
     * @version <1> 2019/4/10 16:29 zhangshen:Created.
     */
    ResultMessage updateTaskInfoByTaskId(CltTaskInfoParamsVO cltTaskInfoParamsVO);

    /**
     * 删除任务
     * @param
     * @return 
     * @version <1> 2019/4/8 19:32 zhangshen:Created.
     */
    ResultMessage deleteTaskInfoByTaskId(Integer taskId);

    /**
     * 批量删除任务
     * @param
     * @return
     * @version <1> 2019/4/8 19:32 zhangshen:Created.
     */
    ResultMessage deleteTaskInfoByTaskIds(List<Integer> taskIds);

    /**
     * 我分享的任务
     * @param 
     * @return 
     * @version <1> 2019/4/8 20:02 zhangshen:Created.
     */
    PageInfo<CltTaskInfo> findShareCltTaskInfoPageInfo(CltTaskInfo cltTaskInfo);

    /**
     * 我参与的任务
     * @param
     * @return
     * @version <1> 2019/4/8 20:02 zhangshen:Created.
     */
    PageInfo<CltTaskInfo> findJoinCltTaskInfoPageInfo(CltTaskInfo cltTaskInfo);

    /**
     * 多条件查询任务列表
     * @param cltTaskInfo
     * @return ageInfo<CltTaskInfo>
     * @version <1> 2019/4/12 mason:Created.
     */
    PageInfo<CltTaskInfo> getListByCombination(CltTaskInfo cltTaskInfo);

    /**
     * 根据taskId查找任务详情
     * @param 
     * @return 
     * @version <1> 2019/4/15 15:56 zhangshen:Created.
     */
    ResultMessage getCltTaskInfoByTaskId(CltTaskInfo cltTaskInfo);

    /**
     * 首页轮播
     * @param collectionTaskInfo 首页轮播
     * @return
     * @version <1> 2019-03-18 lijie： Created.
     */
    ResultMessage updateIndexShow(HttpServletRequest request);

    /**
     * 下载媒体文件
     * @param taskItemJson 子任务信息
     * @return
     * @version <1> 2019-03-18 lijie： Created.
     */
    byte[] downloadMediaByTaskInfoId(HttpServletRequest request,List<CltTaskItemVO> cltTaskItemList) throws IOException;
}
