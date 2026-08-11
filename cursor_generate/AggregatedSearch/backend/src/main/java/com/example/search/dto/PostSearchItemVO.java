package com.example.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchItemVO {

    private Long id;
    private String title;
    private String summary;
    private String authorName;
    private String coverUrl;
    private LocalDateTime createdAt;
}
