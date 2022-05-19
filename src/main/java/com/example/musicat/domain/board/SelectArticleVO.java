package com.example.musicat.domain.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import lombok.Data;

@Getter
@Setter
@ToString
@Alias("selectArticleVo")
public class SelectArticleVO {
	
	private ArticleVO article;
	private FileVO file;
}
