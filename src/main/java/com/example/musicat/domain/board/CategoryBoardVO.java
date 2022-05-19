package com.example.musicat.domain.board;


import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Repository;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Alias("categoryBoardVo")
public class CategoryBoardVO {
	private CategoryVO categoryVo;
	private BoardVO boardVo;

}
