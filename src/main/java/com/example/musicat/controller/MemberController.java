package com.example.musicat.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.musicat.controller.form.GradeArticleForm;
import com.example.musicat.controller.form.JoinForm;
import com.example.musicat.domain.board.GradeArticleVO;
import com.example.musicat.service.board.ArticleService;
import com.example.musicat.service.member.ProfileService;
import com.example.musicat.service.music.MusicApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.musicat.service.member.GradeService;
import com.example.musicat.service.member.MemberService;
import com.example.musicat.domain.member.GradeVO;
import com.example.musicat.domain.member.MemberVO;
import com.example.musicat.domain.paging.Criteria;
import com.example.musicat.domain.paging.Paging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

//여기부터가 기본 세팅
@Slf4j
@Controller
public class MemberController {

	// 양
	@Autowired
	private BCryptPasswordEncoder encodePwd;

	@Autowired
	private MemberService memberService;
	@Autowired
	private GradeService gradeService;
	@Autowired
	private ProfileService profileService;
	@Autowired
	private ArticleService articleService;

	@Autowired
	private MusicApiService musicApiService;

//	회원가입
//	@PostMapping("/join") // 이걸 실행하는 값의 주소
//	public String joinMember(MemberVO mVo) {
//		mVo.setPassword(encodePwd.encode(mVo.getPassword())); //비밀번호 암호화
//		try{
//			this.memberService.registerMember(mVo);
//			this.profileService.addProfile(mVo.getNo());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//log.info("비밀번호 : " + mVo.getPassword() + " 이메일 : " + mVo.getEmail() + " 닉네임 : " + mVo.getNickname());
//
//		//log.info("비밀번호(암호화) : " + mVo.getPassword());
//		return "redirect:/musicatlogin"; // string으로 리턴되는건 html 파일로 넘어감! (회원가입 다음 로그인화면으로 넘어가고 싶다면 templates 안에 있는 로그인
//								// html 파일명 쓰기)
//	}

	@PostMapping("/join") // 이걸 실행하는 값의 주소
	public ModelAndView joinMember(@Validated JoinForm form, BindingResult result) throws Exception{
		ModelAndView mv = new ModelAndView();
		if(result.hasErrors()){
			mv.setViewName("view/member/register");
			return mv;
		}
		if ( !(form.getPassword().equals(form.getConfirmPassword())) ) {
			// 비밀번호 일치 검증
			mv.addObject("form", form);
			mv.addObject("passwdError", "비밀번호가 일치하지 않습니다.");
			mv.setViewName("/musicatlogin");
			return mv;
		}

		String encodePassword = encodePwd.encode(form.getPassword()); //비밀번호 암호화
		MemberVO mvo = MemberVO.joinMember(form.getEmail(), encodePassword, form.getNickname());
		this.memberService.registerMember(mvo);

		mvo = this.memberService.retrieveMemberByEmail(mvo.getEmail());
		log.info("방금 가입한 멤버 넘버 : " + mvo.getNo());
		this.musicApiService.makeNowPlaying(mvo);
//		this.profileService.addProfile(mvo.getNo());

//		return "redirect:/musicatlogin"; // string으로 리턴되는건 html 파일로 넘어감! (회원가입 다음 로그인화면으로 넘어가고 싶다면 templates 안에 있는 로그인
								// html 파일명 쓰기)
		mv.setView(new RedirectView("/musicatlogin"));
		return mv;
	}

	@ResponseBody
	@PostMapping("/joinCheck")
	public int joinCheck(@RequestParam("type") String type
						,@RequestParam("value") String value) {
		HashMap<String, Object> checkMap = new HashMap<>();
		if ("email".equals(type)) {
			checkMap.put(type, value);
		} else if ("nickname".equals(type)) {
			checkMap.put(type, value);
		}
		int check = this.memberService.joinCheck(checkMap);
		return check;
	}

