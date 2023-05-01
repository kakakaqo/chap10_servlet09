package com.javalab.dto;

/** VO, DTO 역할 **/

public class BoardModel {

	// 필드
	private int no = 0;
	private String subject = null;
	private String writer = null;
	private String contents = null;
	private int hit = 0;
	
	// 기본 생성자
	public BoardModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	// 오버로딩 생성자
	public BoardModel(int no, String subject, String writer, String contents, int hit) {
		super();
		this.no = no;
		this.subject = subject;
		this.writer = writer;
		this.contents = contents;
		this.hit = hit;
	}

	// getter/setter
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}
	
}