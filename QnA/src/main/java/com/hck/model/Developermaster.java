package com.hck.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Developermaster implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int ticketNo;

    private String anyLibrarycodechanges;

    private String anyTeamsitechanges;

    private String assignee;

    private String component;

    private String created;

    private String devEndDate;

    private String devStartDate;

    private String duedate;

    private String fixversion;

    private String impactofChanges;

    private String issuetype;

    private String jiraTicket;

    private String plannedDate;

    private String priority;

    private String project;

    private String QAComments;

    private String QAEndDate;

    private String QAInstructions;

    private String QAIssuesfound;

    private String QAStartDate;

    private String remainingestimate;

    private String reopened;

    private String reopenedTestedDate;

    private String reopenedTestingComments;

    private String reporter;

    private String resolution;

    private String resolutionSummary;

    private String resolutionTime;

    private String reviewComments;

    private String reviewedBy;

    private String reviewedOn;

    private String stagdate;

    private String status;

    private String summary;

    private String testedBy;

    private String timespent;

    private String unitTesting;

    private String updated;

    private String verifiedChecklist;

	public int getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(int ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getAnyLibrarycodechanges() {
		return anyLibrarycodechanges;
	}

	public void setAnyLibrarycodechanges(String anyLibrarycodechanges) {
		this.anyLibrarycodechanges = anyLibrarycodechanges;
	}

	public String getAnyTeamsitechanges() {
		return anyTeamsitechanges;
	}

	public void setAnyTeamsitechanges(String anyTeamsitechanges) {
		this.anyTeamsitechanges = anyTeamsitechanges;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getDevEndDate() {
		return devEndDate;
	}

	public void setDevEndDate(String devEndDate) {
		this.devEndDate = devEndDate;
	}

	public String getDevStartDate() {
		return devStartDate;
	}

	public void setDevStartDate(String devStartDate) {
		this.devStartDate = devStartDate;
	}

	public String getDuedate() {
		return duedate;
	}

	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	public String getFixversion() {
		return fixversion;
	}

	public void setFixversion(String fixversion) {
		this.fixversion = fixversion;
	}

	public String getImpactofChanges() {
		return impactofChanges;
	}

	public void setImpactofChanges(String impactofChanges) {
		this.impactofChanges = impactofChanges;
	}

	public String getIssuetype() {
		return issuetype;
	}

	public void setIssuetype(String issuetype) {
		this.issuetype = issuetype;
	}

	public String getJiraTicket() {
		return jiraTicket;
	}

	public void setJiraTicket(String jiraTicket) {
		this.jiraTicket = jiraTicket;
	}

	public String getPlannedDate() {
		return plannedDate;
	}

	public void setPlannedDate(String plannedDate) {
		this.plannedDate = plannedDate;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getQAComments() {
		return QAComments;
	}

	public void setQAComments(String qAComments) {
		QAComments = qAComments;
	}

	public String getQAEndDate() {
		return QAEndDate;
	}

	public void setQAEndDate(String qAEndDate) {
		QAEndDate = qAEndDate;
	}

	public String getQAInstructions() {
		return QAInstructions;
	}

	public void setQAInstructions(String qAInstructions) {
		QAInstructions = qAInstructions;
	}

	public String getQAIssuesfound() {
		return QAIssuesfound;
	}

	public void setQAIssuesfound(String qAIssuesfound) {
		QAIssuesfound = qAIssuesfound;
	}

	public String getQAStartDate() {
		return QAStartDate;
	}

	public void setQAStartDate(String qAStartDate) {
		QAStartDate = qAStartDate;
	}

	public String getRemainingestimate() {
		return remainingestimate;
	}

	public void setRemainingestimate(String remainingestimate) {
		this.remainingestimate = remainingestimate;
	}

	public String getReopened() {
		return reopened;
	}

	public void setReopened(String reopened) {
		this.reopened = reopened;
	}

	public String getReopenedTestedDate() {
		return reopenedTestedDate;
	}

	public void setReopenedTestedDate(String reopenedTestedDate) {
		this.reopenedTestedDate = reopenedTestedDate;
	}

	public String getReopenedTestingComments() {
		return reopenedTestingComments;
	}

	public void setReopenedTestingComments(String reopenedTestingComments) {
		this.reopenedTestingComments = reopenedTestingComments;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getResolutionSummary() {
		return resolutionSummary;
	}

	public void setResolutionSummary(String resolutionSummary) {
		this.resolutionSummary = resolutionSummary;
	}

	public String getResolutionTime() {
		return resolutionTime;
	}

	public void setResolutionTime(String resolutionTime) {
		this.resolutionTime = resolutionTime;
	}

	public String getReviewComments() {
		return reviewComments;
	}

	public void setReviewComments(String reviewComments) {
		this.reviewComments = reviewComments;
	}

	public String getReviewedBy() {
		return reviewedBy;
	}

	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	public String getReviewedOn() {
		return reviewedOn;
	}

	public void setReviewedOn(String reviewedOn) {
		this.reviewedOn = reviewedOn;
	}

	public String getStagdate() {
		return stagdate;
	}

	public void setStagdate(String stagdate) {
		this.stagdate = stagdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTestedBy() {
		return testedBy;
	}

	public void setTestedBy(String testedBy) {
		this.testedBy = testedBy;
	}

	public String getTimespent() {
		return timespent;
	}

	public void setTimespent(String timespent) {
		this.timespent = timespent;
	}

	public String getUnitTesting() {
		return unitTesting;
	}

	public void setUnitTesting(String unitTesting) {
		this.unitTesting = unitTesting;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getVerifiedChecklist() {
		return verifiedChecklist;
	}

	public void setVerifiedChecklist(String verifiedChecklist) {
		this.verifiedChecklist = verifiedChecklist;
	}
	
	
    
}