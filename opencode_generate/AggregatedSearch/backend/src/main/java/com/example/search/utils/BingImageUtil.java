package com.example.search.utils;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * 必应图片搜索工具类
 */
public class BingImageUtil {

    /**
     * 从必应图片搜索链接（mediaurl 参数）中提取并解码真实的图片地址
     */
    public static String extractRealImageUrl(String bingSearchUrl) {
        if (bingSearchUrl == null || bingSearchUrl.isEmpty()) {
            return "";
        }
        try {
            URI uri = new URI(bingSearchUrl);
            String query = uri.getQuery();
            if (query != null && query.contains("mediaurl=")) {
                for (String param : query.split("&")) {
                    if (param.startsWith("mediaurl=")) {
                        String encodedMediaUrl = param.substring("mediaurl=".length());
                        // 必应通常进行了两次编码，先解一次
                        String decodedUrl = URLDecoder.decode(encodedMediaUrl, StandardCharsets.UTF_8.name());
                        // 若仍含百分号说明被二次编码，再解一次
                        if (decodedUrl.contains("%")) {
                            decodedUrl = URLDecoder.decode(decodedUrl, StandardCharsets.UTF_8.name());
                        }
                        return decodedUrl;
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return bingSearchUrl;
    }
}