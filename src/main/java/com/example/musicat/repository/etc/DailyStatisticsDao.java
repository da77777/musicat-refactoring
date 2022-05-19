package com.example.musicat.repository.etc;

import java.util.ArrayList;

import com.example.musicat.domain.etc.DailyStatisticsVO;

public interface DailyStatisticsDao {

	DailyStatisticsVO selectDaily();

	ArrayList<DailyStatisticsVO> selectList();

	void insertAndupdateDaily();

}
