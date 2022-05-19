package com.example.musicat.domain.etc;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Alias("noteVo")
public class NoteVO {
	private int note_no;
	private int member_no;
	private int notecontent_no;
	private int counterpart_no;
	private String counterpart_nickname;
	private String content;
	private String sendDate;
	private String readDate;
	private int sendrecieve;
	private boolean read = false;

	public NoteVO(int note_no, int member_no, int notecontent_no, int counterpart_no, String counterpart_nickname, String content, String sendDate,
			String readDate) {
		super();
		this.note_no = note_no;
		this.member_no = member_no;
		this.notecontent_no = notecontent_no;
		this.counterpart_no = counterpart_no;
		this.counterpart_nickname = counterpart_nickname;
		this.content = content;
		this.sendDate = sendDate;
		this.readDate = readDate;
		checkRead();

	}

	public NoteVO(int note_no, int member_no, int notecontent_no, int counterpart_no, String counterpart_nickname, String content, String sendDate,
			String readDate, int sendrecieve) {
		super();
		this.note_no = note_no;
		this.member_no = member_no;
		this.notecontent_no = notecontent_no;
		this.counterpart_no = counterpart_no;
		this.counterpart_nickname = counterpart_nickname;
		this.content = content;
		this.sendDate = sendDate;
		this.readDate = readDate;
		this.sendrecieve = sendrecieve;
		checkRead();
	}

	public boolean checkRead() {
		if (readDate.length() > 0)
			read = true;

		return read;
	}

	public void setNote_no(int note_no) {
		this.note_no = note_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public void setNotecontent_no(int notecontent_no) {
		this.notecontent_no = notecontent_no;
	}

	public void setCounterpart_no(int counterpart_no) {
		this.counterpart_no = counterpart_no;
	}

	public void setCounterpart_nickname(String counterpart_nickname) {
		this.counterpart_nickname = counterpart_nickname;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public void setReadDate(String readDate) {
		this.readDate = readDate;
		checkRead();
	}

	public void setSendrecieve(int sendrecieve) {
		this.sendrecieve = sendrecieve;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
	
	
}
