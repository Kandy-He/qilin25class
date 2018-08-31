package com.grosup.practice.beans;

public class PaperStatBean {
	
	private String paperKey;
	
	private String paperName;
	
	private int paperDoneTotal;
	
	private int score;
	
	private int wrongCount;
	
	private double wrongCorrectionRate;
	
	private int useTime;
	
	private String wrongHigh;

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

	public int getPaperDoneTotal() {
		return paperDoneTotal;
	}

	public void setPaperDoneTotal(int paperDoneTotal) {
		this.paperDoneTotal = paperDoneTotal;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getWrongCount() {
		return wrongCount;
	}

	public void setWrongCount(int wrongCount) {
		this.wrongCount = wrongCount;
	}

	public double getWrongCorrectionRate() {
		return wrongCorrectionRate;
	}

	public void setWrongCorrectionRate(double wrongCorrectionRate) {
		this.wrongCorrectionRate = wrongCorrectionRate;
	}

	public String getWrongHigh() {
		return wrongHigh;
	}

	public void setWrongHigh(String wrongHigh) {
		this.wrongHigh = wrongHigh;
	}

	public int getUseTime() {
		return useTime;
	}

	public void setUseTime(int useTime) {
		this.useTime = useTime;
	}
}
