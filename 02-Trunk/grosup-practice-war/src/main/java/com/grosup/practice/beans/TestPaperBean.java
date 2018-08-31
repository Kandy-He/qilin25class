package com.grosup.practice.beans;

public class TestPaperBean {
	
	private String paperKey;
	
	private String paperName;
	
	private int gradeID;
	
	private int timeLimit;
	
	private int quesCount;
	//试卷总分
	private int totalScore;

	public String getPaperKey() {
		return paperKey;
	}

	public void setPaperKey(String paperKey) {
		this.paperKey = paperKey;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public int getGradeID() {
		return gradeID;
	}

	public void setGradeID(int gradeID) {
		this.gradeID = gradeID;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public int getQuesCount() {
		return quesCount;
	}

	public void setQuesCount(int quesCount) {
		this.quesCount = quesCount;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
}
  