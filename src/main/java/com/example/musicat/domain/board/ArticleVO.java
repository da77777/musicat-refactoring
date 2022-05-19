package com.example.musicat.domain.board;

import com.example.musicat.controller.form.ArticleForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Alias("articleVo")
public class ArticleVO {

    private int no;
    private int memberNo;
    private int boardNo;
    private String nickname; // 회원명
    private String subject; // 제목
    private String content; // 내용
    private String writeDate; // 작성일
    private int viewcount; // 조회수
    private int likecount; // 추천수
    private int displayNotice;
    private List<FileVO> fileList = new ArrayList<>(); // 이미지 List
    private List<ReplyVO> replyList = new ArrayList<>(); // 댓글
    private FileVO attacheFile; //첨부 파일
    private FileVO thumbnail; // 썸네일
    private String[] tagList;
    private List<TagVO> selectTags = new ArrayList<>();
    private int likeCheck;


    //==생성 메소드==//
    public static ArticleVO createArticle(int memberNo, String nickname, ArticleForm form, FileVO attacheFile, List<FileVO> imageFiles) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.memberNo = memberNo;
        articleVO.boardNo = form.getBoardNo();
        articleVO.nickname = nickname;
        articleVO.subject = form.getSubject();
        articleVO.content = form.getContent();
        articleVO.attacheFile = attacheFile;
        articleVO.fileList = imageFiles;
        return articleVO;
    }


    //==비즈니스 로직==//
    //게시글 수정
    public static void updateArticle(ArticleVO article,int articleNo, ArticleForm form, FileVO attacheFile, List<FileVO> imageFiles) {
        article.setNo(articleNo);
        article.setBoardNo(form.getBoardNo());
        article.setSubject(form.getSubject());
        article.setContent(form.getContent());
        article.setDisplayNotice(form.getDisplayNotice());
        article.setAttacheFile(attacheFile);
        article.setFileList(imageFiles);
    }

    public static ArticleVO addReplyAndLike(ArticleVO article, int likeCheck, List<ReplyVO> replys, int totalCount) {
        article.setLikeCheck(likeCheck);
        article.setReplyList(replys);
        article.setLikecount(totalCount);
        return article;
    }


    @Override
    public String toString() {
        return "Article{" +
                "no=" + no +
                ", memberNo=" + memberNo +
                ", boardNo=" + boardNo +
                ", nickname='" + nickname + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", writedate='" + writeDate + '\'' +
                ", viewcount=" + viewcount +
                ", likecount=" + likecount +
                ", displayNotice=" + displayNotice +
                ", fileList=" + fileList +
                ", replyList=" + replyList +
                ", likeCheck=" + likeCheck +
                '}';
    }

    public ArticleVO() {
    }
}
