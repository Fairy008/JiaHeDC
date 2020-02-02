package com.jh.collection.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.jh.collection.entity.CollectionMediaSource;
import com.jh.collection.entity.CollectionTaskItem;
import com.jh.collection.entity.TaskItemFeild;
import com.jh.collection.entity.vo.CollectionFieldModelVo;
import com.jh.collection.entity.vo.CollectionTaskItemVo;
import com.jh.collection.entity.vo.CoordingatesVo;
import com.jh.collection.mapping.*;
import com.jh.collection.service.ICollectionTaskItemService;
import com.jh.util.JsonUtils;
import com.jh.vo.ResultMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jh.collection.enums.MediaTypeEnum.*;

/**
 * 采集子任务接口
 * @version <1> 2018-12-04: Created.
 */
@Service
public class CollectionTaskItemServiceImpl implements ICollectionTaskItemService {

    private static final Logger LOG = LoggerFactory.getLogger(CollectionTaskItemServiceImpl.class);

    @Autowired
    private CollectionTaskItemMapper collectionTaskItemMapper;
    @Autowired
    private TaskItemFeildMapper taskItemFeildMapper;
    @Autowired
    private CollectionTaskInfoMapper collectionTaskInfoMapper;
    @Autowired
    private CollectionTemplateMapper collectionTemplateMapper;
    @Autowired
    private CollectionMediaSourceMapper collectionMediaSourceMapper;

