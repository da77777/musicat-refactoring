package com.example.musicat.service.board;

import java.io.IOException;
import java.util.List;

import com.example.musicat.domain.board.ArticleVO;
import com.example.musicat.domain.board.FileVO;

public interface FileService {

	// File 1개 조회
	FileVO selectOneFile(int fileNo);

	// File등록
	void uploadFile(ArticleVO article);

	// 게시글과 게시글에 속한 모든 파일 조회
	ArticleVO fileList(ArticleVO article, List<FileVO> files);

	// 썸네일 생성
	void createThumbnail(FileVO thumbnail) throws IOException;

	// 썸네일 조회
	FileVO retrieveThumbFile(FileVO file);

	// 파일 삭제
	void removeFile(int fileNo);
	
	// 게시글의 모든 파일 조회( ajax로 파일 삭제 후 조회에 사용 )
	List<FileVO> selectArticleFiles(int articleNo);
}
