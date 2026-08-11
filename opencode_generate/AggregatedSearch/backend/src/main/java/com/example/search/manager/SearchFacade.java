package com.example.search.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.search.common.ErrorCode;
import com.example.search.datasource.DataSource;
import com.example.search.datasource.PictureSourceImpl;
import com.example.search.datasource.PostSourceImpl;
import com.example.search.datasource.UserSourceImpl;
import com.example.search.exception.ThrowUtils;
import com.example.search.model.dto.search.SearchRequest;
import com.example.search.model.enums.SearchTypeEnum;
import com.example.search.model.vo.SearchVO;
import java.util.HashMap;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 聚合搜索门面
 * type 为空：一次返回三类；type 指定：只查对应数据源
 */
@Component
public class SearchFacade {

    @Resource
    private PictureSourceImpl pictureSourceImpl;

    @Resource
    private PostSourceImpl postSourceImpl;

    @Resource
    private UserSourceImpl userSourceImpl;

    public SearchVO searchList(SearchRequest request) {
        String type = request.getType();
        String searchText = request.getSearchText();
        int current = request.getCurrent();
        int size = request.getPageSize();
        SearchVO searchVO = new SearchVO();

        // 第一种情况：type 为空，返回所有类型
        if (StringUtils.isBlank(type)) {
            searchVO.setPictureList(pictureSourceImpl.doSearch(searchText, current, size).getRecords());
            searchVO.setUserList(userSourceImpl.doSearch(searchText, current, size).getRecords());
            searchVO.setPostList(postSourceImpl.doSearch(searchText, current, size).getRecords());
            return searchVO;
        }

        // 第二种情况：type 非法
        SearchTypeEnum enumByValue = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(enumByValue == null, ErrorCode.PARAMS_ERROR, "搜索类型不存在");

        // 第三种情况：type 合法，只查某个类型（map 选择，避免 switch）
        HashMap<String, DataSource> sourceHashMap = new HashMap<>();
        sourceHashMap.put(SearchTypeEnum.POST.getValue(), postSourceImpl);
        sourceHashMap.put(SearchTypeEnum.PICTURE.getValue(), pictureSourceImpl);
        sourceHashMap.put(SearchTypeEnum.USER.getValue(), userSourceImpl);
        DataSource dataSource = sourceHashMap.get(type);
        Page page = dataSource.doSearch(searchText, current, size);
        searchVO.setDataList(page.getRecords());
        searchVO.setTotal(page.getTotal());
        return searchVO;
    }
}