package com.example.musicat.controller.form;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GradeArticleForm {

    private  int articleNo;
    private  int memberNo;
    private  String prorade;

}
