package com.example.musicat.mapper.board;

import java.util.List;

import com.example.musicat.domain.board.ReplyVO;

public interface ReplyMapper {

	//댓글 모두 조회
	List<ReplyVO> selectAllReply(int articleNo);

	//댓글 등록
	void insertReply(ReplyVO reply);

	//답글 등록
	void insertDepthReply(ReplyVO reply);

	//댓글 수정
	void updateReply(ReplyVO reply);

	//댓글 삭제
	void deleteReply(int replyNo);

	//내가 쓴 댓글
	List<ReplyVO> selectReplyOneMember(int memberNo);
	
}
