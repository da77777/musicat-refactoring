package com.example.musicat.domain.etc;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("dailyVo")
public class DailyStatisticsVO {
	private int dailyNo;
	private String dailyDate;
	private int dailyVisits;
	private int dailyArticle;

}
