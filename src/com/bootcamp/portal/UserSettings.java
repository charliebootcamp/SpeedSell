package com.bootcamp.portal;

import java.util.Properties;

public class UserSettings {
	private final static String SERVER_NAME = "server.name";
	private static final String SETTING_SYSTEM_ROOT_URL = "systemRootUrl";

	private static final String PROFILE_DIR = "profile.dir";
	private static final String THREAD_CORE_SIZE = "threadpool.coresize";
	private static final String THREAD_MAX_SIZE = "threadpool.maxsize";
	private static final String THREAD_ALIVE_TIME_SEC = "threadpool.keepalive.sec";

	private static final int DEFAULT_THREAD_CORE_SIZE = 5;
	private static final int DEFAULT_THREAD_MAX_SIZE = 10;
	private static final int DEFAULT_THREAD_ALIVE_TIME_SEC = 60 * 60 * 24;// 1_day

	// mail
	public final static String MAIL_HOST = "mail.smtp.host";
	public final static String MAIL_PORT = "mail.smtp.port";
	public final static String MAIL_METHOD = "mail.smtp.method";

	public final static String MAIL_USER = "mail.smtp.username";
	public final static String MAIL_PASSWORD = "mail.smtp.password";
	public final static String MAIL_AUTH = "mail.smtp.auth";
	public final static String MAIL_FROM_ADDRESS = "mail.smtp.fromAddress";

	public static final String SEND_MAIL_ASYNC = "mail.send.async";
	private static final String DEFAULT_SEND_MAIL_ASYNC = "true";

	private final static String[] MAIL_PROPERTIES = { MAIL_HOST, MAIL_PORT,
			MAIL_AUTH, MAIL_FROM_ADDRESS, MAIL_USER, MAIL_PASSWORD };

	private static Properties properties = new Properties();

	private UserSettings() {
	}

	static public void init(Properties props) {
		properties.putAll(props);
	}

	public static Object setProperty(String key, String value) {
		return properties.setProperty(key, value);
	}

	public static String getProperty(Object key) {
		return (String) properties.get(key);
	}

	private static int getIntegerProperty(String propertyName,
			Integer defaultValue) {
		try {
			return Integer.parseInt(properties.getProperty(propertyName,
					defaultValue.toString()));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static int getThreadCoreSize() {
		return getIntegerProperty(THREAD_CORE_SIZE, DEFAULT_THREAD_CORE_SIZE);
	}

	public static int getThreadMaxSize() {
		return getIntegerProperty(THREAD_MAX_SIZE, DEFAULT_THREAD_MAX_SIZE);
	}

	public static long getThreadAliveTime() {
		return getIntegerProperty(THREAD_ALIVE_TIME_SEC,
				DEFAULT_THREAD_ALIVE_TIME_SEC);
	}

	public static boolean isSendMailAsync() {
		return Boolean.parseBoolean(properties.getProperty(SEND_MAIL_ASYNC,
				DEFAULT_SEND_MAIL_ASYNC));
	}

	public static String getServerName() {
		return getProperty(SERVER_NAME);
	}

	public static String getProfileDir() {
		return getProperty(PROFILE_DIR);
	}

	public static void setProfileDir(String value) {
		setProperty(PROFILE_DIR, value);
	}

	// for future
	public static String getSystemRootUrl() {
		return properties.getProperty(SETTING_SYSTEM_ROOT_URL, "localhost");
	}

	static public Properties getSmtpConfiguration() {
		Properties result = new Properties();
		for (String key : MAIL_PROPERTIES) {
			result.put(key, properties.getProperty(key, ""));
		}

		String smtpMethod = properties.getProperty(MAIL_METHOD, "")
				.toUpperCase();
		if ("SSL".equals(smtpMethod)) {
			result.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			result.put("mail.smtp.socketFactory.port",
					properties.getProperty(MAIL_PORT, ""));
		} else if ("TLS".equals(smtpMethod)) {
			result.put("mail.smtp.starttls.enable", "true");
		}

		return result;
	}

}