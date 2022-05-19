package com.example.musicat.service.board;

import java.util.List;

import com.example.musicat.domain.board.ReplyVO;
import com.example.musicat.mapper.member.MemberMapper;
import com.example.musicat.repository.board.ReplyDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service("replyService")
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

	private final ReplyDao replyDao;
	private final MemberMapper memberMapper;


	@Override
	public List<ReplyVO> retrieveAllReply(int articleNo) {
		return this.replyDao.selectAllReply(articleNo);
	}

	@Override
	public void registerReply(ReplyVO reply) {
		this.memberMapper.plusMemberComms(reply.getMemberNo());
		this.replyDao.insertReply(reply);
	}

	@Override
	public void modifyReply(ReplyVO reply) {
		this.replyDao.updateReply(reply);
	}

	@Override
	public void removeReply(int replyNo, int memberNo) {
		this.memberMapper.minusMemberComms(memberNo);
		this.replyDao.deleteReply(replyNo);
	}

	//내가 쓴 댓글 조회
	@Override
	public List<ReplyVO> retrieveReplyOneMember(int memberNo) {
		return this.replyDao.selectReplyOneMember(memberNo);
	}
}
