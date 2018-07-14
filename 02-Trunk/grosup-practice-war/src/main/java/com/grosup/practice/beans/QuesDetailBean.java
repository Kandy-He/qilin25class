package com.grosup.practice.beans;


public class QuesDetailBean {
	//题目ID
	private int id;
	
	private int userID;
	
	private String paperKey;
	
	private int quesPosition;
	
	private int quesID;
	//表达式123
	private String expression1;
	
	private String expression2;

	private String expression3;
	//题目描述
	private String description;
	//答案描述
	private String answerDesc;
	//题目答案
	private String answer;
	//答题人所填答案
	private String userAnswer;
	//题目分数
	private int quesScore;
	
	private int quesToScore;
	
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getAnswerDesc() {
		return answerDesc;
	}
	public void setAnswerDesc(String answerDesc) {
		this.answerDesc = answerDesc;
	}
	public String getUserAnswer() {
		return userAnswer;
	}
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}
	public int getQuesScore() {
		return quesScore;
	}
	public void setQuesScore(int quesScore) {
		this.quesScore = quesScore;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getPaperKey() {
		return paperKey;
	}
	public void setPaperKey(String paperKey) {
		this.paperKey = paperKey;
	}
	public int getQuesPosition() {
		return quesPosition;
	}
	public void setQuesPosition(int quesPosition) {
		this.quesPosition = quesPosition;
	}
	public int getQuesID() {
		return quesID;
	}
	public void setQuesID(int quesID) {
		this.quesID = quesID;
	}
	public int getQuesToScore() {
		return quesToScore;
	}
	public void setQuesToScore(int quesToScore) {
		this.quesToScore = quesToScore;
	}
}
