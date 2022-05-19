package com.example.musicat.repository.etc;

import java.util.ArrayList;

import com.example.musicat.domain.etc.DailyStatisticsVO;
import com.example.musicat.mapper.etc.DailyStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("dailyDao")
public class DailyStatisticsDaoImpl implements DailyStatisticsDao {

	private DailyStatisticsMapper dailyMapper;

	private DailyStatisticsVO dailyVo;

	@Autowired
	public DailyStatisticsDaoImpl(DailyStatisticsMapper dailyMapper) {
		this.dailyMapper = dailyMapper;
	}

	@Override
	public DailyStatisticsVO selectDaily() {
		dailyVo = dailyMapper.selectDaily();
		dailyVo.setDailyDate((dailyVo.getDailyDate().split(" ")[0]));

		return dailyVo;
	}

	@Override
	public ArrayList<DailyStatisticsVO> selectList() {
		ArrayList<DailyStatisticsVO> dailyVo = dailyMapper.selectList();
		for (DailyStatisticsVO dailyStatisticsVO : dailyVo) {
			dailyStatisticsVO.setDailyDate((dailyStatisticsVO.getDailyDate().split(" ")[0]));

		}
		return dailyVo;
	}

	@Override
	public void insertAndupdateDaily() {
		this.dailyMapper.insertAndupdateDaily();

	}

}
