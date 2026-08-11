package com.example.search.model.dto.search;

import com.example.search.common.PageRequest;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 聚合查询请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 某个 tab 类型：Post / Picture / User
     */
    private String type;
}