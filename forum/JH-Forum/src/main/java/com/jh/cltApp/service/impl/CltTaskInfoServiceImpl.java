package com.jh.cltApp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.jh.cltApp.entity.CltMediaSource;
import com.jh.cltApp.entity.CltTaskInfo;
import com.jh.cltApp.entity.CltTaskUser;
import com.jh.cltApp.entity.CltTemplate;
import com.jh.cltApp.mapping.ICltTaskInfoMapper;
import com.jh.cltApp.service.ICltMediaSourceService;
import com.jh.cltApp.service.ICltTaskInfoService;
import com.jh.cltApp.service.ICltTaskUserService;
import com.jh.cltApp.service.ICltTemplateService;
import com.jh.cltApp.utils.ZipUtil;
import com.jh.cltApp.vo.CltTaskInfoParamsVO;
import com.jh.cltApp.vo.CltTaskInfoVO;
import com.jh.cltApp.vo.CltTaskItemVO;
import com.jh.collection.entity.CollectionMediaSource;
import com.jh.collection.entity.CollectionTaskInfo;
import com.jh.collection.entity.CollectionTaskItem;
import com.jh.forum.bbs.Enum.CltTaskStatusEnum;
import com.jh.util.CacheUtil;
import com.jh.util.DateUtil;
import com.jh.util.JsonUtils;
import com.jh.util.RedisUtil;
import com.jh.util.cache.IdTransformUtils;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.jh.util.ceph.CephUtils.uploadImage;
import static jdk.nashorn.internal.objects.NativeString.substring;

/**
 * 采集器任务详情实现类
 * @version <1> 2019/4/9 10:02 zhangshen:Created.
 */
@Service
@Transactional
public class CltTaskInfoServiceImpl implements ICltTaskInfoService {

    @Autowired
    private ICltTaskInfoMapper cltTaskInfoMapper;

    @Autowired
    private ICltTemplateService cltTemplateService;

    @Autowired
    private ICltTaskUserService cltTaskUserService;

    @Autowired
    private ICltMediaSourceService cltMediaSourceService;

    private static Logger log = LoggerFactory.getLogger(CltTaskInfoServiceImpl.class);

    @Override
    public PageInfo<CltTaskInfo> findCltTaskInfoPageInfo(CltTaskInfo cltTaskInfo) {
        PageHelper.startPage(cltTaskInfo.getPage(), cltTaskInfo.getRows());
        List<CltTaskInfo> list = cltTaskInfoMapper.findCltTaskInfoList(cltTaskInfo);

        for (CltTaskInfo taskInfo: list) {
            ResultMessage resultMessage = cltTemplateService.getById(taskInfo.getTemplateId());
            CltTemplate template = (CltTemplate)resultMessage.getData();
            taskInfo.setCltTemplate(template);
        }

        return new PageInfo<CltTaskInfo>(list);
    }

    @Override
    public PageInfo<CltTaskInfo> findCltTaskInfoPageInfoCms(CltTaskInfo cltTaskInfo) {
        PageHelper.startPage(cltTaskInfo.getPage(), cltTaskInfo.getRows());
        List<CltTaskInfo> list = cltTaskInfoMapper.findCltTaskInfoListCms(cltTaskInfo);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<CltTaskInfo>(list);
    }

    @Override
    public ResultMessage findCltTaskInfoListInfoCms(CltTaskInfo cltTaskInfo) {
        List<CltTaskInfo> list = cltTaskInfoMapper.findCltTaskInfoListCms(cltTaskInfo);
        IdTransformUtils.idTransForList(list);
        return ResultMessage.success(list);
    }

    @Override
    public PageInfo<CltTaskInfo> findCltTaskInfoPageInfoBbs(CltTaskInfo cltTaskInfo) {
        PageHelper.startPage(cltTaskInfo.getPage(), cltTaskInfo.getRows());
        List<CltTaskInfo> list = cltTaskInfoMapper.findCltTaskInfoListBbs(cltTaskInfo);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<CltTaskInfo>(list);
    }

    /**
     * 根据用户id, 查询任务列表, 包括自己创建的任务和参与的任务
     *
     * @param
     * @return
     * @version <1> 2019/4/9 10:11 zhangshen:Created.
     */
    @Override
    public ResultMessage findCltTaskInfoList(CltTaskInfo cltTaskInfo) {
        List<CltTaskInfo> cltTaskInfoList = cltTaskInfoMapper.findCltTaskInfoList(cltTaskInfo);
        return ResultMessage.success(cltTaskInfoList);
    }

