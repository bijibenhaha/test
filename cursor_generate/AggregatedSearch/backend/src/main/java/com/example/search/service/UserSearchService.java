package com.example.search.service;

import com.example.search.dto.SearchPageVO;
import com.example.search.dto.UserSearchItemVO;
import com.example.search.entity.User;
import com.example.search.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSearchService {

    private final UserRepository userRepository;

    public SearchPageVO<UserSearchItemVO> search(String keyword, int page, int size) {
        Page<User> userPage = userRepository.searchByKeyword(keyword, PageRequest.of(page - 1, size));
        List<UserSearchItemVO> list = userPage.getContent().stream()
                .map(this::toVO)
                .toList();
        return new SearchPageVO<>("user", keyword, userPage.getTotalElements(), list);
    }

    private UserSearchItemVO toVO(User user) {
        return new UserSearchItemVO(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getBio(),
                user.getAvatarUrl(),
                user.getFollowerCount(),
                user.getCreatedAt()
        );
    }
}
