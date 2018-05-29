package com.grosup.practice.beans;

/**
 * 学生基础类
 * @author xuelifei
 *
 */
public class Student {
	
	private int id;
	
	private String name;
	//班级id
	private int classID;
	//头像
	private String icon;
	//微信ID
	private long wxID;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getClassID() {
		return classID;
	}

	public void setClassID(int classID) {
		this.classID = classID;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public long getWxID() {
		return wxID;
	}

	public void setWxID(long wxID) {
		this.wxID = wxID;
	}
}
