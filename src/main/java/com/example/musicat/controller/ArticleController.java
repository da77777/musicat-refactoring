package com.example.musicat.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.example.musicat.controller.form.ArticleForm;
import com.example.musicat.domain.board.*;
import com.example.musicat.domain.member.MemberVO;
import com.example.musicat.domain.music.Music;
import com.example.musicat.repository.board.ArticleDao;

import com.example.musicat.service.member.MemberService;
import com.example.musicat.service.music.MusicApiService;

import com.example.musicat.util.FileManager;

import com.example.musicat.util.TemplateModelFactory;
import com.example.musicat.websocket.manager.NotifyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.musicat.service.board.ArticleService;
import com.example.musicat.service.board.BoardService;
import com.example.musicat.service.board.CategoryService;
import com.example.musicat.service.board.FileService;
import com.example.musicat.service.board.ReplyService;
import com.example.musicat.controller.form.FileFormVO;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("articles")
public class ArticleController {

	private final MemberService memberService;
	private final ArticleService articleService;
	private final FileManager fileManager;
	private final FileService fileService;
	private final ReplyService replyService;
	private final BoardService boardService;
	private final CategoryService categoryService;
	private final ArticleDao articleDao;
	private final MusicApiService musicApiService;

	private final NotifyManager notifyManager;

	private final TemplateModelFactory templateModelFactory;

	/**
	 * ?????? ??????
	 * @param articleNo ????????? ??????
	 * @param req ????????? ?????? ???????????? ?????? ?????? ??????
	 * @param model
	 * @return
	 */
	@GetMapping("/{articleNo}")
	public String detailArticle(@PathVariable("articleNo") int articleNo
			,HttpServletRequest req
			,Model model) {
		// create
		//log.info("ArticleController.detailArticle: authAnon = " + SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());

		MemberVO member = HomeController.checkMemberNo();
		int gradeNo = member.getGradeNo();

		ArticleVO article = this.articleService.retrieveArticle(articleNo);
		//log.info("Acontroller.detailArticle: -------" + article.toString());
		int boardNo = article.getBoardNo();
		//gradeNo = member.getGradeNo();
		boolean grade = this.boardService.retrieveAllReadBoard(boardNo, gradeNo);
		//boolean grade = true;

		List<BoardVO> likeBoardList = this.boardService.retrieveLikeBoardList(member.getNo());
		model.addAttribute("likeBoardList", likeBoardList);

		List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
		model.addAttribute("categoryBoardList", categoryList);

		BoardBoardGradeVO bbg = this.boardService.retrieveOneBoard(boardNo);
		model.addAttribute("boardName",bbg.getBoardVo().getBoardName());
		if (grade) {
			log.info("sidebar");
			int memberNo = member.getNo();
			model.addAttribute("loginMemberNo", memberNo);
			this.articleService.upViewcount(articleNo); // ????????? ??????
			// bind
			List<ReplyVO> replys = this.replyService.retrieveAllReply(articleNo);
			int totalCount = this.articleService.totalRecCount(articleNo);
			int likeCheck = this.articleService.likeCheck(memberNo, articleNo);

			ArticleVO result = ArticleVO.addReplyAndLike(article, likeCheck, replys, totalCount); //????????????
			List<ArticleVO> subArticle = this.articleService.selectSubArticle(articleNo);
			model.addAttribute("subArticles", subArticle);


			List<Music> musicList = musicApiService.retrieveMusics(articleNo);
			model.addAttribute("musicList", musicList);
			log.info("article controller musiclist : " + musicList.toString());



			// xss ?????? Html tag??? ??????
//			String escapeSubject = StringEscapeUtils.unescapeHtml4(article.getSubject());
//			article.setSubject(escapeSubject);
//			String escapeContent = StringEscapeUtils.unescapeHtml4(article.getContent());
//			article.setContent(escapeContent);

			model.addAttribute("article", result);
			log.info("detailArticle: {}", result.toString());
			model.addAttribute("HomeContent", "/view/board/detailArticle");
		} else {
			model.addAttribute("HomeContent", "/view/security/accessDenideGrade");
		}

		templateModelFactory.setCurPlaylistModel(model);

		return "view/home/viewHomeTemplate";
	}

