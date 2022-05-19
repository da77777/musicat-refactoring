package com.example.musicat.repository.board;

import java.util.List;
import java.util.Map;

import com.example.musicat.domain.board.*;
import com.example.musicat.mapper.board.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("articleDao")
public class ArticleDaoImpl implements ArticleDao {

	private ArticleMapper articleMapper;
	
	@Autowired
	public ArticleDaoImpl(ArticleMapper articleMapper) {
		this.articleMapper = articleMapper;
	}

	@Override
	public List<ArticleVO> selectSubArticle(int articleNo) {return this.articleMapper.selectSubArticle(articleNo);}

	@Override
	public List<SelectArticleVO> selectArticle(int articleNo) {
		return this.articleMapper.selectArticle(articleNo);
	}

	@Override
	public List<ArticleVO> selectBoard(int boardNo) {
		return this.articleMapper.selectBoard(boardNo);
	}

	@Override
	public List<ArticleVO>selectBoardList(Map<String, Object> map){
		return this.articleMapper.selectBoardList(map);
	}

	@Override
	public int boardTotalCount(int boardNo){
		return this.articleMapper.boardTotalCount(boardNo);
	}

	@Override
	public void insertGradeArticle(GradeArticleVO gradeArticleVO) {
		this.articleMapper.inserGradeArticle(gradeArticleVO);
	}

	@Override
	public void deleteGradeArticle(Integer articleNo) {
		this.articleMapper.deleteGradeArticle(articleNo);
	}

	@Override
	public void insertArticle(ArticleVO article) {
		this.articleMapper.insertArticle(article);
	}

	@Override
	public void updateArticle(ArticleVO article) {
		this.articleMapper.updateArticle(article);
	}

	@Override
	public void deleteArticle(int articleNo) {
		this.articleMapper.deleteArticle(articleNo);
	}
	
	@Override
	public void upViewcount(int articleNo) {
		this.articleMapper.upViewcount(articleNo);
	}
	
	@Override
	public List<ArticleVO> selectAllArticle() {
		return this.articleMapper.selectAllArticle();
	}

	@Override
	public void insertLike(ArticleVO articleVO) {
		this.articleMapper.insertLike(articleVO);
	}

	@Override
	public void deleteLike(ArticleVO articleVO) {
		this.articleMapper.deleteLike(articleVO);
	}

	@Override
	public int totalRecCount(int articleNo) {
		return this.articleMapper.totalLikeCount(articleNo);
	}
	
	public int likeCheck(ArticleVO articleVO) {
		return this.articleMapper.likeCheck(articleVO);
	}
	
	@Override
	public void upLikecount(int articleNo) {
		this.articleMapper.upLikecount(articleNo);
	}
	
	@Override
	public void downLikecount(int articleNo) {
		this.articleMapper.downLikecount(articleNo);
	}

	@Override
	public void insertTags(int articleNo, String[] tagList) {
		for (int i = 0; i < tagList.length; i++) {
			TagVO tagVO = new TagVO(articleNo, tagList[i].substring(0, tagList[i].length()-1));
			this.articleMapper.insertTag(tagVO);
		}
	}

	@Override
	public List<TagVO> selectArticleTags(int articleNo) {
		return articleMapper.selectArticleTags(articleNo);
	}

	@Override
	public void deleteTag(int tagNo) {
		this.articleMapper.deleteTag(tagNo);
	}

	@Override
	public List<ArticleVO> search(Map<String, Object> map) {
		return this.articleMapper.search(map);
	}

	@Override
	public List<ArticleVO> selectBestArticle() {
		return this.articleMapper.selectBestArticle();
	}

	@Override
	public void deleteAllBestArticle() {
		this.articleMapper.deleteAllBestArticle();
	}

	@Override
	public void insertBestArticle(Map<String, Object> map) {
		this.articleMapper.insertBestArticle(map);
	}

	@Override
	public int selectNowDate() {
		return this.articleMapper.selectNowDate();
	}

	@Override
	public List<ArticleVO> selectUpdateBestArticle(int now) {
		return this.articleMapper.selectUpdateBestArticle(now);
	}

	@Override
	public boolean checkBestArticle(int articleNo) {
		return this.articleMapper.checkBestArticle(articleNo);
	}

	@Override
	public List<BestArticleVO> selectAllBestArticle() {
		return this.articleMapper.selectAllBestArticle();
	}

	//작성한 게시글 조회
	@Override
	public List<ArticleVO> selectMyArticle(int memberNo) {
		return this.articleMapper.selectMyArticle(memberNo);
	}

	@Override
	public List<ArticleVO> selectMyLikeArticle(int memberNo) {
		return this.articleMapper.selectMyLikeArticle(memberNo);
	}
}
