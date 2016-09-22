package com.bootcamp.portal.web.model;

import java.io.Serializable;

public class UserMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	private String messageClass;
	private String messageText;

	public UserMessage(String clz, String msg) {
		messageClass = clz;
		messageText = msg;
	}

	public UserMessage(String msg) {
		messageClass = "success";
		messageText = msg;
	}

	public UserMessage(Exception ex) {
		messageClass = "error";
		messageText = ex.getMessage();
	}

	public String getMessageClass() {
		return messageClass;
	}

	public void setMessageClass(String messageClass) {
		this.messageClass = messageClass;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
}
