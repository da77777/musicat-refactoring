package com.example.musicat.service.etc;

import com.example.musicat.domain.etc.DailyStatisticsVO;

import java.util.ArrayList;


public interface DailyStatisticsService {

	DailyStatisticsVO retrieve();

	ArrayList<DailyStatisticsVO> retrieveList();

	void registerAndmodifyDaily();


}
