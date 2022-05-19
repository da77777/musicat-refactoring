package com.example.musicat.mapper.etc;

import com.example.musicat.domain.etc.NotifyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotifyMapper {
    public void insertNotify(NotifyVO notifyVo);
    public List<NotifyVO> selectNotify(int member_no);
    public void updateNotifyRead(int notify_no);
    public NotifyVO selectNotifyOne(int notify_no);
}
