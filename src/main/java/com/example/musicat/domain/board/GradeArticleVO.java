package com.example.musicat.domain.board;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("gradeArticleVo")
public class GradeArticleVO {

    private int no;
    private int memberNo;
    private String nickname;
    private String proGrade;
    private String nowGrade;
    private int visits;
    private int docs;
    private int comms;
    private String regdate;
    private String writedate;
    private String content;
} 