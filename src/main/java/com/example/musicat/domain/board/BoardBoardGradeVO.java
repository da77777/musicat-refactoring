package com.example.musicat.domain.board;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("boardBoardGradeVo")
public class BoardBoardGradeVO {

	private BoardVO boardVo;
	private BoardGradeVO boardGradeVo;
}
