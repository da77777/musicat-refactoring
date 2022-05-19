package com.example.musicat.controller.form;


import com.example.musicat.controller.ValidationGroups;
import com.example.musicat.domain.board.ArticleVO;
import com.example.musicat.domain.board.BoardVO;
import com.example.musicat.domain.board.FileVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

// 게시글 작성

@Getter
@Setter
//@NoArgsConstructor
public class ArticleForm {

    @Min(value = 1,message = "게시판을 선택해 주세요", groups = {Default.class})
    private int boardNo;

    @NotBlank(message = "제목을 입력해 주세요, 최대 16글자까지 가능합니다.", groups = {Default.class, ValidationGroups.NotBlankGroup.class})
    @Size(min = 0, max = 16, message = "입력은 최대 16글자까지 가능합니다.", groups = {Default.class, ValidationGroups.NotBlankGroup.class,ValidationGroups.SizeCheckGroup.class})
    private String subject;

    @NotBlank(message = "내용을 입력해 주세요, 최대 666글자까지 가능합니다.", groups = {Default.class,ValidationGroups.NotBlankGroup.class})
    @Size(min = 0, max = 666, message = "입력은 최대 666글자까지 가능합니다.", groups = {Default.class, ValidationGroups.NotBlankGroup.class,ValidationGroups.SizeCheckGroup.class})
    private String content;

    private List<BoardVO> boardList = new ArrayList<>(); //작성 게시글 목록
    private int displayNotice; //공지 설정


    private Long fileNo;
    private MultipartFile importAttacheFile;
    private List<MultipartFile> imageFiles;

    private FileVO attacheFile;
    private List<FileVO> fileList = new ArrayList<>();


    public static ArticleForm updateArticle(ArticleVO article){
        ArticleForm form = new ArticleForm();
        form.setSubject(article.getSubject());
        form.setBoardNo(article.getBoardNo());
        form.setContent(article.getContent());
        form.setDisplayNotice(article.getDisplayNotice());
        return form;
    }


    public ArticleForm() {
    }


}
