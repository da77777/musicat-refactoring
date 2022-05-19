package com.example.musicat.mapper.member;

import com.example.musicat.domain.member.FollowVO;
import com.example.musicat.domain.member.MemberVO;
import com.example.musicat.domain.member.ProfileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface FollowMapper {
    int selectFollowingCount(int memberNo);
    int selectFollowedCount(int memberNo);
    // 상대 멤버의 팔로우 여부
    int existFollow(int myNo, int opNo);
    void insertFollow(int myNo, int opNo);
    void deleteFollow(int myNo, int opNo);
}
