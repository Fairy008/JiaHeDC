package com.jh.collection.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.CollectionTaskInfo;
import com.jh.collection.entity.CollectionTemplate;
import com.jh.collection.entity.TaskItemFeild;
import com.jh.collection.entity.query.CollectionTaskInfoQuery;
import com.jh.collection.entity.vo.BbsCollectionTaskVo;
import com.jh.collection.entity.vo.CollectionFieldModelVo;
import com.jh.collection.entity.vo.CollectionTaskVo;
import com.jh.collection.mapping.CollectionTaskInfoMapper;
import com.jh.collection.mapping.CollectionTaskItemMapper;
import com.jh.collection.mapping.CollectionTemplateMapper;
import com.jh.collection.mapping.TaskItemFeildMapper;
import com.jh.collection.service.ICollectionTaskInfoService;
import com.jh.collection.utils.BeanToXmlUtils;
import com.jh.collection.utils.MapKeyComparator;
import com.jh.constant.SysConstant;
import com.jh.forum.bbs.vo.ExpertVO;
import com.jh.util.DateUtil;
import com.jh.util.JsonUtils;
import com.jh.util.cache.IdTransformUtils;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.jh.constant.CommonDefineEnum.SAVE_OBJECT_FAIL;
import static com.jh.constant.CommonDefineEnum.SAVE_OBJECT_SUCCESS;

/**
 * 采集任务（大分类）Service
 * @version <1> 2018-12-04: xy Created.
 */
@Service
public class CollectionTaskInfoServiceImpl implements ICollectionTaskInfoService {

    private static final Logger LOG = LoggerFactory.getLogger(CollectionTaskInfoServiceImpl.class);

    @Autowired
    private CollectionTaskInfoMapper collectionTaskInfoMapper;
    @Autowired
    private CollectionTemplateMapper collectionTemplateMapper;
    @Autowired
    private TaskItemFeildMapper taskItemFeildMapper;
    @Autowired
    private CollectionTaskItemMapper collectionTaskItemMapper;

    @Override
    public PageInfo<CollectionTaskInfo> findByPage(CollectionTaskInfoQuery collectionTaskInfoQuery) {
        LOG.info(">>>collectionTaskInfoQuery:{}", JsonUtils.objectToJson(collectionTaskInfoQuery));
        PageHelper.startPage(collectionTaskInfoQuery.getPage(), collectionTaskInfoQuery.getRows());
        List<CollectionTaskInfo> list = collectionTaskInfoMapper.findByPage(collectionTaskInfoQuery);
        return new PageInfo<CollectionTaskInfo>(list);
    }

    @Override
    public ResultMessage addTaskInfo(CollectionTaskInfo collectionTaskInfo) {
        LOG.info(">>>add addTaskInfo params:{}", JsonUtils.objectToJson(collectionTaskInfo));
        CollectionTaskInfoQuery collectionTaskInfoQuery = new CollectionTaskInfoQuery();
        collectionTaskInfoQuery.setTaskName(collectionTaskInfo.getTaskName());
        collectionTaskInfoQuery.setPhone(collectionTaskInfo.getPhone());
        List<CollectionTaskInfo> list = collectionTaskInfoMapper.findByPage(collectionTaskInfoQuery);
        if(CollectionUtils.isNotEmpty(list)){
            LOG.info(">>>taskInfo is repeat lise size={}",list.size());
           return ResultMessage.fail("任务名不能重复");
        }
        ResultMessage res = ResultMessage.success(SAVE_OBJECT_SUCCESS.getMesasge());
        collectionTaskInfo.setDelFlag(1);
        collectionTaskInfo.setCreateTime(new Date());
        if(collectionTaskInfo.getTemplateId()!=null){
            CollectionTemplate collectionTemplate = collectionTemplateMapper.selectByPrimaryKey(collectionTaskInfo.getTemplateId());
            if(collectionTemplate !=null){
                if(collectionTemplate.getTemplateType().intValue() != collectionTaskInfo.getTaskType().intValue()){
                    return ResultMessage.fail("任务类型和模板类型不匹配");
                }else{
                    collectionTaskInfo.setCropId(collectionTemplate.getCropId());
                    collectionTaskInfo.setPurpose(collectionTemplate.getPurpose());
//                collectionTaskInfo.setWorkplace(collectionTemplate.getWorkplace());
                    collectionTaskInfo.setTemplateContent(collectionTemplate.getTemplateContent());
                }
            }
        }
        collectionTaskInfo.setTaskFlag(1);//默认任务
        int count = collectionTaskInfoMapper.insert(collectionTaskInfo);
        LOG.info(">>>insert taskInfo success");
        return count > 0 ? res : ResultMessage.fail(SAVE_OBJECT_FAIL.getMesasge());
    }

