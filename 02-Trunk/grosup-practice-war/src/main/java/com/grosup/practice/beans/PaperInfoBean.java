package com.grosup.practice.beans;

public class PaperInfoBean {
	//总分
	private int totalScore;
	//总题数
	private int quesCount;
	//做对题数
	private int quesCountRight;
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
	public int getQuesCountRight() {
		return quesCountRight;
	}
	public void setQuesCountRight(int quesCountRight) {
		this.quesCountRight = quesCountRight;
	}
}
