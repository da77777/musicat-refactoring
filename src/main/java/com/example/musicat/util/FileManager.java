package com.example.musicat.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.musicat.service.board.FileService;
import com.example.musicat.service.board.FileServicleImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.musicat.domain.board.FileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Component
public class FileManager {
	

//  local
	private String fileDir = "C:/Users/양다예/AppData/Local/upload/";
	private String thumbnailFileDir = "C:/Users/양다예/AppData/Local/upload/thumbnail/";

//	배포
//	@Value("${file.dir}")
//	private String fileDir;
//	@Value("${file.thumbDir}")
//	private String thumbnailFileDir;

	public static void createDir(String dir) {
		try{
			Files.createDirectories(Paths.get(dir));
		} catch (IOException e) {
			throw new RuntimeException("Could not create upload folder");
		}
	}

	// fileName을 받아서 fullPath 반환
	public String getFullPath(String fileName) {
		return this.fileDir + fileName;
	}

	// 여러개 저장
	public List<FileVO> uploadFiles(List<MultipartFile> multipartFiles) throws IOException{
		List<FileVO> uploadFileResult = new ArrayList<>();
		for (MultipartFile multipartFile : multipartFiles) { // part로 한개씩 꺼낸다.
			if (!multipartFile.isEmpty()) { // Null이 아니라면
				uploadFileResult.add(uploadFile(multipartFile));
			}
		}
		return uploadFileResult;
	}


	// 파일 저장
	public FileVO uploadFile(MultipartFile multipartFile) throws IOException{
		if (multipartFile.isEmpty()) { // 파일이 없는 경우
			return null;
		}
		String originalFileName = multipartFile.getOriginalFilename(); // 사용자가 올린 파일명
		String systemFileName = createSystemFileName(originalFileName);
		Long fileSize = multipartFile.getSize(); // File크기
		multipartFile.transferTo(new File(getFullPath(systemFileName))); // 파일 경로에 저장

		return new FileVO(originalFileName, systemFileName, fileSize); // 파일 저장하고 원본, 시스템 파일명 반환
	}


	// 썸네일 생성
	public void createThumbnail(String systemFileName) throws IOException {
		File image = new File(getFullPath(systemFileName));
		File thumbnail = new File(getFullPath("thumbnail/thumb" + systemFileName));

		//systemFileName에서 확장자 추출
		int pos = systemFileName.lastIndexOf("."); 
		String ext = systemFileName.substring(pos + 1);
		// ext: 확장자
		if (image.exists()) { //썸네일 생성
				Thumbnails.of(image).size(190, 150).outputFormat(ext).toFile(thumbnail);
		}
	}

	
	// Upload Folder에서 삭제
	public void deleteUploadFile(FileVO fileVO){
		File file = new File(getFullPath(fileVO.getSystemFileName()));
		if(file.exists()){
			file.delete();
		}
	}


	// 서버에서 관리할 파일이름 추출
	private String createSystemFileName(String originalFileName) {
		String uuid = UUID.randomUUID().toString(); // 랜덤 UUID값 생성
		String ext = extract(originalFileName); // 원본 파일에서 확장자 추출
		return uuid + "." + ext;
	}

	// 확장자 추출
	private String extract(String originaFileName) {
		int pos = originaFileName.lastIndexOf(".");
		return originaFileName.substring(pos + 1);
	}
}
