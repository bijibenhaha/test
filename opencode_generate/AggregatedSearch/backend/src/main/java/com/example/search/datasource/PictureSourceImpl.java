package com.example.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.search.model.entity.Picture;
import com.example.search.service.PictureService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 图片数据源（查询走 MySQL）
 */
@Service
@Slf4j
public class PictureSourceImpl implements DataSource<Picture> {

    @Resource
    private PictureService pictureService;

    @Override
    public Page<Picture> doSearch(String searchText, long pageNum, long pageSize) {
        return pictureService.searchPictures(searchText, pageNum, pageSize);
    }
}