package com.jh.forum.bbs.service;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.service.IBaseService;
import com.jh.forum.bbs.vo.ExpertVO;
import com.jh.vo.ResultMessage;

import javax.servlet.http.HttpServletRequest;

/**
* @Description:    专家接口
* @Author:         mason
* @CreateDate:     2019/3/4 15:35
* @Version:        1.0
*/
public interface IForumExpertService extends IBaseService<ExpertVO, ExpertVO,Integer> {


    /**
     * 分页查询专家列表
     * @param expertVO
     * @return PageInfo<ForumExpert>
     * @version <1> 2019/3/5 mason:Created.
     */
    PageInfo<ExpertVO> findExpertList(ExpertVO expertVO);

    /**
     * 查询随机5名专家
     * @param
     * @return
     * @version <1> 2019/3/6 mason:Created.
     */
    ResultMessage findRandomExpert();

    /**
     * 保存专家
     * @param expertVO
     * @return ResultMessage
     * @version <1> 2019/3/5 lijie:Created.
     */
    ResultMessage saveForumExpert(HttpServletRequest request,ExpertVO expertVO);


    /**
     * 修改专家
     * @param expertVO
     * @return ResultMessage
     * @version <1> 2019/3/5 lijie:Created.
     */
    ResultMessage updateForumExpert(HttpServletRequest request,ExpertVO expertVO);
    /**
     * 修改专家姓名  工作单位  简介  性别
     * @param expertVO
     * @return ResultMessage
     * @version <1> 2019/3/16 wl:Created.
     */
    ResultMessage updateByAccount(ExpertVO expertVO);

    /**
     * 根据accountId查询专家详情
     * @param
     * @return
     * @version <1> 2019/3/18 mason:Created.
     */
    ResultMessage getByAccountId(Integer accountId);

}
