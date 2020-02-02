package com.jh.forum.bbs.service.impl;

import com.jh.forum.bbs.entity.ForumCollectData;
import com.jh.forum.bbs.mapping.IForumCollectDataMapper;
import com.jh.forum.bbs.service.IForumCollectDataService;
import com.jh.forum.bbs.service.IForumDownloadDataService;
import com.jh.forum.bbs.vo.MyCollectVo;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ForumCollectDataServiceImpl implements IForumCollectDataService {

    @Autowired
    private IForumCollectDataMapper forumCollectDataMapper;

    @Autowired
    private IForumDownloadDataService forumDownloadDataService;


    @Override
    public ResultMessage collectData(ForumCollectData forumCollectData) {
        //收藏数据
        forumCollectDataMapper.collectData(forumCollectData);
        //收藏后收藏总数+1
        forumDownloadDataService.collectStatistc(forumCollectData.getDataId());
        return ResultMessage.success();
    }

    @Override
    public ResultMessage cancelData(ForumCollectData forumCollectData) {
        forumCollectDataMapper.cancelData(forumCollectData);
        //取消收藏后收藏总数-1
        forumDownloadDataService.cancelCollectStatistic(forumCollectData.getDataId());
        return ResultMessage.success();
    }

    @Override
    public ResultMessage selectCollectStatus(ForumCollectData forumCollectData) {
        int i = forumCollectDataMapper.selectCollectStatus(forumCollectData);
        return ResultMessage.success(i);
    }

    @Override
    public ResultMessage queryMyCollect(Integer userId) {
        List<MyCollectVo> list = forumCollectDataMapper.queryMyCollect(userId);
        list = setImageUrlForNewPath(list);
        return ResultMessage.success(list);
    }
    /*
     * 功能描述:将查询到的图片路径中的/ 替换成\
     * @Param:
     * @Return:
     * @version<1>  2019/9/5  wangli :Created
     */
    public List<MyCollectVo> setImageUrlForNewPath(List<MyCollectVo> list){
        if(CollectionUtils.isNotEmpty(list)){
            for(MyCollectVo myCollectVo : list){
                if(myCollectVo.getDataPath()!=null){
                    myCollectVo.setDataPath(CephUtils.getShowPath1(myCollectVo.getDataPath()));
                }
            }
        }
        return list;
    }
}
