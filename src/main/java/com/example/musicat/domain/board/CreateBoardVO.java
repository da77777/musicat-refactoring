package com.example.musicat.domain.board;

import java.util.List;

import com.example.musicat.domain.member.GradeVO;

import lombok.Data;

@Data
public class CreateBoardVO {
	private List<CategoryVO> categoryList;
	private List<GradeVO> gradeList;
	private List<BoardVO> boardkindList;
	private BoardBoardGradeVO bbg;
}
