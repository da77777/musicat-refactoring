package com.example.musicat.service.etc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.musicat.domain.etc.NoteVO;
import com.example.musicat.domain.member.MemberVO;
import com.example.musicat.repository.etc.NoteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteDao dao;
	
	@Override
	public NoteVO test() {
		// TODO Auto-generated method stub
		return dao.test();
	}

	@Override
	public ArrayList<NoteVO> selectNoteList(int userNo, int isUserReciever, int pageSize, int startOffset) {
		// TODO Auto-generated method stub
		return dao.selectNoteList(userNo, isUserReciever, pageSize, startOffset);
	}

	@Override
	public int retriveTotalNoteCount(int userNo, int isRecieve) {
		// TODO Auto-generated method stub
		return dao.selectTotalNoteCount(userNo, isRecieve);
	}

	@Override
	public void updateRead(int noteNo, int userNo) {
		dao.updateRead(noteNo, userNo);
	}

	@Override
	public NoteVO retriveNote(int noteNo) {
		// TODO Auto-generated method stub
		return dao.selectNote(noteNo);
	}

	@Transactional
	@Override
	public boolean registerNote(NoteVO note, MemberVO user) {
		// TODO Auto-generated method stub
		boolean isSuccess = false;
		try {
			int index = dao.insertNoteContent(note.getContent());

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("notecontent_no", index);
			map.put("member_no", user.getNo());
			map.put("member_nickname", user.getNickname());
			map.put("counterpart_no", note.getCounterpart_no());
			map.put("counterpart_nickname", note.getCounterpart_nickname());
			map.put("sendrecieve_member", 0);
			map.put("sendrecieve_counterpart", 1);

			// 내가 보낸 쪽지 insert
			dao.insertNote_member(map);
			// 상대가 받은 쪽지 insert
			dao.insertNote_counterpart(map);
			
			isSuccess = true;
		} catch (Exception e) {
			isSuccess = false;
			e.printStackTrace();
		}
		
		return isSuccess;
	}

	@Transactional
	@Override
	public void removeNote(ArrayList<NoteVO> notelist, int isRecieve) {
		
		notelist.forEach(note -> dao.deleteNote(note));
		
		
		if(isRecieve == 1)
			notelist.forEach(note -> dao.updateRecieveNoteDeleteOnNoteContent(note));
		else if(isRecieve == 0)
			notelist.forEach(note -> dao.updateSendNoteDeleteOnNoteContent(note));
		
		dao.deleteNoteContent();
	}
	
}
