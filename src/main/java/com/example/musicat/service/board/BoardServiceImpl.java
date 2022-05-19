package com.example.musicat.service.board;

import java.util.ArrayList;
import java.util.List;

import com.example.musicat.domain.board.BoardBoardGradeVO;
import com.example.musicat.domain.board.BoardGradeVO;
import com.example.musicat.domain.board.BoardVO;
import com.example.musicat.repository.board.BoardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("boardService")
@Transactional
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao boardDao;

	// 게시판 추가(기본정보 + 등급)
	@Override
	public void registerBoard(BoardVO boardVo, BoardGradeVO boardGradeVo) {
		this.boardDao.insertBoard(boardVo);
		boardGradeVo.setBoardNo(this.boardDao.selectLastInsertBoardNo());
		this.boardDao.insertBoardGrade(boardGradeVo);
	}

	// 게시판 종류 조회
	@Override
	@Transactional(readOnly = true)
	public ArrayList<BoardVO> retrieveBoardkind() {
		return this.boardDao.selectBoardkind();
	}

	// 연결된 게시글 조회
	@Override
	@Transactional(readOnly = true)
	public int retrieveConnectArticle(int boardNo) {
		return this.boardDao.selectConnectArticle(boardNo);
	}



	@Override
	public int checkWriteGrade(int boardNo, int gradeNo) {
		BoardBoardGradeVO boardBoardGradeVO = boardDao.selectOneBoard(boardNo);
		int writeGrade = boardBoardGradeVO.getBoardGradeVo().getWriteGrade();
		int bgCheck = (writeGrade >= gradeNo)? 1 : 0;
		return bgCheck;
	}



	// 게시판 삭제
	@Override
	public void removeBoard(int boardNo) {
		this.boardDao.deleteBoard(boardNo);

	}

	// 게시판 수정
	@Override
	public void modifyBoard(BoardVO boardVo, BoardGradeVO boardGradeVo) {
		this.boardDao.updateBoard(boardVo, boardGradeVo);
	}

	// 게시판 정보 조회
	@Override
	@Transactional(readOnly = true)
	public BoardBoardGradeVO retrieveOneBoard(int boardNo) {
		return this.boardDao.selectOneBoard(boardNo);
	}

	//게시판 이름 중복 여부 조회
	@Override
	@Transactional(readOnly = true)
	public Integer retrieveDuplicatedBoard(String boardName) {
		return this.boardDao.selectDuplicatedBoard(boardName);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BoardVO> retrieveAllWriteBoard(int gradeNo) {
		ArrayList<BoardBoardGradeVO> boardAndGradeList = this.boardDao.selectAllBoard();
		List<BoardBoardGradeVO> writeBoardList = new ArrayList<>();
		List<BoardVO> resultBoardList = new ArrayList<>();
		for (BoardBoardGradeVO bbg : boardAndGradeList) {
			// 쓰기 등급 List 가져오기
			if (bbg.getBoardGradeVo().getReadwrite() == 0) {
				writeBoardList.add(bbg);
//				log.info("wrteList: " + bbg.toString());
			}
		}
		for (BoardBoardGradeVO write : writeBoardList) {
			if (write.getBoardGradeVo().getGradeNo() >= gradeNo) {
				resultBoardList.add(write.getBoardVo());
//				log.info("**Result**" + write.toString());
			}
		}
		return resultBoardList;
	}

	@Override
	public boolean retrieveAllReadBoard(int boardNo, int gradeNo) {
		BoardBoardGradeVO bbgVO = this.boardDao.selectOneBoard(boardNo);
		if (bbgVO.getBoardGradeVo().getReadGrade() >= gradeNo) {
			return true;
		} else {
			return false;
		}

	}

	//즐겨찾기 게시판 추가
	@Override
	public void registerLikeBoard(int memberNo, int boardNo) {
		this.boardDao.insertLikeBoard(memberNo, boardNo);
	}

	//즐겨찾기 게시판 삭제
	@Override
	public void removeLikeBoard(int memberNo, int boardNo) {
		this.boardDao.deleteLikeBoard(memberNo, boardNo);
	}

	//해당 게시판이 회원의 즐겨찾기 게시판 목록에 존재하는지 확인
	@Override
	@Transactional(readOnly = true)
	public int retrieveLikeBoard(int memberNo, int boardNo) {
		return this.boardDao.selectLikeBoard(memberNo, boardNo);
	}

	//즐겨찾기 게시판 조회
	@Override
	@Transactional(readOnly = true)
	public ArrayList<BoardVO> retrieveLikeBoardList(int memberNo) {
		return this.boardDao.selectLikeBoardList(memberNo);
	}
}
