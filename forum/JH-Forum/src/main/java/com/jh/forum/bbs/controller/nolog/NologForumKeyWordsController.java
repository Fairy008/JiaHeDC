package com.jh.forum.bbs.controller.nolog;

import com.jh.forum.bbs.service.IForumKeyWordsService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "nolog查询搜索热词",description = "nolog查询搜索热词")
@RestController
@RequestMapping("/nolog/keyWords")
public class NologForumKeyWordsController {
    /*
     * 功能描述:获取搜索热词
     * @Param:
     * @Return:
     * @version<1>  2019/8/28  wangli :Created
     */
    @Autowired
    private IForumKeyWordsService forumKeyWordsService;

    @ApiOperation(value = "查询搜索热词",notes = "查询搜索热词")
    @PostMapping("/getKeyWords")
    public ResultMessage getKeyWords(){
        return forumKeyWordsService.getKeyWords();
    }
}
