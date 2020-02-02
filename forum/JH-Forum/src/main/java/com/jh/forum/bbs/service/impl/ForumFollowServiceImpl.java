package com.jh.forum.bbs.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.cltApp.feign.IPermPersonService;
import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.base.service.impl.BaseServiceImpl;
import com.jh.forum.bbs.Enum.FollowTypeEnum;
import com.jh.forum.bbs.entity.ForumFollow;
import com.jh.forum.bbs.mapping.IForumFollowMapper;
import com.jh.forum.bbs.service.IForumFollowService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @Description:    关注与收藏相关方法
* @Author:         mason
* @CreateDate:     2019/3/4 17:33
* @Version:        1.0
*/
@Service
@Transactional
public class ForumFollowServiceImpl extends BaseServiceImpl<ForumFollow, ForumFollow,Integer> implements IForumFollowService {

    @Autowired
    private IForumFollowMapper forumFollowMapper;

    @Autowired
    private IPermPersonService permPersonService;

    /**
     * @Description:    继承基础接口的增删改查功能
     * @Author:         mason
     * @CreateDate:     2019/3/4 16:05
     * @Version:        1.0
     */
    @Override
    protected IBaseMapper<ForumFollow, ForumFollow, Integer> getDao() {
        return forumFollowMapper;
    }


    /**
     * 查询关注或点赞列表
     * @param
     * @return
     * @version <1> 2019/3/13 mason:Created.
     */
    @Override
    public ResultMessage findByPage(ForumFollow forumFollow) {
        List<ForumFollow> list = forumFollowMapper.findByPage(forumFollow);
        return ResultMessage.success(list);
    }

    @Override
    public PageInfo<ForumFollow> findByPageInfo(ForumFollow forumFollow) {
        PageHelper.startPage(forumFollow.getPage(), forumFollow.getRows());
        List<ForumFollow> list = forumFollowMapper.findByPage(forumFollow);
        return new PageInfo<ForumFollow>(list);
    }

    /**
     * 查询我的关注列表(带分页)
     * @param
     * @return 返回用户信息
     * @version <1> 2019/4/19 10:36 zhangshen:Created.
     */
    @Override
    public PageInfo<Object> findMyFollowPageInfo(ForumFollow forumFollow) {
        PageHelper.startPage(forumFollow.getPage(), forumFollow.getRows());
        List<ForumFollow> list = forumFollowMapper.findByPage(forumFollow);
        List<Integer> accIds = new ArrayList<Integer>();
        for (ForumFollow f : list) {
            accIds.add(f.getArticleId());
        }
        //根据用户id查询用户信息
        ResultMessage result = permPersonService.findPermPersonListByAccIds(accIds);
        List<Object> objList = (List<Object>)result.getData();
        return new PageInfo(objList);
    }

    /**
     * 查询我的关注列表(不带分页)
     * @param
     * @return
     * @version <1> 2019/4/19 10:36 zhangshen:Created.
     */
    @Override
    public ResultMessage findMyFollowList(ForumFollow forumFollow) {
        List<ForumFollow> list = forumFollowMapper.findByPage(forumFollow);
        List<Integer> accIds = new ArrayList<Integer>();
        for (ForumFollow f : list) {
            accIds.add(f.getArticleId());
        }
        //根据用户id查询用户信息
        ResultMessage result = permPersonService.findPermPersonListByAccIds(accIds);
        List<Object> objList = (List<Object>)result.getData();
        return ResultMessage.success(objList);
    }

    @Override
    public ResultMessage findFolowByCurrentAccountId(Integer article_creator, Integer currentAccountId) {
        ForumFollow forumFollow = new ForumFollow();
        forumFollow.setArticleId(article_creator);
        forumFollow.setCreator(currentAccountId);

        ForumFollow follow = forumFollowMapper.findFolowByCurrentAccountId(forumFollow);

        return ResultMessage.success(follow);
    }


    /**
     * 查询我的关注列表(带分页)
     * @param
     * @return 返回用户信息
     */
    @Override
    public PageInfo<ForumFollow> findMyFollowByPage(ForumFollow forumFollow) {
        PageHelper.startPage(forumFollow.getPage(), forumFollow.getRows());
        forumFollow.setFollowType(FollowTypeEnum.FOLLOW_TYPE_FOCUS.getId());
        List<ForumFollow> list = forumFollowMapper.findByPage(forumFollow);
        List<Integer> accIds = new ArrayList<Integer>();

        if(list != null && list.size() > 0){
            for (ForumFollow f : list) {
                accIds.add(f.getArticleId());
            }
            //根据用户id查询用户信息
            ResultMessage result = permPersonService.findPermPersonListByAccIds(accIds);
            List<Map<String,Object>> objList = (List<Map<String,Object>>)result.getData();

            //将用户信息集成至ForumFollow中
            if(list != null && list.size() > 0){
                for(ForumFollow follow : list){
                    for1:for(Map<String,Object> voMap : objList){
                        Integer accountId = (Integer)voMap.get("accountId");
                        if(follow.getArticleId().equals(accountId)){
                            follow.setPersonMap(voMap);
                            break for1;
                        }
                    }
                }
            }
        }


        return new PageInfo(list);
    }



}
