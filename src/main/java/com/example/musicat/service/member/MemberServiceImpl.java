package com.example.musicat.service.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.musicat.domain.member.MemberVO;
import com.example.musicat.domain.paging.Criteria;
import com.example.musicat.mapper.member.GradeMapper;
import com.example.musicat.mapper.member.MemberMapper;
import com.example.musicat.repository.member.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("memberService") // 얘는 서비스다
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberdao;// memberDao랑 연결해주겠다
//	@Autowired
//	private BCryptPasswordEncoder encodePwd;

	@Qualifier("memberMapper")
	@Autowired
	private MemberMapper memberMapper;

	//email로 회원 정보 조회
	@Override
	public MemberVO retrieveMemberByEmail(String email) {
		MemberVO memberVo = memberdao.selectMemberByEmail(email);
		return memberVo;
	}

	@Transactional
	@Override // 회원가입
	public void registerMember(MemberVO mVo) {
		this.memberdao.insertMember(mVo); //this를 적어주는 이유는 @Autowired 연결 선언해준 memberDao랑 같은 애라는걸 알려주려고 적는 거임 (얘가 얘다)
		this.memberdao.insertMemberGrade(mVo.getNo()); //membergrade insert
		// rest에 MemberNo 넘기면된다.
	}

	@Override
	@Transactional
	public int joinCheck(Map<String, Object> map) {
		return this.memberdao.joinCheck(map);
	}

	@Override //회원 자진 탈퇴
	@Transactional
	public void modifyMember(int memberNo, String password) {
		memberdao.updateMember(memberNo, password);	//@Autowired해서 memberdao로 씀.

	}

	@Override
	public String passwordCheck(int memberNo) {
		String password = this.memberMapper.selectMemberPassword(memberNo);
		return password;
	}

	@Override //비밀번호 재설정
	@Transactional
	public void modifyPassword(int memNo, String newPassword) throws Exception{
		memberdao.updatePassword(memNo, newPassword);
	}
	
//	@Override
//	public MemberVO login(String email, String password) throws Exception {
//		MemberVO member = memberdao.selectMember(email, password);
//
//		if (member != null)
//			memberdao.updateLastDdate(member.getNo());
//
//		return member;
//	}

	@Override // 회원 목록 조회
	public ArrayList<MemberVO> retrieveMemberList(Criteria crt) throws Exception {
		return this.memberMapper.selectMemberList(crt);
	}

	@Override // 회원의 총 수
	public int retrieveTotalMember() throws Exception {
		return this.memberMapper.selectTotalMember();
	}

	@Override // 회원 상세 조회
	public MemberVO retrieveMemberByManager(int no) throws Exception {
		return this.memberMapper.selectMemberByManager(no);
	}



	@Override // 회원 검색 조회
	public ArrayList<MemberVO> retrieveSearchMember(String keyfield, String keyword, Criteria crt) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
	
		map.put("keyword", keyword);
		map.put("crt", crt);

		if (keyfield.equals("email")) {
			return this.memberMapper.selectSearchMemberByEmail(map);
		} else {
			return this.memberMapper.selectSearchMemberByGrade(map);
		}
	}

	@Override // 회원 검색 총 수
	public int retrieveTotalSearchMember(String keyfield, String keyword) throws Exception{
		if(keyfield.equals("email")) {
			return this.memberMapper.selectTotalSearchMemberByEmail(keyword);
		}
		else {
			return this.memberMapper.selectTotalSearchMemberByGrade(keyword);
		}
	}

	@Override // 회원의 정지기간 업데이트
	@Transactional
	public void modifyBan(String banSelect, int no) throws Exception{
		if (banSelect.equals("7d")) {
			this.memberMapper.updateBan7days(no);
		} else if (banSelect.equals("1d")) {
			this.memberMapper.updateBan1day(no);
		} else {
			this.memberMapper.updateBan1minute(no);
		}
	}

	@Override // 회원의 강제 탈퇴
	@Transactional
	public void modifyMemberByForce(int no)throws Exception {
		this.memberMapper.updateMemberByForce(no);
	}

	@Override // 회원의 게시글 +1
	@Transactional
	public void upMemberDocs(int no) {
		this.memberMapper.plusMemberDocs(no);
	}

	@Override // 회원의 게시글 -1
	@Transactional
	public void downMemberDocs(int no) {
		this.memberMapper.minusMemberDocs(no);
	}

	@Override // 회원의 댓글 +1
	@Transactional
	public void upMemberComms(int no) {
		this.memberMapper.plusMemberComms(no);
	}

	@Override // 회원의 댓글 -1
	@Transactional
	public void downMemberComms(int no) {
		this.memberMapper.minusMemberComms(no);
	}

	@Override
	public MemberVO retreiveMemberProfile(int member_no) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public boolean retrieveNickname(String nickname) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional
	public void updatePassword(MemberVO memberVo) {
		// TODO Auto-generated method stub
		this.memberdao.updatePassword(memberVo);
  }

	@Transactional
	public int updateTempPassword(MemberVO mVo) {
		// TODO Auto-generated method stub
		
		int result = 0;
		try {
			result = this.memberdao.updateTempPassword(mVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return result;
		}
		return result;
	}

	@Override
	@Transactional
	public void modifyLastDate(int no) {
		this.memberdao.updateLastDate(no);
	}

	@Override
	@Transactional
	public void modifyGrade(int no, String grade) {
		int gradeNo = 0;
		if(grade.equals("MANAGER")){
			log.info("manager");
			gradeNo = 2;
		}
		else if (grade.equals("USER")){
			log.info("user");
			gradeNo = 3;
		}
		this.memberMapper.updateGrade(no, gradeNo);
	}
}
