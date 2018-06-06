package com.grosup.practice.beans;

public class SessionBean {
	
	public SessionBean(String third_session, String session_key, String openId) {
		this.third_session = third_session;
		this.session_key = session_key;
		this.openId = openId;
	}

	private String third_session;
	
	private String session_key;
	
	private String openId;

	public String getThird_session() {
		return third_session;
	}

	public void setThird_session(String third_session) {
		this.third_session = third_session;
	}

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
