package com.example.musicat.repository.member;

import com.example.musicat.domain.member.MemberVO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface MemberDao {

	// 양 ~
	public MemberVO selectMemberByEmail(String email);

	// ~ 양

	public void test();

	public MemberVO selectMember(int no);

	public MemberVO selectMember(String email, String password) throws Exception;

	public void updateLastDate(int no);

	// 회원 가입
	void insertMember(MemberVO mVo);

	void insertMemberGrade(int memberNo);

	int joinCheck(Map<String, Object> map);

	
	//회원 자진 탈퇴
	void updateMember(int memberNo, String password);
	
	//비밀번호 재설정
	void updatePassword(int memNo, String newPassword);

	MemberVO selectMember(String email);

	MemberVO selectMemberProfile(int member_no);


	boolean selectNickname(String nickname);

	//비밀번호 변경
	void updatePassword(MemberVO memberVo);


	ArrayList<MemberVO> selectSearchMember(int startRow, int memberPerPage, String keyfield, String keyword);

	int updateTempPassword(MemberVO mVo) throws Exception;


	String selectMemberPassword(int memberNo);

}
