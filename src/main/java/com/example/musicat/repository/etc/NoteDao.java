package com.example.musicat.repository.etc;

import com.example.musicat.domain.etc.NoteVO;

import java.util.ArrayList;
import java.util.Map;


public interface NoteDao {
	public NoteVO test();
	public ArrayList<NoteVO> selectNoteList(int userNo, int isUserReciever, int pageSize, int startOffset);
	public int selectTotalNoteCount(int userNo, int isRecieve);
	public void updateRead(int noteNo, int userNo);
	public NoteVO selectNote(int noteNo);

	// 쪽지 insert
	public int insertNoteContent(String content) throws Exception;
	public void insertNote_member(Map<String, Object> map) throws Exception;
	public void insertNote_counterpart(Map<String, Object> map) throws Exception;
	
	// 쪽지 delete
	public void deleteNote(NoteVO note);
	public void updateRecieveNoteDeleteOnNoteContent(NoteVO note);
	public void updateSendNoteDeleteOnNoteContent(NoteVO note);
	public void deleteNoteContent();
}
