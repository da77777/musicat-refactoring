package com.example.musicat.domain.paging;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Alias("criteria")
public class Criteria {
	private int currentPageNo; // 현재 페이지 번호
	private int dataPerPage; // 한 페이지에 출력된 데이터의 수
	private int pageSize; // 화면 하단에 출력될 size ex) 1~5, 10~20

	private int startPage;
	private int maxPage;
	private int endPage;

	public int getPageStart() {
		return (this.currentPageNo - 1) * dataPerPage;
	}


	public void bindEndPage(int totalCount){
		int endNo = totalCount / 16 + 1;
		this.endPage = endNo;
	}


	public static Criteria getThumbnailPaging(int startPage, int totalCount){
		Criteria criteria = new Criteria();
		criteria.currentPageNo = startPage;
		criteria.pageSize = 10;
		criteria.dataPerPage = 16;
		criteria.setStartPage(startPage);
		criteria.setMaxPage(totalCount);
		criteria.setEndPage(startPage);
		return criteria;
	}

	private void setMaxPage(int totalCount){
		this.maxPage = totalCount / pageSize + 1;
	}

	private void setStartPage(int startPage){
		this.startPage = ((startPage - 1)/ pageSize) * pageSize + 1;
	}

	private void setEndPage(int startPage){
		int endNo = startPage + pageSize - 1;
		if (endNo > this.maxPage) {
			this.endPage = this.maxPage - 1;
		} else {
			this.endPage = endNo;
		}
	}


	
	public Criteria() {
		this.currentPageNo = 1;
		this.dataPerPage = 2;
	}
	
	public void setCurrentPageNo(int page) {
		if(page <= 0) {
			this.currentPageNo= 1;
		}
		else {
			this.currentPageNo = page;
		}
	}
	
	public void setDataPerPage(int num) {
		int cnt = this.dataPerPage;
		
		if(num != cnt) {
			this.dataPerPage = cnt;
		}
		else {
			this.dataPerPage = num;
		}
	}
}
