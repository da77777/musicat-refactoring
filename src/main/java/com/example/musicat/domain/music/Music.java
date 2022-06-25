package com.example.musicat.domain.music;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Music {

    private Long id;

    private MetaFile file;

    private Thumbnail thumbnail;

    private String title;

    private int memberNo;

    private int articleNo;

    private List<Link> links;

    private String memberName;


    public Music(MetaFile file, Thumbnail thumbnail, String title, int memberNo, int articleNo) {
        this.file = file;
        this.thumbnail = thumbnail;
        this.title = title;
        this.memberNo = memberNo;
        this.articleNo = articleNo;
    }
}