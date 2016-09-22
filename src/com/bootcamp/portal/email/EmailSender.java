package com.bootcamp.portal.email;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.bootcamp.portal.UserSettings;
import com.bootcamp.portal.domain.Person;
import com.bootcamp.portal.mgr.dto.LetterDto;
import com.bootcamp.portal.utils.BaseThreadPool;
import com.bootcamp.portal.utils.MailMessageSender;
import com.bootcamp.portal.utils.MailMessageSender.AfterSendHandler;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EmailSender {

	private static final String SEND_VERIFICATION_CODE_TEMPLATE = "verificationcode.flt";
	private static final String SEND_RESET_CODE_TEMPLATE = "password_reset_code.flt";
	private static final String SEND_EMAIL_CHANGE_CODE_TEMPLATE = "email_change_code.flt";
	private static final String NEW_USER_VERIFICATION_EMAIL_SUBJECT = "Verification Code";
	private static final String NEW_USER_RESET_PASSWORD_SUBJECT = "Reset password";
	private static final String WIN_AUCTION_REMINDER_SUBJECT = "Remind to pay";
	private static final String WIN_AUCTION_REMINDER = "win_auction_reminder.flt";
	private static final String SEND_QUESTION = "send_question.flt";
	private static final String SEND_ANNONYM_QUESTION = "send_ann_question.flt";
	
	private static Logger LOGGER = Logger.getLogger(EmailSender.class);

	private final Configuration cfg = new Configuration();

	private BaseThreadPool threadPool = new BaseThreadPool();

	private boolean initialized;

	@PostConstruct
	protected void initConfig() {
		this.cfg.setTemplateLoader(new S3TemplateLoader());
		initialized = true;
		LOGGER.info("Config");
	}
	
	public void sendWinnerReminder(Person winner, Long lotId, String lotName){
		Map<String, Object> data = makeGenericData();
		data.putAll(makeUserData(winner));
		data.put("lotId",lotId);
		data.put("lotName", lotName);
		
		LOGGER.info("Sending reminder");
		initConfig();
		
		sendMultipartEmail(winner.getEmail(),
					WIN_AUCTION_REMINDER_SUBJECT,
					WIN_AUCTION_REMINDER, data);
	}
	
	public void sendUserVerificationCode(Person user) {
		Map<String, Object> data = makeGenericData();
		data.putAll(makeUserData(user));
		data.put("code", user.getVerificationCode());

		LOGGER.info("Sending verification code");
		
		sendMultipartEmail(user.getEmail(),
				NEW_USER_VERIFICATION_EMAIL_SUBJECT,
				SEND_VERIFICATION_CODE_TEMPLATE, data);

	}
	
	public void sendEmailChangeCode(Person user) {
		Map<String, Object> data = makeGenericData();
		data.putAll(makeUserData(user));
		data.put("code", user.getVerificationCode());

		LOGGER.info("Sending email change code");
		
		sendMultipartEmail(user.getEmail(),
				NEW_USER_VERIFICATION_EMAIL_SUBJECT,
				SEND_EMAIL_CHANGE_CODE_TEMPLATE, data);

	}
	
	public void sendUserResetPswdCode(Person user) {
		Map<String, Object> data = makeGenericData();
		data.putAll(makeUserData(user));
		data.put("code", user.getPasswordChangeCode());

		LOGGER.info("Sending reset code");		
		
			sendMultipartEmail(user.getEmail(),
					NEW_USER_RESET_PASSWORD_SUBJECT,
					SEND_RESET_CODE_TEMPLATE, data);
		

	}

	private Map<String, Object> makeUserData(Person user) {
		HashMap<String, Object> result = new HashMap<>();
		result.put("username", user.getName());
		return result;
	}

	private HashMap<String, Object> makeGenericData() {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("systemBaseUrl", UserSettings.getSystemRootUrl());
		return result;
	}

	private void sendMessage(Message message, String log,
			AfterSendHandler handler) {
		MailMessageSender job = new MailMessageSender(message, log, handler);
		if (UserSettings.isSendMailAsync()) {
			threadPool.execute(job);
		} else {
			job.runSync();
		}
	}

	protected void sendMultipartEmail(String email, String subject,
			String template, Map<String, Object> data) {
		sendMultipartEmail(email, subject, template, data, null);
	}

	protected void sendMultipartEmail(String email, String subject,
			String template, Map<String, Object> data, AfterSendHandler handler) {
		String log = String.format("%s. Subject: %s. Template: %s", email,
				subject, template);
		final Properties props = UserSettings.getSmtpConfiguration();
		final Session session;

		final String fromAddr = props.remove(UserSettings.MAIL_FROM_ADDRESS)
				.toString();

		if ("true".equalsIgnoreCase(props.getProperty(UserSettings.MAIL_AUTH))) {
			final String userName = props.remove(UserSettings.MAIL_USER)
					.toString();
			final String password = props.remove(UserSettings.MAIL_PASSWORD)
					.toString();

			session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(userName,
									password);
						}
					});
		} else {
			session = Session.getInstance(props);
		}

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromAddr));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
			message.setSubject(subject);
			Multipart mp = new MimeMultipart();

			Writer out = new StringWriter();
			try {
				generateFromTemplate(template, data, out);
				BodyPart htmlPart = new MimeBodyPart();
				htmlPart.setContent(out.toString(), "text/html; charset=utf-8");
				mp.addBodyPart(htmlPart);
			} finally {
				IOUtils.closeQuietly(out);
			}

			message.setContent(mp);
			sendMessage(message, log, handler);
		} catch (MessagingException e) {
			if (handler != null) {
				handler.run(e.getMessage());
			}
			String err = String.format(
					"Unable to send Email to %s. Details: %s", log,
					e.getMessage());
			LOGGER.error(err, e);
		}
	}

	protected void generateFromTemplate(String template,
			Map<String, Object> data, Writer out) throws MessagingException {
		if (!initialized) {
			LOGGER.error("Email system was not configured properly.");
			throw new MessagingException("System is not initialized");
		}

		try {
			Template temp = cfg.getTemplate(template);
			temp.process(data, out);
			out.flush();
		} catch (IOException | TemplateException e) {
			String err = String.format("Unable to generate email body: %s",
					e.getMessage());
			LOGGER.error(err, e);
			throw new MessagingException(err, e);
		}
	}

	public void sendQuestion(LetterDto ltr) throws MessagingException {
		try {
			Map<String, Object> data = makeGenericData();
			data.put("message", ltr.getMessage());
			data.put("senderName", ltr.getSenderName());
			data.put("recepientName", ltr.getRecipientName());
			if(ltr.getSenderId()!= null){
				data.put("senderId", ltr.getSenderId());
			}
			data.put("senderId", ltr.getSenderId());
			data.put("recepientId", ltr.getRecipientId());
			data.put("senderEmail", ltr.getSenderEmail());
			
			LOGGER.info("Sending email");		
			
			if(ltr.getSenderId()!= null){
				sendMultipartEmail(ltr.getRecEmail(),
						ltr.getTitle(),
						SEND_QUESTION, data);
			}else{
				sendMultipartEmail(ltr.getRecEmail(),
						ltr.getTitle(),
						SEND_ANNONYM_QUESTION, data);
			}
		} catch (Exception e) {
			String err = String.format("Unable to generate email body: %s",
					e.getMessage());
			LOGGER.error(err, e);
			throw new MessagingException(err, e);
		}
	}
}