package com.grosup.practice.beans;

public class StatisticsBean {
	
	@Override
	public String toString() {
		return "StatisticsBean [userID=" + userID + ", flower=" + flower
				+ ", rank=" + rank + ", classID=" + classID + "]";
	}

	private int userID;
	
	private int flower;
	
	private int rank;
	
	private int classID;
	
	private StudentBean userBean;

	public StudentBean getUserBean() {
		return userBean;
	}

	public void setUserBean(StudentBean userBean) {
		this.userBean = userBean;
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
}
