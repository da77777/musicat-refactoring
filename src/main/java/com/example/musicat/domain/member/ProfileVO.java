package com.example.musicat.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("profileVo")
public class ProfileVO {
    private int no;
    private String originalFileName;
    private String systemFileName;
    private long fileSize;
    private String bio;

    public ProfileVO(String originalFileName, String systemFileName, long fileSize) {
        this.originalFileName = originalFileName;
        this.systemFileName = systemFileName;
        this.fileSize = fileSize;
    }

    public ProfileVO(int no, String originalFileName, String systemFileName, long fileSize) {
        this.no = no;
        this.originalFileName = originalFileName;
        this.systemFileName = systemFileName;
        this.fileSize = fileSize;
    }

}
