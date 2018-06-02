package com.grosup.practice.beans;

public class SessionBean {
	
	public SessionBean(String sessionKey, String sessionValue) {
		this.sessionKey = sessionKey;
		this.sessionValue = sessionValue;
	}

	private String sessionKey;
	
	private String sessionValue;

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getSessionValue() {
		return sessionValue;
	}

	public void setSessionValue(String sessionValue) {
		this.sessionValue = sessionValue;
	}
}
