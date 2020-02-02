package com.jh.forum;

import com.github.pagehelper.PageInfo;
import com.jh.forum.bbs.entity.ForumComment;
import com.jh.forum.bbs.service.IForumCommentService;
import com.jh.forum.bbs.vo.CommentVo;
import com.jh.vo.ResultMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @version<1> 2019-03-06 lcw :Created.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private IForumCommentService commentService;


    /**
     * 评论内容保存
     */
    @Test
    public void testSaveComment(){

        ForumComment comment = new ForumComment();

        comment.setArticleId(1);
        comment.setContent("这是评论内容");
        comment.setLevel("1");
        comment.setCreator(141); //15392952557
        comment.setCreatorName("刘兵");

        commentService.save(comment);
    }


    @Test
    public void testSaveCommentInFirstCommentId(){
        ForumComment comment = new ForumComment();
        comment.setArticleId(1);
        comment.setFirstCommentId(1);
        comment.setLevel("2");
        comment.setContent("这是二级评论内容 \\\\刘兵 这是评论内容");  //此内容是由前端页面组装的

        comment.setCreator(141); //15392952557
        comment.setCreatorName("刘兵");
        commentService.save(comment);
    }


    @Test
    public void testFndCommentsByArticleId(){
        Integer articleId = 1;
        CommentVo commentVo = new CommentVo();
        commentVo.setArticleId(articleId);
        PageInfo<CommentVo> pageInfos = commentService.findCommentsByPage(commentVo);
        System.out.println(pageInfos);
    }


    @Test
    public void testFindCommentsByFirstCommentId(){
        Integer firstCommentId = 1;
        ResultMessage result = commentService.findCommentsByFirstCommentId(1);
        System.out.println(result.isFlag());

    }



}

