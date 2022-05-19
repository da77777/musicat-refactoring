package com.example.musicat.domain.music;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetaFile {
    private Long id;

    private String originalFileName;

    private String systemFileName;

    private Long fileSize;

    private String fileType;

    private Instant wirteDate;

    public MetaFile(String originalFileName, String systemFileName, String fileType, Long fileSize) {
        this.originalFileName = originalFileName;
        this.systemFileName = systemFileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
    public MetaFile(String originalFileName, String systemFileName, Long fileSize) {
        this.originalFileName = originalFileName;
        this.systemFileName = systemFileName;
        this.fileSize = fileSize;
    }

}
