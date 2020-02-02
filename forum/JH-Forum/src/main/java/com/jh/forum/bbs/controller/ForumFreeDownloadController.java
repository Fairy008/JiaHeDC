package com.jh.forum.bbs.controller;

import com.jh.forum.bbs.entity.ForumFreeDownload;
import com.jh.forum.bbs.service.IForumDownloadDataService;
import com.jh.forum.bbs.service.IForumFreeDownloadService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "免费下载次数",description = "免费下载次数")
@RestController
@RequestMapping("/freeDownload")
public class ForumFreeDownloadController {

    @Autowired
    private IForumFreeDownloadService forumFreeDownloadService;

    @Autowired
    private IForumDownloadDataService forumDownloadDataService;

    @PostMapping("save")
    public ResultMessage save( @RequestBody ForumFreeDownload forumFreeDownload){

        ResultMessage result = forumFreeDownloadService.insertSelective(forumFreeDownload);
        //更新数据总下载数量
        forumDownloadDataService.downloadStatistc(forumFreeDownload.getDataId());
        return result;
    }

    @PostMapping("countFree")
    public ResultMessage countFree( @RequestParam Integer userId){

        ResultMessage result = forumFreeDownloadService.countFree(userId);

        return result;
    }


}
