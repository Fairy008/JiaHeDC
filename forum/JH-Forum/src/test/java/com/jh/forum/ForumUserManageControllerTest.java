package com.jh.forum;

import com.jh.biz.feign.IPersonService;
import com.jh.vo.ResultMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ForumUserManageControllerTest {

    @Autowired
    IPersonService personService;

    @Test
    public void isExistForumRole() throws Exception {
        ResultMessage result=personService.isExistRole(141,"FORUM_ADMIN");
        System.out.println(result);
    }

}