    @Override
    public ResultMessage createTaskInfo(CltTaskInfoParamsVO cltTaskInfoParamsVO) {

        CltTaskInfo cltTaskInfo = cltTaskInfoParamsVO.getCltTaskInfo();
        CltTemplate cltTemplate = cltTaskInfoParamsVO.getCltTemplate();
        Integer[] userArr = cltTaskInfoParamsVO.getUserArr();
        List<Integer> userList = new ArrayList<Integer>();
        for (Integer accountId : userArr) {
            userList.add(accountId);
        }
        if (!userList.contains(cltTaskInfoParamsVO.getCreator())) {
            userList.add(cltTaskInfoParamsVO.getCreator());
        }

        cltTaskInfo.setCreator(cltTaskInfoParamsVO.getCreator());
        cltTaskInfo.setCreatorName(cltTaskInfoParamsVO.getCreatorName());
        cltTaskInfo.setModifier(cltTaskInfoParamsVO.getModifier());
        cltTaskInfo.setModifierName(cltTaskInfoParamsVO.getModifierName());
        cltTaskInfo.setTemplateId(cltTemplate.getTemplateId());
        cltTaskInfo.setTaskStatus(21001);
        String startDateStr = cltTaskInfoParamsVO.getStartDateStr();
        String endDateStr = cltTaskInfoParamsVO.getEndDateStr();
        cltTaskInfo.setStartDate(DateUtil.strToLocalDateTime(startDateStr, "yyyy-MM-dd HH:mm"));
        cltTaskInfo.setEndDate(DateUtil.strToLocalDateTime(endDateStr, "yyyy-MM-dd HH:mm"));
        cltTaskInfo.setTaskFlag(1);

        if (cltTaskInfo.getRegionId() != null) {
            String regionCode= RedisUtil.get(CacheUtil.CACHE_REGION_TYPE + CacheUtil.CACHE_TRANS_CODE + cltTaskInfo.getRegionId());
            cltTaskInfo.setRegionCode(regionCode);
        }

        cltTaskInfoMapper.insertSelective(cltTaskInfo);
        Integer taskId = cltTaskInfo.getTaskId();


        for (int i = 0; i < userList.size(); i++) {
            CltTaskUser cltTaskUser = new CltTaskUser();
            cltTaskUser.setAccountId(userList.get(i));
            cltTaskUser.setIsShoulder("0");
            cltTaskUser.setTaskId(taskId);
            cltTaskUser.setCreator(cltTaskInfoParamsVO.getCreator());
            cltTaskUser.setCreatorName(cltTaskInfoParamsVO.getCreatorName());
            cltTaskUser.setModifier(cltTaskInfoParamsVO.getModifier());
            cltTaskUser.setModifierName(cltTaskInfoParamsVO.getModifierName());
            if (userList.get(i).equals(cltTaskInfoParamsVO.getCreator())) {
                cltTaskUser.setIsShoulder("1");
            }
            cltTaskUserService.createCltTaskUser(cltTaskUser);
        }


        if (null != cltTemplate) {
            if (null == cltTemplate.getTemplateId()) {
                cltTemplate.setCreator(cltTaskInfoParamsVO.getCreator());
                cltTemplate.setCreatorName(cltTaskInfoParamsVO.getCreatorName());
                cltTemplate.setModifier(cltTaskInfoParamsVO.getModifier());
                cltTemplate.setModifierName(cltTaskInfoParamsVO.getModifierName());
                cltTemplateService.createCltTemplate(cltTemplate);
            } else {
                cltTaskInfo.setTemplateId(cltTemplate.getTemplateId());
            }
        }


        return ResultMessage.success(cltTaskInfo);
    }

