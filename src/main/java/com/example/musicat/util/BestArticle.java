package com.example.musicat.util;

import com.example.musicat.domain.board.ArticleVO;
import com.example.musicat.domain.music.Music;
import com.example.musicat.repository.board.ArticleDao;
import com.example.musicat.service.music.MusicApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class BestArticle {

    @Autowired private ArticleDao articleDao;
    @Autowired private MusicApiService musicApiService;

    public void insertBestArticle(List<ArticleVO> bestArticles) {
        HashMap<String, Object> map = new HashMap<>();
        String systemFileName;
        for (ArticleVO bestArticle : bestArticles) {
            map.put("articleNo", bestArticle.getNo());
            map.put("subject", bestArticle.getSubject());
            map.put("likecount", bestArticle.getLikecount());
            // 임시로 차단(data가 안 맞음)
            Map<String, Object> mmap = (Map<String, Object>) musicApiService.retrieveMusics(bestArticle.getNo()).get(0);
            List<Map<String, Object>> resmap = (List<Map<String, Object>>) mmap.get("links");
            Map<String, Object> aMap = resmap.get(1);

            log.info("resMape1: {}", aMap.get("href"));
            map.put("systemFileName", aMap.get("href"));

            this.articleDao.insertBestArticle(map);
            map.remove("articleNo");
            map.remove("subject");
            map.remove("likecount");
            map.remove("systemFileName");
        }
    }

}
