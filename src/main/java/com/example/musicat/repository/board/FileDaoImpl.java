package com.example.musicat.repository.board;

import java.util.List;

import com.example.musicat.domain.board.FileVO;
import com.example.musicat.mapper.board.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("fileDao")
public class FileDaoImpl implements FileDao {

	@Autowired
	private FileMapper fileMapper;
	
	@Override
	public FileVO selectFile(int fileNo) {
		return this.fileMapper.selectFile(fileNo);
	}
	
	public List<FileVO> selectThumbFile(FileVO file){
		return this.fileMapper.selectThumbFile(file);
	}
	
	
	
	@Override
	public List<FileVO> selectFileList(int articleNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertFile(FileVO fileVO) {
		this.fileMapper.insertFile(fileVO);
	}

	@Override
	public void deleteFile(int FileNo) {
		this.fileMapper.deleteFile(FileNo);
	}

	@Override
	public List<FileVO> selectArticleFiles(int articleNo) {
		return this.fileMapper.selectArticleFiles(articleNo);
	}
}
