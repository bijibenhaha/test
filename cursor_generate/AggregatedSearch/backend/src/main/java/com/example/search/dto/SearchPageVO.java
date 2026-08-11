package com.example.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchPageVO<T> {

    private String type;
    private String keyword;
    private long total;
    private List<T> list;
}