	//-------------------------------------------------------------------------------------------------------------

	/**
	 * ?????? ??? ??????
	 */
	@GetMapping("/insert")
	public String writeForm(HttpServletRequest req, Model model) {
		// create
		ArticleForm form = new ArticleForm(); // ??????

		// bind    
    	MemberVO member = HomeController.checkMemberNo();
		model.addAttribute("memberNo", member.getNo());
		
		List<BoardVO> likeBoardList = this.boardService.retrieveLikeBoardList(member.getNo());
		model.addAttribute("likeBoardList", likeBoardList);

		List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
		model.addAttribute("categoryBoardList", categoryList);

		int gradeNo = member.getGradeNo();
//		log.info("Start insertArticleCont--");
//		log.info("writeForm get No::::" + gradeNo);

		// bind
//		log.info("insert form ?????? ?????? ?????? ???");
		List<BoardVO> boardList = this.boardService.retrieveAllWriteBoard(gradeNo);
//		log.info("insert form ?????? ?????? ?????? ???");
//		log.info("End insertArticleCont--");

		// view
		model.addAttribute("boardList", boardList);
		model.addAttribute("form", form);
		model.addAttribute("gradeNo", gradeNo); // ????????? seesion member??? ???????????? grade_no ????????? ???
		model.addAttribute("HomeContent", "/view/board/writeArticleForm");
		log.info("-------- writeForm --------");
		return "view/home/viewHomeTemplate";
	}

	/**
	 * ??????
	 */
	@PostMapping("/insert")
	public ModelAndView insertArticle(@Validated(ValidationSequence.class) @ModelAttribute("form") ArticleForm articleForm
			,BindingResult result
			,@ModelAttribute FileFormVO form
			,@RequestParam("tags") String tags
			,@RequestParam(value = "audioNo", required = false) Long audioNo
			,HttpServletRequest req) throws IOException {
		ModelAndView mv = new ModelAndView();
//		log.info("audioNo= {}",audioNo);
//		log.info("insert??????");
		if (result.hasErrors()){
			List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
			mv.addObject("categoryBoardList", categoryList);
			List<BoardVO> boardList = this.boardService.retrieveAllWriteBoard(1);
			// view
			mv.addObject("boardList", boardList);
			mv.addObject("HomeContent", "/view/board/writeArticleForm");
			mv.setViewName("view/home/viewHomeTemplate");
			return mv;
		}
		// create
		MemberVO member = HomeController.checkMemberNo();

		// ?????? ?????? ?????? ????????? Upload??? ????????? ??????
		FileVO attacheFile = fileManager.uploadFile(fileManager.fileDir, form.getImportAttacheFile()); // ?????? ??????
		List<FileVO> imageFiles = fileManager.uploadFiles(form.getImageFiles()); // ????????? ??????
		if (imageFiles.size() > 0) {
			int pos = imageFiles.get(0).getSystemFileName().indexOf(".");
			String ext = imageFiles.get(0).getSystemFileName().substring(pos + 1);
			if ("mp4".equals(ext)){
				if (imageFiles.get(1) != null){
					fileManager.createThumbnail(imageFiles.get(1).getSystemFileName()); // ????????? ??????
				}
			} else {
				fileManager.createThumbnail(imageFiles.get(0).getSystemFileName()); // ????????? ??????
			}
		}
		// bind
		ArticleVO article = ArticleVO.createArticle(member.getNo(), member.getNickname(), articleForm, attacheFile, imageFiles);

		if(!tags.equals("")){ //????????? tag??? ?????? ??????
			String[] tagList = tags.split(","); // tag???
			article.setTagList(tagList);
		}

		this.articleService.registerArticle(article, audioNo);
		int articleNo = article.getNo(); // ?????? ??? ????????? ????????????page??? ???????????? ????????? ????????? ????????? ????????????. (insert??? ?????? ??? Last ID ????????????.)
		log.info("?????? ?????????={}", article.toString());

		// view
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("/articles/" + articleNo);
		mv.setView(redirectView);

		// ?????? - ?????? ?????????
		//notifyManager.addNotify(new NotifyVO(1, "?????? ?????? ?????????", "/main"));
		return mv;
	}

