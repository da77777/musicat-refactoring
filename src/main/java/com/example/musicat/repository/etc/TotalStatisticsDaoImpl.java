package com.example.musicat.repository.etc;

import com.example.musicat.domain.etc.TotalStatisticsVO;
import com.example.musicat.mapper.etc.TotalStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("totalDao")
public class TotalStatisticsDaoImpl implements TotalStatisticsDao {

	private TotalStatisticsMapper totalMapper;

	private TotalStatisticsVO totalVo;

	@Autowired
	public TotalStatisticsDaoImpl(TotalStatisticsMapper totalMapper) {
		this.totalMapper = totalMapper;
	}
	
	@Override
	public TotalStatisticsVO selectTotal() {
		return this.totalMapper.selectTotal();
	}

	@Override
	public void insertAndupdateTotal() {
		this.totalMapper.insertAndupdateTotal();

	}

}
