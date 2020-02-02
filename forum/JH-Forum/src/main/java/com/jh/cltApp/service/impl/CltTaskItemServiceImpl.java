package com.jh.cltApp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.sd4324530.fastweixin.util.CollectionUtil;
import com.jh.cltApp.entity.CltMediaSource;
import com.jh.cltApp.entity.CltTaskItem;
import com.jh.cltApp.mapping.ICltMediaSourceMapper;
import com.jh.cltApp.mapping.ICltTaskItemMapper;
import com.jh.cltApp.service.ICltMediaSourceService;
import com.jh.cltApp.service.ICltTaskItemService;
import com.jh.cltApp.vo.CltTaskItemVO;
import com.jh.util.DateUtil;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.MultimediaObject;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 采集器数据详情实现类
 * @version <1> 2019/4/9 10:02 zhangshen:Created.
 */
@Service
@Transactional
public class CltTaskItemServiceImpl implements ICltTaskItemService {

    @Autowired
    private ICltTaskItemMapper cltTaskItemMapper;

    @Autowired
    private ICltMediaSourceService cltMediaSourceService;

    private static Logger log = LoggerFactory.getLogger(CltTaskItemServiceImpl.class);

    @Override
    public ResultMessage findTaskItemByItemId(CltTaskItem cltTaskItem) {
        CltTaskItemVO cltTaskItemVO = cltTaskItemMapper.findTaskItemByItemId(cltTaskItem);
        ResultMessage resultMessage = cltMediaSourceService.findCltMediaSourceListByItemId(cltTaskItemVO.getItemId());
        List<CltMediaSource> cltMediaSourceList = (List<CltMediaSource>) resultMessage.getData();
        cltTaskItemVO.setCltMediaSourceList(cltMediaSourceList);
        return ResultMessage.success(cltTaskItemVO);
    }

    @Override
    public PageInfo<CltTaskItemVO> findAllTaskItemPageInfo(CltTaskItem cltTaskItem) {
        PageHelper.startPage(cltTaskItem.getPage(), cltTaskItem.getRows());
        List<CltTaskItemVO> cltTaskItemList = cltTaskItemMapper.findAllTaskItemList(cltTaskItem);
        return new PageInfo<CltTaskItemVO>(cltTaskItemList);
    }

    @Override
    public PageInfo<CltTaskItemVO> findTaskItemPageInfo(CltTaskItem cltTaskItem) {
        PageHelper.startPage(cltTaskItem.getPage(), cltTaskItem.getRows());
        List<CltTaskItemVO> cltTaskItemList = cltTaskItemMapper.findTaskItemList(cltTaskItem);
        return new PageInfo<CltTaskItemVO>(cltTaskItemList);
    }

    @Override
    public ResultMessage findTaskItemList(CltTaskItem cltTaskItem) {
        List<CltTaskItemVO> cltTaskItemList = cltTaskItemMapper.findTaskItemList(cltTaskItem);
        return ResultMessage.success(cltTaskItemList);
    }

    @Override
    public ResultMessage findAllTaskItemList(CltTaskItem cltTaskItem) {
        List<CltTaskItemVO> cltTaskItemList = cltTaskItemMapper.findAllTaskItemList(cltTaskItem);
        return ResultMessage.success(cltTaskItemList);
    }

    @Override
    public ResultMessage createTaskItem(CltTaskItemVO cltTaskItemVO) {
        if (StringUtils.isNotEmpty(cltTaskItemVO.getSurveyTimeStr())) {
            cltTaskItemVO.setSurveyTime(DateUtil.strToLocalDateTime(cltTaskItemVO.getSurveyTimeStr(), "yyyy-MM-dd HH:mm"));
        }

        //有itemId更新, 没itemId新增
        if (cltTaskItemVO.getItemId() != null) {
            cltTaskItemMapper.updateByPrimaryKeySelective(cltTaskItemVO);
        } else {
            cltTaskItemMapper.createTaskItem(cltTaskItemVO);
        }

        Integer itemId = cltTaskItemVO.getItemId();

        return ResultMessage.success(itemId);
    }

