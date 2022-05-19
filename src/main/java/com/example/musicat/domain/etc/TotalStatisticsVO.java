package com.example.musicat.domain.etc;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("totalVo")
public class TotalStatisticsVO {
	private int totalNo;
	private int totalMember;
	private int totalArticle;
	private int totalView;
	private int totalVisits;

}
