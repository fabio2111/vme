package org.fao.fi.vme.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * 
 * 
 * @author Erik van Ingen
 */
@Entity
public class Meeting {

	@Id
	private int id;

	/**
	 * The Vme where the meeting is on about.
	 */
	@OneToOne
	public Vme discussedVme;

	/** 
	 */
	@Temporal(TemporalType.DATE)
	private Date date;

	/** */
	private String reportSummary;

	/** */
	private String committee;

	/**
	 * meetingDocument
	 */
	public Source meetingDocument;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getReportSummary() {
		return reportSummary;
	}

	public void setReportSummary(String reportSummary) {
		this.reportSummary = reportSummary;
	}

	public String getCommittee() {
		return committee;
	}

	public void setCommittee(String committee) {
		this.committee = committee;
	}

	public Vme getDiscussedVme() {
		return discussedVme;
	}

	public void setDiscussedVme(Vme discussedVme) {
		this.discussedVme = discussedVme;
	}

	public Source getMeetingDocument() {
		return meetingDocument;
	}

	public void setMeetingDocument(Source meetingDocument) {
		this.meetingDocument = meetingDocument;
	}

}
