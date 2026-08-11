package com.example.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PictureSearchItemVO {

    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private Integer width;
    private Integer height;
    private LocalDateTime createdAt;
}
