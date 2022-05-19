package com.example.musicat.service.etc;

import com.example.musicat.domain.etc.TotalStatisticsVO;
import com.example.musicat.repository.etc.TotalStatisticsDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service("totalService")
public class TotalStatisticsServiceImpl implements TotalStatisticsService {

	@Autowired
	private TotalStatisticsDaoImpl totalDaoImpl;

	//	@Transactional(readOnly = true)
	@Override
	public TotalStatisticsVO retrieve() {
		return this.totalDaoImpl.selectTotal();
	}

	@Override
	public void registerAndmodifyTotal() {
		this.totalDaoImpl.insertAndupdateTotal();

	}

}