package com.example.musicat.domain.board;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("bestArticleVo")
public class BestArticleVO {

	private int no;
	private int articleNo;
	private String subject;
	private int likecount;
	private String systemFileName;
	private int rank; // 순위
}