    @Override
    public ResultMessage updateTaskInfo(CollectionTaskInfo collectionTaskInfo) {
        LOG.info(">>>update TaskInfo taskInfoId:{}",collectionTaskInfo.getId());
        CollectionTaskInfo dbCollectionTaskInfo = this.getById(collectionTaskInfo.getId());
        if(dbCollectionTaskInfo == null){
            return ResultMessage.fail("此记录不存在");
        }
        collectionTaskInfo.setUpdateTime(new Date());
        ResultMessage res = ResultMessage.success(SAVE_OBJECT_SUCCESS.getMesasge());
        int count = collectionTaskInfoMapper.updateByPrimaryKeySelective(collectionTaskInfo);
        return count > 0 ? res : ResultMessage.fail(SAVE_OBJECT_FAIL.getMesasge());
    }

    @Override
    public ResultMessage deleteTaskInfo(CollectionTaskInfo collectionTaskInfo) {
        LOG.info(">>>delete TaskInfo taskInfoId:{}",collectionTaskInfo.getId());
        CollectionTaskInfo dbCollectionTaskInfo = this.getById(collectionTaskInfo.getId());

        if(dbCollectionTaskInfo == null){
            return ResultMessage.fail("此记录不存在");
        }
        if(dbCollectionTaskInfo.getTaskFlag() == 0){
            return ResultMessage.fail("测试任务不能删除");
        }
        //查看大任务下是否有子任务
        int taskItemCount = collectionTaskItemMapper.findTaskItemCount(collectionTaskInfo.getId());
        if(taskItemCount>0){
            return ResultMessage.fail("已存在采集任务，不能删除");
        }
        collectionTaskInfo.setDelFlag(0);
        collectionTaskInfo.setUpdateTime(new Date());
        ResultMessage res = ResultMessage.success(SAVE_OBJECT_SUCCESS.getMesasge());
        int count = collectionTaskInfoMapper.updateByPrimaryKeySelective(collectionTaskInfo);
        return count > 0 ? res : ResultMessage.fail(SAVE_OBJECT_FAIL.getMesasge());
    }

    @Override
    public CollectionTaskInfo getById(Integer id) {
        LOG.info(">>>query TaskInfo taskInfoId:{}",id);
        CollectionTaskInfo collectionTaskInfo = collectionTaskInfoMapper.selectByPrimaryKey(id);
        if(collectionTaskInfo !=null && StringUtils.isNotEmpty(collectionTaskInfo.getTemplateContent())){
            CollectionTaskVo collectionTaskVo = BeanToXmlUtils.xml2Bean(CollectionTaskVo.class,collectionTaskInfo.getTemplateContent());
            if(collectionTaskVo !=null){
                CollectionTemplate collectionTemplate = new CollectionTemplate();
                collectionTemplate.setCollectionFieldModelVoList(collectionTaskVo.getCollectionFieldModelVoList());
                collectionTaskInfo.setCollectionTemplate(collectionTemplate);
            }else{
                CollectionTemplate collectionTemplate = new CollectionTemplate();
                collectionTemplate.setCollectionFieldModelVoList(new ArrayList<CollectionFieldModelVo>());
                collectionTaskInfo.setCollectionTemplate(collectionTemplate);
            }
        }
        return collectionTaskInfo;
    }

