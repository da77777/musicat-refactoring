package com.example.musicat.domain.board;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("tagVo")
public class TagVO {

    private int tagNo;
    private int articleNo;
    private String name;

    public TagVO(int articleNo, String name) {
        this.articleNo = articleNo;
        this.name = name;
    }
}
