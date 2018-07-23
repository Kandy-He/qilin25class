package com.grosup.practice.util;

public class GrosupException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final int errcode;
	
	private final String message;
	
	/**
	 * @return the errcode
	 */
	public int getErrcode() {
		return errcode;
	}

	/**
	 * @param message
	 */
	public GrosupException(int errcode, String message) {
		super("[" + errcode + "]" + message);
		this.errcode = errcode;
		this.message=message;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public GrosupException(int errcode, String message, Throwable cause) {
		this(errcode, message);
		this.addSuppressed(cause);
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getLocalizedMessage() {
		return super.getMessage();
	}
}
