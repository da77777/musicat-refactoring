package com.example.musicat.service.etc;

import com.example.musicat.domain.etc.TotalStatisticsVO;

public interface TotalStatisticsService {

	TotalStatisticsVO retrieve();

	void registerAndmodifyTotal();

}
