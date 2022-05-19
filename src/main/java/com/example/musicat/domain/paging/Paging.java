package com.example.musicat.domain.paging;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Alias("paging")
@ToString
@NoArgsConstructor
@Slf4j
public class Paging{
	private int total;
	private int showPage = 3;
	private int startPage;
	private int endPage;
	private int endNumber;
	private boolean prev;
	private boolean next;
	private Criteria crt;
	
	private void pagingData() {
		endPage = (int)(Math.ceil(crt.getCurrentPageNo() / (double)showPage) * showPage);
		startPage = (endPage - showPage) + 1;
		
		endNumber = (int)(Math.ceil(total / (double)crt.getDataPerPage()));
		if(endPage > endNumber) {
			endPage = endNumber;
		}
		prev = startPage == 1? false : true;
		next = endPage * crt.getDataPerPage() >= total ? false : true;
	}
	
	public void setShowPage(int no) {
		int now = this.showPage;
		if(no <= 0) {
			this.showPage = now;
		}
		else {
			this.showPage = no;
		}
	}
	
	public void setTotal(int tot) {
		this.total = tot;
		pagingData();
	}
	
	
	
}
