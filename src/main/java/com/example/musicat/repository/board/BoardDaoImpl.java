package com.example.musicat.repository.board;

import java.util.ArrayList;
import java.util.List;

import com.example.musicat.domain.board.BoardBoardGradeVO;
import com.example.musicat.domain.board.BoardGradeVO;
import com.example.musicat.domain.board.BoardVO;
import com.example.musicat.mapper.board.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import lombok.extern.java.Log;

@Log
@Repository("boardDao")
public class BoardDaoImpl implements BoardDao {
	
	@Autowired
	private BoardMapper boardMapper;

	//게시판 추가
	@Override
	public void insertBoard(BoardVO boardVo) {
		this.boardMapper.insertBoard(boardVo);
	}

	@Override
	public int selectLastInsertBoardNo() {
		return this.boardMapper.selectLastInsertBoardNo();
	}

	@Override
	public void insertBoardGrade(BoardGradeVO boardGradeVo) {
		this.boardMapper.insertBoardGrade(boardGradeVo);		
	}

	//게시판 종류 조회
	@Override
	public ArrayList<BoardVO> selectBoardkind() {
		return this.boardMapper.selectBoardkind();
	}
	
	//연결된 게시글 조회
	@Override
	public int selectConnectArticle(int boardNo) {
		return this.boardMapper.selectConnectArticle(boardNo);
	}

	//게시판 삭제
	@Override
	public void deleteBoard(int boardNo) {
		this.boardMapper.deleteBoard(boardNo);
	}

	//게시판 수정
	@Override
	public void updateBoard(BoardVO boardVo, BoardGradeVO boardGradeVo) {
		this.boardMapper.updateBoard(boardVo, boardGradeVo);
	}

	//게시판 정보 조회
	@Override
	public BoardBoardGradeVO selectOneBoard(int boardNo) {
		return this.boardMapper.selectOneBoard(boardNo);
	}
	
	//게시판 정보 조회(전체)
	@Override
	public ArrayList<BoardBoardGradeVO> selectAllBoard() {
		return this.boardMapper.selectAllBoard();
	}

	//게시판 이름 중복 검사
	@Override
	public Integer selectDuplicatedBoard(String boardName) {
		return this.boardMapper.selectDuplicatedBoard(boardName);
	}

	//즐겨찾기 게시판 추가
	@Override
	public void insertLikeBoard(int memberNo, int boardNo) {
		this.boardMapper.insertLikeBoard(memberNo, boardNo);
	}
	
	//즐겨찾기 게시판 삭제
	@Override
	public void deleteLikeBoard(int memberNo, int boardNo) {
		this.boardMapper.deleteLikeBoard(memberNo, boardNo);
	}

	//해당 게시판이 회원의 즐겨찾기 게시판 목록에 존재하는지 확인
	@Override
	public int selectLikeBoard(int memberNo, int boardNo) {
		return this.boardMapper.selectLikeBoard(memberNo, boardNo);
	}

	//즐겨찾기 게시판 조회
	@Override
	public ArrayList<BoardVO> selectLikeBoardList(int no) {
		return this.boardMapper.selectLikeBoardList(no);
	}







}
