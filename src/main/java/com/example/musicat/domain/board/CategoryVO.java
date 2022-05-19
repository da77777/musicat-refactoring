package com.example.musicat.domain.board;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Alias("categoryVo")
@ToString
public class CategoryVO {
    private int categoryNo;

    private String categoryName;

    private List<CategoryVO> categoryList = new ArrayList<>();
    private List<BoardVO> boardList = new ArrayList<>();

    //카테고리, 게시판 목록 조회 시 사용
    public void addBoardList(BoardVO board) {
        this.boardList.add(board);
    }

}
