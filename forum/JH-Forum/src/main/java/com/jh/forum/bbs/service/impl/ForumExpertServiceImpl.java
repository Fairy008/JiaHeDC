package com.jh.forum.bbs.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.biz.feign.IPersonService;
import com.jh.cltApp.feign.IPermPersonService;
import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.base.service.impl.BaseServiceImpl;
import com.jh.forum.bbs.constant.ForumConstant;
import com.jh.forum.bbs.entity.ForumFollow;
import com.jh.forum.bbs.mapping.IForumExpertMapper;
import com.jh.forum.bbs.service.IForumExpertService;
import com.jh.forum.bbs.util.CommonUtils;
import com.jh.forum.bbs.vo.ExpertVO;
import com.jh.util.CollectionUtil;
import com.jh.util.cache.IdTransformUtils;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
* @Description:    专家相关方法
* @Author:         mason
* @CreateDate:     2019/3/4 17:25
* @Version:        1.0
*/
@Service
@Transactional
public class ForumExpertServiceImpl extends BaseServiceImpl<ExpertVO, ExpertVO,Integer> implements IForumExpertService {

    @Autowired
    private IForumExpertMapper forumExpertMapper;

    @Autowired
    private IPersonService personService;

    @Autowired
    private IPermPersonService permPersonService;

    private static Logger log= LoggerFactory.getLogger(ForumExpertServiceImpl.class);

    /**
     * @Description:    继承基础接口的增删改查功能
     * @Author:         mason
     * @CreateDate:     2019/3/4 16:05
     * @Version:        1.0
     */
    @Override
    protected IBaseMapper<ExpertVO, ExpertVO, Integer> getDao() {
        return forumExpertMapper;
    }

    /**
     * 分页查询专家列表
     * @param forumExpert
     * @return PageInfo<ExpertVO>
     * @version <1> 2019/3/5 mason:Created.
     */
    @Override
    public PageInfo<ExpertVO> findExpertList(ExpertVO forumExpert) {
        PageHelper.startPage(forumExpert.getPage(), forumExpert.getRows());
        List<ExpertVO> list = forumExpertMapper.findByPage(forumExpert);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<ExpertVO>(list);
    }

    /**
     * 查询随机5名专家
     * @param
     * @return
     * @version <1> 2019/3/6 mason:Created.
     */
    @Override
    public ResultMessage findRandomExpert() {
        List<ExpertVO> list = forumExpertMapper.findByPage(new ExpertVO());
        if (CollectionUtil.isNotEmpty(list) && list.size() > 0){
            list = CommonUtils.getSubStringByRadom(list, ForumConstant.CHANGE_BATCHES_EXPERT_COUNT);
        }
        List<Integer> accIds = new ArrayList<Integer>();
        for (ExpertVO f : list) {
            accIds.add(f.getAccountId());
        }
        //根据用户id查询用户信息
        ResultMessage result = permPersonService.findPermPersonListByAccIds(accIds);
        List<Map<String,Object>> personList = (List<Map<String,Object>>)result.getData();
        //将查询到的person信息中的头像url信息替换专家信息中的头像url
        for (ExpertVO expert : list){
            for (Map<String,Object> person : personList){
                if (expert.getAccountId().equals(person.get("accountId"))
                        && !"".equals(person.get("photoUrl"))){
                    expert.setPhotoUrl((String)person.get("photoUrl"));
                }
            }
        }

        return ResultMessage.success(list);
    }

    /**
     * 新增专家
     * @param
     * @return
     * @version <1> 2019/3/6 mason:Created.
     */
    @Override
    public ResultMessage saveForumExpert(HttpServletRequest request,ExpertVO expertVO){
        //1.验证专家是否已存在
        ExpertVO expert =new ExpertVO();
        expert.setPhone(expertVO.getPhone());
        List<ExpertVO> expertList = forumExpertMapper.findByPage(expert);
        if(CollectionUtil.isNotEmpty(expertList)){
            return ResultMessage.fail("专家手机号已存在，请检查");
        }
        //2.查看专家是否已注册
        Integer accountId=null;
        ResultMessage result=personService.getAccountIdByPhone(expertVO.getPhone());
        if(result.isFlag()){
            accountId=(Integer)result.getData();
            Map<String,Object>param=new HashMap<String,Object>();
            param.put("accountId",accountId);
            param.put("accountName",expertVO.getPhone());
            param.put("personName",expertVO.getExpertName());
            //专家昵称默认是名称
            param.put("nickName",expertVO.getExpertName());
            personService.editUser(param);
        }
        if(accountId==null){//新增用户
            Map<String,Object>param=new HashMap<String,Object>();
            param.put("accountName",expertVO.getPhone());
            param.put("personName",expertVO.getExpertName());
            param.put("nickName",expertVO.getExpertName());
            param.put("company",expertVO.getCompany());
            param.put("sex",expertVO.getSex());
            param.put("mobile",expertVO.getPhone());
            param.put("dataStatus","1");
            param.put("creator",expertVO.getCreator());
            param.put("remark", expertVO.getIntroduction());
            ResultMessage message=personService.addUser(param);
            if(message.isFlag()){
                accountId=(Integer)message.getData();
            }
        }
        expertVO.setAccountId(accountId);
        //3.设置头像
        String photoUrl = CephUtils.uploadImage(request,expertVO.getExpertId());
        expertVO.setPhotoUrl(photoUrl);
        //4.新增专家
        forumExpertMapper.save(expertVO);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage updateForumExpert(HttpServletRequest request, ExpertVO expertVO) {
        //1、获取头像url
        String photoUrl = CephUtils.uploadImage(request,expertVO.getExpertId());
        expertVO.setPhotoUrl(photoUrl);
        //2、更新专家信息
        update(expertVO);
        //3.更新用户信息
        Map<String,Object>param=new HashMap<String,Object>();
        param.put("accountId",expertVO.getAccountId());
        param.put("personName",expertVO.getExpertName());
        param.put("nickName",expertVO.getExpertName());
        param.put("company",expertVO.getCompany());
        param.put("sex",expertVO.getSex());
        param.put("remark", expertVO.getIntroduction());
        personService.editUser(param);

        return ResultMessage.success();
    }

    @Override
    public ResultMessage updateByAccount(ExpertVO expertVO) {
        forumExpertMapper.updateByAccount(expertVO);
        return ResultMessage.success();
    }

    /**
     * 根据accountId查询专家详情
     * @param accountId
     * @return
     * @version <1> 2019/3/18 mason:Created.
     */
    @Override
    public ResultMessage getByAccountId(Integer accountId) {
        ExpertVO expertVO = forumExpertMapper.getByAccountId(accountId);
        return ResultMessage.success(expertVO);
    }
}
