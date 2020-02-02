package com.jh.forum.bbs.controller;

import com.jh.forum.bbs.service.IForumKeyWordsService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "搜索热词",description = "搜索热词")
@RestController
@RequestMapping("/keyWords")
public class ForumKeyWordsController {

    @Autowired
    private IForumKeyWordsService forumKeyWordsService;

    @ApiOperation(value = "查询搜索热词",notes = "查询搜索热词")
    @PostMapping("/getKeyWords")
    public ResultMessage getKeyWords(){
        return forumKeyWordsService.getKeyWords();
    }
}
