package com.grosup.practice.beans;


public class ProblemBean {
	//题目关键字
	private String problemKey;
	//知识点关键字
	private String knowledgeKey;
	//题型关键字
	private String quesTypeKey;
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
	
	public String getProblemKey() {
		return problemKey;
	}
	public void setProblemKey(String problemKey) {
		this.problemKey = problemKey;
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
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
