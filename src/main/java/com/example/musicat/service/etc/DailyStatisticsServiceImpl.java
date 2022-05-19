package com.example.musicat.service.etc;

import java.util.ArrayList;

import com.example.musicat.domain.etc.DailyStatisticsVO;
import com.example.musicat.repository.etc.DailyStatisticsDao;
import com.example.musicat.repository.etc.DailyStatisticsDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("dailyService")
@Transactional
public class DailyStatisticsServiceImpl implements DailyStatisticsService {

	@Autowired
	private DailyStatisticsDaoImpl dailyDao;

	@Transactional(readOnly = true)
	@Override
	public DailyStatisticsVO retrieve() {
		return this.dailyDao.selectDaily();
	}

	//	@Transactional(readOnly = true)
	@Override
	public ArrayList<DailyStatisticsVO> retrieveList() {
		return this.dailyDao.selectList();
	}

	@Override
	public void registerAndmodifyDaily() {

		this.dailyDao.insertAndupdateDaily();

	}


}