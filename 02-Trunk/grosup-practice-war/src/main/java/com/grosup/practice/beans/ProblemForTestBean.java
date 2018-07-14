package com.grosup.practice.beans;


public class ProblemForTestBean {
	//题目ID
	private int id;
	//题型关键字
	private String proKey;
	//
	private int quesPosition;
//	//题型所属类别关键字
//	private String categoryKey;
//	//题型子类别关键字
//	private String categoryChildKey;
	//所属知识点ID
	private int typeID;
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
	//题目分数
	private int quesScore;
	
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
	public String getProKey() {
		return proKey;
	}
	public void setProKey(String proKey) {
		this.proKey = proKey;
	}
	public int getQuesScore() {
		return quesScore;
	}
	public void setQuesScore(int quesScore) {
		this.quesScore = quesScore;
	}
	public int getQuesPosition() {
		return quesPosition;
	}
	public void setQuesPosition(int quesPosition) {
		this.quesPosition = quesPosition;
	}
}
