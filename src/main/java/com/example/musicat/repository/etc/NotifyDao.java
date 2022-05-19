package com.example.musicat.repository.etc;

import com.example.musicat.domain.etc.NotifyVO;
import com.example.musicat.mapper.etc.NotifyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("notifyDao")
public class NotifyDao {
    @Autowired
    private NotifyMapper mapper;

    public void insertNotify(NotifyVO notifyVo) {
        mapper.insertNotify(notifyVo);
    }

    public List<NotifyVO> selectNotify(int member_no) {
        return mapper.selectNotify(member_no);
    }

    public void updateNotifyRead(int notify_no) {
        mapper.updateNotifyRead(notify_no);
    }

    public NotifyVO selectNotifyOne(int notify_no){
        return mapper.selectNotifyOne(notify_no);
    }
}
