package com.example.search.controller;

import com.example.search.common.Result;
import com.example.search.dto.SearchPageVO;
import com.example.search.service.PictureSearchService;
import com.example.search.service.PostSearchService;
import com.example.search.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private static final int MAX_KEYWORD_LENGTH = 50;

    private final PostSearchService postSearchService;
    private final PictureSearchService pictureSearchService;
    private final UserSearchService userSearchService;

    @GetMapping
    public Result<?> search(
            @RequestParam String keyword,
            @RequestParam String type,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        String trimmed = keyword == null ? "" : keyword.trim();
        if (trimmed.isEmpty()) {
            return Result.fail("keyword cannot be empty");
        }
        if (trimmed.length() > MAX_KEYWORD_LENGTH) {
            return Result.fail("keyword too long");
        }
        if (page < 1 || size < 1 || size > 50) {
            return Result.fail("invalid page or size");
        }

        return switch (type) {
            case "post" -> Result.success(postSearchService.search(trimmed, page, size));
            case "picture" -> Result.success(pictureSearchService.search(trimmed, page, size));
            case "user" -> Result.success(userSearchService.search(trimmed, page, size));
            default -> Result.fail("invalid type, must be post, picture or user");
        };
    }
}