    @Override
    public Map<Integer, List<TaskItemFeild>> getTaskItemFeildMap(Integer taskInfoId) {
        LOG.info(">>>taskInfoId:{}",taskInfoId);
        Map<Integer, List<TaskItemFeild>> map = new HashMap<Integer, List<TaskItemFeild>>();
        List<TaskItemFeild> allTaskItemFeildList = taskItemFeildMapper.findListsByTaskInfo(taskInfoId);
        if(CollectionUtils.isNotEmpty(allTaskItemFeildList)){
            for(TaskItemFeild taskItemFeild : allTaskItemFeildList){
                List<TaskItemFeild> taskItemFeildList = map.get(taskItemFeild.getTaskItemId());
                if(taskItemFeildList != null){
                    taskItemFeildList.add(taskItemFeild);
                }else{
                    List<TaskItemFeild> newTaskItemFeildList = new ArrayList<TaskItemFeild>();
//                    CollectionTaskItem collectionTaskItem = collectionTaskItemMapper.selectByPrimaryKey(taskItemFeild.getTaskItemId());
                    //任务名
                    TaskItemFeild taskTaskFeild = new TaskItemFeild("任务名",taskItemFeild.getTaskName());
                    //作物
                    TaskItemFeild cropFeild = new TaskItemFeild("作物",taskItemFeild.getCropName());
                    //调查地址
                    TaskItemFeild addressTaskFeild = new TaskItemFeild("调查地址",taskItemFeild.getSurveyAddress());
                    //用途
                    TaskItemFeild purposeTaskFeild = new TaskItemFeild("用途",taskItemFeild.getPurpose());
                    //调查时间
                    TaskItemFeild timeFeild = new TaskItemFeild("调查时间", DateUtil.dateToString(taskItemFeild.getSurveyTime(),"yyyy-MM-dd"));
                    //位置信息
                    TaskItemFeild positionFeild = new TaskItemFeild("位置信息", taskItemFeild.getPosition());

                    newTaskItemFeildList.add(taskTaskFeild);
                    newTaskItemFeildList.add(cropFeild);
                    newTaskItemFeildList.add(addressTaskFeild);
                    newTaskItemFeildList.add(purposeTaskFeild);
                    newTaskItemFeildList.add(timeFeild);
                    newTaskItemFeildList.add(positionFeild);

                    newTaskItemFeildList.add(taskItemFeild);
                    map.put(taskItemFeild.getTaskItemId(),newTaskItemFeildList);
                }
            }
        }
        Map<Integer, List<TaskItemFeild>> sortMap = new TreeMap<Integer, List<TaskItemFeild>>(
                new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    @Override
    public String getAllMedisId(Integer taskInfoId) {
        if(taskInfoId == null){
            return "";
        }
        List<String> allTaskItemMedia = collectionTaskItemMapper.findAllTaskItemMedia(taskInfoId);
        if(CollectionUtils.isEmpty(allTaskItemMedia)){
            return "";
        }
        StringBuffer mediaBuf = new StringBuffer();
        for(String media : allTaskItemMedia){
            mediaBuf.append(media).append(",");
        }
        return mediaBuf.toString();
    }

    @Override
    public ResultMessage registerToAllocation(String phone) {
        if(StringUtils.isEmpty(phone)){
            LOG.info(">>>>param is empty,create taskInfo fail phone:{}",phone);
            return ResultMessage.fail("注册失败，默认任务未创建成功");
        }
        //创建测试模板采集字段
        CollectionTaskVo collectionTaskVo = new CollectionTaskVo();
        List<CollectionFieldModelVo> collectionFieldModelVoList = new ArrayList<CollectionFieldModelVo>();
        CollectionFieldModelVo collectionFieldModelVo1 = new CollectionFieldModelVo();
        collectionFieldModelVo1.setFieldNameCh("采集项1");
        collectionFieldModelVo1.setFieldNameEn("cjx1");
        CollectionFieldModelVo collectionFieldModelVo2 = new CollectionFieldModelVo();
        collectionFieldModelVo2.setFieldNameCh("采集项2");
        collectionFieldModelVo2.setFieldNameEn("cjx2");
        collectionFieldModelVoList.add(collectionFieldModelVo1);
        collectionFieldModelVoList.add(collectionFieldModelVo2);
        collectionTaskVo.setCollectionFieldModelVoList(collectionFieldModelVoList);

        String templateContent = BeanToXmlUtils.beanToXml(collectionTaskVo, CollectionTaskVo.class);

        //大任务
        CollectionTaskInfo collectionTaskInfo = new CollectionTaskInfo();
        collectionTaskInfo.setTemplateContent(templateContent);
        collectionTaskInfo.setPhone(phone);
        collectionTaskInfo.setWorkplace("武汉珈和科技有限公司");
        collectionTaskInfo.setPurpose("水稻产量测试");
        collectionTaskInfo.setTaskName("任务测试");
        collectionTaskInfo.setCropId(507);//水稻
        collectionTaskInfo.setTaskOrigin("武汉珈和科技有限公司");
//        collectionTaskInfo.setTaskType(8101);
        collectionTaskInfo.setTaskType(1802);//估产
        collectionTaskInfo.setCreateor(phone);
        collectionTaskInfo.setDelFlag(1);
        collectionTaskInfo.setTaskFlag(0);//测试任务标识
        collectionTaskInfo.setCreateTime(new Date());

        collectionTaskInfoMapper.insert(collectionTaskInfo);
        return ResultMessage.success();
    }

    /**
     * 上传首页轮播图
     * @param
     * @return url 轮播地址
     * @version <1> 2019/3/6 lijie:Created.
     */
    private String uploadImage(HttpServletRequest request, Integer taskId) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("taskFile");
        if(file==null){
            return null;
        }
        //系统前缀
        String reportStorage = CephUtils.getAbsolutePath("").replace("\\",File.separator);
        //轮播目录
        String photoUrl=CephUtils.getCephUrl("FORUM_INDEX_STORAGE").replace("\\",File.separator)+File.separator;

        File f = new File(reportStorage+photoUrl);
        if (!f.exists()) {
            f.mkdirs();
        }
        String suff = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);//文件后缀
        String newName="index_url_" + taskId + "_"+ new Date().getTime()+ "." + suff;//重新生成用户名，防止中文名
        String storagePath = reportStorage +photoUrl+ newName;
        LOG.info("首页轮播图上传地址为：{}",storagePath);
        File copyFile = new File(storagePath);//文件
        try {
            file.transferTo(copyFile);//复制文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        //修改路径
        CollectionTaskInfo task=collectionTaskInfoMapper.selectByPrimaryKey(taskId);
        task.setIndexShowImg(photoUrl+newName);
        collectionTaskInfoMapper.updateByPrimaryKeySelective(task);
        return newName;
    }

    public ResultMessage updateIndexShow(HttpServletRequest request){
        CollectionTaskInfo collectionTaskInfo =new CollectionTaskInfo();
        String indexShow=request.getParameter("indexShow");
        String id=request.getParameter("id");
        collectionTaskInfo.setId(Integer.parseInt(id));
        collectionTaskInfo.setIndexShow(Integer.parseInt(indexShow));
        //1.修改是否轮播
        collectionTaskInfoMapper.updateByPrimaryKeySelective(collectionTaskInfo);
        //2.如果为设置首页轮播，则上传头像
        if(collectionTaskInfo.getIndexShow()==1){
            uploadImage(request,collectionTaskInfo.getId());
        }
        return ResultMessage.success();
    }


    /**
     * 采集任务查询
     * @param regionId 区域ID
     * @param phone 手机号
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return ResultMessage
     * @version <1> 2019-03-18 cxw： Created.
     */
    public ResultMessage findCollectDataList(String phone,Long regionId,String startDate,String endDate){
        if ( regionId == null || org.apache.commons.lang.StringUtils.isBlank(startDate) || org.apache.commons.lang.StringUtils.isBlank(endDate) ){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }
        BbsCollectionTaskVo bcTask =new  BbsCollectionTaskVo();
        if(StringUtils.isNotBlank(phone)) {
            bcTask.setPhone(phone);
        }
        bcTask.setRegionId(regionId);
        bcTask.setStartCreateTime(startDate);
        bcTask.setEndCreateTime(endDate);
        List<CollectionTaskInfo> list =  collectionTaskInfoMapper.findCollectDataList(bcTask);
        IdTransformUtils.idTransForList(list);
        return ResultMessage.success(list);


    }
}
