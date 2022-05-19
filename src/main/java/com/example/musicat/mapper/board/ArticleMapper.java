package com.example.musicat.mapper.board;

import java.util.List;
import java.util.Map;

import com.example.musicat.domain.board.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {
	
	//게시글 세부 조회
	List<SelectArticleVO> selectArticle(int articleNo);

	//앞 뒤 게시글 조회
	List<ArticleVO> selectSubArticle(int articleNo);


	List<ArticleVO> selectBoard(int boardNo);

	//게시판 별 게시글 목록 조회
	List<ArticleVO>selectBoardList(Map<String, Object> map);

	int boardTotalCount(int boardNo);

	//등급 게시판 조회
	List<GradeArticleVO> selectGradeArticles();


	//게시글 등록
	void insertArticle(ArticleVO article);

	//게시글 수정
	void updateArticle(ArticleVO article);

	//게시글 삭제
	void deleteArticle(int articleNo);

	//조회수 증가
	void upViewcount(int aritlceNo);

	//전체글 조회
	List<ArticleVO> selectAllArticle();

	//추천 여부 체크
	int likeCheck(ArticleVO articleVO);

	//추천 (bestArticle Table)
	void insertLike(ArticleVO articleVO);

	//추천 취소(bestArticle Table)
	void deleteLike(ArticleVO articleVO);

	//게시글의 총 추천 수 조회
	int totalLikeCount(int articleNo);

	//추천 (Article Table)
	void upLikecount(int articleNo);

	//추천 취소 (Article Table)
	void downLikecount(int articleNo);

	//태그 등록
	void insertTag(TagVO tagVO);

	//게시글의 모든 태그 조회
	List<TagVO> selectArticleTags(int articleNo);

	//태그 삭제
	void deleteTag(int tagNo);

	//검색
	List<ArticleVO> search(Map<String, Object> map);

	//베스트글 조회
	List<ArticleVO> selectBestArticle();

	//베스트글 Table 비우기
	void deleteAllBestArticle(); // BestArticle 테이블 비우기

	//베스트글 Table Insert
	void insertBestArticle(Map<String, Object> map);

	//베스트글 선정을 위한 현재 날짜 조회
	int selectNowDate(); // 현재 날짜 조회

	//베스트글에 속한 게시글이 삭제됐을 때 새로운 베스트글 게시글 조회
	List<ArticleVO> selectUpdateBestArticle(int now);

	//게시글 삭제시 게시글이 베스트 게시글에 속해 있는지 확인
	boolean checkBestArticle(int articleNo);

	//베스트글 모두 조회
	List<BestArticleVO> selectAllBestArticle();

	//작성한 게시글 조회
	List<ArticleVO> selectMyArticle(int memberNo);

	//추천 누른 게시글 조회
	List<ArticleVO> selectMyLikeArticle(int memberNo);


	void inserGradeArticle(GradeArticleVO gradeArticleVO);

	void deleteGradeArticle(Integer articleNo);
}
