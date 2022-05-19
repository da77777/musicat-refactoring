package com.example.musicat.service.member;

import java.util.ArrayList;
import java.util.Map;

import com.example.musicat.domain.member.GradeVO;

public interface GradeService {

	// 등급 조회
	ArrayList<GradeVO> retrieveGradeList();

	// 등급 수정
	void modifyGrade(GradeVO grade) throws Exception;
	
	// 등급 삭제 시
	void removeGrade(int no) throws Exception;
	
	// 등급 정렬
	void sortGrade() throws Exception;
	
	// 등급 게시판 열람
	Map<String, Object> retrieveGradeBoardList(int gradeNo) throws Exception;
}
