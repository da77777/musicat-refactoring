package com.example.musicat.domain.etc;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatVO {
    private String roomId;
    private String writer;
    private String message;
    private boolean isSystem = false;
    private boolean isUserOuted = false;
}
