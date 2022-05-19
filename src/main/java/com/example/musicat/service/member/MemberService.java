package com.example.musicat.service.member;

import com.example.musicat.domain.member.MemberVO;
import com.example.musicat.domain.paging.Criteria;

import java.util.ArrayList;
import java.util.Map;


public interface MemberService {

	//public MemberVO login(String email, String password) throws Exception;

    // session에 담을 회원 정보 조회
    public MemberVO retrieveMemberByEmail(String email);
    
	// 회원의 리스트 조회
    ArrayList<MemberVO> retrieveMemberList(Criteria crt) throws Exception;
    // 회원 총 수
    int retrieveTotalMember() throws Exception;
    // 해당 회원의 상세 조회
    MemberVO retrieveMemberByManager(int no)throws Exception;
    // 검색에 해당된 회원 조회
    ArrayList<MemberVO> retrieveSearchMember(String keyfield, String keyword, Criteria crt) throws Exception;
    // 검색에 해당된 회원의 총 수
    int retrieveTotalSearchMember(String keyfield, String keyword) throws Exception;
    // 정지 기간 업데이트
    void modifyBan(String banSelect, int no) throws Exception;
    // 회원의 강제 탈퇴
    void modifyMemberByForce(int no) throws Exception;
    // 게시글 등록 시 회원의 게시글 수 변동
    void upMemberDocs(int no);
    // 게시글 삭제 시 회원의 게시글 수 변동
    void downMemberDocs(int no);
    // 댓글 등록 시 회원의 댓글 수 변동
    void upMemberComms(int no);
    // 댓글 삭제 시 회원의 댓글 수 변동
    void downMemberComms(int no);

	// 회원가입
	MemberVO retreiveMemberProfile(int member_no);

    int joinCheck(Map<String, Object> map);

	//회원 자진 탈퇴
	void modifyMember(int memberNo, String password);

	//비밀번호 재설정
	void modifyPassword(int memNo, String newPassword) throws Exception;

	void registerMember(MemberVO mVo);

	boolean retrieveNickname(String nickname);

	public void updatePassword(MemberVO memberVo);

//	boolean retrieveEmail(String email);

	int updateTempPassword(MemberVO mVo);


  void modifyLastDate(int no);

  String passwordCheck(int memberNo);

  // 등급조정
    void modifyGrade(int no, String grade);

}
