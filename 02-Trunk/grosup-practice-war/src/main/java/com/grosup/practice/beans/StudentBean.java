package com.grosup.practice.beans;

/**
 * 学生基础类
 * @author xuelifei
 *
 */
public class StudentBean {
	
	private int id;
	
	private String name;
	//班级id
	private int classID;
	//头像
	private String icon;
	//微信ID
	private long wxID;
	//用户状态：0未审核 ，1 已审核
	private int status;
	//用户角色类别 :0 学生 1老师
	private int userType;
	
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}
}
