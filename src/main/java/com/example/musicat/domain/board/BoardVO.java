package com.example.musicat.domain.board;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Alias("boardVo")
public class BoardVO {

    private int categoryNo;

    private int boardNo;
    private String boardName;
    private int boardkind;
    private String boardkindName;

    //카테고리, 게시판 목록 조회 시 사용
    public BoardVO(int boardNo, String boardName, int boardkind) {
        this.boardNo = boardNo;
        this.boardName = boardName;
        this.boardkind = boardkind;
    }


}
