package com.example.musicat.mapper.etc;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.musicat.domain.etc.DailyStatisticsVO;

@Mapper()
public interface DailyStatisticsMapper {

	DailyStatisticsVO selectDaily();

	ArrayList<DailyStatisticsVO> selectList();

	void insertAndupdateDaily();
}
