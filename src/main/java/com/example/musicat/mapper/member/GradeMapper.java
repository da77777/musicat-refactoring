package com.example.musicat.mapper.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.musicat.domain.member.GradeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;



@Mapper
public interface GradeMapper {
	// 등급 수정
	void updateGrade(GradeVO grade) throws Exception;

	// 등급 삭제시
	void deleteGrade(int no) throws Exception;

	// 쓰기 등급에 맞는 게시판 조회
	ArrayList<String> selectWriteBoardByGrade(int gradeNo);

	// 읽기 등급에 맞는 게시판 조회
	ArrayList<String> selectReadBoardByGrade(int gradeNo);

	// 등급 번호 DB 조회
	ArrayList<Integer> selectGradeNumber() throws Exception;

	// 회원의 등급 정렬
	void rearrangeMemberGrade() throws Exception;
	
	// 등급 정렬
	void standGrade() throws Exception;
	
	// 등급 삭제를 위한 회원등급 조정
	void rearrangeGrade(int no) throws Exception;
	
	// 등급 번호 재정렬
	void fillEmptyNum() throws Exception;

	// 등급 목록 조회
	ArrayList<GradeVO> selectGradeList();
}
