package com.grosup.practice.beans;

import java.util.List;

public class SubjectDetail {
	
	private List<KnowledgeDTO> knowledges;
	
	private int subjectID;
	
	public int getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(int subjectID) {
		this.subjectID = subjectID;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	private String subjectName;

	public List<KnowledgeDTO> getKnowledges() {
		return knowledges;
	}

	public void setKnowledges(List<KnowledgeDTO> knowledges) {
		this.knowledges = knowledges;
	}
	

}
