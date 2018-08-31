package com.grosup.practice.beans;

/**
 * 错题
 *
 */
public class RecordBean {
	
	public RecordBean() {
		this.status = 0;
	}
	
	private String problemKey;
	
	private int userID;
	
	private String knowledgeKey;
	
	private String quesTypeKey;
	
	private String recordTime;
	
	private String expression1;
	
	private String expression2;
	
	private String expression3;
	
	private String userAnswer;
	
	// 题目描述
	private String description;
	// 答案描述
	private String answerDesc;
	
	private int status;

	public String getProblemKey() {
		return problemKey;
	}

	public void setProblemKey(String problemKey) {
		this.problemKey = problemKey;
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

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getKnowledgeKey() {
		return knowledgeKey;
	}

	public void setKnowledgeKey(String knowledgeKey) {
		this.knowledgeKey = knowledgeKey;
	}

	public String getQuesTypeKey() {
		return quesTypeKey;
	}

	public void setQuesTypeKey(String quesTypeKey) {
		this.quesTypeKey = quesTypeKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAnswerDesc() {
		return answerDesc;
	}

	public void setAnswerDesc(String answerDesc) {
		this.answerDesc = answerDesc;
	}

}
