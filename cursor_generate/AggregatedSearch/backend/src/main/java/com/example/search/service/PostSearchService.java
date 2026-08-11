package com.example.search.service;

import com.example.search.dto.PostSearchItemVO;
import com.example.search.dto.SearchPageVO;
import com.example.search.entity.Post;
import com.example.search.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostSearchService {

    private static final int SUMMARY_MAX_LENGTH = 120;

    private final PostRepository postRepository;

    public SearchPageVO<PostSearchItemVO> search(String keyword, int page, int size) {
        Page<Post> postPage = postRepository.searchByKeyword(keyword, PageRequest.of(page - 1, size));
        List<PostSearchItemVO> list = postPage.getContent().stream()
                .map(this::toVO)
                .toList();
        return new SearchPageVO<>("post", keyword, postPage.getTotalElements(), list);
    }

    private PostSearchItemVO toVO(Post post) {
        return new PostSearchItemVO(
                post.getId(),
                post.getTitle(),
                truncate(post.getContent()),
                post.getAuthorName(),
                post.getCoverUrl(),
                post.getCreatedAt()
        );
    }

    private String truncate(String content) {
        if (content == null) {
            return "";
        }
        if (content.length() <= SUMMARY_MAX_LENGTH) {
            return content;
        }
        return content.substring(0, SUMMARY_MAX_LENGTH) + "...";
    }
}
