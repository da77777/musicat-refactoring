package com.example.musicat.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.example.musicat.domain.etc.DailyStatisticsVO;
import com.example.musicat.domain.etc.TotalStatisticsVO;
import com.example.musicat.service.etc.DailyStatisticsServiceImpl;
import com.example.musicat.service.etc.TotalStatisticsServiceImpl;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ManagerController {

//	user security 기본 id : user / password : 콘솔 Using generated security password: ex)75f93c5c-c13c-4bcf-aa11-b9c47b0f899a

	@Autowired
	DailyStatisticsServiceImpl dailyServiceImpl;

	@Autowired
	TotalStatisticsServiceImpl totalServiceImpl;

//	@GetMapping("/manager")
//	public String manager(Model model) {
//		model.addAttribute("managerContent", "fragments/MemberContent");
//		return "view/home/viewManagerTemplate";
//	}

	@GetMapping("/daily")
	public String managerDaily(Model model) {

		dailyServiceImpl.registerAndmodifyDaily();

		ArrayList<DailyStatisticsVO> dailyList = dailyServiceImpl.retrieveList();

		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

		String chkDate = SDF.format(calendar.getTime());





		if (dailyList.size()==1){
			calendar.add(Calendar.DATE, -1);
			chkDate = SDF.format(calendar.getTime());

			dailyList.add(new DailyStatisticsVO(2,chkDate,1,0));

			calendar.add(Calendar.DATE, -1);
			chkDate = SDF.format(calendar.getTime());

			dailyList.add(new DailyStatisticsVO(3,chkDate,2,0));


		}else if (dailyList.size()==2){
			calendar.add(Calendar.DATE, -2);
			chkDate = SDF.format(calendar.getTime());

			dailyList.add(new DailyStatisticsVO(3,chkDate,2,0));

		}

		model.addAttribute("managerContent", "fragments/StatisticsDailyContent");

		model.addAttribute("dailyList", dailyList);

		return "view/home/viewManagerTemplate";
	}

	@GetMapping("/total")
	public String managerTotal(Model model) {

		totalServiceImpl.registerAndmodifyTotal();
		
		TotalStatisticsVO totalVo = totalServiceImpl.retrieve();
		
		

		model.addAttribute("managerContent", "fragments/StatisticsTotalContent");

		model.addAttribute("totalOne", totalVo);

		return "view/home/viewManagerTemplate";
	}

}
