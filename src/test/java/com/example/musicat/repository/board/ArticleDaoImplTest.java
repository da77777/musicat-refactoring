//package com.example.musicat.repository.board;
//
//import com.example.musicat.domain.board.ArticleVO;
//import com.example.musicat.domain.board.SelectArticleVO;
//import com.example.musicat.domain.board.TagVO;
//import com.example.musicat.mapper.board.ArticleMapper;
//import com.example.musicat.mapper.board.FileMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Slf4j
//@SpringBootTest
//class ArticleDaoImplTest {
//
//    @Autowired ArticleDaoImpl articleDao;
//    @Autowired FileMapper fm;
//    @Autowired ArticleMapper articleMapper;
//
//    @Test
//    public void selectArticle() throws Exception{
//        //given
//        int articleNo = 50;
//
//        //when
//        List<SelectArticleVO> findArticle = articleDao.selectArticle(articleNo);
//
//        //then
//        for (SelectArticleVO sa : findArticle) {
//            log.info(sa.toString());
//        }
//        Assertions.assertEquals(articleNo, findArticle.get(0).getArticle().getNo());
//    }
//
//    @Test
//    void selectBoard() {
//        fm.deleteFile(22);
//    }
//
//    @Test
//    void insertArticle() {
//        String[] aa = {"태그테1", "태그테2"};
//        articleDao.insertTags(69, aa);
//    }
//
//    @Test
//    void updateArticle() {
//    }
//
//    @Test
//    void deleteArticle() {
//    }
//
//    @Test
//    void upViewcount() {
//    }
//
//    @Test
//    void selectAllArticle() {
//        List<TagVO> tagVOS = articleMapper.selectArticleTags(69);
//        for (TagVO tagVO : tagVOS) {
//            log.info(tagVO.toString());
//        }
//    }
//
//    @Test
//    void insertLike() {
//        Map<String, Object> map = new HashMap<>();
////        map.put("boardNo", 3);
////        map.put("keyword", "subject");
////        map.put("content", "ㄱㄱ");
//        map.put("subject", "활동");
//        map.put("subject", "휴면");
//        List<ArticleVO> search = articleMapper.search(map);
//        for (ArticleVO articleVO : search) {
//            log.info("search============================");
//            log.info(articleVO.toString());
//            log.info("search============================");
//        }
//    }
//
//    @Test
//    void deleteLike() {
//    }
//
//    @Test
//    void totalRecCount() {
//    }
//
//    @Test
//    void likeCheck() {
//    }
//
//    @Test
//    void upLikecount() {
//    }
//
//    @Test
//    void downLikecount() {
//    }
//}