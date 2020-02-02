package com.jh.forum.bbs.controller.nolog;

import com.github.pagehelper.PageInfo;
import com.jh.forum.bbs.Enum.ArticleStatusEnum;
import com.jh.forum.bbs.service.IForumDownloadDataService;
import com.jh.forum.bbs.vo.DownloadDataVo;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @version<1> 2019-07-05 lcw :Created.
 */

@RestController
@RequestMapping("/nolog/downloadData")
public class NologDownloadDataController {


    @Autowired
    private IForumDownloadDataService downloadDataService;
    @GetMapping("/findHotDataList")
    public ResultMessage findHotDataList(){
        ResultMessage result = downloadDataService.findHotDataList();
        return result;
    }


    /**
     * 分页查询所有可下载数据列表
     * @param downloadDataVo
     * @return
     */
    @PostMapping("/findByPage")
    public PageInfo<DownloadDataVo> findByPage(@RequestBody DownloadDataVo downloadDataVo){
        downloadDataVo.setPushStatus(ArticleStatusEnum.ARTICLE_STATUS_PUBLISHED.getId());
        PageInfo<DownloadDataVo> result = downloadDataService.findByPage(downloadDataVo);
        return  result;
    }


    /**
     * 根据ID查询可下载数据记录行
     * @param dataId
     * @return
     */
    @GetMapping("/getById")
    public ResultMessage getById(Integer dataId){

        ResultMessage result = downloadDataService.findById(dataId);

        return result;
    }


    /**
     * 分页查询所有可下载数据列表
     * @param downloadDataVo
     * @return
     */
    @PostMapping("/findAllByPage")
    public PageInfo<DownloadDataVo> findAllByPage(@RequestBody(required=false) DownloadDataVo downloadDataVo){
        if(downloadDataVo.getTagType() !=null && downloadDataVo.getTagType()!=0){
            downloadDataVo.setKeyWords("");
        }
        downloadDataVo.setPushStatus(ArticleStatusEnum.ARTICLE_STATUS_PUBLISHED.getId());
        PageInfo<DownloadDataVo> result = downloadDataService.findAllByPage(downloadDataVo);
        return  result;
    }


    /**
     * 分页查询所有可下载数据列表
     * @param downloadDataVo
     * @return
     */
    @PostMapping("/findAgriculturalData")
    public ResultMessage findAgriculturalData(@RequestBody(required=false) DownloadDataVo downloadDataVo){
        if(downloadDataVo.getTagType() !=null &&  downloadDataVo.getTagType()!=0){
            downloadDataVo.setKeyWords("");
        }
        downloadDataVo.setPushStatus(ArticleStatusEnum.ARTICLE_STATUS_PUBLISHED.getId());
        ResultMessage result = downloadDataService.findAgriculturalData(downloadDataVo);
        return  result;
    }


    /**
     * 根据类别查询类似的数据
     * @param classify
     * @return
     */
    @GetMapping("/getSimilarData")
    public ResultMessage getSimilarData(Integer classify){

        ResultMessage result = downloadDataService.getSimilarData(classify);

        return result;
    }
}
