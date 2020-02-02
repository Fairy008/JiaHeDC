package com.jh.cltApp.service;

import com.github.pagehelper.PageInfo;
import com.jh.cltApp.entity.CltTaskUser;
import com.jh.vo.ResultMessage;

/**
 * 参与人接口
 * @version <1> 2019/4/8 19:54 zhangshen:Created.
 */
public interface ICltTaskUserService {
    
    /**
     * 查询任务参与人(带分页)
     * @param
     * @return 
     * @version <1> 2019/4/8 18:55 zhangshen:Created.
     */
    PageInfo<CltTaskUser> findCltTaskUserPageInfo(CltTaskUser cltTaskUser);

    /**
     * 查询任务参与人(不带分页)
     * @param
     * @return
     * @version <1> 2019/4/8 18:55 zhangshen:Created.
     */
    ResultMessage findCltTaskUserList(CltTaskUser cltTaskUser);

    /**
     * 新增参与人
     * @param 
     * @return 
     * @version <1> 2019/4/8 19:40 zhangshen:Created.
     */
    ResultMessage createCltTaskUser(CltTaskUser cltTaskUser);

    /**
     * 根据id删除参与人
     * @param
     * @return
     * @version <1> 2019/4/8 19:42 zhangshen:Created.
     */
    ResultMessage deleteCltTaskUserById(Integer id);

    /**
     * 根据任务id删除参与人
     * @param 
     * @return 
     * @version <1> 2019/4/8 19:42 zhangshen:Created.
     */
    ResultMessage deleteCltTaskUserByTaskId(Integer taskId);
}
