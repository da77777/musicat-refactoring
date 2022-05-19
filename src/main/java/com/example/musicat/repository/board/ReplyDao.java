package com.example.musicat.repository.board;

import com.example.musicat.domain.board.ReplyVO;

import java.util.List;


public interface ReplyDao {

	//댓글 모두 조회
	List<ReplyVO> selectAllReply(int articleNo);

	//댓글 등록
	void insertReply(ReplyVO reply);

	//댓글 수정
	void updateReply(ReplyVO reply);

	//댓글 삭제
	void deleteReply(int replyNo);

	//내가 쓴 댓글
	List<ReplyVO> selectReplyOneMember(int memberNo);
}