    @Override
    public PageInfo<CollectionTaskItem> findByPage(CollectionTaskItem collectionTaskItem) {
        LOG.info(">>>query taskItem page params:{}", JsonUtils.objectToJson(collectionTaskItem));
        List<CollectionTaskItem> list = null;
        try {
            PageHelper.startPage(collectionTaskItem.getPage(), collectionTaskItem.getRows());
            list = collectionTaskItemMapper.findByPage(collectionTaskItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PageInfo<CollectionTaskItem>(list);
    }

    @Transactional(readOnly = false)
    @Override
    public ResultMessage addTaskItem(CollectionTaskItem collectionTaskItem) {
        LOG.info(">>>add collectionTaskItem params:{}", JsonUtils.objectToJson(collectionTaskItem));
       /* CollectionTaskInfo collectionTaskInfo = collectionTaskInfoMapper.selectByPrimaryKey(collectionTaskItem.getTaskInfoId());
        if(collectionTaskInfo == null){
            return ResultMessage.fail("主任务不存在");
        }
        CollectionTemplate collectionTemplate = collectionTaskInfo.getCollectionTemplate();
        if(collectionTemplate == null){
            return ResultMessage.fail("采集模板不存在");
        }
        String templateContent = collectionTemplate.getTemplateContent();
        if(StringUtils.isEmpty(templateContent)){
            return ResultMessage.fail("采集模板内容不存在");
        }
        CollectionTaskVo collectionTaskVo = BeanToXmlUtils.xml2Bean(CollectionTaskVo.class,templateContent);
        List<CollectionFieldModelVo> collectionFieldModelVoList = collectionTaskVo.getCollectionFieldModelVoList();
        if(CollectionUtils.isEmpty(collectionFieldModelVoList)){
            return ResultMessage.fail("xml转换异常");
        }*/
        //插入子任务
        collectionTaskItem.setCreateTime(new Date());
        collectionTaskItem.setDelFlag(1);
        collectionTaskItemMapper.insert(collectionTaskItem);
        //插入子任务和采集字段的关联关系
        List<TaskItemFeild> taskItemFeildList = new ArrayList<TaskItemFeild>();
        if(CollectionUtils.isNotEmpty(collectionTaskItem.getCollectionFieldModelVoList())){
            for(CollectionFieldModelVo collectionFieldModelVo : collectionTaskItem.getCollectionFieldModelVoList()){
                TaskItemFeild taskItemFeild = new TaskItemFeild(collectionTaskItem.getId(),collectionFieldModelVo.getFieldNameCh(),collectionFieldModelVo.getFieldNameEn(),collectionFieldModelVo.getCollectionValue(),new Date(),1);
                taskItemFeildList.add(taskItemFeild);
            }
            taskItemFeildMapper.batchInsertTaskItemFeild(taskItemFeildList);
        }
        return ResultMessage.success();
    }

    @Transactional(readOnly =false)
    @Override
    public ResultMessage updateTaskItem(CollectionTaskItem collectionTaskItem) {
        LOG.info(">>>update collectionTaskItem params:{}", JsonUtils.objectToJson(collectionTaskItem));
        CollectionTaskItem dbCollectionTaskItem1 = collectionTaskItemMapper.selectByPrimaryKey(collectionTaskItem.getId());
        if(dbCollectionTaskItem1 == null){
            return ResultMessage.fail("记录不存在");
        }
        if(StringUtils.equals("02",dbCollectionTaskItem1.getCollectionStatus())){//是否采集完成
            return ResultMessage.fail("此任务已采集完成");
        }
        collectionTaskItem.setUpdateTime(new Date());
        collectionTaskItemMapper.updateByPrimaryKeySelective(collectionTaskItem);
        //更新采集字段值
        List<TaskItemFeild> taskItemFeildList = new ArrayList<TaskItemFeild>();
        for(CollectionFieldModelVo collectionFieldModelVo : collectionTaskItem.getCollectionFieldModelVoList()){
            TaskItemFeild taskItemFeild = new TaskItemFeild();
            taskItemFeild.setTaskItemId(collectionTaskItem.getId());//子任务Id
            taskItemFeild.setFeildNameCh(collectionFieldModelVo.getFieldNameCh());//中文名
            taskItemFeild.setFeildNameEn(collectionFieldModelVo.getFieldNameEn());//英文名
            taskItemFeild.setCollectionValue(collectionFieldModelVo.getCollectionValue());//采集值
            taskItemFeildList.add(taskItemFeild);
        }
        if(CollectionUtils.isNotEmpty(taskItemFeildList)){
            taskItemFeildMapper.batchUpdateFeildValue(taskItemFeildList);
        }

        return ResultMessage.success();
    }

    @Override
    public ResultMessage deleteTaskItem(CollectionTaskItem collectionTaskItem) {
        LOG.info(">>>delete collectionTaskItem id:{}",collectionTaskItem.getId());
        CollectionTaskItem dbCollectionTaskItem1 = collectionTaskItemMapper.selectByPrimaryKey(collectionTaskItem.getId());
        if(dbCollectionTaskItem1 == null){
            return ResultMessage.fail("记录不存在");
        }
        collectionTaskItem.setUpdateTime(new Date());
        collectionTaskItem.setDelFlag(0);
        collectionTaskItemMapper.updateByPrimaryKeySelective(collectionTaskItem);
        return ResultMessage.success();
    }

    @Override
    public CollectionTaskItem getById(Integer id) {
        LOG.info(">>>query collectionTaskItem id:{}", id);
        CollectionTaskItem collectionTaskItem = collectionTaskItemMapper.selectByPrimaryKey(id);
        if(collectionTaskItem != null){
            List<CollectionFieldModelVo> taskItemFeildList = taskItemFeildMapper.findByItemId(id);
            collectionTaskItem.setCollectionFieldModelVoList(taskItemFeildList);
            List<CollectionMediaSource> amrMediaList = new ArrayList<CollectionMediaSource>();
            List<CollectionMediaSource> pngMediaList = new ArrayList<CollectionMediaSource>();
            if(StringUtils.isNotEmpty(collectionTaskItem.getMediaId())){
                List<String> mediaIds = Lists.newArrayList(collectionTaskItem.getMediaId().split(","));
                List<CollectionMediaSource> mediaList = collectionMediaSourceMapper.getMediaList(mediaIds);
                if(CollectionUtils.isNotEmpty(mediaList)){
                    for(CollectionMediaSource collectionMediaSource : mediaList){
                        if(StringUtils.equals(collectionMediaSource.getMediaType(),MEDIA_AMR.getValue())){
                            //collectionMediaSource.setMediaBase64(ByteArrayUtils.getImageString(collectionMediaSource.getMediaBin()));
                            collectionMediaSource.setMediaBin(null);
                            amrMediaList.add(collectionMediaSource);
                        }else if(StringUtils.equals(collectionMediaSource.getMediaType(),MEDIA_PNG.getValue())
                                || StringUtils.equals(collectionMediaSource.getMediaType(),MEDIA_JPG.getValue())){
                            collectionMediaSource.setMediaBin(null);
                            pngMediaList.add(collectionMediaSource);
                        }
                    }
                }
//                collectionTaskItem.setCollectionMediaSourceList(mediaList);
            }
            collectionTaskItem.setAmrMediaList(amrMediaList);
            collectionTaskItem.setPngMediaList(pngMediaList);
        }
        return collectionTaskItem;
    }

    @Override
    public List<CollectionTaskItemVo> getAllItemListByInfoId(Integer taskInfoId) {
        return collectionTaskItemMapper.getAllItemListByInfoId(taskInfoId);
    }

    @Override
    public ResultMessage getCoordinates(CollectionTaskItem collectionTaskItemQuery) {
        List<CollectionTaskItem> collectionTaskItemList = collectionTaskItemMapper.findByPage(collectionTaskItemQuery);
        if(CollectionUtils.isEmpty(collectionTaskItemList)){
            return ResultMessage.fail();
        }
        List<CoordingatesVo> coordingatesVoList = new ArrayList<CoordingatesVo>();
        for(CollectionTaskItem collectionTaskItem : collectionTaskItemList){
//            String [lat/lng: (39.87346327531677,116.3154832491759)]
            String position = collectionTaskItem.getPosition();
            if(StringUtils.isEmpty(position) || StringUtils.equals(position,"[null]")){
                continue;
            }
            CoordingatesVo coordingatesVo = new CoordingatesVo();
            //采用截取的方式把经纬度截取出来
            int start = position.indexOf("(");
            int end = position.indexOf(")");
            String coordinate = position.substring(start+1,end);
            coordingatesVo.setLatitude(coordinate.split(",")[0]);
            coordingatesVo.setLongitude(coordinate.split(",")[1]);
            coordingatesVo.setSurveyDddress(collectionTaskItem.getSurveyAddress());
            coordingatesVo.setId(collectionTaskItem.getId());
            coordingatesVoList.add(coordingatesVo);
        }
        return ResultMessage.success(coordingatesVoList);
    }

 /*   @Override
    public ResultMessage getCoordinatesByTaskId(Integer taskId) {
        List<CollectionTaskItem> collectionTaskItemList = collectionTaskItemMapper.getCoordinatesByTaskId(taskId);
        if(CollectionUtils.isEmpty(collectionTaskItemList)){
            return ResultMessage.fail();
        }
        List<CoordingatesVo> coordingatesVoList = new ArrayList<CoordingatesVo>();
        for(CollectionTaskItem collectionTaskItem : collectionTaskItemList){
            String position = collectionTaskItem.getPosition();
            if(StringUtils.isEmpty(position) || StringUtils.equals(position,"[null]")){
                continue;
            }
            CoordingatesVo coordingatesVo = new CoordingatesVo();
            //采用截取的方式把经纬度截取出来
            int start = position.indexOf("(");
            int end = position.indexOf(")");
            String coordinate = position.substring(start+1,end);
            coordingatesVo.setLatitude(coordinate.split(",")[0]);
            coordingatesVo.setLongitude(coordinate.split(",")[1]);
            coordingatesVo.setSurveyDddress(collectionTaskItem.getSurveyAddress());
            coordingatesVo.setId(collectionTaskItem.getId());
            coordingatesVoList.add(coordingatesVo);
        }
        return ResultMessage.success(coordingatesVoList);
    }*/
}
