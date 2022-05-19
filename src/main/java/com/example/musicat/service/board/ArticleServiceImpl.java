package com.example.musicat.service.board;

import java.util.*;

import com.example.musicat.domain.board.*;
import com.example.musicat.domain.paging.Criteria;
import com.example.musicat.mapper.board.ArticleMapper;
import com.example.musicat.mapper.member.MemberMapper;
import com.example.musicat.repository.board.ArticleDao;
import com.example.musicat.service.music.MusicApiService;
import com.example.musicat.util.BestArticle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

//	private final BoardService boardService;
	private final MusicApiService musicApiService;
	private final ArticleMapper articleMapper;
	private final ArticleDao articleDao;
	private final FileService fileService;
	private final MemberMapper memberMapper;
	private final BestArticle bestArticleUtil;
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ArticleVO retrieveArticle(int articleNo) {
		List<SelectArticleVO> results = this.articleDao.selectArticle(articleNo);
		List<TagVO> tags = this.articleMapper.selectArticleTags(articleNo);
		System.out.println("ArticleServiceImpl.retrieveArticle: results : " + results.size());

		ArticleVO article = results.get(0).getArticle(); // 게시글 정보 출력
		article.setSelectTags(tags);

		List<FileVO> fileList = new ArrayList<>();
			if(results.get(0).getFile() != null) { // 파일이 있을 때
				for (SelectArticleVO result : results) {
					fileList.add(result.getFile());
				}
			} else { // 첨부된 파일이 없을 때
				fileList=null;
			}

		return this.fileService.fileList(article, fileList);
	}

	@Override
	public List<ArticleVO> selectSubArticle(int articleNo) {
		return this.articleDao.selectSubArticle(articleNo);
	}

	@Override
	public List<ArticleVO> retrieveBoard(int boardNo) {
		return this.articleDao.selectBoard(boardNo);
	}

	@Override
	public List<ArticleVO> selectBoardList(int boardNo, int currentNo){
		HashMap<String, Object> map = new HashMap<>();
		Criteria cre = Criteria.getThumbnailPaging(currentNo, 1);
		int offset = cre.getPageStart();
		log.info("cre: {}", cre.toString());
		log.info("currentNo: {}", currentNo);
		log.info("offset: {}", offset);
		map.put("boardNo", boardNo);
		map.put("offset", offset);
		return this.articleDao.selectBoardList(map);
	}

	@Override
	public int boardTotalCount(int boardNo){
		// end Page
		return this.articleMapper.boardTotalCount(boardNo);
	}

	@Override
	public List<GradeArticleVO> selectGradeArticles() {
		return articleMapper.selectGradeArticles();
	}

	@Override
	public void insertGradeArtilce(GradeArticleVO gradeArticleVO) {
		this.articleDao.insertGradeArticle(gradeArticleVO);
	}

	@Override
	public void removeGradeArticle(Integer articleNo) {
		this.articleDao.deleteGradeArticle(articleNo);
	}

	// 게시글 추가
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void registerArticle(ArticleVO article, Long audioNo) {
		this.memberMapper.plusMemberDocs(article.getMemberNo());
		this.articleDao.insertArticle(article); // 게시글 추가
		this.fileService.uploadFile(article);
		if (article.getTagList() != null) { // 입력태그가 있을 때만 동작
			this.articleDao.insertTags(article.getNo(), article.getTagList());	
		}
		if (audioNo != null) {
			this.musicApiService.connectToArticle(audioNo, article.getNo());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void modifyArticle(ArticleVO article, Long audioNo) {
		this.articleDao.updateArticle(article);
		this.fileService.uploadFile(article);
		if (article.getTagList() != null) { // 입력태그가 있을 때만 동작
			this.articleDao.insertTags(article.getNo(), article.getTagList());
		}
		if (audioNo != null) {
			this.musicApiService.connectToArticle(audioNo, article.getNo());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int removeArticle(int articleNo, int memberNo) {
		this.musicApiService.deleteMusicByArticleNo(articleNo); // 음악 삭제
		this.memberMapper.minusMemberDocs(memberNo);
		int boardNo = this.articleDao.selectArticle(articleNo).get(0).getArticle().getBoardNo();
		boolean check = this.articleDao.checkBestArticle(articleNo);
		this.articleDao.deleteArticle(articleNo);
		if(check){ // 만약 지우는 게시글이 best글이였다면 bestarticle table 갱신
			updateBestArticle();			
		}
		return boardNo;
	}

	@Override
	public void upViewcount(int articleNo) {
		this.articleDao.upViewcount(articleNo);
	}
	
	@Override
	public List<ArticleVO> retrieveAllArticle() {
		return this.articleDao.selectAllArticle();
	}

	// 추천
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void recUpdate(int memberNo, int articleNo) {
		ArticleVO article = new ArticleVO();
		article.setMemberNo(memberNo);
		article.setNo(articleNo);
		this.articleDao.upLikecount(articleNo);
		this.articleDao.insertLike(article);
	}

	@Override
	public int totalRecCount(int articleNo) {
		return this.articleDao.totalRecCount(articleNo);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void recDelete(ArticleVO articleVO) {
		this.articleDao.downLikecount(articleVO.getNo());
		this.articleDao.deleteLike(articleVO);
	}

	@Override
	public int likeCheck(int memberNo, int ArticleNo) {
		ArticleVO art = new ArticleVO();
		art.setMemberNo(memberNo);
		art.setNo(ArticleNo);
		return this.articleDao.likeCheck(art);
	}

	/**
	 * tag
	 */
	@Override
	@Transactional()
	public void deleteTag(int tagNo) {
		this.articleDao.deleteTag(tagNo);
	}

	/**
	 * 검색 
	 */
	@Override
	public List<ArticleVO> search(Map<String, Object> map) {
		String keyword = (String) map.get("keyword");
		String content = (String) map.get("content");
		if ("subject".equals(keyword)) {
			map.put("subject", content);
		}
		if ("nickname".equals(keyword)) {
			map.put("nickname", content);
		}
		if ("tagname".equals(keyword)) {
			map.put("tagname", content);
		}
		map.remove("keyword");
		map.remove("content");

		List<ArticleVO> result = this.articleDao.search(map);
		return result;
	}

	/**
	 * 베스트글 조회 
	 */
	@Override
	public List<BestArticleVO> selectAllBestArticle() {
		List<BestArticleVO> bestArticles = this.articleDao.selectAllBestArticle();
		int rank = 1;
		for (BestArticleVO bestArticle : bestArticles) {
			bestArticle.setRank(rank);
			rank++;
		}
		return bestArticles;
	}

	//==검색 비즈니스 메서드==//
	//베스트글 삭제시 조회
	private void updateBestArticle(){
		int now = this.articleDao.selectNowDate(); // 현재 날짜 구하기
		log.info("ArticleServiceImpl.updateBestArticle: now = {}", now);
		List<ArticleVO> findBestArticles = this.articleDao.selectUpdateBestArticle(now); // 조회
		// best table 날리기
		this.articleDao.deleteAllBestArticle();
		// find articles insert
		this.bestArticleUtil.insertBestArticle(findBestArticles);
	}

	//작성한 게시글 조회
	@Override
	public List<ArticleVO> retrieveMyArticle(int memberNo) {
		return this.articleDao.selectMyArticle(memberNo);
	}

	//추천 누른 게시글 조회
	@Override
	public List<ArticleVO> retrieveMyLikeArticle(int memberNo) {
		return this.articleDao.selectMyLikeArticle(memberNo);
	}
}
