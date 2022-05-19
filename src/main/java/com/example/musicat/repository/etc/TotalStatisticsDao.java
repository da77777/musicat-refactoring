package com.example.musicat.repository.etc;

import com.example.musicat.domain.etc.TotalStatisticsVO;

public interface TotalStatisticsDao {

	TotalStatisticsVO selectTotal();

	void insertAndupdateTotal();

}
