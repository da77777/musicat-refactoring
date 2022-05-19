package com.example.musicat.mapper.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.musicat.domain.board.FileVO;

@Mapper
public interface FileMapper {
	
	//파일 1개 조회
	FileVO selectFile(int fileNo);

	//파일 등록
	void insertFile(FileVO fileVO);

	//썸네일 조회
	List<FileVO> selectThumbFile(FileVO fileVo);
	
	//파일 삭제
	void deleteFile(int FileNo);

	//게시글에 속한 파일 모두 조회
	List<FileVO> selectArticleFiles(int articleNo);
}
