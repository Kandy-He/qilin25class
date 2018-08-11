package com.grosup.practice.beans;

public class StatisticsBean {
	
	@Override
	public String toString() {
		return "StatisticsBean [userID=" + userID + ", flower=" + flower
				+ ", rank=" + rank + ", classID=" + classID
				+ ", quesTotalDone=" + quesTotalDone + ", quesOnceRightTotal="
				+ quesOnceRightTotal + ", quesWrongTotal=" + quesWrongTotal
				+ ", quesCorrectionRate=" + quesCorrectionRate + "]";
	}

	private int userID;
	
	private String name;
	
	private String icon;
	
	private int flower;
	
	private int rank;
	
	private int classID;
	
	private int quesTotalDone;
	
	private int quesOnceRightTotal;
	
	private int quesWrongTotal;
	
	private String quesCorrectionRate;
	
	private String highestWrongType;
	/**
	 * 错题订正率
	 */
	private int wrongCorrection;
	
	public String getHighestWrongType() {
		return highestWrongType;
	}

	public void setHighestWrongType(String highestWrongType) {
		this.highestWrongType = highestWrongType;
	}

	public int getQuesTotalDone() {
		return quesTotalDone;
	}

	public void setQuesTotalDone(int quesTotalDone) {
		this.quesTotalDone = quesTotalDone;
	}

	public int getQuesOnceRightTotal() {
		return quesOnceRightTotal;
	}

	public void setQuesOnceRightTotal(int quesOnceRightTotal) {
		this.quesOnceRightTotal = quesOnceRightTotal;
	}

	public int getQuesWrongTotal() {
		return quesWrongTotal;
	}

	public void setQuesWrongTotal(int quesWrongTotal) {
		this.quesWrongTotal = quesWrongTotal;
	}

	public String getQuesCorrectionRate() {
		return quesCorrectionRate;
	}

	public void setQuesCorrectionRate(String quesCorrectionRate) {
		this.quesCorrectionRate = quesCorrectionRate;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getFlower() {
		return flower;
	}

	public void setFlower(int flower) {
		this.flower = flower;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getClassID() {
		return classID;
	}

	public void setClassID(int classID) {
		this.classID = classID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getWrongCorrection() {
		return wrongCorrection;
	}

	public void setWrongCorrection(int wrongCorrection) {
		this.wrongCorrection = wrongCorrection;
	}
}
