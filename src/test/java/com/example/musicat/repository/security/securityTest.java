package com.example.musicat.repository.security;

import com.example.musicat.mapper.member.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@Transactional

public class securityTest {

    @Qualifier("memberMapper")
    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPwd;

    @Test
    public void 패스워드일치여부() throws Exception {
        //given
        String password = "1111";
        log.info("password : " + password);

        //when
        String encodePassword = bCryptPwd.encode(password);
        log.info("encodePassword : " + encodePassword);

        //then

        //matches : 평문 패스워드와 암호화된 패스워드의 일치 여부 확인
        boolean a = bCryptPwd.matches(password, encodePassword);
        if(a == true) {
            log.info("비밀번호 일치");
        } else {
            log.info("비밀번호 불일치");
        }
        
//        if(bCryptPwd.encode(password).equals(encodePassword)) {
//            log.info("이거 ----- " + bCryptPwd.encode(password) + " = " + encodePassword);
//        } else {
//            log.info("일치하지 않음");
//        }
    }

}
