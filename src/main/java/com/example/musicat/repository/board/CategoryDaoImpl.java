package com.example.musicat.repository.board;

import java.util.ArrayList;

import com.example.musicat.domain.board.CategoryBoardVO;
import com.example.musicat.domain.board.CategoryVO;
import com.example.musicat.mapper.board.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.musicat.mapper.board.CategoryMapper;
import com.example.musicat.domain.board.CategoryBoardVO;
import com.example.musicat.domain.board.CategoryVO;

import lombok.extern.java.Log;


@Log
@Repository("categoryDao")
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private CategoryMapper categoryMapper;
	
	@Override
	public void insertCategory(String categoryName) {
		this.categoryMapper.insertCategory(categoryName);
	}

	@Override
	public void updateCategory(int categoryNo, String categoryName) {
		this.categoryMapper.updateCategory(categoryNo, categoryName);	
	}

	@Override
	public void deleteCategory(int categoryNo) {
		this.categoryMapper.deleteCategory(categoryNo);
	}
	
	@Override
	public int selectConnectBoard(int categoryNo) {
		return this.categoryMapper.selectConnectBoard(categoryNo);
	}

	@Override
	public CategoryVO selectOneCategory(int categoryNo) {
		return this.categoryMapper.selectOneCategory(categoryNo);
	}

	@Override
	public ArrayList<CategoryVO> selectCategoryList() {
		return this.categoryMapper.selectCategoryList();
	}
	
	//카테고리 + 게시판 목록 조회
	@Override
	public ArrayList<CategoryBoardVO> selectCategoryBoardList() {
		return this.categoryMapper.selectCategoryBoardList();
	}

	@Override
	public Integer selectDuplicatedCategory(String categoryName) {
		return this.categoryMapper.selectDuplicatedCategory(categoryName);
	}
}
