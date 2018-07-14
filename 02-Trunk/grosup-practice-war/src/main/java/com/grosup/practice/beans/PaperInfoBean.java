package com.grosup.practice.beans;

public class PaperInfoBean {
	//总分
	private int totalScore;
	//总题数
	private int quesCount;
	//做对题数
	private int quesCountUnRight;
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public int getQuesCount() {
		return quesCount;
	}
	public void setQuesCount(int quesCount) {
		this.quesCount = quesCount;
	}
	public int getQuesCountUnRight() {
		return quesCountUnRight;
	}
	public void setQuesCountUnRight(int quesCountUnRight) {
		this.quesCountUnRight = quesCountUnRight;
	}
}
