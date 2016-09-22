package com.bootcamp.portal.web.model;

public class LoginForm {
	private String username;
	private String password;
	private boolean rememberMe;
	private String returnUrl;
	private String applicationName;

	public LoginForm() {

	}

	public LoginForm(String username, String password, boolean rememberme) {
		super();
		this.username = username;
		this.password = password;
		this.rememberMe = rememberme;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
}
