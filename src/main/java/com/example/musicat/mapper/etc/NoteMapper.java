package com.example.musicat.mapper.etc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.musicat.domain.etc.NoteVO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface NoteMapper {
	public NoteVO test();
	public ArrayList<NoteVO> selectNoteList(HashMap<String, Object> params);
	public int selectTotalNoteCount(int userNo, int isRecieve);
	public NoteVO selectNote(int noteNo);
	
	// 읽음 update
	public void updateRead_member(int noteNo, int userNo, int isRecieve);
	public void updateRead_counterpart(int noteNo, int userNo, int isRecieve);
	
	// 쪽지 insert
	public void insertNoteContent(String content) throws Exception;
	public int selectLastID() throws Exception;
	public void insertNote_member(Map<String, Object> map) throws Exception;
	public void insertNote_counterpart(Map<String, Object> map) throws Exception;
	
	// 쪽지 delete
	public void deleteNote(NoteVO note);
	public void updateRecieveDeleteOnNoteContent(NoteVO note);
	public void updateSendDeleteOnNoteContent(NoteVO notelist);
	public void deleteNoteContent();
	
}
