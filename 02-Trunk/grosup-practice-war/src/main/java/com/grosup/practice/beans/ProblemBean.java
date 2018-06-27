package com.grosup.practice.beans;


public class ProblemBean {
	//题目ID
	private int id;
	//题型关键字
	private String proTypeKey;
	//题型所属类别关键字
	private String categoryKey;
	//题型子类别关键字
	private String categoryChildKey;
	//所属知识点ID
	private int typeID;
	//表达式123
	private String expression1;
	
	private String expression2;

	private String expression3;
	//题目描述
	private String description;
	//题目答案
	private String answer;
	
	public String getProTypeKey() {
		return proTypeKey;
	}
	public void setProTypeKey(String proTypeKey) {
		this.proTypeKey = proTypeKey;
	}
	public String getCategoryKey() {
		return categoryKey;
	}
	public void setCategoryKey(String categoryKey) {
		this.categoryKey = categoryKey;
	}
	public String getCategoryChildKey() {
		return categoryChildKey;
	}
	public void setCategoryChildKey(String categoryChildKey) {
		this.categoryChildKey = categoryChildKey;
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
}
