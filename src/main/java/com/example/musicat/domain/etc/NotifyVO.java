package com.example.musicat.domain.etc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Alias("notifyVo")
public class NotifyVO {
    private int notify_no;
    private int member_no;
    private String content;
    private String sendDate;
    private String readDate;
    private String link;

    public NotifyVO(int member_no, String content, String link) {
        this.member_no = member_no;
        this.content = content;
        this.link = link;
    }
}
