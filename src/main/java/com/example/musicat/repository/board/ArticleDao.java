package com.example.musicat.repository.board;

import java.util.List;
import java.util.Map;

import com.example.musicat.domain.board.*;

public interface ArticleDao {

	//게시글 세부 조회
	List<SelectArticleVO> selectArticle(int articleNo);

	//앞 뒤 게시글 조회
	List<ArticleVO> selectSubArticle(int articleNo);

	List<ArticleVO> selectBoard(int boardNo);

	//게시판 별 게시글 목록 조회
	List<ArticleVO>selectBoardList(Map<String, Object> map);

	int boardTotalCount(int boardNo);


	//게시글 등록
	void insertArticle(ArticleVO article);

	//게시글 수정
	void updateArticle(ArticleVO article);

	//게시글 삭제
	void deleteArticle(int articleNo);

	//조회수 증가
	void upViewcount(int articleNo);

	//전체글 조회
	List<ArticleVO> selectAllArticle();

	//추천 여부 체크
	int likeCheck(ArticleVO articleVO);

	//추천(ArticleRecommend Table)
	void insertLike(ArticleVO articleVO);

	//추천 삭제(ArticleRecommend Table)
	void deleteLike(ArticleVO articleVO);

	//게시글의 총 추천 수 조회
	int totalRecCount(int totalRecCount);

	//추천수 증가(Article Table)
	void upLikecount(int articleNo);

	//추천수 감소(Article Table)
	void downLikecount(int articleNo);

	//태그 추가
	void insertTags(int articleNo, String[] tagList);

	//게시글의 모든 태그 조회
	List<TagVO> selectArticleTags(int articleNo);

	//태그 삭제
	void deleteTag(int tagNo);

	//검색
	List<ArticleVO> search(Map<String, Object> map);

	//베스트글 조회
	List<ArticleVO> selectBestArticle();

	//베스트글 전체 삭제(BestArticle Table)
	void deleteAllBestArticle();

	//베스트글 등록(BesatArticle Table)
	void insertBestArticle(Map<String, Object> map);

	//지난주 베스트글 조회를 위한 현재 날짜 조회
	int selectNowDate();

	//현재 날짜를 활용해 베스트글 뽑아오기
	List<ArticleVO> selectUpdateBestArticle(int now);

	//게시글 삭제 시 베스트글에 등록되어 있는지 확인
	boolean checkBestArticle(int articleNo);

	//베스트글 모두 조회(BestArticle Table)
	List<BestArticleVO> selectAllBestArticle();

	//작성한 게시글 조회
	List<ArticleVO> selectMyArticle(int memberNo);

	//추천 누른 게시글 조회
	List<ArticleVO> selectMyLikeArticle(int memberNo);


    void insertGradeArticle(GradeArticleVO gradeArticleVO);

	void deleteGradeArticle(Integer articleNo);
}
