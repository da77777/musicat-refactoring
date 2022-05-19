package com.example.musicat.domain.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("replyVo")
public class ReplyVO {

    private int no;
    private int articleNo;
    private int memberNo;
    private String nickname;
    private String writeDate;
    private String content;
    private int groupNo; // 댓글 그룹
    private int sequence; // 원글과의 거리
    private int depth; // 원글, 대댓글
    private int hide; // 원글 삭제 여부


    //==생성 메소드==//
    public static ReplyVO createReply(int articleNo, int memberNo, String nickname, String content){
        ReplyVO replyVO = new ReplyVO();
        replyVO.articleNo = articleNo;
        replyVO.memberNo = memberNo;
        replyVO.nickname = nickname;
        replyVO.content = content;
        return replyVO;
    }

    //답글
    public static ReplyVO createDepthReply(int articleNo, int memberNo, String nickname, String content, int depth, int grpNo) {
        ReplyVO replyVO = new ReplyVO();
        replyVO.articleNo = articleNo;
        replyVO.memberNo = memberNo;
        replyVO.nickname = nickname;
        replyVO.content = content;
        replyVO.setDepth(depth);
        replyVO.setGroupNo(grpNo);
        return replyVO;
    }

    //==비즈니스 로직==//
    public static ReplyVO updateReply(int replyNo, String content) {
        ReplyVO replyVO = new ReplyVO();
        replyVO.no = replyNo;
        replyVO.content = content;
        return replyVO;
    }



    public ReplyVO() {
    }

}
