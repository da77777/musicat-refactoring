package com.example.musicat.repository.board;

import java.io.File;
import java.util.List;

import com.example.musicat.domain.board.FileVO;

public interface FileDao {

	//파일 1개 조회
	FileVO selectFile(int fileNo);

	//게시글의 모든 파일 조회
	List<FileVO> selectFileList(int articleNo);

	//썸네일 추출 FileType Collumn을 가지고 조회
	List<FileVO> selectThumbFile(FileVO file);

	//파일 등록
	void insertFile(FileVO fileVO);

	//파일 삭제
	void deleteFile(int FileNo);
	
	//게시글의 모든 파일 조회회
	List<FileVO> selectArticleFiles(int articleNo);
}
