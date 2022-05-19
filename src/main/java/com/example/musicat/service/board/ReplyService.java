package com.example.musicat.service.board;

import com.example.musicat.domain.board.ReplyVO;

import java.util.List;


public interface ReplyService {

	//댓글 세부 조회
	List<ReplyVO> retrieveAllReply(int articleNo);

	//댓글 작성
	void registerReply(ReplyVO reply);

	//댓글 수정
	void modifyReply(ReplyVO reply);

	//댓글 삭제
	void removeReply(int replyNo, int memberNo);

	//내가 쓴 댓글
	List<ReplyVO> retrieveReplyOneMember(int memberNo);

}
