//package com.example.search.job.once;
//
//import cn.hutool.core.collection.CollUtil;
//import com.example.search.esdao.PostEsDao;
//import com.example.search.mapper.PostMapper;
//import com.example.search.model.dto.post.PostEsDTO;
//import com.example.search.model.entity.Post;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//import javax.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
///**
// * 启动时全量同步帖子到 ES
// */
//@Component
//@Slf4j
//public class FullSyncPostToEs implements CommandLineRunner {
//
//    @Resource
//    private PostMapper postMapper;
//
//    @Resource
//    private PostEsDao postEsDao;
//
//    @Override
//    public void run(String... args) {
//        // 全量同步，包含逻辑删除的帖子，保证删除状态同步到 ES
//        List<Post> postList = postMapper.listPostWithDelete(new Date(0));
//        if (CollUtil.isEmpty(postList)) {
//            log.info("FullSyncPostToEs skip, no post");
//            return;
//        }
//        List<PostEsDTO> postEsDTOList = postList.stream()
//                .map(PostEsDTO::objToDto)
//                .collect(Collectors.toList());
//        final int pageSize = 500;
//        int total = postEsDTOList.size();
//        log.info("FullSyncPostToEs start, total {}", total);
//        for (int i = 0; i < total; i += pageSize) {
//            int end = Math.min(i + pageSize, total);
//            log.info("sync from {} to {}", i, end);
//            postEsDao.saveAll(postEsDTOList.subList(i, end));
//        }
//        log.info("FullSyncPostToEs end, total {}", total);
//    }
//}