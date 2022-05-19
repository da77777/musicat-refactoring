package com.example.musicat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.example.musicat.domain.etc.NoteVO;
import com.example.musicat.domain.member.MemberVO;
import com.example.musicat.service.etc.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class NoteController {
	@Autowired
	NoteService service;
	
	private static final int POST_PER_PAGE = 3;
	private static final int PAGE_BLOCK = 3;

	@PostMapping("/deletenote")
	public String delete(@RequestParam("deleteNote") List<Integer> list, int isRecieve, HttpSession session) {
		
		MemberVO user = (MemberVO) session.getAttribute("loginUser");
		System.out.println("isRecieve : " + isRecieve);
		System.out.println(list.get(0));
		List<NoteVO> notelist = new ArrayList<NoteVO>();
		
		for( Integer no : list) {
			NoteVO temp = new NoteVO();
			temp.setNotecontent_no(no);
			temp.setSendrecieve(isRecieve);
			temp.setMember_no(user.getNo());
			notelist.add(temp);
		}
		
		service.removeNote((ArrayList<NoteVO>) notelist, isRecieve);
		
		return "redirect:notelist?isRecieve=" + isRecieve;
	}

	@ResponseBody
	@PostMapping("/sendnote")
	public Map<String, String> sendNote(int counterpartNo, String counterpartNickname, String content, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		MemberVO user = (MemberVO) session.getAttribute("loginUser");
		
		NoteVO note = new NoteVO();
		note.setCounterpart_no(counterpartNo);
		note.setCounterpart_nickname(counterpartNickname);
		note.setContent(content);
		
		map.put("isSuccess", Boolean.toString(service.registerNote(note, user)));
		
		return map;
	}
	
	@GetMapping("/writenote")
	public String wirteNote(@RequestParam int memberNo, Model model, HttpSession session) {
		MemberVO user = (MemberVO) session.getAttribute("loginUser");
		
		if(memberNo == user.getNo()) {
			model.addAttribute("isSelf", 1);
		}
		else {
			model.addAttribute("isSelf", 0);	
			
			// 멤버 dao에 MemberVo selectMemberProfile(int member_no) 추가 되면 그걸로 바꿔야함
			MemberVO counterpart = new MemberVO();
			counterpart.setNo(2);
			counterpart.setNickname("이이이");
			
			model.addAttribute("counterpart", counterpart);
		}
		
		return "view/etc/writenote";
	}
	
	@GetMapping("/notedetail")
	public String noteDetail(@RequestParam int isRecieve, @RequestParam int noteContentNo, @RequestParam int noteNo, Model model, HttpSession session) {
		MemberVO user = (MemberVO)session.getAttribute("loginUser");
		
		NoteVO note = service.retriveNote(noteNo);
		
		// 받은 쪽지를 상세조회 할 경우
		if(isRecieve == 1 && note.isRead() == false)
			service.updateRead(noteContentNo, user.getNo());

		model.addAttribute("note", note);
		model.addAttribute("isRecieve", isRecieve);
		
		return "view/etc/notedetail";
	}
	
	@GetMapping("/notelist")
	public String noteList(@RequestParam(defaultValue="1") int isRecieve, @RequestParam(defaultValue="1") int currentPage, Model model, HttpSession session) {
		MemberVO user = (MemberVO) session.getAttribute("loginUser");
		//log.info(user.toString());
		
		int startRow = (currentPage - 1) * POST_PER_PAGE;
		
		List<NoteVO> notelist = service.selectNoteList(user.getNo(), isRecieve, POST_PER_PAGE, startRow);
		//notelist.forEach(m -> System.out.println(m.toString()));
		model.addAttribute("notelist", notelist);
		model.addAttribute("isRecieve", isRecieve);
		
		//int totalPostCount = service.retriveTotalNoteCount(user.getNo(), isRecieve);
		//PageInfo pageinfo = new PageInfo(currentPage, PAGE_BLOCK, POST_PER_PAGE, totalPostCount);
		//model.addAttribute("pageInfo", pageinfo);
		int currentBlock = currentPage % PAGE_BLOCK == 0 ? currentPage / PAGE_BLOCK : currentPage / PAGE_BLOCK + 1;

		int startPage = 1 + (currentBlock - 1) * PAGE_BLOCK;
		int endPage = startPage + (PAGE_BLOCK - 1);
		
		System.out.println("startPage : " + startPage);
		
		int totalPostCount = service.retriveTotalNoteCount(user.getNo(), isRecieve);

		int totalPage = totalPostCount % POST_PER_PAGE == 0 ? totalPostCount / POST_PER_PAGE : totalPostCount / POST_PER_PAGE + 1;
		System.out.println("totalPage : " + totalPage);
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		if(endPage <= 0)
			endPage = startPage;
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pageBlock", PAGE_BLOCK);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalPostCount", totalPostCount);
		model.addAttribute("postSize", POST_PER_PAGE);
		
		
		return "view/etc/note";
	}
	
	
}
