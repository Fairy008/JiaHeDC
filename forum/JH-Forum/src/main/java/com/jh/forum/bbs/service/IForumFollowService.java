package com.jh.forum.bbs.service;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.service.IBaseService;
import com.jh.forum.bbs.entity.ForumFollow;
import com.jh.vo.ResultMessage;

/**
* @Description:    关注与点赞接口
* @Author:         mason
* @CreateDate:     2019/3/4 15:36
* @Version:        1.0
*/
public interface IForumFollowService extends IBaseService<ForumFollow, ForumFollow,Integer> {


    /**
     * 查询关注或点赞列表
     * @param
     * @return
     * @version <1> 2019/3/13 mason:Created.
     */
    ResultMessage findByPage(ForumFollow forumFollow);

    /**
     * 查询关注或点赞列表
     * @param
     * @return
     * @version <1> 2019/3/13 mason:Created.
     */
    PageInfo<ForumFollow> findByPageInfo(ForumFollow forumFollow);

    /**
     * 查询我的关注列表(带分页)
     * @param
     * @return 返回用户信息
     * @version <1> 2019/4/19 10:36 zhangshen:Created.
     */
    PageInfo<Object> findMyFollowPageInfo(ForumFollow forumFollow);

    /**
     * 查询我的关注列表(不带分页)
     * @param
     * @return
     * @version <1> 2019/4/19 10:36 zhangshen:Created.
     */
    ResultMessage findMyFollowList(ForumFollow forumFollow);


    /**
     * 查询当前登录人是否已经关注帖子作者（专家）
     * @param article_creator
     * @param currentAccountId
     * @return
     */
    ResultMessage findFolowByCurrentAccountId(Integer article_creator, Integer currentAccountId);

    /**
     * 查询我的关注列表(带分页)
     * @param
     * @return 返回用户信息
     * @version <1> 2019-05-08 lcw :Created.
     */
    PageInfo<ForumFollow> findMyFollowByPage(ForumFollow forumFollow);
}
