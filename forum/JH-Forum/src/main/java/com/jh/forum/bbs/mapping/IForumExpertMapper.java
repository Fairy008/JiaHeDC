package com.jh.forum.bbs.mapping;

import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.bbs.vo.ExpertVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 专家查询接口
 */
@Mapper
public interface IForumExpertMapper extends IBaseMapper<ExpertVO, ExpertVO,Integer> {

    int updateByAccount(ExpertVO expertVO);

    /**
     * 根据accountId查询专家详情
     * @param
     * @return
     * @version <1> 2019/3/18 mason:Created.
     */
    ExpertVO getByAccountId(Integer accountId);


    
}