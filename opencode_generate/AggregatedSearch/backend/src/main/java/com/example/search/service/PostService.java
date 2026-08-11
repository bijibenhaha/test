package com.example.search.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.search.model.dto.post.PostQueryRequest;
import com.example.search.model.entity.Post;
import com.example.search.model.vo.PostVO;
import javax.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 帖子服务
 */
public interface PostService extends IService<Post> {

    /**
     * 获取查询条件
     */
    QueryWrapper<Post> getQueryWrapper(PostQueryRequest postQueryRequest);

    /**
     * 从 ES 查询
     */
    Page<Post> searchFromEs(PostQueryRequest postQueryRequest);

    /**
     * 获取帖子封装
     */
    PostVO getPostVO(Post post, HttpServletRequest request);

    /**
     * 分页获取帖子封装
     */
    Page<PostVO> getPostVOPage(Page<Post> postPage, HttpServletRequest request);
}