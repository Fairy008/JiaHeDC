package com.jh.cltApp.mapping;

import com.jh.cltApp.entity.CltTaskInfo;
import com.jh.cltApp.vo.CltTaskInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ICltTaskInfoMapper {

    /**
     * 根据用户id, 查询任务列表, 包括自己创建的任务和参与的任务
     * @param 
     * @return 
     * @version <1> 2019/4/9 11:31 zhangshen:Created.
     */
    List<CltTaskInfo> findCltTaskInfoList(CltTaskInfo cltTaskInfo);

    /**
     * 根据用户id, 查询后台任务列表, 包括自己创建的任务和参与的任务
     * @param
     * @return
     * @version <1> 2019/4/9 11:31 lijie:Created.
     */
    List<CltTaskInfo> findCltTaskInfoListCms(CltTaskInfo cltTaskInfo);

    /**
     * 根据用户id, 查询我的任务列表, 包括自己创建的任务和参与的任务
     * @param
     * @return
     * @version <1> 2019/4/9 11:31 lijie:Created.
     */
    List<CltTaskInfo> findCltTaskInfoListBbs(CltTaskInfo cltTaskInfo);

    /**
     * 我分享的任务
     * @param 
     * @return 
     * @version <1> 2019/4/9 13:37 zhangshen:Created.
     */
    List<CltTaskInfo> findShareCltTaskInfoPageInfo(@Param("cltTaskInfo") CltTaskInfo cltTaskInfo, @Param("isPublishArr") List<Integer> isPublishArr);

    int deleteByPrimaryKey(Integer taskId);

    Integer insert(CltTaskInfo record);

    Integer insertSelective(CltTaskInfo record);

    CltTaskInfo selectByPrimaryKey(Integer taskId);

    int updateByPrimaryKeySelective(CltTaskInfo record);

    int auditTaskList(CltTaskInfo record);

    int updateByPrimaryKey(CltTaskInfo record);

    /**
     * 多条件查询任务列表
     * @param cltTaskInfo
     * @return List<CltTaskInfo>
     * @version <1> 2019/4/12 mason:Created.
     */
    List<CltTaskInfo> getListByCombination(CltTaskInfo cltTaskInfo);

    /**
     * 根据taskId查找任务详情
     * @param
     * @return
     * @version <1> 2019/4/15 15:56 zhangshen:Created.
     */
    CltTaskInfoVO getCltTaskInfoByTaskId(Integer taskId);
}