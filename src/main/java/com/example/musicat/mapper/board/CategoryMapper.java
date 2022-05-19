package com.example.musicat.mapper.board;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.musicat.domain.board.CategoryBoardVO;
import com.example.musicat.domain.board.CategoryVO;

@Mapper
public interface CategoryMapper {
	
	//카테고리 추가
	public void insertCategory(String categoryName);
	
	//카테고리 수정
	public void updateCategory(int categoryNo, String categoryName);
	
	//연결된 게시판 조회
	public int selectConnectBoard(int categoryNo);
	
	//카테고리 삭제
	public void deleteCategory(int categoryNo);
	
	//카테고리 정보 조회
	public CategoryVO selectOneCategory(int categoryNo);
	
	//카테고리 중복 조회
	public Integer selectDuplicatedCategory(String categoryName);
	
	//카테고리 목록 조회
	public ArrayList<CategoryVO> selectCategoryList();
	
	//카테고리 + 게시판 목록 조회
	public ArrayList<CategoryBoardVO> selectCategoryBoardList();
	

	

	
}