	// 회원 목록 조회
	@GetMapping("/members")
	public String viewMemberList(Model model, Criteria crt) {
		List<MemberVO> lst = null;
		Paging paging = new Paging();
		try {
			int total = this.memberService.retrieveTotalMember();
			paging.setCrt(crt);
			paging.setTotal(total);
			lst = this.memberService.retrieveMemberList(crt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("managerContent", "view/member/memberList");
		model.addAttribute("lst", lst);
		model.addAttribute("paging", paging);
		return "view/home/viewManagerTemplate";
	}

	// 회원 검색
	@PostMapping("/members")
	@ResponseBody
	public Map<String, Object> viewSearchList(@RequestParam("keyword") String keyword, @RequestParam("keyfield") String keyfield,
			@RequestParam("number") int cur, Criteria crt) {
		log.info("keyword : " + keyword);
		Map<String, Object> map = new HashMap<String, Object>();
		List<MemberVO> lst = null;
		Paging paging = new Paging();
		crt.setCurrentPageNo(cur);
		try {
			int total = this.memberService.retrieveTotalSearchMember(keyfield, keyword);
			paging.setCrt(crt);
			paging.setTotal(total);
			log.info("keyword : " + keyword);
			lst = this.memberService.retrieveSearchMember(keyfield, keyword, crt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		map.put("paging", paging);
		map.put("lst", lst);
		log.info("map : " + map);
		return map;
	}

	// 회원 상세 조회
	@GetMapping("/member/{no}")
	public String viewMemberDetail(Model model, @PathVariable int no) throws Exception{
		try {
			MemberVO member = this.memberService.retrieveMemberByManager(no);
			log.info(member.toString());
			model.addAttribute("member", member);
			model.addAttribute("managerContent", "view/member/detailMemberByManager");
			System.out.println(model);
			return "view/home/viewManagerTemplate";
		} catch (Exception e) {
//			e.printStackTrace();
//			return "/error";
			return null;
		}
	}

	// 회원 활동 정지
	@PostMapping("/memberMode/{no}")
	public String updateBanDate(@PathVariable int no, @RequestParam(name = "banSelect") String banSelect, @RequestParam(name = "gradeSelect") String grade) throws Exception{
		log.info("ban = " + banSelect);
		log.info("grade = " + grade);
		try {
			if(!banSelect.equals("no")) {
				this.memberService.modifyBan(banSelect, no);
			}
			this.memberService.modifyGrade(no,grade);
			return "redirect:/members";
		} catch (Exception e) {
			e.printStackTrace();
			return "/error";
		}
	}

	// 회원 강제 탈퇴
	@PostMapping("/memberOut/{no}")
	public String updateOutByManager(@PathVariable int no) throws Exception{
		try {
			this.memberService.modifyMemberByForce(no);
			return "redirect:/members";
		} catch (Exception e) {
			e.printStackTrace();
//			return "/error";
			return null;
		}
	}

//	@GetMapping("/grades")
//	public String viewGradeList(Model model) {
//		ArrayList<GradeVO> grades = null;
//		try {
//			grades = this.gradeService.retrieveGradeList();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		model.addAttribute("grades", grades);
//		model.addAttribute("managerContent", "view/member/gradeList");
//		return "view/home/viewManagerTemplate";
//	}
	
//	회원 자진 탈퇴 화면으로 이동
	@GetMapping("/outForm")  //이걸 실행하는 값의 주소
	public String outForm(MemberVO mVo) {
		//this.memberService.modifyMember(member.getNo(), password);
		return "view/member/out"; 
	}
	
//	회원 자진 탈퇴 로직 실행 =
	@PostMapping("/outMember")  //이걸 실행하는 값의 주소
	public String outMember(@RequestParam String password, HttpSession session) {
		MemberVO member =  (MemberVO) session.getAttribute("loginUser");
		this.memberService.modifyMember(member.getNo(), password);
		return "redirect:logout"; 
	}

	
	
//	@GetMapping("/members")
//	public String callMemberList(Model model) {
//		List<MemberVO> lst = this.memberService.retrieveMemberList(0, 0);
//		model.addAttribute(lst);
//		return "memberList";
//	}
//	
//	@GetMapping("/members/{no}")
//	public String callMemberDetail(Model model, @PathVariable int no) {
//		MemberVO member = this.memberService.retrieveMemberByManager(no);
//		model.addAttribute(member);
//		model.addAttribute("managerContent","/fragments/view/ManagerContent");
//		return "view/member/detailMemberByManager";
//	}
	
//	@PostMapping("/grades")
//	public String modifyGrade(Model model, HttpServletRequest req) {
//		String[] stringNo = req.getParameterValues("gradeNo");
//		String[] stringDocs = req.getParameterValues("docs");
//		String[] stringComms = req.getParameterValues("comms");
//		String[] names = req.getParameterValues("name");
//
//		int[] docs = Arrays.stream(stringDocs).mapToInt(Integer::parseInt).toArray();
//		int[] comms = Arrays.stream(stringComms).mapToInt(Integer::parseInt).toArray();
//		int[] gradeNo = Arrays.stream(stringNo).mapToInt(Integer::parseInt).toArray();
//
//		try {
//			int oldGradeSize = this.gradeService.retrieveGradeList().size();
//			int newGradeSize = gradeNo.length;
//			for (int i = 0; i < newGradeSize; i++) {
//				gradeNo[i] = i + 1;
//				GradeVO grade = new GradeVO();
//				grade.setGradeNo(gradeNo[i]);
//				grade.setName(names[i]);
//				grade.setDocs(docs[i]);
//				grade.setComms(comms[i]);
//				this.gradeService.modifyGrade(grade);
//			}
//
//			if (oldGradeSize > newGradeSize) {
//				for (int i = newGradeSize; i < oldGradeSize; i++) {
//					this.gradeService.removeGrade(i + 1);
//				}
//			}
//
//			this.gradeService.sortGrade();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "redirect:/grades";
//	}

	@GetMapping("/grades/{gradeNo}")
	public void viewOpenBoardByGrade(Model model, @PathVariable int gradeNo) {
		Map<String, Object> map = null;
		try {
			map = this.gradeService.retrieveGradeBoardList(gradeNo);
			model.addAttribute(map);
			System.out.println(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@GetMapping("/passwordCheck")
	public String passwordCheckForm(Model model){
		MemberVO member = HomeController.checkMemberNo();
		model.addAttribute("memberNo", member.getNo());
		return "view/member/passwordCheck";
	}

	@PostMapping("/passwordCheck")
	public String passwordCheck(@RequestParam("password") String password
			,@RequestParam("memberNo") int memberNo
			,Model model){
		String memberPassword = this.memberService.passwordCheck(memberNo);
		log.info("Input Password: {}", memberPassword);
		log.info("Input Password: {}", password);
		if(this.encodePwd.matches(password, memberPassword)){
			log.info("match");
			return "view/member/passwordChange";
		}
		log.info("unMatch");
		model.addAttribute("memberNo", memberNo);
		model.addAttribute("NotEquals", "비밀번호가 일치하지 않습니다.");
		return "view/member/passwordCheck";
	}



	/**
	 * 비밀번호 변경
	 * @param password 변경할 비밀번호
	 */
	@PostMapping("/passwordChange")
	public String passwordChange(@RequestParam("newPassword")  String password
			,HttpSession session) {
		System.out.println(password);
		
		MemberVO mVo = HomeController.checkMemberNo();
//		mVo.setNo(((MemberVO) session.getAttribute("loginUser")).getNo());
		mVo.setPassword(encodePwd.encode(password)); //비밀번호 암호화
		this.memberService.updatePassword(mVo);
		return "redirect:main";
  }
  
	@GetMapping("/findPWD")
	public String findPassword() {
		return "view/member/findPassword";

	}

	@GetMapping("/update/member/grade")
	private ModelAndView updateMemberGrade(@RequestParam("result") String[] result, @RequestParam(value = "resultChoice", required = false) int choice) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setView(new RedirectView("/board/76/articles"));

		String resultStr = "";
		for (int i = 0; i < result.length; i++) {
			resultStr += result[i];
			log.info(result[i]);
		}
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(resultStr.substring(1, resultStr.length() - 1));
			//4. To JsonObject
			JSONObject jsonObj = (JSONObject) obj;
		log.info("JSOn: {}", jsonObj.toString());
			Integer articleNo = Integer.parseInt((String) jsonObj.get("articleNo"));
			Integer memberNoNo = Integer.parseInt((String) jsonObj.get("memberNo"));
			String proGrade = (String) jsonObj.get("prograde");

			if (choice == 0) {
				this.articleService.removeGradeArticle(articleNo);
			} else if (choice == 1) {
				this.memberService.modifyGrade(memberNoNo, proGrade);
				this.articleService.removeGradeArticle(articleNo);
			}
		return mv;
	}
}

