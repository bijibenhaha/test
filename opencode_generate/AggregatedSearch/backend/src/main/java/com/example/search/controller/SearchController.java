package com.example.search.controller;

import com.example.search.common.BaseResponse;
import com.example.search.common.ResultUtils;
import com.example.search.manager.SearchFacade;
import com.example.search.model.dto.search.SearchRequest;
import com.example.search.model.vo.SearchVO;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 聚合搜索接口
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private SearchFacade searchFacade;

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchList(@RequestBody SearchRequest request) {
        SearchVO searchVO = searchFacade.searchList(request);
        return ResultUtils.success(searchVO);
    }
}