    /**
     * mui上传多个文件
     * 1.根据itemId删除此采集点下的所有媒体文件
     * 2.上传文件
     * 3.插入媒体文件信息到媒体表
     * 4.返回是否插入成功
     * @param
     * @return
     * @version <1> 2019/4/20 10:03 zhangshen:Created.
     */
    @Override
    public ResultMessage muiUploadFile(CltMediaSource cltMediaSource, String phone, Integer taskId, Integer itemId, HttpServletRequest request) {

        //1.根据itemId删除此采集点下的所有媒体文件
        cltMediaSourceService.deleteMediaSourceByItemId(itemId);


        List<ResultMessage> result = new ArrayList<ResultMessage>();


        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> fns = multipartRequest.getFileNames();//获取上传的文件列表
        //系统前缀
        String storage = CephUtils.getAbsolutePath("").replace("\\",File.separator);
        //媒体文件目录
        String mediaPath = CephUtils.getCephUrl("CLT_TASK_ITEM_MEDIA_STORAGE").replace("\\",File.separator) + File.separator + createMediaPath(phone, taskId, itemId) + File.separator;
        String tPath = storage + mediaPath;
        //不存在则创建
        File f = new File(tPath);
        if (!f.exists()) {
            f.mkdirs();
        }

        Integer index = 0;
        while (fns.hasNext()) {
            index += 1;

            try {

                String next = fns.next();
                MultipartFile file = multipartRequest.getFile(next);
                String originalFilename = file.getOriginalFilename();
                String path = tPath + originalFilename;

                //如果文件存在则删除
                File targetFile = new File(path);
                if (targetFile.exists()) {
                    targetFile.delete();
                }

                //2.上传文件
                file.transferTo(targetFile);

                String realPath = targetFile.getCanonicalPath();

                cltMediaSource.setItemId(itemId);
                cltMediaSource.setIndex(index);
                String sourcePath = mediaPath + originalFilename;
                String targetPath = realPath.replace("amr","mp3");

                //3、设置文件路径为新的路径
                cltMediaSource.setMediaPath(sourcePath.replace("amr","mp3"));

                String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
                String img = "png, jpg";
                String audio = "amr, mp3";
                if (img.indexOf(suffix) != -1) {
                    cltMediaSource.setMediaType(1);
                } else if (audio.indexOf(suffix) != -1) {
                    cltMediaSource.setMediaType(2);
                    amr2mp3(realPath,targetPath);
                }

                //3.插入媒体文件信息到媒体表
                cltMediaSourceService.cerateMediaSource(cltMediaSource);

                //4.返回是否插入成功
                result.add(ResultMessage.success(cltMediaSource));

            } catch (IOException e) {
                result.add(ResultMessage.fail());
                e.printStackTrace();
            }

        }

        return ResultMessage.success(result);
    }

    @Override
    public ResultMessage deleteTaskItemByItemId(Integer itemId) {
        cltTaskItemMapper.deleteByPrimaryKey(itemId);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage updateTaskItemByItemId(CltTaskItemVO cltTaskItem) {
        cltTaskItemMapper.updateByPrimaryKeySelective(cltTaskItem);
        return ResultMessage.success();
    }

    /**
     * 根据任务id和用户查看自己的采集点数据列表
     * @param
     * @return
     * @version <1> 2019/4/17 15:26 zhangshen:Created.
     */
    @Override
    public ResultMessage findTaskItemListSelf(CltTaskItem cltTaskItem) {
        List<CltTaskItemVO> cltTaskItemList = cltTaskItemMapper.findTaskItemListSelf(cltTaskItem);

        for (CltTaskItemVO vo : cltTaskItemList) {
            ResultMessage resultMessage = cltMediaSourceService.findCltMediaSourceListByItemId(vo.getItemId());
            List<CltMediaSource> cltMediaSourceList = (List<CltMediaSource>) resultMessage.getData();
            if (cltMediaSourceList != null && cltMediaSourceList.size() > 0 ) {
                vo.setCltMediaSourceList(cltMediaSourceList);
            }
        }

        return ResultMessage.success(cltTaskItemList);
    }

    /**
     * 拼接媒体路径： 15071450223/20190420/20/30/
     * @param
     * @return 
     * @version <1> 2019/4/20 10:49 zhangshen:Created.
     */
    private String createMediaPath(String phone, Integer taskId, Integer itemId) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String tableName = dateFormat.format(now);
        return phone + File.separator + tableName + File.separator + taskId + File.separator + itemId;
    }

    /**
     * amr转MP3
     * @param
     * @return
     * @version <1> 2019/5/7 mason:Created.
     */
    private void amr2mp3(String sourcePath,String targetPath){
        try {
            File source = new File(sourcePath);
            File target = new File(targetPath);

            //Audio Attributes
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(128000);
            audio.setChannels(2);
            audio.setSamplingRate(44100);

            //Encoding attributes
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat("mp3");
            attrs.setAudioAttributes(audio);

            //将amr文件转换并创建一个MP3文件
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs);

            //删除原先的amr文件
            File amrFile = new File(sourcePath);
            if (amrFile.exists()) {
                amrFile.delete();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
