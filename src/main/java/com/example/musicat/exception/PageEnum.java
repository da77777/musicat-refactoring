package com.example.musicat.exception;

import lombok.Getter;

@Getter
public enum PageEnum {

    MUSIC_POST(11, "/musicpost");

    private int pageCode;
    private final String pageURI;

    PageEnum(int pageCode, String pageURI) {
        this.pageCode = pageCode;
        this.pageURI = pageURI;
    }

}