	/**
	 * ?????? ??? ??????
	 */
	@GetMapping("/update/{articleNo}")
	public String updateForm(@PathVariable int articleNo
			,HttpServletRequest req
			,Model model) {
		ArticleVO article = this.articleService.retrieveArticle(articleNo); // ????????? ?????? ????????????
		ArticleForm form = ArticleForm.updateArticle(article);
		// create

		MemberVO member = HomeController.checkMemberNo();
		int gradeNo = member.getGradeNo();

		List<BoardVO> likeBoardList = this.boardService.retrieveLikeBoardList(member.getNo());
		model.addAttribute("likeBoardList", likeBoardList);

		List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
		model.addAttribute("categoryBoardList", categoryList);

		// bind
		List<BoardVO> boardList = this.boardService.retrieveAllWriteBoard(gradeNo);

		// view
		model.addAttribute("boardList", boardList);
		model.addAttribute("form", form); //????????? ?????? ?????????
		model.addAttribute("article", article);
		model.addAttribute("gradeNo", gradeNo); // ????????? seesion member??? ???????????? grade_no ????????? ???
		model.addAttribute("HomeContent", "/view/board/updateArticleForm");

		// ????????? ?????? - ?????? ????????????
		List<Music> musics = musicApiService.retrieveMusics(articleNo);
		model.addAttribute("musics", musics);

		return "view/home/viewHomeTemplate";
	}

	// ????????? ??????
	@PostMapping("/update/{articleNo}")
	public ModelAndView updateArticle(@ModelAttribute("article") ArticleVO article
			, @Validated @ModelAttribute("form") ArticleForm articleForm
			, BindingResult result
			, @ModelAttribute FileFormVO form
			, @RequestParam("tags") String tags
			, @PathVariable("articleNo") int articleNo
			, @RequestParam(value = "audioNo", required = false) Long audioNo)
			throws IOException {
		log.info("update??????");

		log.info("update audioNo : {}", audioNo);

		ModelAndView mv = new ModelAndView();
		if (result.hasErrors()){

			MemberVO member = HomeController.checkMemberNo();
			List<BoardVO> likeBoardList = this.boardService.retrieveLikeBoardList(member.getNo());
			mv.addObject("likeBoardList", likeBoardList);
			
			List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
			mv.addObject("categoryBoardList", categoryList);
			List<BoardVO> boardList = this.boardService.retrieveAllWriteBoard(1);
			// view
			mv.addObject("boardList", boardList);
			mv.addObject("HomeContent", "/view/board/updateArticleForm");
			mv.setViewName("view/home/viewHomeTemplate");
			return mv;
		}

		// create
		if(!tags.equals("")){ //????????? tag??? ?????? ??????
			log.info("tag null if??? ??????");
			String[] tagList = tags.split(","); // tag???
			article.setTagList(tagList);
		}

		// ?????? ?????? ?????? ????????? Upload??? ????????? ??????
		FileVO attacheFile = fileManager.uploadFile(fileManager.fileDir, form.getImportAttacheFile()); // ?????? ??????
		List<FileVO> imageFiles = fileManager.uploadFiles(form.getImageFiles()); // ????????? ??????
		if (imageFiles.size() > 0) {
			fileManager.createThumbnail(imageFiles.get(0).getSystemFileName()); // ????????? ??????
		}
		// bind
		ArticleVO.updateArticle(article, articleNo, articleForm, attacheFile,imageFiles);
		this.articleService.modifyArticle(article, audioNo);
		// view
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("/articles/" + articleNo);
		mv.setView(redirectView);


		return mv;
	}

	@GetMapping("/remove/{articleNo}")
	public RedirectView removeArticle(@PathVariable("articleNo") int articleNo
			,HttpServletRequest req) {
		RedirectView redirectView = new RedirectView();
//		HttpSession session = req.getSession();
//		MemberVO member = (MemberVO) session.getAttribute("loginUser");
		MemberVO member = HomeController.checkMemberNo();
		int memberNo = member.getNo();
		int boardNo = this.articleService.removeArticle(articleNo, memberNo);
		redirectView.setUrl("/board/" + boardNo + "/articles");
		return redirectView;
	}