    @Override
    public ResultMessage updateTaskStatusByTaskId(CltTaskInfo cltTaskInfo) {
        cltTaskInfoMapper.updateByPrimaryKeySelective(cltTaskInfo);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage auditTaskList(CltTaskInfo cltTaskInfo) {
        cltTaskInfoMapper.auditTaskList(cltTaskInfo);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage updateTaskInfoByTaskId(CltTaskInfoParamsVO cltTaskInfoParamsVO) {
        CltTaskInfo cltTaskInfo = cltTaskInfoParamsVO.getCltTaskInfo();

        if (cltTaskInfoParamsVO.getCltTemplate().getTemplateId() != null) {
            cltTaskInfo.setTemplateId(cltTaskInfoParamsVO.getCltTemplate().getTemplateId());
        }

        String startDateStr = cltTaskInfoParamsVO.getStartDateStr();
        String endDateStr = cltTaskInfoParamsVO.getEndDateStr();
        cltTaskInfo.setStartDate(DateUtil.strToLocalDateTime(startDateStr, "yyyy-MM-dd HH:mm"));
        cltTaskInfo.setEndDate(DateUtil.strToLocalDateTime(endDateStr, "yyyy-MM-dd HH:mm"));

        if (cltTaskInfo.getRegionId() != null) {
            String regionCode= RedisUtil.get(CacheUtil.CACHE_REGION_TYPE + CacheUtil.CACHE_TRANS_CODE + cltTaskInfo.getRegionId());
            cltTaskInfo.setRegionCode(regionCode);
        }

        cltTaskInfoMapper.updateByPrimaryKeySelective(cltTaskInfo);

        Integer taskId = cltTaskInfoParamsVO.getCltTaskInfo().getTaskId();

        cltTaskUserService.deleteCltTaskUserByTaskId(taskId);


        Integer[] userArr = cltTaskInfoParamsVO.getUserArr();
        List<Integer> userList = new ArrayList<Integer>();
        for (Integer accountId : userArr) {
            userList.add(accountId);
        }
        if (!userList.contains(cltTaskInfoParamsVO.getModifier())) {
            userList.add(cltTaskInfoParamsVO.getModifier());
        }

        for (int i = 0; i < userList.size(); i++) {
            CltTaskUser cltTaskUser = new CltTaskUser();
            cltTaskUser.setTaskId(taskId);
            cltTaskUser.setAccountId(userList.get(i));
            cltTaskUser.setIsShoulder("0");
            cltTaskUser.setCreator(cltTaskInfoParamsVO.getModifier());
            cltTaskUser.setCreatorName(cltTaskInfoParamsVO.getModifierName());
            cltTaskUser.setModifier(cltTaskInfoParamsVO.getModifier());
            cltTaskUser.setModifierName(cltTaskInfoParamsVO.getModifierName());
            if (userList.get(i) == cltTaskInfoParamsVO.getModifier()) {
                cltTaskUser.setIsShoulder("1");
            }
            cltTaskUserService.createCltTaskUser(cltTaskUser);
        }

        return ResultMessage.success(cltTaskInfo);
    }

    @Override
    public ResultMessage deleteTaskInfoByTaskId(Integer taskId) {
        cltTaskInfoMapper.deleteByPrimaryKey(taskId);//删除任务
        cltTaskUserService.deleteCltTaskUserByTaskId(taskId);//删除参与人
        cltMediaSourceService.deleteMediaSourceByTaskId(taskId);//删除媒体资源
        return ResultMessage.success();
    }

    @Override
    public ResultMessage deleteTaskInfoByTaskIds(List<Integer> taskIds) {
        for(Integer taskId:taskIds){
            deleteTaskInfoByTaskId(taskId);
        }
        return ResultMessage.success();
    }

    @Override
    public PageInfo<CltTaskInfo> findShareCltTaskInfoPageInfo(CltTaskInfo cltTaskInfo) {
        PageHelper.startPage(cltTaskInfo.getPage(), cltTaskInfo.getRows());
        List<Integer> isPublishArr = new ArrayList<Integer>();
        if (cltTaskInfo.getIsPublish() == null) {
            isPublishArr.add(CltTaskStatusEnum.ARTICLE_STATUS_PENDING.getId());
            isPublishArr.add(CltTaskStatusEnum.ARTICLE_STATUS_PUBLISHED.getId());
        } else {
            isPublishArr.add(cltTaskInfo.getIsPublish());
        }
        List<CltTaskInfo> list = cltTaskInfoMapper.findShareCltTaskInfoPageInfo(cltTaskInfo, isPublishArr);
        return new PageInfo<CltTaskInfo>(list);
    }

    @Override
    public PageInfo<CltTaskInfo> findJoinCltTaskInfoPageInfo(CltTaskInfo cltTaskInfo) {
        PageHelper.startPage(cltTaskInfo.getPage(), cltTaskInfo.getRows());
        List<CltTaskInfo> list = cltTaskInfoMapper.findCltTaskInfoList(cltTaskInfo);
        return new PageInfo<CltTaskInfo>(list);
    }

    /**
     * 多条件查询任务列表
     *
     * @param cltTaskInfo
     * @return ageInfo<CltTaskInfo>
     * @version <1> 2019/4/12 mason:Created.
     */
    @Override
    public PageInfo<CltTaskInfo> getListByCombination(CltTaskInfo cltTaskInfo) {
        PageHelper.startPage(cltTaskInfo.getPage(), cltTaskInfo.getRows());
        List<CltTaskInfo> list = cltTaskInfoMapper.getListByCombination(cltTaskInfo);
        return new PageInfo<CltTaskInfo>(list);
    }

    /**
     * 根据taskId查找任务详情
     *
     * @param
     * @return
     * @version <1> 2019/4/15 15:56 zhangshen:Created.
     */
    @Override
    public ResultMessage getCltTaskInfoByTaskId(CltTaskInfo cltTaskInfo) {
        CltTaskInfoVO cltTaskInfoVO = cltTaskInfoMapper.getCltTaskInfoByTaskId(cltTaskInfo.getTaskId());
        IdTransformUtils.idTransForObj(cltTaskInfoVO);
        return ResultMessage.success(cltTaskInfoVO);
    }

    public ResultMessage updateIndexShow(HttpServletRequest request) {
        CltTaskInfo collectionTaskInfo = new CltTaskInfo();
        String indexShow = request.getParameter("indexShow");
        String id = request.getParameter("id");
        collectionTaskInfo.setTaskId(Integer.parseInt(id));
        collectionTaskInfo.setIndexShow(Integer.parseInt(indexShow));
        //1.修改是否轮播
        cltTaskInfoMapper.updateByPrimaryKeySelective(collectionTaskInfo);
        //2.如果为设置首页轮播，则上传头像
        if (collectionTaskInfo.getIndexShow() == 1) {
            uploadImage(request, collectionTaskInfo.getTaskId());
        }
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
        File copyFile = new File(storagePath);//文件
        try {
            file.transferTo(copyFile);//复制文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        //修改路径
        CltTaskInfo task=cltTaskInfoMapper.selectByPrimaryKey(taskId);
        task.setIndexShowImg(photoUrl+newName);
        cltTaskInfoMapper.updateByPrimaryKeySelective(task);
        return newName;
    }

    @Override
    public byte[] downloadMediaByTaskInfoId(HttpServletRequest request,List<CltTaskItemVO> taskItemList) throws IOException {
        if(CollectionUtils.isEmpty(taskItemList)){
            return new byte[0];
        }
        List<File> zipfileList = new ArrayList<>();
        Integer taskId = null;
        Integer accountId =null;
        //子任务打包
        String path = "";
        //整个任务路径
        String taskPath ="";
        for(int i = 0;i< taskItemList.size();i++){
            CltTaskItemVO collectionTaskItem = taskItemList.get(i);
            taskId = collectionTaskItem.getTaskId();
            accountId = collectionTaskItem.getCreator();
            ResultMessage result = cltMediaSourceService.findCltMediaSourceListByItemId(collectionTaskItem.getItemId());
            if(!result.isFlag()){
                return new byte[0];
            }
            List<CltMediaSource> mediaIdList = ( List<CltMediaSource> )result.getData();
            if(CollectionUtils.isEmpty(mediaIdList)){
                continue;
            }
            Map<String, Object> map = new HashMap<String, Object>();
            List<File> fileList = new ArrayList<>();
            for(CltMediaSource mediaSource : mediaIdList){
                if(StringUtils.isNotEmpty(mediaSource.getMediaPath())){
                    File file = new File(CephUtils.getAbsolutePath(mediaSource.getMediaPath()));
                    if(file.exists()){
                        fileList.add(file);
                    }
                }
            }
            if(CollectionUtils.isEmpty(fileList)){
                continue;
            }
            //打包文件目录:第二个/为任务目录
            taskPath = CephUtils.getAbsolutePath(CephUtils.getCephUrl("CLT_TASK_ITEM_MEDIA_STORAGE").replace("\\",File.separator) + File.separator + accountId);
            path = taskPath+"_"+taskId+"_zip";
            log.info("打包路径为{}",path);
            File taskInfoDir = new File(path);
            if(!taskInfoDir.exists()){
                taskInfoDir.mkdirs();
            }
            File file = ZipUtil.zip(path, fileList,collectionTaskItem.getSurveyAddress()+"("+(i+1)+")");
            zipfileList.add(file);
        }
        //打包大任务下的所有子任务
        if(zipfileList.size() == 0){
            return new byte[0];
        }
        String taskInfopath = path;
        log.info("任务路径为{}",path);
        File file = ZipUtil.zip(taskInfopath, zipfileList);
        byte[] body = null;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        is.read(body);
        // 关闭流
        if (is != null) {
            is.close();
        }
       /* //删除大任务zip
        file.delete();*/
        //删除小任务zip文件夹
        if(StringUtils.isNotEmpty(path)){
            File smallZipDir = new File(path);
            if(smallZipDir.exists()){
                FileUtils.deleteDirectory(smallZipDir);
            }
        }
        return body;
    }
}
