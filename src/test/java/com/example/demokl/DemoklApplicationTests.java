package com.example.demokl;

import com.example.demokl.dao.DiscussPostMapper;
import com.example.demokl.entity.DiscussPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
@ContextConfiguration(classes =DemoklApplication.class)
class DemoklApplicationTests {

    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Test
    public void testSelectPosts() {
        List<DiscussPost> list=discussPostMapper.selectDiscussPosts(0,0,3);
        for (DiscussPost post:list){
            System.out.println(post);
        }

        int a=discussPostMapper.selectDiscussPostRows(0);
        System.out.println(a);
    }

}
