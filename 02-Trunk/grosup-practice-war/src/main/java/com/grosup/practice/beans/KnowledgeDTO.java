package com.grosup.practice.beans;

public class KnowledgeDTO {
	
	private String knowledgeKey;
	
	private String knowledgeName;
	
	private int haveDone;
	
	private int onceRight;
	
	private int flower;
	/**
	 * 一次做对正确率
	 */
	private int onceRightRate;


	public int getOnceRightRate() {
		return onceRightRate;
	}

	public void setOnceRightRate(int onceRightRate) {
		this.onceRightRate = onceRightRate;
	}

	public String getKnowledgeKey() {
		return knowledgeKey;
	}

	public void setKnowledgeKey(String knowledgeKey) {
		this.knowledgeKey = knowledgeKey;
	}

	public String getKnowledgeName() {
		return knowledgeName;
	}

	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}

	public int getHaveDone() {
		return haveDone;
	}

	public void setHaveDone(int haveDone) {
		this.haveDone = haveDone;
	}

	public int getOnceRight() {
		return onceRight;
	}

	public void setOnceRight(int onceRight) {
		this.onceRight = onceRight;
	}

	public int getFlower() {
		return flower;
	}

	public void setFlower(int flower) {
		this.flower = flower;
	}
}
