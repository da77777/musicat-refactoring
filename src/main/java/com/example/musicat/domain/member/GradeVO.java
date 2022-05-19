package com.example.musicat.domain.member;

import com.example.musicat.domain.board.CategoryVO;
import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Alias("gradeVo")
public class GradeVO {
	private int gradeNo;
	private String name;
	private String grade;
	private int docs;
	private int comms;
	private int person;

	public GradeVO(int gradeNo, String name, int docs, int comms) {
		super();
		this.gradeNo = gradeNo;
		this.name = name;
		this.docs = docs;
		this.comms = comms;
	}

	private List<GradeVO> gradeList = new ArrayList<>();

}
