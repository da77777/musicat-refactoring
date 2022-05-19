package com.example.musicat.controller;

import com.example.musicat.domain.board.*;
import com.example.musicat.domain.member.GradeVO;
import com.example.musicat.domain.member.MemberVO;
import com.example.musicat.domain.paging.Criteria;
import com.example.musicat.service.board.ArticleService;
import com.example.musicat.service.board.BoardService;
import com.example.musicat.service.board.CategoryService;
import com.example.musicat.service.board.FileService;
import com.example.musicat.service.member.GradeService;
import com.example.musicat.service.music.MusicApiService;
import com.example.musicat.util.TemplateModelFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@Validated
public class BoardController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private GradeService gradeService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private FileService fileService;
	@Autowired
	private MusicApiService musicApiService;
	@Autowired
	private TemplateModelFactory templateModelFactory;


	// 메인화면 사이드바
	@GetMapping("/board")
	public String petopiaMain(Model model) {
		model.addAttribute("HomeContent", "fragments/categoryBoardListContent");
		return "view/home/viewHomeTemplate";
	}

	//카테고리 + 게시판 목록 조회 ( 관리자 )
	@GetMapping("/boardManager")
	public String boardManager(Model model) {
		List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
		model.addAttribute("categoryBoardList", categoryList);
		model.addAttribute("managerContent", "/view/board/boardManager");

		//카테고리 추가
		CategoryVO categoryVo = new CategoryVO();
		model.addAttribute("categoryVo", categoryVo);
		return "view/home/viewManagerTemplate";
	}

	@GetMapping("/accessDenideGrade")
	public String accessDenied(Model model) {
		model.addAttribute("managerContent", "view/security/accessDenideGrade");
		return "view/home/viewManagerTemplate";
	}

	//카테고리 추가
	@ResponseBody
	@PostMapping("/writeCategory")
	public Map<String, Integer> writeCategory(@ModelAttribute("categoryVo") CategoryVO categoryVo) {

		Map<String, Integer> map = new HashMap<>();

		String categoryName = categoryVo.getCategoryName();

		Integer duplicatedCategory = categoryService.retrieveDuplicatedCategory(categoryName);

		if (duplicatedCategory == null) {
			map.put("result", 0);
			this.categoryService.registerCategory(categoryVo.getCategoryName());
		} else {
			map.put("result", 1);
		}
		return map;
	}


	//카테고리 수정 페이지
	@ResponseBody
	@PostMapping("/selectOneCategory")
	public CategoryVO selectOneCategory(@RequestBody HashMap<String, Object> map) {

		CategoryVO cVO = new CategoryVO();

		int categoryNo = Integer.parseInt((String) map.get("categoryNo"));
		String categoryName = this.categoryService.retrieveOneCategory(categoryNo).getCategoryName();

		cVO.setCategoryNo(categoryNo);
		cVO.setCategoryName(categoryName);
		return cVO;
	}


	// 카테고리 수정
	@ResponseBody
	@PostMapping("/modifyCategory")
	public Map<String, Integer> modifyCategory ( @ModelAttribute("categoryVo") CategoryVO categoryVo) {
		Map<String, Integer> map = new HashMap<>();

		int categoryNo = categoryVo.getCategoryNo();
		String categoryName = categoryVo.getCategoryName();

		//카테고리명 중복 검사
		Integer duplicatedCategory = this.categoryService.retrieveDuplicatedCategory(categoryName);
		if (duplicatedCategory == null) { //중복 x
			map.put("result", 0);
			this.categoryService.modifyCategory(categoryNo, categoryName);
		} else { //중복 o
			if(duplicatedCategory == categoryNo) {
				map.put("result", 0); //해당 카테고리면 저장o
			} else {
				map.put("result", 1); //다른 카테고리면 저장x
			}
		}
		return map;
	}


	//카테고리 삭제
	@ResponseBody
	@PostMapping("/deleteCategory")
	public Map<String, Integer> deleteCategory(@ModelAttribute("categoryVo") CategoryVO categoryVo) {

		int categoryNo = categoryVo.getCategoryNo();
		int count = categoryService.retrieveConnectBoard(categoryNo);

		Map<String, Integer> map = new HashMap<>();
		if (count != 0) {
			map.put("result", 1);
		} else {
			map.put("result", 0);
			this.categoryService.removeCategory(categoryNo);
		}
		return map;
	}
	

	//게시판 추가 페이지 드롭박스 목록
	@ResponseBody
	@PostMapping("/selectListAdd")
	public CreateBoardVO selectListAdd() {
		CreateBoardVO cbVO = new CreateBoardVO();
		//카테고리 목록
		ArrayList<CategoryVO> categoryList = this.categoryService.retrieveCategoryList();
		//등급 목록
		ArrayList<GradeVO> gradeList = this.gradeService.retrieveGradeList();
		//게시판 종류 목록
		ArrayList<BoardVO> boardkindList = this.boardService.retrieveBoardkind();

		cbVO.setCategoryList(categoryList);
		cbVO.setGradeList(gradeList);
		cbVO.setBoardkindList(boardkindList);

		return cbVO;
	}

	//게시판 추가 - 저장
	@ResponseBody
	@PostMapping("/writeBoard")
	public Map<String, Integer> writeBoard(
			@RequestParam("categoryNo") int categoryNo,
			@RequestParam("boardName") String boardName,
			@RequestParam("writeGrade") int writeGrade,
			@RequestParam("readGrade") int readGrade,
			@RequestParam("boardkind") int boardkind) {

		Map<String, Integer> map = new HashMap<>();
		
		Integer duplicatedBoard = boardService.retrieveDuplicatedBoard(boardName);
		if (duplicatedBoard != null) { //겹치는 게 있으면
			map.put("result", 1);
		} else { //겹치는 게 없으면
			map.put("result", 0);
			//게시판 저장 목록
			BoardVO boardVo = new BoardVO();
			BoardGradeVO boardGradeVo = new BoardGradeVO();

			boardVo.setCategoryNo(categoryNo);
			boardVo.setBoardName(boardName);
			boardVo.setBoardkind(boardkind);
			boardGradeVo.setReadGrade(readGrade);
			boardGradeVo.setWriteGrade(writeGrade);

			this.boardService.registerBoard(boardVo, boardGradeVo);
		}
		return map;
	}

	//게시판 수정 페이지
	@ResponseBody
	@PostMapping("/selectListModify")
	public CreateBoardVO selectListModify(@RequestBody HashMap<String, Object> map ) {

		CreateBoardVO cbVO = new CreateBoardVO();
		//카테고리 목록
		ArrayList<CategoryVO> categoryList = this.categoryService.retrieveCategoryList();
		//등급 목록
		ArrayList<GradeVO> gradeList = this.gradeService.retrieveGradeList();
		//게시판 종류 목록
		ArrayList<BoardVO> boardkindList = this.boardService.retrieveBoardkind();

		//해당 게시판의 정보
		int boardNo = Integer.parseInt((String) map.get("boardNo"));
		BoardBoardGradeVO bbg = this.boardService.retrieveOneBoard(boardNo);

		cbVO.setBbg(bbg);
		cbVO.setCategoryList(categoryList);
		cbVO.setGradeList(gradeList);
		cbVO.setBoardkindList(boardkindList);

		return cbVO;
	}

	// 게시판 수정
	@ResponseBody
	@PostMapping("/modifyBoard")
	public Map<String, Integer> modifyBoard(
			@RequestParam("boardNo") int boardNo,
			@RequestParam("categoryNo") int categoryNo,
			@RequestParam("boardName") String boardName,
			@RequestParam("writeGrade") int writeGrade,
			@RequestParam("readGrade") int readGrade,
			@RequestParam("boardkind") int boardkind) {

		Map<String, Integer> map = new HashMap<>();
		
		BoardVO boardVo = new BoardVO();
		BoardGradeVO boardGradeVo = new BoardGradeVO();

		//게시판명 중복 검사
		Integer duplicatedBoard = boardService.retrieveDuplicatedBoard(boardName);
		if (duplicatedBoard == null) { //중복 x
			map.put("result", 0);
			modifyBoardSub(boardVo, boardGradeVo, boardNo, categoryNo,boardName, writeGrade, readGrade, boardkind);
			this.boardService.modifyBoard(boardVo, boardGradeVo);

		} else if(duplicatedBoard != null) { //중복 o
			if (duplicatedBoard == boardNo) { //해당 게시판이면 result 0
				map.put("result", 0);
				modifyBoardSub(boardVo, boardGradeVo, boardNo, categoryNo,boardName, writeGrade, readGrade, boardkind);
				this.boardService.modifyBoard(boardVo, boardGradeVo);
			} else { //다른 게시판이면 result 1
				map.put("result", 1);
			}
		}
		return map;
	}

	//게시판 수정 sub
	private void modifyBoardSub(BoardVO boardVo, BoardGradeVO boardGradeVo,
							   int boardNo, int categoryNo, String boardName, int writeGrade, int readGrade, int boardkind) {
		boardVo.setBoardNo(boardNo);
		boardVo.setCategoryNo(categoryNo);
		boardVo.setBoardName(boardName);
		boardGradeVo.setWriteGrade(writeGrade);
		boardGradeVo.setReadGrade(readGrade);
		boardVo.setBoardkind(boardkind);
	}


	//게시판 삭제
	@ResponseBody
	@PostMapping("/deleteBoard")
	public Map<String, Integer> deleteBoard(@ModelAttribute("boardVo") BoardVO boardVo) {

		int boardNo = boardVo.getBoardNo();
		int count = this.boardService.retrieveConnectArticle(boardNo);

		Map<String, Integer> map = new HashMap<>();

		if (count != 0) {
			map.put("result", 1);
		} else {
			map.put("result", 0);
			this.boardService.removeBoard(boardNo);
		}
		return map;
	} 


	// 게시판 목록 조회
	@GetMapping("/board/{boardNo}/articles")
	public String selectAllNomalArticle(@PathVariable("boardNo") int boardNo,
										Model model) {
		// create
		BoardBoardGradeVO bbgVO = this.boardService.retrieveOneBoard(boardNo);
		String boardName = bbgVO.getBoardVo().getBoardName();
		int boardkind = bbgVO.getBoardVo().getBoardkind();
		MemberVO member = HomeController.checkMemberNo();
		model.addAttribute("memberNo", member.getNo());
		int gradeNo = member.getGradeNo();

		if(boardkind == 3) {
			List<GradeArticleVO> articles = this.articleService.selectGradeArticles();
			model.addAttribute("articles", articles); // 게시글 정보 전송
		} else{
			List<ArticleVO> articles = new ArrayList<>();
			int startPage = 1;

			int writeCheck = boardService.checkWriteGrade(boardNo, gradeNo);
			model.addAttribute("writeCheck", writeCheck);

			if(boardkind == 0){ //일반 게시판
				articles = this.articleService.retrieveBoard(boardNo);
			} else { // 썸네일 보드
				articles = this.articleService.selectBoardList(boardNo, startPage);
			}

			int totalCount = this.articleService.boardTotalCount(boardNo);
			Criteria creitea = Criteria.getThumbnailPaging(1, totalCount);

			model.addAttribute("startPage", startPage);
			model.addAttribute("pageSize", creitea.getPageSize());
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("endPage", creitea.getEndPage());
			log.info("startPage: {}, pageSize: {}, totalCount: {}, endPage: {}",startPage,creitea.getPageSize(),totalCount,creitea.getEndPage());
			// bind
			FileVO file = new FileVO();
			if(boardkind != 2){
				for (ArticleVO article : articles) {
					// Html 변환
					String escapeSubject = StringEscapeUtils.unescapeHtml4(article.getSubject());
					article.setSubject(escapeSubject);

					file.setArticleNo(article.getNo());
					file.setFileType(1);
					FileVO thumbFile = this.fileService.retrieveThumbFile(file);
					if(thumbFile != null) {
						article.setThumbnail(thumbFile);
					} else {
						FileVO noFile = new FileVO();
						noFile.setSystemFileName("noimage.png");
						article.setThumbnail(noFile);
					}
				}
			} else { //오디오 게시판 썸네일 설정
				setAudioBoardThumbnail(articles);
			}
			model.addAttribute("articles", articles); // 게시글 정보 전송
		}

		List<BoardVO> likeBoardList = this.boardService.retrieveLikeBoardList(member.getNo());
		model.addAttribute("likeBoardList", likeBoardList);

		List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
		CategoryVO categoryVo = new CategoryVO();


		model.addAttribute("boardNo", boardNo);
		model.addAttribute("categoryBoardList", categoryList);
		model.addAttribute("boardName", boardName); // 차후 이름으로 변경할것
		model.addAttribute("boardkind", boardkind); // 게시글 유형

		templateModelFactory.setCurPlaylistModel(model);
		log.info("boardKind: {}", boardkind);

		return "/view/home/viewBoardTemplate";
	}

	//오디오 게시판 썸네일 설정
	private void setAudioBoardThumbnail(List<ArticleVO> articles) {
		for (ArticleVO article : articles) {
			Map<String, Object> mmap = (Map<String, Object>) musicApiService.retrieveMusics(article.getNo()).get(0);
			List<Map<String, Object>> resmap = (List<Map<String, Object>>) mmap.get("links");
			Map<String, Object> aMap = resmap.get(1);
			FileVO fileVO = new FileVO();
			fileVO.setSystemFileName((String) aMap.get("href"));
			article.setThumbnail(fileVO);
		}
	}


	@ResponseBody
	@GetMapping("/board/paging")
	public Map<String, Object> pagingBoardList(@RequestParam("movePage") int movePage,
										   @RequestParam("boardNo") int boardNo,
											   @RequestParam(value = "keyword", required = false) String keyword,
											   @RequestParam(value = "content", required = false) String content){
		HashMap<String, Object> result = new HashMap<>();
		//int
		int totalCount = this.articleService.boardTotalCount(boardNo);
		Criteria creitea = Criteria.getThumbnailPaging(movePage, totalCount);
//		creitea.bindEndPage(totalCount); //endPage 바인딩

		// currentPage, pageSize, endPage
		result.put("currentPage", movePage);
		result.put("creitea", creitea);
		List<ArticleVO> articles = new ArrayList<>();
		if (keyword != null && content != null) { //검색 paigng
			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put(keyword, content);
			Criteria cre = Criteria.getThumbnailPaging(movePage, totalCount);
			int offset = cre.getPageStart();
			searchMap.put("offset", offset);
			articles = this.articleService.search(searchMap);
		}else { // 기본 paging
			articles = this.articleService.selectBoardList(boardNo, movePage);
		}
		result.put("articles", articles);

		return result;
	}




	// 게시판 내 검색
	@GetMapping("/board/search")
	@ResponseBody
	public Map<String, Object> searchByBoard(@RequestParam("keyword") String keyword
			,@RequestParam("content") String content
			,@RequestParam("boardNo") Integer boardNo
			,@RequestParam(value = "aKeyword", required = false) String aKeyword
			,@RequestParam(value = "aContent", required = false) String aContent){
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("offset", 0);
		if(aKeyword != null){
			if(!aKeyword.equals(keyword)){ // keyword가 같다면 생략
				searchMap.put(aKeyword, aContent);
			}
		}
		if(boardNo > 0){
			searchMap.put("boardNo", boardNo);
		}
		searchMap.put("keyword", keyword);
		searchMap.put("content", content);

		Map<String, Object> result = new HashMap<>();
		List<ArticleVO> results = articleService.search(searchMap);
		Criteria creitea = Criteria.getThumbnailPaging(1, results.size());
//		creitea.bindEndPage(totalCount); //endPage 바인딩


		result.put("results", results);
		result.put("currentPage", 1);
		result.put("creitea", creitea);

		return result;
	}

	//즐겨찾기 게시판 추가
	@ResponseBody
	@PostMapping("/likeBoard")
	public Map<String, Integer> likeBoard(@RequestParam("memberNo") int memberNo, @RequestParam("boardNo") int boardNo) {

		Map<String, Integer> map = new HashMap<>();

		//즐찾 한 게시판인지 여부
		int likeboard = this.boardService.retrieveLikeBoard(memberNo, boardNo);

		if (likeboard == 0) {
			map.put("result", 0);
			this.boardService.registerLikeBoard(memberNo, boardNo);
		} else {
			map.put("result", 1);
			this.boardService.removeLikeBoard(memberNo, boardNo);
		}
		return map;
	}

}
