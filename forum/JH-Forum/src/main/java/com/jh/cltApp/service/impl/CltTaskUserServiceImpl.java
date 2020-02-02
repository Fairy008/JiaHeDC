package com.jh.cltApp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.cltApp.entity.CltTaskUser;
import com.jh.cltApp.mapping.ICltTaskUserMapper;
import com.jh.cltApp.service.ICltTaskUserService;
import com.jh.vo.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务参与人实现类
 * @version <1> 2019/4/9 10:02 zhangshen:Created.
 */
@Service
@Transactional
public class CltTaskUserServiceImpl implements ICltTaskUserService {

    @Autowired
    private ICltTaskUserMapper cltTaskUserMapper;

    private static Logger log = LoggerFactory.getLogger(CltTaskUserServiceImpl.class);

    @Override
    public PageInfo<CltTaskUser> findCltTaskUserPageInfo(CltTaskUser cltTaskUser) {
        PageHelper.startPage(cltTaskUser.getPage(), cltTaskUser.getRows());
        List<CltTaskUser> cltTaskUserList = cltTaskUserMapper.findCltTaskUserList(cltTaskUser);
        return new PageInfo<CltTaskUser>(cltTaskUserList);
    }

    @Override
    public ResultMessage findCltTaskUserList(CltTaskUser cltTaskUser) {
        List<CltTaskUser> cltTaskUserList = cltTaskUserMapper.findCltTaskUserList(cltTaskUser);
        return ResultMessage.success(cltTaskUserList);
    }

    @Override
    public ResultMessage createCltTaskUser(CltTaskUser cltTaskUser) {
        cltTaskUserMapper.insertSelective(cltTaskUser);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage deleteCltTaskUserById(Integer id) {
        cltTaskUserMapper.deleteByPrimaryKey(id);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage deleteCltTaskUserByTaskId(Integer taskId) {
        cltTaskUserMapper.deleteCltTaskUserByTaskId(taskId);
        return ResultMessage.success();
    }
}
