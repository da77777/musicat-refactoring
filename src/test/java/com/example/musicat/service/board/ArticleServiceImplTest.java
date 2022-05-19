package com.example.musicat.service.board;

import com.example.musicat.domain.board.ArticleVO;
import com.example.musicat.domain.board.BoardBoardGradeVO;
import com.example.musicat.mapper.board.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ArticleServiceImplTest {

    @Autowired ArticleServiceImpl articleService;
    @Autowired
    ArticleMapper articleMapper;

    @Autowired BoardService boardService;

    @Test
    public void insert() throws Exception{
        //given
        int a = 20;
        int b = a /16;
        System.out.println("b = " + b);
        //when
        //then
    }


    @Test
    public void retrieveArticle() throws Exception{
        //given
        int articleNo = 50;

        //when
        ArticleVO fa = articleService.retrieveArticle(articleNo);

        //then
        log.info(fa.toString());
        Assertions.assertEquals(articleNo, fa.getNo());
    }

//    @Test
//    public void board() throws Exception{
//        given
//        BoardBoardGradeVO boardBoardGradeVO = boardService.retrieveOneBoard(2);
//        when
//        int writeGrade = boardBoardGradeVO.getBoardGradeVo().getWriteGrade();
//        then
//        Assertions.assertEquals(3, writeGrade);
//    }
}