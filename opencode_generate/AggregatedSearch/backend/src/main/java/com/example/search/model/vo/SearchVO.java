package com.example.search.model.vo;

import com.example.search.model.entity.Picture;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 聚合搜索结果视图
 */
@Data
public class SearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<UserVO> userList;

    private List<PostVO> postList;

    private List<Picture> pictureList;

    /**
     * 单个类型搜索时的数据列表（List#records）
     */
    private List<Object> dataList;

    /**
     * 单个类型搜索时的总条数（分页用）
     */
    private long total;
}