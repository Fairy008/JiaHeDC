package com.jh.forum;

import com.github.pagehelper.PageInfo;
import com.jh.forum.bbs.entity.ForumComment;
import com.jh.forum.bbs.service.IForumArticleService;
import com.jh.forum.bbs.service.IForumCommentService;
import com.jh.forum.bbs.vo.ArticleVO;
import com.jh.forum.bbs.vo.CommentVo;
import com.jh.util.JsonUtils;
import com.jh.vo.ResultMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @version<1> 2019-03-06 xiayong :Created.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private IForumArticleService forumArticleService;


    /**
     * 报告保存
     */
    @Test
    public void testSaveArticle(){
        System.out.println("测试分页查询");
        ArticleVO articleVo = new ArticleVO();
        articleVo.setArticleType(11001);
        articleVo.setArticleTitle("这是一个测试报告");
        articleVo.setArticleStatus(12004);
        forumArticleService.save(articleVo);
    }


    /**
     * 分页测试
     */
    @Test
    public void testGetListByCombination(){
        System.out.println("测试分页查询");
        ArticleVO articleVo = new ArticleVO();
        articleVo.setArticleLabel("");
        articleVo.setKeyWords("");
        articleVo.setArticleType(11001);
        articleVo.setRows(1);
        articleVo.setPage(10);
        PageInfo<ArticleVO> pageInfo = forumArticleService.getListByCombination(articleVo);
        System.out.println(JsonUtils.objectToJson(pageInfo));
    }

    /**
     * 热门报告测试 换一批
     */
    @Test
    public void testFindHotArticleList(){
        System.out.println("测试最热报告查询");
        Integer articleType = 11001;
        ResultMessage result = forumArticleService.getHotArticleList(articleType, null);
        System.out.println(JsonUtils.objectToJson(result));

    }

    /**
     * 最新报告测试 换一批
     */
    @Test
    public void testFindNewArticleList(){
        System.out.println("测试最新报告查询");
        Integer articleType = 11001;
        ResultMessage result = forumArticleService.getNewArticleList(articleType, null);
        System.out.println(JsonUtils.objectToJson(result));
    }



}

