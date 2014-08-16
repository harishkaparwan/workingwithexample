package com.hck.model;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Events {

	private static final long serialVersionUID = 1L;

	 @Id
	private int id;
	
	private String start;
	
	private String end;
	
	private String title;
	
	private String body;
	
	private int userId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	 public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	    public String toString() {
	    //    return "{id='" + id + "', start='" + start + "', end='" + end + "', title='" + title + "'}";
	    return "{'id':" + id + ", 'start':'" + start + "', 'end':'" + end + "', 'title':'" + title + "','userId':"+userId +"}";
	    }
}
