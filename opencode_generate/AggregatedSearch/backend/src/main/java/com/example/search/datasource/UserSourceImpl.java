package com.example.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.search.model.dto.user.UserQueryRequest;
import com.example.search.model.vo.UserVO;
import com.example.search.service.UserService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户数据源（查询走 MySQL）
 */
@Service
@Slf4j
public class UserSourceImpl implements DataSource<UserVO> {

    @Resource
    private UserService userService;

    @Override
    public Page<UserVO> doSearch(String searchText, long pageNum, long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setCurrent((int) pageNum);
        userQueryRequest.setPageSize((int) pageSize);
        userQueryRequest.setUserName(searchText);
        return userService.getUserVOByPage(userQueryRequest);
    }
}