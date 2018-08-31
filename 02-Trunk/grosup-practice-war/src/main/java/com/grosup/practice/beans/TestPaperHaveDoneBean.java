package com.grosup.practice.beans;

public class TestPaperHaveDoneBean {
	
	private int userID;
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	private String paperKey;
	
	private String paperName;
	
	private int useTime;
	
	private String isDone;
	
	private int userScore;
	
	private int doneRight;
	
	private int quesCount;

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

	public int getUseTime() {
		return useTime;
	}

	public void setUseTime(int useTime) {
		this.useTime = useTime;
	}

	public String getIsDone() {
		return isDone;
	}

	public void setIsDone(String isDone) {
		this.isDone = isDone;
	}

	public int getUserScore() {
		return userScore;
	}

	public void setUserScore(int userScore) {
		this.userScore = userScore;
	}

	public int getDoneRight() {
		return doneRight;
	}

	public void setDoneRight(int doneRight) {
		this.doneRight = doneRight;
	}

	public int getQuesCount() {
		return quesCount;
	}

	public void setQuesCount(int quesCount) {
		this.quesCount = quesCount;
	}

}
  