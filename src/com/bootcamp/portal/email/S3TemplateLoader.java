package com.bootcamp.portal.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;


import freemarker.cache.TemplateLoader;

public class S3TemplateLoader implements TemplateLoader {

	protected static final Logger LOGGER = Logger
			.getLogger(S3TemplateLoader.class);

	private static Map<String, Object> templatesMap = new HashMap<>();

	@Override
	public void closeTemplateSource(Object arg0) throws IOException {
	}

	static public InputStream getTemplateInputStream(String templateName) {
		//String dir = UserSettings.getProfileDir() + "\\templates\\email";
		String dir = System.getProperty("user.dir") + "\\config\\templates\\email";
		File file = new File(dir + "\\" + templateName);
		try {
			FileInputStream fis = new FileInputStream(file);
			return fis;
		} catch (FileNotFoundException e) {
			LOGGER.error("Email Template file cannot be found");
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Object findTemplateSource(String name) throws IOException {
		if (!templatesMap.containsKey(name)) {
			templatesMap.put(name,
					getTemplateFromStream(getTemplateInputStream(name)));
		}
		return templatesMap.get(name);
	}

	private String getTemplateFromStream(Object templateSource)
			throws IOException {
		InputStream is = (InputStream) templateSource;
		StringWriter writer = new StringWriter();
		IOUtils.copy(is, writer);

		return writer.toString();
	}

	@Override
	public long getLastModified(Object arg0) {
		return 0;
	}

	@Override
	public Reader getReader(Object templateSource, String arg1)
			throws IOException {
		return new StringReader((String) templateSource);
	}

	// for Tests
	public static void setTemplate(String templateName, String template) {
		if (!templatesMap.containsKey(templateName)) {
			templatesMap.put(FilenameUtils.removeExtension(templateName)
					+ "_en_US." + FilenameUtils.getExtension(templateName),
					template);
		}
	}
}