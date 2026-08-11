package com.example.search.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.search.model.entity.Post;
import java.util.Date;
import java.util.List;

/**
 * 帖子数据库操作
 */
public interface PostMapper extends BaseMapper<Post> {

    /**
     * 查询帖子列表（包括已被删除的数据），用于增量同步到 ES
     */
    List<Post> listPostWithDelete(Date minUpdateTime);
}