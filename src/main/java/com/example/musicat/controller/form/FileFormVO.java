package com.example.musicat.controller.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

// 파일 저장용 폼
@Data
public class FileFormVO {
	
	private Long fileNo;
	private MultipartFile importAttacheFile;
	private List<MultipartFile> imageFiles;
	
}
