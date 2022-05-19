package com.example.musicat.service.member;

import com.example.musicat.domain.member.FollowVO;
import com.example.musicat.domain.member.MemberVO;

import java.util.ArrayList;
import java.util.List;

public interface FollowService {
    int countFollowing(int no);
    int countFollowed(int no);
    List<MemberVO> retrieveFollowingList(int no);
    List<MemberVO> retrieveFollowedList(int no);
    int checkFollow(int myNo, int opNo);
    void addFollow(int myNo, int opNo);
    void removeFollow(int myNo, int opNo);
}
