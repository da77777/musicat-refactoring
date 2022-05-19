package com.example.musicat.service.member;

import com.example.musicat.domain.member.FollowVO;
import com.example.musicat.domain.member.MemberVO;
import com.example.musicat.mapper.member.FollowMapper;
import com.example.musicat.mapper.member.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("followService")
@Transactional(readOnly = true)
public class FollowServiceImpl implements FollowService{
    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public int countFollowing(int no) {
        return this.followMapper.selectFollowingCount(no);
    }

    @Override
    public int countFollowed(int no) {
        return this.followMapper.selectFollowedCount(no);
    }

    @Override
    public List<MemberVO> retrieveFollowingList(int no) {
        return this.memberMapper.selectFollowingList(no);
    }

    @Override
    public List<MemberVO> retrieveFollowedList(int no) {
        return this.memberMapper.selectFollowedList(no);
    }

    @Override
    public int checkFollow(int myNo, int opNo) {
        return this.followMapper.existFollow(myNo, opNo);
    }

    @Override
    @Transactional
    public void addFollow(int myNo, int opNo) {
        this.followMapper.insertFollow(myNo, opNo);
    }

    @Override
    @Transactional
    public void removeFollow(int myNo, int opNo) {
        this.followMapper.deleteFollow(myNo, opNo);
    }
}
