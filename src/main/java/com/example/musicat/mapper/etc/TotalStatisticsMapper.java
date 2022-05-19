package com.example.musicat.mapper.etc;

import org.apache.ibatis.annotations.Mapper;

import com.example.musicat.domain.etc.TotalStatisticsVO;

@Mapper()
public interface TotalStatisticsMapper {

	TotalStatisticsVO selectTotal();

	void insertAndupdateTotal();
}
