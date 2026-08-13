package com.example.search.test;

import com.example.search.model.entity.Post;
import com.example.search.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Test1 {

    @Autowired
    private PostService postService;

    @Test
    public void testMysql(){
        List<Post> list = postService.list();
        System.out.println("查看post的文章");
        for (Post post : list){
            System.out.println(post);
        }
    }

}
