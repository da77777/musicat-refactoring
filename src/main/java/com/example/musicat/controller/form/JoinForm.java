package com.example.musicat.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class JoinForm {

    @Email(message = "아이디는 이메일 형식으로 작성해야 합니다.")
    private String email;
    private String password;
    private String confirmPassword;
    @Size(min = 4, max = 8, message = "닉네임은 4 ~ 8글자로 설정해야 합니다.")
    private String nickname;
}
