package com.example.search.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.search.model.entity.Picture;

/**
 * 图片服务
 */
public interface PictureService {

    /**
     * 按关键词分页搜索图片（MySQL）
     */
    Page<Picture> searchPictures(String searchText, long pageNum, long pageSize);
}