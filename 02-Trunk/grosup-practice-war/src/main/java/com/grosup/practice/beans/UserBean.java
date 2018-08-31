package com.grosup.practice.beans;

/**
 * 人员基础类
 * @author xuelifei
 *
 */
public class UserBean {
	
    private int id;
	
	@Override
	public String toString() {
		return "UserBean [id=" + id + ", name=" + name + ", classID=" + classID
				+ ", icon=" + icon + ", wxID=" + wxID + ", status=" + status
				+ ", userType=" + userType + ", className=" + className
				+ ", gradeName=" + gradeName + ", schoolName=" + schoolName
				+ "]";
	}

	private String name;
	//班级id
	private int classID;
	//头像
	private String icon;
	//微信ID
	private String wxID;
	//用户性别 0 女 1男
	private int gender;
	//用户状态：注册是否通过审核
	private int status;
	//用户角色类别 :0 学生 1老师
	private int userType;
	
	private String className;
	
	private String gradeName;
	
	private String schoolName;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

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

	public String getWxID() {
		return wxID;
	}

	public void setWxID(String wxID) {
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

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}
}
