package com.example.search.service;

import com.example.search.dto.PictureSearchItemVO;
import com.example.search.dto.SearchPageVO;
import com.example.search.entity.Picture;
import com.example.search.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PictureSearchService {

    private final PictureRepository pictureRepository;

    public SearchPageVO<PictureSearchItemVO> search(String keyword, int page, int size) {
        Page<Picture> picturePage = pictureRepository.searchByKeyword(keyword, PageRequest.of(page - 1, size));
        List<PictureSearchItemVO> list = picturePage.getContent().stream()
                .map(this::toVO)
                .toList();
        return new SearchPageVO<>("picture", keyword, picturePage.getTotalElements(), list);
    }

    private PictureSearchItemVO toVO(Picture picture) {
        return new PictureSearchItemVO(
                picture.getId(),
                picture.getTitle(),
                picture.getDescription(),
                picture.getThumbnailUrl(),
                picture.getWidth(),
                picture.getHeight(),
                picture.getCreatedAt()
        );
    }
}
