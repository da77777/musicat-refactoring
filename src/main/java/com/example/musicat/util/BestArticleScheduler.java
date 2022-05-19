package com.example.musicat.util;

import com.example.musicat.domain.board.ArticleVO;
import com.example.musicat.repository.board.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class BestArticleScheduler {

    @Autowired ArticleDao articleDao;
    @Autowired BestArticle bestArticleUtil;

   @Scheduled(fixedDelay = 1000 * 604800) // 일주일(매주 월요일)?
    public void cronJobSch() {
        System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
        // best Table 비우기
        this.articleDao.deleteAllBestArticle();
        List<ArticleVO> bestArticles = this.articleDao.selectBestArticle();// 베스트글 조회
        bestArticleUtil.insertBestArticle(bestArticles);
    }


}
