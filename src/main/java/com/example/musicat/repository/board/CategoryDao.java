package com.example.musicat.repository.board;

import com.example.musicat.domain.board.CategoryBoardVO;
import com.example.musicat.domain.board.CategoryVO;

import java.util.ArrayList;


public interface CategoryDao {

	public void insertCategory(String categoryName);
	
	public void updateCategory(int categoryNo, String categoryName);
	
	public void deleteCategory(int categoryNo);

	public int selectConnectBoard(int categoryNo);

	public CategoryVO selectOneCategory(int categoryNo);
	
	public ArrayList<CategoryVO> selectCategoryList();
	
	public ArrayList<CategoryBoardVO> selectCategoryBoardList();

	public Integer selectDuplicatedCategory(String categoryName);
	


}
