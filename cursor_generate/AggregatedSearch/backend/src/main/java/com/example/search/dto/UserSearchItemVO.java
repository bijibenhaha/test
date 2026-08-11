package com.example.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchItemVO {

    private Long id;
    private String username;
    private String nickname;
    private String bio;
    private String avatarUrl;
    private Integer followerCount;
    private LocalDateTime createdAt;
}
