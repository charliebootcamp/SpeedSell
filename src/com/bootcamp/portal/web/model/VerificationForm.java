package com.bootcamp.portal.web.model;

public class VerificationForm {
	private String username;
	private String code;
	private String applicationName;

	public VerificationForm() {

	}

	public VerificationForm(String username, String code) {
		super();
		this.username = username;
		this.code = code;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
