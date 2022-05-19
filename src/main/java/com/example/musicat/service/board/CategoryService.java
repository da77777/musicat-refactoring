package com.example.musicat.service.board;

import com.example.musicat.domain.board.CategoryVO;

import java.util.ArrayList;


public interface CategoryService {
	
	public void registerCategory(String categoryName);
	
	public void modifyCategory(int categoryNo, String categoryName);
	
	public void removeCategory(int categoryNo);

	public int retrieveConnectBoard(int categoryNo);

	public CategoryVO retrieveOneCategory(int categoryNo);

	public ArrayList<CategoryVO> retrieveCategoryList();
	
	public ArrayList<CategoryVO> retrieveCategoryBoardList();

	public Integer retrieveDuplicatedCategory(String categoryName);
}
