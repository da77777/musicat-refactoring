package com.example.musicat.mapper.member;

import com.example.musicat.domain.member.ProfileVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
public interface ProfileMapper {
    // profile 수정
    void updateProfile(Map<String, Object> map);

    // profile 화면
    ProfileVO selectProfile(int no);

    // 회원가입 시 profile 생성
    void insertProfile(ProfileVO profile);

    // bio 수정
    void updateBio(Map<String, Object> map);
}
