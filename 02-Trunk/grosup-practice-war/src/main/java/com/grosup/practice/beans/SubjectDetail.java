package com.grosup.practice.beans;

import java.util.List;

public class SubjectDetail {
	
	private int subID;
	
	private String subName;
	
	private List<TypeDetail> types;

	public int getSubID() {
		return subID;
	}

	public void setSubID(int subID) {
		this.subID = subID;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public List<TypeDetail> getTypeDetails() {
		return types;
	}

	public void setTypeDetails(List<TypeDetail> typeDetails) {
		this.types = typeDetails;
	}
}
