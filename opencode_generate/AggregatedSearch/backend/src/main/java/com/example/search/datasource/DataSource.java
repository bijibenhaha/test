package com.example.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 数据源适配器接口
 * 以后新增的数据源，只要实现这个接口即可接入聚合搜索
 */
public interface DataSource<T> {

    Page<T> doSearch(String searchText, long pageNum, long pageSize);
}