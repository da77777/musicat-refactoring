package com.example.musicat.service.board;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.musicat.domain.board.BoardVO;
import com.example.musicat.domain.board.CategoryBoardVO;
import com.example.musicat.domain.board.CategoryVO;
import com.example.musicat.repository.board.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import lombok.extern.java.Log;
import org.springframework.transaction.annotation.Transactional;

@Log
@Repository("categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	@Override
	public void registerCategory(String categoryName) {
		this.categoryDao.insertCategory(categoryName);
	}

	@Override
	public void modifyCategory(int categoryNo, String categoryName) {
		this.categoryDao.updateCategory(categoryNo, categoryName);
	}

	@Override
	public void removeCategory(int categoryNo) {
		this.categoryDao.deleteCategory(categoryNo);
	}

	@Override
	@Transactional(readOnly = true)
	public int retrieveConnectBoard(int categoryNo) {
		return this.categoryDao.selectConnectBoard(categoryNo);
	}

	@Override
	@Transactional(readOnly = true)
	public CategoryVO retrieveOneCategory(int categoryNo) {
		return this.categoryDao.selectOneCategory(categoryNo);
	}

	@Override
	@Transactional(readOnly = true)
	public ArrayList<CategoryVO> retrieveCategoryList() {
		return this.categoryDao.selectCategoryList();
	}

	// 카테고리 + 게시판 목록 조회
	@Override
	@Transactional(readOnly = true)
	public ArrayList<CategoryVO> retrieveCategoryBoardList() {
		ArrayList<CategoryBoardVO> list = this.categoryDao.selectCategoryBoardList();

		ArrayList<CategoryVO> categoryList = new ArrayList<CategoryVO>();
		HashMap<String, CategoryVO> checkCategory = new HashMap<String, CategoryVO>();

		CategoryVO category = null;

		for (CategoryBoardVO temp : list) {
			if (checkCategory.containsKey(temp.getCategoryVo().getCategoryName())) {
				category = checkCategory.get(temp.getCategoryVo().getCategoryName());
			} else {
				category = new CategoryVO();
				category.setCategoryNo(temp.getCategoryVo().getCategoryNo());
				category.setCategoryName(temp.getCategoryVo().getCategoryName());
				checkCategory.put(category.getCategoryName(), category);
				categoryList.add(category);
			}
			if (temp.getBoardVo().getBoardName() != null)
				category.addBoardList(new BoardVO(temp.getBoardVo().getBoardNo(), temp.getBoardVo().getBoardName(), temp.getBoardVo().getBoardkind()));
		}
		return categoryList;
	}

	@Override
	@Transactional(readOnly = true)
	public Integer retrieveDuplicatedCategory(String categoryName) {
		return this.categoryDao.selectDuplicatedCategory(categoryName);
	}




}
