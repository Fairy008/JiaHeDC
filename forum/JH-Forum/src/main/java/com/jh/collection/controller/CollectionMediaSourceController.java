package com.jh.collection.controller;

import com.jh.collection.entity.CollectionMediaSource;
import com.jh.collection.service.ICollectionMediaSourceService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;

import static com.jh.collection.enums.MediaTypeEnum.*;

/**
 * 数据采集媒体资源Controller
 * @version <1> 2018-11-15 xy： Created.
 */
@Api(value = "采集媒体资源服务",description="采集媒体资源服务")
@RestController
@RequestMapping("/collection/media/")
public class CollectionMediaSourceController {

    private static final Logger LOG = LoggerFactory.getLogger(CollectionMediaSourceController.class);

    @Resource(name="collectionMediaSourceServiceImpl")
    private ICollectionMediaSourceService mediaSourceService;

    /**
     * 上传媒体资源文件
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public ResultMessage uplaod(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "soundTime", required = false)Integer soundTime) throws Exception {
        InputStream inputStream = file.getInputStream();
        byte[] pictureData = new byte[(int) file.getSize()];
        inputStream.read(pictureData);
//        String contentType = file.getContentType();
        String fileName= file.getOriginalFilename();
        CollectionMediaSource mediaSource = new CollectionMediaSource();
        mediaSource.setMediaBin(pictureData);
        if(StringUtils.isNotEmpty(fileName) && fileName.length()>3){
            mediaSource.setMediaType(fileName.substring(fileName.length()-3,fileName.length()));
        }
        mediaSource.setSoundTime(soundTime);
        ResultMessage resultMessage = mediaSourceService.addMediaSource(mediaSource);
        return resultMessage;
    }


    /**
     * 读取媒体资源文件
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public void getPhotoById (@PathVariable("id")Integer id, HttpServletResponse response) throws Exception{
        ResultMessage resultMessage = mediaSourceService.getMediaSource(id);
        if(!resultMessage.isFlag()){
            return;
        }
        CollectionMediaSource mediaSource = (CollectionMediaSource) resultMessage.getData();
        byte[] data = mediaSource.getMediaBin();
        if(mediaSource !=null && (StringUtils.equals(mediaSource.getMediaType(),MEDIA_PNG.getValue())
        || StringUtils.equals(mediaSource.getMediaType(),MEDIA_JPG.getValue()))){
            response.setContentType("image/jpeg");
        }else if(mediaSource !=null && StringUtils.equals(mediaSource.getMediaType(),MEDIA_AMR.getValue())){
//            LOG.info(">>>"+ ByteArrayUtils.getImageString(data));
            response.addHeader("Accept-Ranges", "bytes");
            response.addHeader("Content-Type", "audio/amr;charset=UTF-8");
        }
        response.setCharacterEncoding("UTF-8");
        OutputStream outputSream = response.getOutputStream();
        outputSream.write(data);
        outputSream.flush();
    }
    /**
     * 删除资源
     * @param id
     * @return
     * @version <1> 2018-12-11 xy： Created
     */
    @ApiOperation(value = "删除采集任务",notes = "删除采集任务")
    @PostMapping("delete")
    public ResultMessage delete( Integer id){
        return mediaSourceService.deleteMediaSource(id);
    }
}
