package com.example.musicat.domain.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter @Setter
@ToString
@Alias("likeVo")
public class LikeVO {

    private int no;
    private int memberNo;
    private int articleNo;

    public LikeVO() {
    }

    public int getNo() {
        return no;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public int getArticleNo() {
        return articleNo;
    }

    @Override
    public String toString() {
        return "Like{" +
                "no=" + no +
                ", memberNo=" + memberNo +
                ", articleNo=" + articleNo +
                '}';
    }
}
