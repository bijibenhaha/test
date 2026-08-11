package com.example.search.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.search.common.ErrorCode;
import com.example.search.exception.BusinessException;
import com.example.search.mapper.PictureMapper;
import com.example.search.model.entity.Picture;
import com.example.search.service.PictureService;
import com.example.search.utils.BingImageUtil;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

/**
 * 图片服务实现：通过爬取必应图片搜索来动态获取图片，不再局限于数据库有限数据
 */
@Service
@Slf4j
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {

    private static final String BING_IMAGE_BASE = "https://cn.bing.com";
    private static final String BING_IMAGE_SEARCH = "https://cn.bing.com/images/search?q=%s&form=BESBTB&first=%d";
    private static final String USER_AGENT =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 "
                    + "(KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
    /**
     * 爬虫无法得知真实总数，给一个足够大的值让前端分页可翻页
     */
    private static final long FAKE_TOTAL = 1000L;

    @Override
    public Page<Picture> searchPictures(String searchText, long pageNum, long pageSize) {
        long first = (pageNum - 1) * pageSize;
        String keyword;
        try {
            keyword = URLEncoder.encode(searchText, "UTF-8");
        } catch (Exception e) {
            log.error("关键词编码失败", e);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "搜索词非法");
        }
        String url = String.format(BING_IMAGE_SEARCH, keyword, first);
        log.info("抓取必应图片搜索, url={}", url);

        Document doc;
        try {
            doc = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .timeout(10000)
                    .get();
        } catch (IOException e) {
            log.error("抓取必应图片失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "图片数据获取异常");
        }

        Elements elements = doc.select(".iuscp.isv.smallheight");
        List<Picture> pictures = new ArrayList<>();
        int index = 0;
        for (Element element : elements) {
            if (pictures.size() == pageSize) {
                break;
            }
            try {
                // 拿到的 href 是必应图片搜索链接，需解码出真实图片地址
                Element iusc = element.selectFirst(".iusc");
                Element inflnk = element.selectFirst(".inflnk");
                if (iusc == null || inflnk == null) {
                    continue;
                }
                String picLink = BING_IMAGE_BASE + iusc.attr("href");
                String realUrl = BingImageUtil.extractRealImageUrl(picLink);
                String picName = inflnk.attr("aria-label");
                if (realUrl == null || realUrl.isEmpty()) {
                    continue;
                }
                Picture picture = new Picture();
                // 合成 id，仅用于前端列表 key
                picture.setId((long) ++index);
                picture.setUrl(realUrl);
                picture.setName(Objects.toString(picName, ""));
                picture.setDescription("");
                pictures.add(picture);
            } catch (Exception ignored) {
                log.debug("跳过一条解析失败的图片", ignored);
            }
        }

        Page<Picture> page = new Page<>(pageNum, pageSize);
        page.setRecords(pictures);
        page.setTotal(FAKE_TOTAL);
        return page;
    }
}