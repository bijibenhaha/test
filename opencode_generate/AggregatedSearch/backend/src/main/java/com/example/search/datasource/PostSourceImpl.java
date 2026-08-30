package com.example.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.search.model.dto.post.PostQueryRequest;
import com.example.search.model.entity.Post;
import com.example.search.model.vo.PostVO;
import com.example.search.service.PostService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 帖子数据源（查询走 Elasticsearch）
 */
@Service
@Slf4j
public class PostSourceImpl implements DataSource<PostVO> {

    @Resource
    private PostService postService;

    @Override
    public Page<PostVO> doSearch(String searchText, long pageNum, long pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setCurrent((int) pageNum);
        postQueryRequest.setPageSize((int) pageSize);
        // 从 ES 获取 post
        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        // 从 mysql 获取 post
        return postService.getPostVOPage(postPage, null);
    }
}