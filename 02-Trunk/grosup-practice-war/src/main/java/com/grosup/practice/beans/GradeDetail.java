package com.grosup.practice.beans;

import java.util.List;

public class GradeDetail {
	
	private int gradeID;
	
	private String gradeName;
	
	private List<SubjectDetail> subjects;

	public int getGradeID() {
		return gradeID;
	}

	public void setGradeID(int gradeID) {
		this.gradeID = gradeID;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public List<SubjectDetail> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectDetail> subjects) {
		this.subjects = subjects;
	}
}	
