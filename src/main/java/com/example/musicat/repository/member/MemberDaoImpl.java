package com.example.musicat.repository.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.musicat.domain.member.MemberVO;
import com.example.musicat.mapper.member.MemberMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


import lombok.extern.java.Log;

//여기부터가 기본 세팅
@Repository("memberDao") // Dao라고 알려주는 거 이름은 괄호 안에 있는거
@Log
public class MemberDaoImpl implements MemberDao { // @Autowired가 속해 있는 클래스
	@Autowired // 밑에 선언한 애랑 얘가 속해있는 클래스랑 연결을 자동으로 해주는 거 (속해있는 클래스가 memberDao)
	private SqlSession sqlSession; // @Autowired 밑에 선언 된 mybatis //mybatis는 db 명령문을 자동으로 입력해주는 다른 사람이 만들어놓은
									// 프로그램?명령문? 이라고 보면 된다.

	@Qualifier("memberMapper")
	@Autowired
	private MemberMapper mapper;
//기본 세팅 끝

	// 양 ~
	// 로그인 - email을 통해 session에 담을 정보를 조회하고 memberVo에 담음
	@Override
	public MemberVO selectMemberByEmail(String email) {
		MemberVO memberVo = this.mapper.selectMemberByEmail(email);
		//log.info("Email, password, auth(grade) : " + memberVo.getEmail() + " " + memberVo.getPassword() + " " +  memberVo.getGrade());
		return memberVo;
	}

	// ~ 양

	@Override
	public void test() {}

	// 회원가입
	@Override
	public void insertMember(MemberVO mVo) { // 자바스크립트의 MemberDao에 있는거 리턴부터 변수명(파라미터값) 그대로 복붙
		//this.sqlSession.insert("Member.join", mVo); //(mapper.xml에 있는 매퍼명.해당 id, mVo는 변수명)
		this.mapper.insertMember(mVo);
	}

	@Override
	public void insertMemberGrade(int memberNo){
		this.mapper.insertMemberGrade(memberNo);
	}

	//중복체크(이메일, 닉네임)
	@Override
	public int joinCheck(Map<String, Object> map) {
		return this.mapper.joinCheck(map);
	}


	// 회원 자진 탈퇴
	@Override
	public void updateMember(int memberNo, String password) {
		MemberVO memberVo = new MemberVO();
		memberVo.setNo(memberNo);
		memberVo.setPassword(password);
		this.mapper.updateMember(memberVo);
	}

	// 비밀번호 재설정
	@Override
	public void updatePassword(int memNo, String newPassword) {
		this.mapper.updatePassword(memNo, newPassword);
	}


	@Override
	public void updateLastDate(int no) {
		// TODO Auto-generated method stub
		mapper.updateLastDate(no);
	}

	@Override
	public MemberVO selectMember(String email, String password) throws Exception {
		return mapper.selectMember_byIDPwd(email, password);
	}

	@Override
	public MemberVO selectMember(int no) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public MemberVO selectMember(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberVO selectMemberProfile(int member_no) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean selectNickname(String nickname) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public void updatePassword(MemberVO memberVo) {
		// TODO Auto-generated method stub
		this.mapper.updatePassword(memberVo);
  }
  
	public int updateTempPassword(MemberVO mVo) throws Exception {
		// TODO Auto-generated method stub
		return this.mapper.updateTempPassword(mVo);
	}

	@Override
	public ArrayList<MemberVO> selectSearchMember(int startRow, int memberPerPage, String keyfield, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectMemberPassword(int memberNo) {
		return this.selectMemberPassword(memberNo);
	}
}
