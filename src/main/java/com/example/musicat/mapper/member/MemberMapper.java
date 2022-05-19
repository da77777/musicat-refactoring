package com.example.musicat.mapper.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.musicat.domain.member.MemberVO;
import com.example.musicat.domain.paging.Criteria;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
public interface MemberMapper {

	// 양 : Session에 담을 회원 정보
	public MemberVO selectMemberByEmail(String email);

	public MemberVO selectMember_byIDPwd(String email, String password) throws Exception;

	public void updateLastDate(int no) ;

	public MemberVO test();

	// 연주 ----------------------------------------
	// 회원 가입
	void insertMember(MemberVO mVo);

	void insertMemberGrade(int memberNo);

	// 회원가입 중복 체크
	int joinCheck(Map<String, Object> map);


	// 회원 자진 탈퇴
//	void updateMember(int no, String password);
	void updateMember(MemberVO memberVO);
	//void updateMember(int no);

	// 비밀번호 변경
	void updatePassword(int memNo, String newPassword);
	// 끝 -----------------------------------------------



	// 회원 목록 조회
	ArrayList<MemberVO> selectMemberList(Criteria crt) throws Exception;

	// 회원의 총 수
	int selectTotalMember() throws Exception;

	// 해당 회원의 상세 조회
	MemberVO selectMemberByManager(int no);

	// 정지 기간 7일 업데이트
	void updateBan7days(int no);

	// 정지 기간 1일 업데이트
	void updateBan1day(int no);

	// 정지 기간 1분 업데이트
	void updateBan1minute(int no);

	// 회원의 강제 탈퇴
	void updateMemberByForce(int no);

	// 게시글 등록 시 회원의 게시글 수 변동
	void plusMemberDocs(int no);

	// 게시글 삭제 시 회원의 게시글 수 변동
	void minusMemberDocs(int no);

	// 댓글 등록 시 회원의 댓글 수 변동
	void plusMemberComms(int no);

	// 댓글 삭제 시 회원의 댓글 수 변동
	void minusMemberComms(int no);
	
	public int updateTempPassword(MemberVO mVo) throws Exception;

	// MemberVO selectMember(String email, String password);
	// MemberVO selectMember(String email);
	// MemberVO selectMemberProfile(int member_no);
	// void updateBan(String banSelect, int no);

	// boolean selectNickname(String nickname);

	// 이메일 검색에 해당된 회원 조회
	ArrayList<MemberVO> selectSearchMemberByEmail(Map<String, Object> map);

	// 이메일 검색에 해당된 회원 조회
	ArrayList<MemberVO> selectSearchMemberByGrade(Map<String, Object> map);

	// 이메일 검색에 해당된 회원의 총 수
	int selectTotalSearchMemberByEmail(String keyword);

	// 등급 검색에 해당된 회원의 총 수
	int selectTotalSearchMemberByGrade(String keyword);
	
	void updatePassword(MemberVO memberVo);

	// 멤버의 팔로잉 리스트
	List<MemberVO> selectFollowingList(int memberNo);

	// 멤버의 팔로우 리스트
	List<MemberVO> selectFollowedList(int memberNo);

	String selectMemberPassword(int memberNo);

	// 등급조정
	void updateGrade(int no, int gradeNo);
}
