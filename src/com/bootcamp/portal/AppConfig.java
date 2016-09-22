package com.bootcamp.portal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bootcamp.portal.db.update.UpdateDB;
import com.bootcamp.portal.domain.Person;
import com.bootcamp.portal.email.EmailSender;
import com.bootcamp.portal.mgr.PersonDAO;
import com.bootcamp.portal.mgr.base.BaseManager;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackageClasses = { BaseManager.class, PersonDAO.class,
		EmailSender.class })
public class AppConfig {
	private final static String APPLICATION_VERSION = "1.01";

	private static String appName = "bootcamp";

	public final static String SETTINGS_FILE_NAME = "portal.properties";

	private static final String LOG4J_XML = "log4j.xml";

	private static final String APPLICATION_NAME = "Bootcamp";

	private static String profile = "main";

	private static Logger LOGGER = Logger.getLogger(AppConfig.class);

	public static void init(String prof) {
		profile = prof;
	}

	private File profileDir;
	private org.hibernate.cfg.Configuration hibernateConfiguration;
	private HibernateTransactionManager transactionManager;
	private SessionFactory sessionFactory;

	public AppConfig() throws IOException {

		profileDir = new File(
                new File("C:/SpeedSell/git/SpeedSell/bootcamp"), "config");
//		profileDir = new File(
//				new File(System.getProperty("user.dir")), "config");
		initLog4j();
		Properties hbmProps = readProperties();
		checkDBforUpdates(hbmProps);
		initializeHibernate(hbmProps);
		readSettings();
	}

	static public String getVersion() {
		return APPLICATION_VERSION;
	}

	static public String getAppName() {
		return APPLICATION_NAME;
	}

	@PostConstruct()
	public void initStatics() {
		// StaticResourceHelper.initStatics(context);
	}

	@Bean
	public SessionFactory sessionFactory() {
		return sessionFactory;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		return transactionManager;
	}

	public org.hibernate.cfg.Configuration getHbmConfiguration() {
		return hibernateConfiguration;
	}

	public String getProfile() {
		return profile;
	}

	private Properties loadProps(String fileName) throws IOException {
		Properties result = new Properties();
		result.load(AppConfig.class.getResourceAsStream(fileName));
		return result;
	}

	private void loadPropertiesFromFile(String fileName, Properties props)
			throws IOException {
		File f = new File(profileDir, fileName);
		try (BufferedReader r = new BufferedReader(new FileReader(f))) {
			props.load(r);
		}
	}

	private Properties readProperties() throws IOException {
		Properties hbmProps = loadProps("hibernate-internal.properties");
		loadPropertiesFromFile("hibernate-external.properties", hbmProps);
		return hbmProps;
	}

	// read settings from Users profile
	private void readSettings() {
		Properties settings = new Properties();
		try {
			loadPropertiesFromFile(SETTINGS_FILE_NAME, settings);
			UserSettings.setProfileDir(profileDir.getPath());
			UserSettings.init(settings);
		} catch (Exception e) {
			LOGGER.error(String.format(
					"Trouble with load user settings file %s: %s",
					SETTINGS_FILE_NAME, e.getMessage()));
		}
	}

	/** check db for update. use google.flyway */
	private void checkDBforUpdates(Properties hbmProps) {
		UpdateDB.getInstance().migrate(
				(String) hbmProps.get("hibernate.connection.url"),
				(String) hbmProps.get("hibernate.connection.username"),
				(String) hbmProps.get("hibernate.connection.password"));
	}

	private void initializeHibernate(Properties hbmProps) throws IOException {
		LocalSessionFactoryBuilder bld = new LocalSessionFactoryBuilder(null);
		bld.getProperties().putAll(hbmProps);
		bld.scanPackages(Person.class.getPackage().getName());

		sessionFactory = bld.buildSessionFactory();

		hibernateConfiguration = bld;
		transactionManager = new HibernateTransactionManager(sessionFactory);
	}

	private void initLog4j() {
		File f = new File(profileDir, LOG4J_XML);
		if (f.exists()) {
			try {
				DOMConfigurator.configure(f.getAbsolutePath());
				return;
			} catch (Exception e) {
				LOGGER.warn("Cannot configure logging from " + profileDir
						+ ". Use default.", e);
			}
		}

		DOMConfigurator.configure(AppConfig.class.getResource(LOG4J_XML));
	}

}
