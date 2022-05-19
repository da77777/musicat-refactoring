package com.example.musicat.domain.board;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

//@Getter
@Setter
@Alias("fileVo")
public class FileVO {

    private int no;
    private int articleNo;
    private String originalFileName; // 유저가 올린 진짜 파일 이름
    private String systemFileName; // 서버에서 관리될 파일 이름
    private Long fileSize;
    private int fileType; // 0이면 첨부, 1이면 이미지


    //==생성 메소드==//
    public static FileVO createFile(int articleNo, String originalFileName, String systemFileName, Long fileSize, int fileType){
        FileVO fileVO = new FileVO();
        fileVO.articleNo = articleNo;
        fileVO.originalFileName = originalFileName;
        fileVO.systemFileName = systemFileName;
        fileVO.fileSize = fileSize;
        fileVO.fileType = fileType;
        return fileVO;
    }

    @Override
    public String toString() {
        return "File{" +
                "no=" + no +
                ", articleNo=" + articleNo +
                ", originalFileName='" + originalFileName + '\'' +
                ", systemFileName='" + systemFileName + '\'' +
                ", fileSize=" + fileSize +
                ", fileType=" + fileType +
                '}';
    }


    public FileVO() {
    }

    public FileVO(String originalFileName, String systemFileName, Long fileSize) {
        this.originalFileName = originalFileName;
        this.systemFileName = systemFileName;
        this.fileSize = fileSize;
    }

    public Integer getNo() {
        return no;
    }

    public int getArticleNo() {
        return articleNo;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public String getSystemFileName() {
        return systemFileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public int getFileType() {
        return fileType;
    }

}