	// ?????? 
	@PostMapping("/addLike")
	@ResponseBody
	public Map<String, Object> recUpdate(@RequestBody HashMap<String, Object> map
			,HttpServletRequest req) {
//		HttpSession session = req.getSession();
//		MemberVO member = (MemberVO) session.getAttribute("loginUser");
		MemberVO member = HomeController.checkMemberNo();
		int memberNo = member.getNo();
		int articleNo = Integer.parseInt((String)map.get("articleNo"));

		this.articleService.recUpdate(memberNo, articleNo);
		int totalCount = this.articleService.totalRecCount(articleNo);
		map.put("totalcount", totalCount);
		return map;
	}

	// ?????? ??????
	@PostMapping("/delLike")
	@ResponseBody
	public Map<String, Object> recDelete(@RequestBody HashMap<String, Object> map
			,HttpServletRequest req) {
//		HttpSession session = req.getSession();
//		MemberVO member = (MemberVO) session.getAttribute("loginUser");
		MemberVO member = HomeController.checkMemberNo();
		ArticleVO article = new ArticleVO();
		int articleNo = Integer.parseInt((String)map.get("articleNo"));
		article.setNo(articleNo);
		article.setMemberNo(member.getNo());
		this.articleService.recDelete(article);
		int totalCount = this.articleService.totalRecCount(articleNo);
		map.put("totalcount", totalCount);
		return map;
	}

	@PostMapping("/removeTag")
	@ResponseBody
	public List<TagVO> removeTag(@RequestParam("tagNo") int tagNo
			,@RequestParam("articleNo") int articleNo){
		articleService.deleteTag(tagNo);
		List<TagVO> findTags = articleDao.selectArticleTags(articleNo);
		return findTags;
	}

	/**
	 *  ?????? ??????
	 * view ????????? ?????? ???
	 * ?????? ??????
	 */
	@GetMapping("/board/search")
	public String searchByBoard(@RequestParam("keyword") String keyword
			,@RequestParam("content") String content
			,Model model){

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("offset", 0);
		searchMap.put("keyword", keyword);
		searchMap.put("content", content);
		List<ArticleVO> articles = articleService.search(searchMap);
		model.addAttribute("articles", articles);

		MemberVO member = HomeController.checkMemberNo();
		List<BoardVO> likeBoardList = this.boardService.retrieveLikeBoardList(member.getNo());
		model.addAttribute("likeBoardList", likeBoardList);

		List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
		model.addAttribute("categoryBoardList", categoryList);

		model.addAttribute("keyword",keyword); // ?????? ?????? ??? ?????? ????????? ?????? keyword
		model.addAttribute("content",content); // ?????? ?????? ??? ?????? ????????? ?????? content
		model.addAttribute("boardNo",0);
		model.addAttribute("boardName", "?????? ??????");
		model.addAttribute("boardkind", 0);
		return "/view/home/viewBoardTemplate";
	}

	@GetMapping("/musicRegister")
	public String musicRegister(Model model) {
		int memberNo = HomeController.checkMemberNo().getNo();
		model.addAttribute("memberNo", memberNo);
		log.info("memberNo : " + memberNo);
		return "/view/board/musicRegister";
	}

	@ResponseBody
	@GetMapping("/insert/grade")
	public MemberVO writeGradeArticleForm(@RequestParam("memberNo") int memberNo) throws Exception{
		MemberVO memberVO = this.memberService.retrieveMemberByManager(memberNo);
		return memberVO;
	}

	@PostMapping("/insert/grade")
	public ModelAndView writeGradeArticle(@ModelAttribute("GradeArticleVO") GradeArticleVO gradeArticleVO) throws Exception{
		log.info("Post insert grade");
		ModelAndView mv = new ModelAndView();
		log.info(gradeArticleVO.toString());
		this.articleService.insertGradeArticle(gradeArticleVO);
		log.info("Post insert grade2");
		mv.setView(new RedirectView("/board/76/articles"));
		return mv;
	}

}
