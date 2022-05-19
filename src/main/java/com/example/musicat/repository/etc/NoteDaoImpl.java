package com.example.musicat.repository.etc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.musicat.domain.etc.NoteVO;
import com.example.musicat.mapper.etc.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("noteDao")
public class NoteDaoImpl implements NoteDao {
	@Autowired
	private NoteMapper mapper;
	
	@Override
	public NoteVO test() {
		// TODO Auto-generated method stub
		return mapper.test();
	}

	@Override
	public ArrayList<NoteVO> selectNoteList(int userNo, int isUserReciever, int pageSize, int startOffset) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("userNo", userNo);
		map.put("isUserReciever", isUserReciever);
		map.put("pageSize", pageSize);
		map.put("startOffset", startOffset);
		
		return mapper.selectNoteList(map);
	}

	@Override
	public int selectTotalNoteCount(int userNo, int isRecieve) {
		// TODO Auto-generated method stub
		return mapper.selectTotalNoteCount(userNo, isRecieve);
	}

	@Override
	public void updateRead(int noteNo, int userNo) {
		mapper.updateRead_member(noteNo, userNo, 1);
		mapper.updateRead_counterpart(noteNo, userNo, 0);
	}

	@Override
	public NoteVO selectNote(int noteNo) {
		// TODO Auto-generated method stub
		return mapper.selectNote(noteNo);
	}

	@Override
	public int insertNoteContent(String content) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertNoteContent(content);
		return mapper.selectLastID();
	}

	@Override
	public void insertNote_member(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertNote_member(map);
	}

	@Override
	public void insertNote_counterpart(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		mapper.insertNote_counterpart(map);
	}

	@Override
	public void deleteNote(NoteVO note) {
		// TODO Auto-generated method stub
		mapper.deleteNote(note);
	}

	@Override
	public void updateRecieveNoteDeleteOnNoteContent(NoteVO note) {
		// TODO Auto-generated method stub
		mapper.updateRecieveDeleteOnNoteContent(note);
	}

	@Override
	public void updateSendNoteDeleteOnNoteContent(NoteVO note) {
		// TODO Auto-generated method stub
		mapper.updateSendDeleteOnNoteContent(note);
	}

	@Override
	public void deleteNoteContent() {
		// TODO Auto-generated method stub
		mapper.deleteNoteContent();
	}

}
