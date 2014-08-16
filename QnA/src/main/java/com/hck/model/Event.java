package com.hck.model;

import java.io.Serializable;
import javax.persistence.*;




/**
 * The persistent class for the Events database table.
 * 
 */
@Entity
@Table(name="Events")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int eventID;

    
	private String description;

   
	private String edate;

	private int flagged;

 
	private String notes;

	private String sdate;

	private String user;

    public Event() {
    }

	public int getEventID() {
		return this.eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public int getFlagged() {
		return this.flagged;
	}

	public void setFlagged(int flagged) {
		this.flagged = flagged;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}