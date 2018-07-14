package com.grosup.practice.beans;

/**
 * 错题
 *
 */
public class RecordBean {
	
	public RecordBean() {
		this.status = 0;
	}
	
	private int id;
	
	private int userID;
	
	private String recordTime;
	
	private int typeID;
	
	private String proKey;
	
	private String description;
	
	private String expression1;
	
	private String expression2;
	
	private String expression3;
	
	private String result;
	
	private String answerDesc;
	
	private String correct;
	
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCorrect() {
		return correct;
	}

	public void setCorrect(String correct) {
		this.correct = correct;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getExpression1() {
		return expression1;
	}

	public void setExpression1(String expression1) {
		this.expression1 = expression1;
	}

	public String getExpression2() {
		return expression2;
	}

	public void setExpression2(String expression2) {
		this.expression2 = expression2;
	}

	public String getExpression3() {
		return expression3;
	}

	public void setExpression3(String expression3) {
		this.expression3 = expression3;
	}

	public String getProKey() {
		return proKey;
	}

	public void setProKey(String proKey) {
		this.proKey = proKey;
	}

	public String getAnswerDesc() {
		return answerDesc;
	}

	public void setAnswerDesc(String answerDesc) {
		this.answerDesc = answerDesc;
	}
}
