package com.bootcamp.portal.utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

import org.apache.log4j.Logger;

public class MailMessageSender implements Runnable {

	public interface AfterSendHandler {
		void run(Object param);
	}

	private static Logger LOGGER = Logger.getLogger(MailMessageSender.class);
	private Message message;
	private String log;
	private AfterSendHandler handler;

	public MailMessageSender() {
		super();
	}

	public MailMessageSender(Message message, String log,
			AfterSendHandler handler) {
		this();
		this.message = message;
		this.log = log;
		this.handler = handler;
	}

	private void runHandler(Object param) {
		if (handler != null) {
			handler.run(param);
		}
	}

	@Override
	public void run() {
		runSync();
	}

	public void runSync() {
		try {
			Transport.send(message);
			LOGGER.info("Mail sent to " + log);
			runHandler(null);
		} catch (MessagingException e) {
			String err = String.format(
					"Unable to send Email to %s. Details: %s", log,
					e.getMessage());
			LOGGER.error(err, e);
			runHandler(e.getMessage());
		}
	}
}